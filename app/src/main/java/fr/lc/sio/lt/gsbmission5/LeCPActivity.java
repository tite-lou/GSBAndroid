package fr.lc.sio.lt.gsbmission5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class LeCPActivity extends AppCompatActivity {
    TextView leText2,leText3,leText4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_le_cp);
        SharedPreferences prefs = this.getSharedPreferences("default",0);
        leText2 = (TextView) findViewById(R.id.textView2);
        leText3 = (TextView) findViewById(R.id.textView3);
        leText4 = (TextView) findViewById(R.id.textView4);
       int numRapport = prefs.getInt("numRapport",0);
       leText2.setText(" "+ String.valueOf(numRapport));
       this.AfficherLeCp(numRapport);
       // permet d'afficher les information du visiteur qui a réalisé le compte rendu (ses information on été récupérer ultérieurement et passées en parametres de sesions)
       String leVisiteur = "Realiser par :"+prefs.getString("nom","")+" "+prefs.getString("prenom","");
       leText4.setText(leVisiteur);
    }
    // retour à l'activité de selection d'un compte rendu
    public void retourChoix(View vue){
        Intent connexion = new Intent(this, VoirCPActivity.class);
        startActivity(connexion);

    }
    //retour au menu
    public void retourMenu(View vue){
        Intent connexion = new Intent(this, GsbMenuActivity.class);
        startActivity(connexion);

    }
    // méthode qui permet grâce à l'id du rapport/ compte rendu sélectionner dans l'activité précédente et sauvegarder en parametre de session,
    // de faire appel à une méthode du service web qui nous retourner pour ce rapport sélectionner toute les information le concernant
    // ainsi que les informations du praticien concerné par ce compte rendu
    public void AfficherLeCp (int numRapport){



        SharedPreferences ps = this.getSharedPreferences("default",0);

        String ip = ps.getString("ip","");
        String url =""+ip+"/recupRapportParId/"+numRapport+"/"+ps.getString("id","");
        Log.d("IP-LeCPActivity", url);
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("LECPACTIVITY-TRY","on est pas encore rentrer");
                String newLigne =System.getProperty("line.separator");
                try {

                        Log.d("LECPACTIVITY-TRY","on est renter");

                        // recupération dans une variable string les information du praticien renvoyer par l'objet jSOn ( sous objet du réel objet rapport)
                        String lePracticien = " Praticien Concerné: "+response.getJSONObject("praticien").getString("praNom")+response.getJSONObject("praticien").getString("praPrenom")
                                +newLigne+"Adresse :"+response.getJSONObject("praticien").getString("praAdresse")+" "+response.getJSONObject("praticien").getString("praCp")+ " "+
                                response.getJSONObject("praticien").getString("praVille")+newLigne+response.getJSONObject("praticien").getString("praTypeCode");


                        // récupération dans une variable les information concernant le rapport sélectonné
                        String infoCP="le Rapport numero : "+response.getString("rapNum")+newLigne+" fait le : "+response.getString("dateRapport")+newLigne+" Pour la visite du :"+newLigne+response.getString("dateVisite")+newLigne+"Consulter :"+response.getString("consulte")+
                                newLigne+" Observation : "+response.getString("bilan");
                       // Log.d("TEST-POURLECP",resp);
                        Log.d("LEPRACTICIEN",lePracticien);
                        Log.d("LECPINFO",infoCP);
                        // implementation des varaible dans des text view qui seront afficher sur l'activité et visible par l'utilisateur
                        leText2.setText(lePracticien);
                       // leText3.setText(leVisiteur);
                        leText3.setText(infoCP);


                } catch (Exception e) {

                    Toast.makeText(LeCPActivity.this, "Echec de connexion ",
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

        JsonObjectRequest jsonArraysRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                responseListener, responseErrorListener);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArraysRequest);







    }
}
