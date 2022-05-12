package com.example.listviewwithapirestdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listviewwithapirestdata.Adapter.AdaptadorPaises;
import com.example.listviewwithapirestdata.Model.Paises;
import com.example.listviewwithapirestdata.WebService.Asynchtask;
import com.example.listviewwithapirestdata.WebService.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity  implements Asynchtask
{

    ListView lstOpciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstOpciones = (ListView)findViewById(R.id.lstListaUsuario);

        View header = getLayoutInflater().inflate(R.layout.ly_header, null);
        lstOpciones.addHeaderView(header);

        Map<String, String> datos = new HashMap<String, String>();
       WebService ws= new WebService("http://www.geognos.com/api/en/countries/info/all.json",
                datos, MainActivity.this, MainActivity.this);
       ws.execute("GET");

          /*  lstOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Paises paises =(Paises) parent.getAdapter().getItem(position);
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("Pais",paises.getNombrePais().toString());
                    intent.putExtra("url_imagen",paises.getUrl_Pais().toString());
                    intent.putExtra("iso2",paises.getCapital().toString());
                    intent.putExtra("coordenadas",paises.getCoordenadasPais());
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, paises.getCapital().toString(), Toast.LENGTH_SHORT).show();
                }
            });*/


    }

    @Override
    public void processFinish(String result) throws JSONException {
        ArrayList<Paises> lstUsuarios = new ArrayList<Paises> ();

        try {

            JSONObject JSONlista =  new JSONObject(result);


            lstUsuarios = Paises.JsonObjectsBuild(JSONlista);

            AdaptadorPaises adapatorUsuario = new AdaptadorPaises(this, lstUsuarios);

            lstOpciones.setAdapter(adapatorUsuario);


        }catch (JSONException e)
        {
            Toast.makeText(this.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
        }


    }


}