package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoDomandeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti;
import it.gruppoinit.pal.gp.core.domain.FoMessaggi;
import it.gruppoinit.pal.gp.core.domain.FoSottoscrizioni;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoDomandeOggettiService;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;
import it.gruppoinit.pal.gp.core.service.FoMessaggiService;
import it.gruppoinit.pal.gp.core.service.FoSottoscrizioniService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class FoDomandeServiceImpl extends BaseServiceImpl<FoDomande, PkId> implements FoDomandeService {

    private FoDomandeDAO fodomandeDAO;
    private FoMessaggiService foMessaggiService;
    private FoSottoscrizioniService foSottoscrizioniService;
    private FoDomandeOggettiService foDomandeOggettiService;
    private OggettiService oggettiService;

    @Autowired
    public void setFoDomandeDAO(FoDomandeDAO fodomandeDAO) {

	this.fodomandeDAO = fodomandeDAO;
    }

    @Autowired
    public void setFoMessaggiService(FoMessaggiService foMessaggiService) {

	this.foMessaggiService = foMessaggiService;
    }

    @Autowired
    public void setFoSottoscrizioniService(FoSottoscrizioniService foSottoscrizioniService) {

	this.foSottoscrizioniService = foSottoscrizioniService;
    }

    @Autowired
    public void setFoDomandeOggettiService(FoDomandeOggettiService foDomandeOggettiService) {

	this.foDomandeOggettiService = foDomandeOggettiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<FoDomande> getEntityClass() {

	return FoDomande.class;
    }

    @Override
    public List<FoDomande> findAll(Integer firstResult, Integer maxResult) {

	return fodomandeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoDomande entity) {

	if (validateEntity(entity)) {
	    fodomandeDAO.insert(entity);
	}
    }

    @Override
    public FoDomande findById(PkId id) {

	return fodomandeDAO.findById(id);
    }

    @Override
    public void update(FoDomande entity) {

	if (validateEntity(entity)) {
	    fodomandeDAO.update(entity);
	}
    }

    @Override
    public void delete(FoDomande entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    fodomandeDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    protected boolean isDeleteAllowed(FoDomande entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<FoDomande> findByIdentificativodomanda(String identificativodomanda) {

	if (identificativodomanda == null) {
	    throw new IllegalArgumentException("findByIdentificativodomanda: il parametro identificativodomanda e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("identificativodomanda", identificativodomanda, String.class));
	filterTable.addRestriction(fr);
	return fodomandeDAO.findByFilterTable(filterTable);
    }

    @Override
    protected void childDelete(FoDomande entity) {

	Set<FoMessaggi> foMessaggis = entity.getFoMessaggis();
	for (FoMessaggi foMessaggi : foMessaggis) {
	    foMessaggiService.delete(foMessaggi);
	}
	Set<FoSottoscrizioni> foSottoscrizionis = entity.getFoSottoscrizionis();
	for (FoSottoscrizioni foSottoscrizioni : foSottoscrizionis) {
	    foSottoscrizioniService.delete(foSottoscrizioni);
	}
	Set<FoDomandeOggetti> foDomandeOggettis = entity.getFoDomandeOggettis();
	for (FoDomandeOggetti foDomandeOggetti : foDomandeOggettis) {
	    foDomandeOggettiService.delete(foDomandeOggetti);
	}
    }

    @Override
    public List<FoDomande> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return fodomandeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<FoDomande> findDomandeInSospeso(Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction flag_inviataRestriction = new FilterRestriction();
	flag_inviataRestriction.setAndOrRestriction(AndOrRestriction.OR);
	flag_inviataRestriction.addFilterField(FilterUtils.equals("flgPresentata", false, Boolean.class));
	flag_inviataRestriction.addFilterField(FilterUtils.isNull("flgPresentata"));
	filterTable.addRestriction(flag_inviataRestriction);
	FilterRestriction flgEliminataFr = new FilterRestriction();
	flgEliminataFr.setAndOrRestriction(AndOrRestriction.OR);
	flgEliminataFr.addFilterField(FilterUtils.equals("flgEliminata", false, Boolean.class));
	flgEliminataFr.addFilterField(FilterUtils.isNull("flgEliminata"));
	filterTable.addRestriction(flgEliminataFr);
	return fodomandeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }
}
