package it.gruppoinit.pal.gp.core.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import it.gruppoinit.pal.gp.core.dao.SegnapostoDAO;
import it.gruppoinit.pal.gp.core.domain.Segnaposto;


public class SegnapostoDAOImpl extends BaseDAOImpl<Segnaposto, Integer> implements SegnapostoDAO {

    @Override
    public Class<Segnaposto> getEntityClass() {

	// TODO Auto-generated method stub
	return Segnaposto.class;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.dao.SegnapostoDAO#findByTag(java.lang.String)
     */
    @Override
    public Segnaposto findByTag(String tagContent) {

	Segnaposto retVal = null;
	if(tagContent != null && tagContent.length() > 0){
	    int braceIndex = tagContent.indexOf('(');
	    DetachedCriteria criterion = getEmptyCriteriaForClass();
	    //gestion tag parametrici del tipo [-TAG(codice)-]
	    if(braceIndex > -1){
		tagContent = tagContent.substring(0, braceIndex+1);
		criterion.add(Restrictions.ilike("tag", tagContent, MatchMode.START));
	    }
	    else{
		//TODO gestire i campi dinamici in cui $$$ è sostituito con un codice dinamico
		criterion.add(Restrictions.ilike("tag", tagContent, MatchMode.EXACT));
	    }
	    List<Segnaposto> results = getHibernateTemplate().findByCriteria(criterion);
	    if(results.size() > 1){
		String msg = MessageFormat.format("Errore: più di un segnaposto corrispondono al tag {0} ", new Object[]{tagContent});
		//TODO verificare quale eccezione è più opportuno generare
		throw new RuntimeException(msg);
	    }
	    else if(results.size() == 1){
		retVal = results.get(0);
	    }
	}
	return retVal;
    }
    
    
}
