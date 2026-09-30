package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
 * identifica univocamente una posizione debitoria emessa da un Ente Creditore
 **/

public class IdentificativoPosizioneDebitoriaType {

    /**
     * Identificativo della posizione debitoria, univoco nell'ambito di un servizio di pagamento attivo presso un Ente
     * Creditore
     **/
    private String idDebito = null;
    /**
     * Identificativo univoco del servizio di pagamento intermediato
     **/
    private String idServizio = null;
    private String identificativoBeneficiario = null;

    /**
     * Identificativo della posizione debitoria, univoco nell&#39;ambito di un servizio di pagamento attivo presso un
     * Ente Creditore
     * 
     * @return idDebito
     **/
    @XmlElement(name = "id_debito")
    public String getIdDebito() {

	return idDebito;
    }

    public void setIdDebito(String idDebito) {

	this.idDebito = idDebito;
    }

    public IdentificativoPosizioneDebitoriaType idDebito(String idDebito) {

	this.idDebito = idDebito;
	return this;
    }

    /**
     * Identificativo univoco del servizio di pagamento intermediato
     * 
     * @return idServizio
     **/
    @XmlElement(name = "id_servizio")
    public String getIdServizio() {

	return idServizio;
    }

    public void setIdServizio(String idServizio) {

	this.idServizio = idServizio;
    }

    public IdentificativoPosizioneDebitoriaType idServizio(String idServizio) {

	this.idServizio = idServizio;
	return this;
    }

    /**
     * Get identificativoBeneficiario
     * 
     * @return identificativoBeneficiario
     **/
    @XmlElement(name = "identificativo_beneficiario")
    public String getIdentificativoBeneficiario() {

	return identificativoBeneficiario;
    }

    public void setIdentificativoBeneficiario(String identificativoBeneficiario) {

	this.identificativoBeneficiario = identificativoBeneficiario;
    }

    public IdentificativoPosizioneDebitoriaType identificativoBeneficiario(String identificativoBeneficiario) {

	this.identificativoBeneficiario = identificativoBeneficiario;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class IdentificativoPosizioneDebitoriaType {\n");
	sb.append("    idDebito: ").append(toIndentedString(idDebito)).append("\n");
	sb.append("    idServizio: ").append(toIndentedString(idServizio)).append("\n");
	sb.append("    identificativoBeneficiario: ").append(toIndentedString(identificativoBeneficiario)).append("\n");
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
