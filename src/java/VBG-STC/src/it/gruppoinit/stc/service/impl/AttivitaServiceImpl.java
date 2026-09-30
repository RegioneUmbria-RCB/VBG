package it.gruppoinit.stc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.stc.dao.AttivitaDAO;
import it.gruppoinit.stc.dao.impl.PraticheHelper;
import it.gruppoinit.stc.domain.Attivita;
import it.gruppoinit.stc.service.AttivitaService;
import it.init.sigepro.rte.types.SportelloType;

@Service
public class AttivitaServiceImpl extends BaseServiceImpl<Attivita, Integer> implements AttivitaService {

    @Autowired
    private AttivitaDAO attivitaDAO;

    @Override
    protected Class<Attivita> getEntityClass() {

	return Attivita.class;
    }

    @Override
    public void delete(Attivita entity) {

	attivitaDAO.delete(entity);
    }

    @Override
    public List<Attivita> findAll(Integer firstResult, Integer maxResult) {

	return attivitaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Attivita findById(Integer id) {

	return attivitaDAO.findById(id);
    }

    @Override
    public void insert(Attivita entity) {

	if (validate(entity)) {
	    attivitaDAO.insert(entity);
	}
    }

    @Override
    public void update(Attivita entity) {

	if (validate(entity)) {
	    attivitaDAO.update(entity);
	}
    }

    @Override
    public Attivita findByUniqueKey(Attivita example) {

	return attivitaDAO.findByUniqueKey(example);
    }

    @Override
    public String findIdProcedimentoPrecedentiComunicazioni(Integer idPraticaMittente, Integer idPraticaDestinataria) {

	return attivitaDAO.findIdProcedimentoPrecedentiComunicazioni(idPraticaMittente, idPraticaDestinataria);
    }

    @Override
    public Attivita findBySportelloTypeAndIdAttivita(SportelloType sportello, String idAttivita) {

	return this.attivitaDAO.findBySportelloTypeAndIdAttivita(sportello, idAttivita);
    }

    @Override
    public SportelloType findSportelloDestinatarioByIdAttivitaMittente(Integer idAttivitaMittente) {

	return this.attivitaDAO.findSportelloDestinatarioByIdAttivitaMittente(idAttivitaMittente);
    }

    @Override
    public PraticheHelper findPraticaMittenteByIdAttivitaDestinataria(Integer idAttivitaDestinataria) {

	return this.attivitaDAO.findPraticaMittenteByIdAttivitaDestinataria(idAttivitaDestinataria);
    }
}
