package com.example.garya.lab3cifrado;

public class SDES {

    String[][] SBox0;
    String[][] SBox1;
    String OrdenP10;
    String OrdenP8;
    String OrdenP4;
    String OrdenEP;
    String OrdenIP;

    public SDES(){
        SBox0 = new String[4][4];
        SBox1 = new String[4][4];
        OrdenP10 = "3406179825";
        OrdenP8 = "91350267";
        OrdenP4 = "0231";
        OrdenEP = "13202013";
        OrdenIP = "23567102";
    }

    //Recibe 10 bits
    public String P10(String cadena){
        return "10 bits permutados";
    }

    //Recibe 10 bits
    public String P8(String cadena){
        return "8 bits permutados";
    }

    //Recibe 4 bits
    public String P4(String cadena){
        return "4 bits permutados";
    }

    //Recibe 4 bits
    public String EP(String cadena){
        return "8 bits permutados";
    }

    //Recibe 8 bits
    public String IP(String cadena){
        return "8 bits permutados";
    }

    public String PermutacionInversa(String cadena){
        return "bits iniciales";
    }

    public String LS1(String cadena){
        return "Cadena corrida 1 posicion izquierda";
    }

    public String LS2(String cadena){
        return "Cadena corrida 2 posicion izquierda";
    }

    public String SBoxes(String cadena){
        return "";
    }

}
