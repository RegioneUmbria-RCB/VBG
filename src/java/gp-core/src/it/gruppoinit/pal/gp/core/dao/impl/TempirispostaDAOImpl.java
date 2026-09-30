package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TempirispostaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Tempirisposta;
import it.gruppoinit.pal.gp.core.domain.TempirispostaId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class TempirispostaDAOImpl extends BaseDAOImpl<Tempirisposta, TempirispostaId> implements TempirispostaDAO {

    @Override
    public Class<Tempirisposta> getEntityClass() {

	return Tempirisposta.class;
    }

    @Override
    public List<Tempirisposta> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tempirisposta> findByFilter(Tempirisposta tempirisposta) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (EntityUtils.getNestedProperty(tempirisposta, "tipimovimento.id.tipomovimento") != null) {
	    criteria.add(Restrictions.eq("tipimovimento.id.tipomovimento", tempirisposta.getTipimovimento().getId().getTipomovimento()));
	}
	if (EntityUtils.getNestedProperty(tempirisposta, "tipicontromovimento.id.tipomovimento") != null) {
	    criteria.add(Restrictions.eq("tipicontromovimento.id.tipomovimento", tempirisposta.getTipicontromovimento().getId().getTipomovimento()));
	}
	if (EntityUtils.getNestedProperty(tempirisposta, "amministrazione.id.codice") != null) {
	    criteria.add(Restrictions.eq("amministrazione.id.codice", tempirisposta.getAmministrazione().getId().getCodice()));
	}
	if (EntityUtils.getNestedProperty(tempirisposta, "tipiprocedure.id.codice") != null) {
	    criteria.add(Restrictions.eq("tipiprocedure.id.codice", tempirisposta.getTipiprocedure().getId().getCodice()));
	}
	List<Tempirisposta> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list;
	} else {
	    return list = new ArrayList<Tempirisposta>();
	}
    }
}
