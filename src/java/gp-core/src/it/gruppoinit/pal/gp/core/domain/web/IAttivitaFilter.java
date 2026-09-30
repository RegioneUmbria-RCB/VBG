package it.gruppoinit.pal.gp.core.domain.web;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.IAttivitaTipologie;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.Stradariozone;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipologiaistanza;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;

public class IAttivitaFilter {

    private Comuni comune;
    // id : chiave primaria
    private Integer codiceAttivita;
    private List<Integer> listaCodiceAttivita;
    private Software software;
    private String denominazioneAttivita;
    private Boolean attiva;
    private Boolean operante;
    private Boolean checkIntervento;
    private String scCodice;
    private Tipimovimento tipimovimento;
    private Settori tipoInformazione;
    private Attivita dettaglioInformazione;
    private Tipiarchivioistanze tipiarchivioistanze;
    private String posizioneInArchivio;
    private Tipologiaistanza tipologiaistanza;
    private IAttivitaTipologie attivitaTipologie;
    private String descrizioneLavori;
    private String note;
    private Anagrafe richiedente;
    private Cittadinanza cittadinanza;
    private Aree aree;
    private Stradario stradario;
    private Stradariozone stradariozone;
    private String civico;
    private Stradariocolore stradariocolore;
    private String esponente;
    private String scala;
    private String piano;
    private String interno;
    private String esponenteinterno;
    private String fabbricato;
    private String frazione;
    private String cap;
    private String quartiere;
    private Inventarioprocedimenti inventarioprocedimenti;
    private SchedaDinamicaFilter schedaDinamicaFilter;
    private Integer codSchedaPerEscludereLeAttivita;
    private Integer codiceOsservatorio;
    private Boolean soloStradarioPrimario;

    public IAttivitaFilter() {

	super();
	this.comune = new Comuni();
	this.software = new Software();
	this.tipimovimento = new Tipimovimento();
	this.tipoInformazione = new Settori();
	this.dettaglioInformazione = new Attivita();
	this.tipiarchivioistanze = new Tipiarchivioistanze();
	this.tipologiaistanza = new Tipologiaistanza();
	this.richiedente = new Anagrafe();
	this.cittadinanza = new Cittadinanza();
	this.aree = new Aree();
	this.stradario = new Stradario();
	this.stradariozone = new Stradariozone();
	this.stradariocolore = new Stradariocolore();
	this.attivitaTipologie = new IAttivitaTipologie();
	this.inventarioprocedimenti = new Inventarioprocedimenti();
	this.schedaDinamicaFilter = new SchedaDinamicaFilter();
	this.stradariozone = new Stradariozone();
	this.stradario = new Stradario();
	this.stradariocolore = new Stradariocolore();
    }

    public static IAttivitaFilter FromIAttivitaCommand(IAttivitaCommand command, AlberoprocService alberoprocService) {

	IAttivitaFilter filter = command.getAttivitaFilter();
	if (command.getAlbero() != null && command.getAlbero().getId() != null && command.getAlbero().getId().getCodice() != null) {
	    Alberoproc ap = alberoprocService.findById(new PkId(command.getAlbero().getId().getCodice()));
	    filter.setScCodice(ap.getScCodice());
	}
	return filter;
    }

    public SchedaDinamicaFilter getSchedaDinamicaFilter() {

	return schedaDinamicaFilter;
    }

    public void setSchedaDinamicaFilter(SchedaDinamicaFilter schedaDinamicaFilter) {

	this.schedaDinamicaFilter = schedaDinamicaFilter;
    }

    public Software getSoftware() {

	return software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }

    public String getDenominazioneAttivita() {

	return denominazioneAttivita;
    }

    public void setDenominazioneAttivita(String denominazioneAttivita) {

	this.denominazioneAttivita = denominazioneAttivita;
    }

    public Boolean getAttiva() {

	return attiva;
    }

    public void setAttiva(Boolean attiva) {

	this.attiva = attiva;
    }

    public Boolean getOperante() {

	return operante;
    }

    public void setOperante(Boolean operante) {

	this.operante = operante;
    }

    public Boolean getCheckIntervento() {

	return checkIntervento;
    }

    public void setCheckIntervento(Boolean checkIntervento) {

	this.checkIntervento = checkIntervento;
    }

    public String getScCodice() {

	return scCodice;
    }

    public void setScCodice(String scCodice) {

	this.scCodice = scCodice;
    }

    public Tipimovimento getTipimovimento() {

	return tipimovimento;
    }

    public void setTipimovimento(Tipimovimento tipimovimento) {

	this.tipimovimento = tipimovimento;
    }

    public Settori getTipoInformazione() {

	return tipoInformazione;
    }

    public void setTipoInformazione(Settori tipoInformazione) {

	this.tipoInformazione = tipoInformazione;
    }

    public Attivita getDettaglioInformazione() {

	return dettaglioInformazione;
    }

