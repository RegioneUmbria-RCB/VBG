package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class QueryPosizioniDaAggiungereABlackListHelper extends BaseQueryHelper {

    protected static final Logger log = LoggerFactory.getLogger(QueryPosizioniDaAggiungereABlackListHelper.class);
    protected List<String> statiPagabili;

    public QueryPosizioniDaAggiungereABlackListHelper(SessionFactoryImplementor sessimpl, List<String> statiPagabili) {

	super();
	this.statiPagabili = statiPagabili;
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryPosizioniDaAggiungereABlackListHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryPosizioniDaAggiungereABlackListHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryPosizioniDaAggiungereABlackListHelper: schemaName={}", schemaName);
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

	q.addScalar("idDettPosizioneDebitoria", Hibernate.INTEGER);
	q.addScalar("idPresenza", Hibernate.INTEGER);
    }

    @Override
    public String buildQuery() {

	String parStati = StringUtils.repeat("?,", statiPagabili.size());
	parStati = parStati.substring(0, parStati.length() - 1);
	String sql = "select " +
		" dett_posizione_debitoria.id as idDettPosizioneDebitoria, " + //
		" mercatipresenze_d.id as idPresenza " + //
		"from " + //
		"  dett_posizione_debitoria " + //
		"    inner join mercatipresenze_d on " + //
		"      mercatipresenze_d.idcomune=dett_posizione_debitoria.idcomune and " + //
		"      mercatipresenze_d.fk_pay_pos_deb=dett_posizione_debitoria.id and " + //
		"      mercatipresenze_d.fkidposteggio is not null " + //
		"    inner join autorizzazioni on " + //
		"      mercatipresenze_d.idcomune = autorizzazioni.idcomune and " + //
		"      mercatipresenze_d.fk_autorizzazioni_id = autorizzazioni.id " + //
		"   inner join mercatipresenze_t on " + //
		"      mercatipresenze_t.idcomune=mercatipresenze_d.idcomune and " + //
		"      mercatipresenze_t.id=mercatipresenze_d.fkidtestata " + //
		"where " + //
		"  dett_posizione_debitoria.idcomune = ? and " + //
		"  dett_posizione_debitoria.stato in ( " + //
		parStati + //
		" ) and " + //
		"( mercatipresenze_d.fk_codiceistat is null or mercatipresenze_d.fk_codiceistat NOT IN (?,?) OR  MERCATIPRESENZE_t.DATAREGISTRAZIONE > ?) AND " + // 
		"  not exists " + //
		"  ( " + //
		"    select 1 " + //
		"    from blacklist_src_p_deb_sp " + //
		"      inner join blacklist_motivi on " + //
		"        blacklist_src_p_deb_sp.idcomune = blacklist_motivi.idcomune and " + //
		"        blacklist_src_p_deb_sp.fk_id_blacklist_mot = blacklist_motivi.id and " + //
		"        blacklist_motivi.data_fine_bl is null " + //
		"    where " + //
		"      blacklist_src_p_deb_sp.idcomune = dett_posizione_debitoria.idcomune and " + //
		"      blacklist_src_p_deb_sp.fk_id_paypos_deb = dett_posizione_debitoria.id " + //
		"  ) " + //
		"group by " + //
		"  dett_posizione_debitoria.id, mercatipresenze_d.id";
	int position = 0;
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	for (String stato : statiPagabili) {
	    parameters.add(new ParameterHelper(position, stato, new StringType()));
	    position++;
	}
	parameters.add(new ParameterHelper(position, "BA1", new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, "BA2", new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, Utilities.getDate("10/09/2022", WebConstants.DATE_FORMAT_PATTERN).getTime(), new DateType()));
	position++;
	if (StringUtils.isNotBlank(schemaName)) {
	    sql = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
