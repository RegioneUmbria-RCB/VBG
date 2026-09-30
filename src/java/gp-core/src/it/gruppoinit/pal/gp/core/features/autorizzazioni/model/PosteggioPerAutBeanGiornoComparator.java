package it.gruppoinit.pal.gp.core.features.autorizzazioni.model;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class PosteggioPerAutBeanGiornoComparator implements Comparator<PosteggioPerAutBean> {

    @Override
    public int compare(PosteggioPerAutBean ap1, PosteggioPerAutBean ap2) {

	if (ap1 == null && ap2 == null) {
	    return 0;
	}
	if (ap1 != null && ap2 == null) {
	    return -1;
	}
	if (ap1 == null) {
	    return 1;
	}
	String giorno1 = StringUtils.left(StringUtils.defaultString(ap1.getGiorno(), MercatoGiorniSettimanaEnum.NESSUNO.getValore()), 3);
	String giorno2 = StringUtils.left(StringUtils.defaultString(ap2.getGiorno(), MercatoGiorniSettimanaEnum.NESSUNO.getValore()), 3);
	Integer ordine1 = MercatoGiorniSettimanaEnum.getOrdineDaValore(giorno1);
	Integer ordine2 = MercatoGiorniSettimanaEnum.getOrdineDaValore(giorno2);
	return ordine1.compareTo(ordine2);
    }
}
