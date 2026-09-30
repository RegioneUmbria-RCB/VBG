/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatiConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivita;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.RangeRateizzazioni;
import it.gruppoinit.pal.gp.core.service.MercatiCfgAttivitaService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.RangeRateizzazioniService;

/**
 * @author lucap
 * 
 */
@Service
public class MercatiConfigurazioneServiceImpl extends BaseServiceImpl<MercatiConfigurazione, MercatiConfigurazioneId>
	implements MercatiConfigurazioneService {

    private MercatiConfigurazioneDAO mercatiConfigurazioneDAO;
    private MercatiCfgAttivitaService mercatiCfgAttivitaService;
    private RangeRateizzazioniService rangeRateizzazioniService;
    private MercatiService mercatiService;

    @Autowired
    public void setMercatiConfigurazioneDAO(MercatiConfigurazioneDAO mercatiConfigurazioneDAO) {

	this.mercatiConfigurazioneDAO = mercatiConfigurazioneDAO;
    }

    @Autowired
    public void setMercatiCfgAttivitaService(MercatiCfgAttivitaService mercatiCfgAttivitaService) {

	this.mercatiCfgAttivitaService = mercatiCfgAttivitaService;
    }

    @Autowired
    public void setRangeRateizzazioniService(RangeRateizzazioniService rangeRateizzazioniService) {

	this.rangeRateizzazioniService = rangeRateizzazioniService;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Override
    protected Class<MercatiConfigurazione> getEntityClass() {

	return MercatiConfigurazione.class;
    }

    @Override
    public void delete(MercatiConfigurazione entity) {

	if (isDeleteAllowed(entity)) {
	    mercatiConfigurazioneDAO.delete(entity);
	    List<MercatiCfgAttivita> mercatiCfgAttivitaList = mercatiCfgAttivitaService.findAll(null, null);
	    for (MercatiCfgAttivita mercatiCfgAttivita : mercatiCfgAttivitaList) {
		mercatiCfgAttivitaService.delete(mercatiCfgAttivita);
	    }
	    List<RangeRateizzazioni> listrateizzazioni = rangeRateizzazioniService.findAll(null, null);
	    for (RangeRateizzazioni rangeRateizzazioni : listrateizzazioni) {
		rangeRateizzazioniService.delete(rangeRateizzazioni);
	    }
	}
    }

    @Override
    public List<MercatiConfigurazione> findAll(Integer firstResult, Integer maxResult) {

	return mercatiConfigurazioneDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, null);
    }

    @Override
    public MercatiConfigurazione findById(MercatiConfigurazioneId id) {

	return mercatiConfigurazioneDAO.findById(id);
    }

    @Override
    public void insert(MercatiConfigurazione entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatiConfigurazioneDAO.insert(entity);
	}
    }

    private void dataIntegration(MercatiConfigurazione entity) {

	if (entity.getFlagAttivaGradSpuntisti() == null) {
	    entity.setFlagAttivaGradSpuntisti(0);
	}
    }

    @Override
    public void update(MercatiConfigurazione entity) {

	if (validateEntity(entity)) {
	    mercatiConfigurazioneDAO.update(entity);
	}
    }

    protected boolean isDeleteAllowed(MercatiConfigurazione entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	List<Mercati> mercati = mercatiService.findAll(null, null);
	if (!mercati.isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATI", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public MercatiConfigurazione findConfigurazione() {

	//	MercatiConfigurazioneId id = new MercatiConfigurazioneId(softwareService.findById(ORMHelper.getSoftware()).getCodice());
	//	MercatiConfigurazione mercatiConfigurazione = this.findById(id);
	//	return mercatiConfigurazione;
	return mercatiConfigurazioneDAO.findConfigurazione();
    }

    @Override
    public boolean isAttivaConfigurazioneSpuntista() {

	MercatiConfigurazione mercatiConfigurazione = this.findConfigurazione();
	return MercatiConfigurazione.checkConfigurazioneGradSpuntistiPerManifestazioni(mercatiConfigurazione);
    }

    @Override
    public boolean isAttivaConfigurazioneGGAssenzaMassima() {

	MercatiConfigurazione mercatiConfigurazione = this.findConfigurazione();
	boolean mostraSpuntistiDaDisabilitare = false;
	if (mercatiConfigurazione != null) {
	    if (mercatiConfigurazione.getGgAssenza() != null && mercatiConfigurazione.getGgAssenza() > 0) {
		mostraSpuntistiDaDisabilitare = true;
	    }
	}
	return mostraSpuntistiDaDisabilitare;
    }

    @Override
    public Integer getGGAssenzaMassima() {

	MercatiConfigurazione mercatiConfigurazione = this.findConfigurazione();
	Integer ggAssenzaPermssi = 0;
	if (mercatiConfigurazione != null) {
	    if (mercatiConfigurazione.getGgAssenza() != null && mercatiConfigurazione.getGgAssenza() > 0) {
		ggAssenzaPermssi = mercatiConfigurazione.getGgAssenza();
	    }
	}
	return ggAssenzaPermssi;
    }
}
