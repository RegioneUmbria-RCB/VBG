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

public class QueryCampiIstanzeValorizzati extends BaseQueryHelper {

    private Integer idAttivita;
    private Integer idScheda;
    List<Integer> idCampi;

    public QueryCampiIstanzeValorizzati(SessionFactoryImplementor sessimpl, Integer idAttivita, Integer idScheda) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare la classe QueryCampiIstanzeValorizzati senza passare il riferimento all'attività");
	}
	if (idScheda == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare la classe QueryCampiIstanzeValorizzati senza passare il riferimento ai campi da ricercare");
	}
	Dialect dialetto = sessimpl.getDialect();
	//log.debug("QueryCampiIstanzeValorizzati: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	//log.debug("QueryCampiIstanzeValorizzati: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	//log.debug("QueryCampiIstanzeValorizzati: schemaName={}", schemaName);
	this.idAttivita = idAttivita;
	this.idScheda = idScheda;
    }

    public QueryCampiIstanzeValorizzati(SessionFactoryImplementor sessimpl, Integer idAttivita, List<Integer> idCampi) {

	if (idAttivita == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare la classe QueryCampiIstanzeValorizzati senza passare il riferimento all'attività");
	}
	if (idCampi == null || idCampi.isEmpty()) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare la classe QueryCampiIstanzeValorizzati senza passare il riferimento ai campi da ricercare");
	}
	Dialect dialetto = sessimpl.getDialect();
	//log.debug("QueryCampiIstanzeValorizzati: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	//log.debug("QueryCampiIstanzeValorizzati: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	//log.debug("QueryCampiIstanzeValorizzati: schemaName={}", schemaName);
	this.idAttivita = idAttivita;
	this.idCampi = idCampi;
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

	q.addScalar("dataValidita", Hibernate.DATE);
	q.addScalar("ordine", Hibernate.INTEGER);
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("nomeCampo", Hibernate.STRING);
	q.addScalar("etichetta", Hibernate.STRING);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("tipoDato", Hibernate.STRING);
	q.addScalar("indice", Hibernate.INTEGER);
	q.addScalar("indiceMolteplicita", Hibernate.INTEGER);
	q.addScalar("valore", Hibernate.STRING);
	q.addScalar("valoreDecodificato", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	String sql = "select " +
		" istanze.datavalidita as dataValidita,  " +
		" istanze.i_attivitaordine as ordine,  " +
		" dyn2_campi.id,  " +
		" dyn2_campi.nomecampo as nomeCampo,  " +
		" dyn2_campi.etichetta,  " +
		" dyn2_campi.descrizione,  " +
		" dyn2_campi.tipodato as tipoDato, " +
		" istanzedyn2dati.indice,  " +
		" istanzedyn2dati.indice_molteplicita as indiceMolteplicita,  " +
		" istanzedyn2dati.valore,  " +
		" istanzedyn2dati.valoredecodificato as valoreDecodificato  " +
		"from " +
		" dyn2_modellid " +
		"   inner join dyn2_campi on dyn2_modellid.idcomune = dyn2_campi.idcomune and dyn2_modellid.fk_d2c_id = dyn2_campi.id  " +
		"   inner join istanzedyn2dati on dyn2_campi.idcomune = istanzedyn2dati.idcomune and dyn2_campi.id = istanzedyn2dati.fk_d2c_id " +
		"   inner join istanze on istanzedyn2dati.idcomune = istanze.idcomune and istanzedyn2dati.codiceistanza = istanze.codiceistanza " +
		"where " +
		" dyn2_modellid.idcomune = ? and " +
		" istanze.fk_idi_attivita = ? and " +
		" istanze.datavalidita is not null and ";
	if (this.idScheda != null) {
	    sql += " dyn2_modellid.fk_d2mt_id = ? ";
	} else {
	    String qm = StringUtils.repeat("?,", this.idCampi.size());
	    qm = qm.substring(0, qm.length() - 1);
	    sql += " dyn2_modellid.fk_d2c_id in (" + qm + ")";
	}
	sql += "order by dyn2_campi.id asc, istanze.datavalidita desc, istanze.i_attivitaordine asc";
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
