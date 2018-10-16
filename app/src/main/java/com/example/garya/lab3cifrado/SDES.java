package com.example.garya.lab3cifrado;

public class SDES {

    String[][] SBox0;
    String[][] SBox1;
    String OrdenP10;
    String OrdenP8;
    String OrdenP4;
    String OrdenEP;
    String OrdenIP;
    String clave1;
    String clave2;

    public SDES(){
        SBox0 = new String[4][4];
        SBox1 = new String[4][4];
        OrdenP10 = "3406179825";
        OrdenP8 = "35261704";
        OrdenP4 = "0231";
        OrdenEP = "63724105";
        OrdenIP = "23567104";

        SBox0[0][0] = "01";
        SBox0[0][1] = "00";
        SBox0[0][2] = "11";
        SBox0[0][3] = "10";
        SBox0[1][0] = "11";
        SBox0[1][1] = "10";
        SBox0[1][2] = "01";
        SBox0[1][3] = "00";
        SBox0[2][0] = "00";
        SBox0[2][1] = "10";
        SBox0[2][2] = "01";
        SBox0[2][3] = "11";
        SBox0[3][0] = "11";
        SBox0[3][1] = "01";
        SBox0[3][2] = "11";
        SBox0[3][3] = "10";

        SBox1[0][0] = "00";
        SBox1[0][1] = "01";
        SBox1[0][2] = "10";
        SBox1[0][3] = "11";
        SBox1[1][0] = "10";
        SBox1[1][1] = "00";
        SBox1[1][2] = "01";
        SBox1[1][3] = "11";
        SBox1[2][0] = "11";
        SBox1[2][1] = "00";
        SBox1[2][2] = "01";
        SBox1[2][3] = "00";
        SBox1[3][0] = "10";
        SBox1[3][1] = "01";
        SBox1[3][2] = "00";
        SBox1[3][3] = "11";

    }

    public void GenerarLlaves(String bits){
        String p10 = P10(bits);

        String ls11 = LS1(p10.substring(0,5));
        String ls12 = LS1(p10.substring(5,10));
        clave1 = P8(ls11 + ls12);
        String ls21 = LS2(ls11);
        String ls22 = LS2(ls12);
        clave2 = P8(ls21 + ls22);
    }

    public void Cifrar(char letra){
        int num = letra;
        String bits = Integer.toBinaryString(num);
        
    }

    //Recibe 10 bits
    public String P10(String cadena){
        char[] cadenaPermutada = new char[10];
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            stringBuilder.append(OrdenP10.charAt(i));
            cadenaPermutada[Integer.parseInt(stringBuilder.toString())] = cadena.charAt(i);
            stringBuilder.delete(0, stringBuilder.length());
        }

