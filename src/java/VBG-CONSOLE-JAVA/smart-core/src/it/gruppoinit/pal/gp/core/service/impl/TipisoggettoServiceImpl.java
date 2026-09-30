package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipisoggettoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisoggetto;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RiCariche;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocTipisoggettoService;
import it.gruppoinit.pal.gp.core.service.RiCaricheService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.init.sigepro.rte.types.RuoloType;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Riccardo Bocci
 */
@Service
public class TipisoggettoServiceImpl extends BaseServiceImpl<Tipisoggetto, PkId> implements TipisoggettoService {

    private static final Logger log = LoggerFactory.getLogger(TipisoggettoServiceImpl.class);
    private TipisoggettoDAO tipisoggettoDAO;
    private RiCaricheService riCaricheService;
    private AlberoprocTipisoggettoService alberoprocTipisoggettoService;

    @Autowired
    public void setAlberoprocTipisoggettoService(AlberoprocTipisoggettoService alberoprocTipisoggettoService) {

	this.alberoprocTipisoggettoService = alberoprocTipisoggettoService;
    }

    @Autowired
    public void setTipisoggettoDAO(TipisoggettoDAO tipisoggettoDAO) {

	this.tipisoggettoDAO = tipisoggettoDAO;
    }

    @Autowired
    public void setRiCaricheService(RiCaricheService riCaricheService) {

	this.riCaricheService = riCaricheService;
    }

    @Override
    protected Class<Tipisoggetto> getEntityClass() {

	return Tipisoggetto.class;
    }

    @Override
    public List<Tipisoggetto> findAll(Integer firstResult, Integer maxResult) {

	return tipisoggettoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipisoggetto entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipisoggettoDAO.insert(entity);
	}
    }

    @Override
    public Tipisoggetto findById(PkId id) {

	return tipisoggettoDAO.findById(id);
    }

    @Override
    public void update(Tipisoggetto entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipisoggettoDAO.update(entity);
	}
    }

    private void dataIntegration(Tipisoggetto entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Tipologia soggetto nulla");
	}
	if (entity.getFlagqualita() == null) {
	    entity.setFlagqualita(Boolean.FALSE);
	}
	if (entity.getFlgSpecificadescrizione() == null) {
	    entity.setFlagqualita(Boolean.FALSE);
	}
	if (entity.getFlgDatialbo() == null) {
	    entity.setFlgDatialbo(Boolean.FALSE);
	}
	if (entity.getFoObbligatorio() == null) {
	    entity.setFoObbligatorio(Boolean.FALSE);
	}
	if (entity.getRichiedianagrafecoll() == null) {
	    entity.setRichiedianagrafecoll(Boolean.FALSE);
	}
	if (entity.getFlgLegalerap() == null) {
	    entity.setFlgLegalerap(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Tipisoggetto entity) {

	RiCariche ric = riCaricheService.bindDomainObject(entity.getRiCariche(), String.class, "codice");
	entity.setRiCariche(ric);
    }

    @Override
    public void delete(Tipisoggetto entity) {

	if (isDeleteAllowed(entity)) {
	    tipisoggettoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Tipisoggetto entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<AlberoprocTipisoggetto> alberoprocTipisoggettos = alberoprocTipisoggettoService.findByTipiSoggettoId(entity.getId().getCodice(), 0, 2);
	if (!alberoprocTipisoggettos.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBEROPROC_TIPISOGGETTO", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Tipisoggetto> findByFilterTable(FilterTable filterTable) {

	return tipisoggettoDAO.findByFilterTable(filterTable);
    }

    @Override
    public Tipisoggetto findByRuoloType(RuoloType ruoloType, String codiceSoftware) throws BusinessValidationException {

	Integer codiceTipoSoggetto = null;
	if (ruoloType != null) {
	    String descrizione = ruoloType.getRuolo();
	    if (StringUtils.isBlank(codiceSoftware)) {
		log.error("findByRuoloType: Il software passato è nullo");
		throw new RuntimeException("Il software passato è nullo");
	    }
	    if (StringUtils.isNotBlank(ruoloType.getIdRuolo())) {
		if (Utilities.isInteger(ruoloType.getIdRuolo().trim())) {
		    try {
			codiceTipoSoggetto = Integer.parseInt(ruoloType.getIdRuolo().trim());
		    } catch (NumberFormatException e) {
			log.error("findByRuoloType: ruoloType.getIdRuolo() = '{}' non è un numero", ruoloType.getIdRuolo());
		    }
		}
	    }
	    if (codiceTipoSoggetto != null || StringUtils.isNotBlank(descrizione)) {
		// 1. cerca TS per codice e Software
		FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		FilterRestriction softwareR = new FilterRestriction();
		softwareR.addFilterField(FilterUtils.equals("software.codice", codiceSoftware, String.class));
		ft.addRestriction(softwareR);
		FilterRestriction codiceR = new FilterRestriction();
		codiceR.addFilterField(FilterUtils.equals("id.codice", codiceTipoSoggetto, Integer.class));
		ft.addRestriction(codiceR);
		List<Tipisoggetto> tss = tipisoggettoDAO.findByFilterTable(ft);
		if (!tss.isEmpty()) {
		    return tss.get(0);
		}
		// 2. cerca TS usando descrizione e software
		ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		softwareR = new FilterRestriction();
		softwareR.addFilterField(FilterUtils.equals("software.codice", codiceSoftware, String.class));
		ft.addRestriction(softwareR);
		FilterRestriction descrizioneR = new FilterRestriction();
		descrizioneR.addFilterField(FilterUtils.equalsIgnoreCase("tiposoggetto", descrizione));
		ft.addRestriction(descrizioneR);
		tss = tipisoggettoDAO.findByFilterTable(ft);
		if (!tss.isEmpty()) {
		    return tss.get(0);
		}
		// 3. cerca TS per codice
		ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		codiceR = new FilterRestriction();
		codiceR.addFilterField(FilterUtils.equals("id.codice", codiceTipoSoggetto, Integer.class));
		ft.addRestriction(codiceR);
		tss = tipisoggettoDAO.findByFilterTable(ft);
		if (!tss.isEmpty()) {
		    return tss.get(0);
		}
		// 4. cerca TS usando descrizione
		ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		descrizioneR = new FilterRestriction();
		descrizioneR.addFilterField(FilterUtils.equalsIgnoreCase("tiposoggetto", descrizione));
		ft.addRestriction(descrizioneR);
		tss = tipisoggettoDAO.findByFilterTable(ft);
		if (!tss.isEmpty()) {
		    return tss.get(0);
		}
	    }
	    // 5. se lookup fallisce allora rilancia eccezione
	    // non è stato possibile ricavare il tipo soggetto	
	    log.error("findByRuoloType: Non è stato possibile ricavare il tipoSoggetto id={}, descrizione={}", ruoloType.getIdRuolo(),
		    ruoloType.getRuolo());
	    throw new BusinessValidationException("Non è stato possibile ricavare il tipoSoggetto id=" + ruoloType.getIdRuolo() + ", descrizione="
		    + ruoloType.getRuolo() + ", (rif:tipisoggetto).");
	}
	return null;
    }
}
