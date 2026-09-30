package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.pay.connector.openweb.model.Dovuto;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "rata", //
	"idUnivocoVersamento", //
	"numeroAvviso", //	
	"dovuti", //
	"dataMatrix", //
	"qrCode" //
})
public class EsitiRata {

    @XmlElement(name = "rata")
    private String rata;
    @XmlElement(name = "id_univoco_versamento")
    private String idUnivocoVersamento;
    @XmlElement(name = "numero_avviso")
    private String numeroAvviso;
    @XmlElement(name = "dovuti")
    private List<Dovuto> dovuti;
    @XmlElement(name = "stringa_datamatrix")
    private String dataMatrix;
    @XmlElement(name = "stringa_qrcode")
    private String qrCode;

    public String getRata() {

	return rata;
    }

    public void setRata(String rata) {

	this.rata = rata;
    }

    public String getIdUnivocoVersamento() {

	return idUnivocoVersamento;
    }

    public void setIdUnivocoVersamento(String idUnivocoVersamento) {

	this.idUnivocoVersamento = idUnivocoVersamento;
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

    public String getNumeroAvviso() {

	return numeroAvviso;
    }

    public void setNumeroAvviso(String numeroAvviso) {

	this.numeroAvviso = numeroAvviso;
    }

    public String getDataMatrix() {

	return dataMatrix;
    }

    public void setDataMatrix(String dataMatrix) {

	this.dataMatrix = dataMatrix;
    }

    public String getQrCode() {

	return qrCode;
    }

    public void setQrCode(String qrCode) {

	this.qrCode = qrCode;
    }
}
