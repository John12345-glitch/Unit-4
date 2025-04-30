import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClientPoly {

    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 1234;

        Socket socket = new Socket(host, port);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the message to send: ");
        //String message = scanner.nextLine();
        String message = "HELLO WORLD";
        String key = "KEY"; // Must match server key
        String encryptedMsg = encrypt(message, key);
        System.out.println("Encrypted Message: " + encryptedMsg);

        dos.writeUTF(encryptedMsg);

        dos.close();
        socket.close();
        scanner.close();
    }

    private static String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase();
        key = generateKey(text, key);

        for (int i = 0; i < text.length(); i++) {
            char ptChar = text.charAt(i);
            if (Character.isLetter(ptChar)) {
                char ctChar = (char) (((ptChar + key.charAt(i)) % 26) + 'A');
                result.append(ctChar);
            } else {
                result.append(ptChar);
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


/*📌 Step 1: Plaintext: HELLO WORLD
Key: KEY
We repeat the key to match the message letters:

Plaintext	H	E	L	L	O		W	O	R	L	D
Key	        K	E	Y	K	E		Y	K	E	Y	K
(NOTE: spaces remain unchanged)

📌 Step 2: Encrypt
Apply:pgsql

Encrypted = (PlainTextChar + KeyChar) % 26 + 'A'
Now letter by letter:

H + K = (7 + 10) % 26 = 17 → R

E + E = (4 + 4)%26 = 8 → I

L + Y = (11 + 24) = 35 % 26 = 9 → J

L + K = 21 → V

O + E = 18 → S

(space stays as is)

W + Y = (22 + 24) = 46 → 20 → U

O + K = 24 → Y

R + E = 21 → V

L + Y = 35 → 9 → J

D + K = 13 → N

🔐 Encrypted Message: RIJVS UYVJN

⚙️ Step-by-step Flow of Program:
🔸 Client:
You type: HELLO WORLD

It uses key KEY

It encrypts to RIJVS UYVJN

Sends this string to the server using DataOutputStream.writeUTF()

🔸 Server:
Receives encrypted message: RIJVS UYVJN using DataInputStream.readUTF()

Decrypts using key KEY

Outputs: HELLO WORLD */