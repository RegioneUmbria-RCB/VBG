package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipigradtCfgRotazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipigradtCfgRotazione;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.TipigradtCfgRotazioneService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class TipigradtCfgRotazioneServiceImpl extends BaseServiceImpl<TipigradtCfgRotazione, PkId> implements TipigradtCfgRotazioneService {

    private TipigradtCfgRotazioneDAO tipigradtcfgrotazioneDAO;
    private Dyn2CampiService dyn2CampiService;

    @Autowired
    public void setTipigradtCfgRotazioneDAO(TipigradtCfgRotazioneDAO tipigradtcfgrotazioneDAO) {

	this.tipigradtcfgrotazioneDAO = tipigradtcfgrotazioneDAO;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Override
    protected Class<TipigradtCfgRotazione> getEntityClass() {

	return TipigradtCfgRotazione.class;
    }

    @Override
    public List<TipigradtCfgRotazione> findAll(Integer firstResult, Integer maxResult) {

	return tipigradtcfgrotazioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(TipigradtCfgRotazione entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    tipigradtcfgrotazioneDAO.insert(entity);
	}
    }

    private boolean isInsertAllowed(TipigradtCfgRotazione entity) {

	boolean isInsert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<TipigradtCfgRotazione> cfgRotaziones = this.findByTipigraduatoriet(entity.getTipigraduatoriet().getId().getCodice());
	if (!cfgRotaziones.isEmpty()) {
	    _ivs.add(new InvalidValue("service_error.config_piano_rotazione_esistente", null, null, null, null));
	}
	return isInsert;
    }

    @Override
    public TipigradtCfgRotazione findById(PkId id) {

	return tipigradtcfgrotazioneDAO.findById(id);
    }

    @Override
    public void update(TipigradtCfgRotazione entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    tipigradtcfgrotazioneDAO.update(entity);
	}
    }

    @Override
    public void delete(TipigradtCfgRotazione entity) {

	if (isDeleteAllowed(entity)) {
	    tipigradtcfgrotazioneDAO.delete(entity);
	}
    }

    @Override
    public List<TipigradtCfgRotazione> findByTipigraduatoriet(Integer codice) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "tipigraduatoriet", Integer.class));
	filterTable.addRestriction(fr);
	return tipigradtcfgrotazioneDAO.findByFilterTable(filterTable);
    }

    private void dataIntegration(TipigradtCfgRotazione entity) {

	if (entity == null) {
	    throw new RuntimeException("Non si può inserire/aggiornare tipo Graduatorit Configurazioni Rotazione nulla");
	}
	if (entity.getFlagMultiplo() == null) {
	    entity.setFlagMultiplo(false);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(TipigradtCfgRotazione entity) {

	Dyn2Campi campiMercatoUso = dyn2CampiService.bindDomainObject(entity.getCampiMercatoUso(), PkId.class, "id.codice");
	entity.setCampiMercatoUso(campiMercatoUso);
	Dyn2Campi campiPosteggio = dyn2CampiService.bindDomainObject(entity.getCampiPosteggio(), PkId.class, "id.codice");
	entity.setCampiPosteggio(campiPosteggio);
	Dyn2Campi campiOrdine = dyn2CampiService.bindDomainObject(entity.getCampiOrdine(), PkId.class, "id.codice");
	entity.setCampiOrdine(campiOrdine);
    }
    //    protected boolean isDeleteAllowed(TipigradtCfgRotazione entity) {
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
}
