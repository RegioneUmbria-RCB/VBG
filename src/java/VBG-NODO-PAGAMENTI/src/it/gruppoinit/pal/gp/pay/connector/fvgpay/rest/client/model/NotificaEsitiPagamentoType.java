package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * Notifica gli effetti degli esiti dei pagamenti ricevuti sulle posizioni debitorie associate - viene notificato al
 * servizio di pagamento dal Gateway Il Codice Fiscale beneficiario è utilizzabile da servizi parametrizzati sull'Ente
 * che usano lo stesso end-point, un servizio client deve elaborare solo gli esiti degli enti di sua pertinenza
 **/
public class NotificaEsitiPagamentoType {

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

    public NotificaEsitiPagamentoType identificativoBeneficiario(String identificativoBeneficiario) {

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

    public NotificaEsitiPagamentoType elenco(List<NotificaEsitiPagamentoTypeElenco> elenco) {

	this.elenco = elenco;
	return this;
    }

    public NotificaEsitiPagamentoType addElencoItem(NotificaEsitiPagamentoTypeElenco elencoItem) {

	this.elenco.add(elencoItem);
	return this;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class NotificaEsitiPagamentoType {\n");
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
