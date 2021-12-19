package de.ytendx.endcryption.api.util;

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
        KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");
        Base64.Decoder decoder = Base64.getDecoder();
        PublicKey decodedPublicKey = keyFactory.generatePublic(new X509EncodedKeySpec(decoder.decode(publicKey)));
        return decodedPublicKey;
    }

    public static String toString(PublicKey key){
        Base64.Encoder encoder = Base64.getEncoder();
        String publicKeyStr = encoder.encodeToString(key.getEncoded());
        return publicKeyStr;
    }

}
