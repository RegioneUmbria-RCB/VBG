package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "procedimento")
public class ProcedimentoBean {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "nome")
    private String nome;
    @XmlElement(name = "data_aggiornamento")
    private String dataAggiornamento;
    @XmlElement(name = "tipologia")
    private String tipologia;
    @XmlElement(name = "natura")
    private String natura;
    @XmlElement(name = "amministrazione")
    private String amministrazione;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "requisiti")
    private String requisiti;
    @XmlElement(name = "adempimenti")
    private String adempimenti;
    @XmlElement(name = "scheda_regionale")
    private String schedaRegionale;
    @XmlElement(name = "oneri")
    private List<OneriBean> oneri;
    @XmlElement(name = "normativa")
    private List<NormativaBean> normativa;
    @XmlElement(name = "modulistica")
    private List<ModulisticaBean> modulistica;
    @XmlElement(name = "procedimenti_collegati")
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
