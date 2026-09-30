/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.dao.PaySoggettiDebitoriDAO;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PaySoggettiDebitoriService;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;

/**
 * @author francol
 *
 */
@Service
public class PaySoggettiDebitoriServiceImpl extends BaseServiceImpl<PaySoggettiDebitori, PkId> implements PaySoggettiDebitoriService {

    @Autowired
    private PaySoggettiDebitoriDAO paySoggettiDebitoriDAO;

    @Override
    public void insert(PaySoggettiDebitori entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    this.paySoggettiDebitoriDAO.insert(entity);
	}
    }

    @Override
    public void update(PaySoggettiDebitori entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    this.paySoggettiDebitoriDAO.update(entity);
	}
    }

    @Override
    protected boolean validateEntity(PaySoggettiDebitori entity) {

	boolean validate = super.validateEntity(entity);
	if (StringUtils.isNotBlank(entity.getEmail()) && !Utilities.validaIndirizzoMail(entity.getEmail())) {
	    entity.setEmail(null); // se non valido setto a nullo
	}
	return validate;
    }

    @Override
    public void delete(PaySoggettiDebitori entity) {

	if (isDeleteAllowed(entity)) {
	    this.paySoggettiDebitoriDAO.delete(entity);
	}
    }

    @Override
    public List<PaySoggettiDebitori> findAll(Integer firstResult, Integer maxResult) {

	return this.paySoggettiDebitoriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public PaySoggettiDebitori findById(PkId id) {

	return this.paySoggettiDebitoriDAO.findById(id);
    }

    @Override
    protected Class<PaySoggettiDebitori> getEntityClass() {

	return PaySoggettiDebitori.class;
    }

    private void dataIntegration(PaySoggettiDebitori entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Parametro non valido o nullo");
	}
	if (StringUtils.isNotBlank(entity.getEmail())) {
	    entity.setEmail(entity.getEmail().trim());
	}
	if (StringUtils.isNotBlank(entity.getCap())) {
	    entity.setCap(entity.getCap().trim());
	}
	if (StringUtils.isNotBlank(entity.getCfPi())) {
	    entity.setCfPi(entity.getCfPi().trim());
	}
	if (StringUtils.isNotBlank(entity.getNome())) {
	    entity.setNome(entity.getNome().trim());
	}
	if (StringUtils.isNotBlank(entity.getCognome())) {
	    entity.setCognome(entity.getCognome().trim());
	}
    }

    @Override
    public PaySoggettiDebitori registraSoggettoDebitore(SoggettoDebitoreType sogDeb) throws PayException {

	return this.registraSoggettoDebitore(populateDomainObject(sogDeb));
    }

    @Override
    public PaySoggettiDebitori registraSoggettoDebitore(PaySoggettiDebitori sogDeb) throws PayException {

	PaySoggettiDebitori paySogDeb = null;
	List<PaySoggettiDebitori> soggettiPerCf = this.findByCodiceFiscale(sogDeb.getCfPi(), Boolean.TRUE);
	if (soggettiPerCf.isEmpty()) {
	    //se il soggetto non è ancora registrato lo inserisco 
	} else if (soggettiPerCf.size() == 1) {
	    //se esiste una sola registrazione attiva per questo cf
	    if (checkModified(soggettiPerCf.get(0), sogDeb)) {
		//se ci sono modifiche agli altri dati la disattivo e ne registro una nuova altrimenti riuso quella esistente per le nuove posizioni debitorie
		PaySoggettiDebitori soggCf = soggettiPerCf.get(0);
		soggCf.setFlagAttivo(Boolean.FALSE);
		this.paySoggettiDebitoriDAO.update(soggCf);
	    } else {
		paySogDeb = soggettiPerCf.get(0);
	    }
	} else {
	    //non dovrebbe esserci più di un record attivo fra quelli con lo stesso cf ma in caso di modifiche manuali al db o bugs...
	    //se ce n'è uno che risulta identico ai dati da registrare lo riuso e disattivo tutti gli altri altrimenti li disattivo tutti e ne inserisco uno nuovo per le nuove registrazioni
	    for (PaySoggettiDebitori sogg : soggettiPerCf) {
		if (!checkModified(sogg, sogDeb) && paySogDeb == null) {
		    paySogDeb = sogg;
		} else {
		    sogg.setFlagAttivo(Boolean.FALSE);
		    this.paySoggettiDebitoriDAO.update(sogg);
		}
	    }
	}
	if (paySogDeb == null) {
	    validaSoggettoDebitore(sogDeb);
	    this.insert(sogDeb);
	    paySogDeb = sogDeb;
	}
	return paySogDeb;
    }

    private void validaSoggettoDebitore(PaySoggettiDebitori paySoggettiDebitori) throws PayException {

	if (paySoggettiDebitori == null) {
	    throw new PayException("soggetto debitore non impostato");
	}
	if (StringUtils.isBlank(paySoggettiDebitori.getNome())) {
	    throw new PayException("nome del soggetto debitore non impostato");
	}
	if (StringUtils.isBlank(paySoggettiDebitori.getCfPi())) {
	    throw new PayException("codice fiscale del soggetto debitore non specificato");
	}
    }

    @Override
    public List<PaySoggettiDebitori> findByCodiceFiscale(String cfpi, Boolean attivo) {

	return this.paySoggettiDebitoriDAO.findByCodiceFiscale(cfpi, attivo);
    }

    public static SoggettoDebitoreType populateSchemaObject(PaySoggettiDebitori paySogg) {

	SoggettoDebitoreType sdt = null;
	if (paySogg != null) {
	    sdt = new SoggettoDebitoreType();
	    sdt.setCap(paySogg.getCap());
	    sdt.setCfpi(paySogg.getCfPi());
	    sdt.setCivico(paySogg.getCivico());
	    sdt.setCognome(paySogg.getCognome());
	    sdt.setEmail(paySogg.getEmail());
	    sdt.setLocalita(paySogg.getLocalita());
	    sdt.setNome(paySogg.getNome());
	    sdt.setProvincia(paySogg.getProvincia());
	    sdt.setStato(paySogg.getStato());
	    sdt.setVia(paySogg.getVia());
	}
	return sdt;
    }

    public static PaySoggettiDebitori populateDomainObject(SoggettoDebitoreType sogDeb) {

	PaySoggettiDebitori paySogDeb = null;
	if (sogDeb != null) {
	    paySogDeb = new PaySoggettiDebitori();
	    paySogDeb.setNome(sogDeb.getNome());
	    paySogDeb.setCognome(sogDeb.getCognome());
	    paySogDeb.setCfPi(StringUtils.defaultString(sogDeb.getCfpi()).toUpperCase());
	    paySogDeb.setVia(sogDeb.getVia());
	    paySogDeb.setCivico(sogDeb.getCivico());
	    paySogDeb.setCap(sogDeb.getCap());
	    paySogDeb.setLocalita(sogDeb.getLocalita());
	    paySogDeb.setProvincia(sogDeb.getProvincia());
	    paySogDeb.setStato(sogDeb.getStato());
	    paySogDeb.setEmail(sogDeb.getEmail() == null ? null : sogDeb.getEmail().trim());
	}
	return paySogDeb;
    }

    private boolean checkModified(PaySoggettiDebitori soggDb, PaySoggettiDebitori soggInput) {

	if (!StringUtils.defaultString(soggDb.getNome()).equals(StringUtils.defaultString(soggInput.getNome()))) {
	    return true;
	}
	if (!StringUtils.defaultString(soggDb.getCognome()).equals(StringUtils.defaultString(soggInput.getCognome()))) {
	    return true;
	}
	if (!StringUtils.defaultString(soggDb.getCap()).equals(StringUtils.defaultString(soggInput.getCap()))) {
	    return true;
	}
	if (!StringUtils.defaultString(soggDb.getEmail()).equals(StringUtils.defaultString(soggInput.getEmail()))) {
	    return true;
	}
	if (!StringUtils.defaultString(soggDb.getLocalita()).equals(StringUtils.defaultString(soggInput.getLocalita()))) {
	    return true;
	}
	if (!StringUtils.defaultString(soggDb.getProvincia()).equals(StringUtils.defaultString(soggInput.getProvincia()))) {
	    return true;
	}
	if (!StringUtils.defaultString(soggDb.getStato()).equals(StringUtils.defaultString(soggInput.getStato()))) {
	    return true;
	}
	if (!StringUtils.defaultString(soggDb.getCivico()).equals(StringUtils.defaultString(soggInput.getCivico()))) {
	    return true;
	}
	if (!StringUtils.defaultString(soggDb.getVia()).equals(StringUtils.defaultString(soggInput.getVia()))) {
	    return true;
	}
	//il confronto di modifica viene fatto solo fra soggetti con lostesso cf perciò non si effettua il confronto dei cf
	return false;
    }
}
