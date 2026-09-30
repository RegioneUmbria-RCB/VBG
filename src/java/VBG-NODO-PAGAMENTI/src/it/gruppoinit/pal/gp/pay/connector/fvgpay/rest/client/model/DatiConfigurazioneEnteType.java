package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import javax.xml.bind.annotation.XmlElement;

/**
 * Contiene i dati della configurazione dell’ente necessari per i servizi anagrafici
 **/
public class DatiConfigurazioneEnteType {

    /**
     * codice della tassonomia
     **/
    private String codiceTassonomico = null;
    /**
     * denominazione dell'Ente Creditore
     **/
    private String denominazioneBeneficiario = null;
    /**
     * descrizio del servizio di pagamento
     **/
    private String descrizioServizio = null;
    /**
     * identificativo univoco del servizio di pagamento
     **/
    private String idServizio = null;
    /**
     * Identificativo univoco del servizio di pagamento
     **/
    private String identificativoBeneficiario = null;
    /**
     * password del servizio
     **/
    private String passwordServizio = null;
    /**
     * denominazione del servizio di pagamento
     **/
    private String tipoServizio = null;
    /**
     * user del servizio
     **/
    private String userServizio = null;

    /**
     * codice della tassonomia
     * 
     * @return codiceTassonomico
     **/
    @XmlElement(name = "codice_tassonomico")
    public String getCodiceTassonomico() {

	return codiceTassonomico;
    }

    public void setCodiceTassonomico(String codiceTassonomico) {

	this.codiceTassonomico = codiceTassonomico;
    }

    public DatiConfigurazioneEnteType codiceTassonomico(String codiceTassonomico) {

	this.codiceTassonomico = codiceTassonomico;
	return this;
    }

    /**
     * denominazione dell&#39;Ente Creditore
     * 
     * @return denominazioneBeneficiario
     **/
    @XmlElement(name = "denominazione_beneficiario")
    public String getDenominazioneBeneficiario() {

	return denominazioneBeneficiario;
    }

    public void setDenominazioneBeneficiario(String denominazioneBeneficiario) {

	this.denominazioneBeneficiario = denominazioneBeneficiario;
    }

    public DatiConfigurazioneEnteType denominazioneBeneficiario(String denominazioneBeneficiario) {

	this.denominazioneBeneficiario = denominazioneBeneficiario;
	return this;
    }

    /**
     * descrizio del servizio di pagamento
     * 
     * @return descrizioServizio
     **/
    @XmlElement(name = "descrizio_servizio")
    public String getDescrizioServizio() {

	return descrizioServizio;
    }

    public void setDescrizioServizio(String descrizioServizio) {

	this.descrizioServizio = descrizioServizio;
    }

    public DatiConfigurazioneEnteType descrizioServizio(String descrizioServizio) {

	this.descrizioServizio = descrizioServizio;
	return this;
    }

    /**
     * identificativo univoco del servizio di pagamento
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

    public DatiConfigurazioneEnteType idServizio(String idServizio) {

	this.idServizio = idServizio;
	return this;
    }

    /**
     * Identificativo univoco del servizio di pagamento
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

    public DatiConfigurazioneEnteType identificativoBeneficiario(String identificativoBeneficiario) {

	this.identificativoBeneficiario = identificativoBeneficiario;
	return this;
    }

    /**
     * password del servizio
     * 
     * @return passwordServizio
     **/
    @XmlElement(name = "password_servizio")
    public String getPasswordServizio() {

	return passwordServizio;
    }

    public void setPasswordServizio(String passwordServizio) {

	this.passwordServizio = passwordServizio;
    }

    public DatiConfigurazioneEnteType passwordServizio(String passwordServizio) {

	this.passwordServizio = passwordServizio;
	return this;
    }

    /**
     * denominazione del servizio di pagamento
     * 
     * @return tipoServizio
     **/
    @XmlElement(name = "tipo_servizio")
    public String getTipoServizio() {

	return tipoServizio;
    }

    public void setTipoServizio(String tipoServizio) {

	this.tipoServizio = tipoServizio;
    }

    public DatiConfigurazioneEnteType tipoServizio(String tipoServizio) {

	this.tipoServizio = tipoServizio;
	return this;
    }

    /**
     * user del servizio
     * 
     * @return userServizio
     **/
    @XmlElement(name = "user_servizio")
    public String getUserServizio() {

	return userServizio;
    }

    public void setUserServizio(String userServizio) {

	this.userServizio = userServizio;
    }

    public DatiConfigurazioneEnteType userServizio(String userServizio) {

	this.userServizio = userServizio;
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class DatiConfigurazioneEnteType {\n");
	sb.append("    codiceTassonomico: ").append(toIndentedString(codiceTassonomico)).append("\n");
	sb.append("    denominazioneBeneficiario: ").append(toIndentedString(denominazioneBeneficiario)).append("\n");
	sb.append("    descrizioServizio: ").append(toIndentedString(descrizioServizio)).append("\n");
	sb.append("    idServizio: ").append(toIndentedString(idServizio)).append("\n");
	sb.append("    identificativoBeneficiario: ").append(toIndentedString(identificativoBeneficiario)).append("\n");
	sb.append("    passwordServizio: ").append(toIndentedString(passwordServizio)).append("\n");
	sb.append("    tipoServizio: ").append(toIndentedString(tipoServizio)).append("\n");
	sb.append("    userServizio: ").append(toIndentedString(userServizio)).append("\n");
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
