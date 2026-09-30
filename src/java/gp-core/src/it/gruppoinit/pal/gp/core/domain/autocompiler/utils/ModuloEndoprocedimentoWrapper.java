package it.gruppoinit.pal.gp.core.domain.autocompiler.utils;

import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;

public class ModuloEndoprocedimentoWrapper {

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
    
    public AlberoprocDocumenti getDocumentoAlbero(){
	return this.documentoAlbero;
    }
    
    public void setDocumentoAlbero(AlberoprocDocumenti alberoDoc){
	if(alberoDoc != null){
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
    
    public boolean isDocumentoAlbero(){
	return this.documentoAlbero != null;
    }

    public Integer getCodiceInventario() {

	if (isSchedaDinamica()) {
	    return this.schedaDinamica.getId().getCodiceinventario();
	}
	if (isAllegato()) {
	    return this.allegato.getInventarioprocedimento().getId().getCodice();
	}
	return null;
    }

    public String getDescrizione() {

	if (isSchedaDinamica()) {
	    return this.schedaDinamica.getDyn2Modellit().getDescrizione();
	}
	if (isAllegato()) {
	    return this.allegato.getAllegato();
	}
	if(isDocumentoAlbero()){
	    return this.documentoAlbero.getDescrizione();
	}
	return null;
    }
}
