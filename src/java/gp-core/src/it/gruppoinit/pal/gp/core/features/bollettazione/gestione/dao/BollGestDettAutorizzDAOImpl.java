package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BollGestDettAutorizz;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class BollGestDettAutorizzDAOImpl extends BaseDAOImpl<BollGestDettAutorizz, PkId> implements BollGestDettAutorizzDAO {

    private BollGestMercatiDettDAO bollGestMercatiDettDAO;

    @Autowired
    public BollGestDettAutorizzDAOImpl(BollGestMercatiDettDAO bollGestMercatiDettDAO) {

	this.bollGestMercatiDettDAO = bollGestMercatiDettDAO;
    }

    @Override
    public Class<BollGestDettAutorizz> getEntityClass() {

	return BollGestDettAutorizz.class;
    }

    @Override
    public void deleteBollGestAutorizzazioniByIdBollettazione(Integer idBollettazione) {

	if (idBollettazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo deleteByIdBollettazione senza passare il riferimento della testata della bollettazione");
	}
	String sql = "delete from boll_gest_dett_autorizz where idcomune = ? and fk_bollgest_id = ?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BollGestDettAutorizz.class);
	int index = 0;
	q.setParameter(index, ORMHelper.getIdcomune(), new StringType());
	index++;
	q.setParameter(index, idBollettazione, new IntegerType());
	q.executeUpdate();
    }

    @Override
    public void delete(BollGestDettAutorizz entity) {

	this.bollGestMercatiDettDAO.deleteBollGestMercatiDettByIdGestAutorizzazioni(entity.getId().getCodice());
	delete(entity);
    }
}
