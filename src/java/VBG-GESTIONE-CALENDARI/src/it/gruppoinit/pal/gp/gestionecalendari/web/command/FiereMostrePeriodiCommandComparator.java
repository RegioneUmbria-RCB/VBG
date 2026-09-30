package it.gruppoinit.pal.gp.gestionecalendari.web.command;

import java.util.Comparator;

public class FiereMostrePeriodiCommandComparator implements Comparator<FiereMostrePeriodiCommand> {

    @Override
    public int compare(FiereMostrePeriodiCommand o1, FiereMostrePeriodiCommand o2) {

	return o1.getDal().compareTo(o2.getDal());
    }
}
