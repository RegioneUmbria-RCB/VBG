package it.gruppoinit.pal.gp.core.features.segnaposto.legacy;

import java.util.regex.Pattern;

public class OdtConstants {

    private OdtConstants() {

    }

    public static final String FILE_CONTENT_PAKAGE_ODT = "content.xml";
    public static final String RTF_TAB_ODT = "<text:tab/>";
    public static final String ESTENSIONE_FILE_XML = "xml";
    public static final Pattern PATTERN_SEGNAPOSTO = Pattern.compile("\\[-.+?-\\]", Pattern.MULTILINE);
    public static final String ODT_CRLF = "\r\n";
}
