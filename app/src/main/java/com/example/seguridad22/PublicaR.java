package com.example.seguridad22;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PublicaR extends AppCompatActivity {


    ArrayList<String> spiner_operacion;
    ArrayList<String> type_spinerP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publica_r);
        setSpinnerPublicar();
        spiner_operacion();

    }

    //metodo conexion a Bd


    private void sendData_publ() {

        Spinner spiner_publ = findViewById(R.id.spinner_publi_type);
        Spinner operation= findViewById(R.id.spin_operacion);
        EditText pricetxt = findViewById(R.id.txt_precioPub);
        EditText  addressss= findViewById(R.id.txt_direc_pub);
        EditText number_adress = findViewById(R.id.txt_num_publ);
        EditText zona_dir = findViewById(R.id.txt_zona_pub);
        EditText superficie = findViewById(R.id.txt_superf_m2);
        EditText num_habitacion = findViewById(R.id.txt_num_habi);
        EditText num_banios = findViewById(R.id.txt_num_ban);
        EditText num_piso = findViewById(R.id.txt_num_planta);
        EditText titulo_publi = findViewById(R.id.txt_titulo_publi);
        EditText descripcion_publi = findViewById(R.id.txt_desc_publ);






        //llenado de tablas

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.add("type", type_spinerP.get(spiner_publ.getSelectedItemPosition()));
        params.add("operation", spiner_operacion.get(operation.getSelectedItemPosition()));
        params.add("price", pricetxt.getText().toString());
        params.add("address", addressss.getText().toString());
        params.add("number", number_adress.getText().toString());
        params.add("zone", zona_dir.getText().toString());
        params.add("surface", superficie.getText().toString());
        params.add("num_rooms", num_habitacion.getText().toString());
        params.add("num_bathrooms", num_banios.getText().toString());
        params.add("num_plant", num_piso.getText().toString());

        params.add("title_publ", titulo_publi.getText().toString());
        params.add("description", descripcion_publi.getText().toString());



        //botoon de enviado, registro de datos a login_google

        client.post(Utils.REGISTER_PUBLICATION, params, new JsonHttpResponseHandler(){

            public void  onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse ) {

            }
            public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
                if (response.has("roles")) {
                    Toast.makeText(PublicaR.this, "Publicando... espere...!!!", Toast.LENGTH_LONG).show();
                    Intent menuinicio =new Intent(PublicaR.this, menuprincipal.class);
                    startActivity(menuinicio);

                }
            }

        });
    }
    public void enviar_publ(View view) {
        Toast.makeText(PublicaR.this, "publicando", Toast.LENGTH_LONG).show();
        sendData_publ();

    }



















   ///spinerssssssss
    private void setSpinnerPublicar() {
        type_spinerP = new ArrayList<>();
        type_spinerP.add("Vivienda");
        type_spinerP.add("Departamento");
        type_spinerP.add("Terreno");
        type_spinerP.add("Negocio");
        type_spinerP.add("Oficina");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,type_spinerP);
        Spinner spinner = findViewById(R.id.spinner_publi_type);
        spinner.setAdapter(adapter);

    }
    private void spiner_operacion(){
        spiner_operacion = new ArrayList<>();
        spiner_operacion.add("Venta");
        spiner_operacion.add("Alquiler");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,spiner_operacion);
        Spinner spinner = findViewById(R.id.spin_operacion);
        spinner.setAdapter(adapter);


    }
}
