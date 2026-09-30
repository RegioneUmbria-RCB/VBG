package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiSingoloVersamento", propOrder = { "importo", "causale", "documento", "stato" })
public class DatiSingoloVersamento {

    @XmlElement(name = "Importo")
    private Integer importo;
    @XmlElement(name = "Causale")
    private String causale;
    @XmlElement(name = "Documento")
    private Documento documento;
    @XmlElement(name = "Stato")
    private Integer stato;

    public Integer getImporto() {

	return importo;
    }

    public void setImporto(Integer importo) {

	this.importo = importo;
    }

    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    public Documento getDocumento() {

	return documento;
    }

    public void setDocumento(Documento documento) {

	this.documento = documento;
    }

    public Integer getStato() {

	return stato;
    }

    public void setStato(Integer stato) {

	this.stato = stato;
    }
}
