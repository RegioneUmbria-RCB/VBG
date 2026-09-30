package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ArchiviazioniOggettiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.ArchiviazioniOggetti;
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
public class ArchiviazioniOggettiDAOImpl extends BaseDAOImpl<ArchiviazioniOggetti, PkId> implements ArchiviazioniOggettiDAO {

    @Override
    public Class<ArchiviazioniOggetti> getEntityClass() {

	return ArchiviazioniOggetti.class;
    }

    @Override
    public List<ArchiviazioniOggetti> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public void insert(Integer codiceOggetto, Integer codiceArchiviazioniIstanze) {

	PkId id = this.newIdFromSequence(new ArchiviazioniOggetti());
	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "INSERT INTO " + schema + ".ARCHIVIAZIONI_OGGETTI (ID,IDCOMUNE,FK_ARCHIVIAZIONI_ISTANZE_ID,CODICEOGGETTO) VALUES (?,?,?,?)";
	SQLQuery sqlQuery = session.createSQLQuery(sql);
	sqlQuery = sqlQuery.addScalar("ID", Hibernate.INTEGER).addScalar("IDCOMUNE", Hibernate.STRING)
		.addScalar("FK_ARCHIVIAZIONI_ISTANZE_ID", Hibernate.INTEGER).addScalar("CODICEOGGETTO", Hibernate.INTEGER);
	sqlQuery.setInteger(0, id.getCodice());
	sqlQuery.setString(1, ORMHelper.getIdcomune());
	sqlQuery.setInteger(2, codiceArchiviazioniIstanze);
	sqlQuery.setInteger(3, codiceOggetto);
	sqlQuery.executeUpdate();
    }
}
