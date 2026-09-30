package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.NlaServiziDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.NlaServizi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author fabrizioc
 */
@Repository
public class NlaServiziDAOImpl extends BaseDAOImpl<NlaServizi, PkId> implements NlaServiziDAO {

    @Override
    public Class<NlaServizi> getEntityClass() {

	return NlaServizi.class;
    }

    @Override
    public List<NlaServizi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
