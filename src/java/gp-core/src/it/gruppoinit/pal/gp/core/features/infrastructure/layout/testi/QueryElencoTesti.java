package it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.StringType;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryElencoTesti extends BaseQueryHelper {

    public QueryElencoTesti(SessionFactoryImplementor sessimpl) {

	Dialect dialetto = sessimpl.getDialect();
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("codiceTesto", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("testoBase", Hibernate.STRING);
	q.addScalar("nuovoTesto", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	String sql = "select" + // 
		"  coalesce(layouttesti.codicetesto, layouttestibase.codicetesto) as codiceTesto," + // 
		"  coalesce(layouttesti.software, layouttestibase.software) as software," + //
		"  coalesce(layouttestibase.testo, layouttestibase_tt.testo) as testoBase," + // 
		"  layouttesti.nuovotesto as nuovoTesto " + // 
		"from" + // 
		"  layouttestibase" + // 
		"    left join layouttesti on layouttestibase.software = layouttesti.software and layouttestibase.codicetesto = layouttesti.codicetesto and layouttesti.idcomune = ?" + // 
		"    left join layouttestibase layouttestibase_tt on layouttestibase.codicetesto = layouttestibase_tt.codicetesto and layouttestibase_tt.software = ? ";
	if (!WebConstants.SOFTWARE_TT.equals(ORMHelper.getSoftware())) {
	    sql += " where layouttestibase.software in (?,?) ";
	}
	sql += "union " + // 
		"select" + // 
		"  layouttesti.codicetesto as codiceTesto," + // 
		"  layouttesti.software," + // 
		"  layouttestibase_tt.testo as testoBase," + // 
		"  layouttesti.nuovotesto as nuovoTesto " + // 
		"from " + // 
		"  layouttesti" + // 
		"    left join layouttestibase on layouttesti.software = layouttestibase.software and layouttesti.codicetesto = layouttestibase.codicetesto" + // 
		"    left join layouttestibase layouttestibase_tt on layouttestibase_tt.codicetesto = layouttesti.codicetesto and layouttestibase_tt.software = ? " + // 
		"where" + //
		"  layouttesti.idcomune = ? and";
	if (!WebConstants.SOFTWARE_TT.equals(ORMHelper.getSoftware())) {
	    sql += " layouttesti.software in (?,?) and";
	}
	sql += " layouttestibase.codicetesto is null";
	int idx = 0;
	parameters.add(new ParameterHelper(idx++, ORMHelper.getIdcomune(), new StringType()));
	parameters.add(new ParameterHelper(idx++, WebConstants.SOFTWARE_TT, new StringType()));
	if (!WebConstants.SOFTWARE_TT.equals(ORMHelper.getSoftware())) {
	    parameters.add(new ParameterHelper(idx++, WebConstants.SOFTWARE_TT, new StringType()));
	    parameters.add(new ParameterHelper(idx++, ORMHelper.getSoftware(), new StringType()));
	}
	parameters.add(new ParameterHelper(idx++, WebConstants.SOFTWARE_TT, new StringType()));
	parameters.add(new ParameterHelper(idx++, ORMHelper.getIdcomune(), new StringType()));
	if (!WebConstants.SOFTWARE_TT.equals(ORMHelper.getSoftware())) {
	    parameters.add(new ParameterHelper(idx++, WebConstants.SOFTWARE_TT, new StringType()));
	    parameters.add(new ParameterHelper(idx++, ORMHelper.getSoftware(), new StringType()));
	}
	return sql;
    }
}