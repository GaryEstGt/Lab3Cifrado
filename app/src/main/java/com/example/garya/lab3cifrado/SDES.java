package com.example.garya.lab3cifrado;

public class SDES {

    private String[][] SBox0 = {{"01","00","11","10"},{"11","10","01","00"},{"00","10","01","11"},{"11","01","11","10"}};
    private String[][] SBox1 = {{"00","01","10","11"},{"10","00","01","11"},{"11","00","01","00"},{"10","01","00","11"}};

    private String OrdenP10 = "3406179825";
    private String OrdenP8 = "35261704";
    private String OrdenP4 = "0231";
    private String OrdenEP = "63724105";
    private String OrdenIP = "23567104";

    private String clave1;
    private String clave2;

    public SDES(String bits){
        GenerarLlaves(bits);
    }

    private void GenerarLlaves(String bits){
        String p10 = P10(bits);

        String ls11 = LS1(p10.substring(0,5));
        String ls12 = LS1(p10.substring(5,10));
        clave1 = P8(ls11 + ls12);
        String ls21 = LS2(ls11);
        String ls22 = LS2(ls12);
        clave2 = P8(ls21 + ls22);
    }

    public char Cifrar(char letra){
        int num = letra;

        String bits = Integer.toBinaryString(num);

        int cerosFaltantes = 8 - bits.length()%8;

        if(cerosFaltantes == 8){
            cerosFaltantes = 0;
        }

        for (int i = 0; i < cerosFaltantes; i++) {
            bits = "0" + bits;
        }

        String ip = IP(bits);
        String xor1 = XOR(EP(ip.substring(4,8)),clave1);
        String switch1 = Switch(XOR(ip.substring(0,4), P4(SBoxes1(xor1.substring(0,4)) + SBoxes2(xor1.substring(4,8)))) + ip.substring(4,8));
        String xor3 = XOR(EP(switch1.substring(4, 8)), clave2);
        return (char)Integer.parseInt(PermutacionInversa(XOR(P4(SBoxes1(xor3.substring(0,4)) + SBoxes2(xor3.substring(4,8))), switch1.substring(0,4)) + switch1.substring(4,8)),2);
    }

    public char Descifrar(char letra){
        int num = letra;

        String bits = Integer.toBinaryString(num);

        int cerosFaltantes = 8 - bits.length()%8;

        if(cerosFaltantes == 8){
            cerosFaltantes = 0;
        }

        for (int i = 0; i < cerosFaltantes; i++) {
            bits = "0" + bits;
        }

        String ip = IP(bits);
        String xor1 = XOR(EP(ip.substring(4,8)),clave2);
        String switch1 = Switch(XOR(ip.substring(0,4), P4(SBoxes1(xor1.substring(0,4)) + SBoxes2(xor1.substring(4,8)))) + ip.substring(4,8));
        String xor3 = XOR(EP(switch1.substring(4, 8)), clave1);
        return (char)Integer.parseInt(PermutacionInversa(XOR(P4(SBoxes1(xor3.substring(0,4)) + SBoxes2(xor3.substring(4,8))), switch1.substring(0,4)) + switch1.substring(4,8)),2);
    }
    //Recibe 10 bits
    private String P10(String cadena){
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
    private String P8(String cadena){
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
    private String P4(String cadena){
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
    private String EP(String cadena){
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
    private String IP(String cadena){
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

    private String PermutacionInversa(String cadena){
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

    private String LS1(String cadena){
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

    private String LS2(String cadena){
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

    private String SBoxes1(String cadena){
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

    private String SBoxes2(String cadena){
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

    private String XOR(String cadena1, String cadena2){
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

    private String Switch(String cadena){
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
