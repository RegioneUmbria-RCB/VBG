package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzefidejussioniDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzefidejussioni;
import it.gruppoinit.pal.gp.core.domain.IstanzefidejussioniId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzefidejussioniService extends BaseService<Istanzefidejussioni, IstanzefidejussioniId> {

    /**
     * @see IstanzefidejussioniDAO#findAll(Integer, Integer)
     */
    public List<Istanzefidejussioni> findAll(Integer firstResult, Integer maxResult);

    /**
     * Recupera la lista di Istanzefidejussioni filtrado per codice istanza
     */
    public List<Istanzefidejussioni> findByIstanze(Integer codiceIstanza);

    public boolean existRecordByIstanza(Integer codiceIstanz);
}
