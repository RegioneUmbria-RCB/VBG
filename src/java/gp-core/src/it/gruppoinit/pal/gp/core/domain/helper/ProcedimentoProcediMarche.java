package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.procedimarche.TipoProcedimentoGenerale;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.procedimarche.TipoProcedimentoSpecifico;

public class ProcedimentoProcediMarche {

    private TipoProcedimentoGenerale datiProcedimento;
    private StpEndoTipo2 datiCollegamento;
    private TipoProcedimentoSpecifico datiSpecifici;

    public TipoProcedimentoGenerale getDatiProcedimento() {

	return datiProcedimento;
    }

    public void setDatiProcedimento(TipoProcedimentoGenerale datiProcedimento) {

	this.datiProcedimento = datiProcedimento;
    }

    public StpEndoTipo2 getDatiCollegamento() {

	return datiCollegamento;
    }

    public void setDatiCollegamento(StpEndoTipo2 datiCollegamento) {

	this.datiCollegamento = datiCollegamento;
    }

    public TipoProcedimentoSpecifico getDatiSpecifici() {

	return datiSpecifici;
    }

    public void setDatiSpecifici(TipoProcedimentoSpecifico datiSpecifici) {

	this.datiSpecifici = datiSpecifici;
    }
}
