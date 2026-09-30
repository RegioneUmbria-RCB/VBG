package it.gruppoinit.pal.gp.core.features.commissioni.appello.models;

import java.util.Comparator;

import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;

public class CommedilizieAppelloComparator implements Comparator<CommedilizieAppello> {

    @Override
    public int compare(CommedilizieAppello o1, CommedilizieAppello o2) {

	if (o1.getCommedilizieCarica() != null && o2.getCommedilizieCarica() != null) {
	    int compare = o1.getCommedilizieCarica().getOrdinamento().compareTo(o2.getCommedilizieCarica().getOrdinamento());
	    if (compare != 0) {
		return compare;
	    }
	}
	return o1.getComponente().compareTo(o2.getComponente());
    }
}
