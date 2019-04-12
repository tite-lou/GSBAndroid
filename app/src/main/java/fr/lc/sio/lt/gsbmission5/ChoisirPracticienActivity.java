package fr.lc.sio.lt.gsbmission5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChoisirPracticienActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choisir_practicien2);
    }
    public void retourMenu(View vue){
        Intent connexion = new Intent(this, SaisirCPActivity.class);
        startActivity(connexion);

    }
    public void saisirCP(View vue){
        Intent connexion = new Intent(this, SaisirCPActivity.class);
        startActivity(connexion);

    }
}
