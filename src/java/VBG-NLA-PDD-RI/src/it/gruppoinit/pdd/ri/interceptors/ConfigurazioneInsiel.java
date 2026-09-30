package it.gruppoinit.pdd.ri.interceptors;

public class ConfigurazioneInsiel extends ConfigurazioneLoader {

    public ConfigurazioneInsiel(String idcomunealias) {

	super(idcomunealias + "-" + DEPLOY_PROPERTIES_CLASSPATH);
	this.mittenteCountry = getPropertyValueForIdComuneAlias("intestazione.richiesta.mittente.country", null);
	this.mittenteOrgUnit = getPropertyValueForIdComuneAlias("intestazione.richiesta.mittente.orgunit", null);
	this.mittenteText = getPropertyValueForIdComuneAlias("intestazione.richiesta.mittente.text", null);
	this.destinatarioCountry = getPropertyValueForIdComuneAlias("intestazione.richiesta.destinatario.country", null);
	this.destinatarioOrgUnit = getPropertyValueForIdComuneAlias("intestazione.richiesta.destinatario.orgunit", null);
	this.destinatarioText = getPropertyValueForIdComuneAlias("intestazione.richiesta.destinatario.text", null);
	this.servizioText = getPropertyValueForIdComuneAlias("intestazione.richiesta.servizio.text", null);
	this.comunicazioneText = getPropertyValueForIdComuneAlias("intestazione.richiesta.comunicazione.text", null);
	this.accordoServizioText = getPropertyValueForIdComuneAlias("intestazione.richiesta.accordoservizio.text", null);
    }

    //        <servicelayer:Intestazione>
    //        <servicelayer:Richiesta versione="1">
    //                        <servicelayer:Mittente country="it"  orgUnit="orgMitt">FriuliVeneziaGiulia</servicelayer:Mittente>
    //                        <servicelayer:Destinatario country="it"  orgUnit="org" >PAGenericaPortaleImprese</servicelayer:Destinatario>
    //                        <servicelayer:Servizio versione="1" metodo="richiesta-iscrizione-impresa-RI">interazioni-ri</servicelayer:Servizio>
    //                        <servicelayer:Comunicazione>EGOV_IT_ServizioSincrono</servicelayer:Comunicazione>
    //                        <servicelayer:AccordoServizio versione="1">IdentificativoAccordoServizio</servicelayer:AccordoServizio>
    //        </servicelayer:Richiesta>
    //        </servicelayer:Intestazione>
    private String mittenteCountry = "";
    private String mittenteOrgUnit = "";
    private String mittenteText = "";
    private String destinatarioCountry = "";
    private String destinatarioOrgUnit = "";
    private String destinatarioText = "";
    private String servizioText = "";
    private String comunicazioneText = "";
    private String accordoServizioText = "";

    public String getMittenteCountry() {

	return mittenteCountry;
    }

    public void setMittenteCountry(String mittenteCountry) {

	this.mittenteCountry = mittenteCountry;
    }

    public String getMittenteOrgUnit() {

	return mittenteOrgUnit;
    }

    public void setMittenteOrgUnit(String mittenteOrgUnit) {

	this.mittenteOrgUnit = mittenteOrgUnit;
    }

    public String getMittenteText() {

	return mittenteText;
    }

    public void setMittenteText(String mittenteText) {

	this.mittenteText = mittenteText;
    }

    public String getDestinatarioCountry() {

	return destinatarioCountry;
    }

    public void setDestinatarioCountry(String destinatarioCountry) {

	this.destinatarioCountry = destinatarioCountry;
    }

    public String getDestinatarioOrgUnit() {

	return destinatarioOrgUnit;
    }

    public void setDestinatarioOrgUnit(String destinatarioOrgUnit) {

	this.destinatarioOrgUnit = destinatarioOrgUnit;
    }

    public String getDestinatarioText() {

	return destinatarioText;
    }

    public void setDestinatarioText(String destinatarioText) {

	this.destinatarioText = destinatarioText;
    }

    public String getServizioText() {

	return servizioText;
    }

    public void setServizioText(String servizioText) {

	this.servizioText = servizioText;
    }

    public String getComunicazioneText() {

	return comunicazioneText;
    }

    public void setComunicazioneText(String comunicazioneText) {

	this.comunicazioneText = comunicazioneText;
    }

    public String getAccordoServizioText() {

	return accordoServizioText;
    }

    public void setAccordoServizioText(String accordoServizioText) {

	this.accordoServizioText = accordoServizioText;
    }

    private static final String DEPLOY_PROPERTIES_CLASSPATH = "insiel-interceptors.properties";
}
