package it.paevolution.fileconverter2.service;

public interface BaseService {

	public static enum ConversionsSupportedEnum {
		DOC, DOCX, HTML, ODT, PDF, RTF, TXT
	};

	public static enum FileInTypesEnum {
		DOC, DOCX, ODT, HTML, RTF, TXT, XML, PDF, XLSX, XLS
	};

	public static enum ModelTypesEnum {
		XSL, HTML, RTF
	};

	public static String MIME_TYPE_PDF = "application/pdf";
	public static String MIME_TYPE_RTF_DOC = "application/msword";
	public static String MIME_TYPE_TXT = "text/plain";
	public static String MIME_TYPE_HTML = "text/html";
	public static String MIME_TYPE_ODT = "application/vnd.oasis.opendocument.text";
}
