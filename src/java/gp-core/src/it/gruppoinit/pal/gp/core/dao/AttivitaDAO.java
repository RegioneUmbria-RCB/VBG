package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;

import java.util.List;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
public interface AttivitaDAO extends BaseDAO<Attivita, AttivitaId> {

    /**
     * Metodo che utilizza il metodo di Hibernate Template findByCriteria
     * 
     * Metodo per determinare le attività di un settore
     * 
     * @param entity
     *            : la proprietà codicesettore deve essere popolata per ottenere il risultato desiderato
     * @return List<Attivita>: lista delle attività di un settore
     */
    public List<Attivita> findAttivitaBySettore(Attivita entity);

    /**
     * Restituisce la lista di attività filtrate per i campi: settore, flagDisabilitato e istat, se il campo istat è
     * presente viene usato il seguente criterio di filtraggio:<br />
     * <code>Restrictions.or(Restrictions.ilike("id.codiceistat", entity.getId().getCodiceistat(), MatchMode.ANYWHERE),Restrictions.ilike("istat", entity.getIstat(), MatchMode.ANYWHERE))</code>
     * <br />
     * La lista è ordinata per il campo istat ASC
     * 
     * @param entity
     * @return
     */
    public List<Attivita> findByFilter(Attivita entity);

    /**
     * Restituisce le attivita (filtrando per idcomune e software) ordinandole per il campo istat
     */
    public List<Attivita> findAll(Integer firstResult, Integer maxResult);
}
