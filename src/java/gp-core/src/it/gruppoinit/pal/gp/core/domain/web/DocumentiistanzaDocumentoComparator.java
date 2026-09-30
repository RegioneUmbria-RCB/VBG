package it.gruppoinit.pal.gp.core.domain.web;

import java.io.Serializable;
import java.util.Comparator;

public class DocumentiistanzaDocumentoComparator implements Comparator<DocumentiistanzaValoreBean>, Serializable {

    private static final long serialVersionUID = -4086669621539629996L;

    @Override
    public int compare(DocumentiistanzaValoreBean o1, DocumentiistanzaValoreBean o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	String allegatoextra1 = o1.getChiave().getDocumento();
	String allegatoextra2 = o2.getChiave().getDocumento();
	if (allegatoextra1 == null && allegatoextra2 == null) {
	    return 0;
	}
	if (allegatoextra1 != null && allegatoextra2 == null) {
	    return -1;
	}
	if (allegatoextra1 == null && allegatoextra2 != null) {
	    return 1;
	}
	return allegatoextra1.compareTo(allegatoextra2);
    }
}
