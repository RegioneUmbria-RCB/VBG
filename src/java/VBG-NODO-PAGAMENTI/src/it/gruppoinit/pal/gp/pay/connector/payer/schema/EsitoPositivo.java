package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoPositivo", propOrder = { "codice_risposta", "dataora_richiesta" })
public class EsitoPositivo {

    @XmlElement(name = "codice_risposta")
    private String codiceRisposta;
    @XmlElement(name = "dataora_richiesta")
    private String dataoraRichiesta;

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
}
