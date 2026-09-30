package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Documento", propOrder = { "tipologiaEntrata", "numeroDocumento", "annoDocumento", "importiContabili" })
public class Documento {

    @XmlElement(name = "TipologiaEntrata")
    private String tipologiaEntrata;
    @XmlElement(name = "NumeroDocumento")
    private String numeroDocumento;
    @XmlElement(name = "AnnoDocumento")
    private String annoDocumento;
    @XmlElement(name = "ImportiContabili")
    private List<ImportoContabileWrapper> importiContabili;

    public String getTipologiaEntrata() {

	return tipologiaEntrata;
    }

    public void setTipologiaEntrata(String tipologiaEntrata) {

	this.tipologiaEntrata = tipologiaEntrata;
    }

    public String getNumeroDocumento() {

	return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {

	this.numeroDocumento = numeroDocumento;
    }

    public String getAnnoDocumento() {

	return annoDocumento;
    }

    public void setAnnoDocumento(String annoDocumento) {

	this.annoDocumento = annoDocumento;
    }

    public List<ImportoContabileWrapper> getImportiContabili() {

	return importiContabili;
    }

    public void setImportiContabili(List<ImportoContabileWrapper> importiContabili) {

	this.importiContabili = importiContabili;
    }
}
