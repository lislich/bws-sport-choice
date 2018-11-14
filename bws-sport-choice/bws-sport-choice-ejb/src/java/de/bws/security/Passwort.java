/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bws.security;

import de.bws.entities.Benutzer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


/**
 *
 * @author joshua
 */
public class Passwort {
    
    private static final int ITERATIONEN = 32;
    private static final int LAENGESALT = 64;
    private static final int LAENGEKEY = 128;
    private static final String ALGORITHMUS = "PBKDF2WithHmacSHA1";
    
    /**
     * Generiert ein Array mit zufälligen Bytes als Salt. Die Länge des Salt 
     * ergibt sich aus der Konstante "LAENGESALT".
     * 
     * @author Joshau
     * @return Das Byte-Array
     */
    public static byte[] saltGenerieren(){
        SecureRandom random = new SecureRandom();
        byte salt[] = new byte[LAENGESALT];
        random.nextBytes(salt);
        return salt;
    }
    
    /**
     * 
     * 
     * @author Joshau
     * @param p_passwort 
     * @param p_salt Das Salt zum verschlüsseln des Passworts
     * @return
     * @throws Exception 
     */
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
     * @param p_benutzer der Benutzer, der eingeloggt werden soll
     * @param p_passwort das zu prüfende Passwort
     * @return true - Passwörter stimmen überein, false - Passwörter stimmen nicht überein
     * @throws java.lang.Exception
     */
    public static boolean pruefen(Benutzer p_benutzer, String p_passwort) throws Exception{
        if(p_passwort != null && p_passwort.length() > 0 && p_benutzer != null){
            String hash = hashen(p_passwort, p_benutzer.getSalt());
            return hash.equals(p_benutzer.getPasswort());
        }
        return false;
    }
    
    /**
     * Methode zum generieren eines Standardpassworts. 
     * 
     * @return das geneirerte Passwort
     * @throws java.security.NoSuchAlgorithmException
     */
    public static String passwortGenerieren() throws NoSuchAlgorithmException {
        String[] symbole = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "A", "B", "C", "D", "E", "F"};
        int length = 10;
//        Random random = SecureRandom.getInstanceStrong();
             
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
//            int indexRandom = random.nextInt( symbole.length );
            int indexRandom = ThreadLocalRandom.current().nextInt(0, symbole.length);
            sb.append( symbole[indexRandom] );
        }
            
        String neuesPasswort = sb.toString();
        return neuesPasswort;
    }
}
