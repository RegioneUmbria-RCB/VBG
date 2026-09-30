package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;

import java.util.ArrayList;
import java.util.List;

public class MercatiFormuleCalcoloHelper {

    private MercatiUso mercatiUso;
    private List<MercatiFormuleCalcolo> mercatiFormuleCalcolos = new ArrayList<MercatiFormuleCalcolo>();

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public List<MercatiFormuleCalcolo> getMercatiFormuleCalcolos() {

	return mercatiFormuleCalcolos;
    }

    public void setMercatiFormuleCalcolos(List<MercatiFormuleCalcolo> mercatiFormuleCalcolos) {

	this.mercatiFormuleCalcolos = mercatiFormuleCalcolos;
    }
}
