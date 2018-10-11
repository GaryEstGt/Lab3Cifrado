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

public class DTransposicion extends AppCompatActivity {

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
    Uri uri;
    Uri uri2;
    String cadenaCifrada="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtransposicion);
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
                Intent intentCZig = new Intent(DTransposicion.this, MainActivity.class);
                startActivity(intentCZig);
                return true;
            case R.id.menu_DZigzag:
                Intent intentDZig = new Intent(DTransposicion.this, DZigzag.class);
                startActivity(intentDZig);
                return true;
            case R.id.menu_CTransposicion:
                Intent intentDT = new Intent(DTransposicion.this, CRuta.class);
                startActivity(intentDT);

                return true;
            case R.id.menu_DTransposicion:
                Toast.makeText(this.getApplicationContext(), "Ya esta en descifrar Ruta", Toast.LENGTH_LONG).show();
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
                    if (uri.getPath().contains(".cif")) {
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
                                    cadenaCifrada = Transpocision.EscribirMatriz(1);

                                    ElegirRutaCifrado();
                                } else {
                                    Toast.makeText(this.getApplicationContext(), "Error al descifrar", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(this.getApplicationContext(), "Error al descifrar", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this.getApplicationContext(), "Debe ingresar la clave del descifrado", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this.getApplicationContext(), "Debe elegir un archivo .txt para descifrar", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this.getApplicationContext(), "Debe elegir un archivo para descifrar", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    public void ElegirRutaCifrado() {
        Intent intent2 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
        intent2.setType("*/*");
        intent2.putExtra(Intent.EXTRA_TITLE, txtArchivo.getText().toString().split("\\.")[0] + ".txt");
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
                    Toast.makeText(this.getApplicationContext(), "Archivo descifrado en " + uri2.getPath(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this.getApplicationContext(), "Error al generar el archivo descifrado, verifique si la aplicación tiene permisos de escritura", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
