/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.SettoriId;

import java.util.List;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
public interface SettoriDAO extends BaseDAO<Settori, SettoriId> {

    /**
     * Restituisce i settori (filtrando per idcomune e software) ordinandole per il campo settori
     */
    public List<Settori> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce una lista di settori filtrando per: idcomune, software, settore, id.codicesettore,
     * flagContamqattivita, flagInsmultiplo, foRichiesto, flagDisabilitato, tipiunitamisura.umDescrbreve,
     * tipiunitamisura.id.codice. Se il campo settore è presente il filtraggio viene eseguito secondo il seguente
     * criterio: <br />
     * <code>Restrictions.or(Restrictions.ilike("id.codicesettore", entity.getId().getCodicesettore(),
     * MatchMode.ANYWHERE), Restrictions.ilike("settore", entity.getSettore(), MatchMode.ANYWHERE))</code><br />
     * La lista è ordinata per il campo settore
     * 
     * @param entity
     * @return
     */
    public List<Settori> findByFilter(Settori entity);
}
