package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OccBasedestinazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.OccBasedestinazioni;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class OccBasedestinazioniDAOImpl extends BaseDAOImpl<OccBasedestinazioni, String> implements OccBasedestinazioniDAO {

    @Override
    public Class<OccBasedestinazioni> getEntityClass() {

	return OccBasedestinazioni.class;
    }

    @Override
    public List<OccBasedestinazioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "destinazione", DAOOrderTypeEnum.ASC);
    }
}
