package com.tamer;

import java.io.*;

public class FileIO {

 public static <E> void writeFile(E obj, String filename){
    try (FileOutputStream fileOut= new FileOutputStream(filename);
    ObjectOutputStream out = new ObjectOutputStream(fileOut)){
        out.writeObject(obj);
        StdIO.writeLine("Serialized data is saved in "+filename);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


public static <E> E readObject(String filename){
     try(FileInputStream fileIn=new FileInputStream(filename);
     ObjectInputStream in = new ObjectInputStream(fileIn)){
         E theObj = (E) in.readObject();
         StdIO.writeLine("Deserialized data from file  "+filename);
         return theObj;
     } catch (FileNotFoundException e) {
         e.printStackTrace();
         return null;
     } catch (IOException e) {
         e.printStackTrace();
         return null;
     } catch (ClassNotFoundException e) {
         e.printStackTrace();
         return null;
     }
}




}
