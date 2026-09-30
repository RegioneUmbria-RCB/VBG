package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Mercati;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConfigurazioneContiMercato implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2864644505062672194L;
    private List<MercatiContiHelper> contiHelperList;
    private Mercati mercati;
    private Integer anno;

    public ConfigurazioneContiMercato() {

	this.mercati = new Mercati();
	this.contiHelperList = new ArrayList<MercatiContiHelper>();
	this.anno = 0;
    }

    public List<MercatiContiHelper> getContiHelperList() {

	return contiHelperList;
    }

    public void setContiHelperList(List<MercatiContiHelper> contiHelperList) {

	this.contiHelperList = contiHelperList;
    }

    public Mercati getMercati() {

	return mercati;
    }

    public void setMercati(Mercati mercati) {

	this.mercati = mercati;
    }

    public Integer getAnno() {

	return anno;
    }

    public void setAnno(Integer anno) {

	this.anno = anno;
    }
}
