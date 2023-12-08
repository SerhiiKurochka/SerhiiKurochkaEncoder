package local.serhiikurochka;

import java.util.*;
import java.util.stream.Collectors;

public class CaesarCipher {
    private static final String ALPHABET_EN = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\n\r\t */\\$%&#@!(){}[],;'”<>|^~.`-";
    private static final String ALPHABET_UA = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюя\n\r\t */\\$%&#@!(){}[],;'”<>|^~.`";

    private static final List<String> WORDS_EN = List.of("i", "am", "are", "you", "he", "she", "it", "we", "they",
            "and", "me", "him", "her", "us", "them", "my", "your", "his", "her", "out", "its", "our", "their", "all", "every", "any",
            "some", "other", "at", "to", "for", "with", "without", "don't", "do");
    private static final List<String> WORDS_UA = List.of("і", "та", "я", "ми", "або", "в", "у", "на", "тобто",
            "сьогодні", "тобі", "йому", "вона", "він", "йому", "їх", "їм", "до");

    private String encrypt(String content, int key) {
        String alphabet = getAlphabet(content);
        int offset;
        char[] charArray = content.toCharArray();
        int size = alphabet.length();
        List<Character> charEncrypted = new ArrayList<>();
        for (char charContent : charArray) {
            int index = alphabet.indexOf(charContent);
            if (index != -1) {
                if ((index + key) >= size) {
                    offset = index + key - size;
                } else if (index + key < 0) {
                    offset = index + key + size;
                } else {
                    offset = index + key;
                }
                charEncrypted.add(alphabet.charAt(offset));
            } else {
                charEncrypted.add(charContent);
            }
        }
        return charEncrypted.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    private String getAlphabet(String content) {
        if (isUA(content)) {
            return ALPHABET_UA;
        }
        return ALPHABET_EN;
    }

    private boolean isUA(String content) {
        for (int i = 0; i < content.length(); i++) {
            if (Character.UnicodeBlock.of(content.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return true;
            }
        }
        return false;
    }

    private String decrypt(String content, int offset) {
        return encrypt(content, -offset);
    }


    public void encryptFile(String filePath, int key) {
        FileService fileService = new FileService();
        String outputFilePath = filePath.substring(0, filePath.length() - 4) + "[ENCRYPTED]" + filePath.substring(filePath.length() - 4);
        String content = fileService.read(filePath);
        String encryptedContent = encrypt(content, key);
        fileService.write(outputFilePath, encryptedContent);
    }

    public void decryptFile(String filePath, int key) {
        FileService fileService = new FileService();
        String outputFilePath = filePath.substring(0, filePath.length() - 4) + "[DECRYPT]" + filePath.substring(filePath.length() - 4);
        String content = fileService.read(filePath);
        String decryptedContent = decrypt(content, key);
        fileService.write(outputFilePath, decryptedContent);
    }

    public int bruteForceFile(String path) {
        FileService fileService = new FileService();
        return bruteForce(fileService.read(path));
    }

    private int bruteForce(String encryptedContent) {
        List<String> words;
        String alphabet;
        if (isUA(encryptedContent)) {
            alphabet = ALPHABET_UA;
            words = WORDS_UA;
        } else {
            alphabet = ALPHABET_EN;
            words = WORDS_EN;
        }

        HashMap<Integer, Integer> result = new HashMap<>();
        int offset;
        String decryptedContent = "";
        for (offset = 0; offset < alphabet.length(); offset++) {
            decryptedContent = decrypt(encryptedContent, offset).toLowerCase();
            List<String> splited = List.of(decryptedContent.split(" "));
            int n = 0;
            for (String word : words) {
                if (splited.contains(word)) {
                    n++;
                }

            }
            if (n > 0) {
                result.put(offset, n);
            }
        }

        return Collections.max(result.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
