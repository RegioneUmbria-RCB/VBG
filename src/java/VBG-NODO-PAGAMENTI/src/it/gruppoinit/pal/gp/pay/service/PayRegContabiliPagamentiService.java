/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.pay.domain.PayRegContabiliPagamenti;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;


/**
 * @author francol
 *
 */
public interface PayRegContabiliPagamentiService extends BaseService<PayRegContabiliPagamenti, PkId> {
    
    
    public PayRegContabiliPagamenti creaRegistrazioneContabile(RegistrazioneContabileType regCont);

}
