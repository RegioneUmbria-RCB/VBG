package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Movimenti;

public class IstanzeprocedimentiHelper {

    private Istanzeprocedimenti istanzeprocedimenti;
    private Movimenti movimentoTrasmissione;
    private Movimenti movimentoRitorno;

    public IstanzeprocedimentiHelper() {

	this.istanzeprocedimenti = new Istanzeprocedimenti();
	this.movimentoTrasmissione = new Movimenti();
	this.movimentoRitorno = new Movimenti();
    }

    public Istanzeprocedimenti getIstanzeprocedimenti() {

	return istanzeprocedimenti;
    }

    public void setIstanzeprocedimenti(Istanzeprocedimenti istanzeprocedimenti) {

	this.istanzeprocedimenti = istanzeprocedimenti;
    }

    public Movimenti getMovimentoTrasmissione() {

	return movimentoTrasmissione;
    }

    public void setMovimentoTrasmissione(Movimenti movimentoTrasmissione) {

	this.movimentoTrasmissione = movimentoTrasmissione;
    }

    public Movimenti getMovimentoRitorno() {

	return movimentoRitorno;
    }

    public void setMovimentoRitorno(Movimenti movimentoRitorno) {

	this.movimentoRitorno = movimentoRitorno;
    }
}
