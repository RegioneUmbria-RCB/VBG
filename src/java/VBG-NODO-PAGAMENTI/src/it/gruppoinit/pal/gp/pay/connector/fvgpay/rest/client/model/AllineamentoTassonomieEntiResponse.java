package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

public class AllineamentoTassonomieEntiResponse {

    private Long numErrori = null;
    private Long nuoveTassonomieAssegnate = null;
    private String versioneTassonomia = null;

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

    public AllineamentoTassonomieEntiResponse numErrori(Long numErrori) {

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

    public AllineamentoTassonomieEntiResponse nuoveTassonomieAssegnate(Long nuoveTassonomieAssegnate) {

	this.nuoveTassonomieAssegnate = nuoveTassonomieAssegnate;
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

    public AllineamentoTassonomieEntiResponse versioneTassonomia(String versioneTassonomia) {

	this.versioneTassonomia = versioneTassonomia;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class AllineamentoTassonomieEntiResponse {\n");
	sb.append("    numErrori: ").append(toIndentedString(numErrori)).append("\n");
	sb.append("    nuoveTassonomieAssegnate: ").append(toIndentedString(nuoveTassonomieAssegnate)).append("\n");
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
