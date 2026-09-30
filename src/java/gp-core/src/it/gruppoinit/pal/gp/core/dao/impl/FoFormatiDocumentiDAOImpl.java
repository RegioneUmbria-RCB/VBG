package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoFormatiDocumentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.FoFormatiDocumenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class FoFormatiDocumentiDAOImpl extends BaseDAOImpl<FoFormatiDocumenti, PkId> implements FoFormatiDocumentiDAO {

    @Override
    public Class<FoFormatiDocumenti> getEntityClass() {

	return FoFormatiDocumenti.class;
    }

    @Override
    public List<FoFormatiDocumenti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "formato", DAOOrderTypeEnum.ASC);
    }
}
