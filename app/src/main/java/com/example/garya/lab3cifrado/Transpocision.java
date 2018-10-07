package com.example.garya.lab3cifrado;

public class Transpocision {
    public static char[][] matrizRuta;
    public static boolean validarClave(int columna,int fila,String texto){
        if((columna*fila)<texto.length()){
            return false;
        }
        else{
            matrizRuta=new char[fila][columna];
            return true;
        }
    }
    public static void LlenarMatriz(String texto){
        char[] arregloCaracteres=texto.toCharArray();
        int x = 0; //Para manejar las filas
        int y = 0; //Para manejar las columnas
        int flag = 0;  //Bandera para saber que recorrido se debe realizar
        int n = 0; //Contador de elementos
        //Total de elementos en la matriz
        int elementos = matrizRuta.length * matrizRuta[0].length;

        // Recorrido en espiral
       // System.out.println("\nMostrando recorrido en espiral");
        while (n < elementos) {
            //System.out.println("\nFlag : " + flag);
            switch (flag) {
                case 0: // Recorrido de izquierda a derecha
                    for (int m = x; m < matrizRuta[0].length - y; m++) {
                        matrizRuta[x][m]='a';
                        n++;
                    }
                    flag++;
                    break;

                case 1: //Recorrido de arriba abajo
                    for (int m = x + 1; m < matrizRuta.length - x; m++) {
                        matrizRuta[m][matrizRuta[0].length - 1 - y]='z';
                        n++;
                    }
                    flag++;
                    break;

                case 2: //Recorrido de derecha a izquierda
                    for (int m = matrizRuta[0].length - 2 - y; m >= y; m--) {
                        matrizRuta[matrizRuta.length - 1 - x][m]='q';
                        n++;
                    }
                    flag++;
                    break;

                case 3: //Recorrido de abajo a arriba
                    for (int m = matrizRuta.length - 2 - x; m >= x + 1; m--) {
                        matrizRuta[m][y]='m';
                        n++;
                    }
                    flag = 0;
                    //Se aumentan las filas y las columnas para un subnivel en el espiral
                    x++;
                    y++;
                    break;
            }

        }
    }
}
