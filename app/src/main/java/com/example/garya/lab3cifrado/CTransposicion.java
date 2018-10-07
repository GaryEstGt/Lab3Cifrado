package com.example.garya.lab3cifrado;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CTransposicion extends AppCompatActivity {

    @BindView(R.id.textView2)
    TextView textView2;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctransposicion);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnAbrirArchivo, R.id.btnCifrar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAbrirArchivo:
                break;
            case R.id.btnCifrar:
                String text="Hola soy gar";
                Transpocision.validarClave(3,4,text);
                Transpocision.LlenarMatriz(text);
                break;
        }
    }
}
