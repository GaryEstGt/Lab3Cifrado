package com.example.garya.lab3cifrado;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DSDES extends AppCompatActivity {

    @BindView(R.id.btnAbrirArchivo)
    Button btnAbrirArchivo;
    @BindView(R.id.txtArchivo)
    TextView txtArchivo;
    @BindView(R.id.tvMostrarArchivo)
    TextView tvMostrarArchivo;
    @BindView(R.id.txtClave)
    EditText txtClave;
    @BindView(R.id.btnDescifrar)
    Button btnDescifrar;

    Uri uri,uri2;
    String cadenaDescifrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsdes);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_CZigzag:
                finish();
                Intent intentCZ = new Intent(DSDES.this, MainActivity.class);
                startActivity(intentCZ);
                return true;
            case R.id.menu_DZigzag:
                finish();
                Intent intentDZig = new Intent(DSDES.this, DZigzag.class);
                startActivity(intentDZig);
                return true;
            case R.id.menu_CTransposicion:
                finish();
                Intent intentCT = new Intent(DSDES.this, CRuta.class);
                startActivity(intentCT);
                return true;
            case R.id.menu_DTransposicion:
                finish();
                Intent intentDT = new Intent(DSDES.this, DTransposicion.class);
                startActivity(intentDT);
                return true;
            case R.id.menu_CSDES:
                finish();
                Intent intentCS = new Intent(DSDES.this, CSDES.class);
                startActivity(intentCS);
                return true;
            case R.id.menu_DSDES:
                Toast.makeText(this.getApplicationContext(), "Ya esta en Descifrar SDES", Toast.LENGTH_LONG).show();
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

    @OnClick({R.id.btnAbrirArchivo, R.id.btnDescifrar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAbrirArchivo:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), 0);
                break;
            case R.id.btnDescifrar:
                if (uri != null) {
                    if (uri.getPath().contains(".scif")) {
                        if (!txtClave.getText().toString().isEmpty()) {
                            if(txtClave.getText().toString().length() == 10){
                                cadenaDescifrada = "";
                                try {
                                    SDES sdes = new SDES(txtClave.getText().toString());

                                    for (int i = 0; i < tvMostrarArchivo.getText().toString().length(); i++) {
                                        cadenaDescifrada += sdes.Descifrar(tvMostrarArchivo.getText().toString().charAt(i));
                                    }

                                    ElegirRutaDescifrado();

                                } catch (Exception e) {
                                    Toast.makeText(this.getApplicationContext(), "Error al descifrar", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(null,"Debe ingresar una clave de 10 bits", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this.getApplicationContext(), "Debe ingresar la clave del descifrado", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this.getApplicationContext(), "Debe elegir un archivo .scif para descifrar", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this.getApplicationContext(), "Debe elegir un archivo para descifrar", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                try {
                    super.onActivityResult(requestCode, resultCode, data);
                    if (resultCode == RESULT_CANCELED) {
                        //Cancelado por el usuario
                    }
                    if ((resultCode == RESULT_OK) && (requestCode == 0)) {
                        //Procesar el resultado

                        uri = data.getData();//obtener el uri content
                        String[] texto = uri.getPath().split("/");
                        txtArchivo.setText(texto[texto.length - 1]);
                        String contenido = Lector.LeerArchivo(this.getApplication(), uri);
                        tvMostrarArchivo.setText(contenido);
                        Toast.makeText(this.getApplicationContext(), "Archivo cargado con éxito", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this.getApplicationContext(), "Error al cargar el archivo", Toast.LENGTH_LONG).show();
                }
                break;
            case 1:
                uri2 = data.getData();
                if (Escritor.Escribir(uri2, this.getApplication(), cadenaDescifrada)) {
                    Toast.makeText(this.getApplicationContext(), "Archivo descifrado en " + uri2.getPath(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this.getApplicationContext(), "Error al generar el archivo descifrado, verifique si la aplicación tiene permisos de escritura", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void ElegirRutaDescifrado() {
        Intent intent2 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
        intent2.setType("*/*");
        intent2.putExtra(Intent.EXTRA_TITLE, txtArchivo.getText().toString().split("\\.")[0] + ".txt");
        startActivityForResult(intent2, 1);
    }
}
