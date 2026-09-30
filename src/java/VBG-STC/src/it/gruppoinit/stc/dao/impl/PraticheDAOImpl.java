package it.gruppoinit.stc.dao.impl;

import it.gruppoinit.stc.dao.PraticheDAO;
import it.gruppoinit.stc.domain.Pratiche;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PraticheDAOImpl extends BaseDAOImpl<Pratiche, Integer> implements PraticheDAO {

    @Override
    public Class<Pratiche> getEntityClass() {
	return Pratiche.class;
    }

    /**
     * ricerca la pratica nella tabella PRATICHE con chiave idnodo,idente,idsportello,idpratica
     * 
     * @param example
     *            oggetto filtro utilizzato per popolare i campi idnodo,idente,idsportello,idpratica
     */
    @SuppressWarnings("unchecked")
    @Override
    public Pratiche findByUniqueKey(Pratiche example) {
	DetachedCriteria criteria = getDefaultCriteria();
	criteria.add(Restrictions.eq("configurazioneByFkidnodo.idnodo", example.getConfigurazioneByFkidnodo().getIdnodo()));
	criteria.add(Restrictions.eq("idente", example.getIdente()));
	criteria.add(Restrictions.eq("idsportello", example.getIdsportello()));
	criteria.add(Restrictions.eq("idpratica", example.getIdpratica()));
	List<Pratiche> list = this.getHibernateTemplate().findByCriteria(criteria);
	return list.isEmpty() ? null : list.get(0);
    }

}
