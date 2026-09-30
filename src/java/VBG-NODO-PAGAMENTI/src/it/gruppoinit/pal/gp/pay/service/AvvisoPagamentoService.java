/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import java.util.List;

import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.exception.PayException;

/**
 * @author francol
 *
 */
public interface AvvisoPagamentoService {
    
    public void inviaAvvisoPagamento(PaySoggettiDebitori sendTo, List<PayPosizioniDebitorie> daPagare) throws PayException;
    
    public void annullaAvvisoPagamento(PaySoggettiDebitori sentTo, List<PayPosizioniDebitorie> daPagare) throws PayException;
}
