package it.gruppoinit.pal.gp.pay.service.helper;

import java.util.Comparator;
import java.util.Date;

import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.pay.domain.PayRichieste;

public class PayRichiesteDataRichiestaComparator implements Comparator<PayRichieste> {

    private OrderTypeEnum ordinamento;

    private PayRichiesteDataRichiestaComparator() {

	this.ordinamento = OrderTypeEnum.ASC;
    }

    public PayRichiesteDataRichiestaComparator(OrderTypeEnum ordinamento) {

	this();
	this.ordinamento = ordinamento;
    }

    private int compareConOrdinamento(PayRichieste o1, PayRichieste o2) {

	int risultato = 0;
	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return ordinamento(-1);
	}
	if (o1 == null) {
	    return ordinamento(1);
	}
	Date ordine1 = o1.getDataRichiesta();
	Date ordine2 = o2.getDataRichiesta();
	if (ordine1 == null && ordine2 == null) {
	    return 0;
	}
	if (ordine1 != null && ordine2 == null) {
	    return ordinamento(-1);
	}
	if (ordine1 == null && ordine2 != null) {
	    return ordinamento(1);
	}
	risultato = ordine1.compareTo(ordine2); // default ASC
	return ordinamento(risultato);
    }

    private int ordinamento(int risultato) {

	if (risultato != 0 && this.ordinamento.equals(OrderTypeEnum.DESC)) {
	    if (risultato < 0) {
		return 1;
	    } else {
		return -1;
	    }
	}
	return risultato;
    }

    @Override
    public int compare(PayRichieste o1, PayRichieste o2) {

	return compareConOrdinamento(o1, o2);
    }
    //    public static void main(String[] args) {
    //
    //	PayRichiesteDataRichiestaComparator r = new PayRichiesteDataRichiestaComparator();
    //	PayRichieste o1 = null;
    //	PayRichieste o2 = null;
    //	System.out.println(r.compare(o1, o2));
    //	o1 = new PayRichieste();
    //	o2 = new PayRichieste();
    //	System.out.println(r.compare(o1, o2));
    //	o1.setDataRichiesta(Utilities.getDate("02/01/2025", "dd/MM/yyyy"));
    //	System.out.println(r.compare(o1, o2));
    //	r.setOrdinamento(OrderTypeEnum.DESC);
    //	System.out.println(r.compare(o1, o2));
    //	o2.setDataRichiesta(Utilities.getDate("01/01/2025", "dd/MM/yyyy"));
    //	r.setOrdinamento(OrderTypeEnum.ASC);
    //	System.out.println(r.compare(o1, o2));
    //	r.setOrdinamento(OrderTypeEnum.DESC);
    //	System.out.println(r.compare(o1, o2));
    //	PayRichieste[] reqsArr = new PayRichieste[2];
    //	reqsArr[0] = o2;
    //	reqsArr[1] = o1;
    //	Arrays.sort(reqsArr, new PayRichiesteDataRichiestaComparator(OrderTypeEnum.DESC));
    //	for (PayRichieste payRichieste : reqsArr) {
    //	    System.out.println(payRichieste.getDataRichiesta());
    //	}
    //	Arrays.sort(reqsArr, new PayRichiesteDataRichiestaComparator(OrderTypeEnum.ASC));
    //	for (PayRichieste payRichieste : reqsArr) {
    //	    System.out.println(payRichieste.getDataRichiesta());
    //	}
    //    }
}
