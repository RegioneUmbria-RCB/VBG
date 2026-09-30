package it.gruppoinit.pal.gp.core.dao.helper;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;

public class VwIstanzesoggetticollegatiHelper {

    private Software software;
    private List<Istanze> istanzesoggetticollegatis;

    public Software getSoftware() {

	return software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }

    public List<Istanze> getIstanzesoggetticollegatis() {

	return istanzesoggetticollegatis;
    }

    public void setIstanzesoggetticollegatis(List<Istanze> istanzesoggetticollegatis) {

	this.istanzesoggetticollegatis = istanzesoggetticollegatis;
    }
}