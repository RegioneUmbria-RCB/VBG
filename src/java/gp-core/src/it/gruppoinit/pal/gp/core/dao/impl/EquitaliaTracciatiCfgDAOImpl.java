package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.EquitaliaTracciatiCfgDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.EquitaliaTracciatiCfg;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class EquitaliaTracciatiCfgDAOImpl extends BaseDAOImpl<EquitaliaTracciatiCfg, PkId> implements EquitaliaTracciatiCfgDAO {

    @Override
    public Class<EquitaliaTracciatiCfg> getEntityClass() {

	return EquitaliaTracciatiCfg.class;
    }

    @Override
    public List<EquitaliaTracciatiCfg> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, DAOOrderTypeEnum.ASC);
    }
}
