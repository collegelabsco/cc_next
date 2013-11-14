package com.devsquare.cc.problem.bitmap;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;


public class BitmapHashStringGenerator {

    public static void main(String[] args) throws NoSuchAlgorithmException, Exception {

        File input = new File("C:/image.png");
        
        BufferedImage buffImg = ImageIO.read(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(buffImg, "png", outputStream);
        byte[] data = outputStream.toByteArray();

        System.out.println("Start MD5 Digest");
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        byte[] hash = md.digest();
        System.out.println(returnHex(hash));
    }                                       // Belongs to main class
   
    // Below method of converting Byte Array to hex
    // Can be found at: http://www.rgagnon.com/javadetails/java-0596.html
    static String returnHex(byte[] inBytes) throws Exception {
        String hexString = null;
        for (int i=0; i < inBytes.length; i++) { //for loop ID:1
            hexString +=
            Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }                                   // Belongs to for loop ID:1
    return hexString;
  }      
    
    static final String HEXES = "0123456789ABCDEF";
    public static String getHex( byte [] raw ) {
      if ( raw == null ) {
        return null;
      }
      final StringBuilder hex = new StringBuilder( 2 * raw.length );
      for ( final byte b : raw ) {
        hex.append(HEXES.charAt((b & 0xF0) >> 4))
           .append(HEXES.charAt((b & 0x0F)));
      }
      return hex.toString();
    }// Belongs to returnHex class

}

