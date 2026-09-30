package it.gruppoinit.pal.gp.core.domain.web;

import java.util.HashMap;
import java.util.Map;

public class IstanzeOnLineHelper {

    private int numDomandeConErrori = 0;

    public int getNumDomandeConErrori() {

	return numDomandeConErrori;
    }

    public void setNumDomandeConErrori(int numDomandeConErrori) {

	this.numDomandeConErrori = numDomandeConErrori;
    }

    private Map<String, Integer> domandePervenute = new HashMap<String, Integer>();
    private boolean notificaVisibile = false;

    public boolean isNotificaVisibile() {

	return notificaVisibile;
    }

    public void setNotificaVisibile(boolean notificaVisibile) {

	this.notificaVisibile = notificaVisibile;
    }

    public Map<String, Integer> getDomandePervenute() {

	return domandePervenute;
    }

    public void setDomandePervenute(Map<String, Integer> domandePervenute) {

	this.domandePervenute = domandePervenute;
    }
}
