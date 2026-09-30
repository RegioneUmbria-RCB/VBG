package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.List;

public class InterventoBean {

    private Integer id;
    private String nome;
    private String informazioni;
    private String schedaRegionale;
    private List<NormativaBean> normativa;
    private List<FasiattuativeBean> fasiAttuative;
    private List<ModulisticaBean> modulistica;
    private List<FamiglieEndoBean> procedimentiNecessari;
    private List<FamiglieEndoBean> procedimentiRicorrenti;
    private List<FamiglieEndoBean> procedimentiEventuali;
    private List<EndoprocedimentoSimpleBean> interventiLocali;
    private List<OneriBean> oneri;
    private List<String> note;
    private Boolean modelloDomandaPresente;
    private Boolean presentabileOnline;
    private List<PercorsoBean> percorso;

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

    public String getInformazioni() {

	return informazioni;
    }

    public void setInformazioni(String informazioni) {

	this.informazioni = informazioni;
    }

    public String getSchedaRegionale() {

	return schedaRegionale;
    }

    public void setSchedaRegionale(String schedaRegionale) {

	this.schedaRegionale = schedaRegionale;
    }

    public List<NormativaBean> getNormativa() {

	return normativa;
    }

    public void setNormativa(List<NormativaBean> normativa) {

	this.normativa = normativa;
    }

    public List<FasiattuativeBean> getFasiAttuative() {

	return fasiAttuative;
    }

    public void setFasiAttuative(List<FasiattuativeBean> fasiAttuative) {

	this.fasiAttuative = fasiAttuative;
    }

    public List<ModulisticaBean> getModulistica() {

	return modulistica;
    }

    public void setModulistica(List<ModulisticaBean> modulistica) {

	this.modulistica = modulistica;
    }

    public List<FamiglieEndoBean> getProcedimentiNecessari() {

	return procedimentiNecessari;
    }

    public void setProcedimentiNecessari(List<FamiglieEndoBean> procedimentiNecessari) {

	this.procedimentiNecessari = procedimentiNecessari;
    }

    public List<FamiglieEndoBean> getProcedimentiRicorrenti() {

	return procedimentiRicorrenti;
    }

    public void setProcedimentiRicorrenti(List<FamiglieEndoBean> procedimentiRicorrenti) {

	this.procedimentiRicorrenti = procedimentiRicorrenti;
    }

    public List<FamiglieEndoBean> getProcedimentiEventuali() {

	return procedimentiEventuali;
    }

    public void setProcedimentiEventuali(List<FamiglieEndoBean> procedimentiEventuali) {

	this.procedimentiEventuali = procedimentiEventuali;
    }

    public List<OneriBean> getOneri() {

	return oneri;
    }

    public void setOneri(List<OneriBean> oneri) {

	this.oneri = oneri;
    }

    public List<EndoprocedimentoSimpleBean> getInterventiLocali() {

	return interventiLocali;
    }

    public void setInterventiLocali(List<EndoprocedimentoSimpleBean> interventiLocali) {

	this.interventiLocali = interventiLocali;
    }

    public List<String> getNote() {

	return note;
    }

    public void setNote(List<String> note) {

	this.note = note;
    }

    public Boolean getModelloDomandaPresente() {

	return modelloDomandaPresente;
    }

    public void setModelloDomandaPresente(Boolean modelloDomandaPresente) {

	this.modelloDomandaPresente = modelloDomandaPresente;
    }

    public Boolean getPresentabileOnline() {

	return presentabileOnline;
    }

    public void setPresentabileOnline(Boolean presentabileOnline) {

	this.presentabileOnline = presentabileOnline;
    }

    public List<PercorsoBean> getPercorso() {

	return percorso;
    }

    public void setPercorso(List<PercorsoBean> percorso) {

	this.percorso = percorso;
    }
}
