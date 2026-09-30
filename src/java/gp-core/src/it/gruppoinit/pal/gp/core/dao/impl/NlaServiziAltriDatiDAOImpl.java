package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.NlaServiziAltriDatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.NlaServiziAltriDati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class NlaServiziAltriDatiDAOImpl extends BaseDAOImpl<NlaServiziAltriDati, PkId> implements NlaServiziAltriDatiDAO {

    @Override
    public Class<NlaServiziAltriDati> getEntityClass() {

	return NlaServiziAltriDati.class;
    }

    @Override
    public List<NlaServiziAltriDati> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "nomeparametro", DAOOrderTypeEnum.ASC);
    }
}
