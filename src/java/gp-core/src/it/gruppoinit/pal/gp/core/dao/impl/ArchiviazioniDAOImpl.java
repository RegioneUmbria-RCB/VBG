package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ArchiviazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Archiviazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterOrder;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author fabrizioc
 */
@Repository
public class ArchiviazioniDAOImpl extends BaseDAOImpl<Archiviazioni, PkId> implements ArchiviazioniDAO {

    public static final Logger log = LoggerFactory.getLogger(ArchiviazioniDAOImpl.class);

    @Override
    public Class<Archiviazioni> getEntityClass() {

	return Archiviazioni.class;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<Archiviazioni> findAll(Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterOrder orderByData = FilterUtils.orderDesc("data");
	FilterOrder orderById = FilterUtils.orderDesc("id");
	ft.addOrder(orderByData);
	ft.addOrder(orderById);
	return this.findByFilterTable(ft);
    }

    @Override
    public List<Archiviazioni> findAll(Integer firstResult, Integer maxResult, Boolean isSoloConErrori) {

	boolean b = BooleanUtils.toBoolean(isSoloConErrori);
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (isSoloConErrori) {
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("corretto", false, Boolean.class));
	    ft.addRestriction(fr);
	}
	FilterOrder orderByData = FilterUtils.orderDesc("data");
	FilterOrder orderById = FilterUtils.orderDesc("id");
	ft.addOrder(orderByData);
	ft.addOrder(orderById);
	return this.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public void delete(Archiviazioni entity) {

	String deleteArchOggettiHql = "delete ArchiviazioniOggetti ag where ag.archiviazioniId = ? and ag.id.idcomune = ?";
	String deleteArchIstanzeHql = "delete ArchiviazioniIstanze ai where ai.archiviazioniId = ? and ai.id.idcomune = ?";
	String deleteArchHql = "delete Archiviazioni a where a.id.codice = ? and a.id.idcomune = ?";
	Integer codiceArch = entity.getId().getCodice();
	String idcomune = entity.getId().getIdcomune();
	//Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	//Query query = s.createQuery(deleteArchOggettiHql);
	//query.setInteger("archId", codiceArch);
	//query.setString("idcomune", entity.getId().getIdcomune());
	//int rowCount = query.executeUpdate();
	int rowCount = getHibernateTemplate().bulkUpdate(deleteArchOggettiHql, new Object[] { codiceArch, idcomune });
	log.debug("delete: ArchiviazioniOggetti archiviazioniId={}, rowCount={}", codiceArch, rowCount);
	//query = s.createQuery(deleteArchIstanzeHql);
	//query.setInteger("archId", codiceArch);
	//query.setString("idcomune", entity.getId().getIdcomune());
	//rowCount = query.executeUpdate();
	rowCount = getHibernateTemplate().bulkUpdate(deleteArchIstanzeHql, new Object[] { codiceArch, idcomune });
	log.debug("delete: ArchiviazioniIstanze archiviazioniId={}, rowCount={}", codiceArch, rowCount);
	//query = s.createQuery(deleteArchHql);
	//query.setInteger("archId", codiceArch);
	//query.setString("idcomune", entity.getId().getIdcomune());
	//rowCount = query.executeUpdate();
	rowCount = getHibernateTemplate().bulkUpdate(deleteArchHql, new Object[] { codiceArch, idcomune });
	log.debug("delete: Archiviazioni archiviazioniId={}, rowCount={}", codiceArch, rowCount);
    }
}
