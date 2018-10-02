/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.security;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


/**
 *
 * @author joshua
 */
public class Passwort {
    
    private static final int ITERATIONEN = 32;
    private static final int LAENGESALT = 64;
    private static final int LAENGEKEY = 64;
    private static final String ALGORITHMUS = "PBKDF2WithHmacSHA1";
    
    public static byte[] saltGenerieren(){
        SecureRandom random = new SecureRandom();
        byte salt[] = new byte[LAENGESALT];
        random.nextBytes(salt);
        return salt;
    }
    
    public static String hashen(String p_passwort, byte[] p_salt) throws Exception{
        KeySpec spec = new PBEKeySpec(p_passwort.toCharArray(), p_salt, ITERATIONEN, LAENGEKEY);
        SecretKeyFactory fabrik = SecretKeyFactory.getInstance(ALGORITHMUS);
        byte[] hashedBytes = fabrik.generateSecret(spec).getEncoded();
        return byteToString(hashedBytes);
    }
    
    public static String byteToString(byte[] p_byte) {
        Base64.Encoder encoder = Base64.getEncoder();
        return  encoder.encodeToString(p_byte);
    }
    
    public static byte[] stringToByte(String p_string){
        int laenge = p_string.length();
        byte[] bytes = new byte[p_string.length()];
        for(int i = 0; i < laenge; i++){
            bytes[i] = Byte.parseByte(p_string.substring(i, i+1));
        }
        return bytes;
    }
    
    /**
     * 
     * @param p_eigabe das eigegebene Passwort, das noch gehashed werden muss
     * @param p_gespeichert das gespeicherte, bereits gehashte Passwort
     * @param p_salt salt zum hashen des eingegebenen Passworts
     * @return true - Passwörter stimmen überein, false - Passwörter stimmen nicht überein
     * @throws java.lang.Exception
     */
    public static boolean pruefen(String p_eigabe, String p_gespeichert, byte[] p_salt) throws Exception{
        String hash = hashen(p_eigabe, p_salt);
        return hash.equals(p_gespeichert);
    }
}
