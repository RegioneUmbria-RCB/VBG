/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OggettiinfoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAORestrictionMode;
import it.gruppoinit.pal.gp.core.domain.Oggettiinfo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class OggettiinfoDAOImpl extends BaseDAOImpl<Oggettiinfo, PkId> implements OggettiinfoDAO {

    @Override
    public Class<Oggettiinfo> getEntityClass() {

	return Oggettiinfo.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Oggettiinfo> findByDescrizioneAndTipologia(String descrizione, Integer tipologia) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	String[] properties = { "descrizione", "tipologieoggetto.id.codice" };
	Object[] values = new Object[2];
	values[0] = descrizione;
	values[1] = tipologia;
	MatchMode[] modes = new MatchMode[2];
	modes[0] = MatchMode.ANYWHERE;
	modes[1] = MatchMode.ANYWHERE;
	Criterion crit = getCriterionForObjects(properties, values, modes, DAORestrictionMode.AND);
	criteria.add(crit);
	List<Oggettiinfo> list = (List<Oggettiinfo>) getHibernateTemplate().findByCriteria(criteria);
	return list;
    }
}
