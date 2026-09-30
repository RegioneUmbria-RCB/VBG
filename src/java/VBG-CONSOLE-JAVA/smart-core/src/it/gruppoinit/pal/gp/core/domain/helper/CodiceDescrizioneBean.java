package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CodiceDescrizioneBean implements Comparable<CodiceDescrizioneBean> {

    private String codice;
    private String descrizione;

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Override
    public int compareTo(CodiceDescrizioneBean other) {

	return Comparators.DESCRIZIONE.compare(this, other);
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static class Comparators {

	public static Comparator<CodiceDescrizioneBean> DESCRIZIONE = new Comparator<CodiceDescrizioneBean>() {

	    @Override
	    public int compare(CodiceDescrizioneBean o1, CodiceDescrizioneBean o2) {

		return StringUtils.defaultString(o1.descrizione).toLowerCase().compareTo(StringUtils.defaultString(o2.descrizione).toLowerCase());
	    }
	};
	//	public static Comparator<CodiceDescrizioneBean> CODICE = new Comparator<CodiceDescrizioneBean>() {
	//
	//	    @Override
	//	    public int compare(CodiceDescrizioneBean o1, CodiceDescrizioneBean o2) {
	//
	//		return o1.codice.compareTo(o2.codice);
	//	    }
	//	};
    }
}
