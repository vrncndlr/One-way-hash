import java.math.BigInteger;

public class CountBits{
    public static void main(String[] args) {
        if (args.length < 4 || !args[0].equals("--h1") || !args[2].equals("--h2")) {
            System.out.println("Usage: java CountBits --h1 <value> --h2 <value>");
            return;
        }
//        java CountBits --h1 fd0ea8c886aee21ec738b9ca0e29c03df7498fbeff361dab7735efc59525ce74 --h2 72c4200b3d942882b6ef2b1f3bee08d25dbc18d3daac53ab3ba4bea62743d8e6

        String h1 = args[1];
        String h2 = args[3];

        String h1Binary = hexToBinary(h1);
        String h2Binary = hexToBinary(h2);

        System.out.println("h1: " + h1Binary);
        System.out.println();
        System.out.println("h2: " + h2Binary);
        System.out.println();
        System.out.println("number of common bits: " + countBits(h1Binary, h2Binary));

    }
    private static String hexToBinary(String hexString) {
        BigInteger bigInteger = new BigInteger(hexString, 16);
        String binary = bigInteger.toString(2);

        int paddingLength = hexString.length() * 4 - binary.length(); // 1 hex=4 binary 128/256
        if (paddingLength > 0) {
            binary = "0".repeat(paddingLength) + binary;
        }
        return binary;
    }
    private static int countBits(String h1, String h2){
        int commonBits = 0;
        for(int i=0; i<h1.length(); i++){
            if(h1.charAt(i)==h2.charAt(i)){
                commonBits++;
            }
        }
        return commonBits;
    }
}