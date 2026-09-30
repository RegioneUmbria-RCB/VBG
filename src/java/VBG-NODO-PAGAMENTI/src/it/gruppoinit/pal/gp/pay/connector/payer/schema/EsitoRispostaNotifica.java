package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoRispostaNotifica", propOrder = { "codiceRisposta", "descrizione" })
public class EsitoRispostaNotifica {

    @XmlElement(name = "codice_risposta")
    private String codiceRisposta;
    @XmlElement(name = "descrizione")
    private String descrizione;

    public String getCodiceRisposta() {

	return codiceRisposta;
    }

    public void setCodiceRisposta(String codiceRisposta) {

	this.codiceRisposta = codiceRisposta;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
}
