package local.serhiikurochka;

import local.serhiikurochka.ui.MainForm;

/**
 * @author Serhii Kurochka
 * java -jar myApp.jar command filePath key
 * [ENCRYPT, DECRYPT, BRUTE_FORCE]
 */
public class Runner {
    public static void main(String[] args) {
        CaesarCipher caesarCipher = new CaesarCipher();
        String filePath;
        int key;
        if (args.length == 0) {
            new MainForm();
        } else if (args[0].equalsIgnoreCase("ENCRYPT")) {
            filePath = args[1];
            key = Integer.parseInt(args[2]);
            caesarCipher.encryptFile(filePath, key);
        } else if (args[0].equalsIgnoreCase("DECRYPT")) {
            filePath = args[1];
            key = Integer.parseInt(args[2]);
            caesarCipher.decryptFile(filePath, key);
        } else if (args[0].equalsIgnoreCase("BRUTE_FORCE")) {
            filePath = args[1];
            System.out.println("Encryption key is " + caesarCipher.bruteForceFile(filePath));
        } else {
            System.out.println("Unknown command");
        }

    }
}
