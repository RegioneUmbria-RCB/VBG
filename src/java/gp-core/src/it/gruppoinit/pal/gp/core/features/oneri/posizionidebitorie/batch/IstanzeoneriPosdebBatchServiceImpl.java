package it.gruppoinit.pal.gp.core.features.oneri.posizionidebitorie.batch;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paevolution.ws.pagamenti_types.ElencoDocumentiEsitoType;
import com.paevolution.ws.pagamenti_types.StatoDocumentoType;

import it.gruppoinit.pal.gp.core.dao.IstanzeoneriPosdebBatchDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatch;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatchId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoDocumentoPosizioneDebitoriaDisponibile;
import it.gruppoinit.pal.gp.core.features.oneri.DocumentiDaGenerare;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

/**
 * 
 * @author
 */
@Service
public class IstanzeoneriPosdebBatchServiceImpl extends BaseServiceImpl<IstanzeoneriPosdebBatch, IstanzeoneriPosdebBatchId>
	implements IstanzeoneriPosdebBatchService {

    private static final Logger log = LoggerFactory.getLogger(IstanzeoneriPosdebBatchServiceImpl.class);
    private IstanzeoneriPosdebBatchDAO istanzeoneriposdebbatchDAO;
    private NodoPagamentiService nodoPagamentiService;
    private IstanzeoneriService istanzeoneriService;
    private IEventPublisher eventPublisher;

    @Autowired
    public void setEventPublisher(IEventPublisher eventPublisher) {

	this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setIstanzeoneriService(IstanzeoneriService istanzeoneriService) {

	this.istanzeoneriService = istanzeoneriService;
    }

    @Autowired
    public void setIstanzeoneriposdebbatchDAO(IstanzeoneriPosdebBatchDAO istanzeoneriposdebbatchDAO) {

	this.istanzeoneriposdebbatchDAO = istanzeoneriposdebbatchDAO;
    }

    @Autowired
    public void setNodoPagamentiService(NodoPagamentiService nodoPagamentiService) {

	this.nodoPagamentiService = nodoPagamentiService;
    }

    @Autowired
    public void setIstanzeoneriPosdebBatchDAO(IstanzeoneriPosdebBatchDAO istanzeoneriposdebbatchDAO) {

	this.istanzeoneriposdebbatchDAO = istanzeoneriposdebbatchDAO;
    }

    @Override
    protected Class<IstanzeoneriPosdebBatch> getEntityClass() {

	return IstanzeoneriPosdebBatch.class;
    }

    @Override
    public List<IstanzeoneriPosdebBatch> findAll(Integer firstResult, Integer maxResult) {

	return istanzeoneriposdebbatchDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IstanzeoneriPosdebBatch entity) {

	if (validateEntity(entity)) {
	    istanzeoneriposdebbatchDAO.insert(entity);
	}
    }

    @Override
    public void update(IstanzeoneriPosdebBatch entity) {

	if (validateEntity(entity)) {
	    istanzeoneriposdebbatchDAO.update(entity);
	}
    }

    @Override
    public void delete(IstanzeoneriPosdebBatch entity) {

	if (isDeleteAllowed(entity)) {
	    istanzeoneriposdebbatchDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(IstanzeoneriPosdebBatch entity) {

	return true;
    }

    @Override
    public IstanzeoneriPosdebBatch findById(IstanzeoneriPosdebBatchId id) {

	return this.istanzeoneriposdebbatchDAO.findById(id);
    }

    @Override
    public void generaRichiestaDocumentiSeNonEsiste(Integer onereId, Integer posizioneDebitoriaId, DocumentiDaGenerare documentiDaGenerare) {

	// Verifico solamente se esiste un record con la stessa posizione debitoria/id onere perché vuol dire che 
	// il dato è vecchio e che al momento dell'inserimento uno dei due flag (genera fattura o genera avviso) non era settato.
	// Nel caso in cui non trovo record vado a inserire righe per i documenti richiesti. Questo potrebbe essere un problema 
	// se ho aggiornato un onere la cui causale in passato non richiedeva la generazione dei documenti?
	if (this.istanzeoneriposdebbatchDAO.contaByOnereWherePosizioneDebitoriaUgualeA(onereId, posizioneDebitoriaId) != 0) {
	    return;
	}
	if (documentiDaGenerare.getGeneraAvviso()) {
	    this.istanzeoneriposdebbatchDAO.insert(IstanzeoneriPosdebBatch.newAvviso(onereId, posizioneDebitoriaId));
	}
	if (documentiDaGenerare.getGeneraFattura()) {
	    this.istanzeoneriposdebbatchDAO.insert(IstanzeoneriPosdebBatch.newFattura(onereId, posizioneDebitoriaId));
	}
    }

    @Override
    public boolean esisteConAltraPosizioneDebitoria(Integer onereId, Integer posizioneDebitoriaId) {

	return this.istanzeoneriposdebbatchDAO.contaByOnereWherePosizioneDebitoriaDiversaDa(onereId, posizioneDebitoriaId) != 0;
    }

    @Override
    public void eliminaDaIdOnere(Integer onereId) {

	this.istanzeoneriposdebbatchDAO.deleteByIdOnere(onereId);
    }

    @Override
    @Transactional(noRollbackFor = FunzioneBusinessRemotaException.class)
    public void updateProcessaDocumentiNonCompleti(int numeroRecordDaElaborare) throws FunzioneBusinessRemotaException {

	//	trova tutti i record non completati FLAG_COMPLETATA=0
	//	PER OGNI RECORD VERIFICA SE PRESENTE IL DOCUMENTO
	//	SE PRESENTE ALLORA LANCIA EVENTO EventoDocumentoPosizioneDebitoriaDisponibile
	//	SE NON PRESENTE FA UNA RICHIESTA AL NODO DEI PAGAMENTI PER QUEL TIPO DI DOCUMENTO
	//	SE LA RISPOSTA è OK ALLORA GENERA EVENTO EventoDocumentoPosizioneDebitoriaDisponibile E segna l'attivita come completata
	//il primo giro cerca di completare tutte le istanzeoneri
	int counter = 0;
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("flagCompletata", IstanzeoneriPosdebBatch.NUOVO, Integer.class));
	ft.addRestriction(fr);
	int count = istanzeoneriposdebbatchDAO.countRecord(ft);
	log.debug("updateProcessaDocumentiNonCompleti: da elaborare {}", count);
	ft.addOrder(FilterUtils.orderDesc("id.idistanzeoneri"));
	int pageNumber = 0;
	int startRow = 0;
	int rowEnd;
	int pageSize = numeroRecordDaElaborare;
	if (count > 0) {
	    pageNumber = count / pageSize;
	    if (count % pageSize > 0) {
		pageNumber++;
	    }
	    for (int i = 0; i < pageNumber; i++) {
		startRow = i * pageSize;
		rowEnd = pageSize;
		List<IstanzeoneriPosdebBatch> results = istanzeoneriposdebbatchDAO.findByFilterTable(ft, startRow, rowEnd);
		for (IstanzeoneriPosdebBatch istanzeoneriPosdebBatch : results) {
		    if (counter == numeroRecordDaElaborare) {
			return;
		    }
		    log.debug("updateProcessaDocumentiNonCompleti: ciclo la posizione {}", counter);
		    Istanzeoneri istonere = istanzeoneriService.findById(new PkId(istanzeoneriPosdebBatch.getId().getIdistanzeoneri()));
		    ORMHelper.setSoftware(istonere.getIstanza().getSoftware().getCodice());
		    try {
			String codicecomune = istonere.getIstanza().getComune().getCodicecomune();
			boolean presente = nodoPagamentiService.checkDocumentiForDettPosizioneDebitoria(
				istanzeoneriPosdebBatch.getId().getIdDettPosizioneDebitoria(), istanzeoneriPosdebBatch.getId().getTipoDocumento(),
				codicecomune);
			log.debug("updateProcessaDocumentiNonCompleti: ciclo la posizione {}, {}", new Object[] { counter, presente });
			if (!presente) {
			    // SE NON PRESENTE FA UNA RICHIESTA AL NODO DEI PAGAMENTI PER QUEL TIPO DI DOCUMENTO
			    ElencoDocumentiEsitoType response = null;
			    switch (istanzeoneriPosdebBatch.getId().getTipoDocumento()) {
			    case AVVISO:
				log.debug("updateProcessaDocumentiNonCompleti: invio la richiesta di avviso {}, {}, {}",
					new Object[] { counter, presente, istanzeoneriPosdebBatch.getId().getIdDettPosizioneDebitoria() });
				response = nodoPagamentiService.inviaAvviso(istanzeoneriPosdebBatch.getId().getIdDettPosizioneDebitoria());
				break;
			    case FATTURA:
				log.debug("updateProcessaDocumentiNonCompleti: invio la richiesta di fattura {}, {}, {}",
					new Object[] { counter, presente, istanzeoneriPosdebBatch.getId().getIdDettPosizioneDebitoria() });
				response = nodoPagamentiService.generaFattura(istanzeoneriPosdebBatch.getId().getIdDettPosizioneDebitoria());
				break;
			    default:
				break;
			    }
			    if (response != null && response.getEsitoPosizione().isEmpty() && response.getEsitoPosizione().get(0) != null
				    && response.getEsitoPosizione().get(0).getStatoDocumento() != null
				    && response.getEsitoPosizione().get(0).getStatoDocumento().equals(StatoDocumentoType.DISPONIBILE)) {
				presente = true;
			    }
			}
			if (presente) {
			    log.debug(
				    "updateProcessaDocumentiNonCompleti: contrassegno come IstanzeoneriPosdebBatch.DA_ELABORARE la richiesta di avviso {},  {}, {}",
				    new Object[] { counter, presente, istanzeoneriPosdebBatch.getId().getIdDettPosizioneDebitoria() });
			    istanzeoneriPosdebBatch.setFlagCompletata(IstanzeoneriPosdebBatch.DA_ELABORARE);
			    this.update(istanzeoneriPosdebBatch);
			    counter++;
			    istanzeoneriposdebbatchDAO.commit();
			    istanzeoneriposdebbatchDAO.flush();
			}
			istanzeoneriposdebbatchDAO.clear();
		    } catch (Exception e) {
			log.error("{}", e);
		    }
		}
	    }
	}
	counter = 0;
	log.debug("updateProcessaDocumentiNonCompleti: ricerco le richieste aperte ");
	List<Integer> codiciIstanza = istanzeoneriposdebbatchDAO.findCodiciIstanzaPosizioniDaPreparare(numeroRecordDaElaborare);
	// il secondo giro verifica se complete se si lancia l'evento 
	for (Integer codiceIstanza : codiciIstanza) {
	    if (counter == numeroRecordDaElaborare) {
		return;
	    }
	    log.debug("updateProcessaDocumentiNonCompleti: {} Processo l'istanza  {}", new Object[] { counter, codiceIstanza });
	    Set<Integer> codiciCausali = new HashSet<Integer>();
	    List<Istanzeoneri> findByIstanza = istanzeoneriService.findByIstanza(codiceIstanza);
	    // popolo una mappa suddivisa per causale
	    log.debug("updateProcessaDocumentiNonCompleti: {} popolo la mappa per causale {}", new Object[] { counter, codiceIstanza });
	    String codiceComune = null;
	    for (Istanzeoneri iston : findByIstanza) {
		ORMHelper.setSoftware(iston.getIstanza().getSoftware().getCodice());
		if (StringUtils.isBlank(codiceComune)) {
		    codiceComune = iston.getIstanza().getComune().getCodicecomune();
		}
		codiciCausali.add(iston.getTipicausalioneri().getId().getCodice());
	    }
	    log.debug("updateProcessaDocumentiNonCompleti: {}  mappe per causale popolate {} le ciclo", new Object[] { counter, codiceIstanza });
	    for (Integer codiceCausale : codiciCausali) {
		log.debug("updateProcessaDocumentiNonCompleti: {}  elaboro la causale {} per l'istanza {}",
			new Object[] { counter, codiceCausale, codiceIstanza });
		List<IstanzeoneriPosdebBatch> idOnerePerIstanzaECausaleDaElaborare = istanzeoneriposdebbatchDAO
			.trovaIdOnerePerIstanzaECausaleDaElaborare(codiceIstanza, codiceCausale, IstanzeoneriPosdebBatch.DA_ELABORARE);
		log.debug(
			"updateProcessaDocumentiNonCompleti: {}  elaboro la causale {} per l'istanza {}. idOnerePerIstanzaECausaleDaElaborare.size()={}",
			new Object[] { counter, codiceCausale, codiceIstanza, idOnerePerIstanzaECausaleDaElaborare.size() });
		log.debug("updateProcessaDocumentiNonCompleti: {}  pubblico l'evento per l'istanza {} e causale {}",
			new Object[] { counter, codiceIstanza, codiceCausale });
		eventPublisher
			.publish(new EventoDocumentoPosizioneDebitoriaDisponibile(codiceIstanza, idOnerePerIstanzaECausaleDaElaborare, codiceComune));
		for (IstanzeoneriPosdebBatch istanzeoneriPosdebBatch : idOnerePerIstanzaECausaleDaElaborare) {
		    istanzeoneriPosdebBatch.setFlagCompletata(IstanzeoneriPosdebBatch.ELABORATA);
		    this.update(istanzeoneriPosdebBatch);
		}
		counter++;
		istanzeoneriposdebbatchDAO.commit();
	    }
	    istanzeoneriposdebbatchDAO.flush();
	    istanzeoneriposdebbatchDAO.clear();
	}
    }
}
