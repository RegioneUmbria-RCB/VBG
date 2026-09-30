/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ClpermmenuDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Clpermmenu;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author riccardob
 * 
 */
@Repository
public class ClpermmenuDAOImpl extends BaseDAOImpl<Clpermmenu, PkId> implements ClpermmenuDAO {

    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    public Class<Clpermmenu> getEntityClass() {

	return Clpermmenu.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Clpermmenu> findSubMenu(String menuLink, String software, Integer codiceOperatore, boolean onlyOneSubLevel) {

	DetachedCriteria crit = getIdcomuneCriteria();
	DetachedCriteria respCrit = crit.createCriteria("responsabile");
	respCrit.add(Restrictions.eq("id.codice", codiceOperatore));
	DetachedCriteria softwareOrder = crit.createCriteria("software");
	if (!software.equals(WebConstants.SOFTWARE_TT)) {
	    softwareOrder.add(Restrictions.eq("codice", software));
	}
	DetachedCriteria softwareAttiviCriteria = softwareOrder.createCriteria("softwareattivis", DetachedCriteria.INNER_JOIN);
	softwareAttiviCriteria.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	DetachedCriteria softwareAbilitati = respCrit.createCriteria("softwareAbilitati", "_softwareabi", DetachedCriteria.INNER_JOIN);
	softwareAbilitati.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	softwareOrder.add(Restrictions.eqProperty("codice", "_softwareabi.id.software"));
	DetachedCriteria menuLinkOrder = crit.createCriteria("menu");
	if (onlyOneSubLevel) {
	    menuLinkOrder.add(Restrictions.ilike("menulink", menuLink + "_"));
	} else {
	    menuLinkOrder.add(Restrictions.ilike("menulink", menuLink + "%"));
	}
	if (!isEnterprise()) {
	    menuLinkOrder.add(Restrictions.or(Restrictions.ne("tipoFunzionalita", WebConstants.CLMENU_JAVA_TIPO_FUNZIONALITA_ENTERPRISE),
		    Restrictions.isNull("tipoFunzionalita")));
	}
	menuLinkOrder.addOrder(Order.asc("menulink"));
	softwareOrder.addOrder(Order.asc("ordine"));
	crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	List<Clpermmenu> lista = (List<Clpermmenu>) getHibernateTemplate().findByCriteria(crit);
	return lista;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Clpermmenu findByOperatoreAndClMenuAndSoftware(Integer codiceOperatore, Integer codiceClmenu, String codiceSoftware) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.createCriteria("responsabile").add(Restrictions.eq("id.codice", codiceOperatore));
	crit.createCriteria("menu").add(Restrictions.eq("id", codiceClmenu));
	crit.createCriteria("software").add(Restrictions.eq("codice", codiceSoftware));
	List<Clpermmenu> result = (List<Clpermmenu>) getHibernateTemplate().findByCriteria(crit);
	if (result.isEmpty()) {
	    return null;
	}
	return result.get(0);
    }

    private boolean isEnterprise() {

	return verticalizzazioniService.isInstallazioneEnterprise();
    }
}
