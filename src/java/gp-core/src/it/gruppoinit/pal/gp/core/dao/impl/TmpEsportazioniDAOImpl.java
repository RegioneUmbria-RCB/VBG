package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.TmpEsportazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.TmpEsportazioni;

/**
 * 
 * @author
 */
@Repository
public class TmpEsportazioniDAOImpl extends BaseDAOImpl<TmpEsportazioni, Integer> implements TmpEsportazioniDAO {

    protected final String SCHEMA_NAME = "#SCHEMA_NAME#";

    @Override
    public Class<TmpEsportazioni> getEntityClass() {

	return TmpEsportazioni.class;
    }

    @Override
    public List<TmpEsportazioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, DAOOrderTypeEnum.ASC);
    }

    @Override
    public void deleteBysessionId(String sessionId) {

	String queryDelete = "delete from tmp_esportazioni where sessionid = ?";
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = queryDelete.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, sessionId);
	q.executeUpdate();
    }
}
