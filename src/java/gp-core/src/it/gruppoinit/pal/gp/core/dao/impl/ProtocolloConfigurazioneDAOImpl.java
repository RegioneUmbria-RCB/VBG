package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ProtocolloConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazione;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazioneId;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ProtocolloConfigurazioneDAOImpl extends BaseDAOImpl<ProtocolloConfigurazione, ProtocolloConfigurazioneId> implements
	ProtocolloConfigurazioneDAO {

    @Override
    public Class<ProtocolloConfigurazione> getEntityClass() {

	return ProtocolloConfigurazione.class;
    }

    @Override
    public List<ProtocolloConfigurazione> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.software", DAOOrderTypeEnum.ASC);
    }
}
