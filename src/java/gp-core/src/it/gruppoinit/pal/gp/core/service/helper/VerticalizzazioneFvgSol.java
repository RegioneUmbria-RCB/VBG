package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

import org.apache.commons.lang.StringUtils;

public class VerticalizzazioneFvgSol {

    private Boolean isAttiva;
    private String WEB_SERVICE_PASSWORD;
    private String WEB_SERVICE_USERNAME;
    private String WEB_SERVICE_URL;
    private String USA_BACKOFFICE_COME_CONSOLE;
    private String TEMPIFICAZIONE_ENDO_DEFAULT;
    private String AMMINISTRAZIONE_ENDO_DEFAULT;
    private String CODICENATURA_ENDO_DEFAULTCODICENATURA_ENDO_DEFAULT;
    private VerticalizzazioniService verticalizzazioniService;

    public VerticalizzazioneFvgSol(VerticalizzazioniService verticalizzazioniService, boolean isLoadparametri) {

	this.verticalizzazioniService = verticalizzazioniService;
	if (isLoadparametri) {
	    this.isAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FVG_SOL);
	    this.WEB_SERVICE_PASSWORD = getParametroVerticalizzazione(verticalizzazioniService,
		    WebConstants.VERTICALIZZAZIONE_FVG_SOL_WEB_SERVICE_PASSWORD, false);
	    this.WEB_SERVICE_URL = getParametroVerticalizzazione(verticalizzazioniService, WebConstants.VERTICALIZZAZIONE_FVG_SOL_WEB_SERVICE_URL,
		    false);
	    this.WEB_SERVICE_USERNAME = getParametroVerticalizzazione(verticalizzazioniService,
		    WebConstants.VERTICALIZZAZIONE_FVG_SOL_WEB_SERVICE_USERNAME, false);
	    this.USA_BACKOFFICE_COME_CONSOLE = getParametroVerticalizzazione(verticalizzazioniService,
		    WebConstants.VERTICALIZZAZIONE_FVG_SOL_USA_BACKOFFICE_COME_CONSOLE, false);
	    this.TEMPIFICAZIONE_ENDO_DEFAULT = getParametroVerticalizzazione(verticalizzazioniService,
		    WebConstants.VERTICALIZZAZIONE_FVG_SOL_TEMPIFICAZIONE_ENDO_DEFAULT, false);
	    this.AMMINISTRAZIONE_ENDO_DEFAULT = getParametroVerticalizzazione(verticalizzazioniService,
		    WebConstants.VERTICALIZZAZIONE_FVG_SOL_AMMINISTRAZIONE_ENDO_DEFAULT, false);
	    this.CODICENATURA_ENDO_DEFAULTCODICENATURA_ENDO_DEFAULT = getParametroVerticalizzazione(verticalizzazioniService,
		    WebConstants.VERTICALIZZAZIONE_FVG_SOL_CODICENATURA_ENDO_DEFAULT, false);
	}
    }

    public boolean isConsolleAttiva() {

	boolean isFvgSolAttivaAndConsole = false;
	this.USA_BACKOFFICE_COME_CONSOLE = getParametroVerticalizzazione(verticalizzazioniService,
		WebConstants.VERTICALIZZAZIONE_FVG_SOL_USA_BACKOFFICE_COME_CONSOLE, false);
	if (isAttiva() && "1".equals(getUSA_BACKOFFICE_COME_CONSOLE())) {
	    isFvgSolAttivaAndConsole = true;
	}
	return isFvgSolAttivaAndConsole;
    }

    public boolean isAttiva() {

	return verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FVG_SOL);
    }

    private String getParametroVerticalizzazione(VerticalizzazioniService verticalizzazioniService, String nomeParametro, boolean throwExceptionIfNull) {

	Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_FVG_SOL, nomeParametro);
	if (vp != null) {
	    String result = StringUtils.defaultString(vp.getValore()).trim();
	    if (StringUtils.isNotBlank(result)) {
		return result;
	    }
	}
	if (throwExceptionIfNull) {
	    throw new RuntimeException("Il parametro " + nomeParametro + " della verticalizzazione " + WebConstants.VERTICALIZZAZIONE_FVG_SOL
		    + " non è stato configurato correttamente");
	}
	return "";
    }

    public Boolean getIsAttiva() {

	return isAttiva;
    }

    //    public void setIsAttiva(Boolean isAttiva) {
    //
    //	this.isAttiva = isAttiva;
    //    }
    public String getWEB_SERVICE_PASSWORD() {

	return WEB_SERVICE_PASSWORD;
    }

    //    public void setWEB_SERVICE_PASSWORD(String wEB_SERVICE_PASSWORD) {
    //
    //	WEB_SERVICE_PASSWORD = wEB_SERVICE_PASSWORD;
    //    }
    public String getWEB_SERVICE_USERNAME() {

	return WEB_SERVICE_USERNAME;
    }

    //    public void setWEB_SERVICE_USERNAME(String wEB_SERVICE_USERNAME) {
    //
    //	WEB_SERVICE_USERNAME = wEB_SERVICE_USERNAME;
    //    }
    public String getWEB_SERVICE_URL() {

	return WEB_SERVICE_URL;
    }

    //    public void setWEB_SERVICE_URL(String wEB_SERVICE_URL) {
    //
    //	WEB_SERVICE_URL = wEB_SERVICE_URL;
    //    }
    public String getUSA_BACKOFFICE_COME_CONSOLE() {

	return USA_BACKOFFICE_COME_CONSOLE;
    }

    //    public void setUSA_BACKOFFICE_COME_CONSOLE(String uSA_BACKOFFICE_COME_CONSOLE) {
    //
    //	USA_BACKOFFICE_COME_CONSOLE = uSA_BACKOFFICE_COME_CONSOLE;
    //    }
    public String getTEMPIFICAZIONE_ENDO_DEFAULT() {

	return TEMPIFICAZIONE_ENDO_DEFAULT;
    }

    //    public void setTEMPIFICAZIONE_ENDO_DEFAULT(String tEMPIFICAZIONE_ENDO_DEFAULT) {
    //
    //	TEMPIFICAZIONE_ENDO_DEFAULT = tEMPIFICAZIONE_ENDO_DEFAULT;
    //    }
    public String getAMMINISTRAZIONE_ENDO_DEFAULT() {

	return AMMINISTRAZIONE_ENDO_DEFAULT;
    }

    //    public void setAMMINISTRAZIONE_ENDO_DEFAULT(String aMMINISTRAZIONE_ENDO_DEFAULT) {
    //
    //	AMMINISTRAZIONE_ENDO_DEFAULT = aMMINISTRAZIONE_ENDO_DEFAULT;
    //    }
    public String getCODICENATURA_ENDO_DEFAULTCODICENATURA_ENDO_DEFAULT() {

	return CODICENATURA_ENDO_DEFAULTCODICENATURA_ENDO_DEFAULT;
    }
    //    public void setCODICENATURA_ENDO_DEFAULTCODICENATURA_ENDO_DEFAULT(String cODICENATURA_ENDO_DEFAULTCODICENATURA_ENDO_DEFAULT) {
    //
    //	CODICENATURA_ENDO_DEFAULTCODICENATURA_ENDO_DEFAULT = cODICENATURA_ENDO_DEFAULTCODICENATURA_ENDO_DEFAULT;
    //    }
}
