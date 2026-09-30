package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzereplicateDAO;
import it.gruppoinit.pal.gp.core.domain.Istanzereplicate;
import it.gruppoinit.pal.gp.core.domain.IstanzereplicateId;

import org.springframework.stereotype.Repository;

@Repository
public class IstanzereplicateDAOImpl extends BaseDAOImpl<Istanzereplicate, IstanzereplicateId> implements IstanzereplicateDAO {

    @Override
    public Class<Istanzereplicate> getEntityClass() {

	return Istanzereplicate.class;
    }
}
