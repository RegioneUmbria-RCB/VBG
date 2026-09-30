package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BollGestOneriSequence;
import it.gruppoinit.pal.gp.core.domain.BollGestOneriSequenceId;

@Repository
public class BollGestOneriSequenceDAOImpl extends BaseDAOImpl<BollGestOneriSequence, BollGestOneriSequenceId> implements BollGestOneriSequenceDAO {

    @Override
    public Class<BollGestOneriSequence> getEntityClass() {

	return BollGestOneriSequence.class;
    }

    @Override
    public void deleteByIdBollettazione(Integer idBollettazione) {

	if (idBollettazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo deleteByIdBollettazione senza passare il riferimento della testata della bollettazione");
	}
	String sql = "delete from boll_gest_oneri_sequence where idcomune = ? and fk_bollgest_id = ?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BollGestOneriSequence.class);
	int index = 0;
	q.setParameter(index, ORMHelper.getIdcomune(), new StringType());
	index++;
	q.setParameter(index, idBollettazione, new IntegerType());
	q.executeUpdate();
    }
}
