package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoCreaIuv", propOrder = { "codiceRisposta", "idChiamata", "dataoraRichiesta", "totIuv", "url" })
public class EsitoCreaIuv {

    @XmlElement(name = "codice_risposta")
    private String codiceRisposta;
    @XmlElement(name = "id_chiamata")
    private Integer idChiamata;
    @XmlElement(name = "dataora_richiesta")
    private String dataoraRichiesta;
    @XmlElement(name = "tot_iuv")
    private Integer totIuv;
    @XmlElement(name = "url")
    private String url;

    public String getCodiceRisposta() {

	return codiceRisposta;
    }

    public void setCodiceRisposta(String codiceRisposta) {

	this.codiceRisposta = codiceRisposta;
    }

    public Integer getIdChiamata() {

	return idChiamata;
    }

    public void setIdChiamata(Integer idChiamata) {

	this.idChiamata = idChiamata;
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

    public String getUrl() {

	return url;
    }

    public void setUrl(String url) {

	this.url = url;
    }
}
