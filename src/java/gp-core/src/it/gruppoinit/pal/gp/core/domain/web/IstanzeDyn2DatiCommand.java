package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciHelper;

import java.util.ArrayList;
import java.util.List;

public class IstanzeDyn2DatiCommand extends BaseCommand {

    private List<Istanzedyn2modellit> listaModelliAttivati = new ArrayList<Istanzedyn2modellit>();
    private Boolean readOnlyModello = Boolean.FALSE;
    private ModellidinamiciHelper helperScheda = null;
    private String modello = null;
    private Integer codiceModello = null;
    private Dyn2Modellit dyn2Modellit = new Dyn2Modellit();

    
    public Dyn2Modellit getDyn2Modellit() {
    
        return this.dyn2Modellit;
    }

    
    public void setDyn2Modellit(Dyn2Modellit dyn2Modellit) {
    
        this.dyn2Modellit = dyn2Modellit;
    }

    public List<Istanzedyn2modellit> getListaModelliAttivati() {

	return this.listaModelliAttivati;
    }

    public void setListaModelliAttivati(List<Istanzedyn2modellit> listaModelliAttivati) {

	this.listaModelliAttivati = listaModelliAttivati;
    }

    public Boolean getReadOnlyModello() {

	return this.readOnlyModello;
    }

    public void setReadOnlyModello(Boolean readOnlyModello) {

	this.readOnlyModello = readOnlyModello;
    }

    public ModellidinamiciHelper getHelperScheda() {

	return this.helperScheda;
    }

    public void setHelperScheda(ModellidinamiciHelper helperScheda) {

	this.helperScheda = helperScheda;
    }

    public String getModello() {

	return this.modello;
    }

    public void setModello(String modello) {

	this.modello = modello;
    }

    public Integer getCodiceModello() {

	return this.codiceModello;
    }

    public void setCodiceModello(Integer codiceModello) {

	this.codiceModello = codiceModello;
    }
}
