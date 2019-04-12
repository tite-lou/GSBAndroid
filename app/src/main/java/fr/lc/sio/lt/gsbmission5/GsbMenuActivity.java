package fr.lc.sio.lt.gsbmission5;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class GsbMenuActivity extends AppCompatActivity {

    TextView txtBienvenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsb_menu);

       // SharedPreferences prefs.get

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);


        String id = prefs.getString("id","");
        String prenom = prefs.getString("prenom","");
        txtBienvenu = (TextView) findViewById(R.id.id_bienvenue);
        txtBienvenu.setText("Bonjour : "+prenom);
        //Log.d("jh",  res);
    }
    public void seDeconnecter(View vue) {
      //  Session.fermer();
        SharedPreferences sp = this.getSharedPreferences("default",0);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.apply();
        Intent retourAccueil = new Intent(this, MainActivity.class);
        startActivity(retourAccueil);
    }
    public void saisirCP(View vue){
        Intent saisie = new Intent(this, ListePracticienActivity.class);
        startActivity(saisie);
    }
    public void voirCP(View vue){

        Intent voir = new Intent(this, VoirCPActivity.class);
       startActivity(voir);

    }
    public void choisirModifier(View vue){
        Intent voir = new Intent(this, ChoisirModifierActivity.class);
        startActivity(voir);
    }

}
