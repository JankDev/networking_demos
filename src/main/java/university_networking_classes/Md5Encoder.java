package university_networking_classes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Encoder {

    public static String encodeMd5(String text) {
        try {
            var md5 = MessageDigest.getInstance("MD5");
            var encoded = md5.digest(text.getBytes());

            var hash = new BigInteger(1, encoded);

            return hash.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        if (args.length < 1) {
            System.err.printf("Usage %s <text>", Md5Encoder.class.getName());
        }
        String encoded = encodeMd5(args[0]);
        System.out.println(encoded);

    }


}
