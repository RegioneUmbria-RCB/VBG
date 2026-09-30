package it.gruppoinit.pal.gp.core.features.oneri;

import java.util.Comparator;

import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;

public class IstanzeoneriComparator implements Comparator<Istanzeoneri> {

    @Override
    public int compare(Istanzeoneri chiave1, Istanzeoneri chiave2) {

	int c = 0;
	//1. per raggruppamento
	if (chiave1.getTipicausalioneri().getRaggruppamentocausalioneri() != null) {
	    if (chiave2.getTipicausalioneri().getRaggruppamentocausalioneri() != null) {
		c = chiave1.getTipicausalioneri().getRaggruppamentocausalioneri().getRcoDescr()
			.compareTo(chiave2.getTipicausalioneri().getRaggruppamentocausalioneri().getRcoDescr());
	    } else {
		return 1;
	    }
	} else if (chiave2.getTipicausalioneri().getRaggruppamentocausalioneri() != null) {
	    return -1;
	}
	//2. per causale
	if (c == 0) {
	    c = chiave1.getTipicausalioneri().getCoDescrizione().compareTo(chiave2.getTipicausalioneri().getCoDescrizione());
	}
	//3. per data scadenza
	if (c == 0) {
	    if (chiave1.getDatascadenza() != null) {
		if (chiave2.getDatascadenza() != null) {
		    c = chiave1.getDatascadenza().compareTo(chiave2.getDatascadenza());
		} else {
		    return 1;
		}
	    } else if (chiave2.getDatascadenza() != null) {
		return -1;
	    }
	}
	//4. per data pagamento
	if (c == 0) {
	    if (chiave1.getDatapagamento() != null) {
		if (chiave2.getDatapagamento() != null) {
		    c = chiave1.getDatapagamento().compareTo(chiave2.getDatapagamento());
		} else {
		    return 1;
		}
	    } else if (chiave2.getDatapagamento() != null) {
		return -1;
	    }
	}
	//5. per id
	if (c == 0) {
	    c = chiave1.getId().getCodice().compareTo(chiave2.getId().getCodice());
	}
	return c;
    }
}