    public void setDettaglioInformazione(Attivita dettaglioInformazione) {

	this.dettaglioInformazione = dettaglioInformazione;
    }

    public Tipiarchivioistanze getTipiarchivioistanze() {

	return tipiarchivioistanze;
    }

    public void setTipiarchivioistanze(Tipiarchivioistanze tipiarchivioistanze) {

	this.tipiarchivioistanze = tipiarchivioistanze;
    }

    public String getPosizioneInArchivio() {

	return posizioneInArchivio;
    }

    public void setPosizioneInArchivio(String posizioneInArchivio) {

	this.posizioneInArchivio = posizioneInArchivio;
    }

    public Tipologiaistanza getTipologiaistanza() {

	return tipologiaistanza;
    }

    public void setTipologiaistanza(Tipologiaistanza tipologiaistanza) {

	this.tipologiaistanza = tipologiaistanza;
    }

    public String getDescrizioneLavori() {

	return descrizioneLavori;
    }

    public void setDescrizioneLavori(String descrizioneLavori) {

	this.descrizioneLavori = descrizioneLavori;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public Anagrafe getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(Anagrafe richiedente) {

	this.richiedente = richiedente;
    }

    public Cittadinanza getCittadinanza() {

	return cittadinanza;
    }

    public void setCittadinanza(Cittadinanza cittadinanza) {

	this.cittadinanza = cittadinanza;
    }

    public Aree getAree() {

	return aree;
    }

    public void setAree(Aree aree) {

	this.aree = aree;
    }

    public Stradario getStradario() {

	return stradario;
    }

    public void setStradario(Stradario stradario) {

	this.stradario = stradario;
    }

    public Stradariozone getStradariozone() {

	return stradariozone;
    }

    public void setStradariozone(Stradariozone stradariozone) {

	this.stradariozone = stradariozone;
    }

    public String getCivico() {

	return civico;
    }

    public void setCivico(String civico) {

	this.civico = civico;
    }

    public Stradariocolore getStradariocolore() {

	return stradariocolore;
    }

    public void setStradariocolore(Stradariocolore stradariocolore) {

	this.stradariocolore = stradariocolore;
    }

    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    public Integer getCodiceAttivita() {

	return codiceAttivita;
    }

    public void setCodiceAttivita(Integer codiceAttivita) {

	this.codiceAttivita = codiceAttivita;
    }

    public String getEsponente() {

	return esponente;
    }

    public void setEsponente(String esponente) {

	this.esponente = esponente;
    }

    public String getScala() {

	return scala;
    }

    public void setScala(String scala) {

	this.scala = scala;
    }

    public String getPiano() {

	return piano;
    }

    public void setPiano(String piano) {

	this.piano = piano;
    }

    public String getInterno() {

	return interno;
    }

    public void setInterno(String interno) {

	this.interno = interno;
    }

    public String getEsponenteinterno() {

	return esponenteinterno;
    }

    public void setEsponenteinterno(String esponenteinterno) {

	this.esponenteinterno = esponenteinterno;
    }

    public String getFabbricato() {

	return fabbricato;
    }

    public void setFabbricato(String fabbricato) {

	this.fabbricato = fabbricato;
    }

    public String getFrazione() {

	return frazione;
    }

    public void setFrazione(String frazione) {

	this.frazione = frazione;
    }

    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    public String getQuartiere() {

	return quartiere;
    }

    public void setQuartiere(String quartiere) {

	this.quartiere = quartiere;
    }

    public IAttivitaTipologie getAttivitaTipologie() {

	return attivitaTipologie;
    }

    public void setAttivitaTipologie(IAttivitaTipologie attivitaTipologie) {

	this.attivitaTipologie = attivitaTipologie;
    }

    public Inventarioprocedimenti getInventarioprocedimenti() {

	return inventarioprocedimenti;
    }

    public void setInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti) {

	this.inventarioprocedimenti = inventarioprocedimenti;
    }

    public Integer getCodSchedaPerEscludereLeAttivita() {

	return codSchedaPerEscludereLeAttivita;
    }

    public void setCodSchedaPerEscludereLeAttivita(Integer codSchedaPerEscludereLeAttivita) {

	this.codSchedaPerEscludereLeAttivita = codSchedaPerEscludereLeAttivita;
    }

    public Integer getCodiceOsservatorio() {

	return codiceOsservatorio;
    }

    public void setCodiceOsservatorio(Integer codiceOsservatorio) {

	this.codiceOsservatorio = codiceOsservatorio;
    }

    public List<Integer> getListaCodiceAttivita() {

	return listaCodiceAttivita;
    }

    public void setListaCodiceAttivita(List<Integer> listaCodiceAttivita) {

	this.listaCodiceAttivita = listaCodiceAttivita;
    }

    public Boolean getSoloStradarioPrimario() {

	return soloStradarioPrimario;
    }

    public void setSoloStradarioPrimario(Boolean soloStradarioPrimario) {

	this.soloStradarioPrimario = soloStradarioPrimario;
    }
}
