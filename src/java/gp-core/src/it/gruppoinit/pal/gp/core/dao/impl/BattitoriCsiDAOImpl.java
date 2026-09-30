package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.BattitoriCsiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.BattitoriCsi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class BattitoriCsiDAOImpl extends BaseDAOImpl<BattitoriCsi, PkId> implements BattitoriCsiDAO {

    @Override
    public Class<BattitoriCsi> getEntityClass() {

	return BattitoriCsi.class;
    }

    @Override
    public List<BattitoriCsi> findAll(Integer firstResult, Integer maxResult) {

	//TODO: impostare l'argomento PROP_DELLA_ENTITY
	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "PROP_DELLA_ENTITY", DAOOrderTypeEnum.ASC);
    }
}
