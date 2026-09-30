package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ArchiviazioniIstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.ArchiviazioniIstanze;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author fabrizioc
 */
@Repository
public class ArchiviazioniIstanzeDAOImpl extends BaseDAOImpl<ArchiviazioniIstanze, PkId> implements ArchiviazioniIstanzeDAO {

    @Override
    public Class<ArchiviazioniIstanze> getEntityClass() {

	return ArchiviazioniIstanze.class;
    }

    @Override
    public List<ArchiviazioniIstanze> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public Integer insert(Integer codiceArchiviazione, Integer codiceIstanza) {

	PkId id = this.newIdFromSequence(new ArchiviazioniIstanze());
	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "INSERT INTO " + schema + ".ARCHIVIAZIONI_ISTANZE (ID,IDCOMUNE,FK_ARCHIVIAZIONI_ID,CODICEISTANZA,PARZIALE) VALUES (?,?,?,?,?)";
	SQLQuery sqlQuery = session.createSQLQuery(sql);
	sqlQuery = sqlQuery.addScalar("ID", Hibernate.INTEGER).addScalar("IDCOMUNE", Hibernate.STRING)
		.addScalar("FK_ARCHIVIAZIONI_ID", Hibernate.INTEGER).addScalar("CODICEISTANZA", Hibernate.INTEGER)
		.addScalar("PARZIALE", Hibernate.INTEGER);
	sqlQuery.setInteger(0, id.getCodice());
	sqlQuery.setString(1, ORMHelper.getIdcomune());
	sqlQuery.setInteger(2, codiceArchiviazione);
	sqlQuery.setInteger(3, codiceIstanza);
	sqlQuery.setInteger(4, 0);
	sqlQuery.executeUpdate();
	return id.getCodice();
    }
}
