package com.example.garya.lab3cifrado;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CRSA extends AppCompatActivity {

    @BindView(R.id.btnAbrirArchivo)
    Button btnAbrirArchivo;
    @BindView(R.id.txtArchivo)
    TextView txtArchivo;
    @BindView(R.id.tvMostrarArchivo)
    TextView tvMostrarArchivo;
    @BindView(R.id.btnCifrar)
    Button btnCifrar;
    @BindView(R.id.txtClave)
    TextView txtClave;
    @BindView(R.id.btnAbrirLlave)
    Button btnAbrirLlave;


    Uri uri,uri2, llave;
    String cadenaCifrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crs);
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
                Intent intentCZ = new Intent(CRSA.this, MainActivity.class);
                startActivity(intentCZ);
                return true;
            case R.id.menu_DZigzag:
                finish();
                Intent intentDZig = new Intent(CRSA.this, DZigzag.class);
                startActivity(intentDZig);
                return true;
            case R.id.menu_CTransposicion:
                finish();
                Intent intentCT = new Intent(CRSA.this, CRuta.class);
                startActivity(intentCT);
                return true;
            case R.id.menu_DTransposicion:
                finish();
                Intent intentDT = new Intent(CRSA.this, DTransposicion.class);
                startActivity(intentDT);
                return true;
            case R.id.menu_CSDES:
                finish();
                Intent intentCS = new Intent(CRSA.this, CSDES.class);
                startActivity(intentCS);
                return true;
            case R.id.menu_DSDES:
                finish();
                Intent intentDS = new Intent(CRSA.this, DSDES.class);
                startActivity(intentDS);
                return true;
            case R.id.menu_LRSA:
                finish();
                Intent intentLRSA = new Intent(CRSA.this, RSALlaves.class);
                startActivity(intentLRSA);
                return true;
            case R.id.menu_CRSA:
                Toast.makeText(this.getApplicationContext(), "Ya esta en Cifrar RSA", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_DRSA:
                finish();
                Intent intentDR = new Intent(CRSA.this, DRSA.class);
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

    @OnClick({R.id.btnAbrirArchivo, R.id.btnCifrar, R.id.btnAbrirLlave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAbrirArchivo:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), 0);
                break;
            case R.id.btnAbrirLlave:
                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                intent2.setType("*/*");
                startActivityForResult(Intent.createChooser(intent2, "Choose File"), 2);
                break;
            case R.id.btnCifrar:
                if(uri != null){
                    if(uri.getPath().contains(".txt")){
                        if(!txtClave.getText().toString().isEmpty()){
                                cadenaCifrada = "";
                                try{
                                    RSA rsa = new RSA();
                                    String[] clave = txtClave.getText().toString().split(",");

                                    for (int i = 0; i < tvMostrarArchivo.getText().toString().length(); i++) {
                                        cadenaCifrada += rsa.Cifrar(tvMostrarArchivo.getText().toString().charAt(i),new BigInteger(clave[0]), new BigInteger(clave[1]));
                                    }

                                    ElegirRutaCifrado();
                                }
                                catch (Exception e){
                                    Toast.makeText(this.getApplicationContext(), "Error al cifrar", Toast.LENGTH_LONG).show();
                                }
                        }
                        else{
                            Toast.makeText(this.getApplicationContext(), "Debe seleccionar la llave pública", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(this.getApplicationContext(), "Debe elegir un archivo .txt para cifrar", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(this.getApplicationContext(), "Debe elegir un archivo para cifrar", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 0:
                try{
                    super.onActivityResult(requestCode, resultCode, data);
                    if (resultCode == RESULT_CANCELED) {
                        //Cancelado por el usuario
                    }if ((resultCode == RESULT_OK) && (requestCode == 0)) {
                        //Procesar el resultado

                        uri = data.getData();//obtener el uri content
                        String[] texto = uri.getPath().split("/");
                        txtArchivo.setText(texto[texto.length - 1]);
                        String contenido = Lector.LeerArchivo(this.getApplication(),uri);
                        tvMostrarArchivo.setText(contenido);
                        Toast.makeText(this.getApplicationContext(), "Archivo cargado con éxito", Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    Toast.makeText(this.getApplicationContext(), "Error al cargar el archivo, verifique si tiene permisos de almacenamiento", Toast.LENGTH_LONG).show();
                }
                break;
            case 1:
                uri2 = data.getData();
                if(Escritor.Escribir(uri2, this.getApplication(), cadenaCifrada)){
                    Toast.makeText(this.getApplicationContext(), "Archivo cifrado en " + uri2.getPath(), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this.getApplicationContext(), "Error al generar el archivo cifrado, verifique si la aplicación tiene permisos de escritura", Toast.LENGTH_LONG).show();
                }
                break;
            case 2:
                try{
                    super.onActivityResult(requestCode, resultCode, data);
                    if (resultCode == RESULT_CANCELED) {
                        //Cancelado por el usuario
                    }if ((resultCode == RESULT_OK) && (requestCode == 0)) {
                        //Procesar el resultado

                        llave = data.getData();//obtener el uri content
                        String contenido = Lector.LeerArchivo(this.getApplication(),llave);
                        txtClave.setText(contenido);
                        Toast.makeText(this.getApplicationContext(), "Llave cargada con éxito", Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    Toast.makeText(this.getApplicationContext(), "Error al cargar la llave, verifique si tiene permisos de almacenamiento", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    public void ElegirRutaCifrado(){
        Intent intent2 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
        intent2.setType("*/*");
        intent2.putExtra(Intent.EXTRA_TITLE, txtArchivo.getText().toString().split("\\.")[0] + ".rsacif");
        startActivityForResult(intent2, 1);
    }
}
