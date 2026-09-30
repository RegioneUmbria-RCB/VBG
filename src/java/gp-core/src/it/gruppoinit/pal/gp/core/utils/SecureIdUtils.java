package it.gruppoinit.pal.gp.core.utils;

public class SecureIdUtils {

    private static int LENGTH = 20;

    public static String encode(Integer id, String publicKey, String altraChiave) {

	if (id == null) {
	    return null;
	}
	StringBuilder sb = new StringBuilder("");
	while (sb.length() < SecureIdUtils.LENGTH - id.toString().length()) {
	    sb.append("0");
	}
	sb.append(id.toString());
	sb.append(publicKey);
	Integer hash = sb.toString().hashCode();
	return id.toString() + "." + altraChiave + "." + hash.toString();
    }
}
