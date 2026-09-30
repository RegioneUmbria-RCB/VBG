package it.gruppoinit.pal.gp.core.features.alberoproc.coefficienti.dao;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AlberoCoefficientiT;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class AlberoCoefficientiTDAOImpl extends BaseDAOImpl<AlberoCoefficientiT, PkId> implements IAlberoCoefficientiTDAO {

    @Override
    public Class<AlberoCoefficientiT> getEntityClass() {

	return AlberoCoefficientiT.class;
    }
}
