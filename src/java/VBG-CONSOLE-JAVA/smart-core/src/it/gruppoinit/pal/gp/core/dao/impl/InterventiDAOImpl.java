package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.InterventiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Interventi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class InterventiDAOImpl extends BaseDAOImpl<Interventi, PkId> implements InterventiDAO {

    @Override
    public Class<Interventi> getEntityClass() {

	return Interventi.class;
    }

    @Override
    public List<Interventi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "intervento", DAOOrderTypeEnum.ASC);
    }
}
