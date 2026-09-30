package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BollGestIstanzeoneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * 
 * @author
 */
@Repository
public class BollGestIstanzeoneriDAOImpl extends BaseDAOImpl<BollGestIstanzeoneri, PkId> implements BollGestIstanzeoneriDAO {

    @Override
    public Class<BollGestIstanzeoneri> getEntityClass() {

	return BollGestIstanzeoneri.class;
    }

    @Override
    public List<BollGestIstanzeoneri> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public List<BollGestIstanzeoneri> findByBollGestDett(Integer codiceBollGestDett, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("bollGestDettaglioId", codiceBollGestDett, Integer.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }

    @Override
    public void deleteByIdBollettazione(Integer idBollettazione) {

	if (idBollettazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo deleteByIdBollettazione senza passare il riferimento della testata della bollettazione");
	}
	String sql = "delete from boll_gest_istanzeoneri where idcomune = ? and fk_bollgest_id = ?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BollGestIstanzeoneri.class);
	int index = 0;
	q.setParameter(index, ORMHelper.getIdcomune(), new StringType());
	index++;
	q.setParameter(index, idBollettazione, new IntegerType());
	q.executeUpdate();
    }
}
