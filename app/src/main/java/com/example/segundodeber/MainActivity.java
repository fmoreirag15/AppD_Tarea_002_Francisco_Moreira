package com.example.segundodeber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.segundodeber.modelo.adaptadorLista;
import com.example.segundodeber.modelo.modelos_revista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import  android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    TextView txtBuscar;
    TextView txtInfor;
    Button btnConsultar;

    List<modelos_revista> datos= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBuscar = findViewById(R.id.editTextNumber);
        btnConsultar=findViewById(R.id.button);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBuscar = findViewById(R.id.editTextNumber);
                obtenerDatosConVolley(txtBuscar.getText().toString());
            }
        });
        obtenerDatosConVolley("2");
    }
    private void obtenerDatosConVolley(String codigo)
    {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url="https://revistas.uteq.edu.ec/ws/issues.php?j_id="+codigo+"";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length()>0) {
                            datos.clear();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject Obj = response.getJSONObject(i);
                                    //datos.add(new  modelos_revista("sss","dsads","asda","asdasd","adasd","asdasd","asdad","https://revistas.uteq.edu.ec/public/journals/1/cover_issue_80_es_ES.png"));
                                    System.out.println("titulos"+Obj.get("title").toString());

                                    datos.add(new modelos_revista(  Obj.get("issue_id").toString(), Obj.get("volume").toString(),
                                                                    Obj.get("number").toString(),
                                                                    Obj.get("year").toString(),
                                                                    Obj.get("date_published").toString(),
                                                                    Obj.get("title").toString(),
                                                                    Obj.get("doi").toString(),
                                                                    Obj.get("cover").toString()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }



                        }else
                        {
                            mensajes("No se encontraron resultados");


                        }
                        ejecutar();


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        mensajes("Ocurrio un error");
                        error.printStackTrace();

                    }
                });

        queue.add(jsonObjectRequest);

    }
    public void  mensajes(String mensaje)
    {
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG ).show();
    }
    public void ejecutar()
    {
        if(datos.size()<1)
        {
            mensajes("No se encontraron resultados");
            txtInfor=findViewById(R.id.textView3);
            txtInfor.setText("Resultados de la consulta: "+datos.size());

        }else
        {
            mensajes("Lista de resultados");
            txtInfor=findViewById(R.id.textView3);
            txtInfor.setText("Resultados de la consulta: "+datos.size());
        }
        adaptadorLista lista= new adaptadorLista(datos, this);
        RecyclerView recyclerView=findViewById(R.id.listaRevista);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(lista);

    }
}