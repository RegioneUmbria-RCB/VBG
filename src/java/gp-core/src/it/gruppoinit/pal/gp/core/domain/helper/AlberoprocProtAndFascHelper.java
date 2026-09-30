package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;

import java.util.List;

public class AlberoprocProtAndFascHelper {

    private String comune;
    private List<AlberoprocProtocollo> alberoprocProtocollos;

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public List<AlberoprocProtocollo> getAlberoprocProtocollos() {

	return alberoprocProtocollos;
    }

    public void setAlberoprocProtocollos(List<AlberoprocProtocollo> alberoprocProtocollos) {

	this.alberoprocProtocollos = alberoprocProtocollos;
    }
}
