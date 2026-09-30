package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneBase;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneFlyweight;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneMail;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.LetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocollazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocolloPerEnte;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;

public class ConfigurazioneComunicazioniCommissioni extends ConfigurazioneBase implements IConfigurazioneComunicazione {

    public static final String ID_COMMISSIONE = "ID_COMMISSIONE";
    public static final String RICHIEDE_PROTOCOLLAZIONE = "RICHIEDE_PROTOCOLLAZIONE";
    public static final String TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF = "TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF";
    public static final String ESCLUDI_DESTINATARI_SENZA_MAIL = "ESCLUDI_DESTINATARI_SENZA_MAIL";
    public static final String GESTIONE_SCELTA_MAIL_ANAGRAFE = "GESTIONE_SCELTA_MAIL_ANAGRAFE";
    private String descrizione;
    private int idCommissione;
    private boolean richiedeProtocollazione;
    private boolean trasformaAllegatiCompilabiliInPdf;
    private boolean escludiDestinatariSenzaMail;
    private SceltaTipoMailAnagrafeEnum sceltaTipoMailAnagrafe;
    private List<AllegatoComunicazione> allegatiFissi = new ArrayList<AllegatoComunicazione>();
    private List<LetteraComunicazione> lettereComunicazione = new ArrayList<LetteraComunicazione>();
    private ConfigurazioneMail configurazioneMail = null;
    private List<Integer> soggettiFirmatari = new ArrayList<Integer>();
    private ParametriProtocollazione parametriProtocollazione = null;

    @Override
    public void inizializzaDaDatiDb(ConfigurazioneFlyweight cfg) {

	this.descrizione = cfg.getDescrizione();
	this.idCommissione = getValoreDaConfigurazione(cfg.getParametri(), ID_COMMISSIONE, -1);
	this.richiedeProtocollazione = getValoreDaConfigurazione(cfg.getParametri(), RICHIEDE_PROTOCOLLAZIONE, false);
	this.trasformaAllegatiCompilabiliInPdf = getValoreDaConfigurazione(cfg.getParametri(), TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF, false);
	this.escludiDestinatariSenzaMail = getValoreDaConfigurazione(cfg.getParametri(), ESCLUDI_DESTINATARI_SENZA_MAIL, false);
	this.sceltaTipoMailAnagrafe = getSceltaMailDaConfigurazione(cfg.getParametri(), GESTIONE_SCELTA_MAIL_ANAGRAFE,
		SceltaTipoMailAnagrafeEnum.SOLO_MAIL);
	for (AllegatoComunicazione allegatoComunicazione : cfg.getAllegatiFissi()) {
	    this.allegatiFissi.add(allegatoComunicazione);
	}
	for (LetteraComunicazione allegatoComunicazione : cfg.getLettereComunicazione()) {
	    this.lettereComunicazione.add(allegatoComunicazione);
	}
	if (cfg.getConfigurazioneMail() != null) {
	    this.configurazioneMail = new ConfigurazioneMail(cfg.getConfigurazioneMail().getSenderAccount(),
		    cfg.getConfigurazioneMail().getIdMailTipo());
	}
	for (Integer idFirmatario : cfg.getSoggettiFirmatari()) {
	    this.soggettiFirmatari.add(idFirmatario);
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
	cfg.addParametro(ID_COMMISSIONE, this.idCommissione);
	cfg.addParametro(RICHIEDE_PROTOCOLLAZIONE, this.richiedeProtocollazione);
	cfg.addParametro(TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF, this.trasformaAllegatiCompilabiliInPdf);
	cfg.addParametro(ESCLUDI_DESTINATARI_SENZA_MAIL, this.escludiDestinatariSenzaMail);
	cfg.addParametro(GESTIONE_SCELTA_MAIL_ANAGRAFE, this.sceltaTipoMailAnagrafe.name());
	for (AllegatoComunicazione allegatoComunicazione : this.allegatiFissi) {
	    cfg.getAllegatiFissi().add(allegatoComunicazione);
	}
	for (LetteraComunicazione allegatoComunicazione : this.lettereComunicazione) {
	    cfg.getLettereComunicazione().add(allegatoComunicazione);
	}
	cfg.setConfigurazioneMail(this.configurazioneMail);
	for (Integer idFirmatario : this.soggettiFirmatari) {
	    cfg.getSoggettiFirmatari().add(idFirmatario);
	}
	cfg.setParametriProtocollazione(this.parametriProtocollazione);
	return cfg;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public int getIdCommissione() {

	return idCommissione;
    }

    public boolean isRichiedeProtocollazione() {

	return richiedeProtocollazione;
    }

    public boolean isTrasformaAllegatiCompilabiliInPdf() {

	return trasformaAllegatiCompilabiliInPdf;
    }

    public boolean isEscludiDestinatariSenzaMail() {

	return escludiDestinatariSenzaMail;
    }

    public SceltaTipoMailAnagrafeEnum getSceltaTipoMailAnagrafe() {

	return sceltaTipoMailAnagrafe;
    }

    public List<AllegatoComunicazione> getAllegatiFissi() {

	return allegatiFissi;
    }

    public List<LetteraComunicazione> getLettereComunicazione() {

	return lettereComunicazione;
    }

    public ConfigurazioneMail getConfigurazioneMail() {

	return configurazioneMail;
    }

    public List<Integer> getSoggettiFirmatari() {

	return soggettiFirmatari;
    }

    public ParametriProtocollazione getParametriProtocollazione() {

	return parametriProtocollazione;
    }

    public boolean contieneAllegati() {

	return !this.allegatiFissi.isEmpty() || !this.lettereComunicazione.isEmpty();
    }

    public boolean prevedeFirma() {

	return !this.soggettiFirmatari.isEmpty();
    }
}
