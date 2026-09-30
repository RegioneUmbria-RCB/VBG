package it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Comuni;

public class AmmCollComuneBean implements Comparable<AmmCollComuneBean> {

    @XmlElement
    private String codicecomune;
    @XmlElement
    private String comune;
    @XmlElement
    private String codicecatastale;
    @XmlElement
    private String codiceistat;
    @XmlElement
    private String siglaprovincia;
    @XmlElement
    private String provincia;
    @XmlElement
    private String regione;

    public AmmCollComuneBean() {

    }

    public AmmCollComuneBean(Comuni comune) {

	this();
	this.codicecomune = comune.getCodicecomune();
	this.comune = comune.getComune();
	this.codicecatastale = comune.getCf();
	this.codiceistat = comune.getCodiceistat();
	this.siglaprovincia = comune.getSiglaprovincia();
	this.provincia = comune.getProvincia();
	this.regione = comune.getRegione();
    }

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getCodicecatastale() {

	return codicecatastale;
    }

    public void setCodicecatastale(String codicecatastale) {

	this.codicecatastale = codicecatastale;
    }

    public String getCodiceistat() {

	return codiceistat;
    }

    public void setCodiceistat(String codiceistat) {

	this.codiceistat = codiceistat;
    }

    public String getSiglaprovincia() {

	return siglaprovincia;
    }

    public void setSiglaprovincia(String siglaprovincia) {

	this.siglaprovincia = siglaprovincia;
    }

    public String getProvincia() {

	return provincia;
    }

    public void setProvincia(String provincia) {

	this.provincia = provincia;
    }

    public String getRegione() {

	return regione;
    }

    public void setRegione(String regione) {

	this.regione = regione;
    }

    @Override
    public int compareTo(AmmCollComuneBean o) {

	String questoComune = StringUtils.defaultString(this.comune, "00000000000000000000");
	String altroComune = "00000000000000000000";
	if (o != null && o.getCodicecomune() != null) {
	    altroComune = o.getCodicecomune();
	}
	return questoComune.compareTo(altroComune);
    }
}
