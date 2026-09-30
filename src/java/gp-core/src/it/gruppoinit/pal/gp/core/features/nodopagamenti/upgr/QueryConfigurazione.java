package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.type.StringType;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;

public class QueryConfigurazione extends BaseQueryHelper {

    private static final String MODULO = "NODO_PAGAMENTI";
    private static final String PARAMETRO = "AR_COD_FISC_ENTE_CREDITORE";

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.addScalar("valore", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	String sql = "select " +
		" verticalizzazioniparametri.idcomune, " +
		" verticalizzazioniparametri.software, " +
		" coalesce(verticalizzazioniparametri.codicecomune,'TUTTI') as codiCecomune, " +
		" verticalizzazioniparametri.valore " +
		"from verticalizzazioniparametri " +
		"where " +
		" modulo = ? and " +
		" parametro = ?";
	int position = 0;
	parameters.add(new ParameterHelper(position, QueryConfigurazione.MODULO, new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, QueryConfigurazione.PARAMETRO, new StringType()));
	return sql;
    }
}
