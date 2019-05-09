package com.example.sepehr.gameflag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Region;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final String CHOICEES="pref_numberOfChoice";
    public static final String REGIONS="pref_regionsToInclude";

    private boolean PhoneDevice=true;
    private boolean PreferenceChanged=true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //in cod haro zadim ta agar size bozorg ya darkhastie ma nabod mesl tablet ha PHONEDIVICE false shavad ta barname jor digar ejra shavad
        //vali agar bod dar jahat portrait ejra shavad
        //kholase in khat cod ha moshakhas mikonad k app dar hale ejra roye tablet hast ya na
        int _ScrenSize=getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (_ScrenSize==Configuration.SCREENLAYOUT_SIZE_LARGE   ||   _ScrenSize==Configuration.SCREENLAYOUT_SIZE_XLARGE){
            PhoneDevice=false;
        }
        if (PhoneDevice){
            //JAHAT DARKHASTI BARAYE EJRA SHODAN APP**
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);//dar in khat false yani inke ma begozarim maghadir pishfarz harbar seda shavad bad khoroj vorod ya na k ma goftim na
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(PreferenceChangerListener);

        SharedPreferences sharedPreferences=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Set<String> regions=sharedPreferences.getStringSet(REGIONS,null);
        if (regions.size()==0){
            regions.add(getString(R.string.defualt_region));
            editor.putStringSet(REGIONS,regions);
            editor.apply();
        }
    }






    private SharedPreferences.OnSharedPreferenceChangeListener PreferenceChangerListener=new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            PreferenceChanged=true;
//..
            MainActivityFragment _quizFragment=(MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.quizfragment);
//..
            if (s.equals(REGIONS)){
                Set<String> _regions=sharedPreferences.getStringSet(REGIONS,null);

                if (_regions.size()==0){
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    _regions.add(getString(R.string.defualt_region));
                    editor.putStringSet(REGIONS,_regions);
                    editor.apply();
                    Toast.makeText(MainActivity.this,R.string.default_region_massage, Toast.LENGTH_SHORT).show();
            }
            
            }
            Toast.makeText(MainActivity.this,R.string.restarting_quiz, Toast.LENGTH_SHORT).show();



        }
    };




    @Override
    protected void onStart() {
        super.onStart();
        if (PreferenceChanged){
            MainActivityFragment quizFragment=(MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.quizfragment);
            quizFragment.UpdateGuessRows(PreferenceManager.getDefaultSharedPreferences(this));
            quizFragment.UpdateRegions(PreferenceManager.getDefaultSharedPreferences(this));
            quizFragment.resetQuiz();
            PreferenceChanged=false;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int _orientation=getResources().getConfiguration().orientation;
        if (_orientation== Configuration.ORIENTATION_PORTRAIT){
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        else return false;

    }


































    //.........................................................................................................

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //vaghti roye item clik mishavad b activity Setting_Activity miravad
        startActivity(new Intent(MainActivity.this,Setting_Activity.class));

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
