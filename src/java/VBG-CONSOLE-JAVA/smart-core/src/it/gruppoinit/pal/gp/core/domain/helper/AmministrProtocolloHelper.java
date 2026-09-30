package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.AmministrProtocollo;

import java.util.ArrayList;
import java.util.List;

public class AmministrProtocolloHelper {

    private String comune;
    private List<AmministrProtocollo> amministrProtocollos = new ArrayList<AmministrProtocollo>();

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public List<AmministrProtocollo> getAmministrProtocollos() {

	return amministrProtocollos;
    }

    public void setAmministrProtocollos(List<AmministrProtocollo> amministrProtocollos) {

	this.amministrProtocollos = amministrProtocollos;
    }
}
