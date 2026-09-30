package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoRecuperaPagamento", 
	propOrder = { "codiceRisposta", 
	"codiceErrore", 
	"messaggioErrore", 
	"urlDocumentazione" })
public class EsitoNegativo {

    @XmlElement(name = "codice_risposta")
    private String codiceRisposta;
    @XmlElement(name = "codice_errore")
    private String codiceErrore;
    @XmlElement(name = "messaggio_errore")
    private String messaggioErrore;
    @XmlElement(name = "url_documentazione")
    private String urlDocumentazione;

    public String getCodiceRisposta() {

	return codiceRisposta;
    }

    public void setCodiceRisposta(String codiceRisposta) {

	this.codiceRisposta = codiceRisposta;
    }

    public String getCodiceErrore() {

	return codiceErrore;
    }

    public void setCodiceErrore(String codiceErrore) {

	this.codiceErrore = codiceErrore;
    }

    public String getMessaggioErrore() {

	return messaggioErrore;
    }

    public void setMessaggioErrore(String messaggioErrore) {

	this.messaggioErrore = messaggioErrore;
    }

    public String getUrlDocumentazione() {

	return urlDocumentazione;
    }

    public void setUrlDocumentazione(String urlDocumentazione) {

	this.urlDocumentazione = urlDocumentazione;
    }
}
