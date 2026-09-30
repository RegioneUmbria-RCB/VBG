package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlboCategorieDAO;
import it.gruppoinit.pal.gp.core.dao.AlboPubblicazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.AlboCategorie;
import it.gruppoinit.pal.gp.core.domain.AlboPretorioFilter;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AlboPubblicazioniDAOImpl extends BaseDAOImpl<AlboPubblicazioni, PkId> implements AlboPubblicazioniDAO {

    private AlboCategorieDAO alboCategorieDAO;

    @Autowired
    public void setAlboCategorieDAO(AlboCategorieDAO alboCategorieDAO) {

	this.alboCategorieDAO = alboCategorieDAO;
    }

    @Override
    public Class<AlboPubblicazioni> getEntityClass() {

	return AlboPubblicazioni.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlboPubblicazioni> findAllFilter(AlboPretorioFilter alboPretorioFilter) {

	// §§§BEGIN§§§
	List<AlboPubblicazioni> alboPubblicazionis = null;
	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.addOrder(Order.desc("validaAl"));
	if (alboPretorioFilter.getOggetto() != null && !alboPretorioFilter.getOggetto().equals("")) {
	    det.add(Restrictions.ilike("descrizione", alboPretorioFilter.getOggetto(), MatchMode.ANYWHERE));
	}
	if (alboPretorioFilter.getValidoDal() != null) {
	    det.add(Restrictions.ge("validaDal", alboPretorioFilter.getValidoDal()));
	}
	if (alboPretorioFilter.getValidoAl() != null) {
	    det.add(Restrictions.ge("validaAl", alboPretorioFilter.getValidoAl()));
	}
	if (alboPretorioFilter.getCodicecategoria() != null && alboPretorioFilter.getCodicecategoria() != 0) {
	    AlboCategorie alboCategorie = alboCategorieDAO.findById(new PkId(alboPretorioFilter.getCodicecategoria()));
	    det.add(Restrictions.eq("alboCategorie", alboCategorie));
	}
	if ((alboPretorioFilter.getDa() != null && 0 != alboPretorioFilter.getDa())
		&& (alboPretorioFilter.getA() != null && 0 != alboPretorioFilter.getA())) {
	    alboPubblicazionis = getHibernateTemplate().findByCriteria(det, alboPretorioFilter.getDa(), alboPretorioFilter.getA());
	} else {
	    alboPubblicazionis = getHibernateTemplate().findByCriteria(det);
	}
	return alboPubblicazionis;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<AlboPubblicazioni> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "dataPubblicazione", DAOOrderTypeEnum.DESC);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlboPubblicazioni> findPublicazioniValideAL(AlboPretorioFilter alboPretorioFilter) {

	// §§§BEGIN§§§
	List<AlboPubblicazioni> alboPubblicazionis = null;
	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.addOrder(Order.desc("dataPubblicazione"));
	if (alboPretorioFilter.getOggetto() != null && !alboPretorioFilter.getOggetto().equals("")) {
	    det.add(Restrictions.ilike("descrizione", alboPretorioFilter.getOggetto(), MatchMode.ANYWHERE));
	}
	if (alboPretorioFilter.getDataValidaAl() != null) {
	    det.add(Restrictions.not(Restrictions.gt("validaDal", alboPretorioFilter.getDataValidaAl())));
	    det.add(Restrictions.ge("validaAl", alboPretorioFilter.getDataValidaAl()));
	}
	if (alboPretorioFilter.getCodicecategoria() != null && alboPretorioFilter.getCodicecategoria() != 0) {
	    AlboCategorie alboCategorie = alboCategorieDAO.findById(new PkId(alboPretorioFilter.getCodicecategoria()));
	    det.add(Restrictions.eq("alboCategorie", alboCategorie));
	}
	alboPubblicazionis = getHibernateTemplate().findByCriteria(det);
	return alboPubblicazionis;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
