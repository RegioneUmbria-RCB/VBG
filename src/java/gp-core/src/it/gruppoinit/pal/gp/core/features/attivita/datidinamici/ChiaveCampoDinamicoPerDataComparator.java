package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import java.util.Comparator;

public class ChiaveCampoDinamicoPerDataComparator implements Comparator<ChiaveCampoDinamicoPerData> {

    private boolean ordineCrescente = true;

    public ChiaveCampoDinamicoPerDataComparator(boolean ordineCrescente) {

	this.ordineCrescente = ordineCrescente;
    }

    @Override
    public int compare(ChiaveCampoDinamicoPerData chiave1, ChiaveCampoDinamicoPerData chiave2) {

	if (ordineCrescente) {
	    return this.ordina(chiave1, chiave2);
	}
	return this.ordina(chiave2, chiave1);
    }

    private int ordina(ChiaveCampoDinamicoPerData chiave1, ChiaveCampoDinamicoPerData chiave2) {

	int c;
	c = chiave1.getIdCampo().compareTo(chiave2.getIdCampo());
	if (c == 0) {
	    c = chiave1.getDataSnapshot().compareTo(chiave2.getDataSnapshot());
	}
	if (c == 0) {
	    c = chiave1.getIndice().compareTo(chiave2.getIndice());
	}
	if (c == 0) {
	    c = chiave1.getIndiceMolteplicita().compareTo(chiave2.getIndiceMolteplicita());
	}
	return c;
    }
}
