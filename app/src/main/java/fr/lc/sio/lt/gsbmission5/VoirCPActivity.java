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
    // au clique de  validation via le numéro de rapport récupérer et enregistrer en parametre de session permet d'afficher les détails concernant
    // le raport selectionné en passant à l'activité "voir le CP"
    public void voirLeCP(View vue){
        SharedPreferences prefs = this.getSharedPreferences("default",0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt("numRapport",numRapport);
        edit.commit();
        Intent connexion = new Intent(this, LeCPActivity.class);
        startActivity(connexion);
    }
    // au clique du bouton retour permet de retourner au menu
    public void retourMenu(View vue){
        Intent connexion = new Intent(this, GsbMenuActivity.class);
        startActivity(connexion);

    }
    // Cette premiére méthode permet de remplir la premiére liste déroulante en affichant uniquement les mois et année où un compte rendu a été saisit.
    // Elle fait pour cela appel à une méthode du service web qui lui retourne un tableau JSOn concernant la date d'enregistrement du rapport.
    // elle implemente un tableau de string où le mois et l'année sons concaténé tout en vérifiant qu'il n'y ait pas la presence de même date dans le tableau
    // car un rapport peut  être enregistré le même moi de la même année.
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
                        // récupération de la date envoyé par le tableau JSON
                        String resp = practicien.getString("date");
                        resp = resp.substring(0, 7);
                        Log.d("RESP", "" + resp);
                        possible = false;

                        // vérification de la nous exitence d'un élément identique à la nouveal valeur de la variable "YYYY-mm", si ce n'est pas le cas
                        // on ajoute la nouvel valeur au tableau
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

    //Cette méthode permet à la selection d'un mois et d'une année dans la liste déroulante du dessus de récuprer les compte rendu dont la date correspond
    // à la selection. Via une méthode du web service ou récupere tout les rapport du visiteur et on trie en vérifiant la date (mois année) qui sont
    // passer en parametre de classe et permette de vérifier la conformité du rapport. Qui ensuite implementerons la deuxiéme liste déroulante
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
               // definition du paramte du mois et de l' année de réference afin de comparer le mois et l'anneé de chaque rapport récupéré
                String annee1=dtt.substring(0,4);

                String mois2= dtt.substring(5,7);
                try {
                    for( int i = 0 ; i < response.length() ; i++) {

                        JSONObject practicien = response.getJSONObject(i);
                        // dans uen varible ou stocke les information qui nous interessen afin d'afficher le rapport dans la liste déroulante
                        Log.i("Test 2",practicien.getJSONObject("praticien").getString("praTypeCode"));
                        String resp = practicien.getJSONObject("praticien").getString("praNom")+"-"+practicien.getJSONObject("praticien").getString("praPrenom")+
                               "-"+practicien.getString("consulte")+"-"+practicien.getString("dateRapport");
                        String verifDate = practicien.getString("dateRapport");
                        String idRapport = practicien.getString("rapNum");
                        // implémentation du mois de la date du rapport dans une variable
                        Log.d("verifDate",""+verifDate);
                       String annee2= verifDate.substring(6,10);
                        // implémentation de l'année de la date du rapport dans une variable
                       Log.d("annee2", ""+annee2);
                        String mois1 = verifDate.substring(3,5);
                        //vérification si le mois correspond à celui de réference ansi que pour l'année
                        if(annee2.equals(annee1)&& mois2.equals(mois1)){
                            // si la condition est vérifié on ajoute les informations nécessaire récuperées via une variable string qu'on ajoute dans un liste
                            // ainsi que l'ajout de l'id du rapport dans une deuxiéme liste en paralléle
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
    // Cette méthode permet d'implémenter  la deuxiéme liste déroulante en affichant les infomation d'une liste texte et de récupérer en paralléle
    // et stocker dans un variable l'id de l'objet sélectionner qui sera par la suite enregistrer en paramétre de session et récupéré pour
    // l'activité suivante
    public void RecupeLaListCp(ArrayList list,ArrayList listId){


        ListCp = new ArrayList<String>();
        ListCp= list;
        ListIdRapport=listId;


        Log.i("TEST-LIST",""+ListCp.get(0));
        // implémentation des données de la deuxiéme liste déroulante
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
                        // à chaque objet sélectionnée dans la liste on recupére l'id via la liste implémenter en parraléle et enregistre dans
                        // une variable de classe la valeur
                        numRapport = Integer.valueOf(ListIdRapport.get(position));
                        lePractSelect.setText(practicienSelect+" RApNUM "+String.valueOf(numRapport));

                    }
                }
        );
    }
    // Cette méthode est appeler à la fin de la méthode de recupération des mois et année afin d'implementer via la liste récuperer
    // la liste déroulante qui permet de sélectionner un mois et une année pour lesquels des rapports on été eenregistré.
    public void RecupListMoisAnneeCP(ArrayList List){

        ListMA = List;

        // implémentation de la liste déroulante
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
                // à chaque objet selectionner on enregistre la concaténation du mois de de l'année puis on fais appel à la méthode
                // pour implementer le deuxiéme liste déroulante
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
