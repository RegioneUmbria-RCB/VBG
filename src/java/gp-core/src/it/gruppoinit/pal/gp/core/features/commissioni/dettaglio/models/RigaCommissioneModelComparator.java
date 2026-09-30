package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models;

import java.util.Comparator;

public class RigaCommissioneModelComparator implements Comparator<RigaCommissioneModel> {

    @Override
    public int compare(RigaCommissioneModel riga1, RigaCommissioneModel riga2) {

	return this.ordina(riga1, riga2);
    }

    private int ordina(RigaCommissioneModel riga1, RigaCommissioneModel riga2) {

	int c;
	c = Integer.valueOf(riga1.getOrdine()).compareTo(Integer.valueOf(riga2.getOrdine()));
	return c;
    }
}
