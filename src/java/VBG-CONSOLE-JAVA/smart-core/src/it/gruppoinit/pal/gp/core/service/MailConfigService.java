package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.MailConfigId;

/**
 * 
 * @author francescop
 */
public interface MailConfigService extends BaseService<MailConfig, MailConfigId> {

    /**
     * metodo per il recupero della configurazione per l'invio delle mail il metodo ricerca la configurazione per il
     * software corrente e se non la trova la ricerca per il software TT
     * 
     * @return MailConfig o null
     */
    public MailConfig findMailConfig();
}
