package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

public class AllTassonomieRowDetail {

    private String codiceTassomia = null;
    private String descrizioneEnte = null;
    private String descrizioneTassomia = null;
    private String erroreAllineamento = null;

    /**
     * Get codiceTassomia
     * 
     * @return codiceTassomia
     **/
    @XmlElement(name = "codiceTassomia")
    public String getCodiceTassomia() {

	return codiceTassomia;
    }

    public void setCodiceTassomia(String codiceTassomia) {

	this.codiceTassomia = codiceTassomia;
    }

    public AllTassonomieRowDetail codiceTassomia(String codiceTassomia) {

	this.codiceTassomia = codiceTassomia;
	return this;
    }

    /**
     * Get descrizioneEnte
     * 
     * @return descrizioneEnte
     **/
    @XmlElement(name = "descrizioneEnte")
    public String getDescrizioneEnte() {

	return descrizioneEnte;
    }

    public void setDescrizioneEnte(String descrizioneEnte) {

	this.descrizioneEnte = descrizioneEnte;
    }

    public AllTassonomieRowDetail descrizioneEnte(String descrizioneEnte) {

	this.descrizioneEnte = descrizioneEnte;
	return this;
    }

    /**
     * Get descrizioneTassomia
     * 
     * @return descrizioneTassomia
     **/
    @XmlElement(name = "descrizioneTassomia")
    public String getDescrizioneTassomia() {

	return descrizioneTassomia;
    }

    public void setDescrizioneTassomia(String descrizioneTassomia) {

	this.descrizioneTassomia = descrizioneTassomia;
    }

    public AllTassonomieRowDetail descrizioneTassomia(String descrizioneTassomia) {

	this.descrizioneTassomia = descrizioneTassomia;
	return this;
    }

    /**
     * Get erroreAllineamento
     * 
     * @return erroreAllineamento
     **/
    @XmlElement(name = "erroreAllineamento")
    public String getErroreAllineamento() {

	return erroreAllineamento;
    }

    public void setErroreAllineamento(String erroreAllineamento) {

	this.erroreAllineamento = erroreAllineamento;
    }

    public AllTassonomieRowDetail erroreAllineamento(String erroreAllineamento) {

	this.erroreAllineamento = erroreAllineamento;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class AllTassonomieRowDetail {\n");
	sb.append("    codiceTassomia: ").append(toIndentedString(codiceTassomia)).append("\n");
	sb.append("    descrizioneEnte: ").append(toIndentedString(descrizioneEnte)).append("\n");
	sb.append("    descrizioneTassomia: ").append(toIndentedString(descrizioneTassomia)).append("\n");
	sb.append("    erroreAllineamento: ").append(toIndentedString(erroreAllineamento)).append("\n");
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
