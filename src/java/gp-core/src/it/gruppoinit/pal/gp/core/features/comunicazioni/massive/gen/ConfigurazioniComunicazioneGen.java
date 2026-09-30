package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
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
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;

public class ConfigurazioniComunicazioneGen extends ConfigurazioneBase implements IConfigurazioneComunicazione{

    private String descrizione;
    private int idGen; //generalmente si farà riferimento a una tabella, e al suo relativo record
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
    private ContestoComunicazioneEnum contesto = null;
    
    //ALTRI PARAMETRI
    private String servizioAppIo;
    private String oggetto;
    private String bodymail;
    private List<String> warnings;
    
    
    //MERCATI
    private boolean isSpuntisti;
    private boolean isConcessionario;
    private boolean isAutorizzazioniGroup;
    private Date dataInizio;
    private Date dataFine;
    private int[] idsmercati;
    private boolean istitolare;
    
    //ISTANZE
    private boolean isIstanzeGroup;
    private int[] idsistanze;
    private boolean isMovimenti;
    private String tipomovimento;
    private Integer amministrazione;
    private boolean isRichiedente;
    private boolean isIntermediario;
    private IstanzeFilter filter;
    
    
    public ConfigurazioniComunicazioneGen(ContestoComunicazioneEnum contesto) {
	this.contesto = contesto;
    }
    
    public ConfigurazioniComunicazioneGen(ContestoComunicazioneEnum contesto, int idGen) {
	this.contesto = contesto;
	this.idGen = idGen;
    }
    
