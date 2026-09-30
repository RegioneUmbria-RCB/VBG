package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.RegistrazioniCausaliDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.service.RegistrazioniCausaliService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrazioniCausaliServiceImpl extends BaseServiceImpl<RegistrazioniCausali, PkId> implements RegistrazioniCausaliService {

    private RegistrazioniCausaliDAO registrazioniCausaliDAO;

    @Autowired
    public void setRegistrazioniCausaliDAO(RegistrazioniCausaliDAO registrazioniCausaliDAO) {

	this.registrazioniCausaliDAO = registrazioniCausaliDAO;
    }

    @Override
    protected Class<RegistrazioniCausali> getEntityClass() {

	return RegistrazioniCausali.class;
    }

    @Override
    public void delete(RegistrazioniCausali entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    registrazioniCausaliDAO.delete(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<RegistrazioniCausali> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return registrazioniCausaliDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public RegistrazioniCausali findById(PkId id) {

	// §§§BEGIN§§§
	return registrazioniCausaliDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(RegistrazioniCausali entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    registrazioniCausaliDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(RegistrazioniCausali entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    registrazioniCausaliDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<RegistrazioniCausali> findByDescrizione(String descrizione) {

	// §§§BEGIN§§§
	return registrazioniCausaliDAO.findByDescrizione(descrizione);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<RegistrazioniCausali> findByAbilitato() {

	// §§§BEGIN§§§
	return registrazioniCausaliDAO.findByAbilitato();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<RegistrazioniCausali> findByDescrizioneMercati(String descrizione) {

	// §§§BEGIN§§§
	return registrazioniCausaliDAO.findByDescrizioneMercati(descrizione);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected boolean isDeleteAllowed(RegistrazioniCausali entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!entity.getRegistrazionis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "REGISTRAZIONI", null));
	    delete = false;
	}
	if (!entity.getMercatiConfigurazionesCausaleAumento().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATI_CONFIGURAZIONE", null));
	    delete = false;
	}
	if (!entity.getMercatiConfigurazionesCausaleCanone().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATI_CONFIGURAZIONE", null));
	    delete = false;
	}
	if (!entity.getMercatiConfigurazionesCausaleDiminuzione().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATI_CONFIGURAZIONE", null));
	    delete = false;
	}
	if (!entity.getMercatiConfigurazionesCausaleTransazione().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATI_CONFIGURAZIONE", null));
	    delete = false;
	}
	if (!entity.getAlberoCausalis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ALBERO_CAUSALI", null));
	    delete = false;
	}
	if (!entity.getEndoCausalis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ENDO_CAUSALI", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return delete;
    }

    @Override
    protected boolean validateEntity(RegistrazioniCausali entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	boolean answer = super.validateEntity(entity);
	if (BooleanUtils.isTrue(entity.getRichiedeEndo()) && BooleanUtils.isTrue(entity.getRichiedePosteggio())) {
	    _ivs.add(new InvalidValue("errors.validator.registrazionicausali.endo.or.posteggio", null, "", "", null));
	    this.throwValidationMessages(_ivs);
	}
	return answer;
    }

    @Override
    public List<RegistrazioniCausali> findByDescrizioneEscluseRiduzioni(String descrizione) {

	// §§§BEGIN§§§
	return registrazioniCausaliDAO.findByDescrizioneEscluseRiduzioni(descrizione);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<RegistrazioniCausali> findByDescrizioneSoloRiduzioni(String descrizione) {

	// §§§BEGIN§§§
	return registrazioniCausaliDAO.findByDescrizioneSoloRiduzioni(descrizione);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
