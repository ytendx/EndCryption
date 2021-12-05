package de.ytendx.endcryption.api.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.function.Consumer;

public class CryptionHandler {

    /**
     * Used Cipher instance saved in String format
     */
    private static final String CIPHER = "RSA";

    /**
     * The final keyPair wich is used for decryption in CryptionHandler#decrypt
     */
    private final KeyPair keyPair;

    /**
     * @param keysize The key size of the key pair created by a KeyPairGenerator
     * @param finishCall will be called with the duration needed for generating the keypair. (Debug purposes)
     * @throws NoSuchAlgorithmException Throwed by KeyPairGenerator#getInstance
     */
    public CryptionHandler(final int keysize, Consumer<Long> finishCall) throws NoSuchAlgorithmException {
        final long bevoreGeneration = System.currentTimeMillis();
        KeyPairGenerator generator = KeyPairGenerator.getInstance(CIPHER);
        generator.initialize((keysize == -1 ? 4048 : keysize));
        this.keyPair = generator.generateKeyPair();
        finishCall.accept(System.currentTimeMillis() - bevoreGeneration);
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    /**
     * This method encrypts a given byte array with the given PublicKey
     * @param content this is the content that gets encrypted with the targetKey
     * @param targetKey this is the key wich is used to encrypt the content
     * @return It returns the encrypted byteArray
     */
    public byte[] encrypt(final byte[] content, final PublicKey targetKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        final Cipher encryptCipher = Cipher.getInstance(CIPHER);
        encryptCipher.init(Cipher.ENCRYPT_MODE, targetKey);
        return encryptCipher.doFinal(content);
    }

    public byte[] decrypt(final byte[] encryptedContent) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        return decryptCipher.doFinal(encryptedContent);
    }
}
