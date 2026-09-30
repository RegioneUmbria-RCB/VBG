package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.PeopleprocsportelliDAO;
import it.gruppoinit.pal.gp.core.domain.Peopleprocsportelli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.PeopleprocsportelliHelper;

/**
 * 
 * @author
 */
public interface PeopleprocsportelliService extends BaseService<Peopleprocsportelli, PkId> {

    /**
     * @see PeopleprocsportelliDAO#findAll(Integer, Integer)
     */
    public List<Peopleprocsportelli> findAll(Integer firstResult, Integer maxResult);

    /**
     * Metodo che permette di inserire un record nella tabella PEOPLEPROCSPORTELLI
     * 
     * @param helper
     */
    public void creaRecord(PeopleprocsportelliHelper helper);
}
