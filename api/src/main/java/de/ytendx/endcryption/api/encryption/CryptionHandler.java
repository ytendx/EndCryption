package de.ytendx.endcryption.api.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.function.Consumer;

public class CryptionHandler {

    private static final String CIPHER = "RSA";
    private final KeyPair keyPair;

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
