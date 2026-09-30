package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

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

public class QueryCampiAttivitaValorizzati extends BaseQueryHelper {

    private Integer idAttivita;
    private Integer idScheda;

    public QueryCampiAttivitaValorizzati(SessionFactoryImplementor sessimpl, Integer idAttivita) {

	this(sessimpl, idAttivita, null);
    }

    public QueryCampiAttivitaValorizzati(SessionFactoryImplementor sessimpl, Integer idAttivita, Integer idScheda) {

	Dialect dialetto = sessimpl.getDialect();
	//log.debug("QueryCampiAttivitaValorizzati: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	//log.debug("QueryCampiAttivitaValorizzati: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	//log.debug("QueryCampiAttivitaValorizzati: schemaName={}", schemaName);
	this.idAttivita = idAttivita;
	this.idScheda = idScheda;
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
		" dyn2_campi.id,  " +
		" dyn2_campi.nomecampo as nomeCampo,  " +
		" dyn2_campi.etichetta,  " +
		" dyn2_campi.descrizione,  " +
		" dyn2_campi.tipodato as tipoDato, " +
		" i_attivitadyn2dati.indice,  " +
		" i_attivitadyn2dati.indice_molteplicita as indiceMolteplicita,  " +
		" i_attivitadyn2dati.valore,  " +
		" i_attivitadyn2dati.valoredecodificato as valoreDecodificato  " +
		"from " +
		" dyn2_modellid " +
		"  inner join dyn2_campi on dyn2_modellid.idcomune = dyn2_campi.idcomune and dyn2_modellid.fk_d2c_id = dyn2_campi.id  " +
		"  inner join i_attivitadyn2dati on dyn2_campi.idcomune = i_attivitadyn2dati.idcomune and dyn2_campi.id = i_attivitadyn2dati.fk_d2c_id " +
		"where " +
		" dyn2_modellid.idcomune = ? and ";
	if (this.idScheda != null) {
	    sql += " dyn2_modellid.fk_d2mt_id = ? and ";
	}
	sql += " i_attivitadyn2dati.fk_ia_id = ? order by dyn2_campi.id asc";
	int idx = 0;
	parameters.add(new ParameterHelper(idx, ORMHelper.getIdcomune(), new StringType()));
	idx++;
	if (this.idScheda != null) {
	    parameters.add(new ParameterHelper(idx, this.idScheda, new IntegerType()));
	    idx++;
	}
	parameters.add(new ParameterHelper(idx, this.idAttivita, new IntegerType()));
	return sql;
    }
}
