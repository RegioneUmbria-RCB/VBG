package it.gruppoinit.pal.gp.core.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.VerticalizzazioneQRCodeServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.QrcodeService;
import it.gruppoinit.pal.gp.core.service.helper.QRCodeBean;
import it.gruppoinit.pal.gp.core.service.helper.QrcodeHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipoAuthQRcodeEnum;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

@Service
public class QrcodeServiceImpl implements QrcodeService {

    private static Logger log = LoggerFactory.getLogger(QrcodeServiceImpl.class);
    private IstanzeService istanzeService;
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Override
    public byte[] createQRcodeConTesto(String urlContent, Integer width, Integer height, String testo, TipoImmagine tipoImmagine) {

	// Creo l'immagine del PIN
	byte[] immageCombinata = null;
	byte[] pinByte = null;
	pinByte = this.createPIN(testo, width == null ? 250 : width, tipoImmagine);
	// Creo il qr code
	byte[] b = null;
	if (width != null && height != null) {
	    b = this.createQRcode(urlContent, width, height, tipoImmagine);
	} else {
	    b = this.createQRcode(urlContent, null, null, tipoImmagine);
	}
	// Devo fondere le due immagini
	ByteArrayInputStream bis_qrcode = new ByteArrayInputStream(b);
	ByteArrayInputStream bis_pin = new ByteArrayInputStream(pinByte);
	try {
	    BufferedImage image_qrcode = ImageIO.read(bis_qrcode);
	    BufferedImage image_pin = ImageIO.read(bis_pin);
	    int w = image_qrcode.getWidth();
	    //int h = Math.max(image.getHeight(), overlay.getHeight());
	    int h = image_qrcode.getHeight() + image_pin.getHeight();
	    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
	    Graphics g = combined.getGraphics();
	    g.drawImage(image_qrcode, 0, 0, null);
	    g.drawImage(image_pin, 0, image_qrcode.getWidth(), null);
	    // Salva nuovo file
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(combined, tipoImmagine.name().toLowerCase(), baos);
	    baos.flush();
	    immageCombinata = baos.toByteArray();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return immageCombinata;
    }

    private ImageType getImageTypeFromTipoImmagine(TipoImmagine tipoImmagine) {

	switch (tipoImmagine) {
	case JPG:
	    return ImageType.JPG;
	case GIF:
	    return ImageType.GIF;
	case PNG:
	    return ImageType.PNG;
	}
	return ImageType.PNG;
    }

    @Override
    public byte[] createQRcode(String urlToConvert, Integer with, Integer height, TipoImmagine tipoImmagine) {

	log.debug("createQRcode# Conversione stringa url in QRCode [byte]");
	byte[] b = null;
	try {
	    ImageType imageType = getImageTypeFromTipoImmagine(tipoImmagine);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    if (with != null && height != null) {
		out = QRCode.from(urlToConvert).withSize(with, height).to(imageType).stream();
	    } else {
		out = QRCode.from(urlToConvert).to(imageType).stream();
	    }
	    b = out.toByteArray();
	} catch (Exception e) {
	    log.error("Errore durante la crezione del QR Code");
	}
	return b;
    }

    @Override
    public QRCodeBean visurapratica(QrcodeHelper qrcodeHelper, Integer codiceistanza) {

	QRCodeBean result = null;
	String guid = "";
	Istanze i = istanzeService.findById(new PkId(codiceistanza));
	guid = i.getUuid();
	ORMHelper.setSoftware(i.getSoftware().getCodice());
	// byte[] b = null;
	switch (qrcodeHelper.getAuthQRcodeEnum()) {
	case AUTH:
	    //	    result = visurapraticaAUTH(qrcodeHelper, guid);
	    break;
	case GUEST:
	    result = visurapraticaGUEST(qrcodeHelper, guid, i.getSoftware().getCodice());
	    break;
	case PIN:
	    //	    b = visurapraticaPIN(qrcodeHelper, guid);
	    break;
	default:
	    break;
	}
	return result;
    }

    @Override
    public QRCodeBean downloadDocumentiMovimento(QrcodeHelper qrcodeHelper, Integer codicemovimento) {

	//TODO da implementare
	QRCodeBean result = new QRCodeBean();
	//	byte[] b = null;
	//	switch (qrcodeHelper.getAuthQRcodeEnum()) {
	//	case AUTH:
	//	    b = downloadDocumentiMovimentoAUTH(qrcodeHelper);
	//	    break;
	//	case GUEST:
	//	    b = downloadDocumentiMovimentoGUEST(qrcodeHelper);
	//	    break;
	//	case PIN:
	//	    b = downloadDocumentiMovimentoPIN(qrcodeHelper);
	//	    break;
	//	default:
	//	    break;
	//	}
	return result;
    }

    public static void main(String[] args) throws IOException {

	QrcodeServiceImpl s = new QrcodeServiceImpl();
	QrcodeHelper dd = new QrcodeHelper();
	dd.setAuthQRcodeEnum(TipoAuthQRcodeEnum.PIN);
	dd.setHigth(150);
	dd.setWith(150);
	// byte[] createPIN = s.createQRcodeConTesto("http://www.google.com", 150, 150, UUIDGenerator.getUUID(), TipoImmagine.PNG);
	byte[] createPIN = s.createQRcode("http://www.google.com", 150, 150, TipoImmagine.PNG);
	File f = new File("C:/TEMP/qrcod." + TipoImmagine.PNG.name().toLowerCase());
	FileOutputStream fos = new FileOutputStream(f);
	fos.write(createPIN);
	fos.close();
	//		BarcodeEAN codeEAN = new BarcodeEAN();
	//		codeEAN.setCodeType(codeEAN.EAN13);
	//		codeEAN.setCode("9780201615883");
	//		PdfContentByte cb= new PdfContenRtByte(pw);
	//		com.lowagie.text.Image imageEAN = codeEAN.createImageWithBarcode(cb, null, null);
    }

    private byte[] downloadDocumentiMovimentoPIN(QrcodeHelper qrcodeHelper) {

	String urlToCovert = "downloadDocumentiMovimentoPIN";
	// Creo l'immagine del PIN
	byte[] immageCombinata = null;
	byte[] pinByte = null;
	pinByte = this.createPIN("PIN:1000000000", 250, TipoImmagine.PNG);
	// Creo il qr code
	byte[] b = null;
	if (qrcodeHelper.getWith() != null && qrcodeHelper.getHigth() != null) {
	    b = this.createQRcode(urlToCovert, qrcodeHelper.getWith(), qrcodeHelper.getHigth(), TipoImmagine.PNG);
	} else {
	    b = this.createQRcode(urlToCovert, null, null, TipoImmagine.PNG);
	}
	// Devo fondere le due immagini
	ByteArrayInputStream bis_qrcode = new ByteArrayInputStream(b);
	ByteArrayInputStream bis_pin = new ByteArrayInputStream(pinByte);
	try {
	    BufferedImage image_qrcode = ImageIO.read(bis_qrcode);
	    BufferedImage image_pin = ImageIO.read(bis_pin);
	    int w = image_qrcode.getWidth();
	    //int h = Math.max(image.getHeight(), overlay.getHeight());
	    int h = image_qrcode.getHeight() + image_pin.getHeight();
	    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics g = combined.getGraphics();
	    g.drawImage(image_qrcode, 0, 0, null);
	    g.drawImage(image_pin, 0, image_qrcode.getWidth(), null);
	    // Salva nuovo file
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(combined, "png", baos);
	    baos.flush();
	    immageCombinata = baos.toByteArray();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return immageCombinata;
    }

    private byte[] downloadDocumentiMovimentoGUEST(QrcodeHelper qrcodeHelper) {

	String urlToCovert = "downloadDocumentiMovimentoGUEST";
	byte[] b = null;
	if (qrcodeHelper.getWith() != null && qrcodeHelper.getHigth() != null) {
	    b = this.createQRcode(urlToCovert, qrcodeHelper.getWith(), qrcodeHelper.getHigth(), TipoImmagine.PNG);
	} else {
	    b = this.createQRcode(urlToCovert, null, null, TipoImmagine.PNG);
	}
	return b;
    }

    private byte[] downloadDocumentiMovimentoAUTH(QrcodeHelper qrcodeHelper) {

	String urlToCovert = "downloadDocumentiMovimentoAUTH";
	byte[] b = null;
	if (qrcodeHelper.getWith() != null && qrcodeHelper.getHigth() != null) {
	    b = this.createQRcode(urlToCovert, qrcodeHelper.getWith(), qrcodeHelper.getHigth(), TipoImmagine.PNG);
	} else {
	    b = this.createQRcode(urlToCovert, null, null, TipoImmagine.PNG);
	}
	return b;
    }

    //    private byte[] visurapraticaPIN(QrcodeHelper qrcodeHelper, String guid) {
    //
    //	String urlToCovert = "visurapraticaPIN";
    //	byte[] b = null;
    //	if (qrcodeHelper.getWith() != null && qrcodeHelper.getHigth() != null) {
    //	    b = this.createQRcode(urlToCovert, qrcodeHelper.getWith(), qrcodeHelper.getHigth());
    //	} else {
    //	    b = this.createQRcode(urlToCovert, null, null);
    //	}
    //	return b;
    //    }
    private QRCodeBean visurapraticaGUEST(QrcodeHelper qrcodeHelper, String guid, String software) {

	QRCodeBean result = new QRCodeBean();
	Verticalizzazioniparametri vpms = verticalizzazioniService.getVerticalizzazioniparametri(
		VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE,
		VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_URL_VISURA_ANONIMA);
	String url = "";
	if (vpms != null && StringUtils.isNotBlank(vpms.getValore())) {
	    url = vpms.getValore();
	}
	url = url.replace("{UUID}", guid);
	url = url.replace("{IDCOMUNEALIAS}", ORMHelper.getIdcomuneAlias());
	url = url.replace("{SOFTWARE}", software);
	byte[] b = null;
	if (qrcodeHelper.getWith() != null && qrcodeHelper.getHigth() != null) {
	    b = this.createQRcode(url, qrcodeHelper.getWith(), qrcodeHelper.getHigth(), TipoImmagine.PNG);
	} else {
	    b = this.createQRcode(url, null, null, TipoImmagine.PNG);
	}
	result.setImage(b);
	result.setUrl(url);
	return result;
    }

    //    private byte[] visurapraticaAUTH(QrcodeHelper qrcodeHelper, String guid) {
    //
    //	String urlToCovert = "visurapraticaAUTH";
    //	byte[] b = null;
    //	if (qrcodeHelper.getWith() != null && qrcodeHelper.getHigth() != null) {
    //	    b = this.createQRcode(urlToCovert, qrcodeHelper.getWith(), qrcodeHelper.getHigth());
    //	} else {
    //	    b = this.createQRcode(urlToCovert, null, null);
    //	}
    //	return b;
    //    }
    public byte[] createPIN(String text, int width, TipoImmagine tipoImmagine) {

	/*
	   Because font metrics is based on a graphics context, we need to create
	   a small, temporary image so we can ascertain the width and height
	   of the final image
	 */
	BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2d = img.createGraphics();
	Font font = new Font("Arial", Font.PLAIN, 13);
	g2d.setFont(font);
	FontMetrics fm = g2d.getFontMetrics();
	//width = 250;
	int height = fm.getHeight();
	g2d.dispose();
	img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	g2d = img.createGraphics();
	g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	g2d.setFont(font);
	g2d.setBackground(Color.WHITE);
	fm = g2d.getFontMetrics();
	g2d.setColor(Color.BLACK);
	g2d.drawString(text, 0, fm.getAscent());
	g2d.dispose();
	byte[] imageInByte = null;
	try {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(img, tipoImmagine.name().toLowerCase(), baos);
	    baos.flush();
	    imageInByte = baos.toByteArray();
	    // ImageIO.write(img, "png", new File("Text.png"));
	} catch (IOException ex) {
	    log.debug("createPIN# Errore durante la creazione dell'immagine PIN");
	}
	return imageInByte;
    }
}
