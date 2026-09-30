package it.gruppoinit.pal.gp.core.features.alberoproc.coefficienti.dao;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AlberoCoefficientiR;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class AlberoCoefficientiRDAOImpl extends BaseDAOImpl<AlberoCoefficientiR, PkId> implements IAlberoCoefficientiRDAO {

    @Override
    public Class<AlberoCoefficientiR> getEntityClass() {

	return AlberoCoefficientiR.class;
    }
}
