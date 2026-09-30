package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * Descrive lo stato delle posizioni debitorie dopo l'esecuzione di una richiesta di revoca di uno dei pagamenti
 * associati: - viene notificato al servizio di pagamento dal Gateway Il Codice Fiscale beneficiario è utilizzabile da
 * servizi parametrizzati sull'Ente che usano lo stesso end-point, un servizio client deve elaborare solo gli esiti
 * degli enti di sua pertinenza
 **/
public class NotificaEsitiRevocaType {

    private String identificativoBeneficiario = null;
    private List<NotificaEsitiPagamentoTypeElenco> elenco = new ArrayList<NotificaEsitiPagamentoTypeElenco>();

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

    public NotificaEsitiRevocaType identificativoBeneficiario(String identificativoBeneficiario) {

	this.identificativoBeneficiario = identificativoBeneficiario;
	return this;
    }

    /**
     * Get elenco
     * 
     * @return elenco
     **/
    @XmlElement(name = "elenco")
    public List<NotificaEsitiPagamentoTypeElenco> getElenco() {

	return elenco;
    }

    public void setElenco(List<NotificaEsitiPagamentoTypeElenco> elenco) {

	this.elenco = elenco;
    }

    public NotificaEsitiRevocaType elenco(List<NotificaEsitiPagamentoTypeElenco> elenco) {

	this.elenco = elenco;
	return this;
    }

    public NotificaEsitiRevocaType addElencoItem(NotificaEsitiPagamentoTypeElenco elencoItem) {

	this.elenco.add(elencoItem);
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NotificaEsitiRevocaType {\n");
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
