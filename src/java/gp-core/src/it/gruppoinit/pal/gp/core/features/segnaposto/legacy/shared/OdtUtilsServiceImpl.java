package it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.utils.CheckboxUtils;
import it.gruppoinit.pal.gp.core.service.QrcodeService;
import it.gruppoinit.pal.gp.core.service.helper.QRCodeBean;
import it.gruppoinit.pal.gp.core.service.helper.QrcodeHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipoAuthQRcodeEnum;
import it.gruppoinit.pal.gp.core.utils.DocumentMergeUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class OdtUtilsServiceImpl implements IOdtUtilsService {

    private OggettiService oggettiService;
    private QrcodeService qrcodeService;

    @Autowired
    public OdtUtilsServiceImpl(OggettiService oggettiService, QrcodeService qrcodeService) {

	super();
	this.oggettiService = oggettiService;
	this.qrcodeService = qrcodeService;
    }

    @Override
    public String odtBonificaValueForODT(String valuePalceHolder) {

	if (StringUtils.isNotBlank(valuePalceHolder)) {
	    valuePalceHolder = odtBonificaCharacterODT(valuePalceHolder);
	    // I caratteri NON ASCI che vengono codificati per RTF, nel caso dell'ODT dobbiamo
	    // Nel caso dell'odt il check viene popolato con parentesi quadre aperte e chiuse
	    if (valuePalceHolder.contains(CheckboxUtils.getCheckedImg())) {
		valuePalceHolder = "[X]";
	    }
	    if (valuePalceHolder.contains(CheckboxUtils.getUncheckedImg())) {
		valuePalceHolder = "[ ]";
	    }
	}
	return valuePalceHolder;
    }

    @Override
    public String odtIsImmagineForODT(String placeHolderValue, String placeHolder, IUsefulDataForPlaceholderReplacement data) {

	String estensione = "jpeg";
	byte[] binaryData = null;
	Oggetti oggetto = null;
	if (placeHolder.equalsIgnoreCase("CAS_STEMMA")) {
	    Comuniassociatisoftware cas = data.getDatiComuneassociato();
	    if (cas != null && cas.getOggetti() != null) {
		oggetto = oggettiService.findById(cas.getOggetti().getId());
		binaryData = oggetto.getOggetto();
	    }
	    if (binaryData == null) {
		cas = data.getDatiComuneassociatoTT();
		if (EntityUtils.getNestedProperty(cas, "oggetti.id.codice") != null) {
		    oggetto = oggettiService.findById(cas.getOggetti().getId());
		    binaryData = oggetto.getOggetto();
		}
	    }
	    if (oggetto != null) {
		estensione = StringUtils.substringAfterLast(oggetto.getNomefile(), ".");
	    }
	}
	if (placeHolder.equalsIgnoreCase("QRCODE-VISURA-GUEST") || placeHolder.equalsIgnoreCase("QRCODE-VISURA-PIN")
		|| placeHolder.equalsIgnoreCase("QRCODE-VISURA-AUTH")) {
	    if (data.getIstanza() != null && data.getIstanza().getId() != null && data.getIstanza().getId().getCodice() != null) {
		QrcodeHelper qrHelper = new QrcodeHelper();
		TipoAuthQRcodeEnum authQRcodeEnum = DocumentMergeUtils.getAuthQRcode(placeHolder);
		qrHelper.setAuthQRcodeEnum(authQRcodeEnum);
		QRCodeBean b = qrcodeService.visurapratica(qrHelper, data.getIstanza().getId().getCodice());
		binaryData = b.getImage();
		estensione = "png";
	    }
	}
	if (placeHolder.equalsIgnoreCase("QRCODE-DOWNLOAD-GUEST") || placeHolder.equalsIgnoreCase("QRCODE-DOWNLOAD-PIN")
		|| placeHolder.equalsIgnoreCase("QRCODE-DOWNLOAD-AUTH")) {
	    if (data.getMovimento() != null && data.getMovimento().getId() != null && data.getMovimento().getId().getCodice() != null) {
		QrcodeHelper qrHelper = new QrcodeHelper();
		TipoAuthQRcodeEnum authQRcodeEnum = DocumentMergeUtils.getAuthQRcode(placeHolder);
		qrHelper.setAuthQRcodeEnum(authQRcodeEnum);
		QRCodeBean b = qrcodeService.downloadDocumentiMovimento(qrHelper, data.getMovimento().getId().getCodice());
		binaryData = b.getImage();
		estensione = "png";
	    }
	}
	if (binaryData != null) {
	    return Utilities.createTempFileFromArrayByte(binaryData, estensione).toURI().toString();
	}
	return "";
    }

    private String odtBonificaCharacterODT(String valuePalceHolder) {

	// Per l'RTF il carattere "è" è codificato con "\\\\'e8", nel caso di odt dobbiamo 
	// dobbiamo riportarlo come carattere
	if (valuePalceHolder.contains("\\\\'e8")) {
	    valuePalceHolder = StringUtils.replace(valuePalceHolder, "\\\\'e8", "è");
	}
	/**
	 * Applica la regola inversa dello stringFormat quando si tratta di caratteri NO ASCI Nel caso di odt non serve
	 * codificare i caratteri NO ASCI. ES. Lo stringFormat trasforma 'à' in '\\u224?', il metodo di occupa di
	 * riportarlo al valore originale 'à'
	 **/
	if (StringUtils.contains(valuePalceHolder, "\\u")) {
	    Pattern NON_ASCII_REGEX_PATTERN = Pattern.compile("\\\\u[0-9]*\\\\?");
	    Matcher matcher = NON_ASCII_REGEX_PATTERN.matcher(valuePalceHolder);
	    String nonAsciiChar = null;
	    while (matcher.find()) {
		nonAsciiChar = matcher.group();
		String daSostituire = "\\\\\\" + nonAsciiChar + "\\?";
		nonAsciiChar = StringUtils.substringAfter(nonAsciiChar, "\\u");
		String f = new String(Character.toChars(Integer.parseInt(nonAsciiChar)).clone());
		valuePalceHolder = valuePalceHolder.replaceFirst(daSostituire, f);
	    }
	}
	return valuePalceHolder;
    }
}
