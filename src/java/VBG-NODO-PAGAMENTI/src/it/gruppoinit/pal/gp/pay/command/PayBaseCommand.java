/**
 * 
 */
package it.gruppoinit.pal.gp.pay.command;

import java.io.Serializable;

/**
 * @author francol
 *
 */
public class PayBaseCommand implements Serializable {

    private static final long serialVersionUID = -6465768602291080256L;
    protected String idRichiesta;

    public String getIdRichiesta() {

	return idRichiesta;
    }
}
