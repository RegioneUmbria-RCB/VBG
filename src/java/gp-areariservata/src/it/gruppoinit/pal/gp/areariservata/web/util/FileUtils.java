package it.gruppoinit.pal.gp.areariservata.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtils {

    private static final String MAC_QUERY_STRING_PARAM = "&_MAC_=";
    private static final String CHIAVE_SEGRETA = "SECRET_" + System.currentTimeMillis();

    public static String getLinkForFile(String queryString) {

	String mac = encode(queryString + CHIAVE_SEGRETA);
	return queryString + MAC_QUERY_STRING_PARAM + mac;
    }

    public static boolean verificaLinkFile(String queryStringWithMAC) {

	String qsOrig = queryStringWithMAC.substring(0, queryStringWithMAC.indexOf(MAC_QUERY_STRING_PARAM));
	String mac = encode(qsOrig + CHIAVE_SEGRETA);
	String macQs = queryStringWithMAC.substring(queryStringWithMAC.indexOf(MAC_QUERY_STRING_PARAM)).replace(MAC_QUERY_STRING_PARAM, "");
	return macQs.equalsIgnoreCase(mac);
    }

    private static String encode(String toEncode) {

	try {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update((toEncode).getBytes());
	    byte[] out = md.digest();
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < out.length; i++) {
		sb.append(Integer.toString((out[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    return sb.toString();
	} catch (NoSuchAlgorithmException e) {
	    throw new RuntimeException(e);
	}
    }

    public static void main(String[] args) {

	String qs = getLinkForFile("codiceOggetto=300&_ts=30000");
	System.out.println(qs);
	qs = getLinkForFile("codiceOggetto=300&_ts=30001");
	System.out.println(qs);
	System.out.println(verificaLinkFile("codiceOggetto=301&_ts=30000&_MAC_=b0f580a5927a7363c4d32ba2ef976edd"));
    }
}
