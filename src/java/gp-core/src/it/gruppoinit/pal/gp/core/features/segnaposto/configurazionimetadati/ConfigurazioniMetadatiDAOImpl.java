package it.gruppoinit.pal.gp.core.features.segnaposto.configurazionimetadati;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioniMetadati;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioniMetadatiId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class ConfigurazioniMetadatiDAOImpl extends BaseDAOImpl<ConfigurazioniMetadati, ConfigurazioniMetadatiId>
	implements IConfigurazioniMetadatiDAO {

    @Override
    public Class<ConfigurazioniMetadati> getEntityClass() {

	return ConfigurazioniMetadati.class;
    }

    @Override
    public <T /*extends HasPkId*/> void save(T entity) {

	_validateEntityForInsertOrUpdate2(entity);
	getHibernateTemplate().merge(entity);
    }

    @Override
    public List<ConfigurazioniMetadati> findByIdComuniAssociatiSoftware(Integer idComuniAssociatiSoftware) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idComuniAssociatiSoftware", idComuniAssociatiSoftware, Integer.class));
	//fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("categoria"));
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("valore"));
	return this.findByFilterTable(ft);
    }

    @Override
    public ConfigurazioniMetadati findByChiaveAndIdcomune(String chiave, boolean isCercaInTT) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.chiave", chiave, String.class));
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	if (isCercaInTT) {
	    fr.addFilterField(FilterUtils.equals("software", "TT", String.class));
	} else {
	    fr.addFilterField(FilterUtils.equals("software", ORMHelper.getSoftware(), String.class));
	}
	ft.addRestriction(fr);
	List<ConfigurazioniMetadati> findByFilterTable = this.findByFilterTable(ft);
	if (findByFilterTable.size() > 0) {
	    return findByFilterTable.get(0);
	} else {
	    return null;
	}
    }

    @Override
    public List<String> findByCategoireDistinct() {

	DetachedCriteria det = getIdcomuneCriteria();
	ProjectionList pl = Projections.projectionList();
	det.addOrder(Order.asc("categoria"));
	det.add(Restrictions.eq("software", ORMHelper.getSoftware()));
	pl.add(Projections.groupProperty("categoria"));
	det.setProjection(pl);
	return (List<String>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public int updateCategoria(String categoriaOrginale, String nuovaCategoria) {

	String hql = "update ConfigurazioniMetadati cm set cm.categoria=? where cm.software=? and upper(cm.categoria)=?";
	return getHibernateTemplate().bulkUpdate(hql, new Object[] { nuovaCategoria, ORMHelper.getSoftware(), categoriaOrginale });
    }
}
