package it.gruppoinit.pal.gp.core.features.oneri;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class ChiavePerCausaleDatPagamentoDataScadenzaTipologiaComparator implements Comparator<ChiavePerCausaleDatPagamentoDataScadenzaTipologia> {

    @Override
    public int compare(ChiavePerCausaleDatPagamentoDataScadenzaTipologia chiave1, ChiavePerCausaleDatPagamentoDataScadenzaTipologia chiave2) {

	int c = 0;
	if (StringUtils.isNotBlank(chiave1.getRaggruppamento())) {
	    if (StringUtils.isNotBlank(chiave2.getRaggruppamento())) {
		c = chiave1.getRaggruppamento().compareTo(chiave2.getRaggruppamento());
	    } else {
		return 1;
	    }
	} else if (StringUtils.isNotBlank(chiave2.getRaggruppamento())) {
	    return -1;
	}
	if (c == 0) {
	    if (StringUtils.isNotBlank(chiave1.getCausale())) {
		c = chiave1.getCausale().compareTo(chiave2.getCausale());
	    } else if (StringUtils.isNotBlank(chiave2.getCausale())) {
		return 1;
	    }
	}
	if (c == 0) {
	    if (chiave1.getDataScadenza() != null) {
		if (chiave2.getDataScadenza() != null) {
		    c = chiave1.getDataScadenza().compareTo(chiave2.getDataScadenza());
		} else {
		    return 1;
		}
	    } else if (chiave2.getDataScadenza() != null) {
		return -1;
	    }
	}
	if (c == 0) {
	    if (chiave1.getDataPagamento() != null) {
		if (chiave2.getDataPagamento() != null) {
		    c = chiave1.getDataPagamento().compareTo(chiave2.getDataPagamento());
		} else {
		    return 1;
		}
	    } else if (chiave2.getDataPagamento() != null) {
		return -1;
	    }
	}
	return c;
    }
}
