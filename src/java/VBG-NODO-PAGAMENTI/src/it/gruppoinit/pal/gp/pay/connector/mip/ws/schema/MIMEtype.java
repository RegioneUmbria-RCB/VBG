/*
 * MIP API - WS Conversion Libreria API del Modulo Incassi e Pagamenti del Comune di Genova per operazioni su Avvisi di
 * Pagamento
 *
 * OpenAPI spec version: 0.0.94 Contact: helpservizionline@comune.genova.it
 *
 */
package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

/**
 * I MIME types gestiti
 */
public enum MIMEtype {

    APPLICATION_EPUB_ZIP("application/epub+zip"),
    APPLICATION_JAVA_ARCHIVE("application/java-archive"),
    APPLICATION_JSON("application/json"),
    APPLICATION_MSWORD("application/msword"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    APPLICATION_PROBLEM_JSON("application/problem+json"),
    APPLICATION_PDF("application/pdf"),
    APPLICATION_RTF("application/rtf"),
    APPLICATION_VND_AMAZON_EBOOK("application/vnd.amazon.ebook"),
    APPLICATION_VND_MOZILLA_XUL_XML("application/vnd.mozilla.xul+xml"),
    APPLICATION_VND_MS_EXCEL("application/vnd.ms-excel"),
    APPLICATION_VND_MS_FONTOBJECT("application/vnd.ms-fontobject"),
    APPLICATION_VND_MS_POWERPOINT("application/vnd.ms-powerpoint"),
    APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION("application/vnd.oasis.opendocument.presentation"),
    APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET("application/vnd.oasis.opendocument.spreadsheet"),
    APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT("application/vnd.oasis.opendocument.text"),
    APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_PRESENTATION("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEET("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    APPLICATION_X_7Z_COMPRESSED("application/x-7z-compressed"),
    APPLICATION_X_ABIWORD("application/x-abiword"),
    APPLICATION_XHTML_XML("application/xhtml+xml"),
    APPLICATION_XML("application/xml"),
    APPLICATION_X_RAR_COMPRESSED("application/x-rar-compressed"),
    APPLICATION_X_TAR("application/x-tar"),
    APPLICATION_ZIP("application/zip"),
    AUDIO_MIDI_AUDIO_X_MIDI("audio/midi audio/x-midi"),
    AUDIO_OGG("audio/ogg"),
    AUDIO_WAV("audio/wav"),
    AUDIO_WEBM("audio/webm"),
    IMAGE_BMP("image/bmp"),
    IMAGE_GIF("image/gif"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_SVG_XML("image/svg+xml"),
    IMAGE_TIFF("image/tiff"),
    IMAGE_WEBP("image/webp"),
    IMAGE_X_ICON("image/x-icon"),
    TEXT_CALENDAR("text/calendar"),
    TEXT_CSV("text/csv"),
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    VIDEO_3GPP("video/3gpp"),
    VIDEO_MPEG("video/mpeg"),
    VIDEO_OGG("video/ogg"),
    VIDEO_WEBM("video/webm"),
    VIDEO_X_MSVIDEO("video/x-msvideo");

    private String value;

    MIMEtype(String value) {

	this.value = value;
    }

    public String getValue() {

	return value;
    }

    @Override
    public String toString() {

	return String.valueOf(value);
    }

    public static MIMEtype fromValue(String input) {

	for (MIMEtype b : MIMEtype.values()) {
	    if (b.value.equals(input)) {
		return b;
	    }
	}
	return null;
    }
}
