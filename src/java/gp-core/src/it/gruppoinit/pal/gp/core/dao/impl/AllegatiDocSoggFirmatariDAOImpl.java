package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AllegatiDocSoggFirmatariDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AllegatiDocSoggFirmatari;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AllegatiDocSoggFirmatariDAOImpl extends BaseDAOImpl<AllegatiDocSoggFirmatari, PkId> implements AllegatiDocSoggFirmatariDAO {

    @Override
    public Class<AllegatiDocSoggFirmatari> getEntityClass() {

	return AllegatiDocSoggFirmatari.class;
    }

    @Override
    public List<AllegatiDocSoggFirmatari> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, DAOOrderTypeEnum.ASC);
    }
}
