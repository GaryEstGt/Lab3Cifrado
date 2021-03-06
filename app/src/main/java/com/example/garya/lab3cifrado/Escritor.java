package com.example.garya.lab3cifrado;

import android.app.Application;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public class Escritor {
    private static Charset UTF8 = Charset.forName("UTF-8");

    public static boolean Escribir(Uri selectedFile, Application app, String cadena){
        try{
            ParcelFileDescriptor file = app.getContentResolver().openFileDescriptor(selectedFile, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(file.getFileDescriptor());
            Writer writer = new OutputStreamWriter(fileOutputStream, UTF8);
            writer.write(cadena);
            writer.flush();
            writer.close();
            fileOutputStream.close();
            file.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
