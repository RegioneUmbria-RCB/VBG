package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Istanze;

import java.util.ArrayList;
import java.util.List;

public class IstanzeHelper {

    private List<Istanze> istanzes;
    private List<Istanze> storicoistanzes;

    public IstanzeHelper() {

	this.istanzes = new ArrayList<Istanze>();
    }

    public List<Istanze> getIstanzes() {

	return istanzes;
    }

    public void setIstanzes(List<Istanze> istanzes) {

	this.istanzes = istanzes;
    }

    public List<Istanze> getStoricoistanzes() {

	return storicoistanzes;
    }

    public void setStoricoistanzes(List<Istanze> storicoistanzes) {

	this.storicoistanzes = storicoistanzes;
    }
}
