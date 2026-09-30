package it.gruppoinit.pal.gp.core.features.scadenzario;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class ScadenzarioOneriQueryHelper extends BaseQueryHelper {

    private Date dataScadenza;
    private String[] codiciComuni;

    public ScadenzarioOneriQueryHelper(SessionFactoryImplementor sessimpl, Date dataOdierna, String[] codiciComuni) {

	String hibernateDialect = sessimpl.getDialect().toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	this.dataScadenza = dataOdierna;
	this.codiciComuni = codiciComuni;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	//	sb.append(" istanzeoneri.idcomune = ?"); //
	//	sb.append(" AND istanzeoneri.datascadenza < ?"); //
	//	sb.append(" AND istanzeoneri.flentratauscita = ?"); //
	//	sb.append(" AND istanzeoneri.prezzo > ?"); //
	//	sb.append(" AND istanzeoneri.datapagamento IS NULL"); //
	//	sb.append(" AND coalesce(istanzeoneri.flag_nondovuto,0) = ? "); //
	//	sb.append(" AND istanze.codicecomune IN ("); //
	//	sb.append("  CODICI_COMUNE_LIST"); //
	//	sb.append(" )"); //
	int pos = 0;
	q.setString(pos++, ORMHelper.getIdcomune());
	if (!ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    q.setString(pos++, ORMHelper.getSoftware());
	}
	q.setDate(pos++, dataScadenza);
	q.setInteger(pos++, 1); // flentratauscita
	q.setInteger(pos++, 0); // prezzo
	q.setInteger(pos++, 0); // flag_nondovuto
	for (String comune : codiciComuni) {
	    q.setString(pos++, comune);
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("datascadenza", Hibernate.DATE);
	q.addScalar("numeroistanza", Hibernate.STRING);
	q.addScalar("richiedente", Hibernate.STRING);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("totale", Hibernate.BIG_DECIMAL);
	q.addScalar("totalepagato", Hibernate.BIG_DECIMAL);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.addScalar("codicestc", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	StringBuilder sb = new StringBuilder();
	sb.append("select");
	sb.append(" istanzeoneri.datascadenza AS datascadenza,"); //
	sb.append(" istanze.numeroistanza AS numeroistanza,"); //
	sb.append(applyConcatFunction(this._dialetto, "' '", new String[] { "anagrafe.nominativo", "anagrafe.nome" })).append(" AS richiedente,");
	sb.append(" alberoproc.descrizione_completa AS descrizione,"); //
	sb.append(" SUM(istanzeoneri.prezzo) AS totale,"); //
	sb.append(" SUM(istanzeoneri.importopagato) AS totalepagato,"); //
	sb.append(" istanze.codiceistanza,"); //
	sb.append(" istanze.software,"); //
	sb.append(" istanzeoneri.docriferimento,"); //
	sb.append(" domandestc.id_domandamitt AS codicestc"); //
	sb.append(" FROM"); //
	sb.append(" istanzeoneri"); //
	sb.append(" INNER JOIN istanze ON istanze.idcomune = istanzeoneri.idcomune"); //
	sb.append("  AND istanze.codiceistanza = istanzeoneri.codiceistanza"); //
	sb.append(" LEFT JOIN domandestc ON istanze.idcomune = domandestc.idcomune"); //
	sb.append(" AND istanze.codiceistanza = domandestc.codiceistanza"); //
	sb.append(" INNER JOIN tipicausalioneri ON tipicausalioneri.idcomune = istanzeoneri.idcomune"); //
	sb.append("  AND tipicausalioneri.co_id = istanzeoneri.fkidtipocausale"); //
	sb.append(" LEFT JOIN raggruppamentocausalioneri ON raggruppamentocausalioneri.idcomune = tipicausalioneri.idcomune"); //
	sb.append("  AND raggruppamentocausalioneri.rco_id = tipicausalioneri.fk_rco_id"); //
	sb.append(" INNER JOIN alberoproc ON alberoproc.idcomune = istanze.idcomune"); //
	sb.append("  AND alberoproc.sc_id = istanze.codiceinterventoproc"); //
	sb.append(" INNER JOIN anagrafe ON anagrafe.idcomune = istanze.idcomune"); //
	sb.append("   AND anagrafe.codiceanagrafe = istanze.codicerichiedente"); //
	sb.append(" WHERE"); //
	sb.append(" istanzeoneri.idcomune = ?"); //
	if (!ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    sb.append(" AND istanze.software = ?"); //
	}
	sb.append(" AND istanzeoneri.datascadenza < ?"); //
	sb.append(" AND istanzeoneri.flentratauscita = ?"); //
	sb.append(" AND istanzeoneri.prezzo > ?"); //
	sb.append(" AND istanzeoneri.datapagamento IS NULL"); //
	sb.append(" AND coalesce(istanzeoneri.flag_nondovuto,0) = ? "); //
	sb.append(" AND istanze.codicecomune IN ("); //
	String qm = StringUtils.repeat("?,", codiciComuni.length);
	qm = qm.substring(0, qm.length() - 1);
	sb.append(qm); //
	sb.append(" )"); //
	sb.append(" GROUP BY"); //
	sb.append(" istanzeoneri.datascadenza,"); //
	sb.append(" istanze.numeroistanza,"); //
	sb.append(" istanze.codiceistanza,"); //
	sb.append(" alberoproc.descrizione_completa,"); //
	sb.append(applyConcatFunction(this._dialetto, "' '", new String[] { "anagrafe.nominativo", "anagrafe.nome" })).append(",");
	sb.append(" istanze.software,"); //
	sb.append(" istanzeoneri.docriferimento,"); //
	sb.append(" domandestc.id_domandamitt"); //
	sb.append(" ORDER BY"); //
	sb.append(" istanzeoneri.datascadenza ASC,"); //
	sb.append(" istanze.codiceistanza ASC");
	return sb.toString();
    }

    protected String applyConcatFunction(DialettoEnum dialetto, String separator, String... paramsToConcat) {

	String result = new String();
	switch (dialetto) {
	    case MYSQL:
		// concat(ifnull(param[0],''),ifnull(param[1],''),....)
		result = result.concat("concat(");
		for (String param : paramsToConcat) {
		    result = result.concat("ifnull(").concat(param).concat(",''),");
		    if (StringUtils.isNotBlank(separator)) {
			result = result.concat(separator).concat(",");
		    }
		}
		result = result.substring(0, (result.length() - 1));
		result = result.concat(")");
		break;
	    case POSTGRES:
		// coalesce(param[0],'') || ' ' ||  coalesce(param[0],'') 
		for (String param : paramsToConcat) {
		    result = result.concat("coalesce(").concat(param).concat(",'') || ");
		    if (StringUtils.isNotBlank(separator)) {
			result = result.concat(separator).concat(" || ");
		    }
		}
		result = result.substring(0, (result.length() - 3));
		break;
	    case ORACLE:
		// param[0] || param[1] || ...
		for (String param : paramsToConcat) {
		    result = result.concat(param).concat(" || ");
		    if (StringUtils.isNotBlank(separator)) {
			result = result.concat(separator).concat(" || ");
		    }
		}
		result = result.substring(0, (result.length() - 3));
		break;
	    case SQLSERVER:
		// param[0] + param[1] + ...
		for (String param : paramsToConcat) {
		    result = result.concat(param).concat(" + ");
		    if (StringUtils.isNotBlank(separator)) {
			result = result.concat(separator).concat(" + ");
		    }
		}
		result = result.substring(0, (result.length() - 2));
		break;
	    default:
		break;
	}
	return result;
    }
}
