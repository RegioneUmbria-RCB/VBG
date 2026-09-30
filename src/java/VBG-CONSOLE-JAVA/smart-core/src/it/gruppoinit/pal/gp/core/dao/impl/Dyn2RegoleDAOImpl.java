/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2RegoleDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francol
 * 
 */
@Repository
public class Dyn2RegoleDAOImpl extends BaseDAOImpl<Dyn2Regole, PkId> implements Dyn2RegoleDAO {

    public Dyn2RegoleDAOImpl() {

    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl#getEntityClass()
     */
    @Override
    public Class<Dyn2Regole> getEntityClass() {

	return Dyn2Regole.class;
    }

    @Override
    public List<Dyn2Regole> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Dyn2Regole> findByCampoDinamicoInModello(Integer idCampo, Integer idModellot) {

	DetachedCriteria critRegole = getIdcomunebaseAndSoftwareCriteria();
	DetachedCriteria critExpr = critRegole.createCriteria("dyn2Espressionis", DetachedCriteria.INNER_JOIN);
	//critExpr.add(Restrictions.eq("dyn2Campi.id.codice", idCampo));
	DetachedCriteria campiCriteria = critExpr.createCriteria("dyn2Campi", DetachedCriteria.INNER_JOIN);
	campiCriteria.add(Restrictions.eq("id.codice", idCampo));
	DetachedCriteria d2mdCriteria = campiCriteria.createCriteria("dyn2Modellids", DetachedCriteria.INNER_JOIN);
	d2mdCriteria.add(Restrictions.eq("dyn2Modellit.id.codice", idModellot));
	List<Dyn2Regole> regole = getHibernateTemplate().findByCriteria(critRegole);
	return regole;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Dyn2Regole> findByDescrizioneAndSoftware(String textToSearch, String codicesoftware) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(codicesoftware)) {
	    criteria.add(Restrictions.eq("software.codice", codicesoftware));
	} else {
	    criteria.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	}
	if (StringUtils.isNotBlank(textToSearch)) {
	    try {
		criteria.add(Restrictions.eq("id.codice", Integer.parseInt(textToSearch.replaceAll("%", ""))));
	    } catch (Exception e) {
		criteria.add(Restrictions.ilike("descrizione", textToSearch, MatchMode.ANYWHERE));
	    }
	}
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
