package fr.lc.sio.lt.gsbmission5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ModifierLeCPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_le_cp);
    }
    public void retourChoix(View vue){
        Intent connexion = new Intent(this, ModifierLeCPActivity.class);
        startActivity(connexion);

    }
    public void retourMenu(View vue){
        Intent connexion = new Intent(this, GsbMenuActivity.class);
        startActivity(connexion);

    }
}
