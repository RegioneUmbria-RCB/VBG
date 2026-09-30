package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

public class ProcedimentoBean {

    private Integer id;
    private String nome;
    private String dataAggiornamento;
    private String tipologia;
    private String natura;
    private String amministrazione;
    private String descrizione;
    private String requisiti;
    private String adempimenti;
    private String schedaRegionale;
    private List<OneriBean> oneri;
    private List<NormativaBean> normativa;
    private List<ModulisticaBean> modulistica;
    private List<EndoprocedimentoSimpleBean> procedimentiCollegati = new ArrayList<EndoprocedimentoSimpleBean>();

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getDataAggiornamento() {

	return dataAggiornamento;
    }

    public void setDataAggiornamento(String dataAggiornamento) {

	this.dataAggiornamento = dataAggiornamento;
    }

    public String getTipologia() {

	return tipologia;
    }

    public void setTipologia(String tipologia) {

	this.tipologia = tipologia;
    }

    public String getNatura() {

	return natura;
    }

    public void setNatura(String natura) {

	this.natura = natura;
    }

    public String getAmministrazione() {

	return amministrazione;
    }

    public void setAmministrazione(String amministrazione) {

	this.amministrazione = amministrazione;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getRequisiti() {

	return requisiti;
    }

    public void setRequisiti(String requisiti) {

	this.requisiti = requisiti;
    }

    public String getAdempimenti() {

	return adempimenti;
    }

    public void setAdempimenti(String adempimenti) {

	this.adempimenti = adempimenti;
    }

    public String getSchedaRegionale() {

	return schedaRegionale;
    }

    public void setSchedaRegionale(String schedaRegionale) {

	this.schedaRegionale = schedaRegionale;
    }

    public List<OneriBean> getOneri() {

	return oneri;
    }

    public void setOneri(List<OneriBean> oneri) {

	this.oneri = oneri;
    }

    public List<NormativaBean> getNormativa() {

	return normativa;
    }

    public void setNormativa(List<NormativaBean> normativa) {

	this.normativa = normativa;
    }

    public List<ModulisticaBean> getModulistica() {

	return modulistica;
    }

    public void setModulistica(List<ModulisticaBean> modulistica) {

	this.modulistica = modulistica;
    }

    public List<EndoprocedimentoSimpleBean> getProcedimentiCollegati() {

	return procedimentiCollegati;
    }

    public void setProcedimentiCollegati(List<EndoprocedimentoSimpleBean> procedimentiCollegati) {

	this.procedimentiCollegati = procedimentiCollegati;
    }
}
