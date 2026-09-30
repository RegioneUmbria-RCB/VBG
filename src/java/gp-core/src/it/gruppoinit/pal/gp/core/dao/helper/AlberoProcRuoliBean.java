package it.gruppoinit.pal.gp.core.dao.helper;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AlberoProcRuoliBean {

    private Integer codice;
    private String percorso;
    private String descrizione;
    private Integer idruolo;

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public String getPercorso() {

	return percorso;
    }

    public void setPercorso(String percorso) {

	this.percorso = percorso;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Integer getIdruolo() {

	return idruolo;
    }

    public void setIdruolo(Integer idruolo) {

	this.idruolo = idruolo;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
