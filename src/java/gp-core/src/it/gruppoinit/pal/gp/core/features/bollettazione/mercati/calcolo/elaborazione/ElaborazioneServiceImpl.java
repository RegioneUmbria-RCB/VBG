package it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.elaborazione;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.TitolaritaPagamentiEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollTestata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RichiestaCalcoloBollettazioneMercato;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.AbstractQueryBollettazioneMercatiHelper;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollettazioneDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IRecuperaInformazioniGiornataService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;

@Service
public class ElaborazioneServiceImpl implements ElaborazioneService {

    private IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService;
    private ElaborazioneCalcoloDAO elaborazioneCalcoloDAO;
    private BollettazioneDAO bollettazioneDAO;
    private ContiService contiService;

    @Autowired
    public void setRecuperaInformazioniGiornataService(IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService) {

	this.recuperaInformazioniGiornataService = recuperaInformazioniGiornataService;
    }

    @Autowired
    public void setBollettazioneDAO(BollettazioneDAO bollettazioneDAO) {

	this.bollettazioneDAO = bollettazioneDAO;
    }

    @Autowired
    public void setElaborazioneCalcoloDAO(ElaborazioneCalcoloDAO elaborazioneCalcoloDAO) {

	this.elaborazioneCalcoloDAO = elaborazioneCalcoloDAO;
    }

    @Autowired
    public void setContiService(ContiService contiService) {

	this.contiService = contiService;
    }

    @Override
    public Integer elabora(RichiestaCalcoloBollettazioneMercato request, TitolaritaPagamentiEnum titolarita, Boolean intestaAzienda,
	    Boolean conguaglio, CreazioneBollTestata creazioneBollTestata, BollCfgTipo bollCfgTipo) {

	String guidOperazione = UUID.randomUUID().toString();
	AbstractQueryBollettazioneMercatiHelper queryHelper = this.bollettazioneDAO.getQueryBuilder(guidOperazione, titolarita,
		request.getFiltriMercati(), request.getIntervalloDate(), conguaglio);
	return this.elaborazioneCalcoloDAO.elabora(this.recuperaInformazioniGiornataService, request, intestaAzienda, queryHelper,
		this.bollettazioneDAO, guidOperazione, creazioneBollTestata, bollCfgTipo, this.contiService);
    }
}
