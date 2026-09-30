package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.RiTipiinterventoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.RiTipiintervento;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class RiTipiinterventoDAOImpl extends BaseDAOImpl<RiTipiintervento, String> implements RiTipiinterventoDAO {

    @Override
    public Class<RiTipiintervento> getEntityClass() {

	return RiTipiintervento.class;
    }

    @Override
    public List<RiTipiintervento> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
