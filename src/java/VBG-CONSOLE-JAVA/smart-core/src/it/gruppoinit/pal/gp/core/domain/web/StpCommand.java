/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author francescop
 * 
 */
public class StpCommand {

    private String nomeAttivita;
    private String tipologiaEndoprocedimento;
    private InvioSchedaEndoTipo2 invioSchedaEndoTipo2;
    private InvioSchedaEndoTipo1 invioSchedaEndoTipo1;
    private Inventarioprocedimenti inventarioprocedimenti;
    private List<ChiaveValoreBean<Inventarioprocedimenti, Boolean>> inventarioprocedimentisPrima = new ArrayList<ChiaveValoreBean<Inventarioprocedimenti, Boolean>>();
    private List<ChiaveValoreBean<Inventarioprocedimenti, Boolean>> inventarioprocedimentisDopo = new ArrayList<ChiaveValoreBean<Inventarioprocedimenti, Boolean>>();
    private String dataInizioValidita;
    private String dataFineValidita;

    public Inventarioprocedimenti getInventarioprocedimenti() {

	return inventarioprocedimenti;
    }

    public void setInvioSchedaEndoTipo1(InvioSchedaEndoTipo1 invioSchedaEndoTipo1) {

	this.invioSchedaEndoTipo1 = invioSchedaEndoTipo1;
    }

    public InvioSchedaEndoTipo1 getInvioSchedaEndoTipo1() {

	return invioSchedaEndoTipo1;
    }

    public void setInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti) {

	this.inventarioprocedimenti = inventarioprocedimenti;
    }

    public List<ChiaveValoreBean<Inventarioprocedimenti, Boolean>> getInventarioprocedimentisPrima() {

	return inventarioprocedimentisPrima;
    }

    public void setInventarioprocedimentisPrima(List<ChiaveValoreBean<Inventarioprocedimenti, Boolean>> inventarioprocedimentisPrima) {

	this.inventarioprocedimentisPrima = inventarioprocedimentisPrima;
    }

    public String getDataInizioValidita() {

	return dataInizioValidita;
    }

    public void setDataInizioValidita(String dataInizioValidita) {

	this.dataInizioValidita = dataInizioValidita;
    }

    public String getDataFineValidita() {

	return dataFineValidita;
    }

    public void setDataFineValidita(String dataFineValidita) {

	this.dataFineValidita = dataFineValidita;
    }

    public List<ChiaveValoreBean<Inventarioprocedimenti, Boolean>> getInventarioprocedimentisDopo() {

	return inventarioprocedimentisDopo;
    }

    public void setInventarioprocedimentisDopo(List<ChiaveValoreBean<Inventarioprocedimenti, Boolean>> inventarioprocedimentisDopo) {

	this.inventarioprocedimentisDopo = inventarioprocedimentisDopo;
    }

    public InvioSchedaEndoTipo2 getInvioSchedaEndoTipo2() {

	return invioSchedaEndoTipo2;
    }

    public void setInvioSchedaEndoTipo2(InvioSchedaEndoTipo2 invioSchedaEndoTipo2) {

	this.invioSchedaEndoTipo2 = invioSchedaEndoTipo2;
    }

    public String getNomeAttivita() {

	return nomeAttivita;
    }

    public void setNomeAttivita(String nomeAttivita) {

	this.nomeAttivita = nomeAttivita;
    }

    public String getTipologiaEndoprocedimento() {

	return tipologiaEndoprocedimento;
    }

    public void setTipologiaEndoprocedimento(String tipologiaEndoprocedimento) {

	this.tipologiaEndoprocedimento = tipologiaEndoprocedimento;
    }
}
