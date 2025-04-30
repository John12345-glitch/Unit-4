import java.io.*;
import java.net.*;

public class TranspositionClient {

    public static String encrypt(String text, int key) {
        text = text.replaceAll("\\s+", "").toUpperCase();
        StringBuilder[] columns = new StringBuilder[key];

        for (int i = 0; i < key; i++) {
            columns[i] = new StringBuilder();
        }

        for (int i = 0; i < text.length(); i++) {
            columns[i % key].append(text.charAt(i));
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder col : columns) {
            result.append(col);
        }

        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        String message = "WE ARE DISCOVERED RUN";
        int key = 4;

        String encrypted = encrypt(message, key);

        dos.writeInt(key);           // Send key
        dos.writeUTF(encrypted);     // Send encrypted message

        String decrypted = dis.readUTF(); // Receive original text from server

        System.out.println("Original Message: " + message);
        System.out.println("Encrypted Sent: " + encrypted);
        System.out.println("Decrypted from Server: " + decrypted);

        dis.close();
        dos.close();
        socket.close();
    }
}
