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
    TextView leText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_le_cp);
        SharedPreferences prefs = this.getSharedPreferences("default",0);
        leText = (TextView) findViewById(R.id.textView2);
       int numRapport = prefs.getInt("numRapport",0)+1;
       leText.setText(" "+ String.valueOf(numRapport));
       this.AfficherLeCp(numRapport);
    }
    public void retourChoix(View vue){
        Intent connexion = new Intent(this, VoirCPActivity.class);
        startActivity(connexion);

    }
    public void retourMenu(View vue){
        Intent connexion = new Intent(this, GsbMenuActivity.class);
        startActivity(connexion);

    }
    public void AfficherLeCp (int numRapport){



        SharedPreferences ps = this.getSharedPreferences("default",0);

        String ip = ps.getString("ip","");
        String url =""+ip+"/recupRapportParId/"+ps.getString("id","")+"/"+numRapport;
        Log.d("IP-voirActivity", url);
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {




                        String resp = response.getJSONObject("praticien").getString("praNom")+"-"+response.getJSONObject("praticien").getString("praPrenom")+
                                "-"+response.getString("consulte")+"-"+response.getString("dateRapport");
                        Log.d("TEST-POURLECP",resp);
                        leText.setText(resp);





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
