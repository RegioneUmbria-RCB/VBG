package it.gruppoinit.pal.gp.gestionecalendari.web.command;

import java.util.Comparator;

public class FiereMostreMerceologieCommandComparator implements Comparator<FiereMostreMerceologieCommand> {

    @Override
    public int compare(FiereMostreMerceologieCommand o1, FiereMostreMerceologieCommand o2) {

	return o1.getMerceologia().compareTo(o2.getMerceologia());
    }
}
