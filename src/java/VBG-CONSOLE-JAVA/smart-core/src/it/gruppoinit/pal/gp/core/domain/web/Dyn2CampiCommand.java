package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;

public class Dyn2CampiCommand extends PopupFlowBaseCommand<Dyn2Campi> {

    private String tipodato;
    private Boolean changeTipodato;
    private Integer dyn2ModellotId;

    public Integer getDyn2ModellotId() {

	return dyn2ModellotId;
    }

    public void setDyn2ModellotId(Integer dyn2ModellotId) {

	this.dyn2ModellotId = dyn2ModellotId;
    }

    public Dyn2CampiCommand() {

	this.entity = new Dyn2Campi();
	this.popup = Boolean.FALSE;
    }

    public String getTipodato() {

	return tipodato;
    }

    public void setTipodato(String tipodato) {

	this.tipodato = tipodato;
    }

    public Boolean getChangeTipodato() {

	return changeTipodato;
    }

    public void setChangeTipodato(Boolean changeTipodato) {

	this.changeTipodato = changeTipodato;
    }
    
    public boolean isDeleteAllowed(){
	return this.entity != null && (this.entity.getDyn2Modellids() == null || this.entity.getDyn2Modellids().size() == 0);
    }
    
    public int getNumeroQuadri(){
	int count = 0;
	if(entity != null && entity.getDyn2Modellids() != null){
	    count = entity.getDyn2Modellids().size();
	}
	return count;
    }
}
