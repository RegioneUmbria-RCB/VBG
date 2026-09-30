package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocTipisogBackDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisogBack;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AlberoprocTipisogBackDAOImpl extends BaseDAOImpl<AlberoprocTipisogBack, PkId> implements AlberoprocTipisogBackDAO {

    @Override
    public Class<AlberoprocTipisogBack> getEntityClass() {

	return AlberoprocTipisogBack.class;
    }

    @Override
    public List<AlberoprocTipisogBack> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "id.codice", DAOOrderTypeEnum.ASC);
    }
}
