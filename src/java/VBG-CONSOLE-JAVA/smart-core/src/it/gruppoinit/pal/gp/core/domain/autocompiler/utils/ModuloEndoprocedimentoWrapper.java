package it.gruppoinit.pal.gp.core.domain.autocompiler.utils;

import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;

import org.apache.commons.lang.StringUtils;

public class ModuloEndoprocedimentoWrapper implements Comparable<ModuloEndoprocedimentoWrapper> {

    private Allegati allegato;
    private Inventarioprocdyn2modellit schedaDinamica;
    private AlberoprocDocumenti documentoAlbero;

    public Allegati getAllegato() {

	return allegato;
    }

    public void setAllegato(Allegati allegato) {

	if (allegato != null) {
	    this.allegato = allegato;
	    this.schedaDinamica = null;
	    this.documentoAlbero = null;
	}
    }

    public Inventarioprocdyn2modellit getSchedaDinamica() {

	return schedaDinamica;
    }

    public void setSchedaDinamica(Inventarioprocdyn2modellit schedaDinamica) {

	if (schedaDinamica != null) {
	    this.schedaDinamica = schedaDinamica;
	    this.allegato = null;
	    this.documentoAlbero = null;
	}
    }

    public AlberoprocDocumenti getDocumentoAlbero() {

	return this.documentoAlbero;
    }

    public void setDocumentoAlbero(AlberoprocDocumenti alberoDoc) {

	if (alberoDoc != null) {
	    this.documentoAlbero = alberoDoc;
	    this.allegato = null;
	    this.schedaDinamica = null;
	}
    }

    public boolean isSchedaDinamica() {

	return this.schedaDinamica != null;
    }

    public boolean isAllegato() {

	return this.allegato != null;
    }

    public boolean isDocumentoAlbero() {

	return this.documentoAlbero != null;
    }

    public Integer getCodiceInventario() {

	if (isSchedaDinamica()) {
	    return this.schedaDinamica.getId().getCodiceinventario();
	}
	if (isAllegato()) {
	    return this.allegato.getInventarioprocedimento().getId().getCodice();
	}
	if (isDocumentoAlbero()) {
	    return this.documentoAlbero.getAlberoproc().getId().getCodice();
	}
	return null;
    }

    public String getIdcomune() {

	if (isSchedaDinamica()) {
	    return this.schedaDinamica.getId().getIdcomune();
	}
	if (isAllegato()) {
	    return this.allegato.getInventarioprocedimento().getId().getIdcomune();
	}
	if (isDocumentoAlbero()) {
	    return this.documentoAlbero.getAlberoproc().getId().getIdcomune();
	}
	return null;
    }

    public String getDescrizione() {

	if (isSchedaDinamica()) {
	    return StringUtils.defaultString(this.schedaDinamica.getDyn2Modellit().getDescrizione());
	}
	if (isAllegato()) {
	    return StringUtils.defaultString(this.allegato.getAllegato());
	}
	if (isDocumentoAlbero()) {
	    return StringUtils.defaultString(this.documentoAlbero.getDescrizione());
	}
	return null;
    }

    public int getOrdine() {

	int order = 0;
	if (isAllegato()) {
	    order = getAllegato().getOrdine() != null ? getAllegato().getOrdine() : 0;
	} else if (isDocumentoAlbero()) {
	    order = getDocumentoAlbero().getOrdine() != null ? getDocumentoAlbero().getOrdine() : 0;
	} else if (isSchedaDinamica()) {
	    order = getSchedaDinamica().getOrdine() != null ? getSchedaDinamica().getOrdine() : 0;
	}
	return order;
    }

    @Override
    public int compareTo(ModuloEndoprocedimentoWrapper o) {

	int myOrder = getOrdine();
	String myDesc = getDescrizione();
	int yourOrder = o.getOrdine();
	String yourDesc = o.getDescrizione();
	int retVal = myOrder - yourOrder;
	if (retVal == 0) {
	    retVal = myDesc.compareTo(yourDesc);
	}
	return retVal;
    }
}
