package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FidejussionestatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Fidejussionestati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class FidejussionestatiDAOImpl extends BaseDAOImpl<Fidejussionestati, PkId> implements FidejussionestatiDAO {

    @Override
    public Class<Fidejussionestati> getEntityClass() {

	return Fidejussionestati.class;
    }

    @Override
    public List<Fidejussionestati> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
