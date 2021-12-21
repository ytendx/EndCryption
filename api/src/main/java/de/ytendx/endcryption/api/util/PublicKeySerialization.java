package de.ytendx.endcryption.api.util;

import de.ytendx.endcryption.api.encryption.CryptionHandler;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Created with help from https://stackoverflow.com/questions/52384809/public-key-to-string-and-then-back-to-public-key-java/52386057
 */
public class PublicKeySerialization {

    public static PublicKey fromString(String publicKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        byte[] encoded = Base64.getDecoder().decode(publicKey);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static String toString(PublicKey key){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(key.getEncoded());
    }

}
