package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "CommitMsg")
@XmlType(name = "CommitMsg", propOrder = { "portaleId", "numeroOperazione", "idOrdine", "commit" })
@XmlAccessorType(XmlAccessType.FIELD)
public class CommitMsg {

    @XmlElement(name = "PortaleID", required = true)
    private String portaleId;
    @XmlElement(required = true, name = "NumeroOperazione")
    private String numeroOperazione;
    @XmlElement(name = "IDOrdine", required = true)
    private String idOrdine;
    @XmlElement(required = true, name = "Commit")
    private Commit commit;

    public String getPortaleId() {

	return portaleId;
    }

    public void setPortaleId(String portaleId) {

	this.portaleId = portaleId;
    }

    public String getNumeroOperazione() {

	return numeroOperazione;
    }

    public void setNumeroOperazione(String numeroOperazione) {

	this.numeroOperazione = numeroOperazione;
    }

    public String getIdOrdine() {

	return idOrdine;
    }

    public void setIdOrdine(String idOrdine) {

	this.idOrdine = idOrdine;
    }

    public Commit getCommit() {

	return commit;
    }

    public void setCommit(Commit commit) {

	this.commit = commit;
    }
}
