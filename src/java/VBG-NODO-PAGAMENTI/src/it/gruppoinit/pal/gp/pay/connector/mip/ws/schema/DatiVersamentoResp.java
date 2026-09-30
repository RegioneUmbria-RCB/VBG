package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiVersamentoResp", propOrder = { "iuv", "codiceAvviso", "qrCode", "descrizionePagamento", "importoTotale",
	"datiSingoloVersamento" })
public class DatiVersamentoResp {

    @XmlElement(name = "IUV")
    private String iuv;
    @XmlElement(name = "CodiceAvviso")
    private String codiceAvviso;
    @XmlElement(name = "QRCode")
    private String qrCode;
    @XmlElement(name = "DescrizionePagamento")
    private String descrizionePagamento;
    @XmlElement(name = "ImportoTotale")
    private Integer importoTotale;
    @XmlElement(name = "DatiSingoloVersamento")
    private DatiSingoloVersamento datiSingoloVersamento;

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
    }

    public String getQrCode() {

	return qrCode;
    }

    public void setQrCode(String qrCode) {

	this.qrCode = qrCode;
    }

    public String getDescrizionePagamento() {

	return descrizionePagamento;
    }

    public void setDescrizionePagamento(String descrizionePagamento) {

	this.descrizionePagamento = descrizionePagamento;
    }

    public Integer getImportoTotale() {

	return importoTotale;
    }

    public void setImportoTotale(Integer importoTotale) {

	this.importoTotale = importoTotale;
    }

    public DatiSingoloVersamento getDatiSingoloVersamento() {

	return datiSingoloVersamento;
    }

    public void setDatiSingoloVersamento(DatiSingoloVersamento datiSingoloVersamento) {

	this.datiSingoloVersamento = datiSingoloVersamento;
    }
}
