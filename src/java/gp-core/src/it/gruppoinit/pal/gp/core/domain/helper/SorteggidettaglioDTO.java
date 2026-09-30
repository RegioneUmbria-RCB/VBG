package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

public class SorteggidettaglioDTO {

    // istanza.id.codice
    private Integer codiceistanza;
    //    istanza.numeroistanza
    private String numeroistanza;
    //    istanza.data
    private Date dataistanza;
    //    istanza.numeroprotocollo
    private String numeroprotocollo;
    //    istanza.dataprotocollo
    private Date dataprotocollo;
    //    istanza.alberoproc.vwAlberoproc.scDescrizione
    private String scDescrizione;
    //    istanza.tipiarchivioistanza.archivio	
    private String archivio;
    //    istanza.lavori
    private String lavori;
    //    istanza.responsabile.responsabile
    private String responsabile;
    //    istanza.responsabileProcedimento.responsabile
    private String responsabileprocedimento;
    //    istanza.istruttore.responsabile
    private String responsabileIstruttore;
    //    sorteggiata
    private Boolean sorteggiata;
    private String richiedenteNome;
    private String richiedenteNominativo;
    private String titolarelegaleNominativo;
    //istanza.tiposoggetti
    private Boolean flgSpecificadescrizione;
    private String descrsoggetto;
    private String tiposoggetto;
    private String comune;
    // flag intervento obbligatorio
    private Boolean flagInterventoObbligatorio;
    private IstanzestradarioDTO istanzestradarioDTO;
    //    istanza.transientRichiedenteQualitaAzienda
    private String transientRichiedenteQualitaAzienda;
    //    istanza.transientLocalizzazionePrimario
    private String transientLocalizzazionePrimario;

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public Date getDataistanza() {

	return dataistanza;
    }

    public void setDataistanza(Date dataistanza) {

	this.dataistanza = dataistanza;
    }

    public String getNumeroprotocollo() {

	return numeroprotocollo;
    }

    public void setNumeroprotocollo(String numeroprotocollo) {

	this.numeroprotocollo = numeroprotocollo;
    }

    public Date getDataprotocollo() {

	return dataprotocollo;
    }

    public void setDataprotocollo(Date dataprotocollo) {

	this.dataprotocollo = dataprotocollo;
    }

    public String getScDescrizione() {

	return scDescrizione;
    }

    public void setScDescrizione(String scDescrizione) {

	this.scDescrizione = scDescrizione;
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

    public String getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(String responsabile) {

	this.responsabile = responsabile;
    }

    public String getResponsabileprocedimento() {

	return responsabileprocedimento;
    }

    public void setResponsabileprocedimento(String responsabileprocedimento) {

	this.responsabileprocedimento = responsabileprocedimento;
    }

    public String getResponsabileIstruttore() {

	return responsabileIstruttore;
    }

    public void setResponsabileIstruttore(String responsabileIstruttore) {

	this.responsabileIstruttore = responsabileIstruttore;
    }

    public Boolean getSorteggiata() {

	return sorteggiata;
    }

    public void setSorteggiata(Boolean sorteggiata) {

	this.sorteggiata = sorteggiata;
    }

    public Boolean getFlagInterventoObbligatorio() {

	return flagInterventoObbligatorio;
    }

    public void setFlagInterventoObbligatorio(Boolean flagInterventoObbligatorio) {

	this.flagInterventoObbligatorio = flagInterventoObbligatorio;
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

    public String getTitolarelegaleNominativo() {

	return titolarelegaleNominativo;
    }

    public void setTitolarelegaleNominativo(String titolarelegaleNominativo) {

	this.titolarelegaleNominativo = titolarelegaleNominativo;
    }

    public Boolean getFlgSpecificadescrizione() {

	return flgSpecificadescrizione;
    }

    public void setFlgSpecificadescrizione(Boolean flgSpecificadescrizione) {

	this.flgSpecificadescrizione = flgSpecificadescrizione;
    }

    public String getDescrsoggetto() {

	return descrsoggetto;
    }

    public void setDescrsoggetto(String descrsoggetto) {

	this.descrsoggetto = descrsoggetto;
    }

    public String getTiposoggetto() {

	return tiposoggetto;
    }

    public void setTiposoggetto(String tiposoggetto) {

	this.tiposoggetto = tiposoggetto;
    }

    public IstanzestradarioDTO getIstanzestradarioDTO() {

	return istanzestradarioDTO;
    }

    public void setIstanzestradarioDTO(IstanzestradarioDTO istanzestradarioDTO) {

	this.istanzestradarioDTO = istanzestradarioDTO;
    }

    public String getTransientRichiedenteQualitaAzienda() {

	String descrizioneRichiedente = "";
	descrizioneRichiedente = StringUtils.defaultIfEmpty(richiedenteNominativo, "");
	if (StringUtils.isNotBlank(richiedenteNome)) {
	    descrizioneRichiedente = descrizioneRichiedente.concat(" ").concat(richiedenteNome);
	}
	if (BooleanUtils.isTrue(flgSpecificadescrizione)) {
	    if (StringUtils.isNotBlank(descrsoggetto)) {
		descrizioneRichiedente = descrizioneRichiedente.concat(" ").concat(descrsoggetto);
	    }
	} else {
	    descrizioneRichiedente = descrizioneRichiedente.concat(" ").concat(StringUtils.defaultIfEmpty(tiposoggetto, ""));
	}
	if (StringUtils.isNotBlank(titolarelegaleNominativo)) {
	    descrizioneRichiedente = descrizioneRichiedente.concat(" ").concat(titolarelegaleNominativo);
	}
	transientRichiedenteQualitaAzienda = descrizioneRichiedente;
	return transientRichiedenteQualitaAzienda;
    }

    public void setTransientRichiedenteQualitaAzienda(String transientRichiedenteQualitaAzienda) {

	this.transientRichiedenteQualitaAzienda = transientRichiedenteQualitaAzienda;
    }

    public String getTransientLocalizzazionePrimario() {

	String descrizioneLocalizzazione = "";
	descrizioneLocalizzazione = StringUtils.defaultIfEmpty(istanzestradarioDTO.getPrefisso(), "");
	descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" ")
		.concat(StringUtils.defaultIfEmpty(istanzestradarioDTO.getDescrizione(), ""));
	if (StringUtils.isNotBlank(istanzestradarioDTO.getCivico())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" ").concat(istanzestradarioDTO.getCivico());
	}
	if (StringUtils.isNotBlank(getIstanzestradarioDTO().getEsponente())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" ").concat(istanzestradarioDTO.getEsponente());
	}
	if (StringUtils.isNotBlank(istanzestradarioDTO.getColore())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" ").concat(istanzestradarioDTO.getColore());
	}
	if (StringUtils.isNotBlank(getIstanzestradarioDTO().getCap())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" - ").concat(istanzestradarioDTO.getCap());
	}
	if (StringUtils.isNotBlank(getIstanzestradarioDTO().getLocfraz())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" - ").concat(istanzestradarioDTO.getLocfraz());
	}
	if (StringUtils.isNotBlank(getIstanzestradarioDTO().getCodviario())) {
	    descrizioneLocalizzazione = descrizioneLocalizzazione.concat(" (").concat(istanzestradarioDTO.getCodviario().concat(")"));
	}
	transientLocalizzazionePrimario = descrizioneLocalizzazione;
	return transientLocalizzazionePrimario;
    }

    public void setTransientLocalizzazionePrimario(String transientLocalizzazionePrimario) {

	this.transientLocalizzazionePrimario = transientLocalizzazionePrimario;
    }
}
