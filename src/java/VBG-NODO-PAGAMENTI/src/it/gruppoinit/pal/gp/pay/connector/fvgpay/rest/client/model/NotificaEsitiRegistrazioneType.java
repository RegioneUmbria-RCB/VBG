package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * Indica se le posizioni debitorie sono state registrate correttamente: - viene notificato al servizio di pagamento dal
 * Gateway - se una registrazione è stata effettuata correttamente viene restituito lo stato della posizione debitoria -
 * se la registrazione è fallita viene descritto il problema rilevato Il Codice Fiscale beneficiario è utilizzabile da
 * servizi parametrizzati sull'Ente che usano lo stesso end-point, un servizio client deve elaborare solo gli esiti
 * degli enti di sua pertinenza
 **/
public class NotificaEsitiRegistrazioneType {

    private String identificativoBeneficiario = null;
    private List<NotificaEsitiRegistrazioneTypeElenco> elenco = new ArrayList<NotificaEsitiRegistrazioneTypeElenco>();

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

    public NotificaEsitiRegistrazioneType identificativoBeneficiario(String identificativoBeneficiario) {

	this.identificativoBeneficiario = identificativoBeneficiario;
	return this;
    }

    /**
     * Get elenco
     * 
     * @return elenco
     **/
    @XmlElement(name = "elenco")
    public List<NotificaEsitiRegistrazioneTypeElenco> getElenco() {

	return elenco;
    }

    public void setElenco(List<NotificaEsitiRegistrazioneTypeElenco> elenco) {

	this.elenco = elenco;
    }

    public NotificaEsitiRegistrazioneType elenco(List<NotificaEsitiRegistrazioneTypeElenco> elenco) {

	this.elenco = elenco;
	return this;
    }

    public NotificaEsitiRegistrazioneType addElencoItem(NotificaEsitiRegistrazioneTypeElenco elencoItem) {

	this.elenco.add(elencoItem);
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NotificaEsitiRegistrazioneType {\n");
	sb.append("    identificativoBeneficiario: ").append(toIndentedString(identificativoBeneficiario)).append("\n");
	sb.append("    elenco: ").append(toIndentedString(elenco)).append("\n");
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
