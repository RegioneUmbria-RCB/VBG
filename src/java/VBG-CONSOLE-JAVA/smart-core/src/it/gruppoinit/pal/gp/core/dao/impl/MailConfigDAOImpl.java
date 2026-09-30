package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MailConfigDAO;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.MailConfigId;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class MailConfigDAOImpl extends BaseDAOImpl<MailConfig, MailConfigId> implements MailConfigDAO {

    @Override
    public Class<MailConfig> getEntityClass() {

	return MailConfig.class;
    }
}
