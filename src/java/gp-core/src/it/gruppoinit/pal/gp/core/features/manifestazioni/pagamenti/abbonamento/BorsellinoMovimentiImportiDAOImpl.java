package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimentiImporti;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class BorsellinoMovimentiImportiDAOImpl extends BaseDAOImpl<BorsellinoMovimentiImporti, PkId> implements IBorsellinoMovimentiImportiDAO {

    @Override
    public Class<BorsellinoMovimentiImporti> getEntityClass() {

	return BorsellinoMovimentiImporti.class;
    }

    @Override
    public void deleteByIdMovimento(Integer codiceMovimento) {

	if (codiceMovimento == null) {
	    throw new IllegalArgumentException(
		    "Impossibile cancellare i dati di BorsellinoMovimentiImporti senza passare il codiceMovimento di riferimento");
	}
	String sql = "delete from borsellino_movimenti_importi where idcomune = ? and fkid_borsellinomovimenti = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BorsellinoMovimentiImporti.class)
		.addSynchronizedEntityClass(BorsellinoMovimenti.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codiceMovimento);
	query.executeUpdate();
	flush();
    }
}