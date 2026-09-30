package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import java.util.Comparator;

public class FirmaRemotaParametroComparator implements Comparator<FirmaRemotaParametroModel> {

    @Override
    public int compare(FirmaRemotaParametroModel parametro1, FirmaRemotaParametroModel parametro2) {

	if (parametro1 == null && parametro2 == null) {
	    return 0;
	}
	if (parametro1 != null && parametro2 == null) {
	    return -1;
	}
	if (parametro1 == null && parametro2 != null) {
	    return 1;
	}
	Integer ordine1 = parametro1.getOrdine();
	Integer ordine2 = parametro2.getOrdine();
	if (ordine1 == null && ordine2 == null) {
	    return 0;
	}
	if (ordine1 != null && ordine2 == null) {
	    return -1;
	}
	if (ordine1 == null && ordine2 != null) {
	    return 1;
	}
	return ordine1.compareTo(ordine2);
    }
}