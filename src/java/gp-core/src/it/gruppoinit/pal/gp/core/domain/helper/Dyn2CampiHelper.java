package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Mappature;

import java.util.List;

public class Dyn2CampiHelper {

    private Dyn2Campi campo;
    private List<Mappature> mappatures;

    public Dyn2CampiHelper() {

	this.campo = new Dyn2Campi();
    }

    public Dyn2Campi getCampo() {

	return campo;
    }

    public void setCampo(Dyn2Campi campo) {

	this.campo = campo;
    }

    public List<Mappature> getMappatures() {

	return mappatures;
    }

    public void setMappatures(List<Mappature> mappatures) {

	this.mappatures = mappatures;
    }
}
