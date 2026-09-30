package it.gruppoinit.infocamere.schema.legaldocs;

import it.gruppoinit.infocamere.schema.legaldocs.pindex.PIndex;

public class ConservazioneResponseHelper {

    private PIndex index;
    private Error error;
    private boolean conservazioneSospesa;

    public PIndex getIndex() {

	return index;
    }

    public void setIndex(PIndex index) {

	this.index = index;
    }

    public Error getError() {

	return error;
    }

    public void setError(Error error) {

	this.error = error;
    }

    public boolean isConservazioneSospesa() {

	return conservazioneSospesa;
    }

    public void setConservazioneSospesa(boolean conservazioneSospesa) {

	this.conservazioneSospesa = conservazioneSospesa;
    }
}
