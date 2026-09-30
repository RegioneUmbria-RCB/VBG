package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDocumenticatDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenticat;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class AlberoprocDocumenticatDAOImpl extends BaseDAOImpl<AlberoprocDocumenticat, PkId> implements AlberoprocDocumenticatDAO {

    @Override
    public Class<AlberoprocDocumenticat> getEntityClass() {

	return AlberoprocDocumenticat.class;
    }

    @Override
    public List<AlberoprocDocumenticat> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
