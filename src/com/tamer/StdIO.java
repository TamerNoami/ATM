package com.tamer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StdIO {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String realLine()  {
        String txt = null;
        try {
            txt = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return txt;
    }

    public  static void write(String s){
        System.out.print(s);
    }

    public static void writeLine(String s){
        System.out.println(s);
    }
}
