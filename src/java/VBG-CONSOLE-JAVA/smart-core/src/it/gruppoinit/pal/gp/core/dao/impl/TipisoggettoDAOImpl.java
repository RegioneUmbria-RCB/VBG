package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipisoggettoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class TipisoggettoDAOImpl extends BaseDAOImpl<Tipisoggetto, PkId> implements TipisoggettoDAO {

    @Override
    public Class<Tipisoggetto> getEntityClass() {

	return Tipisoggetto.class;
    }

    @Override
    public List<Tipisoggetto> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "tiposoggetto", DAOOrderTypeEnum.ASC);
    }
}