        for (int i = 0; i < 10; i++) {
            stringBuilder.append(cadenaPermutada[i]);
        }
        return stringBuilder.toString();
    }

    //Recibe 10 bits
    public String P8(String cadena){
        char[] cadenaPermutada = new char[8];
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            stringBuilder.append(OrdenP8.charAt(i));
            cadenaPermutada[Integer.parseInt(stringBuilder.toString())] = cadena.charAt(i);
            stringBuilder.delete(0, stringBuilder.length());
        }

        for (int i = 0; i < 8; i++) {
            stringBuilder.append(cadenaPermutada[i]);
        }
        return stringBuilder.toString();
    }

    //Recibe 4 bits
    public String P4(String cadena){
        char[] cadenaPermutada = new char[4];
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            stringBuilder.append(OrdenP4.charAt(i));
            cadenaPermutada[Integer.parseInt(stringBuilder.toString())] = cadena.charAt(i);
            stringBuilder.delete(0, stringBuilder.length());
        }

        for (int i = 0; i < 4; i++) {
            stringBuilder.append(cadenaPermutada[i]);
        }
        return stringBuilder.toString();
    }

    //Recibe 4 bits
    public String EP(String cadena){
        char[] cadenaPermutada = new char[8];
        StringBuilder stringBuilder = new StringBuilder();

        int posicionCadena = 0;
        for (int i = 0; i < 8; i++) {
            stringBuilder.append(OrdenEP.charAt(i));
            cadenaPermutada[Integer.parseInt(stringBuilder.toString())] = cadena.charAt(posicionCadena);
            stringBuilder.delete(0, stringBuilder.length());

            if(posicionCadena == 3){
                posicionCadena = 0;
            }
            else{
                posicionCadena++;
            }
        }

        for (int i = 0; i < 8; i++) {
            stringBuilder.append(cadenaPermutada[i]);
        }
        return stringBuilder.toString();
    }

    //Recibe 8 bits
    public String IP(String cadena){
        char[] cadenaPermutada = new char[8];
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            stringBuilder.append(OrdenIP.charAt(i));
            cadenaPermutada[Integer.parseInt(stringBuilder.toString())] = cadena.charAt(i);
            stringBuilder.delete(0, stringBuilder.length());
        }

        for (int i = 0; i < 8; i++) {
            stringBuilder.append(cadenaPermutada[i]);
        }
        return stringBuilder.toString();
    }

    public String PermutacionInversa(String cadena){
        char[] cadenaInversa = new char[8];
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            stringBuilder.append(OrdenIP.charAt(i));
            cadenaInversa[i] = cadena.charAt(Integer.parseInt(stringBuilder.toString()));
            stringBuilder.delete(0, stringBuilder.length());
        }

        for (int i = 0; i < 8; i++) {
            stringBuilder.append(cadenaInversa[i]);
        }
        return stringBuilder.toString();
    }

    public String LS1(String cadena){
        StringBuilder stringBuilder = new StringBuilder();
        char[] swiftCadena = new char[6];

        for (int i = 0; i < 4; i++) {
            swiftCadena[i] = cadena.charAt(i + 1);
        }

        swiftCadena[4] = cadena.charAt(0);

        for (int i = 0; i < 5; i++) {
            stringBuilder.append(swiftCadena[i]);
        }
        return stringBuilder.toString();
    }

    public String LS2(String cadena){
        StringBuilder stringBuilder = new StringBuilder();

        char[] swiftCadena = new char[7];

        for (int i = 0; i < 3; i++) {
            swiftCadena[i] = cadena.charAt(i + 2);
        }

        swiftCadena[3] = cadena.charAt(0);
        swiftCadena[4] = cadena.charAt(1);

        for (int i = 0; i < 5; i++) {
            stringBuilder.append(swiftCadena[i]);
        }
        return stringBuilder.toString();
    }

    public String SBoxes1(String cadena){
        int fila, columna;
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(cadena.charAt(0));
        stringBuilder.append(cadena.charAt(3));

        fila = Integer.parseInt( stringBuilder.toString(),2);
        stringBuilder.delete(0, stringBuilder.length());

        stringBuilder.append(cadena.charAt(1));
        stringBuilder.append(cadena.charAt(2));

        columna = Integer.parseInt( stringBuilder.toString(),2);


        return SBox0[fila][columna];
    }

    public String SBoxes2(String cadena){
        int fila, columna;
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(cadena.charAt(0));
        stringBuilder.append(cadena.charAt(3));

        fila = Integer.parseInt( stringBuilder.toString(),2);
        stringBuilder.delete(0, stringBuilder.length());

        stringBuilder.append(cadena.charAt(1));
        stringBuilder.append(cadena.charAt(2));

        columna = Integer.parseInt( stringBuilder.toString(),2);


        return SBox1[fila][columna];
    }

    public String XOR(String cadena1, String cadena2){
        String textoXOR = "";

        for (int i = 0; i < cadena1.length(); i++) {
            if(cadena1.charAt(i) == cadena2.charAt(i)){
                textoXOR+="1";
            }
            else{
                textoXOR+="0";
            }
        }

        return textoXOR;
    }

    public String Switch(String cadena){
        StringBuilder stringBuilder = new StringBuilder();

        char[] cadenaCambiada = new char[8];

        for (int i = 0; i < 4; i++) {
            cadenaCambiada[i+4] = cadena.charAt(i);
            cadenaCambiada[i] = cadena.charAt(i+4);
        }

        for (int i = 0; i < 8; i++) {
            stringBuilder.append(cadenaCambiada[i]);
        }
        return stringBuilder.toString();
    }

}
