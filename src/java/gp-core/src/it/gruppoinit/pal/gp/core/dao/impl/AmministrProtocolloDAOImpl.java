package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AmministrProtocolloDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AmministrProtocollo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class AmministrProtocolloDAOImpl extends BaseDAOImpl<AmministrProtocollo, PkId> implements AmministrProtocolloDAO {

    @Override
    public Class<AmministrProtocollo> getEntityClass() {

	return AmministrProtocollo.class;
    }

    @Override
    public List<AmministrProtocollo> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, DAOOrderTypeEnum.ASC);
    }
}
