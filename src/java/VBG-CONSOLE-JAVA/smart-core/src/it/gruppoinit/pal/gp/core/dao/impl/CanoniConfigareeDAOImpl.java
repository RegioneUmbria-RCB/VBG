package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CanoniConfigareeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CanoniConfigaree;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CanoniConfigareeDAOImpl extends BaseDAOImpl<CanoniConfigaree, PkId> implements CanoniConfigareeDAO {

    @Override
    public Class<CanoniConfigaree> getEntityClass() {

	return CanoniConfigaree.class;
    }

    @Override
    public List<CanoniConfigaree> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "anno", DAOOrderTypeEnum.ASC);
    }
}
