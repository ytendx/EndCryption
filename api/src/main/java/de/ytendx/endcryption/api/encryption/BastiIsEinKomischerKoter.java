package de.ytendx.endcryption.api.encryption;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class BastiIsEinKomischerKoter{

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        String secretMessage = "Baeldung secret message";
        System.out.println("Started! Initializing generator...");
        long current = System.currentTimeMillis();
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(4048);
        System.out.println("Successfully initialized!");
        KeyPair pair = generator.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        keyFactory.generatePublic(publicKeySpec);
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        System.out.println("Du some stuff.....");
        byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        System.out.println("Uncrypted: " + secretMessage);
        System.out.println("Encrypted: " + encodedMessage);
        String s = new String(decryptCipher.doFinal(encryptedMessageBytes));
        System.out.println("Decrypted: " + s);
        System.out.println("Needed " + (System.currentTimeMillis()-current) + "ms");
    }

}
