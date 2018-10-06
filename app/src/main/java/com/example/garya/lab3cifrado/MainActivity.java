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

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnAbrirArchivo)
    Button btnAbrirArchivo;
    @BindView(R.id.txtArchivo)
    TextView txtArchivo;
    @BindView(R.id.tvMostrarArchivo)
    TextView tvMostrarArchivo;
    @BindView(R.id.txtClave)
    EditText txtClave;
    @BindView(R.id.btnCifrar)
    Button btnCifrar;

    Uri uri, uri2;
    private String cadenaCifrada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Toast.makeText(this.getApplicationContext(), "Ya esta en Cifrar zig zag", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_DZigzag:
                Intent intentDZig = new Intent(MainActivity.this, DZigzag.class);
                startActivity(intentDZig);
                return true;
            case R.id.menu_CTransposicion:
                Intent intentCT = new Intent(MainActivity.this, CTransposicion.class);
                startActivity(intentCT);
                return true;
            case R.id.menu_DTransposicion:
                Intent intentDT = new Intent(MainActivity.this, DTransposicion.class);
                startActivity(intentDT);
                return true;
            case R.id.menu_CCesar:
                Intent intentCC = new Intent(MainActivity.this, CCesar.class);
                startActivity(intentCC);
                return true;
            case R.id.DCesar:
                Intent intentDC= new Intent(MainActivity.this, DCesar.class);
                startActivity(intentDC);
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
                if(uri != null){
                    if(uri.getPath().contains(".txt")){
                        if(!txtClave.getText().toString().isEmpty()){
                            cadenaCifrada = "";
                            ZigZag zigZag = new ZigZag();
                            int clave = 0;
                            try{
                                clave = Integer.parseInt(txtClave.getText().toString());
                                cadenaCifrada = zigZag.Cifrar(tvMostrarArchivo.getText().toString(), clave);
                                if(cadenaCifrada != "ERROR"){
                                    ElegirRutaCifrado();
                                }
                                else{
                                    Toast.makeText(this.getApplicationContext(), "Error al cifrar", Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(this.getApplicationContext(), "Error al cifrar", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(this.getApplicationContext(), "Debe ingresar la clave del cifrado", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(this.getApplicationContext(), "Error al cargar el archivo", Toast.LENGTH_LONG).show();
                }
                break;
            case 1:
                uri2 = data.getData();
                if(Escritor.Escribir(uri2, this.getApplication(), cadenaCifrada)){
                    Toast.makeText(this.getApplicationContext(), "Archivo comprimido en " + uri2.getPath(), Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this.getApplicationContext(), "Error al generar el archivo cifrado, verifique si la aplicación tiene permisos de escritura", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void ElegirRutaCifrado(){
        Intent intent2 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
        intent2.setType("*/*");
        intent2.putExtra(Intent.EXTRA_TITLE, txtArchivo.getText().toString().split("\\.")[0] + ".cif");
        startActivityForResult(intent2, 1);
    }
}
