package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;

import java.io.Serializable;
import java.util.Comparator;

public class RegistrazioniFilterMercatoUsoPosteggioComparator implements Comparator<RegistrazioniFilter>, Serializable {

    private static final long serialVersionUID = 9007893376923608085L;

    @Override
    public int compare(RegistrazioniFilter o1, RegistrazioniFilter o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String mercato1 = o1.getMercati() == null ? "" : o1.getMercati().getDescrizione();
	String uso1 = o1.getMercatiUso() == null ? "" : o1.getMercatiUso().getDescrizione();
	String posteggio1 = o1.getPosteggio() == null ? "" : o1.getPosteggio().getCodiceposteggio();
	String mercato2 = o2.getMercati() == null ? "" : o2.getMercati().getDescrizione();
	String uso2 = o2.getMercatiUso() == null ? "" : o2.getMercatiUso().getDescrizione();
	String posteggio2 = o2.getPosteggio() == null ? "" : o2.getPosteggio().getCodiceposteggio();
	int condizioneMercato = mercato1.compareTo(mercato2);
	if (condizioneMercato != 0) {
	    return mercato1.compareTo(mercato2);
	}
	int condizioneUso = uso1.compareTo(uso2);
	if (condizioneUso != 0) {
	    return condizioneUso;
	}
	return posteggio1.compareTo(posteggio2);
    }
}
