package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryCampiSnapshotValorizzati extends BaseQueryHelper {

    private Integer idAttivita;
    private Integer idScheda;
    List<Integer> idCampi;

    public QueryCampiSnapshotValorizzati(SessionFactoryImplementor sessimpl, Integer idAttivita, Integer idScheda) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare la classe QueryCampiSnapshotValorizzati senza passare il riferimento all'attività");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare la classe QueryCampiSnapshotValorizzati senza passare il riferimento alla scheda");
	}
	Dialect dialetto = sessimpl.getDialect();
	//log.debug("QueryCampiSnapshotValorizzati: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	//log.debug("QueryCampiSnapshotValorizzati: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	//log.debug("QueryCampiSnapshotValorizzati: schemaName={}", schemaName);
	this.idAttivita = idAttivita;
	this.idScheda = idScheda;
	this.idCampi = null;
    }

    public QueryCampiSnapshotValorizzati(SessionFactoryImplementor sessimpl, Integer idAttivita, List<Integer> idCampi) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare la classe QueryCampiSnapshotValorizzati senza passare il riferimento all'attività");
	}
	if (idCampi == null || idCampi.isEmpty()) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare la classe QueryCampiSnapshotValorizzati senza passare il riferimento ai campi da ricercare");
	}
	Dialect dialetto = sessimpl.getDialect();
	//log.debug("QueryCampiSnapshotValorizzati: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	//log.debug("QueryCampiSnapshotValorizzati: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	//log.debug("QueryCampiSnapshotValorizzati: schemaName={}", schemaName);
	this.idAttivita = idAttivita;
	this.idCampi = idCampi;
	this.idScheda = null;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    // log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("dataSnapshot", Hibernate.DATE);
	q.addScalar("idCampo", Hibernate.INTEGER);
	q.addScalar("indice", Hibernate.INTEGER);
	q.addScalar("indiceMolteplicita", Hibernate.INTEGER);
    }

    @Override
    public String buildQuery() {

	String sql = "select " +
		" i_attivita_snapshot.data as dataSnapshot,  " +
		" dyn2_campi.id as idCampo,  " +
		" i_attivitadyn2dati_snapshot.indice,  " +
		" i_attivitadyn2dati_snapshot.indice_molteplicita as indiceMolteplicita " +
		"from " +
		" dyn2_modellid " +
		"  inner join dyn2_campi on dyn2_modellid.idcomune = dyn2_campi.idcomune and dyn2_modellid.fk_d2c_id = dyn2_campi.id  " +
		"  inner join i_attivitadyn2dati_snapshot on dyn2_campi.idcomune = i_attivitadyn2dati_snapshot.idcomune and dyn2_campi.id = i_attivitadyn2dati_snapshot.fk_d2c_id " +
		"  inner join i_attivita_snapshot on i_attivitadyn2dati_snapshot.idcomune = i_attivita_snapshot.idcomune and i_attivitadyn2dati_snapshot.fk_ias_id = i_attivita_snapshot.id " +
		"where " +
		" dyn2_modellid.idcomune = ? and " +
		" i_attivita_snapshot.fk_ia_id = ? and ";
	if (this.idScheda != null) {
	    sql += " dyn2_modellid.fk_d2mt_id = ? ";
	} else {
	    String qm = StringUtils.repeat("?,", this.idCampi.size());
	    qm = qm.substring(0, qm.length() - 1);
	    sql += " dyn2_modellid.fk_d2c_id in (" + qm + ")";
	}
	sql += "order by dyn2_campi.id asc, i_attivita_snapshot.data desc";
	int idx = 0;
	parameters.add(new ParameterHelper(idx, ORMHelper.getIdcomune(), new StringType()));
	idx++;
	parameters.add(new ParameterHelper(idx, this.idAttivita, new IntegerType()));
	idx++;
	if (this.idScheda != null) {
	    parameters.add(new ParameterHelper(idx, this.idScheda, new IntegerType()));
	} else {
	    for (Integer idCampo : this.idCampi) {
		parameters.add(new ParameterHelper(idx, idCampo, new IntegerType()));
		idx++;
	    }
	}
	return sql;
    }
}
