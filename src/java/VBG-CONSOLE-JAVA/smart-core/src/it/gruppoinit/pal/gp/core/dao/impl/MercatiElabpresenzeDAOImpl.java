package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiElabpresenzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.MercatiElabpresenze;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class MercatiElabpresenzeDAOImpl extends BaseDAOImpl<MercatiElabpresenze, PkId> implements MercatiElabpresenzeDAO {

    @Override
    public Class<MercatiElabpresenze> getEntityClass() {

	return MercatiElabpresenze.class;
    }

    @Override
    public List<MercatiElabpresenze> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "dataElaborazione", DAOOrderTypeEnum.ASC);
    }
}
