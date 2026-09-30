package it.gruppoinit.pal.gp.core.dao.helper;

import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;

import java.io.Serializable;
import java.util.Comparator;

public class GraduatorieDyn2DatiComparator implements Comparator<Istanzedyn2dati>, Serializable {

    private static final long serialVersionUID = 6116065212450138353L;

    private GraduatorieDyn2DatiComparator() {

    }

    /**
     * definisce l'ordinamento crescente da valori più piccoli a valori più grandi
     */
    public static final int ORDER_ASC = 0;
    /**
     * definisce l'ordinamento decrescente da valori più grandi a valori più piccoli
     */
    public static final int ORDER_DESC = 1;
    private int order = ORDER_ASC;

    /**
     * il tipo di ordinamento se crescente o decrescente
     * 
     * @param orderAscDesc
     *            intero preso dalle costanti di classe
     * @see GraduatorieDyn2DatiComparator.ORDER_ASC
     * @see GraduatorieDyn2DatiComparator.ORDER_DESC
     */
    public GraduatorieDyn2DatiComparator(int orderAscDesc) {

	this();
	if (!(orderAscDesc == ORDER_ASC || orderAscDesc == ORDER_DESC)) {
	    throw new RuntimeException("l'ordinamento deve essere scelto tra le costanti di classe");
	}
	this.order = orderAscDesc;
    }

    @Override
    public int compare(Istanzedyn2dati o1, Istanzedyn2dati o2) {

	switch (this.order) {
	case ORDER_DESC:
	    return compareDesc(o1, o2);
	default:
	    return compareAsc(o1, o2);
	}
    }

    public static int compareAsc(Istanzedyn2dati o1, Istanzedyn2dati o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return 1;
	}
	if (o1 == null && o2 != null) {
	    return -1;
	}
	return compara(o1.getValore(), o2.getValore(), ORDER_ASC);
    }

    public static int compareDesc(Istanzedyn2dati o1, Istanzedyn2dati o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	return compara(o1.getValore(), o2.getValore(), ORDER_DESC);
    }

    public static int compara(String valore1, String valore2, int orderAscDesc) {

	if ((valore1 == null || valore1.equals(""))) {
	    valore1 = "0";
	}
	if ((valore2 == null || valore2.equals(""))) {
	    valore2 = "0";
	}
	Double valore1Double = Double.parseDouble(valore1.replace(',', '.'));
	Double valore2Double = Double.parseDouble(valore2.replace(',', '.'));
	int valoreComparato = valore1Double.compareTo(valore2Double);
	if (valoreComparato == 0) {
	    return 0; // valori identici
	}
	if (orderAscDesc == ORDER_ASC) {
	    if (valoreComparato < 0) {
		// l'oggetto di valore1 è più piccolo di valore2
		// l'ordinamento è desc quindi devo tornare dal più grande
		// al più piccolo
		return -1;
	    } else {
		return 1;
	    }
	} else {
	    if (valoreComparato < 0) {
		// l'oggetto di valore1 è più piccolo di valore2
		// l'ordinamento è desc quindi devo tornare dal più piccolo
		// al più grande
		return 1;
	    } else {
		return -1;
	    }
	}
    }
    // public static void main(String[] args) {
    //
    // GraduatorieDyn2DatiComparator c = new GraduatorieDyn2DatiComparator(ORDER_DESC);
    // Istanzedyn2dati o1 = new Istanzedyn2dati();
    // Istanzedyn2dati o2 = new Istanzedyn2dati();
    // o1.setValore("210,357");
    // o2.setValore("210,358");
    // java.util.List<Istanzedyn2dati> list = new java.util.ArrayList<Istanzedyn2dati>();
    // list.add(o1);
    // list.add(o2);
    // java.util.Collections.sort(list, c);
    // for (java.util.Iterator iterator = list.iterator(); iterator.hasNext();) {
    // Istanzedyn2dati istanzedyn2dati = (Istanzedyn2dati) iterator.next();
    // System.out.println(istanzedyn2dati.getValore());
    // }
    // }
}
