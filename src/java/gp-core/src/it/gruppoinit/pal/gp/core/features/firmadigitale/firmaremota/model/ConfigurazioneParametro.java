package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.ConfigurazioneParametroWs;

public class ConfigurazioneParametro {

    @XmlElement(name = "chiave")
    private String chiave;
    @XmlElement(name = "etichetta")
    private String etichetta;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "valore")
    private String valore;
    @XmlElement(name = "obbligatorio")
    private boolean obbligatorio;
    @XmlElement(name = "visibile")
    private boolean visibile;
    @XmlElement(name = "readOnly")
    private boolean readOnly;
    @XmlElement(name = "tipoCampo")
    private String tipoCampo;
    @XmlElement(name = "tipoordine")
    private Integer ordine;

    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
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

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public boolean isObbligatorio() {

	return obbligatorio;
    }

    public void setObbligatorio(boolean obbligatorio) {

	this.obbligatorio = obbligatorio;
    }

    public boolean isVisibile() {

	return visibile;
    }

    public void setVisibile(boolean visibile) {

	this.visibile = visibile;
    }

    public boolean isReadOnly() {

	return readOnly;
    }

    public void setReadOnly(boolean readOnly) {

	this.readOnly = readOnly;
    }

    public String getTipoCampo() {

	return tipoCampo;
    }

    public void setTipoCampo(String tipoCampo) {

	this.tipoCampo = tipoCampo;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    public Integer getOrdine() {

	return ordine;
    }

    public static ConfigurazioneParametro fromConfigurazioneParametroWs(ConfigurazioneParametroWs parametro) {

	if (parametro == null) {
	    return null;
	}
	ConfigurazioneParametro retVal = new ConfigurazioneParametro();
	retVal.setChiave(parametro.getChiave());
	retVal.setDescrizione(parametro.getDescrizione());
	retVal.setValore(parametro.getValore());
	retVal.setObbligatorio(parametro.isObbligatorio());
	return retVal;
    }
}
