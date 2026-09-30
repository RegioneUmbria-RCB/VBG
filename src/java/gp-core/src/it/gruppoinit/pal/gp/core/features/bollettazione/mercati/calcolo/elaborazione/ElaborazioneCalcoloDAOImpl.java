package it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.elaborazione;

import java.util.List;
import java.util.Map;

import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.BollettazioneAuditLogger;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollTestata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RichiestaCalcoloBollettazioneMercato;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.AbstractQueryBollettazioneMercatiHelper;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollettazioneDAO;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.coefficienti.ChiaveCoefficienteMercato;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.coefficienti.RecuperoCoefficientiJDBCWorker;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.coefficienti.ValoreCoefficienteMercato;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.livelliservizio.ChiaveLivelloServizio;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.livelliservizio.RecuperLivelliServizioJDBCWorker;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.livelliservizio.ValoreLivelloServizio;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IRecuperaInformazioniGiornataService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;

@SuppressWarnings("rawtypes")
@Repository
public class ElaborazioneCalcoloDAOImpl extends BaseDAOImpl implements ElaborazioneCalcoloDAO {

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Override
    public Integer elabora(IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService, RichiestaCalcoloBollettazioneMercato richiesta,
	    Boolean intestaAzienda, AbstractQueryBollettazioneMercatiHelper queryHelper, BollettazioneDAO bollettazioneDAO, String guidOperazione,
	    CreazioneBollTestata creazioneBollTestata, BollCfgTipo bollCfgTipo, ContiService contiService) {

	BollettazioneAuditLogger.logger.info("Inizio elaborazione bollettazione mercati, guid assegnato {}", guidOperazione);
	//1. Mappa coefficienti
	RecuperoCoefficientiJDBCWorker coefficientiWorker = new RecuperoCoefficientiJDBCWorker(richiesta.getIntervalloDate(), contiService);
	getSession().doWork(coefficientiWorker);
	Map<ChiaveCoefficienteMercato, List<ValoreCoefficienteMercato>> mappaCoefficienti = coefficientiWorker.getMappaCoefficienti();
	//2. Mappa livelli di servizio
	RecuperLivelliServizioJDBCWorker livelliServizioWorker = new RecuperLivelliServizioJDBCWorker(richiesta.getIntervalloDate());
	getSession().doWork(livelliServizioWorker);
	Map<ChiaveLivelloServizio, List<ValoreLivelloServizio>> mappaLivelliServizio = livelliServizioWorker.getMappaLivelliServizio();
	//3. Elaborazione
	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	InserimentoRigheJDBCWorker worker = new InserimentoRigheJDBCWorker(sfi, recuperaInformazioniGiornataService, queryHelper, intestaAzienda,
		guidOperazione, richiesta.getIntervalloDate(), bollettazioneDAO, creazioneBollTestata, bollCfgTipo, mappaCoefficienti,
		mappaLivelliServizio);
	getSession().doWork(worker);
	BollettazioneAuditLogger.logger.info("Termine elaborazione bollettazione mercati con guid {}", guidOperazione);
	return worker.getIdTestata();
    }
}
