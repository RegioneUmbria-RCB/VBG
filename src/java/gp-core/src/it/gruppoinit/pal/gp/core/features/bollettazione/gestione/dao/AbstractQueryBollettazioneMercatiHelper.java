package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;

public abstract class AbstractQueryBollettazioneMercatiHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryBollettazioneIstanzeHelper.class);

    public List<ParameterHelper> getParameters() {

	return this.parameters;
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

	q.addScalar("provenienza", Hibernate.STRING);
	q.addScalar("subentro", Hibernate.BOOLEAN);
	q.addScalar("idGiornata", Hibernate.INTEGER);
	q.addScalar("dataGiornata", Hibernate.DATE);
	q.addScalar("idPosteggio", Hibernate.INTEGER);
	q.addScalar("idRiferimento", Hibernate.INTEGER);
	q.addScalar("idAnagrafe", Hibernate.INTEGER);
	q.addScalar("assenzaGiustificata", Hibernate.BOOLEAN);
	q.addScalar("idAutorizzazioneConcessione", Hibernate.INTEGER);
	q.addScalar("concpresente", Hibernate.BOOLEAN);
	q.addScalar("spuntpresente", Hibernate.BOOLEAN);
	q.addScalar("catmerc", Hibernate.STRING);
	q.addScalar("idUso", Hibernate.INTEGER);
	q.addScalar("idAutorizzazioniSubentri", Hibernate.INTEGER);
    }

    @Override
    public abstract String buildQuery();
}
