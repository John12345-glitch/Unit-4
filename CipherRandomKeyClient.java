
//Mono Alphabetic Cipher RandomKey
import java.io.*;
import java.net.*;
import java.util.Random;

public class CipherRandomKeyClient {
    static int[] key;

    public static String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        Random rand = new Random();
        key = new int[text.length()];

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int shift = rand.nextInt(25) + 1;
                key[i] = shift;
                ch = (char) (base + (ch - base + shift) % 26);
            } else {
                key[i] = 0; // No shift for non-letters
            }
            result.append(ch);
        }
        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        String message = "HELLO";
        String encrypted = encrypt(message);

        dos.writeUTF(encrypted);        // Send encrypted message
        dos.writeInt(key.length);       // Send key length
        for (int k : key) {
            dos.writeInt(k);           // Send key values one by one
        }

        String serverResponse = dis.readUTF(); // Decrypted message from server
        System.out.println("Original: " + message);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted from Server: " + serverResponse);

        dis.close();
        dos.close();
        socket.close();
    }
}
