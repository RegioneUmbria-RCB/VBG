package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2modTSnapshotDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshotId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class IAttivitadyn2modTSnapshotDAOImpl extends BaseDAOImpl<IAttivitadyn2modTSnapshot, IAttivitadyn2modTSnapshotId>
	implements IAttivitadyn2modTSnapshotDAO {

    @Override
    public Class<IAttivitadyn2modTSnapshot> getEntityClass() {

	return IAttivitadyn2modTSnapshot.class;
    }

    @Override
    public List<IAttivitadyn2modTSnapshot> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, null);
    }

    @Override
    public void deleteBySnapshot(Integer idSnapshot) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "delete from " + schemaName + ".I_ATTIVITADYN2MOD_T_SNAPSHOT where IDCOMUNE=? and FK_IAS_ID=?";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idSnapshot);
	query.executeUpdate();
    }
}
