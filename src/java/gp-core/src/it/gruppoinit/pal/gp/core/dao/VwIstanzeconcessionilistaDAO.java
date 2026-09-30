package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwIstanzeconcessionilista;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface VwIstanzeconcessionilistaDAO extends BaseDAO<VwIstanzeconcessionilista, PkId> {

    /**
     * Restituisce la vista Istanze concessioni Lista (filtrando per idcomune e software)
     */
    public List<VwIstanzeconcessionilista> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce la vista Istanze concessioni Lista (filtrando per idcomune, software, posteggi, uso)
     */
    public List<VwIstanzeconcessionilista> findByPosteggioAndUso(MercatiD mercatid, MercatiUso mercatiUso);
}
