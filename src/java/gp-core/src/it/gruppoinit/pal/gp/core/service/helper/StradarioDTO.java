package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradariozone;

import java.util.Date;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

public class StradarioDTO {

    private PkId id;
    private Stradariozone stradariozone;
    private String prefisso;
    private String descrizione;
    private String cap;
    private String locfraz;
    private String csDate;
    private String codviario;
    private Comuni comune;
    private Comuni comuneLocalizzazione;
    private Date datavalidita;
    private String prefissoAndDescrizione;

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public Stradariozone getStradariozone() {

	return stradariozone;
    }

    public void setStradariozone(Stradariozone stradariozone) {

	this.stradariozone = stradariozone;
    }

    public String getPrefisso() {

	return prefisso;
    }

    public void setPrefisso(String prefisso) {

	this.prefisso = prefisso;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    public String getLocfraz() {

	return locfraz;
    }

    public void setLocfraz(String locfraz) {

	this.locfraz = locfraz;
    }

    public String getCsDate() {

	return csDate;
    }

    public void setCsDate(String csDate) {

	this.csDate = csDate;
    }

    public String getCodviario() {

	return codviario;
    }

    public void setCodviario(String codviario) {

	this.codviario = codviario;
    }

    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    public Comuni getComuneLocalizzazione() {

	return comuneLocalizzazione;
    }

    public void setComuneLocalizzazione(Comuni comuneLocalizzazione) {

	this.comuneLocalizzazione = comuneLocalizzazione;
    }

    public Date getDatavalidita() {

	return datavalidita;
    }

    public void setDatavalidita(Date datavalidita) {

	this.datavalidita = datavalidita;
    }

    @Transient
    public String getPrefissoAndDescrizione() {

	this.prefissoAndDescrizione = "";
	if (StringUtils.isNotBlank(this.prefisso)) {
	    prefissoAndDescrizione += this.prefisso.trim();
	}
	if (StringUtils.isNotBlank(this.descrizione)) {
	    if (StringUtils.isNotBlank(this.prefissoAndDescrizione)) {
		this.prefissoAndDescrizione += " ";
	    }
	    this.prefissoAndDescrizione += this.descrizione.trim();
	}
	return prefissoAndDescrizione;
    }

    public void setPrefissoAndDescrizione(String prefissoAndDescrizione) {

	this.prefissoAndDescrizione = prefissoAndDescrizione;
    }
}
