package com.example.seguridad22;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.seguridad22.fragments.inicioPrincipal;
import com.example.seguridad22.fragments.misPubli;
import com.example.seguridad22.fragments.publicarAR;

public class menuprincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,

        inicioPrincipal.OnFragmentInteractionListener,
        publicarAR.OnFragmentInteractionListener,
        misPubli.OnFragmentInteractionListener



   {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprincipal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        /*   boton flotante
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // menu de inicio por defecto
        Fragment fragment = new inicioPrincipal();
        getSupportFragmentManager().beginTransaction(). add(R.id.Contenedor,fragment).commit();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuprincipal, menu);
        return true;
    }

    @Override
    //menu desplegable derecha
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_cerrar) {
            cerrarapp();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Boolean FragmentoSeleccionado=false;


        if (id == R.id.nav_home) {

            fragment = new inicioPrincipal();
            FragmentoSeleccionado = true;

        } else if (id == R.id.nav_publicar) {


            Intent Publicar = new Intent(this, PublicaR.class);
            startActivity(Publicar);
            //fragment = new publicarAR();
            //FragmentoSeleccionado = true;

        } else if (id == R.id.nav_misPubl) {

            fragment = new misPubli();
            FragmentoSeleccionado = true;

        } else if (id == R.id.nav_tools) {


        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_send) {


        }
        else if (id == R.id.menu_cerrar) {

            cerrarapp();

        }
        if (FragmentoSeleccionado){

            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment). commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

       private void cerrarapp() {
          // finish();
           //System.exit(0);

           Intent intent = new Intent(Intent.ACTION_MAIN);
           intent.addCategory(Intent.CATEGORY_HOME);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(intent);

           //Intent loginn =new Intent(menuprincipal.this, login_google.class);
           //startActivity(loginn);
       }


       @Override
    public void onFragmentInteraction(Uri uri) {

    }



}
