package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiDettaglio;
import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiDettaglioId;
import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiTestata;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

@Service
public class AssegnazioneGruppiTestataServiceImpl implements IAssegnazioneGruppiTestataService {

    private static final Logger log = LoggerFactory.getLogger(AssegnazioneGruppiTestataServiceImpl.class);
    private IAssegnazioneGruppiTestataDAO assegnazioneGruppiTestataDAO;
    private IAssegnazioneGruppiDettaglioService assegnazioneGruppiDettaglioService;
    private IstanzeService istanzeService;
    private ResponsabiliService responsabiliService;

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setAssegnazioneGruppiDettaglioService(IAssegnazioneGruppiDettaglioService assegnazioneGruppiDettaglioService) {

	this.assegnazioneGruppiDettaglioService = assegnazioneGruppiDettaglioService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setAssegnazioneGruppiTestataDAO(IAssegnazioneGruppiTestataDAO assegnazioneGruppiTestataDAO) {

	this.assegnazioneGruppiTestataDAO = assegnazioneGruppiTestataDAO;
    }

    @Override
    public Integer verificaSeAperta(Integer fkGruppoIstrutori, Integer codiceIstanza) {

	return assegnazioneGruppiTestataDAO.verificaSeAperta(fkGruppoIstrutori, codiceIstanza);
    }

    @Override
    public void insert(AssegnazioneGruppiTestata assegnazioneGruppiTestata) {

	assegnazioneGruppiTestataDAO.insert(assegnazioneGruppiTestata);
    }

    @Override
    public AssegnazioneGruppiTestata findById(Integer idTestata) {

	return assegnazioneGruppiTestataDAO.findById(new PkId(idTestata));
    }

    @Override
    public Integer trovaTestataAperta(Integer fkGruppoIstrutori) {

	return assegnazioneGruppiTestataDAO.trovaTestataAperta(fkGruppoIstrutori);
    }

    @Override
    public void chiudiAssegnazione(Integer idTestata, Date dataChiusura) {

	//Chiudiamo il gruppo
	log.debug("AssegnazioneGruppiTestataServiceImpl#chiudiAssegnazione - chiusura del gruppo {} in data {}", idTestata, dataChiusura);
	AssegnazioneGruppiTestata oldTestata = this.findById(idTestata);
	oldTestata.setDataChiusura(dataChiusura);
	this.assegnazioneGruppiTestataDAO.update(oldTestata);
	// apriamo un nuovo gruppo
	AssegnazioneGruppiTestata newTestata = new AssegnazioneGruppiTestata();
	newTestata.setAmbito(oldTestata.getAmbito());
	newTestata.setGruppiIstruttori(oldTestata.getGruppiIstruttori());
	newTestata.setDataApertura(dataChiusura);
	this.assegnazioneGruppiTestataDAO.insert(newTestata);
	//verificare eccedenze
	//mappa che contiene operatore con relative pratiche assegnate
	Map<Integer, Integer> codResp = new HashMap<Integer, Integer>();
	List<ChiaveValoreBean<Integer, Integer>> ls = assegnazioneGruppiDettaglioService.countPresenzeRespPerAssegnazioneTestata(idTestata,
		oldTestata.getAmbito());
	for (ChiaveValoreBean<Integer, Integer> cbv : ls) {
	    Integer codiceResponsabile = cbv.getChiave();
	    Integer istanzeResponsabile = cbv.getValore();
	    // calcolo eventuali eccedenze
	    Responsabili r = responsabiliService.findById(new PkId(codiceResponsabile));
	    Integer peso = r.getPesoCaricoLavoro();
	    if (peso < istanzeResponsabile) {
		Integer eccedenza = istanzeResponsabile - peso;
		log.debug("L'operatore {} ha lavorato n° {} in più rispetto al suo carico di lavoro [carico:{}]",
			new Object[] { r.getResponsabile(), eccedenza, r.getPesoCaricoLavoro() });
		// prendo tutte le istanze aperte 
		List<Integer> codiciIstanza = assegnazioneGruppiDettaglioService.findIstanzeAperte(idTestata, codiceResponsabile,
			oldTestata.getAmbito());
		for (int i = 0; i < eccedenza; i++) {
		    AssegnazioneGruppiDettaglio agd = new AssegnazioneGruppiDettaglio();
		    AssegnazioneGruppiDettaglioId idDett = new AssegnazioneGruppiDettaglioId();
		    idDett.setCodiceIstanza(codiciIstanza.get(i));
		    idDett.setIdcomune(ORMHelper.getIdcomune());
		    idDett.setIdTestata(newTestata.getId().getCodice());
		    agd.setId(idDett);
		    agd.setAssegnazioneGruppiTestata(newTestata);
		    Istanze istanza = istanzeService.findById(new PkId(codiciIstanza.get(i)));
		    agd.setIstanze(istanza);
		    log.debug("Sposto l'istanza:{} assegnata all'operatore:{} nella nuova testata:{}",
			    new Object[] { istanza.getId().getCodice(), r.getResponsabile(), newTestata.getId().getCodice() });
		    this.assegnazioneGruppiDettaglioService.insert(agd);
		}
	    }
	    codResp.put(codiceResponsabile, istanzeResponsabile);
	}
    }

    @Override
    public void chiudiAssegnazione(Integer idTestata) {

	this.chiudiAssegnazione(idTestata, new Date());
    }
}
