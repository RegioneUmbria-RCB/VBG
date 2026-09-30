package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.GraduatoriedCom;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentodoctipo;

public class GraduatoriedComHelper {

    private GraduatoriedCom graduatoriedCom;
    private List<Movimentiallegati> movimentiAllegatis = new ArrayList<Movimentiallegati>();
    private List<Tipimovimentodoctipo> tipiMovdoctipos = new ArrayList<Tipimovimentodoctipo>();

    public GraduatoriedCom getGraduatoriedCom() {

	return graduatoriedCom;
    }

    public void setGraduatoriedCom(GraduatoriedCom graduatoriedCom) {

	this.graduatoriedCom = graduatoriedCom;
    }

    public List<Movimentiallegati> getMovimentiAllegatis() {

	return movimentiAllegatis;
    }

    public void setMovimentiAllegatis(List<Movimentiallegati> movimentiAllegatis) {

	this.movimentiAllegatis = movimentiAllegatis;
    }

    public List<Tipimovimentodoctipo> getTipiMovdoctipos() {

	return tipiMovdoctipos;
    }

    public void setTipiMovdoctipos(List<Tipimovimentodoctipo> tipiMovdoctipos) {

	this.tipiMovdoctipos = tipiMovdoctipos;
    }
}
