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
        // recupération de certaines variables à propos du visiteur connecté
        SharedPreferences prefs = this.getSharedPreferences("default",0);


        String id = prefs.getString("id","");
        String prenom = prefs.getString("prenomVist","");
        Log.d("PRENOM",prenom);
        txtBienvenu = (TextView) findViewById(R.id.id_bienvenue);
        txtBienvenu.setText("Bonjour : "+prenom);
        //Log.d("jh",  res);
    }
    // Methode qui permet au visiteur de se déconnecter et de retourner à la page de début, pour se connecter
    public void seDeconnecter(View vue) {
      //au clique du bouton "se déconnecter"
        SharedPreferences sp = this.getSharedPreferences("default",0);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.apply();
        Intent retourAccueil = new Intent(this, MainActivity.class);
        startActivity(retourAccueil);
    }
    // Méthode pour se rendre à l'activité de saisit d'un compte rendu
    public void saisirCP(View vue){
        // au clique du bouton "saisir  un CP ""
        Intent saisie = new Intent(this, SaisirCPActivity.class);
        startActivity(saisie);
    }
    //Méthode pour se rendre à l'activité permettant de voir les different compte-rendu saisit par le visiteur
    public void voirCP(View vue){

        // au clique su bouton "Voir CP"
        Intent voir = new Intent(this, VoirCPActivity.class);
       startActivity(voir);

    }


}
