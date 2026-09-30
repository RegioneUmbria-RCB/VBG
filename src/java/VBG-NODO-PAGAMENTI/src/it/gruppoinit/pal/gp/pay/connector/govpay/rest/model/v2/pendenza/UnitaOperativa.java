package it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnitaOperativa", propOrder = { "idUnita", "ragioneSociale", "indirizzo", "civico", "cap", "localita", "provincia", "nazione",
	"email", "pec", "tel", "fax", "web", "area", })
public class UnitaOperativa {

    @XmlElement(name = "idUnita")
    private String idUnita = null;
    @XmlElement(name = "ragioneSociale")
    private String ragioneSociale = null;
    @XmlElement(name = "indirizzo")
    private String indirizzo = null;
    @XmlElement(name = "civico")
    private String civico = null;
    @XmlElement(name = "cap")
    private String cap = null;
    @XmlElement(name = "localita")
    private String localita = null;
    @XmlElement(name = "provincia")
    private String provincia = null;
    @XmlElement(name = "nazione")
    private String nazione = null;
    @XmlElement(name = "email")
    private String email = null;
    @XmlElement(name = "pec")
    private String pec = null;
    @XmlElement(name = "tel")
    private String tel = null;
    @XmlElement(name = "fax")
    private String fax = null;
    @XmlElement(name = "web")
    private String web = null;
    @XmlElement(name = "area")
    private String area = null;

    /**
     * Codice fiscale
     **/
    public UnitaOperativa idUnita(String idUnita) {

	this.idUnita = idUnita;
	return this;
    }

    public String getIdUnita() {

	return idUnita;
    }

    public void setIdUnita(String idUnita) {

	this.idUnita = idUnita;
    }

    /**
     * Ragione sociale
     **/
    public UnitaOperativa ragioneSociale(String ragioneSociale) {

	this.ragioneSociale = ragioneSociale;
	return this;
    }

    public String getRagioneSociale() {

	return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {

	this.ragioneSociale = ragioneSociale;
    }

    /**
     **/
    public UnitaOperativa indirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
	return this;
    }

    public String getIndirizzo() {

	return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }

    /**
     **/
    public UnitaOperativa civico(String civico) {

	this.civico = civico;
	return this;
    }

    public String getCivico() {

	return civico;
    }

    public void setCivico(String civico) {

	this.civico = civico;
    }

    /**
     **/
    public UnitaOperativa cap(String cap) {

	this.cap = cap;
	return this;
    }

    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    /**
     **/
    public UnitaOperativa localita(String localita) {

	this.localita = localita;
	return this;
    }

    public String getLocalita() {

	return localita;
    }

    public void setLocalita(String localita) {

	this.localita = localita;
    }

    /**
     **/
    public UnitaOperativa provincia(String provincia) {

	this.provincia = provincia;
	return this;
    }

    public String getProvincia() {

	return provincia;
    }

    public void setProvincia(String provincia) {

	this.provincia = provincia;
    }

    /**
     **/
    public UnitaOperativa nazione(String nazione) {

	this.nazione = nazione;
	return this;
    }

    public String getNazione() {

	return nazione;
    }

    public void setNazione(String nazione) {

	this.nazione = nazione;
    }

    /**
     * Posta elettronica ordinaria
     **/
    public UnitaOperativa email(String email) {

	this.email = email;
	return this;
    }

    public String getEmail() {

	return email;
    }

    public void setEmail(String email) {

	this.email = email;
    }

    /**
     * Posta elettronica certificata
     **/
    public UnitaOperativa pec(String pec) {

	this.pec = pec;
	return this;
    }

    public String getPec() {

	return pec;
    }

    public void setPec(String pec) {

	this.pec = pec;
    }

    /**
     * Numero di telefono dell'help desk di primo livello
     **/
    public UnitaOperativa tel(String tel) {

	this.tel = tel;
	return this;
    }

    public String getTel() {

	return tel;
    }

    public void setTel(String tel) {

	this.tel = tel;
    }

    /**
     * Numero di fax dell'help desk di primo livello
     **/
    public UnitaOperativa fax(String fax) {

	this.fax = fax;
	return this;
    }

    public String getFax() {

	return fax;
    }

    public void setFax(String fax) {

	this.fax = fax;
    }

    /**
     * Url del sito web
     **/
    public UnitaOperativa web(String web) {

	this.web = web;
	return this;
    }

    public String getWeb() {

	return web;
    }

    public void setWeb(String web) {

	this.web = web;
    }

    /**
     * Nome dell'area di competenza
     **/
    public UnitaOperativa area(String area) {

	this.area = area;
	return this;
    }

    public String getArea() {

	return area;
    }

    public void setArea(String area) {

	this.area = area;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	UnitaOperativa unitaOperativa = (UnitaOperativa) o;
	return Objects.equals(idUnita, unitaOperativa.idUnita) && Objects.equals(ragioneSociale, unitaOperativa.ragioneSociale)
		&& Objects.equals(indirizzo, unitaOperativa.indirizzo) && Objects.equals(civico, unitaOperativa.civico)
		&& Objects.equals(cap, unitaOperativa.cap) && Objects.equals(localita, unitaOperativa.localita)
		&& Objects.equals(provincia, unitaOperativa.provincia) && Objects.equals(nazione, unitaOperativa.nazione)
		&& Objects.equals(email, unitaOperativa.email) && Objects.equals(pec, unitaOperativa.pec) && Objects.equals(tel, unitaOperativa.tel)
		&& Objects.equals(fax, unitaOperativa.fax) && Objects.equals(web, unitaOperativa.web) && Objects.equals(area, unitaOperativa.area);
    }

    @Override
    public int hashCode() {

	return Objects.hash(idUnita, ragioneSociale, indirizzo, civico, cap, localita, provincia, nazione, email, pec, tel, fax, web, area);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class UnitaOperativa {\n");
	sb.append("    idUnita: ").append(toIndentedString(idUnita)).append("\n");
	sb.append("    ragioneSociale: ").append(toIndentedString(ragioneSociale)).append("\n");
	sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
	sb.append("    civico: ").append(toIndentedString(civico)).append("\n");
	sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
	sb.append("    localita: ").append(toIndentedString(localita)).append("\n");
	sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
	sb.append("    nazione: ").append(toIndentedString(nazione)).append("\n");
	sb.append("    email: ").append(toIndentedString(email)).append("\n");
	sb.append("    pec: ").append(toIndentedString(pec)).append("\n");
	sb.append("    tel: ").append(toIndentedString(tel)).append("\n");
	sb.append("    fax: ").append(toIndentedString(fax)).append("\n");
	sb.append("    web: ").append(toIndentedString(web)).append("\n");
	sb.append("    area: ").append(toIndentedString(area)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
