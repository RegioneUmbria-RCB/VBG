package it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio;

import java.util.Date;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;

public class SorteggioDettaglioDTO {

    private Integer codiceIstanza;
    private String numeroIstanza;
    private Date dataIstanza;
    private String numeroProtocollo;
    private Date dataProtocollo;
    private String intervento;
    private String archivio;
    private String lavori;
    private String operatore;
    private String responsabileProcedimento;
    private String responsabileIstruttoria;
    private Boolean sorteggiata;
    private String richiedenteNome;
    private String richiedenteNominativo;
    private String titolareLegaleNominativo;
    private Boolean flagSpecificaDescrizione;
    private String descrSoggetto;
    private String tipoSoggetto;
    private String comune;
    private String localizzazionePrefisso;
    private String localizzazioneDescrizione;
    private String localizzazioneCAP;
    private String localizzazioneLocfraz;
    private String localizzazioneCodViario;
    private String localizzazioneCivico;
    private String localizzazioneColore;
    private Boolean localizzazionePrimaria;
    private Boolean flagInterventoObbligatorio;
    private String localizzazioneEsponente;

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public Date getDataIstanza() {

	return dataIstanza;
    }

    public void setDataIstanza(Date dataIstanza) {

	this.dataIstanza = dataIstanza;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataProtocollo() {

	return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {

	this.dataProtocollo = dataProtocollo;
    }

    public String getIntervento() {

	return intervento;
    }

    public void setIntervento(String intervento) {

	this.intervento = intervento;
    }

    public String getArchivio() {

	return archivio;
    }

    public void setArchivio(String archivio) {

	this.archivio = archivio;
    }

    public String getLavori() {

	return lavori;
    }

    public void setLavori(String lavori) {

	this.lavori = lavori;
    }

    public String getOperatore() {

	return operatore;
    }

    public void setOperatore(String operatore) {

	this.operatore = operatore;
    }

    public String getResponsabileProcedimento() {

	return responsabileProcedimento;
    }

    public void setResponsabileProcedimento(String responsabileProcedimento) {

	this.responsabileProcedimento = responsabileProcedimento;
    }

    public String getResponsabileIstruttoria() {

	return responsabileIstruttoria;
    }

    public void setResponsabileIstruttoria(String responsabileIstruttoria) {

	this.responsabileIstruttoria = responsabileIstruttoria;
    }

    public Boolean getSorteggiata() {

	return sorteggiata;
    }

    public void setSorteggiata(Boolean sorteggiata) {

	this.sorteggiata = sorteggiata;
    }

    public String getRichiedenteNome() {

	return richiedenteNome;
    }

    public void setRichiedenteNome(String richiedenteNome) {

	this.richiedenteNome = richiedenteNome;
    }

    public String getRichiedenteNominativo() {

	return richiedenteNominativo;
    }

    public void setRichiedenteNominativo(String richiedenteNominativo) {

	this.richiedenteNominativo = richiedenteNominativo;
    }

    public String getTitolareLegaleNominativo() {

	return titolareLegaleNominativo;
    }

    public void setTitolareLegaleNominativo(String titolareLegaleNominativo) {

	this.titolareLegaleNominativo = titolareLegaleNominativo;
    }

    public Boolean getFlagSpecificaDescrizione() {

	return flagSpecificaDescrizione;
    }

    public void setFlagSpecificaDescrizione(Boolean flagSpecificaDescrizione) {

	this.flagSpecificaDescrizione = flagSpecificaDescrizione;
    }

    public String getDescrSoggetto() {

	return descrSoggetto;
    }

    public void setDescrSoggetto(String descrSoggetto) {

	this.descrSoggetto = descrSoggetto;
    }

    public String getTipoSoggetto() {

	return tipoSoggetto;
    }

    public void setTipoSoggetto(String tipoSoggetto) {

	this.tipoSoggetto = tipoSoggetto;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getLocalizzazionePrefisso() {

	return localizzazionePrefisso;
    }

    public void setLocalizzazionePrefisso(String localizzazionePrefisso) {

	this.localizzazionePrefisso = localizzazionePrefisso;
    }

    public String getLocalizzazioneDescrizione() {

	return localizzazioneDescrizione;
    }

    public void setLocalizzazioneDescrizione(String localizzazioneDescrizione) {

	this.localizzazioneDescrizione = localizzazioneDescrizione;
    }

    public String getLocalizzazioneCAP() {

	return localizzazioneCAP;
    }

    public void setLocalizzazioneCAP(String localizzazioneCAP) {

	this.localizzazioneCAP = localizzazioneCAP;
    }

    public String getLocalizzazioneLocfraz() {

	return localizzazioneLocfraz;
    }

    public void setLocalizzazioneLocfraz(String localizzazioneLocfraz) {

	this.localizzazioneLocfraz = localizzazioneLocfraz;
    }

    public String getLocalizzazioneCodViario() {

	return localizzazioneCodViario;
    }

    public void setLocalizzazioneCodViario(String localizzazioneCodViario) {

	this.localizzazioneCodViario = localizzazioneCodViario;
    }

    public String getLocalizzazioneCivico() {

	return localizzazioneCivico;
    }

    public void setLocalizzazioneCivico(String localizzazioneCivico) {

	this.localizzazioneCivico = localizzazioneCivico;
    }

    public String getLocalizzazioneColore() {

	return localizzazioneColore;
    }

    public void setLocalizzazioneColore(String localizzazioneColore) {

	this.localizzazioneColore = localizzazioneColore;
    }

    public Boolean getLocalizzazionePrimaria() {

	return localizzazionePrimaria;
    }

    public void setLocalizzazionePrimaria(Boolean localizzazionePrimaria) {

	this.localizzazionePrimaria = localizzazionePrimaria;
    }

    public String getLocalizzazioneEsponente() {

	return localizzazioneEsponente;
    }

    public void setLocalizzazioneEsponente(String localizzazioneEsponente) {

	this.localizzazioneEsponente = localizzazioneEsponente;
    }

    public Boolean getFlagInterventoObbligatorio() {

	return flagInterventoObbligatorio;
    }

    public void setFlagInterventoObbligatorio(Boolean flagInterventoObbligatorio) {

	this.flagInterventoObbligatorio = flagInterventoObbligatorio;
    }

    public String getTransientRichiedenteQualitaAzienda() {

	String descrizioneRichiedente = "";
	descrizioneRichiedente = StringUtils.defaultIfEmpty(richiedenteNominativo, "");
	if (StringUtils.isNotBlank(richiedenteNome)) {
	    descrizioneRichiedente = descrizioneRichiedente.concat(" ").concat(richiedenteNome);
	}
	if (BooleanUtils.isTrue(flagSpecificaDescrizione)) {
	    if (StringUtils.isNotBlank(descrSoggetto)) {
		descrizioneRichiedente = descrizioneRichiedente.concat(" ").concat(descrSoggetto);
	    }
	} else {
	    descrizioneRichiedente = descrizioneRichiedente.concat(" ").concat(StringUtils.defaultIfEmpty(tipoSoggetto, ""));
	}
	if (StringUtils.isNotBlank(titolareLegaleNominativo)) {
	    descrizioneRichiedente = descrizioneRichiedente.concat(" ").concat(titolareLegaleNominativo);
	}
	return descrizioneRichiedente;
    }

    public String getTransientLocalizzazionePrimario() {

	String descrizioneLocalizzazione = "";
	descrizioneLocalizzazione = StringUtils.defaultIfEmpty(this.getLocalizzazionePrefisso(), "");
	descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" ").concat(StringUtils.defaultIfEmpty(this.getLocalizzazioneDescrizione(), ""));
	if (StringUtils.isNotBlank(this.getLocalizzazioneCivico())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" ").concat(this.getLocalizzazioneCivico());
	}
	if (StringUtils.isNotBlank(this.getLocalizzazioneEsponente())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" ").concat(this.getLocalizzazioneEsponente());
	}
	if (StringUtils.isNotBlank(this.getLocalizzazioneColore())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" ").concat(this.getLocalizzazioneColore());
	}
	if (StringUtils.isNotBlank(this.getLocalizzazioneCAP())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" - ").concat(this.getLocalizzazioneCAP());
	}
	if (StringUtils.isNotBlank(this.getLocalizzazioneLocfraz())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" - ").concat(this.getLocalizzazioneLocfraz());
	}
	if (StringUtils.isNotBlank(this.getLocalizzazioneCodViario())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" (").concat(this.getLocalizzazioneCodViario().concat(")"));
	}
	return descrizioneLocalizzazione;
    }

    public static SorteggioDettaglioDTO fromIstanza(Istanze istanza, Boolean sorteggiata, Boolean flagInterventoObbligatorio) {

	if (istanza == null) {
	    throw new RuntimeException("Impossibile utilizzare SorteggioDettaglioDTO.fromIstanza senza passare l'istanza");
	}
	SorteggioDettaglioDTO retVal = new SorteggioDettaglioDTO();
	if (istanza.getTipiarchivioistanza() != null) {
	    retVal.setArchivio(istanza.getTipiarchivioistanza().getArchivio());
	}
	if (istanza.getId() != null) {
	    retVal.setCodiceIstanza(istanza.getId().getCodice());
	}
	if (istanza.getComune() != null) {
	    retVal.setComune(istanza.getComune().getComune());
	}
	retVal.setDataIstanza(istanza.getData());
	retVal.setDataProtocollo(istanza.getDataprotocollo());
	retVal.setDescrSoggetto(istanza.getDescrsoggetto());
	if (istanza.getTipisoggetto() != null) {
	    retVal.setFlagSpecificaDescrizione(istanza.getTipisoggetto().getFlgSpecificadescrizione());
	    retVal.setTipoSoggetto(istanza.getTipisoggetto().getTiposoggetto());
	}
	if (istanza.getAlberoproc() != null) {
	    retVal.setIntervento(istanza.getAlberoproc().getDescrizioneCompleta());
	}
	retVal.setLavori(istanza.getLavori());
	Istanzestradario primario = istanza.getTransientIstanzeStradarioPrimario();
	if (primario != null && primario.getStradario() != null) {
	    retVal.setLocalizzazioneCAP(primario.getCap());
	    retVal.setLocalizzazioneCivico(primario.getCivico());
	    retVal.setLocalizzazioneCodViario(primario.getStradario().getCodviario());
	    if (primario.getStradariocolore() != null) {
		retVal.setLocalizzazioneColore(primario.getStradariocolore().getColore());
	    }
	    retVal.setLocalizzazioneEsponente(primario.getEsponente());
	    retVal.setLocalizzazioneDescrizione(primario.getStradario().getDescrizione());
	    retVal.setLocalizzazioneLocfraz(primario.getStradario().getLocfraz());
	    retVal.setLocalizzazionePrefisso(primario.getStradario().getPrefisso());
	    retVal.setLocalizzazionePrimaria(primario.getPrimario());
	}
	retVal.setNumeroIstanza(istanza.getNumeroistanza());
	retVal.setNumeroProtocollo(istanza.getNumeroprotocollo());
	if (istanza.getResponsabile() != null) {
	    retVal.setOperatore(istanza.getResponsabile().getResponsabile());
	}
	if (istanza.getIstruttore() != null) {
	    retVal.setResponsabileIstruttoria(istanza.getIstruttore().getResponsabile());
	}
	if (istanza.getResponsabileProcedimento() != null) {
	    retVal.setResponsabileProcedimento(istanza.getResponsabileProcedimento().getResponsabile());
	}
	if (istanza.getRichiedente() != null) {
	    retVal.setRichiedenteNome(istanza.getRichiedente().getNome());
	    retVal.setRichiedenteNominativo(istanza.getRichiedente().getNominativo());
	}
	retVal.setSorteggiata(sorteggiata);
	retVal.setFlagInterventoObbligatorio(flagInterventoObbligatorio);
	if (istanza.getTitolarelegale() != null) {
	    retVal.setTitolareLegaleNominativo(istanza.getTitolarelegale().getNominativo());
	}
	return retVal;
    }
}
