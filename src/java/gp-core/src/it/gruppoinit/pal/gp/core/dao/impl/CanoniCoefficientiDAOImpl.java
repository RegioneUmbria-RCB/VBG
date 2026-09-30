package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CanoniCoefficientiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CanoniCoefficienti;
import it.gruppoinit.pal.gp.core.domain.CanoniCoefficientiId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CanoniCoefficientiDAOImpl extends BaseDAOImpl<CanoniCoefficienti, CanoniCoefficientiId> implements CanoniCoefficientiDAO {

    @Override
    public Class<CanoniCoefficienti> getEntityClass() {

	return CanoniCoefficienti.class;
    }

    @Override
    public List<CanoniCoefficienti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "id.anno", DAOOrderTypeEnum.ASC);
    }
}
