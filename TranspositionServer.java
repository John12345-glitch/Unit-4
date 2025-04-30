import java.io.*;
import java.net.*;

public class TranspositionServer {

    public static String decrypt(String cipher, int key) {
        int length = cipher.length();
        int rows = length / key;
        int extra = length % key;
        int[] colLens = new int[key];

        for (int i = 0; i < key; i++) {
            colLens[i] = rows + (i < extra ? 1 : 0);
        }

        String[] columns = new String[key];
        int index = 0;
        for (int i = 0; i < key; i++) {
            columns[i] = cipher.substring(index, index + colLens[i]);
            index += colLens[i];
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows + 1; i++) {
            for (int j = 0; j < key; j++) {
                if (i < columns[j].length()) {
                    result.append(columns[j].charAt(i));
                }
            }
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

        int key = dis.readInt();            // Receive key
        String encrypted = dis.readUTF();   // Receive encrypted text

        String decrypted = decrypt(encrypted, key);

        dos.writeUTF(decrypted);            // Send decrypted text

        dis.close();
        dos.close();
        socket.close();
        server.close();
    }
}
