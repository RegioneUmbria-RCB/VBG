package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.VwPosteggiconcessioniDAO;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessioni;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessioniId;

import java.util.List;

public interface VwPosteggiconcessioniService extends BaseService<VwPosteggiconcessioni, VwPosteggiconcessioniId> {

    /**
     * vedi doc del DAO
     * 
     * @see VwPosteggiconcessioniDAO#findPosteggiMercatoUso(Integer, Integer)
     * @param codiceMercato
     * @param idUso
     * @return
     */
    public List<VwPosteggiconcessioni> findPosteggiMercatoUso(Integer codiceMercato, Integer idUso, Integer firstResult, Integer maxResults);

    public List<VwPosteggiconcessioni> findByMercatoUsoAndPosteggio(Integer codiceMercato, Integer idUso, Integer idPosteggio, Integer firstResult,
	    Integer maxResults);

    public int countPosteggiMercatoUso(Integer codiceMercato, Integer idUso);
}
