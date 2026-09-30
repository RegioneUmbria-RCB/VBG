package it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;

@XmlRootElement(name = "amministrazione")
public class AmministrazioniBean {

    @XmlElement(name = "codice")
    private Integer codice;
    @XmlElement
    private String amministrazione;
    @XmlElement
    private String ufficio;
    @XmlElement
    private String referente;
    @XmlElement
    private String indirizzo;
    @XmlElement
    private String citta;
    @XmlElement
    private String cap;
    @XmlElement
    private String provincia;
    @XmlElement
    private String telefono1;
    @XmlElement
    private String telefono2;
    @XmlElement
    private String fax;
    @XmlElement
    private String email;
    @XmlElement
    private String pec;
    @XmlElement
    private String web;
    @XmlElement
    private String partitaiva;

    public static AmministrazioniBean fromAmministrazioni(Amministrazioni amm) {

	if (amm == null) {
	    return null;
	}
	AmministrazioniBean ret = new AmministrazioniBean();
	ret.setCodice(amm.getId().getCodice());
	ret.setAmministrazione(amm.getAmministrazione());
	ret.setCap(StringUtils.defaultString(amm.getCap()));
	ret.setCitta(StringUtils.defaultString(amm.getCitta()));
	ret.setEmail(StringUtils.defaultString(amm.getEmail()));
	ret.setFax(StringUtils.defaultString(amm.getFax()));
	ret.setIndirizzo(StringUtils.defaultString(amm.getIndirizzo()));
	ret.setPartitaiva(StringUtils.defaultString(amm.getPartitaiva()));
	ret.setPec(StringUtils.defaultString(amm.getPec()));
	ret.setProvincia(StringUtils.defaultString(amm.getProvincia()));
	ret.setReferente(StringUtils.defaultString(amm.getReferente()));
	ret.setTelefono1(StringUtils.defaultString(amm.getTelefono1()));
	ret.setTelefono2(StringUtils.defaultString(amm.getTelefono2()));
	ret.setUfficio(StringUtils.defaultString(amm.getUfficio()));
	ret.setWeb(StringUtils.defaultString(amm.getWeb()));
	return ret;
    }

    public AmministrazioniBean() {

    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public String getAmministrazione() {

	return amministrazione;
    }

    public void setAmministrazione(String amministrazione) {

	this.amministrazione = amministrazione;
    }

    public String getUfficio() {

	return ufficio;
    }

    public void setUfficio(String ufficio) {

	this.ufficio = ufficio;
    }

    public String getReferente() {

	return referente;
    }

    public void setReferente(String referente) {

	this.referente = referente;
    }

    public String getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }

    public String getCitta() {

	return citta;
    }

    public void setCitta(String citta) {

	this.citta = citta;
    }

    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    public String getProvincia() {

	return provincia;
    }

    public void setProvincia(String provincia) {

	this.provincia = provincia;
    }

    public String getTelefono1() {

	return telefono1;
    }

    public void setTelefono1(String telefono1) {

	this.telefono1 = telefono1;
    }

    public String getTelefono2() {

	return telefono2;
    }

    public void setTelefono2(String telefono2) {

	this.telefono2 = telefono2;
    }

    public String getFax() {

	return fax;
    }

    public void setFax(String fax) {

	this.fax = fax;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    public String getPec() {

	return pec;
    }

    public void setPec(String pec) {

	this.pec = pec;
    }

    public String getWeb() {

	return web;
    }

    public void setWeb(String web) {

	this.web = web;
    }

    public String getPartitaiva() {

	return partitaiva;
    }

    public void setPartitaiva(String partitaiva) {

	this.partitaiva = partitaiva;
    }
}
