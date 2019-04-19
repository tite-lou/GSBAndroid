package fr.lc.sio.lt.gsbmission5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;
import java.lang.String;
import android.app.Activity;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import android.widget.AdapterView.OnItemSelectedListener;
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
    TextView lePractSelect;
    ArrayList<String> ListCp ;
   ArrayList<String> ListMA;
   String dtt;
   ArrayList<String> ListIdRapport;
    int numRapport;
    Spinner lespinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_cp);
        lesPracticiens = (ListView) findViewById(R.id.id_lstCP);
        lePractSelect=(TextView) findViewById(R.id.id_afficherCpSelect);
        lespinner=(Spinner) findViewById(R.id.spinner);
        //this.AfficherListCp();
        this.AfficherLesMoisAnneeCp();

      /*  ArrayAdapter<String> adapter = new ArrayAdapter<String>(
             this,
               android.R.layout.simple_list_item_1,
                ListCp
        );*/

       //lesPracticiens.setAdapter(adapter);
    }
    public void voirLeCP(View vue){
        SharedPreferences prefs = this.getSharedPreferences("default",0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt("numRapport",numRapport);
        edit.commit();
        Intent connexion = new Intent(this, LeCPActivity.class);
        startActivity(connexion);
    }
    public void retourMenu(View vue){
        Intent connexion = new Intent(this, GsbMenuActivity.class);
        startActivity(connexion);

    }
    public void AfficherLesMoisAnneeCp(){
        SharedPreferences ps = this.getSharedPreferences("default",0);

        final String ip = ps.getString("ip","");
        // String url =""+ip+"/recupListeRapport/"+ps.getString("id","");
        String url =""+ip+"/recupDateDuRapport/"+ps.getString("id","");
        Log.d("IP-voirActivity", url);
        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                ArrayList<String> Listes = new ArrayList<String>();
                Log.d("LENGHT :", String.valueOf(response.length()));
                try {
                    boolean possible;
                    for( int i = 0 ; i < response.length() ; i++) {

                        JSONObject practicien = response.getJSONObject(i);
                        Log.i("Test 2", practicien.getString("date"));
                        // String resp = practicien.getJSONObject("praticien").getString("praNom")+"-"+practicien.getJSONObject("praticien").getString("praPrenom")+
                        //         "-"+practicien.getString("consulte")+"-"+practicien.getString("dateRapport");
                        String resp = practicien.getString("date");
                        resp = resp.substring(0, 7);
                        Log.d("RESP", "" + resp);
                        possible = false;


                        for(String element: Listes){
                            if(element.equals(resp)){
                                possible = true;
                            }
                        }
                        if(possible == false){
                             Listes.add(resp);
                        }

                    }

                   // RecupeLaListCp(List);
                    RecupListMoisAnneeCP(Listes);
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

    public void AfficherListCp (){


        Log.d("DATE :", " "+dtt);

        SharedPreferences ps = this.getSharedPreferences("default",0);

        String ip = ps.getString("ip","");
        String url =""+ip+"/recupListeRapport/"+ps.getString("id","");
       // String url =""+ip+"/lesCR/"+ps.getString("id","");
        Log.d("IP-voirActivity", url);
        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

               ArrayList<String> List = new ArrayList<String>();
               ArrayList<String> ListIdRap = new ArrayList<String>();
               Log.d("LENGHTDELISTVIEW :", String.valueOf(response.length()));
                String annee1=dtt.substring(0,4);

                String mois2= dtt.substring(5,7);
                try {
                    for( int i = 0 ; i < response.length() ; i++) {

                        JSONObject practicien = response.getJSONObject(i);
                        Log.i("Test 2",practicien.getJSONObject("praticien").getString("praTypeCode"));
                        String resp = practicien.getJSONObject("praticien").getString("praNom")+"-"+practicien.getJSONObject("praticien").getString("praPrenom")+
                               "-"+practicien.getString("consulte")+"-"+practicien.getString("dateRapport");
                        String verifDate = practicien.getString("dateRapport");
                        String idRapport = practicien.getString("rapNum");
                        Log.d("verifDate",""+verifDate);
                       String annee2= verifDate.substring(6,10);
                       Log.d("annee2", ""+annee2);
                        String mois1 = verifDate.substring(3,5);
                        if(annee2.equals(annee1)&& mois2.equals(mois1)){
                            List.add(resp);
                            ListIdRap.add(idRapport);

                          Log.i("debuf if ", ""+resp+" id "+idRapport);
                       }

                    }


                    RecupeLaListCp(List,ListIdRap);

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
    public void RecupeLaListCp(ArrayList list,ArrayList listId){


        ListCp = new ArrayList<String>();
        ListCp= list;
        ListIdRapport=listId;


        Log.i("TEST-LIST",""+ListCp.get(0));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                ListCp
        );

        lesPracticiens.setAdapter(adapter);
        lesPracticiens.setOnItemClickListener(
                new OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View vue, int position, long id){
                        String practicienSelect = ListCp.get(position);
                        numRapport = Integer.valueOf(ListIdRapport.get(position));
                        lePractSelect.setText(practicienSelect+" RApNUM "+String.valueOf(numRapport));

                    }
                }
        );
    }
    public void RecupListMoisAnneeCP(ArrayList List){

        ListMA = List;

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                ListMA
        );

        lespinner.setAdapter(adapter2);
        lespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                Log.d("ITEM:", item.toString());
                numRapport = position;
                lePractSelect.setText(String.valueOf(numRapport)+item.toString());
                dtt = item.toString();
                AfficherListCp();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
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
