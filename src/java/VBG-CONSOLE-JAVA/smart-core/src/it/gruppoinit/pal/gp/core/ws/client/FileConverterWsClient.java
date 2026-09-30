package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.fileconverter.ConvertRequest;
import it.gruppoinit.fileconverter.ConvertResponse;
import it.gruppoinit.fileconverter.MergeAndConvertRequest;
import it.gruppoinit.fileconverter.MergeAndConvertResponse;
import it.gruppoinit.fileconverter.MergeDataAndConvertRequest;
import it.gruppoinit.fileconverter.MergeDataAndConvertResponse;
import it.gruppoinit.fileconverter.MergeDataRequest;
import it.gruppoinit.fileconverter.MergeDataResponse;
import it.gruppoinit.fileconverter.definitions.Fileconverter;
import it.gruppoinit.fileconverter.definitions.FileconverterServiceLocator;
import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.net.URL;
import java.rmi.RemoteException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileConverterWsClient {

    private static final Logger log = LoggerFactory.getLogger(FileConverterWsClient.class);

    public enum ContentType {
	TXT, HTML
    };

    public enum ConversionType {
	PDF, HTML
    };

    public ConvertResponse convert(ConvertRequest req) {

	Fileconverter port = getWsPort();
	ConvertResponse resp;
	try {
	    log(req);
	    resp = port.convert(req);
	    log(resp);
	    return resp;
	} catch (Exception e) {
	    log.error("convert(): {}", e.getMessage());
	    throw new RuntimeException("Errore durante la chiamata al metodo convert di FileConverter: " + e.getMessage());
	}
    }

    public ConvertBinaryResponse convertBinary(ConvertBinaryRequest req) throws RemoteException {

	Fileconverter port = getWsPort();
	ConvertBinaryResponse resp;
	log(req);
	try {
	    resp = port.convertBinary(req);
	    log(resp);
	    return resp;
	} catch (RemoteException e) {
	    log.error("convertBinary(): {}", e.getMessage());
	    throw new RemoteException("Errore durante la chiamata al metodo convertBinary di FileConverter: " + e.getMessage(), e);
	}
    }

    public MergeDataResponse mergeData(MergeDataRequest req) {

	Fileconverter port = getWsPort();
	MergeDataResponse resp;
	try {
	    log(req);
	    resp = port.mergeData(req);
	    log(resp);
	    return resp;
	} catch (Exception e) {
	    log.error("mergeData(): {}", e.getMessage());
	    throw new RuntimeException("Errore durante la chiamata al metodo mergeData di FileConverter: " + e.getMessage());
	}
    }

    public MergeDataAndConvertResponse mergeDataAndConvert(MergeDataAndConvertRequest req) {

	Fileconverter port = getWsPort();
	MergeDataAndConvertResponse resp;
	try {
	    log(req);
	    resp = port.mergeDataAndConvert(req);
	    log(resp);
	    return resp;
	} catch (Exception e) {
	    log.error("mergeDataAndConvert(): {}", e.getMessage());
	    throw new RuntimeException("Errore durante la chiamata al metodo mergeDataAndConvert di FileConverter: " + e.getMessage());
	}
    }

    public MergeAndConvertResponse mergeAndConvert(MergeAndConvertRequest req) {

	Fileconverter port = getWsPort();
	MergeAndConvertResponse resp;
	try {
	    log(req);
	    resp = port.mergeAndConvert(req);
	    log(resp);
	    return resp;
	} catch (Exception e) {
	    log.error("mergeAndConvert(): {}", e.getMessage());
	    throw new RuntimeException("Errore durante la chiamata al metodo mergeAndConvert di FileConverter: " + e.getMessage());
	}
    }

    private Fileconverter getWsPort() {

	FileconverterServiceLocator locator = new FileconverterServiceLocator();
	URL url;
	Fileconverter port;
	try {
	    String _url = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_FILECONVERTER);
	    log.debug("getWsPort(): url='{}'", _url);
	    url = new URL(_url);
	    port = locator.getfileconverterSoap11(url);
	    return port;
	} catch (Exception e) {
	    log.error("getWsPort(): {}", e.getMessage());
	    throw new RuntimeException("Errore durante l'inizializzazione della chiamata a FileConverter: " + e.getMessage());
	}
    }

    private void log(ConvertRequest req) {

	if (req != null && log.isDebugEnabled()) {
	    Integer length = req.getContent() != null ? req.getContent().length() : null;
	    log.debug("ConvertRequest contentType:'{}',conversionType:'{}',content length:'{}',token:'{}'",
		    new Object[] { req.getContentType(), req.getConversionType(), length, req.getToken() });
	}
    }

    private void log(MergeDataRequest req) {

	if (req != null && log.isDebugEnabled()) {
	    Integer lengthRtf = req.getRtfBinaryData() != null ? req.getRtfBinaryData().length : null;
	    Integer lengthXml = req.getXmlBinaryData() != null ? req.getXmlBinaryData().length : null;
	    log.debug("MergeDataRequest lengthXml:'{}',lengthRtf:'{}',token:'{}'", new Object[] { lengthXml, lengthRtf, req.getToken() });
	}
    }

    private void log(MergeDataAndConvertRequest req) {

	if (req != null && log.isDebugEnabled()) {
	    Integer lengthRtf = req.getRtfBinaryData() != null ? req.getRtfBinaryData().length : null;
	    Integer lengthXml = req.getXmlBinaryData() != null ? req.getXmlBinaryData().length : null;
	    log.debug("MergeDataAndConvertRequest lengthXml:'{}',lengthRtf:'{}',conversionType:'{}',token:'{}'", new Object[] { lengthXml, lengthRtf,
		    req.getConversionType(), req.getToken() });
	}
    }

    private void log(MergeAndConvertRequest req) {

	if (req != null && log.isDebugEnabled()) {
	    Integer lengthData = req.getData() != null ? req.getData().length : null;
	    Integer lengthModel = req.getModel() != null ? req.getModel().length : null;
	    log.debug("MergeDataAndConvertRequest lengthData:'{}',lengthModel:'{}',conversionType:'{}',token:'{}'", new Object[] { lengthData,
		    lengthModel, req.getConversionType(), req.getToken() });
	}
    }

    private void log(ConvertBinaryRequest req) {

	if (req != null && log.isDebugEnabled()) {
	    Integer length = req.getBinaryData() != null ? req.getBinaryData().length : null;
	    log.debug("ConvertBinaryRequest contentType:'{}',conversionType:'{}',binaryData length:'{}',token:'{}'",
		    new Object[] { req.getContentType(), req.getConversionType(), length, req.getToken() });
	}
    }

    private void log(ConvertResponse resp) {

	if (resp != null && log.isDebugEnabled()) {
	    Integer length = resp.getBinaryData() != null ? resp.getBinaryData().length : null;
	    log.debug("ConvertResponse mimeType:'{}',fileName:'{}',binaryData length:'{}'", new Object[] { resp.getMimeType(), resp.getFileName(),
		    length });
	}
    }

    private void log(ConvertBinaryResponse resp) {

	if (resp != null && log.isDebugEnabled()) {
	    Integer length = resp.getBinaryData() != null ? resp.getBinaryData().length : null;
	    log.debug("ConvertBinaryResponse mimeType:'{}',fileName:'{}',binaryData length:'{}'",
		    new Object[] { resp.getMimeType(), resp.getFileName(), length });
	}
    }

    private void log(MergeDataResponse resp) {

	if (resp != null && log.isDebugEnabled()) {
	    Integer length = resp.getBinaryData() != null ? resp.getBinaryData().length : null;
	    log.debug("MergeDataResponse mimeType:'{}',fileName:'{}',binaryData length:'{}'", new Object[] { resp.getMimeType(), resp.getFileName(),
		    length });
	}
    }

    private void log(MergeDataAndConvertResponse resp) {

	if (resp != null && log.isDebugEnabled()) {
	    Integer length = resp.getBinaryData() != null ? resp.getBinaryData().length : null;
	    log.debug("MergeDataAndConvertResponse fileName:'{}',length:'{}',mimeType:'{}'",
		    new Object[] { resp.getFileName(), length, resp.getMimeType() });
	}
    }

    private void log(MergeAndConvertResponse resp) {

	if (resp != null && log.isDebugEnabled()) {
	    Integer length = resp.getBinaryData() != null ? resp.getBinaryData().length : null;
	    log.debug("MergeAndConvertResponse fileName:'{}',length:'{}',mimeType:'{}'",
		    new Object[] { resp.getFileName(), length, resp.getMimeType() });
	}
    }
}
