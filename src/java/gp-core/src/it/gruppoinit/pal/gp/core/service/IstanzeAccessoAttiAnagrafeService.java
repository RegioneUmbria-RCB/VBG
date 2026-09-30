package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzeAccessoAttiAnagrafeDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafe;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafeId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeAccessoAttiAnagrafeService extends BaseService<IstanzeAccessoAttiAnagrafe, IstanzeAccessoAttiAnagrafeId> {

    /**
     * @see IstanzeAccessoAttiAnagrafeDAO#findAll(Integer, Integer)
     */
    public List<IstanzeAccessoAttiAnagrafe> findAll(Integer firstResult, Integer maxResult);

    public List<IstanzeAccessoAttiAnagrafe> findByIstanzeAccessoAttiAnagrafeId(IstanzeAccessoAttiAnagrafeId id, Integer firstResult, Integer maxResult);

    public List<IstanzeAccessoAttiAnagrafe> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult);

    public List<IstanzeAccessoAttiAnagrafe> findByIstanzeAccessoAttiT(Integer codice, Integer firstResult, Integer maxResult);

    public List<IstanzeAccessoAttiAnagrafe> findByAnagrafeAndAccessoAttiT(Integer codiceAnagrafe, Integer codiceIstanzaAccessoAttiT);
}
