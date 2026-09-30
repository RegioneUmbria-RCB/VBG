package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoRecuproIuvDaRichiesta", propOrder = { "codiceRisposta", "dataoraRichiesta", "totIuv", "iuv", "dataoraRisposta", "paginazione" })
public class EsitoRecuproIuvDaRichiesta {

    @XmlElement(name = "codice_risposta")
    private String codiceRisposta;
    @XmlElement(name = "dataora_richiesta")
    private String dataoraRichiesta;
    @XmlElement(name = "tot_iuv")
    private Integer totIuv;
    @XmlElement(name = "iuv")
    private List<Iuv> iuv = null;
    @XmlElement(name = "dataora_risposta")
    private String dataoraRisposta;
    @XmlElement(name = "paginazione")
    private Paginazione paginazione;

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

    public Integer getTotIuv() {

	return totIuv;
    }

    public void setTotIuv(Integer totIuv) {

	this.totIuv = totIuv;
    }

    public List<Iuv> getIuv() {

	return iuv;
    }

    public void setIuv(List<Iuv> iuv) {

	this.iuv = iuv;
    }

    public String getDataoraRisposta() {

	return dataoraRisposta;
    }

    public void setDataoraRisposta(String dataoraRisposta) {

	this.dataoraRisposta = dataoraRisposta;
    }

    public Paginazione getPaginazione() {

	return paginazione;
    }

    public void setPaginazione(Paginazione paginazione) {

	this.paginazione = paginazione;
    }
}
