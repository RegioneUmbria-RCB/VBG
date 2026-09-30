package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class MovimentiallegatiDataregistrazioneDescrizioneComparator implements Comparator<Movimentiallegati>, Serializable {

    private static final long serialVersionUID = 4171412049921515882L;

    public MovimentiallegatiDataregistrazioneDescrizioneComparator() {

    }

    @Override
    public int compare(Movimentiallegati o1, Movimentiallegati o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	Date data1 = o1.getDataregistrazione();
	Date data2 = o2.getDataregistrazione();
	String descrizione1 = o1.getDescrizione();
	String descrizione2 = o2.getDescrizione();
	int condizioneData = data1.compareTo(data2);
	if (condizioneData != 0) {
	    return data1.compareTo(data2);
	}
	return descrizione1.compareTo(descrizione2);
    }
}
