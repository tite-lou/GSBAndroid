package fr.lc.sio.lt.gsbmission5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ListePracticienActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_practicien);
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
