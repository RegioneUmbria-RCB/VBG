package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;

import org.apache.commons.lang.StringUtils;

public class IstanzeDTO {

    private PkId id;
    private String numeroistanza;
    private AnagrafeDTO richiedente;
    private AnagrafeDTO titolarelegale;
    private String tiposoggetto;
    private String software;
    private String transientDescrizioneRichiedenteQualitaAzienda;
    private Integer attivitaOrdine;

    public IstanzeDTO() {

	id = new PkId();
	richiedente = new AnagrafeDTO();
	titolarelegale = new AnagrafeDTO();
    }

    public IstanzeDTO(Integer codice, String numero) {

	id = new PkId(codice);
	numeroistanza = numero;
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public AnagrafeDTO getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(AnagrafeDTO richiedente) {

	this.richiedente = richiedente;
    }

    public AnagrafeDTO getTitolarelegale() {

	return titolarelegale;
    }

    public void setTitolarelegale(AnagrafeDTO titolarelegale) {

	this.titolarelegale = titolarelegale;
    }

    public String getTiposoggetto() {

	return tiposoggetto;
    }

    public void setTiposoggetto(String tiposoggetto) {

	this.tiposoggetto = tiposoggetto;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public Integer getAttivitaOrdine() {

	return attivitaOrdine;
    }

    public void setAttivitaOrdine(Integer attivitaOrdine) {

	this.attivitaOrdine = attivitaOrdine;
    }

    public String getTransientDescrizioneRichiedenteQualitaAzienda() {

	this.transientDescrizioneRichiedenteQualitaAzienda = getRichiedente().getNominativo();
	if (StringUtils.isNotBlank(getRichiedente().getNome())) {
	    this.transientDescrizioneRichiedenteQualitaAzienda += " " + getRichiedente().getNome();
	}
	if (StringUtils.isNotBlank(getTiposoggetto())) {
	    this.transientDescrizioneRichiedenteQualitaAzienda += " " + getTiposoggetto();
	}
	//	    if (StringUtils.isNotBlank(getDescrizionesoggetto())) {
	//		this.transientDescrizioneRichiedenteQualitaAzienda += " " + getDescrizionesoggetto();
	//	    } else {
	//		this.transientDescrizioneRichiedenteQualitaAzienda += " " + getTiposoggetto();
	//	    }
	//	}
	//	if (StringUtils.isNotBlank(getAziendanominativo())) {
	//	    this.transientDescrizioneRichiedenteQualitaAzienda += " " + getAziendanominativo();
	if (EntityUtils.getNestedProperty(getTitolarelegale(), "id.codice") != null) {
	    this.transientDescrizioneRichiedenteQualitaAzienda += " " + getTitolarelegale().getNominativo();
	    if (StringUtils.isNotBlank(getTitolarelegale().getNome())) {
		this.transientDescrizioneRichiedenteQualitaAzienda += " " + getTitolarelegale().getNome();
	    }
	}
	return transientDescrizioneRichiedenteQualitaAzienda;
    }
}
