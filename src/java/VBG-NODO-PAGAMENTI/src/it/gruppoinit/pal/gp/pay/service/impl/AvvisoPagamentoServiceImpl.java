/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.AvvisoPagamentoService;


/**
 * @author francol
 *
 */
@Service
public class AvvisoPagamentoServiceImpl implements AvvisoPagamentoService {

    @Override
    public void inviaAvvisoPagamento(PaySoggettiDebitori sendTo, List<PayPosizioniDebitorie> daPagare) throws PayException {

	throw new PayException("funzionalità non disponibile");
    }

    @Override
    public void annullaAvvisoPagamento(PaySoggettiDebitori sentTo, List<PayPosizioniDebitorie> daPagare) throws PayException {

	throw new PayException("funzionalità non disponibile");
    }
}
