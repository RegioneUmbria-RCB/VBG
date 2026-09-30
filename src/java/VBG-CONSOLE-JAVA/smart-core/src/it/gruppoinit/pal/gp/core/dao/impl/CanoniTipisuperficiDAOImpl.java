package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CanoniTipisuperficiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CanoniTipisuperfici;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CanoniTipisuperficiDAOImpl extends BaseDAOImpl<CanoniTipisuperfici, PkId> implements CanoniTipisuperficiDAO {

    @Override
    public Class<CanoniTipisuperfici> getEntityClass() {

	return CanoniTipisuperfici.class;
    }

    @Override
    public List<CanoniTipisuperfici> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "tiposuperficie", DAOOrderTypeEnum.ASC);
    }
}
