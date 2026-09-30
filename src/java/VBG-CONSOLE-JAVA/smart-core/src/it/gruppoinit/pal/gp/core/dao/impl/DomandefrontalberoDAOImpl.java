package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.DomandefrontalberoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Domandefrontalbero;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class DomandefrontalberoDAOImpl extends BaseDAOImpl<Domandefrontalbero, PkId> implements DomandefrontalberoDAO {

    @Override
    public Class<Domandefrontalbero> getEntityClass() {

	return Domandefrontalbero.class;
    }

    @Override
    public List<Domandefrontalbero> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
