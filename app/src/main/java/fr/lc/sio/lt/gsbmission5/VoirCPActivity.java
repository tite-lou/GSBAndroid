package fr.lc.sio.lt.gsbmission5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import java.util.*;
import java.util.List;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;
import java.lang.String;
import android.app.Activity;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONObject;

import fr.lc.sio.lt.gsbmission5.Metier.Practicien;
import fr.lc.sio.lt.gsbmission5.Metier.Rapport;
import fr.lc.sio.lt.gsbmission5.Metier.Visiteur;
import  fr.lc.sio.lt.gsbmission5.Metier.InputStreamOperations;

public class VoirCPActivity extends AppCompatActivity {

    ListView lesPracticiens;
   String ListCp[] ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_cp);
        lesPracticiens = (ListView) findViewById(R.id.id_lstPracticiens);
        this.AfficherListCp();
        Log.i("VOIRLISTE",L)
      /*  ArrayAdapter<String> adapter = new ArrayAdapter<String>(
             this,
               android.R.layout.simple_list_item_1,
                ListCp
        );*/

      // lesPracticiens.setAdapter(adapter);
    }
    public void voirLeCP(View vue){
        Intent connexion = new Intent(this, LeCPActivity.class);
        startActivity(connexion);
    }
    public void retourMenu(View vue){
        Intent connexion = new Intent(this, GsbMenuActivity.class);
        startActivity(connexion);

    }

    public void AfficherListCp (){



        SharedPreferences ps = this.getSharedPreferences("default",0);

        String ip = ps.getString("ip","");
        String url =""+ip+"/recupListeRapport/"+ps.getString("id","");
        Log.d("IP-voirActivity", url);
        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                ListCp = new String[response.length()];
                try {
                    for( int i = 0 ; i < response.length() ; i++) {

                        JSONObject practicien = response.getJSONObject(i);
                        Log.i("Test 2",practicien.getJSONObject("praticien").getString("praTypeCode"));
                        String resp = practicien.getJSONObject("praticien").getString("praNom")+"-"+practicien.getJSONObject("praticien").getString("praPrenom")+
                                "-"+practicien.getString("consulte")+"-"+practicien.getString("dateRapport");
                        ListCp[i]= resp;
                    }
                    Log.i("TEST-LIST",ListCp[1]);


                } catch (Exception e) {

                    Toast.makeText(VoirCPActivity.this, "Echec de connexion ",
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

        JsonArrayRequest jsonArraysRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                responseListener, responseErrorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArraysRequest);







    }
    public void RecupererListeCP(View vue){



        SharedPreferences ps = this.getSharedPreferences("default",0);

        String ip = ps.getString("ip","");
        String url =""+ip+"/recupListeRapport/"+ps.getString("id","");
        Log.d("IP", url);
        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {
                        for( int i = 0 ; i < response.length() ; i++) {

                         }

                } catch (Exception e) {

                    Toast.makeText(VoirCPActivity.this, "Echec de connexion ",
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

        JsonArrayRequest jsonArraysRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                responseListener, responseErrorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArraysRequest);




    }


  /* public List<Rapport> getLesRapport(Practicien numPra, String id, Date dateVisite, Date dateRapportEnv,String commentaire){
        SharedPreferences sp = this.getSharedPreferences("default",0);
        String idVis = sp.getString("id","");
        String nom = sp.getString("nom","");
        String prenom = sp.getString("prenom","");
        Visiteur leVisiteur = new Visiteur();
       leVisiteur = new Visiteur();
       leVisiteur.setVisMatricule(idVis);
       leVisiteur.setVisNom(nom);
       leVisiteur.setVisPrenom(prenom);
        List<Rapport> lesRapport = new ArrayList<Rapport>();
       lesRapport.add(new Rapport(numPra,leVisiteur,id,commentaire,dateVisite,dateRapportEnv));
       // lesRapport = chargerLesRapport();
       return lesRapport;
   }*/
}
