package com.paevolution.appioproducer.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;

public class SecurityTools {

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final String ISO_8601_REGEXP = "^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$";
    private final Pattern DATE_PATTERN = Pattern.compile(ISO_8601_REGEXP);
    private static final Random RANDOM = new SecureRandom();
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String generateMessageId(int length) {

	return generateRandomString(length);
    }

    public static String generateAlphanumericString(int length) {

	return RandomStringUtils.randomAlphabetic(length);
    }
    /*
     * TODO: Usare il GUID di Java per generare il messageId
     */

    public static byte hexToByte(String hexString) {

	int firstDigit = Character.digit(hexString.charAt(0), 16);
	int secondDigit = Character.digit(hexString.charAt(1), 16);
	return (byte) ((firstDigit << 4) + secondDigit);
    }

    public static UUID generateType4UUID() {

	return UUID.randomUUID();
    }

    public static UUID generateType5UUID(String namespace, String name) throws UnsupportedEncodingException {

	byte[] nameSpacebBytes = bytesFromUUID(namespace);
	byte[] nameBytes = name.getBytes("UTF-8");
	byte[] result = joinBytes(nameSpacebBytes, nameBytes);
	return type5UUIDFromBytes(result);
    }

    /**
     * 
     * @param uuidHexString
     * @return
     */
    public static byte[] bytesFromUUID(String uuidHexString) {

	String normalizedUUIDString = uuidHexString.replace("-", "");
	assert normalizedUUIDString.length() == 32;
	byte[] bytes = new byte[16];
	for (int i = 0; i < 16; i++) {
	    byte b = hexToByte(normalizedUUIDString.substring(i * 2, i * 2 + 2));
	    bytes[i] = b;
	}
	return bytes;
    }

    public static byte[] joinBytes(byte[] byteArray1, byte[] byteArray2) {

	int finalLength = byteArray1.length + byteArray2.length;
	byte[] joinedArray = new byte[finalLength];
	//
	//
	for (int i = 0; i < byteArray1.length; i++) {
	    joinedArray[i] = byteArray1[i];
	}
	for (int i = 0; i < byteArray2.length; i++) {
	    joinedArray[byteArray1.length + i] = byteArray2[i];
	}
	return joinedArray;
    }

    public static Byte[] mergeBytes(Byte[]... byteArray) {

	return Stream.of(byteArray).flatMap(Stream::of).toArray(Byte[]::new);
    }

    public static UUID type5UUIDFromBytes(byte[] name) {

	MessageDigest md;
	try {
	    md = MessageDigest.getInstance("SHA-1");
	} catch (NoSuchAlgorithmException ex) {
	    throw new InternalError("SHA-1", ex);
	}
	byte[] bytes = Arrays.copyOfRange(md.digest(name), 0, 16);
	bytes[6] &= 0x0f; /* clear version */
	bytes[6] |= 0x50; /* set to version 5 */
	bytes[8] &= 0x3f; /* clear variant */
	bytes[8] |= 0x80; /* set to IETF variant */
	return constructType5UUID(bytes);
    }

    public static UUID constructType5UUID(byte[] data) {

	long msb = 0;
	long lsb = 0;
	assert data.length == 16 : "data must be 16 bytes in length";
	for (int i = 0; i < 8; i++) {
	    msb = (msb << 8) | (data[i] & 0xff);
	}
	for (int i = 8; i < 16; i++) {
	    lsb = (lsb << 8) | (data[i] & 0xff);
	}
	return new UUID(msb, lsb);
    }

    private static String generateRandomString(int length) {

	StringBuilder alphanumericBuilder = new StringBuilder(length);
	for (int i = 0; i < length; i++) {
	    alphanumericBuilder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
	}
	return new String(alphanumericBuilder);
    }
}
