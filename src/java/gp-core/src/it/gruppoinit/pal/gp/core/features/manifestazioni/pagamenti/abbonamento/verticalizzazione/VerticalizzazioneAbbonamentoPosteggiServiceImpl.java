package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.ComportamentoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.RipartizioneContiHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.StrategiaInvioComunicazioniEnum;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;

public class VerticalizzazioneAbbonamentoPosteggiServiceImpl implements IVerticalizzazioneAbbonamentoPosteggiService {

    private VerticalizzazioniService verticalizzazioniService;
    private ContiService contiService;
    private MailtipoService mailTipoService;
    private AmministrazioniService amministrazioniService;
    private String codiceComune;
    private static final String NOME_VERTICALIZZAZIONE = "ABBONAMENTO_POSTEGGI";
    private static final String PAR_COMPORTAMENTO = "COMPORTAMENTO";
    private static final String PAR_RIPARTIZIONE_CONTI_RICARICHE = "RIPARTIZIONE_CONTI_RICARICHE";
    private static final String PAR_SOGLIA_AVVISO = "SOGLIA_AVVISO";
    private static final String PAR_BLOCCA_ASSEGNAZIONE = "BLOCCA_ASSEGNAZIONE";
    private static final String PAR_STRATEGIA_INVIO_COMUNICAZIONI = "STRATEGIA_INVIO_COMUNICAZIONI";
    private static final String PAR_MESSAGGIO_CREDITO_SOTTO_SOGLIA = "MESSAGGIO_CREDITO_SOTTO_SOGLIA";
    private static final String PAR_COM_CHIUSURA_GG_DESCRIZIONE = "COM_CHIUSURA_GG_DESCRIZIONE";
    private static final String PAR_COMUNICAZIONI_PROT_TEMPLATE = "COMUNICAZIONI_PROT_TEMPLATE";
    private static final String PAR_COMUNICAZIONI_PROT_UFFICIO = "COMUNICAZIONI_PROT_UFFICIO";
    private static final String PAR_COMUNICAZIONI_PROT_CLASSIFICA = "COMUNICAZIONI_PROT_CLASSIFICA";
    private static final String PAR_COMUNICAZIONI_PROT_TIPODOC = "COMUNICAZIONI_PROT_TIPODOC";
    private static final String PAR_COMUNICAZIONI_MAIL_ESCLUSIONI = "COMUNICAZIONI_MAIL_ESCLUSIONI";
    private static final String PAR_COMUNICAZIONI_DESTINATARI = "COMUNICAZIONI_DESTINATARI";
    private static final String PAR_COMUNICAZIONI_MAIL_SENDER = "COMUNICAZIONI_MAIL_SENDER";
    private static final String PAR_COM_CHIUSURA_GG_TEMPLATE = "COM_CHIUSURA_GG_TEMPLATE";
    // 
    private static final String PAR_COM_NPD_CRED_INS_DESCRIZIONE = "COM_NPD_CRED_INS_DESCRIZIONE";
    private static final String PAR_COM_NPD_CRED_INS_TEMPLATE = "COM_NPD_CRED_INS_TEMPLATE";
    private static final String MESSAGGIO_PREDEFINITO_CREDITO_SOTTO_SOGLIA = "L'autorizzazione ha attivato un abbonamento ma il suo credito è sotto la soglia configurata";

    public VerticalizzazioneAbbonamentoPosteggiServiceImpl(VerticalizzazioniService verticalizzazioniService, ContiService contiService,
	    MailtipoService mailTipoService, AmministrazioniService amministrazioniService, String codiceComune) {

	if (codiceComune == null) {
	    throw new IllegalArgumentException(
		    "È stata richiamata la verticalizzazione " + NOME_VERTICALIZZAZIONE + " senza passare il codice comune");
	}
	this.codiceComune = codiceComune;
	this.verticalizzazioniService = verticalizzazioniService;
	this.contiService = contiService;
	this.mailTipoService = mailTipoService;
	this.amministrazioniService = amministrazioniService;
    }

    @Override
    public boolean isAttiva() {

	return this.verticalizzazioniService.isAttivaPerComune(NOME_VERTICALIZZAZIONE, this.codiceComune);
    }

