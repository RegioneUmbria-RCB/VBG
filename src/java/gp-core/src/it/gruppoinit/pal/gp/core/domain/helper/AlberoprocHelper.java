package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAteco;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtatt;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLeggi;
import it.gruppoinit.pal.gp.core.domain.AlberoprocOneri;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisogBack;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.GruppiIstruttori;
import it.gruppoinit.pal.gp.core.domain.LdpDecodifiche;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlberoprocHelper {

    private Alberoproc currentAlberoproc;
    private Responsabili respistruttoria;
    private Responsabili responsabile;
    private Responsabili operatoreStc;
    private String progressivoistanze;
    private Tipiprocedure tipoProcedura;
    private Tipimovimento movAvvio;
    private Azioni azione;
    private Tipologiaregistri tipologiaregistro;
    private Boolean presentabileOnline;
    private Boolean grpFlagAssegnazioneAut;
    private Boolean grpFlagAccettazione;
    private GruppiIstruttori gruppiIstruttori;
    private Amministrazioni amministrazioni;
    private Set<AlberoprocDocumenti> alberoprocDocumentis = new HashSet<AlberoprocDocumenti>();
    // Usato se serve ordinare la collezione per un campo tramite l'interfaccia Comparable
    private List<AlberoprocDocumenti> alberoprocDocumentiList = new ArrayList<AlberoprocDocumenti>();
    private Set<AlberoprocLeggi> alberoprocLeggis = new HashSet<AlberoprocLeggi>();
    private Set<AlberoprocEndo> alberoprocEndos = new HashSet<AlberoprocEndo>();
    private Set<AlberoprocDyn2modellit> alberoprocDyn2modellits = new HashSet<AlberoprocDyn2modellit>();
    private Set<AlberoprocD2modtatt> alberoprocD2modtatts = new HashSet<AlberoprocD2modtatt>();
    private Set<AlberoprocRuoli> alberoprocRuolis = new HashSet<AlberoprocRuoli>();
    private Set<AlberoprocOneri> alberoprocOneris = new HashSet<AlberoprocOneri>();
    private Set<AlberoprocAteco> alberoprocAtecos = new HashSet<AlberoprocAteco>();
    private Set<AlberoprocTipisoggetto> alberoprocTipisoggettos = new HashSet<AlberoprocTipisoggetto>();
    private Set<AlberoprocArendo> alberoprocArendos = new HashSet<AlberoprocArendo>();
    private Set<AlberoprocTipisogBack> alberoprocTipisogBacks = new HashSet<AlberoprocTipisogBack>();
    private List<String> listNote = new ArrayList<String>();
    private List<PercorsoAlberoprocHelper> percorsoAlberoprocHelpers = new ArrayList<PercorsoAlberoprocHelper>();
    private boolean flagOggettoPraticaDefault;
    private LdpDecodifiche ldpOccupazionis;
    private LdpDecodifiche ldpGeometries;
    private LdpDecodifiche ldpPeriodis;

    public AlberoprocHelper() {

	this.tipologiaregistro = new Tipologiaregistri();
    }

    public Set<AlberoprocDocumenti> getAlberoprocDocumentis() {

	return alberoprocDocumentis;
    }

    public void setAlberoprocDocumentis(Set<AlberoprocDocumenti> alberoprocDocumentis) {

	this.alberoprocDocumentis = alberoprocDocumentis;
    }

    public List<AlberoprocDocumenti> getAlberoprocDocumentiList() {

	return alberoprocDocumentiList;
    }

    public void setAlberoprocDocumentiList(List<AlberoprocDocumenti> alberoprocDocumentiList) {

	this.alberoprocDocumentiList = alberoprocDocumentiList;
    }

    public Set<AlberoprocLeggi> getAlberoprocLeggis() {

	return alberoprocLeggis;
    }

    public void setAlberoprocLeggis(Set<AlberoprocLeggi> alberoprocLeggis) {

	this.alberoprocLeggis = alberoprocLeggis;
    }

    public Set<AlberoprocEndo> getAlberoprocEndos() {

	return alberoprocEndos;
    }

    public void setAlberoprocEndos(Set<AlberoprocEndo> alberoprocEndos) {

	this.alberoprocEndos = alberoprocEndos;
    }

    public Set<AlberoprocDyn2modellit> getAlberoprocDyn2modellits() {

	return alberoprocDyn2modellits;
    }

    public void setAlberoprocDyn2modellits(Set<AlberoprocDyn2modellit> alberoprocDyn2modellits) {

	this.alberoprocDyn2modellits = alberoprocDyn2modellits;
    }

    public Set<AlberoprocRuoli> getAlberoprocRuolis() {

	return alberoprocRuolis;
    }

    public void setAlberoprocRuolis(Set<AlberoprocRuoli> alberoprocRuolis) {

	this.alberoprocRuolis = alberoprocRuolis;
    }

    public Set<AlberoprocOneri> getAlberoprocOneris() {

	return alberoprocOneris;
    }

    public void setAlberoprocOneris(Set<AlberoprocOneri> alberoprocOneris) {

	this.alberoprocOneris = alberoprocOneris;
    }

    public Set<AlberoprocAteco> getAlberoprocAtecos() {

	return alberoprocAtecos;
    }

    public void setAlberoprocAtecos(Set<AlberoprocAteco> alberoprocAtecos) {

	this.alberoprocAtecos = alberoprocAtecos;
    }

    public void setAlberoprocTipisoggettos(Set<AlberoprocTipisoggetto> alberoprocTipisoggettos) {

	this.alberoprocTipisoggettos = alberoprocTipisoggettos;
    }

    public Set<AlberoprocTipisoggetto> getAlberoprocTipisoggettos() {

	return alberoprocTipisoggettos;
    }

    public Alberoproc getCurrentAlberoproc() {

	return currentAlberoproc;
    }

    public void setCurrentAlberoproc(Alberoproc currentAlberoproc) {

	this.currentAlberoproc = currentAlberoproc;
    }

    public Responsabili getRespistruttoria() {

	return respistruttoria;
    }

    public void setRespistruttoria(Responsabili respistruttoria) {

	this.respistruttoria = respistruttoria;
    }

    public Responsabili getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(Responsabili responsabile) {

	this.responsabile = responsabile;
    }

    public void setOperatoreStc(Responsabili operatoreStc) {

	this.operatoreStc = operatoreStc;
    }

    public Responsabili getOperatoreStc() {

	return operatoreStc;
    }

    public String getProgressivoistanze() {

	return progressivoistanze;
    }

    public void setProgressivoistanze(String progressivoistanze) {

	this.progressivoistanze = progressivoistanze;
    }

    public Tipiprocedure getTipoProcedura() {

	return tipoProcedura;
    }

    public void setTipoProcedura(Tipiprocedure tipoProcedura) {

	this.tipoProcedura = tipoProcedura;
    }

    public Azioni getAzione() {

	return azione;
    }

    public void setAzione(Azioni azione) {

	this.azione = azione;
    }

    public Tipologiaregistri getTipologiaregistro() {

	return tipologiaregistro;
    }

    public void setTipologiaregistro(Tipologiaregistri tipologiaregistro) {

	this.tipologiaregistro = tipologiaregistro;
    }

    public Boolean getPresentabileOnline() {

	return presentabileOnline;
    }

    public void setPresentabileOnline(Boolean presentabileOnline) {

	this.presentabileOnline = presentabileOnline;
    }

    public Boolean getGrpFlagAssegnazioneAut() {

	return grpFlagAssegnazioneAut;
    }

    public void setGrpFlagAssegnazioneAut(Boolean grpFlagAssegnazioneAut) {

	this.grpFlagAssegnazioneAut = grpFlagAssegnazioneAut;
    }

    public Boolean getGrpFlagAccettazione() {

	return grpFlagAccettazione;
    }

    public void setGrpFlagAccettazione(Boolean grpFlagAccettazione) {

	this.grpFlagAccettazione = grpFlagAccettazione;
    }

    public GruppiIstruttori getGruppiIstruttori() {

	return gruppiIstruttori;
    }

    public void setGruppiIstruttori(GruppiIstruttori gruppiIstruttori) {

	this.gruppiIstruttori = gruppiIstruttori;
    }

    public void setMovAvvio(Tipimovimento movAvvio) {

	this.movAvvio = movAvvio;
    }

    public Tipimovimento getMovAvvio() {

	return movAvvio;
    }

    public Set<AlberoprocD2modtatt> getAlberoprocD2modtatts() {

	return alberoprocD2modtatts;
    }

    public void setAlberoprocD2modtatts(Set<AlberoprocD2modtatt> alberoprocD2modtatts) {

	this.alberoprocD2modtatts = alberoprocD2modtatts;
    }

    public Set<AlberoprocArendo> getAlberoprocArendos() {

	return alberoprocArendos;
    }

    public void setAlberoprocArendos(Set<AlberoprocArendo> alberoprocArendos) {

	this.alberoprocArendos = alberoprocArendos;
    }

    public Set<AlberoprocTipisogBack> getAlberoprocTipisogBacks() {

	return alberoprocTipisogBacks;
    }

    public void setAlberoprocTipisogBacks(Set<AlberoprocTipisogBack> alberoprocTipisogBacks) {

	this.alberoprocTipisogBacks = alberoprocTipisogBacks;
    }

    public List<String> getListNote() {

	return listNote;
    }

    public void setListNote(List<String> listNote) {

	this.listNote = listNote;
    }

    public boolean isFlagOggettoPraticaDefault() {

	return flagOggettoPraticaDefault;
    }

    public void setFlagOggettoPraticaDefault(boolean flagOggettoPraticaDefault) {

	this.flagOggettoPraticaDefault = flagOggettoPraticaDefault;
    }

    public List<PercorsoAlberoprocHelper> getPercorsoAlberoprocHelpers() {

	return percorsoAlberoprocHelpers;
    }

    public void setPercorsoAlberoprocHelpers(List<PercorsoAlberoprocHelper> percorsoAlberoprocHelpers) {

	this.percorsoAlberoprocHelpers = percorsoAlberoprocHelpers;
    }

    public LdpDecodifiche getLdpOccupazionis() {

	return ldpOccupazionis;
    }

    public void setLdpOccupazionis(LdpDecodifiche ldpOccupazionis) {

	this.ldpOccupazionis = ldpOccupazionis;
    }

    public LdpDecodifiche getLdpGeometries() {

	return ldpGeometries;
    }

    public void setLdpGeometries(LdpDecodifiche ldpGeometries) {

	this.ldpGeometries = ldpGeometries;
    }

    public LdpDecodifiche getLdpPeriodis() {

	return ldpPeriodis;
    }

    public void setLdpPeriodis(LdpDecodifiche ldpPeriodis) {

	this.ldpPeriodis = ldpPeriodis;
    }

    public Amministrazioni getAmministrazioni() {

	return amministrazioni;
    }

    public void setAmministrazioni(Amministrazioni amministrazioni) {

	this.amministrazioni = amministrazioni;
    }
}
