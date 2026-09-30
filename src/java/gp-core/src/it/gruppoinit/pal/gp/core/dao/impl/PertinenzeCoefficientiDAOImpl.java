package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.PertinenzeCoefficientiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PertinenzeCoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class PertinenzeCoefficientiDAOImpl extends BaseDAOImpl<PertinenzeCoefficienti, PkId> implements PertinenzeCoefficientiDAO {

    @Override
    public Class<PertinenzeCoefficienti> getEntityClass() {

	return PertinenzeCoefficienti.class;
    }

    @Override
    public List<PertinenzeCoefficienti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "anno", DAOOrderTypeEnum.DESC);
    }
}
