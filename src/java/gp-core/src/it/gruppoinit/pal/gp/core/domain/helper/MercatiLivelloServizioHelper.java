package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.MercatiLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;

import java.util.ArrayList;
import java.util.List;

public class MercatiLivelloServizioHelper {

    private MercatiUso mercatiUso;
    private List<MercatiLivelloServizio> mercatiLivelloServizios = new ArrayList<MercatiLivelloServizio>();

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public List<MercatiLivelloServizio> getMercatiLivelloServizios() {

	return mercatiLivelloServizios;
    }

    public void setMercatiLivelloServizios(List<MercatiLivelloServizio> mercatiLivelloServizios) {

	this.mercatiLivelloServizios = mercatiLivelloServizios;
    }
}
