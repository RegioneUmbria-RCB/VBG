package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.MercatiDLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;

import java.util.ArrayList;
import java.util.List;

public class MercatiDLivelloServizioHelper {

    private MercatiUso mercatiUso;
    private List<MercatiDLivelloServizio> mercatiDLivelloServizios = new ArrayList<MercatiDLivelloServizio>();

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public List<MercatiDLivelloServizio> getMercatiDLivelloServizios() {

	return mercatiDLivelloServizios;
    }

    public void setMercatiDLivelloServizios(List<MercatiDLivelloServizio> mercatiDLivelloServizios) {

	this.mercatiDLivelloServizios = mercatiDLivelloServizios;
    }
}
