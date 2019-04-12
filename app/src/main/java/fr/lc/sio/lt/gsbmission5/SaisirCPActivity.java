package fr.lc.sio.lt.gsbmission5;

import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;
import java.lang.String;
import android.app.Activity;

public class SaisirCPActivity extends AppCompatActivity {
    Spinner listPraticien;
    String listP[] ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisir_cp);
    }
    public void retourMenu(View vue){
        Intent connexion = new Intent(this, GsbMenuActivity.class);
        startActivity(connexion);

    }
    public void RemplirSpinner(){

    }

    public void RecupeLaListCp(String[] list){
        listP = new String[list.length];
        listP = list;
        Log.i("TEST-LIST",listP[1]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listP
        );

        listPraticien.setAdapter(adapter);
        listPraticien.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View vue, int position, long id){
                        String practicienSelect = listP[position];
                        //numRapport = position;
                       // lePractSelect.setText(practicienSelect+" "+String.valueOf(numRapport));

                    }
                }
        );
    }
}
