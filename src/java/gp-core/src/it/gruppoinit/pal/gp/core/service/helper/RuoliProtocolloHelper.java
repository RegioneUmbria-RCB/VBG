package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

public class RuoliProtocolloHelper {

    private Comuni comune;
    private List<ChiaveValoreBean<Software, String>> cvb = new ArrayList<ChiaveValoreBean<Software, String>>();

    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    public List<ChiaveValoreBean<Software, String>> getCvb() {

	return cvb;
    }

    public void setCvb(List<ChiaveValoreBean<Software, String>> cvb) {

	this.cvb = cvb;
    }
}
