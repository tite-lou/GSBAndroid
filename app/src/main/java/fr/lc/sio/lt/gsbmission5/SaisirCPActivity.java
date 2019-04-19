package fr.lc.sio.lt.gsbmission5;

import android.support.v7.app.AppCompatActivity;
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
import android.widget.AdapterView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.preference.PreferenceManager;
import java.lang.String;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.lc.sio.lt.gsbmission5.Metier.Rapport;

public class SaisirCPActivity extends AppCompatActivity {
    Spinner listPraticien;
    String listP[] ;
    int ListId[];
    DatePicker date;
    EditText bilan;
    TextView leText;
    int numRapport;
    String rapportToString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisir_cp);
        listPraticien=(Spinner) findViewById(R.id.spinner3);
        date =(DatePicker) findViewById(R.id.datepicker);
        bilan =(EditText) findViewById(R.id.bilan);
        leText=(TextView) findViewById(R.id.textView5);

        this.RemplirSpinner();

    }

    public void envoiRapport(View vue) {

            /*final String  dateVisite = String.valueOf(date.getYear()+"-"+date.getMonth()+"-"+date.getDayOfMonth());
            Log.d("TESTCALENDER",dateVisite);
            final String bb = bilan.getText().toString();
            final int praNum = ListId[numRapport];
            */
        // Insyanciation directe sans passer par le mobile, donc à remplacer quand on récupère les
        // données depuis la vue android


        SharedPreferences session = this.getSharedPreferences("default",0);

        String ip = session.getString("ip","");
        final String idVisi = session.getString("id","");



        String url = ip +"/ajouterRapport";
       // String url = ip +"/lesCR";
        RequestQueue queue = Volley.newRequestQueue(this);


        final String commentaire = bilan.getText().toString();
        final int praNum = ListId[numRapport];
        SharedPreferences ps = this.getSharedPreferences("default",0);

        StringRequest requete = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("REPONSE", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", error.networkResponse.statusCode + error.getMessage(), error);
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams() {

                GsonBuilder fabrique = new GsonBuilder();
                final Gson gson = fabrique.create();

                Rapport rapport = new Rapport(praNum, idVisi, commentaire, new GregorianCalendar(date.getYear(), date.getMonth(), date.getDayOfMonth()));

                rapportToString = gson.toJson(rapport);

                Map<String, String> parametres = new HashMap<String, String>();


                Log.d("Debug parametre", rapportToString);
                System.out.println("Rapport"+rapportToString);
                parametres.put("rapport",rapportToString);

                return parametres;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");

                return headers;
            }
        };
        queue.add(requete);



        Intent connexion = new Intent(this, GsbMenuActivity.class);
        startActivity(connexion);


    }


    public void RemplirSpinner(){

        SharedPreferences ps = this.getSharedPreferences("default",0);

        String ip = ps.getString("ip","");
        String url =""+ip+"/recupListePraticien/"+ps.getString("id","");
        Log.d("IP-voirActivity", url);
        Response.Listener<JSONArray> responseListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                String  List[] = new String[response.length()];
                int lid[]= new int[response.length()];
                try {
                    for( int i = 0 ; i < response.length() ; i++) {
                        JSONObject practicien = response.getJSONObject(i);
                        Log.i("PRACTICIEN NOMPRENOM",practicien.getString("praNom")+" "+practicien.getString("praPrenom"));
                        String resp = practicien.getString("praNom")+"-"+practicien.getString("praPrenom");
                       List[i]= resp;

                       lid[i]= Integer.parseInt(practicien.getString("praNum"));

                    }


                  RecupeLaListCp(List,lid);

                } catch (Exception e) {

                    Toast.makeText(SaisirCPActivity.this, "Echec de connexion ",
                            Toast.LENGTH_LONG).show();
                    Log.e("APP-RV", "Erreur 6: " + e.getMessage(),e);
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

    public void RecupeLaListCp(String[] list, int[] lid) {
        listP = new String[list.length];
        ListId = lid;
        listP =list;
        Log.i("TEST-LIST", listP[1]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listP
        );

        listPraticien.setAdapter(adapter);

        listPraticien.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                Log.d("ITEM:", item.toString());
                numRapport = position;
                leText.setText(String.valueOf(numRapport));


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
      /*  listPraticien.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View vue, int position, long id){
                        String practicienSelect = listP[position];
                        numRapport = position;
                        leText.setText(practicienSelect+" "+String.valueOf(numRapport));

                    }
                }
        );*/


    }


}
