package it.gruppoinit.stc.dao;

import it.gruppoinit.stc.dao.impl.PraticheDAOImpl;
import it.gruppoinit.stc.domain.Pratiche;

public interface PraticheDAO extends BaseDAO<Pratiche, Integer> {

    /**
     * @see PraticheDAOImpl#findByUniqueKey(Pratiche)
     * @param example
     * @return
     */
    public Pratiche findByUniqueKey(Pratiche example);

}
