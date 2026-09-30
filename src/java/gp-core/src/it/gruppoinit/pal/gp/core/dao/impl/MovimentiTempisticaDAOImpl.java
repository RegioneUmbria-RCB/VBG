package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MovimentiTempisticaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.MovimentiTempistica;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.MovimentiTempisticaService;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class MovimentiTempisticaDAOImpl extends BaseDAOImpl<MovimentiTempistica, PkId> implements MovimentiTempisticaDAO {

    @Override
    public Class<MovimentiTempistica> getEntityClass() {

	return MovimentiTempistica.class;
    }

    @Override
    public List<MovimentiTempistica> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public int findDurataProrogaPerIstanza(Integer codiceIstanza) {

	DetachedCriteria crit = getIdcomuneCriteria();
	DetachedCriteria movimentoApertura = crit.createAlias("movimentoByFkApertura", "_movimentoByFkApertura");
	movimentoApertura.add(Restrictions.eq("_movimentoByFkApertura.istanzaId", codiceIstanza));
	crit.add(Restrictions.eq("evento", MovimentiTempisticaService.TIPO_EVENTO.P.toString()));
	Projection projection = Projections.sum("durata");
	crit.setProjection(projection);
	List<Integer> counts = getHibernateTemplate().findByCriteria(crit);
	if (counts != null) {
	    if (counts.size() > 0) {
		if (counts.get(0) != null) {
		    return counts.get(0).intValue();
		}
	    }
	}
	return 0;
    }

    @Override
    public Date findDataUltimaInterruzione(Integer codiceIstanza) {

	DetachedCriteria crit = getIdcomuneCriteria();
	DetachedCriteria movimentoApertura = crit.createAlias("movimentoByFkChiusura", "_movimentoByFkChiusura");
	movimentoApertura.add(Restrictions.eq("_movimentoByFkChiusura.istanzaId", codiceIstanza));
	movimentoApertura.add(Restrictions.isNotNull("_movimentoByFkChiusura.data"));
	crit.add(Restrictions.eq("evento", MovimentiTempisticaService.TIPO_EVENTO.I.toString()));
	crit.addOrder(Order.desc("_movimentoByFkChiusura.data"));
	crit.setProjection(Projections.property("_movimentoByFkChiusura.data"));
	List<Date> result = getHibernateTemplate().findByCriteria(crit, 0, 1);
	if (result != null) {
	    if (result.size() > 0) {
		return result.get(0);
	    }
	}
	return null;
    }
}
