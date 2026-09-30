package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ConsensiInformativiDAO;
import it.gruppoinit.pal.gp.core.domain.ConsensiInformativi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

@Repository
public class ConsensiInformativiDAOImpl extends BaseDAOImpl<ConsensiInformativi, PkId> implements ConsensiInformativiDAO {

    @Override
    public Class<ConsensiInformativi> getEntityClass() {

	return ConsensiInformativi.class;
    }
}
