package it.gruppoinit.pal.gp.pay.connector.mip.service;

import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.AttualizzaAvvisoDati;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.NotificaStatoPagamentoDati;

public interface MIPBackendService {

    /**
     * 
     * @param request
     */
    boolean gestisciNotificaStatoPagamento(NotificaStatoPagamentoDati request);

    /**
     * 
     * @param request
     */
    void gestisciattualizzaAvviso(AttualizzaAvvisoDati request);
}
