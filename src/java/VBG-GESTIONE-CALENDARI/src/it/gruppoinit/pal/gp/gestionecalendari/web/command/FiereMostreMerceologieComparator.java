package it.gruppoinit.pal.gp.gestionecalendari.web.command;

import it.gruppoinit.pal.gp.core.domain.FiereMostreMerceologie;

import java.util.Comparator;

public class FiereMostreMerceologieComparator implements Comparator<FiereMostreMerceologie> {

    @Override
    public int compare(FiereMostreMerceologie o1, FiereMostreMerceologie o2) {

	return o1.getMerceologia().compareTo(o2.getMerceologia());
    }
}
