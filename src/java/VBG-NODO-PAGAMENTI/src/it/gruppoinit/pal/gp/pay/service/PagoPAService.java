/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;

/**
 * @author francol
 *
 */
public interface PagoPAService {

    public String generaQRCode(PayPosizioniDebitorie posDeb);
}
