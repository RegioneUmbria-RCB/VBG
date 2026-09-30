package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.opensaml.artifact.InvalidArgumentException;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneBase;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneFlyweight;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneMail;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocollazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocolloPerEnte;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.IVerticalizzazioneAbbonamentoPosteggiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class ConfigurazioneComunicazioniManifestazioni extends ConfigurazioneBase implements IConfigurazioneComunicazione {

    public enum TIPO_COMUNICAZIONE {
	CHIUSURA_GIORNATA,
	APERTURA_POS_CREDITO_INSUFFICIENTE,
	NON_DEFINITA
    }

    public static final String ID_GIORNATA_PARAM = "ID_GIORNATA";
    public static final String TIPO_COMUNICAZIONE_PARAM = "TIPO_COMUNICAZIONE";
    public static final String RICHIEDE_PROTOCOLLAZIONE = "RICHIEDE_PROTOCOLLAZIONE";
    public static final String ESCLUDI_DESTINATARI_SENZA_MAIL = "ESCLUDI_DESTINATARI_SENZA_MAIL";
    public static final String GESTIONE_SCELTA_MAIL_ANAGRAFE = "GESTIONE_SCELTA_MAIL_ANAGRAFE";
    private static final String SEGNAPOSTO_GIORNATA = "[-GIORNATA-]";
    private static final String DESCRIZIONE_CHIUSURA_GIORNATA_DEFAULT = "Chiusura giornata " + SEGNAPOSTO_GIORNATA;
    private static final String DESCRIZIONE_APERUTA_POS_DEB_CREDITO_INSUFF_DEFAULT = "Notifica apertura posizioni debitorie per credito insufficiente " +
	    SEGNAPOSTO_GIORNATA;
    private int idGiornata;
    private TIPO_COMUNICAZIONE tipoComunicazione;
    private String descrizione;
    private boolean richiedeProtocollazione = false;
    private boolean escludiDestinatariSenzaMail;
    private SceltaTipoMailAnagrafeEnum sceltaTipoMailAnagrafe;
    private ConfigurazioneMail configurazioneMail = null;
    private ParametriProtocollazione parametriProtocollazione = null;

    public static ConfigurazioneComunicazioniManifestazioni fromGiornataManifestazione(IVerticalizzazioneAbbonamentoPosteggiService vertService,
	    MercatipresenzeT giornata) {

	if (giornata == null || giornata.getId() == null || giornata.getId().getCodice() == null) {
	    throw new InvalidArgumentException(
		    "Impossibile inizializzare la configurazione della comunicazione senza passare la giornata di riferimento");
	}
	ConfigurazioneComunicazioniManifestazioni config = new ConfigurazioneComunicazioniManifestazioni();
	//1. Id della giornata
	config.idGiornata = giornata.getId().getCodice();
	//2. Descrizione
	config.descrizione = vertService.descrizioneComunicazioneChiusuraGiornata();
	if (StringUtils.isBlank(config.descrizione)) {
	    config.descrizione = DESCRIZIONE_CHIUSURA_GIORNATA_DEFAULT;
	}
	config.tipoComunicazione = TIPO_COMUNICAZIONE.CHIUSURA_GIORNATA;
	config.descrizione = config.descrizione.replace(SEGNAPOSTO_GIORNATA, Utilities.formatDate(giornata.getDataRegistrazione(), false));
	//3. Protocollazione
	if (vertService.templateProtocollazione() != null) {
	    //3.1 Attivazione della protocollazione
	    config.richiedeProtocollazione = true;
	    //3.2 Parametri di protocollo per ente
	    ParametriProtocolloPerEnte parametro = new ParametriProtocolloPerEnte(vertService.amministrazione().getId().getCodice(),
		    vertService.classifica(), vertService.tipoDocumento(), giornata.getMercato().getComune().getCodicecomune(), null);
	    List<ParametriProtocolloPerEnte> parametri = new ArrayList<ParametriProtocolloPerEnte>();
	    parametri.add(parametro);
	    config.parametriProtocollazione = new ParametriProtocollazione(vertService.templateProtocollazione().getId().getCodice(), parametri);
	}
	if (vertService.senderAccountId() != null) {
	    //4. Esclusione dei destinatari senza mail
	    config.escludiDestinatariSenzaMail = vertService.escludiDestinatariSenzaMail();
	    //5. Scelta mail tipo anagrafe
	    config.sceltaTipoMailAnagrafe = vertService.tipoMailAnagrafe();
	    //6. Mail sender e testo tipo
	    config.configurazioneMail = new ConfigurazioneMail(vertService.senderAccountId(),
		    vertService.templateMailChiusuraGiornata().getId().getCodice());
	}
	return config;
    }

    public static ConfigurazioneComunicazioniManifestazioni fromCreditoInsufficientePresenza(IVerticalizzazioneAbbonamentoPosteggiService vertService,
	    MercatipresenzeT giornata) {

	if (giornata == null || giornata.getId() == null || giornata.getId().getCodice() == null) {
	    throw new InvalidArgumentException(
		    "Impossibile inizializzare la configurazione della comunicazione senza passare la presenza di riferimento");
	}
	ConfigurazioneComunicazioniManifestazioni config = new ConfigurazioneComunicazioniManifestazioni();
	//1. Id della giornata
	config.idGiornata = giornata.getId().getCodice();
	//2. Descrizione
	config.descrizione = vertService.descrizioneComunicazioneAperturaPosizioneDebitoriaCreditoInsufficiente();
	if (StringUtils.isBlank(config.descrizione)) {
	    config.descrizione = DESCRIZIONE_APERUTA_POS_DEB_CREDITO_INSUFF_DEFAULT;
	}
	config.tipoComunicazione = TIPO_COMUNICAZIONE.APERTURA_POS_CREDITO_INSUFFICIENTE;
	config.descrizione = config.descrizione.replace(SEGNAPOSTO_GIORNATA, Utilities.formatDate(giornata.getDataRegistrazione(), false));
	//3. Protocollazione
	if (vertService.templateProtocollazione() != null) {
	    //3.1 Attivazione della protocollazione
	    config.richiedeProtocollazione = true;
	    //3.2 Parametri di protocollo per ente
	    ParametriProtocolloPerEnte parametro = new ParametriProtocolloPerEnte(vertService.amministrazione().getId().getCodice(),
		    vertService.classifica(), vertService.tipoDocumento(), giornata.getMercato().getComune().getCodicecomune(), null);
	    List<ParametriProtocolloPerEnte> parametri = new ArrayList<ParametriProtocolloPerEnte>();
	    parametri.add(parametro);
	    config.parametriProtocollazione = new ParametriProtocollazione(vertService.templateProtocollazione().getId().getCodice(), parametri);
	}
	if (vertService.senderAccountId() != null) {
	    //4. Esclusione dei destinatari senza mail
	    config.escludiDestinatariSenzaMail = vertService.escludiDestinatariSenzaMail();
	    //5. Scelta mail tipo anagrafe
	    config.sceltaTipoMailAnagrafe = vertService.tipoMailAnagrafe();
	    //6. Mail sender e testo tipo
	    config.configurazioneMail = new ConfigurazioneMail(vertService.senderAccountId(),
		    vertService.templateMailAperturaPosizioneDebitoriaCreditoInsufficiente().getId().getCodice());
	}
	return config;
    }

    public int getIdGiornata() {

	return idGiornata;
    }

    public SceltaTipoMailAnagrafeEnum getSceltaTipoMailAnagrafe() {

	return sceltaTipoMailAnagrafe;
    }

    public boolean isRichiedeProtocollazione() {

	return richiedeProtocollazione;
    }

    public ParametriProtocollazione getParametriProtocollazione() {

	return parametriProtocollazione;
    }

    public ConfigurazioneMail getConfigurazioneMail() {

	return configurazioneMail;
    }

    @Override
    public void inizializzaDaDatiDb(ConfigurazioneFlyweight cfg) {

	this.descrizione = cfg.getDescrizione();
	this.idGiornata = getValoreDaConfigurazione(cfg.getParametri(), ID_GIORNATA_PARAM, -1);
	this.tipoComunicazione = decodeTipoComunicazione(getValoreDaConfigurazione(cfg.getParametri(), TIPO_COMUNICAZIONE_PARAM, ""));
	this.richiedeProtocollazione = getValoreDaConfigurazione(cfg.getParametri(), RICHIEDE_PROTOCOLLAZIONE, false);
	this.escludiDestinatariSenzaMail = getValoreDaConfigurazione(cfg.getParametri(), ESCLUDI_DESTINATARI_SENZA_MAIL, false);
	this.sceltaTipoMailAnagrafe = getSceltaMailDaConfigurazione(cfg.getParametri(), GESTIONE_SCELTA_MAIL_ANAGRAFE,
		SceltaTipoMailAnagrafeEnum.PEC_O_MAIL);
	if (cfg.getConfigurazioneMail() != null) {
	    this.configurazioneMail = new ConfigurazioneMail(cfg.getConfigurazioneMail().getSenderAccount(),
		    cfg.getConfigurazioneMail().getIdMailTipo());
	}
	if (cfg.getParametriProtocollazione() != null) {
	    List<ParametriProtocolloPerEnte> params = cfg.getParametriProtocollazione().getParametriPerEnte();
	    this.parametriProtocollazione = new ParametriProtocollazione(cfg.getParametriProtocollazione().getCodiceMailtipo(), params);
	}
    }

    @Override
    public ConfigurazioneFlyweight getParametriPerDb() {

	ConfigurazioneFlyweight cfg = new ConfigurazioneFlyweight();
	cfg.setDescrizione(this.descrizione);
	cfg.addParametro(ID_GIORNATA_PARAM, this.idGiornata);
	cfg.addParametro(TIPO_COMUNICAZIONE_PARAM, this.tipoComunicazione.name());
	cfg.addParametro(RICHIEDE_PROTOCOLLAZIONE, this.richiedeProtocollazione);
	cfg.addParametro(ESCLUDI_DESTINATARI_SENZA_MAIL, this.escludiDestinatariSenzaMail);
	cfg.addParametro(GESTIONE_SCELTA_MAIL_ANAGRAFE, this.sceltaTipoMailAnagrafe.name());
	cfg.setConfigurazioneMail(this.configurazioneMail);
	cfg.setParametriProtocollazione(this.parametriProtocollazione);
	return cfg;
    }

    private TIPO_COMUNICAZIONE decodeTipoComunicazione(String valoreDaConfigurazione) {

	if (StringUtils.isBlank(valoreDaConfigurazione)) {
	    return TIPO_COMUNICAZIONE.NON_DEFINITA;
	}
	if (valoreDaConfigurazione.equalsIgnoreCase(TIPO_COMUNICAZIONE.APERTURA_POS_CREDITO_INSUFFICIENTE.name())) {
	    return TIPO_COMUNICAZIONE.APERTURA_POS_CREDITO_INSUFFICIENTE;
	}
	if (valoreDaConfigurazione.equalsIgnoreCase(TIPO_COMUNICAZIONE.CHIUSURA_GIORNATA.name())) {
	    return TIPO_COMUNICAZIONE.CHIUSURA_GIORNATA;
	}
	throw new InvalidArgumentException("TIPO_COMUNICAZIONE non valida: [" + valoreDaConfigurazione + "]");
    }

    public TIPO_COMUNICAZIONE getTipoComunicazione() {

	return this.tipoComunicazione;
    }
}
