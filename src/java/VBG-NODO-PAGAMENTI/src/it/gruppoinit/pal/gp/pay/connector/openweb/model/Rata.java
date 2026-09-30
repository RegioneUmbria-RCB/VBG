package it.gruppoinit.pal.gp.pay.connector.openweb.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tipoRata", //
	"idUnivocoVersamento", //
	"scadenza", //
	"dovuti" //
})
public class Rata {

    @XmlElement(name = "tipo_rata")
    private String tipoRata;// ENUM
    @XmlElement(name = "id_univoco_versamento", nillable = true)
    private String idUnivocoVersamento;
    @XmlElement(name = "scadenza")
    private String scadenza;
    @XmlElement(name = "dovuti")
    private List<Dovuto> dovuti;

    public String getTipoRata() {

	return tipoRata;
    }

    public void setTipoRata(String tipoRata) {

	this.tipoRata = tipoRata;
    }

    public String getIdUnivocoVersamento() {

	return idUnivocoVersamento;
    }

    public void setIdUnivocoVersamento(String idUnivocoVersamento) {

	this.idUnivocoVersamento = idUnivocoVersamento;
    }

    public String getScadenza() {

	return scadenza;
    }

    public void setScadenza(String scadenza) {

	this.scadenza = scadenza;
    }

    public List<Dovuto> getDovuti() {

	if (this.dovuti == null) {
	    this.dovuti = new ArrayList<Dovuto>();
	}
	return dovuti;
    }

    public void setDovuti(List<Dovuto> dovuti) {

	this.dovuti = dovuti;
    }
}
