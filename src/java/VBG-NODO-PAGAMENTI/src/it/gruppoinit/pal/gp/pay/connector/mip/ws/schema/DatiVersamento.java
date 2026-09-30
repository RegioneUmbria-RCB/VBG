package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiVersamento", propOrder = { "tassonomiaAvviso", "dataScadenzaPagamento", "dataScadenzaAvviso", "descrizionePagamento",
	"tipoPagamento", "importoTotale", "datiSingoloVersamento" })
public class DatiVersamento {

    @XmlElement(name = "TassonomiaAvviso")
    private String tassonomiaAvviso;
    @XmlElement(name = "DataScadenzaPagamento")
    private String dataScadenzaPagamento;
    @XmlElement(name = "DataScadenzaAvviso")
    private String dataScadenzaAvviso;
    @XmlElement(name = "DescrizionePagamento")
    private String descrizionePagamento;
    @XmlElement(name = "TipoPagamento")
    private Integer tipoPagamento;
    @XmlElement(name = "ImportoTotale")
    private Integer importoTotale;
    @XmlElement(name = "DatiSingoloVersamento")
    private DatiSingoloVersamento datiSingoloVersamento;

    public String getTassonomiaAvviso() {

	return tassonomiaAvviso;
    }

    public void setTassonomiaAvviso(String tassonomiaAvviso) {

	this.tassonomiaAvviso = tassonomiaAvviso;
    }

    public String getDataScadenzaPagamento() {

	return dataScadenzaPagamento;
    }

    public void setDataScadenzaPagamento(String dataScadenzaPagamento) {

	this.dataScadenzaPagamento = dataScadenzaPagamento;
    }

    public String getDataScadenzaAvviso() {

	return dataScadenzaAvviso;
    }

    public void setDataScadenzaAvviso(String dataScadenzaAvviso) {

	this.dataScadenzaAvviso = dataScadenzaAvviso;
    }

    public String getDescrizionePagamento() {

	return descrizionePagamento;
    }

    public void setDescrizionePagamento(String descrizionePagamento) {

	this.descrizionePagamento = descrizionePagamento;
    }

    public Integer getTipoPagamento() {

	return tipoPagamento;
    }

    public void setTipoPagamento(Integer tipoPagamento) {

	this.tipoPagamento = tipoPagamento;
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
