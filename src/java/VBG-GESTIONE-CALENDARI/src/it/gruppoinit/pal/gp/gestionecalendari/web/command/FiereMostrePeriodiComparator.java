package it.gruppoinit.pal.gp.gestionecalendari.web.command;

import it.gruppoinit.pal.gp.core.domain.FiereMostrePeriodi;

import java.util.Comparator;

public class FiereMostrePeriodiComparator implements Comparator<FiereMostrePeriodi> {

    @Override
    public int compare(FiereMostrePeriodi o1, FiereMostrePeriodi o2) {

	return o1.getDal().compareTo(o2.getAl());
    }
}
