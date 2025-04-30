import java.io.*;
import java.net.*;

public class SequentialClient {
    public static String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int shift = i + 1;
                ch = (char) (base + (ch - base + shift) % 26);
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

        dos.writeUTF(encrypted);  // Send encrypted message to server

        String decrypted = dis.readUTF();  // Receive decrypted message
        System.out.println("Original Message: " + message);
        System.out.println("Encrypted Sent: " + encrypted);
        System.out.println("Decrypted from Server: " + decrypted);

        dis.close();
        dos.close();
        socket.close();
    }
}
