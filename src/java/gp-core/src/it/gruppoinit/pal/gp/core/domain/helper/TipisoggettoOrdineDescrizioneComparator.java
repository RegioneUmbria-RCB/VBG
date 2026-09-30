package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

public class TipisoggettoOrdineDescrizioneComparator implements Comparator<Tipisoggetto>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8135918594369145751L;

    @Override
    public int compare(Tipisoggetto o1, Tipisoggetto o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	Integer ordine1 = o1.getOrdine() == null ? 0 : o1.getOrdine().intValue();
	Integer ordine2 = o2.getOrdine() == null ? 0 : o2.getOrdine().intValue();
	String descrizione1 = StringUtils.defaultString(o1.getTiposoggetto());
	String descrizione2 = StringUtils.defaultString(o2.getTiposoggetto());
	int condizione = ordine1.compareTo(ordine2);
	if (condizione != 0) {
	    return condizione;
	}
	return descrizione1.compareTo(descrizione2);
    }
}
