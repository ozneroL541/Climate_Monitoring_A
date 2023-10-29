package src.cryptography;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.util.Base64;
/**
 * Advanced Encryption Standard
 * Classe di cifratura e decifratura tramite algoritmo AES.
 * @author Lorenzo Radice
 * @version 0.0.1
 */
public class AES {
    // Algorithm
    private static final String ALGORITHM = "AES";
    /**
     * Algoritmo di cifratura AES
     * @param value testo in chiaro
     * @param password password
     * @return testo cifrato
     * @throws Exception Eccezioni che possono giungere tramite l'utilizzo dei metodi di cifratura
     */
    public static String encrypt(String value, String password) throws Exception {
        // Cipher key
        SecretKeySpec key = new SecretKeySpec(extendString(password).getBytes(), ALGORITHM);
        // Cipher to be used
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // Encryption
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // Encrypted text
        byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        // Return encryption
        return Base64.getEncoder().encodeToString(encryptedByteValue);
    }
    /**
     * Algoritmo di decifratura AES
     * @param value testo cifrato
     * @param password password
     * @return testo in chiaro
     * @throws Exception Eccezioni che possono giungere tramite l'utilizzo dei metodi di cifratura
     */
    public static String decrypt(String value, String password) throws Exception {
        // Cipher key
        SecretKeySpec key = new SecretKeySpec(extendString(password).getBytes(), ALGORITHM);
        // Cipher to be used
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // Decryption
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.getDecoder().decode(value);
        byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
        // Return decryption
        return new String(decryptedByteValue,"utf-8");
    }
    /*
     * Controlla che la chiave sia accettabile
     * @param keyBytes chiave in bytes
     * @return true se la chiave è valida
     */
    public static boolean isKeyValid(byte[] keyBytes) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return true;
        } catch (InvalidKeyException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    /*
     * Estende la password perché abbia la lunghezza corretta.
     * @param str password
     * @return la stringa con lunghezza corretta
     */
    private static String extendString(String str) {
        // Correct lenght 
        final short length = 16;
        String r = null;
        Character c;
        if ( str == null || str.isEmpty() || str.length() <= 0 ) {
            return "";
        } else {
            r = str;
            while ( r.length() < length ) {
                r += r;
            }
            if ( r.length() > length ) {
                c = r.charAt(r.length()-1);
                r = r.charAt(length) + c.toString() + r.substring(2, length );
            }
            return r;
        }
    }
}

