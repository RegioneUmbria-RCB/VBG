package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

public class AnagraficaCreditoreDto {

    private String cfEnte = null;
    private String creditore = null;
    private String denominazioneEnte = null;
    private String pivaEnte = null;

    /**
     * Get cfEnte
     * 
     * @return cfEnte
     **/
    @XmlElement(name = "cfEnte")
    public String getCfEnte() {

	return cfEnte;
    }

    public void setCfEnte(String cfEnte) {

	this.cfEnte = cfEnte;
    }

    public AnagraficaCreditoreDto cfEnte(String cfEnte) {

	this.cfEnte = cfEnte;
	return this;
    }

    /**
     * Get creditore
     * 
     * @return creditore
     **/
    @XmlElement(name = "creditore")
    public String getCreditore() {

	return creditore;
    }

    public void setCreditore(String creditore) {

	this.creditore = creditore;
    }

    public AnagraficaCreditoreDto creditore(String creditore) {

	this.creditore = creditore;
	return this;
    }

    /**
     * Get denominazioneEnte
     * 
     * @return denominazioneEnte
     **/
    @XmlElement(name = "denominazioneEnte")
    public String getDenominazioneEnte() {

	return denominazioneEnte;
    }

    public void setDenominazioneEnte(String denominazioneEnte) {

	this.denominazioneEnte = denominazioneEnte;
    }

    public AnagraficaCreditoreDto denominazioneEnte(String denominazioneEnte) {

	this.denominazioneEnte = denominazioneEnte;
	return this;
    }

    /**
     * Get pivaEnte
     * 
     * @return pivaEnte
     **/
    @XmlElement(name = "pivaEnte")
    public String getPivaEnte() {

	return pivaEnte;
    }

    public void setPivaEnte(String pivaEnte) {

	this.pivaEnte = pivaEnte;
    }

    public AnagraficaCreditoreDto pivaEnte(String pivaEnte) {

	this.pivaEnte = pivaEnte;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class AnagraficaCreditoreDto {\n");
	sb.append("    cfEnte: ").append(toIndentedString(cfEnte)).append("\n");
	sb.append("    creditore: ").append(toIndentedString(creditore)).append("\n");
	sb.append("    denominazioneEnte: ").append(toIndentedString(denominazioneEnte)).append("\n");
	sb.append("    pivaEnte: ").append(toIndentedString(pivaEnte)).append("\n");
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
