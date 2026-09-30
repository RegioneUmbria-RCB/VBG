package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TmpStatiComunicazioniDDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.PassoCreazioneComunicazioneEnum;
import it.gruppoinit.pal.gp.core.domain.TmpStatiComunicazioniD;
import it.gruppoinit.pal.gp.core.service.TmpStatiComunicazioniDService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TmpStatiComunicazioniDServiceImpl extends BaseServiceImpl<TmpStatiComunicazioniD, Integer> implements TmpStatiComunicazioniDService {

    private TmpStatiComunicazioniDDAO tmpStatiComunicazioniDDAO;

    @Autowired
    public void setTmpStatiComunicazioniDDAO(TmpStatiComunicazioniDDAO tmpStatiComunicazioniDDAO) {

	this.tmpStatiComunicazioniDDAO = tmpStatiComunicazioniDDAO;
    }

    @Override
    public void insert(Integer posizione, Integer fkComunicazioniD, String stato) {

	tmpStatiComunicazioniDDAO.insert(posizione, fkComunicazioniD, stato);
    }

    @Override
    protected Class<TmpStatiComunicazioniD> getEntityClass() {

	return tmpStatiComunicazioniDDAO.getEntityClass();
    }

    @Override
    public void insert(TmpStatiComunicazioniD entity) {

	//	if (validateEntity(entity)) {
	//	    tmpStatiComunicazioniDDAO.insert(entity);
	//	}
	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public void update(TmpStatiComunicazioniD entity) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public void delete(TmpStatiComunicazioniD entity) {

	//	tmpStatiComunicazioniDDAO.delete(entity);
	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public List<TmpStatiComunicazioniD> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public TmpStatiComunicazioniD findById(Integer id) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public List<TmpStatiComunicazioniD> findByIdComunicazioned(Integer codiceComunicazione) {

	return tmpStatiComunicazioniDDAO.findByIdComunicazioned(codiceComunicazione);
    }

    @Override
    public void update(Integer codiceComunicazioneD, PassoCreazioneComunicazioneEnum insertMovimento, String errore) {

	tmpStatiComunicazioniDDAO.update(codiceComunicazioneD, insertMovimento, errore);
    }

    @Override
    public void delete(Integer codiceComunicazioneD, PassoCreazioneComunicazioneEnum passoComunicazione) {

	tmpStatiComunicazioniDDAO.delete(codiceComunicazioneD, passoComunicazione);
    }

    @Override
    public void delete(Integer codiceComunicazioneD) {

	tmpStatiComunicazioniDDAO.delete(codiceComunicazioneD);
    }

    @Override
    public TmpStatiComunicazioniD findPrimoPassoConErrore(Integer idcomunicazioned) {

	return tmpStatiComunicazioniDDAO.findPrimoPassoConErrore(idcomunicazioned);
    }

    @Override
    public List<TmpStatiComunicazioniD> findComunicazioniBloccateInvioEmail(Integer codiceCominicazioneT) {

	return tmpStatiComunicazioniDDAO.findComunicazioniBloccateInvioEmail(codiceCominicazioneT);
    }
}
