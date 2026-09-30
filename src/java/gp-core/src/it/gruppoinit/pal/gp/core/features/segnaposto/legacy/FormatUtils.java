package it.gruppoinit.pal.gp.core.features.segnaposto.legacy;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

public class FormatUtils {

    private static final Pattern NON_ASCII_REGEX_PATTERN = Pattern.compile("[^\\p{ASCII}]");

    private FormatUtils() {

    }

    private static Map<String, String> myMap;
    static {
	Map<String, String> aMap = new HashMap<String, String>();
	aMap.put("`", "\\'60");
	aMap.put(",,", "\\'84");
	aMap.put("...", "\\'85");
	aMap.put("†", "\\'86");
	aMap.put("‡", "\\'87");
	aMap.put("∘", "\\'88");
	aMap.put("‰", "\\'89");
	aMap.put("‹", "\\'8b");
	aMap.put("‘", "\\'91");
	aMap.put("’", "\\'92");
	aMap.put("“", "\\'93");
	aMap.put("”", "\\'94");
	aMap.put("–", "\\'96");
	aMap.put("—", "\\'97");
	aMap.put("™", "\\'99");
	aMap.put("¦", "\\'a6");
	aMap.put("§", "\\'a7");
	aMap.put("¨", "\\'a8");
	aMap.put("©", "\\'a9");
	aMap.put("«", "\\'ab");
	aMap.put("¬", "\\'ac");
	aMap.put("(-)", "\\-");
	aMap.put("®", "\\'ae");
	aMap.put("¯", "\\'af");
	aMap.put("°", "\\'b0");
	aMap.put("±", "\\'b1");
	aMap.put("²", "\\'b2");
	aMap.put("³", "\\'b3");
	aMap.put("´", "\\'b4");
	aMap.put("·", "\\'b7");
	aMap.put("¸", "\\'b8");
	aMap.put("º", "\\'ba");
	aMap.put("»", "\\'bb");
	aMap.put("¿", "\\'bf");
	aMap.put("ß", "\\'df");
	// myMap = Collections.unmodifiableMap(aMap);
	myMap = aMap;
    }

    public static String stringFormat(String value) {

	return stringFormat(value, false);
    }

    public static String escapeUnicodeSpecialChars(String value) {

	for (Entry<String, String> entry : myMap.entrySet()) {
	    String k = entry.getKey();
	    if (value.indexOf(k) >= 0) {
		value = value.replace(k, entry.getValue());
	    }
	}
	return value;
    }

    public static String stringFormat(String value, boolean capitalize) {

	String retVal = StringUtils.trimToEmpty(value);
	if (capitalize) {
	    retVal = WordUtils.capitalizeFully(retVal);
	}
	//escape dei caratteri non ASCII
	Matcher matcher = NON_ASCII_REGEX_PATTERN.matcher(retVal);
	StringBuffer sbRetVal = new StringBuffer();
	String nonAsciiChar = null;
	while (matcher.find()) {
	    nonAsciiChar = matcher.group();
	    matcher.appendReplacement(sbRetVal, escapeUnicodeCharacter(nonAsciiChar));
	}
	matcher.appendTail(sbRetVal);
	return sbRetVal.toString();
    }

    private static String escapeUnicodeCharacter(String toEscape) {

	StringBuilder sbEscaped = new StringBuilder();
	if (toEscape != null && toEscape.length() > 0) {
	    sbEscaped.append("\\\\\\\\u");
	    char[] chars = toEscape.toCharArray();
	    //in teoria il metodo dovrebbe ricevere stringhe di un solo carattere ma può funzionare anche con sequenze di caratteri.
	    for (int i = 0; i < chars.length;) {
		int unicodeCodePoint = Character.codePointAt(chars, i);
		if (Character.isHighSurrogate(chars[i])) {
		    i++;
		}
		i++;
		sbEscaped.append(unicodeCodePoint);
	    }
	    sbEscaped.append("?");
	}
	return sbEscaped.toString();
    }

    public static String decimalFormat(BigDecimal decimalValue) {

	return decimalFormat(decimalValue, 2);
    }

    public static String decimalFormat(BigDecimal decimalValue, int numDecimals) {

	return decimalFormat(decimalValue, numDecimals, false);
    }

    public static String decimalFormat(BigDecimal decimalValue, int numDecimals, boolean useSeparator) {

	String retVal = "";
	if (decimalValue != null) {
	    NumberFormat df = DecimalFormat.getNumberInstance(Locale.ITALY);
	    if (numDecimals == RtfConstants.MINIMUM_NUMBER_OF_DECIMAL_DIGITS) {
		numDecimals = decimalValue.scale();
	    }
	    df.setMaximumFractionDigits(numDecimals);
	    df.setMinimumFractionDigits(numDecimals);
	    df.setMinimumIntegerDigits(1);
	    df.setGroupingUsed(useSeparator);
	    retVal = df.format(decimalValue);
	}
	return retVal;
    }

    public static String booleanFormat(Boolean boolValue, String ifTrue, String ifFalse, String ifNull) {

	String retVal = "";
	if (boolValue != null) {
	    if (boolValue) {
		retVal = ifTrue != null ? ifTrue : "";
	    } else {
		retVal = ifFalse != null ? ifFalse : "";
	    }
	} else {
	    retVal = ifNull != null ? ifNull : "";
	}
	return retVal;
    }

    public static String integerFormat(Integer integerValue) {

	String retVal = "";
	if (integerValue != null) {
	    retVal = integerValue.toString();
	}
	return retVal;
    }

    public static String integerFormat(Short integerValue) {

	String retVal = "";
	if (integerValue != null) {
	    retVal = integerValue.toString();
	}
	return retVal;
    }

    public static String dateFormat(Date dateValue, DateFormat df) {

	if (dateValue == null) {
	    return "";
	}
	if (df == null) {
	    df = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	}
	return df.format(dateValue);
    }

    public static String dateFormat(Date dateValue) {

	return dateFormat(dateValue, null);
    }

    /**
     * La funzione torna una stringa separata ogni <b>charSplitLength</b> da <b>separator</b>. Nelle tabelle se una
     * stringa è troppo lunga non viene fatto il wrap in questo caso aggiungiamo uno spazio ogni tot caratteri. <br />
     * Es: la stringa <b>e55879ed0e54a943252807c43b61bae2e7b141c3e2c53b7737e0f18a05c10f0b</b> che è un hash 256 di un
     * file viene trasformata invocando la funzione
     * 
     * <pre>
     * splitStringByNCharacters("e55879ed0e54a943252807c43b61bae2e7b141c3e2c53b7737e0f18a05c10f0b", 20, " - ")
     * </pre>
     * 
     * in <b>e55879ed0e54a9432528 - 07c43b61bae2e7b141c3 - e2c53b7737e0f18a05c1 - 0f0b</b>
     * 
     * @param in
     *            La stringa da modificare
     * @param charSplitLength
     *            ogni quanti caratteri aggiungere un separatore
     * @param separator
     *            il separatore
     * @return
     */
    public static String splitStringByNCharacters(String in, int charSplitLength, String separator) {

	if (in == null) {
	    return in;
	}
	String numChars = StringUtils.repeat(".", charSplitLength);
	String[] string = in.split("(?<=\\G" + numChars + ")");
	return StringUtils.join(string, separator);
    }
}
