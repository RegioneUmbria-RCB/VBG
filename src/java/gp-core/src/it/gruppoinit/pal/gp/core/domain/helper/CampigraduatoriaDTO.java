package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class CampigraduatoriaDTO {

    private PkId id;
    private Dyn2CampiDTO dyn2Campi;
    private String valore;
    private Integer ordine;

    public CampigraduatoriaDTO() {

	id = new PkId();
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public Dyn2CampiDTO getDyn2Campi() {

	return dyn2Campi;
    }

    public void setDyn2Campi(Dyn2CampiDTO dyn2Campi) {

	this.dyn2Campi = dyn2Campi;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }
}
