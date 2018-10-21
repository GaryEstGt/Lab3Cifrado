package com.example.garya.lab3cifrado;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    Uri uri;
    RSA rsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsallaves);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnGenerarLlaves)
    public void onViewClicked() {
        if(!txtP.getText().toString().isEmpty() && !txtQ.getText().toString().isEmpty()){
            if(RSA.esPrimo(Long.parseLong(txtP.getText().toString())) && RSA.esPrimo(Long.parseLong(txtQ.getText().toString()))){
                if(Long.parseLong(txtP.getText().toString()) >= 13){
                    if(Long.parseLong(txtQ.getText().toString()) >= 17){
                        rsa = new RSA(new BigInteger(txtP.getText().toString()),new BigInteger(txtQ.getText().toString()));
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
                if (Escritor.Escribir(uri, this.getApplication(), rsa.GenerarLlavePrivada())) {
                    Toast.makeText(this.getApplicationContext(), "Llave privada generada en" + uri.getPath(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this.getApplicationContext(), "Error al generar la llave privada, verifique si la aplicación tiene permisos de escritura", Toast.LENGTH_LONG).show();
                }
                break;
            case 2:
                uri = data.getData();
                if (Escritor.Escribir(uri, this.getApplication(), rsa.GenerarLlavePublica())) {
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
        startActivityForResult(intent2, 2);
    }
}
