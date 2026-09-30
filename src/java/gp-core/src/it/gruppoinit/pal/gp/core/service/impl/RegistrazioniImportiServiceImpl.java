package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.RegistrazioniImportiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.RegistrazioniImportiService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrazioniImportiServiceImpl extends BaseServiceImpl<RegistrazioniImporti, PkId> implements RegistrazioniImportiService {

    private RegistrazioniImportiDAO registrazioniImportiDAO;
    private RegistrazioniService registrazioniService;

    @Autowired
    public void setRegistrazioniImportiDAO(RegistrazioniImportiDAO registrazioniImportiDAO) {

	this.registrazioniImportiDAO = registrazioniImportiDAO;
    }

    @Autowired
    public void setRegistrazioniService(RegistrazioniService registrazioniService) {

	this.registrazioniService = registrazioniService;
    }

    public boolean isDeleteAllowed(RegistrazioniImporti entity) {

	// §§§BEGIN§§§
	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!entity.getRegIoAssegnazionis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "REG_IO_ASSEGNAZIONI", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return true;
    }

    @Override
    public List<RegistrazioniImporti> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return registrazioniImportiDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public RegistrazioniImporti findById(PkId id) {

	// §§§BEGIN§§§
	return registrazioniImportiDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected Class<RegistrazioniImporti> getEntityClass() {

	return RegistrazioniImporti.class;
    }

    @Override
    public List<RegistrazioniImporti> findByRegistrazione(Registrazioni registrazioni) {

	// §§§BEGIN§§§
	return registrazioniImportiDAO.findByRegistrazione(registrazioni);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(RegistrazioniImporti entity) {

	// §§§BEGIN§§§
	// entity validate
	if (validateEntity(entity)) {
	    // business validate
	    boolean insert = true;
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    Registrazioni registrazioni = registrazioniService.findById(entity.getRegistrazioni().getId());
	    if (registrazioni.getRegistrazioniCausali().isNonPrevedeIncassi()) {
		entity.setNonPrevedeIncassi(true);
	    }
	    if (registrazioni.getRegistrazioniCausali().isSoloImportiNegativi()) {
		if (entity.getImporto().compareTo(new BigDecimal(0)) >= 0) {
		    _ivs.add(new InvalidValue("alert", null, "", getMessageFromBundle("service_error.solo_importi_negativi", null), null));
		    insert = false;
		}
	    } else {
		if (entity.getImporto().compareTo(new BigDecimal(0)) <= 0) {
		    _ivs.add(new InvalidValue("alert", null, "", getMessageFromBundle("service_error.solo_importi_positivi", null), null));
		    insert = false;
		}
	    }
	    entity.setRegistrazioni(registrazioni);
	    if (!insert) {
		this.throwValidationMessages(_ivs);
	    }
	    // end business validation
	    registrazioniImportiDAO.insert(entity);
	    registrazioniService.updateImportoRegistrazione(registrazioni);
	}
	// §§§END§§§
    }

    @Override
    public void delete(RegistrazioniImporti entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    Registrazioni registrazioni = registrazioniService.findById(entity.getRegistrazioni().getId());
	    entity.setRegistrazioni(registrazioni);
	    registrazioniImportiDAO.delete(entity);
	    registrazioniService.updateImportoRegistrazione(registrazioni);
	}
	// §§§END§§§
    }

    public void update(RegistrazioniImporti entity) {

	// §§§BEGIN§§§
	// entity validate
	if (validateEntity(entity)) {
	    // business validate
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    boolean validate = true;
	    Registrazioni registrazioni = registrazioniService.findById(entity.getRegistrazioni().getId());
	    if (registrazioni.getRegistrazioniCausali().isSoloImportiNegativi()) {
		if (entity.getImporto().compareTo(BigDecimal.ZERO) >= 0) {
		    InvalidValue iv = new InvalidValue("alert", null, "", "Sono ammessi solo valori negativi", null);
		    _ivs.add(iv);
		    validate = false;
		}
	    } else {
		if (entity.getImporto().compareTo(BigDecimal.ZERO) <= 0) {
		    InvalidValue iv = new InvalidValue("alert", null, "", "Sono ammessi solo valori positivi", null);
		    _ivs.add(iv);
		    validate = false;
		}
	    }
	    entity.setRegistrazioni(registrazioni);
	    if (!validate) {
		this.throwValidationMessages(_ivs);
	    }
	    // end business validate
	    registrazioniImportiDAO.update(entity);
	    registrazioniService.updateImportoRegistrazione(registrazioni);
	}
	// §§§END§§§
    }

    @Override
    public List<RegistrazioniImporti> findByRegistrazioniFilter(RegistrazioniFilter filter) {

	// §§§BEGIN§§§
	return registrazioniImportiDAO.findByRegistrazioniFilter(filter);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<RegistrazioniImporti> findScadenzeByRegistrazioniFilter(RegistrazioniFilter filter) {

	// §§§BEGIN§§§
	List<RegistrazioniImporti> registrazioniImportiList = this.findByRegistrazioniFilter(filter);
	List<RegistrazioniImporti> scadenzeList = new ArrayList<RegistrazioniImporti>();
	for (RegistrazioniImporti registrazioniImporti : registrazioniImportiList) {
	    if (registrazioniImporti.getRimanenza().compareTo(new BigDecimal(0)) > 0) {
		scadenzeList.add(registrazioniImporti);
	    }
	}
	return scadenzeList;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<RegistrazioniImporti> findByRegistrazioneGroupByConto(Registrazioni registrazioni) {

	// §§§BEGIN§§§
	return registrazioniImportiDAO.findByRegistrazioneGroupByConto(registrazioni);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public int countPerAggiornamentoIVA(BigDecimal valoreIva, Date data, Software software) {

	return registrazioniImportiDAO.countPerAggiornamentoIVA(valoreIva, data, software);
    }

    @Override
    public List<Integer> findPerAggiornamentoIVA(BigDecimal valoreIva, Date data, Software software) {

	return registrazioniImportiDAO.findPerAggiornamentoIVA(valoreIva, data, software);
    }
}
