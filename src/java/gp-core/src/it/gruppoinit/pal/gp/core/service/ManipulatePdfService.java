package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.service.helper.AnnotazionePropertyPdfHelper;

public interface ManipulatePdfService {

    public byte[] addAnnotation(AnnotazionePropertyPdfHelper annotazionePropertyPdfHelper);

    public int getNumberPagePdf(byte[] pdf);

    public byte[] addImageToPDF(byte[] oggetto, byte[] qrcode, Integer page, Integer posX, Integer posY);
}
