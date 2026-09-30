package it.gruppoinit.service.imp;

import it.gruppoinit.constants.QRCodeCostant;
import it.gruppoinit.service.QRCodeService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    public String generaStringaQRCode(String codiceAvviso, String importo, String codiceFiscaleEnte) {

	String qrcode = QRCodeCostant.PATTERN_QRCODE;
	qrcode = qrcode.replace("{0}", codiceAvviso);
	qrcode = qrcode.replace("{1}", codiceFiscaleEnte);
	if (StringUtils.isNotEmpty(importo)) {
	    importo = StringUtils.replace(importo, ",", ".");
	    qrcode = qrcode.replace("{2}", importo);
	}
	return qrcode;
    }
}
