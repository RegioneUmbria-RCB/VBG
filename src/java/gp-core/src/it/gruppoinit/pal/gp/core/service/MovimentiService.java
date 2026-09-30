package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.MovimentoRestBean;

public interface MovimentiService extends MovimentiBaseService {

    /**
     * Metodo di inserimento utilizzato dalle chiamate rest-private
     * 
     * @param bean
     * @return
     */
    MovimentoRestBean insertRest(MovimentoRestBean bean);
}
