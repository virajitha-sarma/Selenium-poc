package com.selenium;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

public class Util {

  public static void writeToFile(String fileName, byte[] content) {
    OutputStream fw = null;
    try {
      fw = new FileOutputStream(fileName);
      fw.write(content);
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage());
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } finally{
       if(fw != null) {
         try {
           fw.close();
         } catch (IOException e) {
           System.err.println(e.getMessage());
         }
       }
    }
  }

  public static byte[] base64Decode(String encodedContent) {
    return Base64.getDecoder().decode(encodedContent);
  }
}

