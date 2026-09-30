package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiD2cAssegnazDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.MercatiD2cAssegnaz;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class MercatiD2cAssegnazDAOImpl extends BaseDAOImpl<MercatiD2cAssegnaz, PkId> implements MercatiD2cAssegnazDAO {

    @Override
    public Class<MercatiD2cAssegnaz> getEntityClass() {

	return MercatiD2cAssegnaz.class;
    }

    @Override
    public List<MercatiD2cAssegnaz> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.codice", DAOOrderTypeEnum.ASC);
    }
}
