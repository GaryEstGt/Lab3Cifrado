package com.example.garya.lab3cifrado;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RSALlaves extends AppCompatActivity {

    @BindView(R.id.txtP)
    EditText txtP;
    @BindView(R.id.txtQ)
    EditText txtQ;
    @BindView(R.id.btnGenerarLlaves)
    Button btnGenerarLlaves;
    String llavePublica="";
    String llavePrivada="";
    Uri uri;
    RSA rsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsallaves);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_CZigzag:
                finish();
                Intent intentcZig = new Intent(RSALlaves.this, MainActivity.class);
                startActivity(intentcZig);
                return true;
            case R.id.menu_DZigzag:
                finish();
                Intent intentDZig = new Intent(RSALlaves.this, DZigzag.class);
                startActivity(intentDZig);
                return true;
            case R.id.menu_CTransposicion:
                finish();
                Intent intentCT = new Intent(RSALlaves.this, CRuta.class);
                startActivity(intentCT);
                return true;
            case R.id.menu_DTransposicion:
                finish();
                Intent intentDT = new Intent(RSALlaves.this, DTransposicion.class);
                startActivity(intentDT);
                return true;
            case R.id.menu_CSDES:
                finish();
                Intent intentCS = new Intent(RSALlaves.this, CSDES.class);
                startActivity(intentCS);
                return true;
            case R.id.menu_DSDES:
                finish();
                Intent intentDS = new Intent(RSALlaves.this, DSDES.class);
                startActivity(intentDS);
                return true;
            case R.id.menu_LRSA:
                Toast.makeText(this.getApplicationContext(), "Ya esta en Generar Llaves RSA", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_CRSA:
                finish();
                Intent intentCR = new Intent(RSALlaves.this, CRSA.class);
                startActivity(intentCR);
                return true;
            case R.id.menu_DRSA:
                finish();
                Intent intentDR = new Intent(RSALlaves.this, DRSA.class);
                startActivity(intentDR);
                return true;
            case R.id.Salir:
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.btnGenerarLlaves)
    public void onViewClicked() {
        if(!txtP.getText().toString().isEmpty() && !txtQ.getText().toString().isEmpty()){
            if(RSA.esPrimo(new BigInteger(txtP.getText().toString())) && RSA.esPrimo(new BigInteger(txtQ.getText().toString()))){
                if(Long.parseLong(txtP.getText().toString()) >= 13){
                    if(Long.parseLong(txtQ.getText().toString()) >= 17){
                        rsa = new RSA(new BigInteger(txtP.getText().toString()),new BigInteger(txtQ.getText().toString()));
                        llavePublica=rsa.GenerarLlavePublica();
                        llavePrivada=rsa.GenerarLlavePrivada();
                        ElegirRutaLlavePublica();
                        ElegirRutaLlavePrivada();
                    }
                    else{
                        Toast.makeText(this.getApplicationContext(), "El número mínimo para q es 17", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(this.getApplicationContext(), "El número mínimo para p es 13", Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(this.getApplicationContext(), "p y q deben se números primos", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this.getApplicationContext(), "Debe ingresar p y q", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                uri = data.getData();
                if (Escritor.Escribir(uri, this.getApplication(), llavePrivada)) {
                    Toast.makeText(this.getApplicationContext(), "Llave privada generada en" + uri.getPath(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this.getApplicationContext(), "Error al generar la llave privada, verifique si la aplicación tiene permisos de escritura", Toast.LENGTH_LONG).show();
                }
                break;
            case 0:
                uri = data.getData();
                if (Escritor.Escribir(uri, this.getApplication(), llavePublica)) {
                    Toast.makeText(this.getApplicationContext(), "Llave publica generada en " + uri.getPath(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this.getApplicationContext(), "Error al generar la llave publica, verifique si la aplicación tiene permisos de escritura", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    public void ElegirRutaLlavePrivada(){
        Intent intent2 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
        intent2.setType("*/*");
        intent2.putExtra(Intent.EXTRA_TITLE, "private.key");
        startActivityForResult(intent2, 1);
    }

    public void ElegirRutaLlavePublica(){
        Intent intent2 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
        intent2.setType("*/*");
        intent2.putExtra(Intent.EXTRA_TITLE, "public.key");
        startActivityForResult(intent2, 0);
    }
}
