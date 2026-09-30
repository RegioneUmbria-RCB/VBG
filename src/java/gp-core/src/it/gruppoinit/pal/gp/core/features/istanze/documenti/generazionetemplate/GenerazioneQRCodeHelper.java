package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.VerticalizzazioneQRCodeServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

public class GenerazioneQRCodeHelper {

    private static final Logger log = LoggerFactory.getLogger(GenerazioneQRCodeHelper.class);
    private String template;
    private float posX;
    private float posY;
    private float heigth;
    private float width;
    private String chiaveMAC;
    private String url;
    private String codLetteraTipo;
    private Integer numPag;

    private void getParametriDaVerticalizzazione(VerticalizzazioniService verticalizzazioniService) {

	Map<String, String> vp = verticalizzazioniService
		.getVerticalizzazioniparametriMap(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE);
	String _posX = vp.get(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_TEMPLATE_POS_X);
	this.posX = StringUtils.isBlank(_posX) ? 0 : new Float(_posX);
	String _posY = vp.get(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_TEMPLATE_POS_Y);
	this.posY = StringUtils.isBlank(_posY) ? 0 : new Float(_posY);
	String _heigth = vp.get(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_TEMPLATE_HEIGHT);
	this.heigth = StringUtils.isBlank(_heigth) ? 100 : new Float(_heigth);
	String _width = vp.get(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_TEMPLATE_WIDTH);
	this.width = StringUtils.isBlank(_width) ? 100 : new Float(_width);
	String _chiaveMAC = vp.get(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_TEMPLATE_CHIAVE_MAC);
	this.chiaveMAC = StringUtils.isBlank(_chiaveMAC) ? null : _chiaveMAC;
	String _url = vp.get(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_TEMPLATE_URL_DOWNLOAD);
	this.url = StringUtils.isBlank(_url) ? null : _url;
	String _letteraTipo = vp.get(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_TEMPLATE_RIF_LETTERA_TIPO);
	this.codLetteraTipo = StringUtils.isBlank(_letteraTipo) ? null : _letteraTipo;
	String _numPag = vp.get(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_TEMPLATE_PAGE_NUM);
	this.numPag = StringUtils.isBlank(_numPag) ? 1 : Integer.valueOf(_numPag);
    }

    public GenerazioneQRCodeHelper(VerticalizzazioniService verticalizzazioniService) {

	if (!new VerticalizzazioneQRCodeServiceImpl(verticalizzazioniService, ORMHelper.getIdcomune()).isAttiva()) {
	    log.debug("La verticalizzazione {} non è attiva", VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE);
	} else {
	    boolean isAttivoLayer = new VerticalizzazioneQRCodeServiceImpl(verticalizzazioniService, ORMHelper.getIdcomune()).isAttivoTemplate();
	    if (!isAttivoLayer) {
		log.error("La verticalizzazione {} non è attiva", VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE_TEMPLATE_AUTOMATICO);
	    } else {
		this.getParametriDaVerticalizzazione(verticalizzazioniService);
	    }
	}
    }

    public String getTemplate() {

	return template;
    }

    public void setTemplate(String template) {

	this.template = template;
    }

    public float getPosX() {

	return posX;
    }

    public void setPosX(float posX) {

	this.posX = posX;
    }

    public float getPosY() {

	return posY;
    }

    public void setPosY(float posY) {

	this.posY = posY;
    }

    public float getHeigth() {

	return heigth;
    }

    public void setHeigth(float heigth) {

	this.heigth = heigth;
    }

    public float getWidth() {

	return width;
    }

    public void setWidth(float width) {

	this.width = width;
    }

    public String getChiaveMAC() {

	return chiaveMAC;
    }

    public void setChiaveMAC(String chiaveMAC) {

	this.chiaveMAC = chiaveMAC;
    }

    public String getUrl() {

	return url;
    }

    public void setUrl(String url) {

	this.url = url;
    }

    public String getCodLetteraTipo() {

	return codLetteraTipo;
    }

    public void setCodLetteraTipo(String codLetteraTipo) {

	this.codLetteraTipo = codLetteraTipo;
    }

    public Integer getNumPag() {

	return numPag;
    }

    public void setNumPag(Integer numPag) {

	this.numPag = numPag;
    }
}
