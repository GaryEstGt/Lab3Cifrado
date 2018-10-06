package com.example.garya.lab3cifrado;

import java.util.LinkedList;

public class ZigZag {

    public String Cifrar(String cadena, int clave){
        try{
            LinkedList[] listas = new LinkedList[clave];
            for (int i = 0; i < listas.length; i++) {
                listas[i] = new LinkedList();
            }

            String cadenaCifrada = "";

            int olasMinimas = 0, caracteresPorOla = 0, caracteresFaltantes = 0;
            caracteresPorOla = (clave * 2) - 2;

            if(cadena.length()%caracteresPorOla == 0){
                olasMinimas = cadena.length() / caracteresPorOla;
            }
            else{
                olasMinimas = (cadena.length() / caracteresPorOla) + 1;
                caracteresFaltantes = caracteresPorOla - cadena.length()%caracteresPorOla;
                for (int i = 0; i < caracteresFaltantes; i++) {
                    cadena = cadena + "¬";
                }
            }

            int contadorCaracteres = 0;

            for (int i = 0; i < olasMinimas; i++) {
                int nivel = 0;
                boolean deRegreso = false;
                for (int j = 0; j < caracteresPorOla; j++) {
                    listas[nivel].add(cadena.charAt(contadorCaracteres));
                    contadorCaracteres++;

                    if(nivel < clave-1 && !deRegreso){
                        nivel++;
                    }
                    else if(nivel == clave-1){
                        deRegreso = true;
                        nivel--;
                    }
                    else if(deRegreso){
                        nivel--;
                        if(nivel == 0){
                            deRegreso = false;
                        }
                    }
                }
            }

            for (int i = 0; i < listas.length; i++) {
                for (int j = 0; j < listas[i].size(); j++) {
                    cadenaCifrada += listas[i].get(j);
                }
            }

            return cadenaCifrada;
        }
        catch (Exception e){
            return "ERROR";
        }
    }
}
