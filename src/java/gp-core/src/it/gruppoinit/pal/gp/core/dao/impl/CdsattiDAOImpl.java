package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.CdsattiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Cdsatti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CdsattiDTO;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class CdsattiDAOImpl extends BaseDAOImpl<Cdsatti, PkId> implements CdsattiDAO {

    @Override
    public Class<Cdsatti> getEntityClass() {

	return Cdsatti.class;
    }

    @Override
    public List<Cdsatti> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
	// return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY,
	// DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<CdsattiDTO> findDTOByCds(Integer idCds, Boolean soloConOggetti) {

	DetachedCriteria criteria = getCriteriaFindDTOByCdsOrIstanza(idCds, null, soloConOggetti);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(CdsattiDTO.class));
	List<CdsattiDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    private DetachedCriteria getCriteriaFindDTOByCdsOrIstanza(Integer idCds, Integer codiceIstanza, Boolean isCercaConOggetto) {

	if (codiceIstanza == null && idCds == null) {
	    throw new BusinessValidationException("Dati non corretti. Codice istanza e codice CDS nulli");
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("cds", "_cds", Criteria.INNER_JOIN);
	criteria.createAlias("oggetti", "_oggetti", DetachedCriteria.LEFT_JOIN);
	if (idCds != null) {
	    criteria.add(Restrictions.eq("_cds.id.codice", idCds));
	}
	if (codiceIstanza != null) {
	    criteria.add(Restrictions.eq("_cds.istanzeId", codiceIstanza));
	}
	//.Restriction per selezionare documento con o zenza oggetto
	//1. isCercaProcuraConOggetto==null : tutti
	//2. isCercaProcuraConOggetto==true : solo quelli con oggetto
	//3. isCercaProcuraConOggetto==false : solo quelli senza oggetto
	if (isCercaConOggetto != null) {
	    if (isCercaConOggetto == true)
		criteria.add(Restrictions.isNotNull("_oggetti.id.codice"));
	    else {
		criteria.add(Restrictions.isNull("_oggetti.id.codice"));
	    }
	}
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("id.idcomune"), "ID_IDCOMUNE");
	plist.add(Projections.property("data"), "DATA");
	plist.add(Projections.property("ora"), "ORA");
	plist.add(Projections.property("verbale"), "VERBALE");
	plist.add(Projections.property("note"), "NOTE");
	plist.add(Projections.property("_oggetti.id.codice"), "CODICEOGGETTO");
	plist.add(Projections.property("_oggetti.nomefile"), "NOMEFILE");
	plist.add(Projections.property("_oggetti.dimensioneFile"), "DIMENSIONEFILE");
	plist.add(Projections.property("dataconvocazione"), "DATACONVOCAZIONE");
	plist.add(Projections.property("oraconvocazione"), "ORACONVOCAZIONE");
	plist.add(Projections.property("dataconvocazione2"), "DATACONVOCAZIONE2");
	plist.add(Projections.property("oraconvocazione2"), "ORACONVOCAZIONE2");
	plist.add(Projections.property("chiusa"), "CHIUSA");
	plist.add(Projections.property("fileverbale"), "FILEVERBALE");
	plist.add(Projections.property("positivia"), "POSITIVIA");
	criteria.setProjection(plist);
	return criteria;
    }

    @Override
    public List<CdsattiDTO> findDTOByIstanza(Integer codiceIstanza, Boolean cercaOggetti) {

	DetachedCriteria criteria = getCriteriaFindDTOByCdsOrIstanza(null, codiceIstanza, cercaOggetti);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(CdsattiDTO.class));
	List<CdsattiDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }
}
