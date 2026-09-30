package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.upgr;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryMassiveDettDestinatariHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryMassiveDettDestinatariHelper.class);

    public QueryMassiveDettDestinatariHelper(SessionFactoryImplementor sessimpl) {

	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryMassiveDettDestinatariHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryMassiveDettDestinatariHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryMassiveDettDestinatariHelper: schemaName={}", schemaName);
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	throw new NotImplementedException();
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idComune", Hibernate.STRING);
	q.addScalar("idMassiveD", Hibernate.INTEGER);
	q.addScalar("codiceAnagrafe", Hibernate.INTEGER);
	q.addScalar("mailDestinatario", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	StringBuilder sql = new StringBuilder();
	sql.append("SELECT");
	sql.append(" massive_dettaglio.idcomune as idComune,");
	sql.append(" massive_dettaglio.id as idMassiveD,");
	sql.append(" massive_dettaglio.codiceanagrafe as codiceAnagrafe,");
	sql.append(" massive_dettaglio.mail_destinatario as mailDestinatario");
	sql.append(" FROM");
	sql.append(" massive_dettaglio");
	sql.append(" LEFT JOIN massive_dett_destinatari ON massive_dett_destinatari.idcomune = massive_dettaglio.idcomune");
	sql.append(" AND massive_dett_destinatari.fkid_massive_d = massive_dettaglio.id");
	sql.append(" WHERE");
	sql.append(" massive_dett_destinatari.id IS NULL");
	sql.append(" AND   massive_dettaglio.codiceanagrafe IS NOT NULL");
	sql.append(" order by");
	sql.append(" massive_dettaglio.idcomune");
	return sql.toString();
    }
}
