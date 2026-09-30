package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class AllTassonomie {

    private Date dataAllineamento = null;
    private String descrizione = null;
    private Long idAllTassonomie = null;
    private Long numErrori = null;
    private Long nuoveTassonomieAssegnate = null;
    private String tipoAllineamento = null;
    private String userCf = null;
    private String versioneTassonomia = null;

    /**
     * Get dataAllineamento
     * 
     * @return dataAllineamento
     **/
    @XmlElement(name = "dataAllineamento")
    public Date getDataAllineamento() {

	return dataAllineamento;
    }

    public void setDataAllineamento(Date dataAllineamento) {

	this.dataAllineamento = dataAllineamento;
    }

    public AllTassonomie dataAllineamento(Date dataAllineamento) {

	this.dataAllineamento = dataAllineamento;
	return this;
    }

    /**
     * Get descrizione
     * 
     * @return descrizione
     **/
    @XmlElement(name = "descrizione")
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public AllTassonomie descrizione(String descrizione) {

	this.descrizione = descrizione;
	return this;
    }

    /**
     * Get idAllTassonomie
     * 
     * @return idAllTassonomie
     **/
    @XmlElement(name = "idAllTassonomie")
    public Long getIdAllTassonomie() {

	return idAllTassonomie;
    }

    public void setIdAllTassonomie(Long idAllTassonomie) {

	this.idAllTassonomie = idAllTassonomie;
    }

    public AllTassonomie idAllTassonomie(Long idAllTassonomie) {

	this.idAllTassonomie = idAllTassonomie;
	return this;
    }

    /**
     * Get numErrori
     * 
     * @return numErrori
     **/
    @XmlElement(name = "numErrori")
    public Long getNumErrori() {

	return numErrori;
    }

    public void setNumErrori(Long numErrori) {

	this.numErrori = numErrori;
    }

    public AllTassonomie numErrori(Long numErrori) {

	this.numErrori = numErrori;
	return this;
    }

    /**
     * Get nuoveTassonomieAssegnate
     * 
     * @return nuoveTassonomieAssegnate
     **/
    @XmlElement(name = "nuoveTassonomieAssegnate")
    public Long getNuoveTassonomieAssegnate() {

	return nuoveTassonomieAssegnate;
    }

    public void setNuoveTassonomieAssegnate(Long nuoveTassonomieAssegnate) {

	this.nuoveTassonomieAssegnate = nuoveTassonomieAssegnate;
    }

    public AllTassonomie nuoveTassonomieAssegnate(Long nuoveTassonomieAssegnate) {

	this.nuoveTassonomieAssegnate = nuoveTassonomieAssegnate;
	return this;
    }

    /**
     * Get tipoAllineamento
     * 
     * @return tipoAllineamento
     **/
    @XmlElement(name = "tipoAllineamento")
    public String getTipoAllineamento() {

	return tipoAllineamento;
    }

    public void setTipoAllineamento(String tipoAllineamento) {

	this.tipoAllineamento = tipoAllineamento;
    }

    public AllTassonomie tipoAllineamento(String tipoAllineamento) {

	this.tipoAllineamento = tipoAllineamento;
	return this;
    }

    /**
     * Get userCf
     * 
     * @return userCf
     **/
    @XmlElement(name = "userCf")
    public String getUserCf() {

	return userCf;
    }

    public void setUserCf(String userCf) {

	this.userCf = userCf;
    }

    public AllTassonomie userCf(String userCf) {

	this.userCf = userCf;
	return this;
    }

    /**
     * Get versioneTassonomia
     * 
     * @return versioneTassonomia
     **/
    @XmlElement(name = "versioneTassonomia")
    public String getVersioneTassonomia() {

	return versioneTassonomia;
    }

    public void setVersioneTassonomia(String versioneTassonomia) {

	this.versioneTassonomia = versioneTassonomia;
    }

    public AllTassonomie versioneTassonomia(String versioneTassonomia) {

	this.versioneTassonomia = versioneTassonomia;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class AllTassonomie {\n");
	sb.append("    dataAllineamento: ").append(toIndentedString(dataAllineamento)).append("\n");
	sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
	sb.append("    idAllTassonomie: ").append(toIndentedString(idAllTassonomie)).append("\n");
	sb.append("    numErrori: ").append(toIndentedString(numErrori)).append("\n");
	sb.append("    nuoveTassonomieAssegnate: ").append(toIndentedString(nuoveTassonomieAssegnate)).append("\n");
	sb.append("    tipoAllineamento: ").append(toIndentedString(tipoAllineamento)).append("\n");
	sb.append("    userCf: ").append(toIndentedString(userCf)).append("\n");
	sb.append("    versioneTassonomia: ").append(toIndentedString(versioneTassonomia)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private static String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
