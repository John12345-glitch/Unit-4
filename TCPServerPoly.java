import java.io.*;
import java.net.*;
//Poly Alphabetic Ciphe
public class TCPServerPoly {

    public static void main(String[] args) throws IOException {
        int port = 1234;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server is running on port " + port + "...");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        DataInputStream dis = new DataInputStream(socket.getInputStream());

        String encryptedMsg = dis.readUTF();
        System.out.println("Received Encrypted Message: " + encryptedMsg);

        String key = "KEY"; // Must match client key
        String decryptedMsg = decrypt(encryptedMsg, key);
        System.out.println("Decrypted Message: " + decryptedMsg);

        dis.close();
        socket.close();
        serverSocket.close();
    }

    private static String decrypt(String cipherText, String key) {
        StringBuilder result = new StringBuilder();
        cipherText = cipherText.toUpperCase();
        key = generateKey(cipherText, key);

        for (int i = 0; i < cipherText.length(); i++) {
            char ctChar = cipherText.charAt(i);
            if (Character.isLetter(ctChar)) {
                char ptChar = (char) ((((ctChar - key.charAt(i)) + 26) % 26) + 'A');
                result.append(ptChar);
            } else {
                result.append(ctChar);
            }
        }
        return result.toString();
    }

    private static String generateKey(String text, String key) {
        StringBuilder newKey = new StringBuilder();
        key = key.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            if (Character.isLetter(text.charAt(i))) {
                newKey.append(key.charAt(j));
                j = (j + 1) % key.length();
            } else {
                newKey.append(text.charAt(i));
            }
        }
        return newKey.toString();
    }
}
