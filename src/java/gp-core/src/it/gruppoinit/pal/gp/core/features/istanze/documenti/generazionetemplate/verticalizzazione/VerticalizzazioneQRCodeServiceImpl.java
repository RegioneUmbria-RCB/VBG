package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

public class VerticalizzazioneQRCodeServiceImpl implements IVerticalizzazioneQRCodeService {

    
    private VerticalizzazioniService service;
    private boolean attiva = false;
    private String codiceComune;
    public static final String VERTICALIZZAZIONE_QRCODE = "QRCODE";
    public static final String VERTICALIZZAZIONE_QRCODE_URL_VISURA_ANONIMA = "URL_VISURA_ANONIMA";
    public static final String VERTICALIZZAZIONE_QRCODE_URL_VISURA_AUTENTICAZIONE = "URL_VISURA_AUTENTICAZIONE";
    public static final String VERTICALIZZAZIONE_QRCODE_URL_VISURA_PIN = "URL_VISURA_PIN";
    public static final String VERTICALIZZAZIONE_QRCODE_URL_WS_RIEPILOGO_PRATICA = "URL_WS_RIEPILOGO_PRATICA";
    public static final String VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_URL_DOWNLOAD = "ALLEGATOPDF_URL_DOWNLOAD";
    public static final String VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_WIDTH = "ALLEGATOPDF_WIDTH";
    public static final String VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_HEIGHT = "ALLEGATOPDF_HEIGHT";
    public static final String VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_POS_X = "ALLEGATOPDF_POS_X";
    public static final String VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_POS_Y = "ALLEGATOPDF_POS_Y";
    public static final String VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_PAGE_NUM = "ALLEGATOPDF_PAGE_NUM";
    public static final String VERTICALIZZAZIONE_QRCODE_ALLEGATOPDF_AUTOMATICO = "ALLEGATOPDF_AUTOMATICO";
    public static final String VERTICALIZZAZIONE_QRCODE_TEMPLATE_AUTOMATICO = "TEMPLATE_AUTOMATICO";
    public static final String VERTICALIZZAZIONE_QRCODE_TEMPLATE_CHIAVE_MAC = "TEMPLATE_CHIAVE_MAC";
    public static final String VERTICALIZZAZIONE_QRCODE_TEMPLATE_HEIGHT = "TEMPLATE_HEIGHT";
    public static final String VERTICALIZZAZIONE_QRCODE_TEMPLATE_PAGE_NUM = "TEMPLATE_PAGE_NUM";
    public static final String VERTICALIZZAZIONE_QRCODE_TEMPLATE_POS_X = "TEMPLATE_POS_X";
    public static final String VERTICALIZZAZIONE_QRCODE_TEMPLATE_POS_Y = "TEMPLATE_POS_Y";
    public static final String VERTICALIZZAZIONE_QRCODE_TEMPLATE_URL_DOWNLOAD = "TEMPLATE_URL_DOWNLOAD";
    public static final String VERTICALIZZAZIONE_QRCODE_TEMPLATE_WIDTH = "TEMPLATE_WIDTH";
    public static final String VERTICALIZZAZIONE_QRCODE_TEMPLATE_RIF_LETTERA_TIPO = "TEMPLATE_RIF_LETTERA_TIPO";

    public VerticalizzazioneQRCodeServiceImpl(VerticalizzazioniService service, String codiceComune) {

	if (codiceComune == null) {
	    throw new IllegalArgumentException("È stata richiamata la verticalizzazione del QRCODE senza passare il codice comune");
	}
	this.service = service;
	this.attiva = isAttivaInternal();
	this.codiceComune = codiceComune;
    }

    private boolean isAttivaInternal() {

	return this.service.isAttivaPerComune(VerticalizzazioneQRCodeServiceImpl.VERTICALIZZAZIONE_QRCODE, codiceComune);
    }

    @Override
    public boolean isAttiva() {

	return this.attiva;
    }

    @Override
    public boolean isAttivoTemplate() {

	if (this.isAttiva()) {
	    Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(VERTICALIZZAZIONE_QRCODE,
		    VERTICALIZZAZIONE_QRCODE_TEMPLATE_AUTOMATICO, ORMHelper.getIdcomune());
	    return vp != null ? vp.getValore().equalsIgnoreCase("S") : false;
	}
	return false;
    }

    @Override
    public String getMac() {

	if (this.isAttiva()) {
	    Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(VERTICALIZZAZIONE_QRCODE,
		    VERTICALIZZAZIONE_QRCODE_TEMPLATE_CHIAVE_MAC, ORMHelper.getIdcomune());
	    return StringUtils.isNotEmpty(vp.getValore()) ? vp.getValore() : null;
	}
	return null;
    }

    @Override
    public String getUrl() {

	if (this.isAttiva()) {
	    Verticalizzazioniparametri vp = this.service.getVerticalizzazioniparametriPerComune(VERTICALIZZAZIONE_QRCODE,
		    VERTICALIZZAZIONE_QRCODE_TEMPLATE_URL_DOWNLOAD, ORMHelper.getIdcomune());
	    return StringUtils.isNotEmpty(vp.getValore()) ? vp.getValore() : null;
	}
	return null;
    }
}
