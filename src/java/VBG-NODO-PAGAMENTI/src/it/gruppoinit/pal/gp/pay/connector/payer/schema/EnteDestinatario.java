package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "codiceEntePortaleEsterno", "descrEntePortaleEsterno", "valore", "causale", "importoContabileIngresso",
	"importoContabileUscita", "codiceTassonomia" })
@XmlAccessorType(XmlAccessType.FIELD)
public class EnteDestinatario {

    @XmlElement(required = true, name = "CodiceEntePortaleEsterno")
    private String codiceEntePortaleEsterno;
    @XmlElement(required = true, name = "DescrEntePortaleEsterno")
    private String descrEntePortaleEsterno;
    @XmlElement(required = true, name = "Valore")
    private Integer valore;
    @XmlElement(name = "Causale")
    private String causale;
    @XmlElement(name = "ImportoContabileIngresso")
    private Integer importoContabileIngresso;
    @XmlElement(name = "ImportoContabileUscita")
    private Integer importoContabileUscita;
    @XmlElement(name = "CodiceTassonomia")
    private String codiceTassonomia;

    public String getCodiceEntePortaleEsterno() {

	return codiceEntePortaleEsterno;
    }

    public void setCodiceEntePortaleEsterno(String codiceEntePortaleEsterno) {

	this.codiceEntePortaleEsterno = codiceEntePortaleEsterno;
    }

    public String getDescrEntePortaleEsterno() {

	return descrEntePortaleEsterno;
    }

    public void setDescrEntePortaleEsterno(String descrEntePortaleEsterno) {

	this.descrEntePortaleEsterno = descrEntePortaleEsterno;
    }

    public Integer getValore() {

	return valore;
    }

    public void setValore(Integer valore) {

	this.valore = valore;
    }

    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    public Integer getImportoContabileIngresso() {

	return importoContabileIngresso;
    }

    public void setImportoContabileIngresso(Integer importoContabileIngresso) {

	this.importoContabileIngresso = importoContabileIngresso;
    }

    public Integer getImportoContabileUscita() {

	return importoContabileUscita;
    }

    public void setImportoContabileUscita(Integer importoContabileUscita) {

	this.importoContabileUscita = importoContabileUscita;
    }

    public String getCodiceTassonomia() {

	return codiceTassonomia;
    }

    public void setCodiceTassonomia(String codiceTassonomia) {

	this.codiceTassonomia = codiceTassonomia;
    }
}
