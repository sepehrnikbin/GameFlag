package com.example.sepehr.gameflag;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class MainActivityFragment extends Fragment {

    public static final int Flag_In_Quiz =10;
    private List<String> fileNameList; //name file tasvir parcham ha baraye navahi entekhab shode
    private List<String> quizcountyList; //name file tasvir parchamhayeeee estefade shode dar azmon feli ra negahdarimikonad
    private Set<String> regionsSet; //navahi joghrafiayi ke faal shode hastand ra negahdari mikonad
    private String correctAnswer; //name file parcham baraye pasokh sahih feli ra negahdari mikonad
    private int totalguess; //tedad hads haye sahihh vaaaa ghalati ke bazikon ta kononanjam dade ra negahdari mikonad
    private int correctAnswersss; //tedad had haye sahih ra negahdari mikonad k be tadrij adad an dar akhar 10 mishavad chon 10 parcham vojod darad
    private int guessesRows; //tedad linearlayout haye do kalameyi k dar tanzimat moshakhas mishavad ra negah dari mikonad
    private Random random; //  1*tolid konande adad tasadofi baraye entekhab parcham ast  2*b tor tasadofi pasokh sahih ra dar yeki az dokme haye bazi miandazad
    private Handler handler; //hengami k karbar ye pasokh sahi ra entekhab mikonad az shey handler baraye bargozari parcham digar bad az yek vaghfe kota estefade mikonim
    private Animation ShakeAnimatoin; //harekat hengam ijad yek pasokh ghalatttttt*
    int adad=10;
    private LinearLayout quizLinearLayout;
    private TextView questionNumberTextView;
    private ImageView FlagImageView;
    private LinearLayout[] guessesLinearLayout;
    private TextView AnswerTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        fileNameList = new ArrayList<>();
        quizcountyList = new ArrayList<>();
        random =new Random();
        handler = new Handler();
        ShakeAnimatoin = AnimationUtils.loadAnimation(getActivity(), R.anim.incorrect_shake);
        ShakeAnimatoin.setRepeatCount(3);   ///********mohemmmm yad bgir
        quizLinearLayout = (LinearLayout) view.findViewById(R.id.quizlinearlyout);
        questionNumberTextView = (TextView) view.findViewById(R.id.questionNumberTextView);
        FlagImageView = (ImageView) view.findViewById(R.id.FlagImageee);
        guessesLinearLayout=new LinearLayout[4];
        guessesLinearLayout[0] = (LinearLayout) view.findViewById(R.id.row1LinearLyout);
        guessesLinearLayout[1] = (LinearLayout) view.findViewById(R.id.row2LinearLyout);
        guessesLinearLayout[2] = (LinearLayout) view.findViewById(R.id.row3LinearLyout);
        guessesLinearLayout[3] = (LinearLayout) view.findViewById(R.id.row4LinearLyout);
        AnswerTextView = (TextView) view.findViewById(R.id.answerTextView);
        questionNumberTextView.setText(getString(R.string.question, 1, Flag_In_Quiz));

        //...
        for (LinearLayout row:guessesLinearLayout){

            for(int column=0 ; column<row.getChildCount();column++){
                Button button=(Button)row.getChildAt(column);
                button.setOnClickListener(guessesButtonListener);
            }
        }//.....
        return view;
    }


    private View.OnClickListener guessesButtonListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "hahahahha", Toast.LENGTH_SHORT).show();

        }
    };



    public void UpdateGuessRows(SharedPreferences sharedpreference){
        String Choices=sharedpreference.getString(MainActivity.CHOICEES,null);
       //int hast k bala tarif kardim
        guessesRows=Integer.parseInt(Choices)/2;
        /*guessesLinearLayout[0].setVisibility(View.GONE);
        guessesLinearLayout[1].setVisibility(View.GONE);
        guessesLinearLayout[2].setVisibility(View.GONE);
        guessesLinearLayout[3].setVisibility(View.GONE);*/

        for (LinearLayout layout:guessesLinearLayout){
            layout.setVisibility(View.GONE);
        }
        for (int row=0; row<guessesRows; row++){
            guessesLinearLayout[row].setVisibility(View.VISIBLE);
        }

    }

    public void UpdateRegions(SharedPreferences shared){
        regionsSet=shared.getStringSet(MainActivity.REGIONS,null);
    }




    public void resetQuiz(){
       AssetManager assets=getActivity().getAssets();
       //AssetManager assets=getContext().getAssets();
        fileNameList.clear();

        try {
            for (String regions:regionsSet)
            {
                String[] paths=assets.list(regions);
                for (String _path:paths)
                    fileNameList.add(_path.replace(".png",""));
            }

        }
        catch (IOException exception){
            Log.e("FlagQuiz","Error Loading Image file names",exception);
        }

        correctAnswersss=0;
        totalguess=0;
        quizcountyList.clear();
        int Flagcounter=1;
        int numberofFlags=fileNameList.size();

        while (Flagcounter<=Flag_In_Quiz){  //**ya khata ine
            int randomIndex=random.nextInt(numberofFlags); //**ya khata ine
            String filename=fileNameList.get(randomIndex);
            if (!quizcountyList.contains(filename))
            {
                //quizcountyList.add(filename);
                ++Flagcounter ;
                // Flagcounter++;
           }
        }





        /*try {
            //Flag_In_Quiz=10;
          while (Flagcounter<=Flag_In_Quiz){  //**ya khata ine
              int randomIndex=random.nextInt(numberofFlags); //**ya khata ine
              String filename=fileNameList.get(randomIndex);
              if (!quizcountyList.contains(filename)){
                 quizcountyList.add(filename);
                  ++Flagcounter ;
                 // Flagcounter++;
              }
         }


        }
        catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/
        //LoadNextFlag();
    }


    public void LoadNextFlag(){
        try {
            String nexImage=quizcountyList.remove(0);
            correctAnswer=nexImage;
            AnswerTextView.setText("");
            questionNumberTextView.setText(getString(R.string.question,(correctAnswersss +1),Flag_In_Quiz));
            String regionS=nexImage.substring(0,nexImage.indexOf('-'));
            AssetManager assets=getActivity().getAssets();
            try (InputStream stream=assets.open(regionS + "/" +nexImage +".png")){
                Drawable flag=Drawable.createFromStream(stream,nexImage);
                FlagImageView.setImageDrawable(flag);
                animate(false);

                /*InputStream stream=_Assets.open(regionS + "/" +nexImage +".png");
                Drawable Flag=Drawable.createFromStream(stream,nexImage);
                FlagImageView.setImageDrawable(Flag);
                animate(false);*/

            }catch (Exception e){
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }


    private void animate(boolean animateOut){
        if (correctAnswersss==0)
            return;
        int centerX=(quizLinearLayout.getLeft()+quizLinearLayout.getRight())/2;
        int centerY=(quizLinearLayout.getTop()+quizLinearLayout.getBottom())/2;
        int radius=Math.max(quizLinearLayout.getWidth(),quizLinearLayout.getHeight());
        Animator animator;
        if (animateOut){
            animator= ViewAnimationUtils.createCircularReveal(quizLinearLayout,centerX,centerY,radius,0);
            animator.addListener(new AnimatorListenerAdapter() {
                /**
                 * {@inheritDoc}
                 *
                 * @param animation
                 */
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    LoadNextFlag();
                }
            });



        }else{

            animator=ViewAnimationUtils.createCircularReveal(quizLinearLayout,centerX,centerY,radius,0);
        }
        animator.setDuration(500);
        animator.start();


    }





}
