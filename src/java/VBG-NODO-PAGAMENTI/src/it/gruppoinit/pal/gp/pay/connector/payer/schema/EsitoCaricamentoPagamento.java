package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoCaricamentoPagamento", propOrder = { "codiceRisposta", "dataoraRichiesta", "url" })
public class EsitoCaricamentoPagamento {

    @XmlElement(name = "codice_risposta")
    private String codiceRisposta;
    @XmlElement(name = "dataora_richiesta")
    private String dataoraRichiesta;
    @XmlElement(name = "url")
    private String url;

    public String getCodiceRisposta() {

	return codiceRisposta;
    }

    public void setCodiceRisposta(String codiceRisposta) {

	this.codiceRisposta = codiceRisposta;
    }

    public String getDataoraRichiesta() {

	return dataoraRichiesta;
    }

    public void setDataoraRichiesta(String dataoraRichiesta) {

	this.dataoraRichiesta = dataoraRichiesta;
    }

    public String getUrl() {

	return url;
    }

    public void setUrl(String url) {

	this.url = url;
    }
}
