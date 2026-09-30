package it.paevolution.fileconverter2.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.Tika;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;

import it.paevolution.fileconverter2.service.BaseService.ConversionsSupportedEnum;
import it.paevolution.fileconverter2.service.BaseService.FileInTypesEnum;

public class Utils {

    private static Tika t = new Tika();

    public static String getMimeFromExtension(String extension) {

	return t.detect("document." + extension);
    }

    public static FileInTypesEnum getFileInTypesFromFileName(String fileName) {

	final DocumentFormat targetFormat = DefaultDocumentFormatRegistry.getFormatByExtension(fileName.substring(fileName.lastIndexOf(".") + 1));
	// DOC, DOCX, ODT, HTML, RTF, TXT, XML, PDF, XLSX, XLS
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.DOC.getName())) {
	    return FileInTypesEnum.DOC;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.DOCX.getName())) {
	    return FileInTypesEnum.DOCX;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.ODT.getName())) {
	    return FileInTypesEnum.ODT;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.HTML.getName())) {
	    return FileInTypesEnum.HTML;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.RTF.getName())) {
	    return FileInTypesEnum.RTF;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.TXT.getName())) {
	    return FileInTypesEnum.TXT;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.XHTML.getName())) {
	    return FileInTypesEnum.HTML;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.PDF.getName())) {
	    return FileInTypesEnum.PDF;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.XLSX.getName())) {
	    return FileInTypesEnum.XLSX;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.XLS.getName())) {
	    return FileInTypesEnum.XLS;
	}
	return null;
    }

    public static ConversionsSupportedEnum getConversionsSupportedFromFileName(String extension) {

	final DocumentFormat targetFormat = DefaultDocumentFormatRegistry.getFormatByExtension(extension);
	// DOC, HTML, ODT, PDF, RTF, TXT
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.DOC.getName())) {
	    return ConversionsSupportedEnum.DOC;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.DOCX.getName())) {
	    return ConversionsSupportedEnum.DOCX;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.XHTML.getName())) {
	    return ConversionsSupportedEnum.HTML;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.ODT.getName())) {
	    return ConversionsSupportedEnum.ODT;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.PDF.getName())) {
	    return ConversionsSupportedEnum.PDF;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.RTF.getName())) {
	    return ConversionsSupportedEnum.RTF;
	}
	if (targetFormat.getName().equals(DefaultDocumentFormatRegistry.TXT.getName())) {
	    return ConversionsSupportedEnum.TXT;
	}
	return null;
    }

    public static String getFileInTypesEnumToString(FileInTypesEnum contentType) {

	switch (contentType) {
	case HTML:
	    return "html";
	case RTF:
	    return "rtf";
	}
	// default
	return "html";
    }

    public static FileInTypesEnum getDescrizioneFileInTypesEnum(String contentType) {

	if (null == contentType || contentType.equals("")) {
	    return FileInTypesEnum.HTML;
	}
	if (contentType.equalsIgnoreCase("html")) {
	    return FileInTypesEnum.HTML;
	}
	if (contentType.equalsIgnoreCase("rtf")) {
	    return FileInTypesEnum.RTF;
	}
	if (contentType.equalsIgnoreCase("txt")) {
	    return FileInTypesEnum.TXT;
	}
	if (contentType.equalsIgnoreCase("pdf")) {
	    return FileInTypesEnum.PDF;
	}
	if (contentType.equalsIgnoreCase("odt")) {
	    return FileInTypesEnum.ODT;
	}
	if (contentType.equalsIgnoreCase("doc")) {
	    return FileInTypesEnum.DOC;
	}
	if (contentType.equalsIgnoreCase("docx")) {
	    return FileInTypesEnum.DOCX;
	}
	if (contentType.equalsIgnoreCase("XLSX")) {
	    return FileInTypesEnum.XLSX;
	}
	if (contentType.equalsIgnoreCase("XLS")) {
	    return FileInTypesEnum.XLS;
	}
	// default
	return FileInTypesEnum.HTML;
    }

    /**
     * 
     * @param conversion
     * @return
     */
    public static String getConversionsSupportedEnumToString(ConversionsSupportedEnum conversion) {

	switch (conversion) {
	case DOC:
	    return "doc";
	case HTML:
	    return "html";
	case ODT:
	    return "odt";
	case PDF:
	    return "pdf";
	case RTF:
	    return "rtf";
	case TXT:
	    return "txt";
	default:
	    return "pdf";
	}
    }

    /**
     * 
     * @param conversion
     * @return
     */
    public static ConversionsSupportedEnum getDescrizioneConversionsSupportedEnum(String conversion) {

	if (null == conversion || conversion.equals("")) {
	    return ConversionsSupportedEnum.PDF;
	}
	if (conversion.equalsIgnoreCase("doc")) {
	    return ConversionsSupportedEnum.DOC;
	}
	if (conversion.equalsIgnoreCase("rtf")) {
	    return ConversionsSupportedEnum.RTF;
	}
	if (conversion.equalsIgnoreCase("odt")) {
	    return ConversionsSupportedEnum.ODT;
	}
	if (conversion.equalsIgnoreCase("txt")) {
	    return ConversionsSupportedEnum.TXT;
	}
	if (conversion.equalsIgnoreCase("html")) {
	    return ConversionsSupportedEnum.HTML;
	}
	// default
	return ConversionsSupportedEnum.PDF;
    }

    public static String replace(String text, String find, String replace) {

	Pattern p = null;
	Matcher m = null;
	p = Pattern.compile(find, Pattern.CASE_INSENSITIVE);
	m = p.matcher(text);
	return m.replaceAll(replace);
    }
}
