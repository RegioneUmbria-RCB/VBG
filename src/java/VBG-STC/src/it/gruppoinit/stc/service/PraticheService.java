package it.gruppoinit.stc.service;

import it.gruppoinit.stc.dao.impl.PraticheDAOImpl;
import it.gruppoinit.stc.domain.Pratiche;

public interface PraticheService extends BaseService<Pratiche, Integer> {

    /**
     * @see PraticheDAOImpl#findByUniqueKey(Pratiche)
     * @param example
     * @return
     */
    public Pratiche findByUniqueKey(Pratiche example);

}
