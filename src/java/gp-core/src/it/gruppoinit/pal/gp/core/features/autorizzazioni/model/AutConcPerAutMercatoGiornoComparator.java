package it.gruppoinit.pal.gp.core.features.autorizzazioni.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.AreaPubblica;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.AutorizzazioniMercatoSrv;

/**
 * a parità di autorizzazione ordina per mercato / giorno secondo la denominazione dei giorni, LUN,MAR,MER.GIO,VEN,SAB,
 * DOM
 * 
 * @author riccardo.bocci
 *
 */
public class AutConcPerAutMercatoGiornoComparator implements Comparator<AutorizzazioniMercatoSrv> {

    @Override
    public int compare(AutorizzazioniMercatoSrv o1, AutorizzazioniMercatoSrv o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null) {
	    return 1;
	}
	String cf1 = StringUtils.defaultString(o1.getCodiceFiscale());
	String cf2 = StringUtils.defaultString(o2.getCodiceFiscale());
	int comp = cf1.compareTo(cf2);
	if (comp != 0) {
	    return comp;
	}
	Integer autId1 = o1.getId() == null ? Integer.valueOf(0) : o1.getId();
	Integer autId2 = o2.getId() == null ? Integer.valueOf(0) : o2.getId();
	if (!autId1.equals(autId2)) {
	    return autId1.compareTo(autId2);
	}
	// è la stessa autorizzazione cerco la proprietà area pubblica
	return compareAreaPubblica(o1, o2);
	//	if (StringUtils.isNotBlank(descrizioneLegge1) && StringUtils.isBlank(descrizioneLegge2)) {
	//	    return -1;
	//	}
	//	if (StringUtils.isBlank(descrizioneLegge1) && StringUtils.isNotBlank(descrizioneLegge2)) {
	//	    return 1;
	//	}
    }

    public static void main(String[] args) {

	List<AutorizzazioniMercatoSrv> auts = new ArrayList<AutorizzazioniMercatoSrv>();
	AutorizzazioniMercatoSrv aut2 = popolaMock("grgnicola73h23g888o", 1, "GRIOLI", "Lunedì");
	AutorizzazioniMercatoSrv aut1 = popolaMock("bccrcr73h23g888o", 1, "GRIOLI", "Lunedì");
	auts.add(aut2);
	auts.add(aut1);
	auts.add(popolaMock("grgnicola73h23g888o", 1, "GRIOLI", "Giovedì"));
	auts.add(popolaMock("grgnicola73h23g888o", 1, "GRIOLI", "Martedì"));
	auts.add(popolaMock("grgnicola73h23g888o", 1, "GRIOLI", "Domenica"));
	auts.add(popolaMock("grgnicola73h23g888o", 1, "GRIOLI", "Venerdì"));
	auts.add(popolaMock("grgnicola73h23g888o", 1, "GRIOLI", "MERCOLEDI"));
	auts.add(popolaMock("grgnicola73h23g888o", 1, "GRIOLI", "SABAT"));
	auts.add(popolaMock("grgnicola73h23g888o", 1, null, "Venerdì"));
	for (AutorizzazioniMercatoSrv autorizzazioniMercatoSrv : auts) {
	    System.out.println(ReflectionToStringBuilder.toString(autorizzazioniMercatoSrv, ToStringStyle.SHORT_PREFIX_STYLE));
	}
	System.out.println("============================");
	Collections.sort(auts, new AutConcPerAutMercatoGiornoComparator());
	for (AutorizzazioniMercatoSrv autorizzazioniMercatoSrv : auts) {
	    System.out.println(ReflectionToStringBuilder.toString(autorizzazioniMercatoSrv, ToStringStyle.SHORT_PREFIX_STYLE));
	}
    }

    public static AutorizzazioniMercatoSrv popolaMock(String cf, int idAut, String mercato, String giorno) {

	AutorizzazioniMercatoSrv aut1 = new AutorizzazioniMercatoSrv();
	aut1.setId(idAut);
	aut1.setCodiceFiscale(cf);
	if (StringUtils.isNotBlank(mercato)) {
	    AreaPubblica ap = new AreaPubblica();
	    ap.setDenominazione(mercato);
	    ap.setGiorno(giorno);
	    aut1.setAreaPubblica(ap);
	}
	return aut1;
    }

    private int compareAreaPubblica(AutorizzazioniMercatoSrv oggetto1, AutorizzazioniMercatoSrv oggetto2) {

	AreaPubblica ap1 = oggetto1.getAreaPubblica();
	AreaPubblica ap2 = oggetto2.getAreaPubblica();
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
