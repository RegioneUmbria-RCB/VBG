package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;

public class IstanzestradarioCommand extends BaseCommand {

    private boolean vertCartograficoAttiva;
    private Istanzestradario entity;
    private Istanzemappali istanzemappali;

    public IstanzestradarioCommand() {

	this.entity = new Istanzestradario();
	this.istanzemappali = new Istanzemappali();
    }

    public Istanzestradario getEntity() {

	return entity;
    }

    public void setEntity(Istanzestradario entity) {

	this.entity = entity;
    }

    public Istanzemappali getIstanzemappali() {

	return istanzemappali;
    }

    public void setIstanzemappali(Istanzemappali istanzemappali) {

	this.istanzemappali = istanzemappali;
    }

    public boolean isVertCartograficoAttiva() {

	return vertCartograficoAttiva;
    }

    public void setVertCartograficoAttiva(boolean vertCartograficoAttiva) {

	this.vertCartograficoAttiva = vertCartograficoAttiva;
    }
}
