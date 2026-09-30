package it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDDisabilitati;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class MercatiDDisabilitatiServiceImpl extends BaseServiceImpl<MercatiDDisabilitati, PkId> implements MercatiDDisabilitatiService {

    private static final Logger log = LoggerFactory.getLogger(MercatiDDisabilitatiServiceImpl.class);
    private MercatiDDisabilitatiDAO mercatiDDisabilitatiDAO;
    private MercatiService mercatiService;
    private MercatiDService mercatiDService;
    private MercatipresenzeTService mercatipresenzeTService;

    @Autowired
    public void setMercatiDDisabilitatiDAO(MercatiDDisabilitatiDAO mercatiDDisabilitatiDAO) {

	this.mercatiDDisabilitatiDAO = mercatiDDisabilitatiDAO;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setMercatipresenzeTService(MercatipresenzeTService mercatipresenzeTService) {

	this.mercatipresenzeTService = mercatipresenzeTService;
    }

    @Override
    public void insert(MercatiDDisabilitati entity) {

	this.dataIntegration(entity);
	if (validateEntity(entity)) {
	    this.mercatiDDisabilitatiDAO.insert(entity);
	}
    }

    @Override
    public void update(MercatiDDisabilitati entity) {

	this.dataIntegration(entity);
	if (validateEntity(entity)) {
	    this.mercatiDDisabilitatiDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatiDDisabilitati entity) {

	if (isDeleteAllowed(entity)) {
	    this.mercatiDDisabilitatiDAO.delete(entity);
	}
    }

    @Override
    public List<MercatiDDisabilitati> findAll(Integer firstResult, Integer maxResult) {

	return this.mercatiDDisabilitatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public MercatiDDisabilitati findById(PkId id) {

	return this.mercatiDDisabilitatiDAO.findById(id);
    }

    @Override
    protected Class<MercatiDDisabilitati> getEntityClass() {

	return MercatiDDisabilitati.class;
    }

    private void dataIntegration(MercatiDDisabilitati entity) {

	if (entity == null) {
	    throw new RuntimeException("L'argomento (MercatiDDisabilitati) passato è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(MercatiDDisabilitati entity) {

	MercatiD posteggio = this.mercatiDService.bindDomainObject(entity.getPosteggio(), PkId.class, "id.codice");
	entity.setPosteggio(posteggio);
	Mercati mercato = this.mercatiService.bindDomainObject(entity.getMercato(), PkId.class, "id.codice");
	entity.setMercato(mercato);
    }

    @Override
    public List<Integer> findByIdGiornata(Integer idGiornata) {

	return this.mercatiDDisabilitatiDAO.findByIdGiornata(idGiornata);
    }

    @Override
    public void impostaStatoPosteggio(ImpostaStatoPosteggioFlyweight request) {

	if (request == null) {
	    throw new IllegalArgumentException("Impossibile impostare lo stato di un posteggio senza passare nessun riferimento");
	}
	if (request.getIdGiornata() == null) {
	    throw new IllegalArgumentException("Impossibile impostare lo stato di un posteggio senza passare la giornata di riferimento");
	}
	if (request.getIdPosteggio() == null) {
	    throw new IllegalArgumentException("Impossibile impostare lo stato di un posteggio senza passare il posteggio di riferimento");
	}
	if (request.getAbilitato() == null) {
	    throw new IllegalArgumentException("Impossibile impostare lo stato di un posteggio senza indicare se abilitarlo o meno");
	}
	//1. Recupero la giornata
	MercatipresenzeT giornata = this.mercatipresenzeTService.findById(new PkId(request.getIdGiornata()));
	//2. Recupero il posteggio
	MercatiD posteggio = this.mercatiDService.findById(new PkId(request.getIdPosteggio()));
	//3. Verifico se richiesta abilitazione
	if (Boolean.TRUE.equals(request.getAbilitato())) {
	    this.mercatiDDisabilitatiDAO.abilita(giornata, posteggio);
	    return;
	}
	//3. E' stata richiesta la disabilitazione
	this.mercatiDDisabilitatiDAO.disabilita(giornata, posteggio, request.getAnnotazione());
    }
}
