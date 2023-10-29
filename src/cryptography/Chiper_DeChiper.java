package src.cryptography;

/**
 * Classe per cifrare.
 * @see src.cryptography.AES
 * @author Lorenzo Radice
 * @version 0.0.1
 */
public class Chiper_DeChiper {
    /**
     * Cifra ogni singola stringa del record con la password.
     * @param record record
     * @param password password
     * @return record cifrato
     */
    public static String[] recordCipher( String[] record, String password ) {
        final int length = record.length;
        String[] out = new String[record.length];
        for ( int i = 0 ; i < length; i++ ) {
            try {
                out[i] = AES.encrypt(record[i], password);
            } catch (Exception e) {
                out[i] = "";
                e.printStackTrace();
            }
        }
        return out;
    }
    /**
     * Ritorna un record cifrato.
     * L'indice n viene si riferisce alla posizione nell'array contenente la password.
     * La posizione contente la password non risulterà nell'array risultante.
     * @param record record
     * @param n indice
     * @return
     */
    public static String[] recordCipher_pw( String[] record, int n ) {
        final int length = record.length;
        String password = record[n];
        String[] out = new String[ record.length - 1 ];
        int j =  0;
        for ( int i = 0 ; i < length; i++ ) {
            if ( i == n ) {
                i++;
            }
            try {
                out[j] = AES.encrypt(record[i], password);
            } catch (Exception e) {
                out[j] = "";
                //e.printStackTrace();
            }
            j++;
        }
        return out;
    }
    /**
     * Decifra un record.
     * @param record record
     * @param pw password
     * @return record decifrato
     */
    public static String[] deCipher_Record( String [] record, String pw ) {
        final int length = record.length;
        String[] out = new String[record.length];
        for ( int i = 0 ; i < length; i++ ) {
            try {
                out[i] = AES.decrypt(record[i], pw);
            } catch (Exception e) {
                out[i] = "";
                e.printStackTrace();
            }
        }
        return out;
    }
}
