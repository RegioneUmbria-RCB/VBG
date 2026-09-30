package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AppIoServizi;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziId;

@Repository
public class AppIoServiziDAOImpl extends BaseDAOImpl<AppIoServizi, AppIoServiziId> implements IAppIoServiziDAO {

    @Override
    public Class<AppIoServizi> getEntityClass() {

	return AppIoServizi.class;
    }

    @Override
    public List<AppIoServizi> findAll(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
	    DAOOrderTypeEnum orderType) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "id.identificativoServizio", DAOOrderTypeEnum.ASC);
    }

    @Override
    public void insert(AppIoServizi entity) {

	super.insert(entity);
    }

    @Override
    public void updateServizio(String desc, String precIdServizio, String newIdServizio) {

	if (!precIdServizio.equalsIgnoreCase(newIdServizio)) {
	    AppIoServiziId id = new AppIoServiziId();
	    id.setIdcomune(ORMHelper.getIdcomune());
	    id.setIdentificativoServizio(newIdServizio);
	    AppIoServizi appIoServizi = new AppIoServizi();
	    appIoServizi.setId(id);
	    appIoServizi.setDescrizione(desc);
	    insert(appIoServizi);
	    String hql1 = "update AppIoServiziConfig p set p.id.identificativoServizio=? where p.id.identificativoServizio=? and p.id.idcomune=?";
	    getHibernateTemplate().bulkUpdate(hql1, new Object[] { newIdServizio, precIdServizio, ORMHelper.getIdcomune() });
	    String hql2 = "update AppIoParam p set p.id.identificativoServizio=? where p.id.identificativoServizio=? and p.id.idcomune=?";
	    getHibernateTemplate().bulkUpdate(hql2, new Object[] { newIdServizio, precIdServizio, ORMHelper.getIdcomune() });
	    String hql3 = "update AppIoServiziConfigParam p set p.id.identificativoServizio=? where p.id.identificativoServizio=? and p.id.idcomune=?";
	    getHibernateTemplate().bulkUpdate(hql3, new Object[] { newIdServizio, precIdServizio, ORMHelper.getIdcomune() });
	    String hql4 = "update TipimovimentoAppIoServizi p set p.id.identificativoServizio=? where p.id.identificativoServizio=? and p.id.idcomune=?";
	    getHibernateTemplate().bulkUpdate(hql4, new Object[] { newIdServizio, precIdServizio, ORMHelper.getIdcomune() });
	    String hql5 = "update AppIoCoda p set p.identificativoServizio=? where p.identificativoServizio=? and p.id.idcomune=?";
	    getHibernateTemplate().bulkUpdate(hql5, new Object[] { newIdServizio, precIdServizio, ORMHelper.getIdcomune() });
	    AppIoServiziId id2 = new AppIoServiziId();
	    id2.setIdcomune(ORMHelper.getIdcomune());
	    id2.setIdentificativoServizio(precIdServizio);
	    AppIoServizi appIoServizi2 = (AppIoServizi) findById(id2);
	    delete(appIoServizi2);
	}
    }
}
