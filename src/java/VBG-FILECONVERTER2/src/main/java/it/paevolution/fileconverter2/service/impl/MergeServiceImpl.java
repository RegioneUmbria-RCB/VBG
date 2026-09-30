package it.paevolution.fileconverter2.service.impl;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.fileconverter.ConvertResponse;
import it.gruppoinit.fileconverter.MergeAndConvertResponse;
import it.gruppoinit.fileconverter.MergeDataAndConvertResponse;
import it.gruppoinit.fileconverter.MergeDataResponse;
import it.paevolution.fileconverter2.service.BaseService;
import it.paevolution.fileconverter2.service.BaseService.ConversionsSupportedEnum;
import it.paevolution.fileconverter2.service.BaseService.FileInTypesEnum;
import it.paevolution.fileconverter2.service.BaseService.ModelTypesEnum;
import it.paevolution.fileconverter2.service.FileConverterService;
import it.paevolution.fileconverter2.service.MergeService;

@Service
public class MergeServiceImpl implements MergeService {

	private static final Logger log = LoggerFactory.getLogger(MergeServiceImpl.class);
	@Autowired
	private FileConverterService fileConverterService;
	private String outputFileName = "";
	private String rtfContent = "";
	private String preXSLstr = """
            <?xml version="1.0" encoding="utf-8"?>
            <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
            <xsl:template match="/">\
            """;
	private String postXSLstr = """
            </xsl:template>
            <xsl:template name="FormatDate"> \
                <xsl:param name="DateTime" /> \
            	<xsl:if test="string-length($DateTime)!=0"> \
            		<xsl:value-of select="substring($DateTime,9,2)"/>/<xsl:value-of select="substring($DateTime,6,2)"/>/<xsl:value-of select="substring($DateTime,1,4)"/>  \
            </xsl:if></xsl:template> \
            <xsl:template name="FormatDateyyyyMMdd"> \
                <xsl:param name="DateTimeyyyyMMdd" /> \
                 <xsl:if test="string-length($DateTimeyyyyMMdd)!=0"> \
                        <xsl:value-of select="substring($DateTimeyyyyMMdd,7,2)"/>/<xsl:value-of select="substring($DateTimeyyyyMMdd,5,2)"/>/<xsl:value-of select="substring($DateTimeyyyyMMdd,1,4)"/>\
            </xsl:if></xsl:template>\
            </xsl:stylesheet>\
            """;

	@Override
	public synchronized MergeDataResponse mergeData(byte[] rtfBinaryData, byte[] xmlBinaryData) {

		doInternal(rtfBinaryData);
		MergeDataResponse response = new MergeDataResponse();
		byte[] result = effettuaSostituzioniDaRTF(this.rtfContent.getBytes(), xmlBinaryData);
		response.setBinaryData(result);
		response.setMimeType(BaseService.MIME_TYPE_RTF_DOC);
		response.setFileName(this.outputFileName);
		return response;
	}

	@Override
	public synchronized MergeDataAndConvertResponse mergeDataAndExport(byte[] rtfBinaryData, byte[] xmlBinaryData,
			ConversionsSupportedEnum conversionType) {

		MergeDataAndConvertResponse response = new MergeDataAndConvertResponse();
		doInternal(rtfBinaryData);
		byte[] result = effettuaSostituzioniDaRTF(this.rtfContent.getBytes(), xmlBinaryData);
		response.setBinaryData(result);
		ConvertResponse cResponse = fileConverterService.convertBinaryContent(result, FileInTypesEnum.RTF,
				conversionType);
		response.setMimeType(cResponse.getMimeType());
		response.setBinaryData(cResponse.getBinaryData());
		response.setFileName(cResponse.getFileName());
		return response;
	}

