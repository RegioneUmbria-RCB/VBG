package it.gruppoinit.pal.gp.core.features.areariservata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

@Service
public class VerticalizzazioneAreaRiservataServiceImpl implements IVerticalizzazioneAreaRiservataService {

    @Autowired
    private VerticalizzazioniService service;
    @Autowired
    private MailtipoService mailTipoService;
    public static final String NOME_VERTICALIZZAZIONE = "AREA_RISERVATA";
    private static final String PAR_AREA_RISERVATA_JAVA_ATTIVA = "AREA_RISERVATA_JAVA_ATTIVA";
    private static final String PAR_CENTRO_SERVIZI = "CENTRO_SERVIZI";
    private static final String PAR_CENTRO_SERVIZI_URL_BREVI = "CENTRO_SERVIZI_URL_BREVI";
    private static final String PAR_REG_INVMAIL_ATTIVA_INVIO = "REG_INVMAIL_ATTIVA_INVIO";
    private static final String PAR_REG_INVMAIL_LISTA_ID_CALLER = "REG_INVMAIL_LISTA_ID_CALLER";
    private static final String PAR_REG_INVMAIL_TIPO_MODELLO = "REG_INVMAIL_TIPO_MODELLO";
    private static final String PAR_REG_INVMAIL_MODELLO_RESET = "REG_INVMAIL_MODELLO_RESET";
    private static final String PAR_URL_SERVLET_RESETCREDENZIALI = "URL_SERVLET_RESETCREDENZIALI";
    private static final String PAR_URL_AUTHENTICATION_OVERRIDE = "URL_AUTHENTICATION_OVERRIDE";
    public static final String PAR_URL_GENERA_RICEVUTA_PRATICA = "URL_GENERA_RICEVUTA_PRATICA";
    private static final String PAR_NOME_FILE_RICEVUTA = "NOME_FILE_RICEVUTA";
    private static final String PAR_DESCRIZIONE_FILE_RICEVUTA = "DESCRIZIONE_FILE_RICEVUTA";
    private static final String PAR_URL_AVVIO_PROCEDIMENTO = "URL_AVVIO_PROCEDIMENTO";
    private static final String PAR_JSON_URL_SERVIZI_CONDIVISI = "JSON_URL_SERVIZI_CONDIVISI";
    private static final String PAR_ISTANZEPRES_POSIZIONEARCHIVIO = "ISTANZEPRES_POSIZIONEARCHIVIO";

    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE);
    }

    @Override
    public boolean isAreaRiservataJavaAttiva() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_AREA_RISERVATA_JAVA_ATTIVA, "S", false);
    }

    @Override
    public boolean isCentroServizi() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_CENTRO_SERVIZI, "1", false);
    }

    @Override
    public boolean isCentroServiziSoloUrlBrevi() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	if (!isCentroServizi()) {
	    return false;
	}
	return this.service.getBoolean(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_CENTRO_SERVIZI_URL_BREVI, "1", false);
    }

    @Override
    public boolean isInvioMailNuovoUtenteRegistrato() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_REG_INVMAIL_ATTIVA_INVIO, "S", false);
    }

    @Override
    public String getIDCallersPerInvioMailNuovoUtenteRegistrato() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	if (!this.isInvioMailNuovoUtenteRegistrato()) {
	    return null;
	}
	return this.service.getString(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_REG_INVMAIL_LISTA_ID_CALLER);
    }

    @Override
    public Mailtipo getModelloPerInvioMailNuovoUtenteRegistrato() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	if (!this.isInvioMailNuovoUtenteRegistrato()) {
	    return null;
	}
	Integer idMailTipo = this.service.getInteger(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_REG_INVMAIL_TIPO_MODELLO);
	if (idMailTipo == null) {
	    return null;
	}
	return this.mailTipoService.findById(new PkId(idMailTipo));
    }

    @Override
    public Mailtipo getModelloPerInvioMailResetCredenziali() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	Integer idMailTipo = this.service.getInteger(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_REG_INVMAIL_MODELLO_RESET);
	if (idMailTipo == null) {
	    return null;
	}
	return this.mailTipoService.findById(new PkId(idMailTipo));
    }

    @Override
    public String getUrlServletPerResetCredenziali() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_URL_SERVLET_RESETCREDENZIALI);
    }

    @Override
    public String getUrlAuthenticationOverride() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_URL_AUTHENTICATION_OVERRIDE);
    }

    @Override
    public String getUrlRigenerazioneRicevutaPratica() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_URL_GENERA_RICEVUTA_PRATICA);
    }

    @Override
    public String getNomeFileRicevuta() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_NOME_FILE_RICEVUTA);
    }

    @Override
    public String getDescrizioneFileRicevuta() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_DESCRIZIONE_FILE_RICEVUTA);
    }

    @Override
    public String getUrlAvvioProcedimento() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_URL_AVVIO_PROCEDIMENTO);
    }

    @Override
    public String getUrlJsonServiziCondivisi() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getString(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_JSON_URL_SERVIZI_CONDIVISI);
    }

    @Override
    public boolean isMostraPosizioneArchivioSuIstanzePresentate() {

	if (!this.isAttiva()) {
	    throw new IllegalArgumentException(
		    "La verticalizzazione " + VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva.");
	}
	return this.service.getBoolean(VerticalizzazioneAreaRiservataServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneAreaRiservataServiceImpl.PAR_ISTANZEPRES_POSIZIONEARCHIVIO, "1", false);
    }
}
