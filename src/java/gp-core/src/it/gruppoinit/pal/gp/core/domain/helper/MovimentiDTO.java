package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class MovimentiDTO implements Serializable {

    private static final long serialVersionUID = 1699786830723608975L;
    private Integer codicemovimento;
    private String idcomune;
    private String softwarecodice;
    private String softwaredescrizione;
    private Integer codiceistanza;
    private String numeroistanza;
    private String comune;
    private String numeroprotocolloistanza;
    private Date dataprotocolloistanza;
    private String posizionearchivio;
    private String procedura;
    private Integer codicerichiedente;
    private String richiedentenominativo;
    private String richiedentenome;
    private String richiedentecodicefiscale;
    private String richiedentepartitaiva;
    private Integer richstoricoid;
    private String richstoriconominativo;
    private String richstoriconome;
    private String richstoricocodicefiscale;
    private String richstoricopartitaiva;
    private Integer codiceazienda;
    private String aziendanominativo;
    private String aziendanome;
    private String aziendacodicefiscale;
    private String aziendapartitaiva;
    private Integer aziendastoricoid;
    private String aziendastoriconominativo;
    private String aziendastoriconome;
    private String aziendastoricocodicefiscale;
    private String aziendastoricopartitaiva;
    private String codicestato;
    private String stato;
    private String intervento;
    private String tipomovimento;
    private String tipomovimentodescrizione;
    private Integer endoprocedimentocodice;
    private String endoprocedimentodescrizione;
    private Integer amministrazionicodice;
    private String amministrazionidescrizione;
    private Date datamovimento;
    private String pareremovimento;
    private Boolean esitomovimento;
    private String notemovimento;
    private Boolean pubblicamovimento;
    private String numeroprotocollomovimento;
    private Date dataprotocollomovimento;
    private String fkidprotocollomovimento;
    private Integer responsabilecodice;
    private String responsabiledescrizione;
    private Date datainserimentomovimento;
    private String movimentodescrizione;
    private Boolean pubblicapareremovimento;
    private Boolean creatodastcmovimento;
    private Integer inviatoconstcmovimento;
    private Boolean inviatoacamcommovimento;
    private Boolean flagdaleggeremovimento;
    private Date datascadenzamovimento;
    private Boolean flagdisabilitatomovimento;
    private Boolean flagcmovobbligmovimento;
    private Integer ordineinserimentomovimento;
    private String numprotmittentemovimento;
    private Date dataprotmittentemovimento;
    private String tiposoggetto;
    private String descrizionesoggetto;
    private Date dataistanza;
    private Date datafine;
    private String istruttore;

    public String getTiposoggetto() {

	return tiposoggetto;
    }

    public void setTiposoggetto(String tiposoggetto) {

	this.tiposoggetto = tiposoggetto;
    }

    public String getDescrizionesoggetto() {

	return descrizionesoggetto;
    }

    public void setDescrizionesoggetto(String descrizionesoggetto) {

	this.descrizionesoggetto = descrizionesoggetto;
    }

    private String transientDescrizioneRichiedenteQualitaAzienda;
    private String transientDescrizioneRichiedenteAziendaStorico;

    public Integer getCodicemovimento() {

	return codicemovimento;
    }

    public void setCodicemovimento(Integer codicemovimento) {

	this.codicemovimento = codicemovimento;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getSoftwarecodice() {

	return softwarecodice;
    }

    public void setSoftwarecodice(String softwarecodice) {

	this.softwarecodice = softwarecodice;
    }

    public String getSoftwaredescrizione() {

	return softwaredescrizione;
    }

    public void setSoftwaredescrizione(String softwaredescrizione) {

	this.softwaredescrizione = softwaredescrizione;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getNumeroprotocolloistanza() {

	return numeroprotocolloistanza;
    }

    public void setNumeroprotocolloistanza(String numeroprotocolloistanza) {

	this.numeroprotocolloistanza = numeroprotocolloistanza;
    }

    public Date getDataprotocolloistanza() {

	return dataprotocolloistanza;
    }

    public void setDataprotocolloistanza(Date dataprotocolloistanza) {

	this.dataprotocolloistanza = dataprotocolloistanza;
    }

    public String getPosizionearchivio() {

	return posizionearchivio;
    }

    public void setPosizionearchivio(String posizionearchivio) {

	this.posizionearchivio = posizionearchivio;
    }

    public String getProcedura() {

	return procedura;
    }

    public void setProcedura(String procedura) {

	this.procedura = procedura;
    }

    public Integer getCodicerichiedente() {

	return codicerichiedente;
    }

    public void setCodicerichiedente(Integer codicerichiedente) {

	this.codicerichiedente = codicerichiedente;
    }

    public String getRichiedentenominativo() {

	return richiedentenominativo;
    }

    public void setRichiedentenominativo(String richiedentenominativo) {

	this.richiedentenominativo = richiedentenominativo;
    }

    public String getRichiedentenome() {

	return richiedentenome;
    }

    public void setRichiedentenome(String richiedentenome) {

	this.richiedentenome = richiedentenome;
    }

    public String getRichiedentecodicefiscale() {

	return richiedentecodicefiscale;
    }

    public void setRichiedentecodicefiscale(String richiedentecodicefiscale) {

	this.richiedentecodicefiscale = richiedentecodicefiscale;
    }

    public String getRichiedentepartitaiva() {

	return richiedentepartitaiva;
    }

    public void setRichiedentepartitaiva(String richiedentepartitaiva) {

	this.richiedentepartitaiva = richiedentepartitaiva;
    }

    public Integer getRichstoricoid() {

	return richstoricoid;
    }

    public void setRichstoricoid(Integer richstoricoid) {

	this.richstoricoid = richstoricoid;
    }

    public String getRichstoriconominativo() {

	return richstoriconominativo;
    }

    public void setRichstoriconominativo(String richstoriconominativo) {

	this.richstoriconominativo = richstoriconominativo;
    }

    public String getRichstoriconome() {

	return richstoriconome;
    }

    public void setRichstoriconome(String richstoriconome) {

	this.richstoriconome = richstoriconome;
    }

    public String getRichstoricocodicefiscale() {

	return richstoricocodicefiscale;
    }

    public void setRichstoricocodicefiscale(String richstoricocodicefiscale) {

	this.richstoricocodicefiscale = richstoricocodicefiscale;
    }

    public String getRichstoricopartitaiva() {

	return richstoricopartitaiva;
    }

    public void setRichstoricopartitaiva(String richstoricopartitaiva) {

	this.richstoricopartitaiva = richstoricopartitaiva;
    }

    public String getCodicestato() {

	return codicestato;
    }

    public void setCodicestato(String codicestato) {

	this.codicestato = codicestato;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getIntervento() {

	return intervento;
    }

    public void setIntervento(String intervento) {

	this.intervento = intervento;
    }

    public String getTipomovimento() {

	return tipomovimento;
    }

    public void setTipomovimento(String tipomovimento) {

	this.tipomovimento = tipomovimento;
    }

    public String getTipomovimentodescrizione() {

	return tipomovimentodescrizione;
    }

    public void setTipomovimentodescrizione(String tipomovimentodescrizione) {

	this.tipomovimentodescrizione = tipomovimentodescrizione;
    }

    public Integer getEndoprocedimentocodice() {

	return endoprocedimentocodice;
    }

    public void setEndoprocedimentocodice(Integer endoprocedimentocodice) {

	this.endoprocedimentocodice = endoprocedimentocodice;
    }

    public String getEndoprocedimentodescrizione() {

	return endoprocedimentodescrizione;
    }

    public void setEndoprocedimentodescrizione(String endoprocedimentodescrizione) {

	this.endoprocedimentodescrizione = endoprocedimentodescrizione;
    }

    public Integer getAmministrazionicodice() {

	return amministrazionicodice;
    }

    public void setAmministrazionicodice(Integer amministrazionicodice) {

	this.amministrazionicodice = amministrazionicodice;
    }

    public String getAmministrazionidescrizione() {

	return amministrazionidescrizione;
    }

    public void setAmministrazionidescrizione(String amministrazionidescrizione) {

	this.amministrazionidescrizione = amministrazionidescrizione;
    }

    public Integer getResponsabilecodice() {

	return responsabilecodice;
    }

    public void setResponsabilecodice(Integer responsabilecodice) {

	this.responsabilecodice = responsabilecodice;
    }

    public String getResponsabiledescrizione() {

	return responsabiledescrizione;
    }

    public void setResponsabiledescrizione(String responsabiledescrizione) {

	this.responsabiledescrizione = responsabiledescrizione;
    }

    public Date getDatamovimento() {

	return datamovimento;
    }

    public void setDatamovimento(Date datamovimento) {

	this.datamovimento = datamovimento;
    }

    public String getPareremovimento() {

	return pareremovimento;
    }

    public void setPareremovimento(String pareremovimento) {

	this.pareremovimento = pareremovimento;
    }

    public Boolean getEsitomovimento() {

	return esitomovimento;
    }

    public void setEsitomovimento(Boolean esitomovimento) {

	this.esitomovimento = esitomovimento;
    }

    public String getNotemovimento() {

	return notemovimento;
    }

    public void setNotemovimento(String notemovimento) {

	this.notemovimento = notemovimento;
    }

    public Boolean getPubblicamovimento() {

	return pubblicamovimento;
    }

    public void setPubblicamovimento(Boolean pubblicamovimento) {

	this.pubblicamovimento = pubblicamovimento;
    }

    public String getNumeroprotocollomovimento() {

	return numeroprotocollomovimento;
    }

    public void setNumeroprotocollomovimento(String numeroprotocollomovimento) {

	this.numeroprotocollomovimento = numeroprotocollomovimento;
    }

    public Date getDataprotocollomovimento() {

	return dataprotocollomovimento;
    }

    public void setDataprotocollomovimento(Date dataprotocollomovimento) {

	this.dataprotocollomovimento = dataprotocollomovimento;
    }

    public String getFkidprotocollomovimento() {

	return fkidprotocollomovimento;
    }

    public void setFkidprotocollomovimento(String fkidprotocollomovimento) {

	this.fkidprotocollomovimento = fkidprotocollomovimento;
    }

    public Date getDatainserimentomovimento() {

	return datainserimentomovimento;
    }

    public void setDatainserimentomovimento(Date datainserimentomovimento) {

	this.datainserimentomovimento = datainserimentomovimento;
    }

    public String getMovimentodescrizione() {

	return movimentodescrizione;
    }

    public void setMovimentodescrizione(String movimentodescrizione) {

	this.movimentodescrizione = movimentodescrizione;
    }

    public Boolean getPubblicapareremovimento() {

	return pubblicapareremovimento;
    }

    public void setPubblicapareremovimento(Boolean pubblicapareremovimento) {

	this.pubblicapareremovimento = pubblicapareremovimento;
    }

    public Boolean getCreatodastcmovimento() {

	return creatodastcmovimento;
    }

    public void setCreatodastcmovimento(Boolean creatodastcmovimento) {

	this.creatodastcmovimento = creatodastcmovimento;
    }

    public Integer getInviatoconstcmovimento() {

	return inviatoconstcmovimento;
    }

    public void setInviatoconstcmovimento(Integer inviatoconstcmovimento) {

	this.inviatoconstcmovimento = inviatoconstcmovimento;
    }

    public Boolean getInviatoacamcommovimento() {

	return inviatoacamcommovimento;
    }

    public void setInviatoacamcommovimento(Boolean inviatoacamcommovimento) {

	this.inviatoacamcommovimento = inviatoacamcommovimento;
    }

    public Boolean getFlagdaleggeremovimento() {

	return flagdaleggeremovimento;
    }

    public void setFlagdaleggeremovimento(Boolean flagdaleggeremovimento) {

	this.flagdaleggeremovimento = flagdaleggeremovimento;
    }

    public Date getDatascadenzamovimento() {

	return datascadenzamovimento;
    }

    public void setDatascadenzamovimento(Date datascadenzamovimento) {

	this.datascadenzamovimento = datascadenzamovimento;
    }

    public Boolean getFlagdisabilitatomovimento() {

	return flagdisabilitatomovimento;
    }

    public void setFlagdisabilitatomovimento(Boolean flagdisabilitatomovimento) {

	this.flagdisabilitatomovimento = flagdisabilitatomovimento;
    }

    public Boolean getFlagcmovobbligmovimento() {

	return flagcmovobbligmovimento;
    }

    public void setFlagcmovobbligmovimento(Boolean flagcmovobbligmovimento) {

	this.flagcmovobbligmovimento = flagcmovobbligmovimento;
    }

    public Integer getOrdineinserimentomovimento() {

	return ordineinserimentomovimento;
    }

    public void setOrdineinserimentomovimento(Integer ordineinserimentomovimento) {

	this.ordineinserimentomovimento = ordineinserimentomovimento;
    }

    public String getNumprotmittentemovimento() {

	return numprotmittentemovimento;
    }

    public void setNumprotmittentemovimento(String numprotmittentemovimento) {

	this.numprotmittentemovimento = numprotmittentemovimento;
    }

    public Date getDataprotmittentemovimento() {

	return dataprotmittentemovimento;
    }

    public void setDataprotmittentemovimento(Date dataprotmittentemovimento) {

	this.dataprotmittentemovimento = dataprotmittentemovimento;
    }

    public Integer getCodiceazienda() {

	return codiceazienda;
    }

    public void setCodiceazienda(Integer codiceazienda) {

	this.codiceazienda = codiceazienda;
    }

    public String getAziendanominativo() {

	return aziendanominativo;
    }

    public void setAziendanominativo(String aziendanominativo) {

	this.aziendanominativo = aziendanominativo;
    }

    public String getAziendanome() {

	return aziendanome;
    }

    public void setAziendanome(String aziendanome) {

	this.aziendanome = aziendanome;
    }

    public String getAziendacodicefiscale() {

	return aziendacodicefiscale;
    }

    public void setAziendacodicefiscale(String aziendacodicefiscale) {

	this.aziendacodicefiscale = aziendacodicefiscale;
    }

    public String getAziendapartitaiva() {

	return aziendapartitaiva;
    }

    public void setAziendapartitaiva(String aziendapartitaiva) {

	this.aziendapartitaiva = aziendapartitaiva;
    }

    public Integer getAziendastoricoid() {

	return aziendastoricoid;
    }

    public void setAziendastoricoid(Integer aziendastoricoid) {

	this.aziendastoricoid = aziendastoricoid;
    }

    public String getAziendastoriconominativo() {

	return aziendastoriconominativo;
    }

    public void setAziendastoriconominativo(String aziendastoriconominativo) {

	this.aziendastoriconominativo = aziendastoriconominativo;
    }

    public String getAziendastoriconome() {

	return aziendastoriconome;
    }

    public void setAziendastoriconome(String aziendastoriconome) {

	this.aziendastoriconome = aziendastoriconome;
    }

    public String getAziendastoricocodicefiscale() {

	return aziendastoricocodicefiscale;
    }

    public void setAziendastoricocodicefiscale(String aziendastoricocodicefiscale) {

	this.aziendastoricocodicefiscale = aziendastoricocodicefiscale;
    }

    public String getAziendastoricopartitaiva() {

	return aziendastoricopartitaiva;
    }

    public void setAziendastoricopartitaiva(String aziendastoricopartitaiva) {

	this.aziendastoricopartitaiva = aziendastoricopartitaiva;
    }

    public Date getDataistanza() {

	return dataistanza;
    }

    public void setDataistanza(Date dataistanza) {

	this.dataistanza = dataistanza;
    }

    public Date getDatafine() {

	return datafine;
    }

    public void setDatafine(Date datafine) {

	this.datafine = datafine;
    }

    public String getIstruttore() {

	return istruttore;
    }

    public void setIstruttore(String istruttore) {

	this.istruttore = istruttore;
    }

    public String getTransientDescrizioneRichiedenteQualitaAzienda() {

	this.transientDescrizioneRichiedenteQualitaAzienda = getRichiedentenominativo();
	if (StringUtils.isNotBlank(getRichiedentenome())) {
	    this.transientDescrizioneRichiedenteQualitaAzienda += " " + getRichiedentenome();
	}
	if (StringUtils.isNotBlank(getTiposoggetto())) {
	    if (StringUtils.isNotBlank(getDescrizionesoggetto())) {
		this.transientDescrizioneRichiedenteQualitaAzienda += " " + getDescrizionesoggetto();
	    } else {
		this.transientDescrizioneRichiedenteQualitaAzienda += " " + getTiposoggetto();
	    }
	}
	if (StringUtils.isNotBlank(getAziendanominativo())) {
	    this.transientDescrizioneRichiedenteQualitaAzienda += " " + getAziendanominativo();
	}
	return transientDescrizioneRichiedenteQualitaAzienda;
    }

    public String getTransientDescrizioneRichiedenteAziendaStorico() {

	this.transientDescrizioneRichiedenteAziendaStorico = getRichstoriconominativo();
	if (StringUtils.isNotBlank(getRichstoriconome())) {
	    this.transientDescrizioneRichiedenteAziendaStorico += " " + getRichstoriconome();
	}
	if (StringUtils.isNotBlank(getTiposoggetto())) {
	    if (StringUtils.isNotBlank(getDescrizionesoggetto())) {
		this.transientDescrizioneRichiedenteAziendaStorico += " " + getDescrizionesoggetto();
	    } else {
		this.transientDescrizioneRichiedenteAziendaStorico += " " + getTiposoggetto();
	    }
	}
	if (StringUtils.isNotBlank(getAziendastoriconominativo())) {
	    this.transientDescrizioneRichiedenteAziendaStorico += " " + getAziendastoriconominativo();
	}
	if (StringUtils.isNotBlank(getAziendastoriconome())) {
	    this.transientDescrizioneRichiedenteAziendaStorico += " " + getAziendastoriconome();
	}
	return transientDescrizioneRichiedenteAziendaStorico;
    }
}
