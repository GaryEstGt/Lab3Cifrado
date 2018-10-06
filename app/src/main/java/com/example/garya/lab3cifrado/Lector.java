package com.example.garya.lab3cifrado;

import android.app.Application;
import android.database.Cursor;
import android.net.Uri;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Lector {

    public static String LeerArchivo(Application application, Uri archivo) throws IOException {
        Cursor returnCursor1 = application.getContentResolver().query(archivo, null, null, null, null);
        returnCursor1.moveToFirst();
        InputStream IS = application.getContentResolver().openInputStream(archivo);
        BufferedReader BR = new BufferedReader(new InputStreamReader(IS));
        StringBuilder SB = new StringBuilder();
        int line;
        while ((line = BR.read()) != -1) {
            char val = (char) line;
            SB.append(val);
        }

        IS.close();
        BR.close();
        return SB.toString();
    }
}
