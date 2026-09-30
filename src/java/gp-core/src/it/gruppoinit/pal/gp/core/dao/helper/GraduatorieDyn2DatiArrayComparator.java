package it.gruppoinit.pal.gp.core.dao.helper;

import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;

import java.io.Serializable;
import java.util.Comparator;

public class GraduatorieDyn2DatiArrayComparator implements Comparator<Object[]>, Serializable {

    private static final long serialVersionUID = 4014384479671047318L;

    private GraduatorieDyn2DatiArrayComparator() {

    }

    Integer[] orderAscDesc = null;

    public GraduatorieDyn2DatiArrayComparator(Integer[] orderAscDesc) {

	this();
	this.orderAscDesc = orderAscDesc;
    }

    @Override
    public int compare(Object[] o1, Object[] o2) {

	int result = 0;
	for (int i = 0; i < o1.length; i++) {
	    Istanzedyn2dati id2d1 = (Istanzedyn2dati) o1[i];
	    Istanzedyn2dati id2d2 = (Istanzedyn2dati) o2[i];
	    result = GraduatorieDyn2DatiComparator.compara(id2d1.getValore(), id2d2.getValore(), orderAscDesc[i]);
	    if (result != 0) {
		break;
	    }
	}
	return result;
    }
    // public static void main(String[] args) {
    //
    // Integer[] ordinamenti = new Integer[2];
    // ordinamenti[0] = GraduatorieDyn2DatiComparator.ORDER_DESC;
    // ordinamenti[1] = GraduatorieDyn2DatiComparator.ORDER_ASC;
    // GraduatorieDyn2DatiArrayComparator c = new GraduatorieDyn2DatiArrayComparator(ordinamenti);
    // Istanzedyn2dati o1 = new Istanzedyn2dati();
    // Istanzedyn2dati o2 = new Istanzedyn2dati();
    //
    // o1.setValore("210,357");
    // o2.setValore("20080921");
    //
    // Istanzedyn2dati o3 = new Istanzedyn2dati();
    // Istanzedyn2dati o4 = new Istanzedyn2dati();
    //
    // o3.setValore("210,357");
    // o4.setValore("20080922");
    //
    // List<Istanzedyn2dati[]> list = new ArrayList<Istanzedyn2dati[]>();
    //
    // Istanzedyn2dati[] list1 = new Istanzedyn2dati[2];
    // list1[0] = o1;
    // list1[1] = o2;
    //
    // Istanzedyn2dati[] list2 = new Istanzedyn2dati[2];
    // list2[0] = o3;
    // list2[1] = o4;
    //
    // list.add(list1);
    // list.add(list2);
    //
    // Collections.sort(list, c);
    //
    // for (Iterator iterator = list.iterator(); iterator.hasNext();) {
    // Istanzedyn2dati[] istanzedyn2dati = (Istanzedyn2dati[]) iterator.next();
    // for (Istanzedyn2dati istanzedyn2dati2 : istanzedyn2dati) {
    // System.out.println(istanzedyn2dati2.getValore());
    // }
    // }
    //
    // }
}
