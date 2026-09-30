package it.gruppoinit.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class InventarioprocedimentoBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3220764829170026535L;
    private String naturabase;
    private String procedimento;
    private Integer amministrazione;
    private Date dataaggiornamento;
    private Integer codiceTipo;
    private Integer disabilitato;
    private Integer ordine;
    private String codiceancitel;
    private Integer flagPubblica;
    private String software;
    private Integer codice;
    private List<SubEndoBean> subEndos;
    private List<InventarioprocedimentoSoftwareBean> ipSoftwareBeans;

    public InventarioprocedimentoBean() {

	super();
    }

    public InventarioprocedimentoBean(String naturabase, String procedimento, Integer amministrazione, Date dataaggiornamento, Integer codiceTipo,
	    Integer disabilitato, Integer ordine, String codiceancitel, Integer flagPubblica, String software, Integer codice) {

	this();
	this.naturabase = naturabase;
	this.procedimento = procedimento;
	this.amministrazione = amministrazione;
	this.dataaggiornamento = dataaggiornamento;
	this.codiceTipo = codiceTipo;
	this.disabilitato = disabilitato;
	this.ordine = ordine;
	this.codiceancitel = codiceancitel;
	this.flagPubblica = flagPubblica;
	this.software = software;
	this.codice = codice;
    }

    public String getNaturabase() {

	return naturabase;
    }

    public void setNaturabase(String naturabase) {

	this.naturabase = naturabase;
    }

    public String getProcedimento() {

	return procedimento;
    }

    public void setProcedimento(String procedimento) {

	this.procedimento = procedimento;
    }

    public Integer getAmministrazione() {

	return amministrazione;
    }

    public void setAmministrazione(Integer amministrazione) {

	this.amministrazione = amministrazione;
    }

    public Date getDataaggiornamento() {

	return dataaggiornamento;
    }

    public void setDataaggiornamento(Date dataaggiornamento) {

	this.dataaggiornamento = dataaggiornamento;
    }

    public Integer getCodiceTipo() {

	return codiceTipo;
    }

    public void setCodiceTipo(Integer codiceTipo) {

	this.codiceTipo = codiceTipo;
    }

    public Integer getDisabilitato() {

	return disabilitato;
    }

    public void setDisabilitato(Integer disabilitato) {

	this.disabilitato = disabilitato;
    }

    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    public String getCodiceancitel() {

	return codiceancitel;
    }

    public void setCodiceancitel(String codiceancitel) {

	this.codiceancitel = codiceancitel;
    }

    public Integer getFlagPubblica() {

	return flagPubblica;
    }

    public void setFlagPubblica(Integer flagPubblica) {

	this.flagPubblica = flagPubblica;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public List<SubEndoBean> getSubEndos() {

	return subEndos;
    }

    public void setSubEndos(List<SubEndoBean> subEndos) {

	this.subEndos = subEndos;
    }

    public List<InventarioprocedimentoSoftwareBean> getIpSoftwareBeans() {

	return ipSoftwareBeans;
    }

    public void setIpSoftwareBeans(List<InventarioprocedimentoSoftwareBean> ipSoftwareBeans) {

	this.ipSoftwareBeans = ipSoftwareBeans;
    }
}
