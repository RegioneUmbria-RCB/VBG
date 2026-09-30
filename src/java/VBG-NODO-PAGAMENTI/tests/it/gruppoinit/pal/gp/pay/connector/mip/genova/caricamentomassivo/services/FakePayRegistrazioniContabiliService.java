package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniContabiliService;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;

public class FakePayRegistrazioniContabiliService implements PayRegistrazioniContabiliService {

    @Override
    public void insert(PayRegistrazioniContabili entity) {

	//non necessario
    }

    @Override
    public void update(PayRegistrazioniContabili entity) {

	//non necessario
    }

    @Override
    public void delete(PayRegistrazioniContabili entity) {

	//non necessario
    }

    @Override
    public List<PayRegistrazioniContabili> findAll(Integer firstResult, Integer maxResult) {

	//non necessario
	return null;
    }

    @Override
    public PayRegistrazioniContabili findById(PkId id) {

	PayRegistrazioniContabili registrazione = new PayRegistrazioniContabili(id);
	registrazione.setAnno(2026);
	registrazione.setDataRegistrazione(new Date());
	registrazione.setDescrizione("Registrazione con id " + id.getCodice().toString());
	return registrazione;
    }

    @Override
    public PayRegistrazioniContabili bindDomainObject(PayRegistrazioniContabili entity, Class<?> idClass, String idPath) {

	//non necessario
	return null;
    }

    @Override
    public PkId newIdFromSequencetable(PayRegistrazioniContabili entity) {

	//non necessario
	return null;
    }

    @Override
    public PayRegistrazioniContabili creaRegistrazioneContabile(RegistrazioneContabileType regCont, PayConfigurationHelper payCfg, boolean otf)
	    throws PayException {

	//non necessario
	return null;
    }

    @Override
    public int countPosizioniByIdRegistrazione(Integer idRegistrazioneContabile) {

	//non necessario
	return 0;
    }

    @Override
    public List<Integer> findIdPosizioniByIdRegistrazione(Integer idRegistrazioneContabile) {

	//non necessario
	return null;
    }
}
