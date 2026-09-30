package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiPagamento", propOrder = { "esito", "esitoEnum", "importoTotalePagato", "iuv", "ccp", "datiSingoloPagamento" })
public class DatiPagamento {

    @XmlElement(name = "Esito")
    private int esito;
    private EsitoStatoPagamento esitoEnum;
    @XmlElement(name = "ImportoTotalePagato")
    private Integer importoTotalePagato;
    @XmlElement(name = "IUV")
    private String iuv;
    @XmlElement(name = "CCP")
    private String ccp;
    @XmlElement(name = "DatiSingoloPagamento")
    private DatiSingoloPagamento datiSingoloPagamento;

    public int getEsito() {

	return esito;
    }

    public void setEsito(int esito) {

	this.esito = esito;
    }

    public EsitoStatoPagamento getEsitoEnum() {

	return EsitoStatoPagamento.fromValue(this.getEsito());
    }

    public Integer getImportoTotalePagato() {

	return importoTotalePagato;
    }

    public void setImportoTotalePagato(Integer importoTotalePagato) {

	this.importoTotalePagato = importoTotalePagato;
    }

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public String getCcp() {

	return ccp;
    }

    public void setCcp(String ccp) {

	this.ccp = ccp;
    }

    public DatiSingoloPagamento getDatiSingoloPagamento() {

	return datiSingoloPagamento;
    }

    public void setDatiSingoloPagamento(DatiSingoloPagamento datiSingoloPagamento) {

	this.datiSingoloPagamento = datiSingoloPagamento;
    }
    
    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
