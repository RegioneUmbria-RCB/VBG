package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CcDestinazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.CcDestinazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CcDestinazioniDAOImpl extends BaseDAOImpl<CcDestinazioni, PkId> implements CcDestinazioniDAO {

    @Override
    public Class<CcDestinazioni> getEntityClass() {

	return CcDestinazioni.class;
    }

    @Override
    public List<CcDestinazioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "destinazione", DAOOrderTypeEnum.ASC);
    }
}
