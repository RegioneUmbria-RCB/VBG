/**
 * 
 */
package it.gruppoinit.pal.gp.core.utils;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author francol Classe di utilità per operazioni di codifica/decodifica con chiave segreta
 */
public class CryptoUtils {

    public static final String DEFAULT_SECRET_KEY = "$1REDACTED";
    public static final Logger log = LoggerFactory.getLogger(CryptoUtils.class);
    Cipher ecipher;
    Cipher dcipher;
    // 8-byte Salt
    byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };
    // Iteration count
    int iterationCount = 19;

    public CryptoUtils() {

    }

    /**
     *
     * @param secretKey
     *            Key used to encrypt data
     * @param plainText
     *            Text input to be encrypted
     * @return Returns encrypted text
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.io.UnsupportedEncodingException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     *
     */
    public String encrypt(String secretKey, String plainText) {

	try {
	    //Key generation for enc and desc
	    KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
	    SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
	    // Prepare the parameter to the ciphers
	    AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
	    //Enc process
	    ecipher = Cipher.getInstance(key.getAlgorithm());
	    ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
	    String charSet = "UTF-8";
	    byte[] in = plainText.getBytes(charSet);
	    byte[] out = ecipher.doFinal(in);
	    //String encStr = new String(Base64.getEncoder().encode(out));
	    String encStr = new String(Base64.encodeBase64(out));
	    return encStr;
	} catch (Exception e) {
	    String msg = "errore nella codifica della stringa " + plainText + " con la chiave segreta";
	    log.error("encrypt - " + msg, e);
	    throw new RuntimeException(msg, e);
	}
    }

    /**
     * @param secretKey
     *            Key used to decrypt data
     * @param encryptedText
     *            encrypted text input to decrypt
     * @return Returns plain text after decryption
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws java.io.UnsupportedEncodingException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     */
    public String decrypt(String secretKey, String encryptedText) {

	try {
	    //Key generation for enc and desc
	    KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
	    SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
	    // Prepare the parameter to the ciphers
	    AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
	    //Decryption process; same key will be used for decr
	    dcipher = Cipher.getInstance(key.getAlgorithm());
	    dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
	    //byte[] enc = Base64.getDecoder().decode(encryptedText);
	    byte[] enc = Base64.decodeBase64(encryptedText);
	    byte[] utf8 = dcipher.doFinal(enc);
	    String charSet = "UTF-8";
	    String plainStr = new String(utf8, charSet);
	    return plainStr;
	} catch (Exception e) {
	    String msg = "errore nella decodifica della stringa " + encryptedText + " con la chiave segreta";
	    log.error("decrypt - " + msg, e);
	    throw new RuntimeException(msg, e);
	}
    }

    public static void main(String[] args) throws Exception {

	CryptoUtils cryptoUtil = new CryptoUtils();
	String enc = "";
	String plainAfter = cryptoUtil.decrypt(DEFAULT_SECRET_KEY, enc);
	System.out.println("Original text after decryption: " + plainAfter);
	enc = cryptoUtil.encrypt(DEFAULT_SECRET_KEY, plainAfter);
	System.out.println("password: [" + enc + "]");
    }
}
