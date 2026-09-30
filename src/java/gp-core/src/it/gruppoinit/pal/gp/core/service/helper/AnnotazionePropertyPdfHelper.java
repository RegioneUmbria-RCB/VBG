package it.gruppoinit.pal.gp.core.service.helper;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

public class AnnotazionePropertyPdfHelper {

    private static final Logger log = LoggerFactory.getLogger(AnnotazionePropertyPdfHelper.class);
    private float dimensioneRettangoloX;
    private float dimensioneRettangoloY;
    private float posizioneRettangoloX;
    private float posizioneRettangoloY;
    private String coloreFontRGB;
    private String coloreSfondoAnnotationRBG;
    private Integer paginaAnnotation;
    private int lockAnnotation;
    private String annotatazione;
    private byte[] filePdf;
    private String nomeFile;
    private Integer codiceMailTipo;
    private Integer codiceMailTipoMovimento;
    private boolean isBordoAnnotazioneAttivo;

    public AnnotazionePropertyPdfHelper(VerticalizzazioniService verticalizzazioniService) {

	if (!verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF, WebConstants.SOFTWARE_TT)) {
	    throw new RuntimeException("La verticalizzazione " + WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF + " non è attiva.");
	}
	Map<String, String> vertParams = verticalizzazioniService
		.getVerticalizzazioniparametriMap(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF);
	// DIM X rettangolo
	String _dimensioneRettangoloX = vertParams.get(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_DIM_RETTANGOLO_X);
	if (StringUtils.isBlank(_dimensioneRettangoloX)) {
	    log.debug("AnnotazionePropertyPdfHelper# parametro {} vuoto applico un valore di default di 190",
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_DIM_RETTANGOLO_X);
	    this.dimensioneRettangoloX = 190;
	} else {
	    try {
		Integer i = Integer.parseInt(_dimensioneRettangoloX);
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} : {}",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_DIM_RETTANGOLO_X, i.floatValue());
		this.dimensioneRettangoloX = i.floatValue();
	    } catch (NumberFormatException ne) {
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} non corretto applico un valore di default di 190",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_DIM_RETTANGOLO_X);
		this.dimensioneRettangoloX = 190;
	    }
	}
	// DIM Y rettangolo
	String _dimensioneRettangoloY = vertParams.get(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_DIM_RETTANGOLO_Y);
	if (StringUtils.isBlank(_dimensioneRettangoloY)) {
	    log.debug("AnnotazionePropertyPdfHelper# parametro {} vuoto applico un valore di default di 50",
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_DIM_RETTANGOLO_Y);
	    this.dimensioneRettangoloY = 50;
	} else {
	    try {
		Integer i = Integer.parseInt(_dimensioneRettangoloY);
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} : {}",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_DIM_RETTANGOLO_Y, i.floatValue());
		this.dimensioneRettangoloY = i.floatValue();
	    } catch (NumberFormatException ne) {
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} non corretto applico un valore di default di 50",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_DIM_RETTANGOLO_Y);
		dimensioneRettangoloY = 50;
	    }
	}
	// POSIZIONE X rettangolo
	String _posizioneRettangoloX = vertParams.get(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_POSIZIONE_X);
	if (StringUtils.isBlank(_posizioneRettangoloX)) {
	    log.debug("AnnotazionePropertyPdfHelper# parametro {} vuoto applico un valore di default di 0",
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_POSIZIONE_X);
	    this.posizioneRettangoloX = 0;
	} else {
	    try {
		Integer i = Integer.parseInt(_posizioneRettangoloX);
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} : {}", WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_POSIZIONE_X,
			i.floatValue());
		this.posizioneRettangoloX = i.floatValue();
	    } catch (NumberFormatException ne) {
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} non corretto applico un valore di default di 0",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_POSIZIONE_X);
		posizioneRettangoloX = 0;
	    }
	}
	// POSIZIONE Y rettangolo
	String _posizioneRettangoloY = vertParams.get(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_POSIZIONE_Y);
	if (StringUtils.isBlank(_posizioneRettangoloY)) {
	    log.debug("AnnotazionePropertyPdfHelper# parametro {} vuoto applico un valore di default di 0",
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_POSIZIONE_Y);
	    this.posizioneRettangoloY = 0;
	} else {
	    try {
		Integer i = Integer.parseInt(_posizioneRettangoloY);
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} : {}", WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_POSIZIONE_Y,
			i.floatValue());
		this.posizioneRettangoloY = i.floatValue();
	    } catch (NumberFormatException ne) {
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} non corretto applico un valore di default di 0",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_POSIZIONE_Y);
		posizioneRettangoloY = 0;
	    }
	}
	// COD_MAIL_TIPO
	String _cod_mail_tipo = vertParams.get(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COD_MAIL_TIPO);
	if (StringUtils.isBlank(_cod_mail_tipo)) {
	    log.debug("AnnotazionePropertyPdfHelper# parametro {} vuoto l'annotazione sarà vuota",
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COD_MAIL_TIPO);
	} else {
	    try {
		Integer i = Integer.parseInt(_cod_mail_tipo);
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} : {}",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COD_MAIL_TIPO, i);
		this.codiceMailTipo = i;
	    } catch (NumberFormatException ne) {
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} non corretto, l'annotazione sarà vuota",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COD_MAIL_TIPO);
	    }
	}
	// COD_MAIL_TIPO_MOV
	String _cod_mail_tipo_mov = vertParams.get(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COD_MAIL_TIPO_MOV);
	if (StringUtils.isBlank(_cod_mail_tipo_mov)) {
	    log.debug("AnnotazionePropertyPdfHelper# parametro {} vuoto l'annotazione sarà vuota",
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COD_MAIL_TIPO_MOV);
	} else {
	    try {
		Integer i = Integer.parseInt(_cod_mail_tipo_mov);
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} : {}",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COD_MAIL_TIPO_MOV, i);
		this.codiceMailTipoMovimento = i;
	    } catch (NumberFormatException ne) {
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} non corretto, l'annotazione sarà vuota",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COD_MAIL_TIPO_MOV);
	    }
	}
	// BACKGROUND COLOR ANNOTATION
	this.coloreSfondoAnnotationRBG = vertParams.get(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COLORE_BACKGROUND_ANNOTATION);
	if (StringUtils.isBlank(this.coloreSfondoAnnotationRBG)) {
	    log.debug("AnnotazionePropertyPdfHelper# parametro {} vuoto l'annotazione non avrà un background color impostato",
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COLORE_BACKGROUND_ANNOTATION);
	}
	// FONT COLOR ANNOTATION
	this.coloreFontRGB = vertParams.get(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COLORE_FONT_ANNOTATION);
	if (StringUtils.isBlank(this.coloreFontRGB)) {
	    log.debug("AnnotazionePropertyPdfHelper# parametro {} vuoto l'annotazione non avrà un font color impostato",
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_COLORE_FONT_ANNOTATION);
	}
	// LOCK ANNOTATION
	String _lock_annotation = vertParams.get(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_LOCK_ANNOTATION);
	if (StringUtils.isBlank(_lock_annotation)) {
	    log.debug("AnnotazionePropertyPdfHelper# parametro {} vuoto l'annotazione di defaul bloccata",
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_LOCK_ANNOTATION);
	    this.lockAnnotation = 1;
	} else {
	    try {
		Integer i = Integer.parseInt(_lock_annotation);
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} : {}",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_LOCK_ANNOTATION, i);
		if (i == 0) {
		    this.lockAnnotation = 0;
		} else {
		    this.lockAnnotation = 1;
		}
	    } catch (NumberFormatException ne) {
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} non corretto, l'annotazione di defaul bloccata",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_LOCK_ANNOTATION);
		this.lockAnnotation = 1;
	    }
	}
	// NUMERO PAGINE ANNOTAZIONE
	String _pagina_annotazione = vertParams.get(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_NUM_PAGINA_ANNOTATION);
	if (StringUtils.isBlank(_pagina_annotazione)) {
	    log.debug("AnnotazionePropertyPdfHelper# parametro {} vuoto l'annotazione di defaul imposto pagina 1",
		    WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_NUM_PAGINA_ANNOTATION);
	    this.paginaAnnotation = 1;
	} else {
	    try {
		Integer i = Integer.parseInt(_pagina_annotazione);
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} : {}",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_NUM_PAGINA_ANNOTATION, i);
		paginaAnnotation = i;
	    } catch (NumberFormatException ne) {
		log.debug("AnnotazionePropertyPdfHelper# valore parametro {} non corretto, di defaul imposto pagina 1",
			WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_NUM_PAGINA_ANNOTATION);
		this.lockAnnotation = 1;
	    }
	}
	// BORDO ANNOTAZIONE
	String _is_bordo_annotazione_attivo = vertParams.get(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF_IS_BORDO_ANNOTAZIONE_ATTIVO);
	this.isBordoAnnotazioneAttivo = false;
	if (StringUtils.isNotBlank(_is_bordo_annotazione_attivo)) {
	    this.isBordoAnnotazioneAttivo = "1".equals(_is_bordo_annotazione_attivo) ? true : false;
	}
    }

    public AnnotazionePropertyPdfHelper() {

	this.dimensioneRettangoloX = 0;
	this.dimensioneRettangoloY = 0;
	this.posizioneRettangoloX = 0;
	this.posizioneRettangoloY = 0;
    }

    public float getDimensioneRettangoloX() {

	return dimensioneRettangoloX;
    }

    public void setDimensioneRettangoloX(float dimensioneRettangoloX) {

	this.dimensioneRettangoloX = dimensioneRettangoloX;
    }

    public float getDimensioneRettangoloY() {

	return dimensioneRettangoloY;
    }

    public void setDimensioneRettangoloY(float dimensioneRettangoloY) {

	this.dimensioneRettangoloY = dimensioneRettangoloY;
    }

    public float getPosizioneRettangoloX() {

	return posizioneRettangoloX;
    }

    public void setPosizioneRettangoloX(float posizioneRettangoloX) {

	this.posizioneRettangoloX = posizioneRettangoloX;
    }

    public float getPosizioneRettangoloY() {

	return posizioneRettangoloY;
    }

    public void setPosizioneRettangoloY(float posizioneRettangoloY) {

	this.posizioneRettangoloY = posizioneRettangoloY;
    }

    public String getColoreFontRGB() {

	return coloreFontRGB;
    }

    public void setColoreFontRGB(String coloreFontRGB) {

	this.coloreFontRGB = coloreFontRGB;
    }

    public String getColoreSfondoAnnotationRBG() {

	return coloreSfondoAnnotationRBG;
    }

    public void setColoreSfondoAnnotationRBG(String coloreSfondoAnnotationRBG) {

	this.coloreSfondoAnnotationRBG = coloreSfondoAnnotationRBG;
    }

    public Integer getPaginaAnnotation() {

	return paginaAnnotation;
    }

    public void setPaginaAnnotation(Integer paginaAnnotation) {

	this.paginaAnnotation = paginaAnnotation;
    }

    public int getLockAnnotation() {

	return lockAnnotation;
    }

    public void setLockAnnotation(int lockAnnotation) {

	this.lockAnnotation = lockAnnotation;
    }

    public String getAnnotatazione() {

	return annotatazione;
    }

    public void setAnnotatazione(String annotatazione) {

	this.annotatazione = annotatazione;
    }

    public byte[] getFilePdf() {

	return filePdf;
    }

    public void setFilePdf(byte[] filePdf) {

	this.filePdf = filePdf;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public Integer getCodiceMailTipo() {

	return codiceMailTipo;
    }

    public void setCodiceMailTipo(Integer codiceMailTipo) {

	this.codiceMailTipo = codiceMailTipo;
    }

    public Integer getCodiceMailTipoMovimento() {

	return codiceMailTipoMovimento;
    }

    public void setCodiceMailTipoMovimento(Integer codiceMailTipoMovimento) {

	this.codiceMailTipoMovimento = codiceMailTipoMovimento;
    }

    public boolean isBordoAnnotazioneAttivo() {

	return isBordoAnnotazioneAttivo;
    }

    public void setBordoAnnotazioneAttivo(boolean isBordoAnnotazioneAttivo) {

	this.isBordoAnnotazioneAttivo = isBordoAnnotazioneAttivo;
    }
}
