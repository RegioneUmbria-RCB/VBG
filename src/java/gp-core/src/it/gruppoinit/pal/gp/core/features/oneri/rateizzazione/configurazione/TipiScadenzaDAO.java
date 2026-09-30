/**
 * 
 */
package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;

/**
 * @author francescop
 * 
 */
public interface TipiScadenzaDAO extends BaseDAO<TipiScadenza, Integer> {

    public List<TipoScadenzaBean> findTipiScadenzaConsentiti();
}
