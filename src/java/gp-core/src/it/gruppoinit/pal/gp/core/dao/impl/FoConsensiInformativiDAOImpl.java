package it.gruppoinit.pal.gp.core.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.FoConsensiInformativiDAO;
import it.gruppoinit.pal.gp.core.domain.FoConsensiInformativi;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class FoConsensiInformativiDAOImpl extends BaseDAOImpl<FoConsensiInformativi, PkId> implements FoConsensiInformativiDAO {

    @Override
    public Class<FoConsensiInformativi> getEntityClass() {

	return FoConsensiInformativi.class;
    }
}