package it.gruppoinit.pal.gp.core.features.oneri;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryOneriSistemaPagamentiHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryOneriSistemaPagamentiHelper.class);
    private Integer idPosizioneDebitoria;
    private String cfEnteCreditore;

    public QueryOneriSistemaPagamentiHelper(SessionFactoryImplementor sessimpl, Integer idPosizioneDebitoria, String cfEnteCreditore) {

	super();
	this.idPosizioneDebitoria = idPosizioneDebitoria;
	this.cfEnteCreditore = cfEnteCreditore;
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryOneriSistemaPagamentiHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryOneriSistemaPagamentiHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryOneriSistemaPagamentiHelper: schemaName={}", schemaName);
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idIstanzeOneri", Hibernate.INTEGER);
	q.addScalar("dataUltimoStato", Hibernate.DATE);
	q.addScalar("importoTotale", Hibernate.BIG_DECIMAL);
    }

    @Override
    public String buildQuery() {

	int position = 0;
	String sql = "select coalesce(boll_gest_istanzeoneri.fk_codiceistanzeoneri,istanzeoneri.id) as idIstanzeOneri, " +
		"  dett_posizione_debitoria.data_ultimo_stato as dataUltimoStato, " +
		"  coalesce(boll_gest_dettaglio.importo_totale,istanzeoneri.prezzo) as importoTotale " + //
		" from " + //
		"  dett_posizione_debitoria " + //
		"    left join boll_gest_dettaglio on " + //
		"      dett_posizione_debitoria.idcomune = boll_gest_dettaglio.idcomune and " +
		"      dett_posizione_debitoria.id = boll_gest_dettaglio.fk_posdebdettaglio_id " + //
		"    left join boll_gest_istanzeoneri on " + //
		"      boll_gest_dettaglio.idcomune = boll_gest_istanzeoneri.idcomune and " +
		"      boll_gest_dettaglio.id = boll_gest_istanzeoneri.fk_bollgestdet_id " + //
		"  left join istoneri_dett_posizioni on " + //
		"   dett_posizione_debitoria.idcomune = istoneri_dett_posizioni.idcomune and " +
		"   dett_posizione_debitoria.id = istoneri_dett_posizioni.fk_dettposdebitoria_id " + //
		"  left join istanzeoneri on " + //
		"   istoneri_dett_posizioni.idcomune = istanzeoneri.idcomune and " +
		"   istoneri_dett_posizioni.fk_istanzeoneri_id = istanzeoneri.id " + //
		" where " + //
		" dett_posizione_debitoria.idcomune = ?  " + //
		" and dett_posizione_debitoria.id_posizione_debitoria = ? " + //
		" and dett_posizione_debitoria.cf_ente_creditore = ?";
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, this.idPosizioneDebitoria, new IntegerType()));
	position++;
	parameters.add(new ParameterHelper(position, this.cfEnteCreditore, new StringType()));
	if (StringUtils.isNotBlank(schemaName)) {
	    sql = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
