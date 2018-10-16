package com.example.garya.lab3cifrado;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CRuta extends AppCompatActivity {

    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.txtArchivo)
    TextView txtArchivo;
    @BindView(R.id.tvMostrarArchivo)
    TextView tvMostrarArchivo;
    @BindView(R.id.txtClave)
    EditText txtClave;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.txtColumna)
    EditText txtColumna;
    Uri uri2;
    Uri uri;
    String cadenaCifrada = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cruta);
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
                Intent intentCZ = new Intent(CRuta.this, MainActivity.class);
                startActivity(intentCZ);
                return true;
            case R.id.menu_DZigzag:
                finish();
                Intent intentDZig = new Intent(CRuta.this, DZigzag.class);
                startActivity(intentDZig);
                return true;
            case R.id.menu_CTransposicion:
                Toast.makeText(this.getApplicationContext(), "Ya esta en Cifrar Ruta", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_DTransposicion:
                finish();
                Intent intentDT = new Intent(CRuta.this, DTransposicion.class);
                startActivity(intentDT);
                return true;
            case R.id.menu_CSDES:
                finish();
                Intent intentCS = new Intent(CRuta.this, CSDES.class);
                startActivity(intentCS);
                return true;
            case R.id.menu_DSDES:
                finish();
                Intent intentDS = new Intent(CRuta.this, DSDES.class);
                startActivity(intentDS);
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

    @OnClick({R.id.btnAbrirArchivo, R.id.btnCifrar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAbrirArchivo:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "Choose File"), 0);
                break;
            case R.id.btnCifrar:
                if (uri != null) {
                    if (uri.getPath().contains(".txt")) {
                        if (!txtClave.getText().toString().isEmpty()) {
                            int fila = 0;
                            int columna = 0;
                            String contenido= null;
                            try {
                                contenido = Lector.LeerArchivo(this.getApplication(),uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                fila = Integer.parseInt(txtClave.getText().toString());
                                columna = Integer.parseInt(txtColumna.getText().toString());
                                boolean verificacion=Transpocision.validarClave(columna,fila,contenido);
                                if (verificacion) {
                                    Transpocision.LlenarMatriz(contenido);
                                    cadenaCifrada = Transpocision.EscribirMatriz(2);
                                    ElegirRutaCifrado();
                                } else {
                                    Toast.makeText(this.getApplicationContext(), "Error al cifrar", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(this.getApplicationContext(), "Error al cifrar", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this.getApplicationContext(), "Debe ingresar la clave del cifrado", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this.getApplicationContext(), "Debe elegir un archivo .txt para cifrar", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this.getApplicationContext(), "Debe elegir un archivo para cifrar", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    public void ElegirRutaCifrado() {
        Intent intent2 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
        intent2.setType("*/*");
        intent2.putExtra(Intent.EXTRA_TITLE, txtArchivo.getText().toString().split("\\.")[0] + ".cif");
        startActivityForResult(intent2, 1);
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
                if (Escritor.Escribir(uri2, this.getApplication(), cadenaCifrada)) {
                    Toast.makeText(this.getApplicationContext(), "Archivo cifrado en " + uri2.getPath(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this.getApplicationContext(), "Error al generar el archivo cifrado, verifique si la aplicación tiene permisos de escritura", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
