package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;

public class QueryPerRicalcoloHelper extends BaseQueryHelper {

    RicalcoloFilter filter;
    String[] comuniAbilitati;

    public QueryPerRicalcoloHelper(RicalcoloFilter filter, String[] comuniAbilitati) {

	this.filter = filter;
	this.comuniAbilitati = comuniAbilitati;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("codiceIstanza", Hibernate.INTEGER);
    }

    @Override
    public String buildQuery() {

	int idx = 0;
	String sql = "select istanze.codiceistanza as codiceIstanza from istanze where istanze.idcomune = ? ";
	parameters.add(new ParameterHelper(idx, ORMHelper.getIdcomune(), new StringType()));
	idx++;
	//filtro software
	if (StringUtils.isNotBlank(this.filter.getSoftware())) {
	    sql += " and software = ? ";
	    parameters.add(new ParameterHelper(idx, this.filter.getSoftware(), new StringType()));
	    idx++;
	}
	//filtro dallaData
	if (this.filter.getDallaData() != null) {
	    sql += " and istanze.data >= ?";
	    Calendar t = Calendar.getInstance();
	    t.setTime(this.filter.getDallaData());
	    t.set(Calendar.HOUR, 0);
	    t.set(Calendar.MINUTE, 0);
	    t.set(Calendar.SECOND, 0);
	    parameters.add(new ParameterHelper(idx, t.getTime(), new TimestampType()));
	    idx++;
	}
	//filtro allaData
	if (this.filter.getAllaData() != null) {
	    sql += " and istanze.data <= ?";
	    Calendar t = Calendar.getInstance();
	    t.setTime(this.filter.getAllaData());
	    t.set(Calendar.HOUR, 0);
	    t.set(Calendar.MINUTE, 0);
	    t.set(Calendar.SECOND, 0);
	    parameters.add(new ParameterHelper(idx, t.getTime(), new TimestampType()));
	    idx++;
	}
	//filtro comuni associati
	if (this.comuniAbilitati != null) {
	    sql += " and istanze.codicecomune in  (";
	    for (int i = 0; i < this.comuniAbilitati.length; i++) {
		sql += "?,";
		parameters.add(new ParameterHelper(idx, this.comuniAbilitati[i], new StringType()));
		idx++;
	    }
	    sql = sql.substring(0, sql.length() - 1);
	    sql += ")";
	}
	sql += " order by istanze.idcomune, istanze.codiceistanza desc";
	return sql;
    }
}
