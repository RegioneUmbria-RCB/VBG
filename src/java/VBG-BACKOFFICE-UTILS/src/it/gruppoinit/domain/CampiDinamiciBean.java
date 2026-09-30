package it.gruppoinit.domain;

import java.io.Serializable;
import java.util.List;

public class CampiDinamiciBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6625111309771369808L;
    private Integer codice;
    private String software;
    private String nomecampo;
    private String etichetta;
    private String descrizione;
    private String tipoDato;
    private Integer obbligatorio;
    private String scriptCodeBase64;
    private String scriptUpdateCodeBase64;
    private String fkd2bcId;
    private List<CampiDinamiciProprietaBean> props;
    private List<CampiDinamiciScript> scripts;

    public CampiDinamiciBean() {

	super();
    }

    public CampiDinamiciBean(Integer codice, String software, String nomecampo, String etichetta, String descrizione, String tipoDato,
	    Integer obbligatorio, String scriptCodeBase64, String scriptUpdateCodeBase64, String fkd2bcId) {

	this();
	this.codice = codice;
	this.software = software;
	this.nomecampo = nomecampo;
	this.etichetta = etichetta;
	this.descrizione = descrizione;
	this.tipoDato = tipoDato;
	this.obbligatorio = obbligatorio;
	this.scriptCodeBase64 = scriptCodeBase64;
	this.scriptUpdateCodeBase64 = scriptUpdateCodeBase64;
	this.fkd2bcId = fkd2bcId;
    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getNomecampo() {

	return nomecampo;
    }

    public void setNomecampo(String nomecampo) {

	this.nomecampo = nomecampo;
    }

    public String getEtichetta() {

	return etichetta;
    }

    public void setEtichetta(String etichetta) {

	this.etichetta = etichetta;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getTipoDato() {

	return tipoDato;
    }

    public void setTipoDato(String tipoDato) {

	this.tipoDato = tipoDato;
    }

    public Integer getObbligatorio() {

	return obbligatorio;
    }

    public void setObbligatorio(Integer obbligatorio) {

	this.obbligatorio = obbligatorio;
    }

    public String getScriptCodeBase64() {

	return scriptCodeBase64;
    }

    public void setScriptCodeBase64(String scriptCodeBase64) {

	this.scriptCodeBase64 = scriptCodeBase64;
    }

    public String getScriptUpdateCodeBase64() {

	return scriptUpdateCodeBase64;
    }

    public void setScriptUpdateCodeBase64(String scriptUpdateCodeBase64) {

	this.scriptUpdateCodeBase64 = scriptUpdateCodeBase64;
    }

    public String getFkd2bcId() {

	return fkd2bcId;
    }

    public void setFkd2bcId(String fkd2bcId) {

	this.fkd2bcId = fkd2bcId;
    }

    public List<CampiDinamiciProprietaBean> getProps() {

	return props;
    }

    public void setProps(List<CampiDinamiciProprietaBean> props) {

	this.props = props;
    }

    public List<CampiDinamiciScript> getScripts() {

	return scripts;
    }

    public void setScripts(List<CampiDinamiciScript> scripts) {

	this.scripts = scripts;
    }
}
