package it.gruppoinit.pal.gp.core.features.segnaposto.legacy;

import java.util.regex.Pattern;

public class RtfConstants {

    private RtfConstants() {

    }

    public static final String SEPARATORE_LINK_ALLEGATI = "@#";
    public static final String RTF_CRLF = "\\\\par ";
    public static final int MINIMUM_NUMBER_OF_DECIMAL_DIGITS = -1;
    public static final String RTF_TAB = "\\\\tab ";
    private static final String REGEX_SEGNAPOSTO = "\\[-\\w+(\\([\\w,]+\\))?-\\]";
    public static final Pattern PATTERN_SEGNAPOSTO = Pattern.compile(REGEX_SEGNAPOSTO, Pattern.MULTILINE);
}
