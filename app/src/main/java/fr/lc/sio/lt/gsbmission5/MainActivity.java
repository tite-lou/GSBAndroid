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
        edit.putString("ip","http://10.20.65.171/gsbAndroid/web/app.php");
        edit.commit();
    }
    // Cette méthode permet de récupérer le mot de passe et le login saisit dans le formulaire et envoyer en objet Json les parametrs et retourne
    // un objet visiteur ou vide si ses identifiant n'éxiste pas dans la base de données.
    // Une fois les paramétre du visiteur retourner par le Web Service sous format JSON Object, la méthode fait appel à la méthode ConnecterVisiteur
    // et lui envoie les parametres : id, nom , prénom.
    // La fonction traite les eventuelle erreur de retour ou de la non-existence de l'objet à l'aide de message texte affichant l'erreur.
    public void seConnecter(View vue){
        //récuperation des valeurs saisie pr l'utilisateur
        String texteLogin = login.getText().toString();
        String texteMdp = mdp.getText().toString();

        Log.d("login",texteLogin+" "+texteMdp);
        SharedPreferences ps = this.getSharedPreferences("default",0);

        // définition de l'url de l'action voulut du web service et appel de e dernier
        String ip = ps.getString("ip","");
       String url =""+ip+"/connexion/"+texteLogin+"/"+texteMdp;

        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    Log.d("vist", response.getString("visPrenom"));
                    // verification de l'existance du visiteur
                    if(response.toString().equals("false")){
                        Toast.makeText(MainActivity.this, "Echec de connexion ",
                                Toast.LENGTH_LONG).show();
                    }else{

                    // recuperation de ces information retourner par le web service sous format d'un objet JSON
                    String matricule = response.getString("visMatricule");
                    String nom = response.getString("visNom");
                    String prenom = response.getString("visPrenom");
                    Toast.makeText(MainActivity.this, "Connexion réussie : "
                            + matricule, Toast.LENGTH_LONG).show();
                    Log.i("APP_RV","Visiteur : "+ response.toString());
                    //Appel de la méthode d'instanciation d'un visiteur
                    connecterVisiteur(matricule,nom,prenom);
                    }

                    //traitement d'execption éventuelles
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

        //appel au service web et de la création de l'objet JSON qui nou sera retourner
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, responseErrorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);




    }
    // Cette méthode est appelée par seConnecter et permet d'instancier un nouvel objet de la classe visiteur avec les parametres recupérés par l'objet JSON
    // envoyé par le service web. Cette méthode garde également en parametre de session les différentes informations du visiteur connecté afin de faciliter
    // le développement par la suite.
    // Enfin la methode nous permet de passer à l'Activité suivante qui est la page d'accueil (le Menu)
    public void connecterVisiteur(String matricule, String nom ,String prenom) {

        //instenciation du visiteur
        visiteur = new Visiteur();
        visiteur.setVisMatricule(matricule);
        visiteur.setVisNom(nom);
        visiteur.setVisPrenom(prenom);
        //sauvegarde en parametre de session accessible à tout les activité
        SharedPreferences prefs = this.getSharedPreferences("default",0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("id",matricule);
        edit.putString("nom", visiteur.getVisNom());
        Log.d("PRENOM ENREG",prenom);
        edit.putString("prenomVist", prenom);
        edit.commit();

        //passage à l'activité suivante
        Intent connexion = new Intent(this, GsbMenuActivity.class);
        startActivity(connexion);


    }
    // Cette méthode permet tout simplement de rénitialiser le formulaire de connexion si jamais l'utilisateur à fait une erreur et qu'il souhaite tout recommencer.
    public void reinitialiser(View view) {

        // Action sur clic du bouton Annuler
        login.setText("");
        mdp.setText("");
        SharedPreferences pref = this.getSharedPreferences("default",0);
        pref.edit().clear().commit();

    }
}
