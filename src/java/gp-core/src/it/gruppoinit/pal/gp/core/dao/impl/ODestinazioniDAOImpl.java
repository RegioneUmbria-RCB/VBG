package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ODestinazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.ODestinazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ODestinazioniDAOImpl extends BaseDAOImpl<ODestinazioni, PkId> implements ODestinazioniDAO {

    @Override
    public Class<ODestinazioni> getEntityClass() {

	return ODestinazioni.class;
    }

    @Override
    public List<ODestinazioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "destinazione", DAOOrderTypeEnum.ASC);
    }
}
