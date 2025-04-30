import java.io.*;
import java.net.*;

public class CaesarCipherClient {

    // Method to encrypt text
    

    public static String encrypt(String text, int shift) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
           char ch = text.charAt(i);
           if ('A' <= ch && ch <= 'Z') {
             // encryptedText.append((char)((((ch - 'A') + shift) % 26) + 'A'));
             encryptedText.append((char) ('A' + (ch - 'A' + shift) % 26));

           } else if ('a' <= ch && ch <= 'z') {
              //encryptedText.append((char)((((ch - 'a') + shift) % 26) + 'a'));
              encryptedText.append((char) ('a' + (ch - 'a' + shift) % 26));

           } else {
              encryptedText.append(ch);
           }
        }
        return encryptedText.toString();
     }

    public static void main(String[] args) {
        String text = "HELLO WORLD";  // Message to send
        int shift = 3;  // Shift for encryption
        String serverAddress = "localhost";
        int port = 500;

        try (Socket socket = new Socket(serverAddress, port)) {
            // Create DataOutputStream to send encrypted text
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            // Encrypt the text
            System.out.println("Sending  Text: " + text);
            String encryptedText = encrypt(text, shift);
            System.out.println("Sending Encrypted Text: " + encryptedText);

            // Send encrypted message
            output.writeUTF(encryptedText);

           
            
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
Encryption Formula

Example 1: Encrypting 'H' with shift = 3
'H' - 'A' = 72 - 65 = 7 (Convert 'H' to a 0-based index)
Add the shift: 7 + 3 = 10
Apply % 26 to handle wrap-around: 10 % 26 = 10
Convert back to character: 'A' + 10 = 'K'
✅ 'H' → 'K'
-----------------------------------------
Example 2: Encrypting 'Y' with shift = 5
'Y' - 'A' = 89 - 65 = 24
Add the shift: 24 + 5 = 29
Apply % 26 to wrap around: 29 % 26 = 3
Convert back to character: 'A' + 3 = 'D'
✅ 'Y' → 'D' (because it wraps around after 'Z')
 */