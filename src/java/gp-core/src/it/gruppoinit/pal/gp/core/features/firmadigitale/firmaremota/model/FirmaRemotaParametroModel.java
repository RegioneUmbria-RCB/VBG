package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

public class FirmaRemotaParametroModel {

    private String chiave;
    private String descrizione;
    private boolean obbligatorio;
    private boolean readOnly;
    private String tipoCampo;
    private String valoreDefault;
    private boolean visibile;
    private Integer ordine;

    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public boolean isObbligatorio() {

	return obbligatorio;
    }

    public void setObbligatorio(boolean obbligatorio) {

	this.obbligatorio = obbligatorio;
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

    public String getValoreDefault() {

	return valoreDefault;
    }

    public void setValoreDefault(String valoreDefault) {

	this.valoreDefault = valoreDefault;
    }

    public boolean isVisibile() {

	return visibile;
    }

    public void setVisibile(boolean visibile) {

	this.visibile = visibile;
    }

    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }
}
