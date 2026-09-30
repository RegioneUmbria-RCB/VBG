package it.gruppoinit.stc.dao.impl;

import it.gruppoinit.stc.dao.MessaggipraticheDAO;
import it.gruppoinit.stc.domain.Messaggipratiche;

import org.springframework.stereotype.Repository;

@Repository
public class MessaggipraticheDAOImpl extends BaseDAOImpl<Messaggipratiche, Integer> implements MessaggipraticheDAO {

    @Override
    public Class<Messaggipratiche> getEntityClass() {
	return Messaggipratiche.class;
    }

}
