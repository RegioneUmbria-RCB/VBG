package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class QuerySpuntistiMercatiHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QuerySpuntistiMercatiHelper.class);
    private Integer codiceMercato;
    private Integer codiceUso;
    private Integer giorniDiAssenza;

    public QuerySpuntistiMercatiHelper(Integer codiceMercato, Integer codiceUso, Integer giorniDiAssenza, SessionFactoryImplementor sessimpl) {

	log.debug("QuerySpuntistiMercatiHelper: recupero il dialetto della SessionFactoryImplementor");
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QuerySpuntistiMercatiHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QuerySpuntistiMercatiHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QuerySpuntistiMercatiHelper: schemaName={}", schemaName);
	this.codiceMercato = codiceMercato;
	this.codiceUso = codiceUso;
	this.giorniDiAssenza = giorniDiAssenza;
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

	q.addScalar("idautorizzazione", Hibernate.BIG_DECIMAL);
	q.addScalar("autoriznumero", Hibernate.STRING);
	q.addScalar("idspuntistimercati", Hibernate.BIG_DECIMAL);
	q.addScalar("dataregistrazione", Hibernate.DATE);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("codicemercato", Hibernate.BIG_DECIMAL);
	q.addScalar("mercato", Hibernate.STRING);
	q.addScalar("codicegiorno", Hibernate.BIG_DECIMAL);
	q.addScalar("giorno", Hibernate.STRING);
	q.addScalar("codiceanagrafe", Hibernate.BIG_DECIMAL);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("nominativo", Hibernate.STRING);
	q.addScalar("codicefiscale", Hibernate.STRING);
	q.addScalar("partitaiva", Hibernate.STRING);
	// q.addScalar("dataregistrazionepresenza", Hibernate.DATE);
    }

    @Override
    public String buildQuery() {

	//////
	Date toDay = new Date();
	Date dataConfronto = Utilities.addAndremoveDays(toDay, giorniDiAssenza, false);
	String _toDay = stringToDate_DDMMYYYY(Utilities.formatDate(toDay, false), _dialetto).toString();
	String _dataConfronto = stringToDate_DDMMYYYY(Utilities.formatDate(dataConfronto, false), _dialetto).toString();
	log.debug("buildQuery# Create query.....");
	//////
	String sql = "SELECT ";
	sql += " AUTORIZZAZIONI.ID AS idautorizzazione ";
	sql += ", AUTORIZZAZIONI.AUTORIZNUMERO AS autoriznumero ";
	sql += ", SPUNTISTI_MERCATI.ID AS idspuntistimercati ";
	sql += ", SPUNTISTI_MERCATI.DATA_REGISTRAZIONE AS dataregistrazione ";
	sql += ", SPUNTISTI_MERCATI.IDCOMUNE AS idcomune ";
	sql += ", MERCATI.CODICEMERCATO AS codicemercato ";
	sql += ", MERCATI.DESCRIZIONE AS mercato ";
	sql += ", MERCATI_USO.ID AS codicegiorno ";
	sql += ", MERCATI_USO.DESCRIZIONE AS giorno ";
	sql += ", ANAGRAFE.CODICEANAGRAFE AS codiceanagrafe ";
	sql += ", ANAGRAFE.NOME AS nome ";
	sql += ", ANAGRAFE.NOMINATIVO AS nominativo ";
	sql += ", ANAGRAFE.CODICEFISCALE AS codicefiscale ";
	sql += ", ANAGRAFE.PARTITAIVA AS partitaiva ";
	sql += "FROM ";
	sql += " SPUNTISTI_MERCATI ";
	sql += " INNER JOIN ";
	sql += " AUTORIZZAZIONI ";
	sql += " ON ";
	sql += " SPUNTISTI_MERCATI.IDCOMUNE = AUTORIZZAZIONI.IDCOMUNE ";
	sql += " AND SPUNTISTI_MERCATI.FK_AUTORIZZAZIONE=AUTORIZZAZIONI.ID ";
	sql += " INNER JOIN ";
	sql += " ANAGRAFE ";
	sql += " ON ";
	sql += " AUTORIZZAZIONI.IDCOMUNE = ANAGRAFE.IDCOMUNE ";
	sql += " AND AUTORIZZAZIONI.FK_CODICEANAGRAFE=ANAGRAFE.CODICEANAGRAFE ";
	sql += " LEFT JOIN ";
	sql += " MERCATIPRESENZE_D ";
	sql += " ON ";
	sql += " AUTORIZZAZIONI.IDCOMUNE=MERCATIPRESENZE_D.IDCOMUNE ";
	sql += " AND AUTORIZZAZIONI.ID = MERCATIPRESENZE_D.FK_AUTORIZZAZIONI_ID ";
	sql += " LEFT JOIN ";
	sql += " MERCATIPRESENZE_T ";
	sql += " ON ";
	sql += " MERCATIPRESENZE_T.IDCOMUNE=SPUNTISTI_MERCATI.IDCOMUNE ";
	sql += " AND MERCATIPRESENZE_T.FKCODICEMERCATO=SPUNTISTI_MERCATI.FK_MERCATO ";
	sql += " AND MERCATIPRESENZE_T.FKIDMERCATIUSO=SPUNTISTI_MERCATI.FK_MERCATO_USO ";
	sql += " AND MERCATIPRESENZE_T.ID=MERCATIPRESENZE_D.FKIDTESTATA ";
	sql += " INNER JOIN ";
	sql += " MERCATI ";
	sql += " ON ";
	sql += " SPUNTISTI_MERCATI.IDCOMUNE = MERCATI.IDCOMUNE ";
	sql += " AND SPUNTISTI_MERCATI.FK_MERCATO=MERCATI.CODICEMERCATO ";
	sql += " INNER JOIN ";
	sql += " MERCATI_USO ";
	sql += " ON ";
	sql += " SPUNTISTI_MERCATI.IDCOMUNE =MERCATI_USO.IDCOMUNE ";
	sql += " AND SPUNTISTI_MERCATI.FK_MERCATO_USO=MERCATI_USO.ID ";
	sql += "WHERE ";
	sql += " SPUNTISTI_MERCATI.IDCOMUNE = ? ";
	sql += " AND SPUNTISTI_MERCATI.FLG_ATTIVO = ? ";
	sql += " AND MERCATI.CODICEMERCATO = ? ";
	sql += " AND MERCATI_USO.ID = ? ";
	sql += " AND SPUNTISTI_MERCATI.DATA_REGISTRAZIONE <  " + _dataConfronto;
	sql += " AND SPUNTISTI_MERCATI.ID NOT IN ";
	sql += " ( ";
	sql += " SELECT SPUNTISTI_MERCATI.ID ";
	sql += " FROM ";
	sql += " SPUNTISTI_MERCATI ";
	sql += " INNER JOIN ";
	sql += " AUTORIZZAZIONI ";
	sql += " ON ";
	sql += " SPUNTISTI_MERCATI.IDCOMUNE = AUTORIZZAZIONI.IDCOMUNE ";
	sql += " AND SPUNTISTI_MERCATI.FK_AUTORIZZAZIONE=AUTORIZZAZIONI.ID ";
	sql += " INNER JOIN ";
	sql += " MERCATIPRESENZE_D ";
	sql += " ON ";
	sql += " AUTORIZZAZIONI.IDCOMUNE=MERCATIPRESENZE_D.IDCOMUNE ";
	sql += " AND AUTORIZZAZIONI.ID = MERCATIPRESENZE_D.FK_AUTORIZZAZIONI_ID ";
	sql += " INNER JOIN ";
	sql += " MERCATIPRESENZE_T ";
	sql += " ON ";
	sql += " MERCATIPRESENZE_T.IDCOMUNE=MERCATIPRESENZE_D.IDCOMUNE ";
	sql += " AND MERCATIPRESENZE_T.FKCODICEMERCATO=SPUNTISTI_MERCATI.FK_MERCATO ";
	sql += " AND MERCATIPRESENZE_T.FKIDMERCATIUSO=SPUNTISTI_MERCATI.FK_MERCATO_USO ";
	sql += " AND MERCATIPRESENZE_T.ID=MERCATIPRESENZE_D.FKIDTESTATA ";
	sql += " WHERE ";
	sql += " MERCATIPRESENZE_T.IDCOMUNE = ? ";
	sql += " AND MERCATIPRESENZE_T.FKCODICEMERCATO = ? ";
	sql += " AND MERCATIPRESENZE_T.FKIDMERCATIUSO = ? ";
	sql += " AND MERCATIPRESENZE_T.DATAREGISTRAZIONE >= " + _dataConfronto;
	sql += " AND MERCATIPRESENZE_T.DATAREGISTRAZIONE <= " + _toDay;
	sql += " AND SPUNTISTI_MERCATI.FLG_ATTIVO = ? ";
	sql += " AND MERCATIPRESENZE_D.NUMEROPRESENZE>? ";
	sql += " ) ";
	sql += "GROUP BY ";
	sql += " AUTORIZZAZIONI.ID ";
	sql += ", AUTORIZZAZIONI.AUTORIZNUMERO ";
	sql += ", SPUNTISTI_MERCATI.ID ";
	sql += ", SPUNTISTI_MERCATI.DATA_REGISTRAZIONE ";
	sql += ", SPUNTISTI_MERCATI.IDCOMUNE ";
	sql += ", MERCATI.CODICEMERCATO ";
	sql += ", MERCATI.DESCRIZIONE ";
	sql += ", MERCATI_USO.ID ";
	sql += ", MERCATI_USO.DESCRIZIONE ";
	sql += ", ANAGRAFE.CODICEANAGRAFE ";
	sql += ", ANAGRAFE.NOME ";
	sql += ", ANAGRAFE.NOMINATIVO ";
	sql += ", ANAGRAFE.CODICEFISCALE ";
	sql += ", ANAGRAFE.PARTITAIVA";
	log.debug("buildQuery# populate param query.....");
	int position = 0;
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, 1, new IntegerType()));
	position++;
	parameters.add(new ParameterHelper(position, codiceMercato, new IntegerType()));
	position++;
	parameters.add(new ParameterHelper(position, codiceUso, new IntegerType()));
	position++;
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, codiceMercato, new IntegerType()));
	position++;
	parameters.add(new ParameterHelper(position, codiceUso, new IntegerType()));
	position++;
	parameters.add(new ParameterHelper(position, 1, new IntegerType()));
	position++;
	parameters.add(new ParameterHelper(position, 0, new IntegerType()));
	position++;
	////
	log.debug("sql={}", sql);
	return sql;
    }
}
