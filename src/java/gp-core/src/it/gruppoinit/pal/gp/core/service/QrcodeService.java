package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.service.helper.QRCodeBean;
import it.gruppoinit.pal.gp.core.service.helper.QrcodeHelper;

public interface QrcodeService {

    public static enum TipoImmagine {
	PNG, JPG, GIF
    };

    public byte[] createQRcode(String urlToCovert, Integer width, Integer height, TipoImmagine tipoImmagine);

    public byte[] createQRcodeConTesto(String urlContent, Integer width, Integer height, String testo, TipoImmagine tipoImmagine);

    public QRCodeBean visurapratica(QrcodeHelper qrcodeHelper, Integer codiceIstanza);

    public QRCodeBean downloadDocumentiMovimento(QrcodeHelper qrcodeHelper, Integer codiceMovimento);
}
