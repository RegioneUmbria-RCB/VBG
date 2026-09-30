package it.alveo.firmaremota.aruba;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class CryptoUtils {

    private static final String ALGORITHM = "PBEWithMD5AndDES";
    // 8-byte Salt
    private static final byte[] SALT = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };
    // Iteration count
    private static final int ITERATIONCOUNT = 19;

    public static String encrypt(String secretKey, String plainText) throws RuntimeException {

	try {
	    KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), CryptoUtils.SALT, CryptoUtils.ITERATIONCOUNT);
	    SecretKey key = SecretKeyFactory.getInstance(CryptoUtils.ALGORITHM).generateSecret(keySpec);
	    AlgorithmParameterSpec paramSpec = new PBEParameterSpec(CryptoUtils.SALT, CryptoUtils.ITERATIONCOUNT);
	    Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
	    ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
	    byte[] in = plainText.getBytes(StandardCharsets.UTF_8);
	    byte[] out = ecipher.doFinal(in);
	    return Base64.getEncoder().encodeToString(out);
	} catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
		| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
	    throw new RuntimeException(e);
	}
    }

    public static String decrypt(String secretKey, String encryptedText) throws RuntimeException {

	try {
	    KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), CryptoUtils.SALT, CryptoUtils.ITERATIONCOUNT);
	    SecretKey key = SecretKeyFactory.getInstance(CryptoUtils.ALGORITHM).generateSecret(keySpec);
	    AlgorithmParameterSpec paramSpec = new PBEParameterSpec(CryptoUtils.SALT, CryptoUtils.ITERATIONCOUNT);
	    Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
	    dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
	    byte[] enc = Base64.getDecoder().decode(encryptedText);
	    byte[] utf8 = dcipher.doFinal(enc);
	    return new String(utf8, StandardCharsets.UTF_8);
	} catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
		| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
	    throw new RuntimeException(e);
	}
    }

    public static void main(String[] args) throws Exception {

	String sk = "smendichi";
	String pt = "sdfds!wefpk4904a'!";
	String newText = CryptoUtils.encrypt(sk, pt);
	System.out.println("Encrypt: " + newText);
	newText = CryptoUtils.decrypt(sk, newText);
	System.out.println("Decrypt: " + newText);
    }
}
