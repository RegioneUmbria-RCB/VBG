/**
 * 
 */
package it.gruppoinit.pal.gp.pay.command;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;

/**
 * @author francol
 *
 */
public class RichiestaSuListaPosizioniCommand extends PayBaseCommand {

    public RichiestaSuListaPosizioniCommand(String idRichiesta) {

	this();
	this.idRichiesta = idRichiesta;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 8154573460292336341L;
    private List<PayPosizioniDebitorie> posizioni;

    public RichiestaSuListaPosizioniCommand() {

	this.posizioni = new ArrayList<PayPosizioniDebitorie>();
    }

    public List<PayPosizioniDebitorie> getPosizioni() {

	return posizioni;
    }
}
