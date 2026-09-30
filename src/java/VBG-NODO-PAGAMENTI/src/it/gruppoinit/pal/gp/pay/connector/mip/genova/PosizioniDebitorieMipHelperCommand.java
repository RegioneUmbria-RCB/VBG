package it.gruppoinit.pal.gp.pay.connector.mip.genova;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.pay.command.PayBaseCommand;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;

public class PosizioniDebitorieMipHelperCommand extends PayBaseCommand {

    public PosizioniDebitorieMipHelperCommand(String idRichiesta) {

	this.idRichiesta = idRichiesta;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 6683730326197338990L;
    List<PayRegistrazioniContabili> registrazioniPosizioni = new ArrayList<PayRegistrazioniContabili>();

    public List<PayRegistrazioniContabili> getRegistrazioniPosizioni() {

	return registrazioniPosizioni;
    }
}
