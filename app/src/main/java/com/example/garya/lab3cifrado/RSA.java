package com.example.garya.lab3cifrado;

import java.math.BigInteger;

public class RSA {
    BigInteger e;
    BigInteger d;
    BigInteger p;
    BigInteger q;
    BigInteger phi;
    BigInteger n;

    public RSA(BigInteger p, BigInteger q){
        this.p = p;
        this.q = q;
        n = p.multiply(q);
        phi = p.subtract(new BigInteger("1")).multiply(q.subtract(new BigInteger("1")));
    }

    public RSA(){

    }

    public char Cifrar(char letra, BigInteger E, BigInteger N){
        Long l;
        l = (long)letra;
        BigInteger le = new BigInteger(l.toString());
        return (char)(le.modPow(E,N)).intValue();
    }

    public char Descifrar(char letra, BigInteger D, BigInteger N){
        Long l;
        l = (long)letra;
        BigInteger le = new BigInteger(l.toString());
        return (char)(le.modPow(D,N)).intValue();
    }

    public String GenerarLlavePublica(){
        for (BigInteger i = new BigInteger("3"); i.compareTo(phi) == -1; i = i.add(new BigInteger("2"))) {
            if(i.gcd(phi).compareTo(new BigInteger("1")) == 0){
                e = i;
                break;
            }
        }

        return e.toString() + "," + n.toString();
    }

    public String GenerarLlavePrivada(){
        d = e.modInverse(phi);

        if(d.compareTo(new BigInteger("0")) == -1){
            d = d.add(phi);
        }

        return d.toString() + "," + n.toString();
    }

    public static boolean esPrimo(BigInteger num){
        boolean resultado = true;

        for (BigInteger i = new BigInteger("2"); i.compareTo(num) < 0; i = i.add(new BigInteger("1"))) {
            if(num.mod(i).equals(new BigInteger("0"))){
                resultado = false;
                break;
            }
        }

        return resultado;
    }

    /*public static BigInteger mcd(BigInteger num1, BigInteger num2){
        BigInteger a, b;
        BigInteger mcd;

        if(num1.compareTo(num2) == 1){
            a = num1;
            b = num2;
        }
        else{
            b = num1;
            a = num2;
        }

        BigInteger residuo = a.mod(b);

        if(residuo.compareTo(new BigInteger("0")) == 0){
            mcd = b;
        }
        else{
            while (residuo.compareTo(new BigInteger("0")) != 0){
                a = b;
                b = residuo;
                residuo = a.mod(b);
            }

            mcd = b;
        }

        return mcd;
    }*/

    /*public static long ModuloInverso(long a, long b)
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
    }*/
}