    @Override
    public ComportamentoEnum comportamento() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_COMPORTAMENTO, this.codiceComune);
	if (parametro == null) {
	    return ComportamentoEnum.fromDefault();
	}
	return ComportamentoEnum.fromValue(parametro.getValore());
    }

    @Override
    public RipartizioneContiHelper ripartizione() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_RIPARTIZIONE_CONTI_RICARICHE, this.codiceComune);
	if (parametro == null || StringUtils.isBlank(parametro.getValore())) {
	    throw new IllegalArgumentException("Nessun conto configurato per la verticalizzazione " + NOME_VERTICALIZZAZIONE);
	}
	return RipartizioneContiHelper.fromParametro(parametro.getValore(), contiService);
    }

    @Override
    public BigDecimal sogliaAvviso() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_SOGLIA_AVVISO, this.codiceComune);
	if (parametro == null) {
	    return BigDecimal.ZERO;
	}
	return new BigDecimal(parametro.getValore());
    }

    @Override
    public boolean isBloccaAssegnazioni() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_BLOCCA_ASSEGNAZIONE, this.codiceComune);
	if (parametro == null) {
	    return Boolean.FALSE;
	}
	return "1".equals(parametro.getValore());
    }

    @Override
    public String messaggioCreditoSottoSoglia() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_MESSAGGIO_CREDITO_SOTTO_SOGLIA, this.codiceComune);
	if (parametro == null || StringUtils.isBlank(parametro.getValore())) {
	    return MESSAGGIO_PREDEFINITO_CREDITO_SOTTO_SOGLIA;
	}
	return parametro.getValore();
    }

    @Override
    public String descrizioneComunicazioneChiusuraGiornata() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_COM_CHIUSURA_GG_DESCRIZIONE, this.codiceComune);
	if (parametro == null || StringUtils.isBlank(parametro.getValore())) {
	    return null;
	}
	return parametro.getValore();
    }

    @Override
    public Mailtipo templateProtocollazione() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_COMUNICAZIONI_PROT_TEMPLATE, this.codiceComune);
	if (parametro == null) {
	    return null;
	}
	return this.mailTipoService.findById(new PkId(Integer.valueOf(parametro.getValore())));
    }

    @Override
    public Amministrazioni amministrazione() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_COMUNICAZIONI_PROT_UFFICIO, this.codiceComune);
	if (parametro == null) {
	    return null;
	}
	return this.amministrazioniService.findById(new PkId(Integer.valueOf(parametro.getValore())));
    }

    @Override
    public String classifica() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_COMUNICAZIONI_PROT_CLASSIFICA, this.codiceComune);
	if (parametro == null || StringUtils.isBlank(parametro.getValore())) {
	    return null;
	}
	return parametro.getValore();
    }

    @Override
    public String tipoDocumento() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_COMUNICAZIONI_PROT_TIPODOC, this.codiceComune);
	if (parametro == null || StringUtils.isBlank(parametro.getValore())) {
	    return null;
	}
	return parametro.getValore();
    }

    @Override
    public boolean escludiDestinatariSenzaMail() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_COMUNICAZIONI_MAIL_ESCLUSIONI, this.codiceComune);
	if (parametro == null) {
	    return Boolean.FALSE;
	}
	return "1".equals(parametro.getValore());
    }

    @Override
    public SceltaTipoMailAnagrafeEnum tipoMailAnagrafe() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_COMUNICAZIONI_DESTINATARI, this.codiceComune);
	if (parametro == null) {
	    return SceltaTipoMailAnagrafeEnum.fromDefault();
	}
	return SceltaTipoMailAnagrafeEnum.fromName(parametro.getValore());
    }

    @Override
    public Integer senderAccountId() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_COMUNICAZIONI_MAIL_SENDER, this.codiceComune);
	if (parametro == null) {
	    return null;
	}
	return new Integer(parametro.getValore());
    }

    @Override
    public Mailtipo templateMailChiusuraGiornata() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_COM_CHIUSURA_GG_TEMPLATE, this.codiceComune);
	if (parametro == null) {
	    return null;
	}
	return this.mailTipoService.findById(new PkId(Integer.valueOf(parametro.getValore())));
    }

    @Override
    public String descrizioneComunicazioneAperturaPosizioneDebitoriaCreditoInsufficiente() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_COM_NPD_CRED_INS_DESCRIZIONE, this.codiceComune);
	if (parametro == null || StringUtils.isBlank(parametro.getValore())) {
	    return null;
	}
	return parametro.getValore();
    }

    @Override
    public Mailtipo templateMailAperturaPosizioneDebitoriaCreditoInsufficiente() {

	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_COM_NPD_CRED_INS_TEMPLATE, this.codiceComune);
	if (parametro == null) {
	    return null;
	}
	return this.mailTipoService.findById(new PkId(Integer.valueOf(parametro.getValore())));
    }

    @Override
    public boolean strategiaInvioComunicazioniSupportata(StrategiaInvioComunicazioniEnum strategiaDaSupportare) {

	if (strategiaDaSupportare == null) {
	    return false;
	}
	Verticalizzazioniparametri parametro = this.verticalizzazioniService.getVerticalizzazioniparametriPerComune(NOME_VERTICALIZZAZIONE,
		PAR_STRATEGIA_INVIO_COMUNICAZIONI, this.codiceComune);
	if (parametro == null) {
	    return false;
	}
	String valore = StringUtils.defaultIfEmpty(parametro.getValore(), "").trim().toUpperCase();
	String[] valori = valore.split(",");
	for (String v : valori) {
	    StrategiaInvioComunicazioniEnum strategia = StrategiaInvioComunicazioniEnum
		    .fromValue(StringUtils.defaultString(v, StrategiaInvioComunicazioniEnum.NESSUNA.name()).trim());
	    if (strategia.equals(strategiaDaSupportare)) {
		return true;
	    }
	}
	return false;
    }
}
