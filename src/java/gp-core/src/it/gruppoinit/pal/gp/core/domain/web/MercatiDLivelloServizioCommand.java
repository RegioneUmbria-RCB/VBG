package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.MercatiDLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;

public class MercatiDLivelloServizioCommand extends BaseCommand {

    private MercatiDLivelloServizio entity;
    private MercatiUso mercatiUso;
    private String[] listacodici;
    private LivelloServizioWizard livelloServizioWizard;

    public MercatiDLivelloServizio getEntity() {

	return entity;
    }

    public void setEntity(MercatiDLivelloServizio entity) {

	this.entity = entity;
    }

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public String[] getListacodici() {

	return listacodici;
    }

    public void setListacodici(String[] listacodici) {

	this.listacodici = listacodici;
    }

    
    public LivelloServizioWizard getLivelloServizioWizard() {
    
        return livelloServizioWizard;
    }

    
    public void setLivelloServizioWizard(LivelloServizioWizard livelloServizioWizard) {
    
        this.livelloServizioWizard = livelloServizioWizard;
    }
    
}
