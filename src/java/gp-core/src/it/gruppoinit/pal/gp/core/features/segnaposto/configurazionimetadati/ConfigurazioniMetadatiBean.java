package it.gruppoinit.pal.gp.core.features.segnaposto.configurazionimetadati;

public class ConfigurazioniMetadatiBean {

    private String chiave;
    private String valore;
    private String categoria;
    private Integer ordine;
    private Integer idComuniAssociatiSoftware;

    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public Integer getIdComuniAssociatiSoftware() {

	return idComuniAssociatiSoftware;
    }

    public void setIdComuniAssociatiSoftware(Integer idComuniAssociatiSoftware) {

	this.idComuniAssociatiSoftware = idComuniAssociatiSoftware;
    }

    public String getCategoria() {

	return categoria;
    }

    public void setCategoria(String categoria) {

	this.categoria = categoria;
    }

    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }
}