	@Override
	public synchronized MergeAndConvertResponse mergeAndConvert(byte[] data, FileInTypesEnum dataType, byte[] model,
			ModelTypesEnum modelType, ConversionsSupportedEnum conversionType) {

		MergeAndConvertResponse response = new MergeAndConvertResponse();
		FileInTypesEnum _dataType = null;
		byte[] result = null;
		if (dataType != null && dataType.equals(FileInTypesEnum.XML) && modelType != null
				&& modelType.equals(ModelTypesEnum.XSL)) {
			result = doXSLTransformation(model, data);
			_dataType = FileInTypesEnum.HTML;
		} else if (dataType != null && dataType.equals(FileInTypesEnum.XML) && modelType != null
				&& modelType.equals(ModelTypesEnum.RTF)) {
			doInternal(data);
			result = effettuaSostituzioniDaRTF(this.rtfContent.getBytes(), data);
			_dataType = FileInTypesEnum.RTF;
		}
		ConvertResponse cResponse = fileConverterService.convertBinaryContent(result, _dataType, conversionType);
		response.setBinaryData(cResponse.getBinaryData());
		response.setFileName(cResponse.getFileName());
		response.setMimeType(cResponse.getMimeType());
		return response;
	}

	private void doInternal(byte[] rtfBinaryData) {

		this.outputFileName = "output_" + System.currentTimeMillis() + ".rtf";
		this.rtfContent = convertByteArrayToString(rtfBinaryData);
		StringBuffer resultXSL = new StringBuffer(this.preXSLstr);
		resultXSL = resultXSL.append(rtfContent);
		resultXSL = resultXSL.append(this.postXSLstr);
		this.rtfContent = resultXSL.toString();
	}

	private synchronized byte[] doXSLTransformation(byte[] xslData, byte[] xmlData) {

		if (log.isDebugEnabled()) {
			log.debug(convertByteArrayToString(xslData));
			log.debug(convertByteArrayToString(xmlData));
		}
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(xslData);
			Source source = new javax.xml.transform.stream.StreamSource(bis);
			Transformer transformer = tFactory.newTransformer(source);
			StringWriter writer = new StringWriter();
			javax.xml.transform.stream.StreamResult outResult = new javax.xml.transform.stream.StreamResult(writer);
			ByteArrayInputStream bisXmlData = new ByteArrayInputStream(xmlData);
			transformer.transform(new javax.xml.transform.stream.StreamSource(bisXmlData), outResult);
			return writer.getBuffer().toString().getBytes("UTF-8");
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException(e);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private synchronized byte[] effettuaSostituzioniDaRTF(byte[] rtfContent, byte[] xmlData) {

		if (log.isDebugEnabled()) {
			log.debug("\n\n" + convertByteArrayToString(rtfContent));
			log.debug("\n\n" + convertByteArrayToString(xmlData));
		}
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(rtfContent);
			Source source = new javax.xml.transform.stream.StreamSource(bis);
			Transformer transformer = tFactory.newTransformer(source);
			StringWriter writer = new StringWriter();
			javax.xml.transform.stream.StreamResult outResult = new javax.xml.transform.stream.StreamResult(writer);
			ByteArrayInputStream bisXmlData = new ByteArrayInputStream(xmlData);
			transformer.transform(new javax.xml.transform.stream.StreamSource(bisXmlData), outResult);
			// elimino <\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>
			// che viene messo di default dalla trasformazione
			writer.getBuffer().replace(0, 38, "");
			String output = this.convertToRtfCharacter(writer.toString());
			return output.getBytes("UTF-8");
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException(e);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	private String convertToRtfCharacter(String stringRtf) {

		String output = stringRtf;
		if (output.contains("è")) {
			output = output.replace("è", "\\'e8");
		}
		if (output.contains("é")) {
			output = output.replace("é", "\\'e9");
		}
		if (output.contains("à")) {
			output = output.replace("à", "\\'e0");
		}
		if (output.contains("ò")) {
			output = output.replace("ò", "\\'f2");
		}
		if (output.contains("ì")) {
			output = output.replace("ì", "\\'ec");
		}
		if (output.contains("ù")) {
			output = output.replace("ù", "\\'f9");
		}
		if (output.contains("€")) {
			output = output.replace("€", "\\'80");
		}
		if (output.contains("£")) {
			output = output.replace("£", "\\'a3");
		}
		if (output.contains("§")) {
			output = output.replace("§", "\\'a7");
		}
		if (output.contains("°")) {
			output = output.replace("°", "\\'b0");
		}
		if (output.contains("ç")) {
			output = output.replace("ç", "\\'e7");
		}
		return output;
	}

	private String convertByteArrayToString(byte[] stringContent) {

		return new String(stringContent);
	}
}
