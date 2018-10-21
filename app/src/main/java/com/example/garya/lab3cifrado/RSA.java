package com.example.garya.lab3cifrado;

public class RSA {
    Long e;
    Long d;
    Long p;
    Long q;
    Long phi;
    Long n;

    public RSA(long p, long q){
        this.p = p;
        this.q = q;
        n = p*q;
        phi = (p-1)*(q-1);
    }

    public RSA(){

    }

    public char Cifrar(char letra, long E, long N){
        return (char)(Math.pow((int)letra,E)%N);
    }

    public char Descifrar(char letra, long D, long N){
        return (char)(Math.pow((int)letra,D)%N);
    }

    public String GenerarLlavePublica(){
        for (long i = 3; i < phi; i++) {
            if(esPrimo(i) && mcd(i,phi) == 1){
                e = i;
                break;
            }
        }

        return e.toString() + "," + n.toString();
    }

    public String GenerarLlavePrivada(){
        d = ModuloInverso(e,phi);

        if(d < 0){
            d = d+phi;
        }

        return d.toString() + "," + n.toString();
    }

    public static boolean esPrimo(long num){
        boolean resultado = true;

        for (int i = 2; i <= num/2; i++) {
            if(num%i == 0){
                resultado = false;
                break;
            }
        }

        return resultado;
    }

    public static long mcd(long num1, long num2){
        long a, b;
        long mcd = 0;

        if(num1 > num2){
            a = num1;
            b = num2;
        }
        else{
            b = num1;
            a = num2;
        }

        long residuo = a%b;

        if(residuo == 0){
            mcd = b;
        }
        else{
            while (residuo != 0){
                a = b;
                b = residuo;
                residuo = a%b;
            }

            mcd = b;
        }

        return mcd;
    }

    public static long ModuloInverso(long a, long b)
    {
        long resp = 0;
        long x=0,y=0,d=0;

        if(b==0)
        {
            resp = 1;
        }
        else
        {
            long x2 = 1, x1 = 0, y2 = 0, y1 = 1;
            long q = 0, r = 0;

            while(b>0)
            {
                q = (a/b);
                r = a - q*b;
                x = x2-q*x1;
                y = y2 - q*y1;
                a = b;
                b = r;
                x2 = x1;
                x1 = x;
                y2 = y1;
                y1 = y;
            }

            resp = x2;
        }

        return resp;
    }
}
