package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ProtocolloDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Protocollo;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class ProtocolloDAOImpl extends BaseDAOImpl<Protocollo, PkId> implements ProtocolloDAO {

    @Override
    public Class<Protocollo> getEntityClass() {

	return Protocollo.class;
    }

    @Override
    public List<Protocollo> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "ordine", DAOOrderTypeEnum.ASC);
    }
}
