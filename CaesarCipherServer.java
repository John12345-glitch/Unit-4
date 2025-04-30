import java.io.*;
import java.net.*;

public class CaesarCipherServer {

    // Method to decrypt text
    public static String decrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                ch = (char) (base + (ch - base - shift + 26) % 26);
            }
            result.append(ch);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        int shift = 3;  // Shift for decryption
        int port = 500; // Port number

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is waiting for a connection...");

            Socket socket = serverSocket.accept();  // Accept client connection
            System.out.println("Client connected!");

            // Create DataInputStream to receive encrypted text
            DataInputStream input = new DataInputStream(socket.getInputStream());
            String encryptedText = input.readUTF(); // Read encrypted text
            System.out.println("Received Encrypted Text: " + encryptedText);

            // Decrypt the text
            String decryptedText = decrypt(encryptedText, shift);
            System.out.println("Decrypted Text: " + decryptedText);

            // Send acknowledgment to client
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF("Message received and decrypted: " + decryptedText);

            // Close resources
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
 Explanation:
  Decryption Formula

ch = (char) (base + (ch - base - shift + 26) % 26);
👉 This shifts the letter backward in the alphabet.
Example 3: Decrypting 'K' with shift = 3
'K' - 'A' = 75 - 65 = 10
Subtract the shift: 10 - 3 = 7
Apply % 26 to wrap around: (7 + 26) % 26 = 7
Convert back to character: 'A' + 7 = 'H'
✅ 'K' → 'H'
___________________________________________________
Example 4: Decrypting 'D' with shift = 5
'D' - 'A' = 68 - 65 = 3
Subtract the shift: 3 - 5 = -2
Apply % 26 to handle negative index: (-2 + 26) % 26 = 24
Convert back to character: 'A' + 24 = 'Y'
✅ 'D' → 'Y' (because it wrapped backward before 'A')
 */