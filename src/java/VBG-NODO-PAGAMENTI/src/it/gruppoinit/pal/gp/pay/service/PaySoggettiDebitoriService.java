/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;


/**
 * @author francol
 *
 */
public interface PaySoggettiDebitoriService extends BaseService<PaySoggettiDebitori, PkId> {
    
    public PaySoggettiDebitori registraSoggettoDebitore(SoggettoDebitoreType sogDeb) throws PayException;
    
    public PaySoggettiDebitori registraSoggettoDebitore(PaySoggettiDebitori sogDeb) throws PayException;
    
    public List<PaySoggettiDebitori> findByCodiceFiscale(String cfpi, Boolean attivo);
}
