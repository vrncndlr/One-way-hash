import java.security.*;
import java.lang.Math;

public class CollisionResistance {

    public static void main(String[] args) {
        String digestAlgorithm = "SHA-256";
        String textEncoding = "UTF-8";

        String input = "IV1013 security";
        String input2 = "Security is fun";
        String input3 = "Yes, indeed";
        String input4 = "Secure IV1013";
        String input5 = "No way";

        findCollision(input, input.length());
        findCollision(input2, input2.length());
        findCollision(input3, input3.length());
        findCollision(input4, input4.length());
        findCollision(input5, input5.length());


    }
    public static void printDigest(String inputText, String algorithm, byte[] digest) {
        System.out.println("Digest for the message " + inputText +", using " + algorithm + " is:");
        for (int i=0; i<digest.length; i++)
            System.out.format("%02x", digest[i]&0xff);
        System.out.println();
    }
    public static byte[] createDigest(String input){
        try{
            byte[] bytes = input.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes);
            return md.digest();
        }catch (NoSuchAlgorithmException e) {
            System.out.println("Algorithm not available: " + e.getMessage());
        }
        return null;
    }

    public static int trimDigest(byte[] digest) {
        int trimmed = (digest[0] & 0xFF) << 16 | (digest[1] & 0xFF) << 8 | (digest[2] & 0xFF); //extract the first 3 bytes of the hash
        return trimmed & 0xFFFFFF; //keep only the first 24 bits
    }

    public static String generateRandomString(int length) {
        String chars = "!\"#€%&\'()*+,-./:;<=>?@[\\]_{|}~ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public static void findCollision(String msg, int len){
        byte[] hash = createDigest(msg);
        boolean noMatch = true;
        int count = 0;
        String newMsg = "";
        byte[] newHash = new byte[(msg.length()/4)];
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            while(noMatch){
                newMsg = generateRandomString(len);
                newHash = createDigest(newMsg);
                int trimmedHash1 = trimDigest(hash);
                int trimmedHash2 = trimDigest(newHash);
                if(trimmedHash1==trimmedHash2){
                    noMatch = false;
                    System.out.println("Original string: " + msg);
                    System.out.println("Collision found after " + count + " iterations:");
                    System.out.println("New string: " + newMsg);
                    printDigest(newMsg,"SHA-256", newHash);
                    break;
                }
            count++;
            }
        }catch (NoSuchAlgorithmException e) {
            System.out.println("Algorithm not available: " + e.getMessage());
        }
    }

}