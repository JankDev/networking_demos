package university_networking_classes;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

public class DesEncryption {
    private final static String CRYPTO_ALGORITHM = "DES";
    private final static String CRYPTO_MODE = "CBC";
    private final static String CRYPTO_PADDING = "PKCS5Padding";
    private final static byte[] IV = {11, 22, 33, 44, 99, 88, 77, 66};

    private final Cipher encryptCipher = Cipher.getInstance(String.format("%s/%s/%s", CRYPTO_ALGORITHM, CRYPTO_MODE, CRYPTO_PADDING)); // ENCRYPT CIPHER
    private final Cipher decryptCipher = Cipher.getInstance(String.format("%s/%s/%s", CRYPTO_ALGORITHM, CRYPTO_MODE, CRYPTO_PADDING)); // DECRYPT CIPHER
    private final SecretKey key = KeyGenerator.getInstance(CRYPTO_ALGORITHM).generateKey();
    private final AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV);

    public DesEncryption() throws Exception {
        this.encryptCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        this.decryptCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
    }

    private String encrypt(String text) throws BadPaddingException, IllegalBlockSizeException {
        var encrypted = encryptCipher.doFinal(text.getBytes()); // ENCRYPT
        var encoded = Base64.getEncoder().encode(encrypted); // ENCODE
        return new String(encoded);
    }

    private String decrypt(String text) throws Exception {
        var decodedBytes = Base64.getDecoder().decode(text.getBytes()); // DECODE
        var decrypted = decryptCipher.doFinal(decodedBytes); // DECRYPT
        return new String(decrypted);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.printf("Usage: java %s.java <text>\n", DesEncryption.class.getSimpleName());
            return;
        }
        var des = new DesEncryption();
        String message = args[0];
        String encoded = des.encrypt(message);
        System.out.println("Encoded: " + encoded);
        System.out.println("Decoded: " + des.decrypt(encoded));
    }
}