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
        while (n < elementos) {
            //System.out.println("\nFlag : " + flag);
            switch (flag) {
                case 0: // Recorrido de izquierda a derecha
                    for (int m = x; m < matrizRuta[0].length - y; m++) {
                        if(n>arregloCaracteres.length-1){
                            matrizRuta[x][m]='¬';
                        }
                        else{
                            matrizRuta[x][m]=arregloCaracteres[n];
                        }

                        n++;
                    }
                    flag++;
                    break;

                case 1: //Recorrido de arriba abajo
                    for (int m = x + 1; m < matrizRuta.length - x; m++) {
                        if(n>arregloCaracteres.length-1){
                            matrizRuta[m][matrizRuta[0].length - 1 - y]='¬';
                        }
                        else{
                            matrizRuta[m][matrizRuta[0].length - 1 - y]=arregloCaracteres[n];
                        }

                        n++;
                    }
                    flag++;
                    break;

                case 2: //Recorrido de derecha a izquierda
                    for (int m = matrizRuta[0].length - 2 - y; m >= y; m--) {
                        if(n>arregloCaracteres.length-1){
                            matrizRuta[matrizRuta.length - 1 - x][m]='¬';
                        }else{
                            matrizRuta[matrizRuta.length - 1 - x][m]=arregloCaracteres[n];
                        }

                        n++;
                    }
                    flag++;
                    break;

                case 3: //Recorrido de abajo a arriba
                    for (int m = matrizRuta.length - 2 - x; m >= x + 1; m--) {
                        if(n>arregloCaracteres.length-1){
                            matrizRuta[m][y]='¬';
                        }else{
                            matrizRuta[m][y]=arregloCaracteres[n];
                        }

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
    public static String EscribirMatriz(int accion){
      //  char[] arregloCaracteres=texto.toCharArray();
        String textoResultante="";
        int x = 0; //Para manejar las filas
        int y = 0; //Para manejar las columnas
        int flag = 0;  //Bandera para saber que recorrido se debe realizar
        int n = 0; //Contador de elementos
        //Total de elementos en la matriz
        int elementos = matrizRuta.length * matrizRuta[0].length;

        // Recorrido en espiral
        while (n < elementos) {
            //System.out.println("\nFlag : " + flag);
            switch (flag) {
                case 1: // Recorrido de izquierda a derecha
                    for (int m = 1+y; m < matrizRuta[0].length - y; m++) {
                            textoResultante+=matrizRuta[matrizRuta.length-1-x][m];
                        n++;
                    }
                    flag++;
                    break;

                case 0: //Recorrido de arriba abajo
                    for (int m = x; m < matrizRuta.length-y ; m++) {
                            textoResultante+=matrizRuta[m][0+ y];
                        n++;
                    }
                    flag++;
                    break;

                case 3: //Recorrido de derecha a izquierda
                    for (int m = matrizRuta[0].length - 2 - y; m > y; m--) {
                            textoResultante+=matrizRuta[0+ x][m];
                        n++;
                    }
                    flag = 0;
                    //Se aumentan las filas y las columnas para un subnivel en el espiral
                    x++;
                    y++;
                    break;

                case 2: //Recorrido de abajo a arriba
                    for (int m = matrizRuta.length - 2 - x; m >= x; m--) {
                            textoResultante+=matrizRuta[m][matrizRuta.length-1-y];
                        n++;
                    }
                    flag++;
                    break;
            }

        }
        if(accion==1){
            textoResultante.replaceAll("¬","");
        }
        return  textoResultante;
    }
}
