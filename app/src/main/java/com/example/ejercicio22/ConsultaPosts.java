package com.example.ejercicio22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConsultaPosts extends AppCompatActivity {
    ListView ListDato;
    List<Datos> datosList;
    ArrayList<String> arrayDato;
    Button btnGuardar;
    TextView txtpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_posts);
        ListDato = (ListView) findViewById(R.id.listemple);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        txtpost = (TextView)findViewById(R.id.txtpost);
        datosList = new ArrayList<>();
        arrayDato = new ArrayList<String>();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarApi();
            }
        });
    }

    private void guardarApi() {
        RequestQueue cola = Volley.newRequestQueue(this);
        String endpointurl = "https://jsonplaceholder.typicode.com/posts/1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, endpointurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray DatoArray = jsonObject.getJSONArray("Datos");

                    for(int i=0; i < DatoArray.length(); i++){
                        JSONObject RowDato = DatoArray.getJSONObject(1);
                        Datos datos = new Datos(RowDato.getInt("userId"),
                                RowDato.getInt("id"),
                                RowDato.getString("title"),
                                RowDato.getString("body")
                        );

                        datosList.add(datos);
                        arrayDato.add(datos.getId()+" "+datos.getTitle()+" "+datos.getBody());
                    }
                    ArrayAdapter adp = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arrayDato);
                    ListDato.setAdapter(adp);

                }catch (JSONException ex){

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        cola.add(stringRequest);

    }
}