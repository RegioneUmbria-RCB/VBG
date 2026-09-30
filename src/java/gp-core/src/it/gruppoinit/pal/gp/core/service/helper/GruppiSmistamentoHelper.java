package it.gruppoinit.pal.gp.core.service.helper;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GruppiSmistamentoHelper {

    private String descrizione;
    private int numWarnings;
    private String tipmov;
    private Set<Integer> endoprocedimentis = new HashSet<Integer>();

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public int getNumWarnings() {

	return numWarnings;
    }

    public void setNumWarnings(int numWarnings) {

	this.numWarnings = numWarnings;
    }

    public String getTipmov() {

	return tipmov;
    }

    public void setTipmov(String tipmov) {

	this.tipmov = tipmov;
    }

    public Set<Integer> getEndoprocedimentis() {

	return endoprocedimentis;
    }

    public void setEndoprocedimentis(Set<Integer> endoprocedimentis) {

	this.endoprocedimentis = endoprocedimentis;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
