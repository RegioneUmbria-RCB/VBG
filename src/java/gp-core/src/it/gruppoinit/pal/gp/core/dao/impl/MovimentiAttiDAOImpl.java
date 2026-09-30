package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MovimentiAttiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.MovimentiAtti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class MovimentiAttiDAOImpl extends BaseDAOImpl<MovimentiAtti, PkId> implements MovimentiAttiDAO {

    @Override
    public Class<MovimentiAtti> getEntityClass() {

	return MovimentiAtti.class;
    }

    @Override
    public List<MovimentiAtti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "dataRichiestaAtto", DAOOrderTypeEnum.ASC);
    }
}
