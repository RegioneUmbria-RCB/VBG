package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.ParametriConstants;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneBase;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneFlyweight;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneMail;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.LetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocollazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocolloPerEnte;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;

public class ConfigurazioneComunicazioniBollettazione extends ConfigurazioneBase implements IConfigurazioneComunicazione {

    public static final String ID_BOLLETTAZIONE = "ID_BOLLETTAZIONE";
    private String descrizione;
    private int idBollettazione;
    private boolean richiedeProtocollazione;
    private boolean trasformaAllegatiCompilabiliInPdf;
    private boolean allegaAvvisiPagamento;
    private boolean escludiDestinatariSenzaMail;
    private boolean soloPosizioniDebitorieNonPagate;
    private SceltaTipoMailAnagrafeEnum sceltaTipoMailAnagrafe;
    private List<AllegatoComunicazione> allegatiFissi = new ArrayList<AllegatoComunicazione>();
    private List<LetteraComunicazione> lettereComunicazione = new ArrayList<LetteraComunicazione>();
    private ConfigurazioneMail configurazioneMail = null;
    private List<Integer> soggettiFirmatari = new ArrayList<Integer>();
    private ParametriProtocollazione parametriProtocollazione = null;

    @Override
    public void inizializzaDaDatiDb(ConfigurazioneFlyweight cfg) {

	this.descrizione = cfg.getDescrizione();
	this.idBollettazione = getValoreDaConfigurazione(cfg.getParametri(), ID_BOLLETTAZIONE, -1);
	this.richiedeProtocollazione = getValoreDaConfigurazione(cfg.getParametri(), ParametriConstants.RICHIEDE_PROTOCOLLAZIONE, false);
	this.trasformaAllegatiCompilabiliInPdf = getValoreDaConfigurazione(cfg.getParametri(),
		ParametriConstants.TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF, false);
	this.allegaAvvisiPagamento = getValoreDaConfigurazione(cfg.getParametri(), ParametriConstants.ALLEGA_AVVISI_PAGAMENTO, false);
	this.escludiDestinatariSenzaMail = getValoreDaConfigurazione(cfg.getParametri(), ParametriConstants.ESCLUDI_DESTINATARI_SENZA_MAIL, false);
	this.soloPosizioniDebitorieNonPagate = getValoreDaConfigurazione(cfg.getParametri(), ParametriConstants.FILTRA_POSIZIONI_DEBITORIE_NON_PAGATE,
		false);
	this.sceltaTipoMailAnagrafe = getSceltaMailDaConfigurazione(cfg.getParametri(), ParametriConstants.GESTIONE_SCELTA_MAIL_ANAGRAFE,
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
	cfg.addParametro(ID_BOLLETTAZIONE, this.idBollettazione);
	cfg.addParametro(ParametriConstants.RICHIEDE_PROTOCOLLAZIONE, this.richiedeProtocollazione);
	cfg.addParametro(ParametriConstants.TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF, this.trasformaAllegatiCompilabiliInPdf);
	cfg.addParametro(ParametriConstants.ALLEGA_AVVISI_PAGAMENTO, this.allegaAvvisiPagamento);
	cfg.addParametro(ParametriConstants.ESCLUDI_DESTINATARI_SENZA_MAIL, this.escludiDestinatariSenzaMail);
	cfg.addParametro(ParametriConstants.FILTRA_POSIZIONI_DEBITORIE_NON_PAGATE, this.soloPosizioniDebitorieNonPagate);
	cfg.addParametro(ParametriConstants.GESTIONE_SCELTA_MAIL_ANAGRAFE, this.sceltaTipoMailAnagrafe.name());
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

    public int getIdBollettazione() {

	return idBollettazione;
    }

    public boolean isRichiedeProtocollazione() {

	return richiedeProtocollazione;
    }

    public boolean isTrasformaAllegatiCompilabiliInPdf() {

	return trasformaAllegatiCompilabiliInPdf;
    }

    public boolean isAllegaAvvisiPagamento() {

	return allegaAvvisiPagamento;
    }

    public boolean isEscludiDestinatariSenzaMail() {

	return escludiDestinatariSenzaMail;
    }

    public boolean isSoloPosizioniDebitorieNonPagate() {

	return soloPosizioniDebitorieNonPagate;
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

    public ParametriProtocollazione getParametriProtocollazione() {

	return parametriProtocollazione;
    }

    public List<Integer> getSoggettiFirmatari() {

	return soggettiFirmatari;
    }

    public boolean contieneAllegati() {

	return !this.allegatiFissi.isEmpty() || !this.lettereComunicazione.isEmpty() || allegaAvvisiPagamento;
    }

    public boolean prevedeFirma() {

	return !this.soggettiFirmatari.isEmpty();
    }

    public SceltaTipoMailAnagrafeEnum getSceltaTipoMailAnagrafe() {

	return sceltaTipoMailAnagrafe;
    }
}
