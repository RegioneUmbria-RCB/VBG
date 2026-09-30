package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentioneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class InventarioprocedimentioneriDAOImpl extends BaseDAOImpl<Inventarioprocedimentioneri, PkId> implements InventarioprocedimentioneriDAO {

    @Override
    public Class<Inventarioprocedimentioneri> getEntityClass() {

	return Inventarioprocedimentioneri.class;
    }

    @Override
    public List<Inventarioprocedimentioneri> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Inventarioprocedimentioneri> findOneriPerProcedimenti(String idComune, String codiceComuneGruppo, List<PkId> pkProcedimenti, boolean escludiDisattivi) {

	DetachedCriteria crit = this.getEmptyCriteriaForClass();
	//where idcomune = 
	crit.add(Restrictions.eq("id.idcomune", idComune));
	//where codice comune = valore o isnull
	if (StringUtils.isNotBlank(codiceComuneGruppo)) {
	    crit.add(Restrictions.eq("codiceComune", codiceComuneGruppo));
	} else {
	    crit.add(Restrictions.isNull("codiceComune"));
	}
	//where codiceinventario = pk1.codice and fk_invproc_idcomune = pk1.idcomune
	Disjunction orPkEquals = Restrictions.disjunction();
	for (PkId pk : pkProcedimenti) {
	    Criterion pkEquals = Restrictions.and(Restrictions.eq("inventarioprocedimentiId", pk.getCodice()),
		    Restrictions.eq("inventarioprocedimentiIdComune", pk.getIdcomune()));
	    orPkEquals.add(pkEquals);
	}
	crit.add(orPkEquals);
	//escludo le causali disattive
	crit.createAlias("tipicausalioneri", "causale");
	crit.add(Restrictions.ne("causale.coDisabilitato", Boolean.TRUE));
	if(escludiDisattivi){
	    crit.add(Restrictions.ne("flagDisattivo", Boolean.TRUE));
	}
	//order by idprocedimento e descrizione causale
	crit.addOrder(Order.asc("inventarioprocedimentiId"));
	crit.addOrder(Order.asc("causale.coDescrizione"));
	return getHibernateTemplate().findByCriteria(crit);
    }
}
