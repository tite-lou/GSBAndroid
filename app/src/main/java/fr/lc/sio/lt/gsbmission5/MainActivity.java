package fr.lc.sio.lt.gsbmission5;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;
import java.lang.String;
import 	java.util.Arrays;
import java.util.Arrays;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.app.Activity;
import fr.lc.sio.lt.gsbmission5.Metier.Modele;
import fr.lc.sio.lt.gsbmission5.Metier.Practicien;
import fr.lc.sio.lt.gsbmission5.Metier.Visiteur;



public class MainActivity extends AppCompatActivity {
    private EditText login, mdp;
    Visiteur visiteur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (EditText) findViewById(R.id.edt_login);
        mdp = (EditText) findViewById(R.id.edt_mdp);
        SharedPreferences ps = this.getSharedPreferences("default",0);
        SharedPreferences.Editor edit = ps.edit();
        edit.putString("ip","http://10.20.128.127/gsbAndroid/web/app.php");
        edit.commit();
    }
    public void seConnecter(View vue){
        String texteLogin = login.getText().toString();
        String texteMdp = mdp.getText().toString();

        Log.d("login",texteLogin+" "+texteMdp);
        SharedPreferences ps = this.getSharedPreferences("default",0);

        String ip = ps.getString("ip","");
       String url =""+ip+"/connexion/"+texteLogin+"/"+texteMdp;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    Log.d("vist", response.getString("visNom"));
                    if(response.toString().equals("false")){
                        Toast.makeText(MainActivity.this, "Echec de connexion ",
                                Toast.LENGTH_LONG).show();
                    }else{


                    String matricule = response.getString("visMatricule");
                    String nom = response.getString("visNom");
                    String prenom = response.getString("visPrenom");
                    Toast.makeText(MainActivity.this, "Connexion réussie : "
                            + matricule, Toast.LENGTH_LONG).show();
                    Log.i("APP_RV","Visiteur : "+ response.toString());
                    connecterVisiteur(matricule,nom,prenom);
                    }

                } catch (Exception e) {

                    Toast.makeText(MainActivity.this, "Echec de connexion ",
                            Toast.LENGTH_LONG).show();
                    Log.e("APP-RV", "Erreur : " + e.getMessage());
                }
            }
        };

        Response.ErrorListener responseErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("APP_RV", "Erreur JSON : " + error.getMessage());
                Log.d("vist", "error"+error.getMessage());
            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, responseErrorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);




    }
    public void connecterVisiteur(String matricule, String nom ,String prenom) {

        visiteur = new Visiteur();
        visiteur.setVisMatricule(matricule);
        visiteur.setVisNom(nom);
        visiteur.setVisPrenom(prenom);
        SharedPreferences prefs = this.getSharedPreferences("default",0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("id",matricule);
        edit.putString("nom", visiteur.getVisNom());
        edit.putString("prenom", visiteur.getVisPrenom());
        edit.commit();

        Intent connexion = new Intent(this, GsbMenuActivity.class);
        startActivity(connexion);


    }
    public void reinitialiser(View view) {

        // Action sur clic du bouton Annuler
        login.setText("");
        mdp.setText("");

    }
}
