package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Sorteggidettaglio;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class SorteggidettaglioComparator implements Comparator<Sorteggidettaglio>, Serializable {

    private static final long serialVersionUID = 3168640720152712778L;

    public SorteggidettaglioComparator() {

    }

    @Override
    public int compare(Sorteggidettaglio o1, Sorteggidettaglio o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	Date data1 = o1.getIstanza().getData();
	Date data2 = o2.getIstanza().getData();
	String numeroistanza1 = o1.getIstanza().getNumeroistanza() == null ? "" : o1.getIstanza().getNumeroistanza();
	String numeroistanza2 = o2.getIstanza().getNumeroistanza() == null ? "" : o2.getIstanza().getNumeroistanza();
	int condizioneData = data2.compareTo(data1);
	if (condizioneData != 0) {
	    return data2.compareTo(data1);
	}
	numeroistanza2 = StringUtils.leftPad(numeroistanza2, 35, 'Z');
	numeroistanza1 = StringUtils.leftPad(numeroistanza1, 35, 'Z');
	return numeroistanza2.compareTo(numeroistanza1);
    }
}
