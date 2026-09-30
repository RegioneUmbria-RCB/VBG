package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipisoggettopeopleDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Tipisoggettopeople;
import it.gruppoinit.pal.gp.core.domain.TipisoggettopeopleId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class TipisoggettopeopleDAOImpl extends BaseDAOImpl<Tipisoggettopeople, TipisoggettopeopleId> implements TipisoggettopeopleDAO {

    @Override
    public Class<Tipisoggettopeople> getEntityClass() {

	return Tipisoggettopeople.class;
    }

    @Override
    public List<Tipisoggettopeople> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "tiporapprpeople", DAOOrderTypeEnum.ASC);
    }
}
