package it.gruppoinit.pal.gp.core.features.istanze.datidinamici;

import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Istanze;

@SuppressWarnings("rawtypes")
@Repository
public class UpgrRichiamaFormuleCaricamentoESalvataggioDAOImpl extends BaseDAOImpl implements IUpgrRichiamaFormuleCaricamentoESalvataggioDAO {

    @SuppressWarnings("unchecked")
    @Override
    public TreeSet<Integer> getCodiciIstanzaByInterventi(Set<Integer> elencoInterventi) {

	if (elencoInterventi == null || elencoInterventi.isEmpty()) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo getCodiciIstanzaByInterventi senza passare l'elenco degli interventi da usare come filtro");
	}
	StringBuilder sql = new StringBuilder("select codiceistanza from istanze where idcomune = ? and codiceinterventoproc in (");
	for (int i = 0; i < elencoInterventi.size(); i++) {
	    sql.append("?,");
	}
	sql.deleteCharAt(sql.length() - 1).append(")");
	SQLQuery query = getSession().createSQLQuery(sql.toString()).addSynchronizedEntityClass(Istanze.class);
	int index = 0;
	query.setString(index, ORMHelper.getIdcomune());
	index++;
	for (Integer codice : elencoInterventi) {
	    query.setInteger(index, codice);
	    index++;
	}
	query.addScalar("codiceistanza", Hibernate.INTEGER);
	return new TreeSet<Integer>(query.list());
    }

    @Override
    public Class getEntityClass() {

	return null;
    }
}
