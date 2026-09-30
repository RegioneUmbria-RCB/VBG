package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipisoggettopeopleDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipisoggettopeople;
import it.gruppoinit.pal.gp.core.domain.TipisoggettopeopleId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.TipisoggettopeopleService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.init.sigepro.rte.types.RuoloType;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class TipisoggettopeopleServiceImpl extends BaseServiceImpl<Tipisoggettopeople, TipisoggettopeopleId> implements TipisoggettopeopleService {

    Logger log = LoggerFactory.getLogger(TipisoggettopeopleServiceImpl.class);
    private TipisoggettopeopleDAO tipisoggettopeopleDAO;

    @Autowired
    public void setTipisoggettopeopleDAO(TipisoggettopeopleDAO tipisoggettopeopleDAO) {

	this.tipisoggettopeopleDAO = tipisoggettopeopleDAO;
    }

    @Override
    protected Class<Tipisoggettopeople> getEntityClass() {

	return Tipisoggettopeople.class;
    }

    @Override
    public List<Tipisoggettopeople> findAll(Integer firstResult, Integer maxResult) {

	return tipisoggettopeopleDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipisoggettopeople entity) {

	if (validateEntity(entity)) {
	    tipisoggettopeopleDAO.insert(entity);
	}
    }

    @Override
    public Tipisoggettopeople findById(TipisoggettopeopleId id) {

	return tipisoggettopeopleDAO.findById(id);
    }

    @Override
    public void update(Tipisoggettopeople entity) {

	if (validateEntity(entity)) {
	    tipisoggettopeopleDAO.update(entity);
	}
    }

    @Override
    public void delete(Tipisoggettopeople entity) {

	if (isDeleteAllowed(entity)) {
	    tipisoggettopeopleDAO.delete(entity);
	}
    }

    //    protected boolean isDeleteAllowed(Tipisoggettopeople entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
    @Override
    public Tipisoggetto findByRuoloType(RuoloType ruoloType, String codiceSoftware) throws BusinessValidationException {

	if (ruoloType != null) {
	    if (StringUtils.isBlank(codiceSoftware)) {
		log.error("findByRuoloType: Il software passato è nullo");
		throw new RuntimeException("Il software passato è nullo");
	    }
	    if (StringUtils.isNotBlank(ruoloType.getIdRuolo())) {
		log.debug("findByRuoloType: cerco il tiposoggetto con tiporapprpeople={}", ruoloType.getIdRuolo());
		// 1. cerca TS per codice e Software
		FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		FilterRestriction idFf = new FilterRestriction();
		idFf.addFilterField(FilterUtils.equals("id.software", codiceSoftware, String.class));
		idFf.addFilterField(FilterUtils.equals("id.tiporapprpeople", ruoloType.getIdRuolo(), String.class));
		ft.addRestriction(idFf);
		List<Tipisoggettopeople> tss = tipisoggettopeopleDAO.findByFilterTable(ft);
		if (!tss.isEmpty()) {
		    return tss.get(0).getTipisoggetto();
		}
		// 3. cerca TS per codice
		ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		idFf = new FilterRestriction();
		idFf.addFilterField(FilterUtils.equals("id.tiporapprpeople", ruoloType.getIdRuolo(), String.class));
		ft.addRestriction(idFf);
		tss = tipisoggettopeopleDAO.findByFilterTable(ft);
		if (!tss.isEmpty()) {
		    return tss.get(0).getTipisoggetto();
		}
	    }
	    // 5. se lookup fallisce allora rilancia eccezione
	    // non è stato possibile ricavare il tipo soggetto dai dati passati	
	    log.error("findByRuoloType: Non è stato possibile ricavare il tipoSoggetto id={}, descrizione={}", ruoloType.getIdRuolo(),
		    ruoloType.getRuolo());
	    throw new BusinessValidationException(
		    "Non è stato possibile ricavare il tipoSoggetto con id=" + ruoloType.getIdRuolo() + ", (Rif:Tipisoggettopeople). ");
	}
	return null;
    }

    @Override
    public boolean existsByTipoSoggettoAndMappatura(Integer codiceTipoSoggetto, String mappaturaDI) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipisoggettoId", codiceTipoSoggetto, Integer.class));
	fr.addFilterField(FilterUtils.equals("id.tiporapprpeople", mappaturaDI, String.class));
	ft.addRestriction(fr);
	return tipisoggettopeopleDAO.existsRecords(ft);
    }
}