    @Override
    public void inizializzaDaDatiDb(ConfigurazioneFlyweight cfg) {

	this.descrizione = cfg.getDescrizione();
	this.richiedeProtocollazione = getValoreDaConfigurazione(cfg.getParametri(), ParametriConstants.RICHIEDE_PROTOCOLLAZIONE, false);
	this.trasformaAllegatiCompilabiliInPdf = getValoreDaConfigurazione(cfg.getParametri(),
		ParametriConstants.TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF, false);
	this.allegaAvvisiPagamento = getValoreDaConfigurazione(cfg.getParametri(), ParametriConstants.ALLEGA_AVVISI_PAGAMENTO, false);
	this.escludiDestinatariSenzaMail = getValoreDaConfigurazione(cfg.getParametri(), ParametriConstants.ESCLUDI_DESTINATARI_SENZA_MAIL, false);
	this.soloPosizioniDebitorieNonPagate = getValoreDaConfigurazione(cfg.getParametri(), ParametriConstants.FILTRA_POSIZIONI_DEBITORIE_NON_PAGATE,
		false);
	this.sceltaTipoMailAnagrafe = getSceltaMailDaConfigurazione(cfg.getParametri(), ParametriConstants.GESTIONE_SCELTA_MAIL_ANAGRAFE,
		null);
	
	this.servizioAppIo = getValoreDaConfigurazione(cfg.getParametri(), ParametriConstants.APPIO_SERVIZIO, null);
	this.oggetto = getValoreDaConfigurazione(cfg.getParametri(), ParametriConstants.OGGETTOMAIL_NAME, null);
	this.bodymail = getValoreDaConfigurazione(cfg.getParametri(), ParametriConstants.BODYMAIL_NAME, null);
	
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
    
    public boolean isAppioInvio(){
	return servizioAppIo != null;
    }

    @Override
    public ConfigurazioneFlyweight getParametriPerDb() {

	ConfigurazioneFlyweight cfg = new ConfigurazioneFlyweight();
	cfg.setDescrizione(this.descrizione);
	//cfg.addParametro(ID_BOLLETTAZIONE, this.idBollettazione);
	cfg.addParametro(ParametriConstants.RICHIEDE_PROTOCOLLAZIONE, this.richiedeProtocollazione);
	cfg.addParametro(ParametriConstants.TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF, this.trasformaAllegatiCompilabiliInPdf);
	cfg.addParametro(ParametriConstants.ALLEGA_AVVISI_PAGAMENTO, this.allegaAvvisiPagamento);
	cfg.addParametro(ParametriConstants.ESCLUDI_DESTINATARI_SENZA_MAIL, this.escludiDestinatariSenzaMail);
	cfg.addParametro(ParametriConstants.FILTRA_POSIZIONI_DEBITORIE_NON_PAGATE, this.soloPosizioniDebitorieNonPagate);
	
	if(this.sceltaTipoMailAnagrafe != null){
	    cfg.addParametro(ParametriConstants.GESTIONE_SCELTA_MAIL_ANAGRAFE, this.sceltaTipoMailAnagrafe.name());
	}
	if(this.servizioAppIo != null) {
	    cfg.addParametro(ParametriConstants.APPIO_SERVIZIO, this.servizioAppIo);
	}
	
	if(!StringUtils.isBlank(this.oggetto)){
		cfg.addParametro(ParametriConstants.OGGETTOMAIL_NAME, this.oggetto); 
	}
	if(!StringUtils.isBlank(this.bodymail)){
	        cfg.addParametro(ParametriConstants.BODYMAIL_NAME, this.bodymail);
	}

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

    
    public int getIdGen() {
    
        return idGen;
    }
    
    public void setIdGen(int idGen) {
    
        this.idGen = idGen;
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

    public ContestoComunicazioneEnum getContesto() {
    
        return contesto;
    }

    
    public void setContesto(ContestoComunicazioneEnum contesto) {
    
        this.contesto = contesto;
    }

    
    public boolean isSpuntisti() {
    
        return isSpuntisti;
    }

    public void setSpuntisti(boolean isSpuntisti) {
	    
        this.isSpuntisti = isSpuntisti;
    }
    
    public boolean isConcessionario() {
    
        return isConcessionario;
    }

    public void setConcessionario(boolean isConcessionario) {
	    
        this.isConcessionario = isConcessionario;
    }
    
    public boolean isAutorizzazioniGroup() {
    
        return isAutorizzazioniGroup;
    }

    
    public void setAutorizzazioniGroup(boolean isAutorizzazioniGroup) {
    
        this.isAutorizzazioniGroup = isAutorizzazioniGroup;
    }

    
    public Date getDataInizio() {
    
        return dataInizio;
    }

    
    public void setDataInizio(Date dataInizio) {
    
        this.dataInizio = dataInizio;
    }

    
    public Date getDataFine() {
    
        return dataFine;
    }

    
    public void setDataFine(Date dataFine) {
    
        this.dataFine = dataFine;
    }

    
    public boolean isIstanzeGroup() {
    
        return isIstanzeGroup;
    }

    
    public void setIstanzeGroup(boolean isIstanzeGroup) {
    
        this.isIstanzeGroup = isIstanzeGroup;
    }

    public int[] getIdsistanze() {
    
        return idsistanze;
    }

    
    public void setIdsistanze(int[] idsistanze) {
    
        this.idsistanze = idsistanze;
    }

    
    public int[] getIdsmercati() {
    
        return idsmercati;
    }

    
    public void setIdsmercati(int[] idsmercati) {
    
        this.idsmercati = idsmercati;
    }

    
    public boolean isMovimenti() {
    
        return isMovimenti;
    }

    
    public void setMovimenti(boolean isMovimenti) {
    
        this.isMovimenti = isMovimenti;
    }

    
    public String getTipomovimento() {
    
        return tipomovimento;
    }

    
    public void setTipomovimento(String tipomovimento) {
    
        this.tipomovimento = tipomovimento;
    }

    
    public Integer getAmministrazione() {
    
        return amministrazione;
    }

    
    public void setAmministrazione(Integer amministrazione) {
    
        this.amministrazione = amministrazione;
    }

    
    public boolean isIstitolare() {
    
        return istitolare;
    }

    
    public void setIstitolare(boolean istitolare) {
    
        this.istitolare = istitolare;
    }

    
    public boolean isRichiedente() {
    
        return isRichiedente;
    }

    
    public void setRichiedente(boolean isRichiedente) {
    
        this.isRichiedente = isRichiedente;
    }

    
    public boolean isIntermediario() {
    
        return isIntermediario;
    }

    
    public void setIntermediario(boolean isIntermediario) {
    
        this.isIntermediario = isIntermediario;
    }

    
    public IstanzeFilter getFilter() {
    
        return filter;
    }

    
    public void setFilter(IstanzeFilter filter) {
    
        this.filter = filter;
    }

    
    public List<String> getWarnings() {
    
        return warnings;
    }

    
    public void setWarnings(List<String> warnings) {
    
        this.warnings = warnings;
    }
    
}
