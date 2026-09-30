package it.gruppoinit.pal.gp.pay.connector.openweb.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tipoDovuto", //
	"idUnivocoDovuto", //
	"causale", //
	"importo", //
	"accertamento", //
	"annoCompetenza", //
	"hashdocumentoBollo" //
})
public class Dovuto {

    @XmlElement(name = "tipo_dovuto")
    private String tipoDovuto; // ENUM a  b
    @XmlElement(name = "id_univoco_dovuto")
    private String idUnivocoDovuto;
    @XmlElement(name = "causale")
    private String causale;
    @XmlElement(name = "importo")
    private double importo;
    @XmlElement(name = "accertamento")
    private String accertamento;
    @XmlElement(name = "anno_competenza")
    private Integer annoCompetenza;
    @XmlElement(name = "hashdocumento_bollo")
    private String hashdocumentoBollo;

    public String getTipoDovuto() {

	return tipoDovuto;
    }

    public void setTipoDovuto(String tipoDovuto) {

	this.tipoDovuto = tipoDovuto;
    }

    public String getIdUnivocoDovuto() {

	return idUnivocoDovuto;
    }

    public void setIdUnivocoDovuto(String idUnivocoDovuto) {

	this.idUnivocoDovuto = idUnivocoDovuto;
    }

    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    public double getImporto() {

	return importo;
    }

    public void setImporto(double importo) {

	this.importo = importo;
    }

    public String getAccertamento() {

	return accertamento;
    }

    public void setAccertamento(String accertamento) {

	this.accertamento = accertamento;
    }

    public Integer getAnnoCompetenza() {

	return annoCompetenza;
    }

    public void setAnnoCompetenza(Integer annoCompetenza) {

	this.annoCompetenza = annoCompetenza;
    }

    public String getHashdocumentoBollo() {

	return hashdocumentoBollo;
    }

    public void setHashdocumentoBollo(String hashdocumentoBollo) {

	this.hashdocumentoBollo = hashdocumentoBollo;
    }
}
