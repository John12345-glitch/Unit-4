import java.io.*;
import java.net.*;

public class SequentialServer {
    public static String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int shift = i + 1;
                ch = (char) (base + (ch - base - shift + 26) % 26);
            }
            result.append(ch);
        }
        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5000);
        System.out.println("Server started... Waiting for client.");

        Socket socket = server.accept();
        System.out.println("Client connected.");

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        String encrypted = dis.readUTF();  // Receive encrypted message
        String decrypted = decrypt(encrypted);

        dos.writeUTF(decrypted);  // Send decrypted message back

        dis.close();
        dos.close();
        socket.close();
        server.close();
    }
}
