package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.CommedilizieTipopareriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioniEdiliziePareriMovimentiModel;
import it.gruppoinit.pal.gp.core.features.commissioni.model.EsitoOperazioneDML;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipopareriService;

/**
 * 
 * @author
 */
@Service
public class CommedilizieTipopareriServiceImpl extends BaseServiceImpl<CommedilizieTipopareri, PkId> implements CommedilizieTipopareriService {

    private CommedilizieTipopareriDAO commedilizietipopareriDAO;

    @Autowired
    public void setCommedilizieTipopareriDAO(CommedilizieTipopareriDAO commedilizietipopareriDAO) {

	this.commedilizietipopareriDAO = commedilizietipopareriDAO;
    }

    @Override
    protected Class<CommedilizieTipopareri> getEntityClass() {

	return CommedilizieTipopareri.class;
    }

    @Override
    public List<CommedilizieTipopareri> findAll(Integer firstResult, Integer maxResult) {

	return commedilizietipopareriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CommedilizieTipopareri entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    commedilizietipopareriDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public CommedilizieTipopareri findById(PkId id) {

	// §§§BEGIN§§§
	return commedilizietipopareriDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(CommedilizieTipopareri entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    commedilizietipopareriDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(CommedilizieTipopareri entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    commedilizietipopareriDAO.delete(entity);
	}
	// §§§END§§§
    }

    @Override
    protected void childDelete(CommedilizieTipopareri entity) {

	commedilizietipopareriDAO.deleteMovimentiConfigurati(entity.getId().getCodice());
    }

    protected boolean isDeleteAllowed(CommedilizieTipopareri entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getCommissioniedilizieRs().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "COMMISSIONIEDILIZIE_R", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<CommissioniEdiliziePareriMovimentiModel> findMovimentiConfigurati(Integer codiceTipologiaParere) {

	return commedilizietipopareriDAO.findMovimentiConfigurati(codiceTipologiaParere);
    }

    @Override
    public EsitoOperazioneDML insertMovimentoPerSoftware(Integer codice, String software, String tipomovimento) {

	EsitoOperazioneDML ret = new EsitoOperazioneDML();
	if (commedilizietipopareriDAO.findConfigurazioniPerSoftware(codice, software)) {
	    ret.setEsito(false);
	    ret.setErrore("Esiste gia' una configurazione per il software " + software);
	    return ret;
	}
	commedilizietipopareriDAO.insertMovimentoPerSoftware(codice, software, tipomovimento);
	return ret;
    }

    @Override
    public EsitoOperazioneDML eliminaMovimentoPerSoftware(Integer codice, String software) {

	EsitoOperazioneDML ret = new EsitoOperazioneDML();
	commedilizietipopareriDAO.eliminaMovimentoPerSoftware(codice, software);
	return ret;
    }

    @Override
    public String findTipomovPerTipologiaParereECommissioniEdilizieR(Integer codiceTipoparere, Integer codiceCommissioniEdilizieR) {

	return commedilizietipopareriDAO.findTipomovPerTipologiaParereECommissioniEdilizieR(codiceTipoparere, codiceCommissioniEdilizieR);
    }

    @Override
    public List<CommedilizieTipopareri> findConfigurazioniPerTipomovimento(String tipomovimento) {

	return commedilizietipopareriDAO.findConfigurazioniPerTipomovimento(tipomovimento);
    }
}
