package com.example.seguridad22;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.seguridad22.Utilities.Methods;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PublicaR extends AppCompatActivity {

    RecyclerView recyclerMenu;
    Button btn_registerMe;
    EditText nameMenu;
    EditText priceMenu;

    ImageView imgMenu;
    Button btn_imgMenu;
   // ArrayList<ItemMenu> listData;
    int CODE_PERMISSIONS=101;


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
    // fin spiner




    //inicio camara
    //DESDE AQUI VA LA PARTE DE LA FOTO
    final int COD_GALERIA=10;
    final int COD_CAMERA=20;
    String path;
    private void cargarImagen() {

        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(PublicaR.this);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    tomarFotografia();
                }else{
                    if (opciones[i].equals("Cargar Imagen")){
                        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),COD_GALERIA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }
    private void tomarFotografia() {

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Methods.FileAndPath fileAndPath= Methods.createFile(path);
        File file = fileAndPath.getFile();
        path = fileAndPath.getPath();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri fileuri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
            //permiso para mi aplicacion
            //Uri fileuri = FileProvider.getUriForFile(getApplicationContext(), "com.myfoodapp.seminarioproyect.myfoodapp", file);
            camera.putExtra(MediaStore.EXTRA_OUTPUT, fileuri);
        } else {
            camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        startActivityForResult(camera, COD_CAMERA);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case COD_GALERIA:
                    Uri imgPath=data.getData();
                    imgMenu.setImageURI(imgPath);
                    path = Methods.getRealPathFromURI(this,imgPath);
                    Toast.makeText(PublicaR.this, path, Toast.LENGTH_SHORT).show();
                    break;
                case COD_CAMERA:
                    loadImageCamera();
            }
        }
    }
    private boolean reviewPermissions() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }

        if(this.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        requestPermissions(new String [] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODE_PERMISSIONS);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (CODE_PERMISSIONS == requestCode) {
            if (permissions.length == 3) {
                btn_imgMenu.setVisibility(View.VISIBLE);

            }

        }
    }


    private void loadImageCamera() {
        Bitmap img = BitmapFactory.decodeFile(path);
        if(img != null) {
            imgMenu.setImageBitmap(img);

        }
    }
    //fin camara

}
