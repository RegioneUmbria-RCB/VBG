package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "posizione_debitoria")
public class PosizioneDebitoriaRequestType {

    @XmlElement(name = "id_posizione_debitoria")
    private BigDecimal idPosizioneDebitoria;
    @XmlElement(name = "codice_avviso")
    private String codiceAvviso;
    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "pagamento_completato")
    private Boolean pagamentoCompletato;
    @XmlElement(name = "descrizione_causale")
    private String descrizioneCausale;
    @XmlElement(name = "soggetto_debitore")
    private String soggettoDebitore;

    public PosizioneDebitoriaRequestType() {

    }

    public BigDecimal getIdPosizioneDebitoria() {

	return idPosizioneDebitoria;
    }

    public void setIdPosizioneDebitoria(BigDecimal idPosizioneDebitoria) {

	this.idPosizioneDebitoria = idPosizioneDebitoria;
    }

    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
    }

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public Boolean getPagamentoCompletato() {

	return pagamentoCompletato;
    }

    public void setPagamentoCompletato(Boolean pagamentoCompletato) {

	this.pagamentoCompletato = pagamentoCompletato;
    }

    public String getDescrizioneCausale() {

	return descrizioneCausale;
    }

    public void setDescrizioneCausale(String descrizioneCausale) {

	this.descrizioneCausale = descrizioneCausale;
    }

    public String getSoggettoDebitore() {

	return soggettoDebitore;
    }

    public void setSoggettoDebitore(String soggettoDebitore) {

	this.soggettoDebitore = soggettoDebitore;
    }
}
