package it.gruppoinit.pal.gp.core.features.movimenti.allegati.eventi.sottoscrittori;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver.MovimentiAllegatiResolverFactoryService;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.FasiDiEsecuzioneEnum;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.TipimovimentodoctipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.eventi.EventoMovimentoInserito;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;

@Service
public class SottoscrittoreMovAllegatiEventoMovimentoInseritoServiceImpl implements IEventSubscriber<EventoMovimentoInserito> {

    private static final Logger logger = LoggerFactory.getLogger(SottoscrittoreMovAllegatiEventoMovimentoInseritoServiceImpl.class);
    private MovimentiallegatiService service;
    private TipimovimentodoctipoService docTipoService;
    private MovimentiNoSecurityService movimentiNoSecurityService;
    private MovimentiAllegatiResolverFactoryService movimentiAllegatiResolverFactoryService;

    @Autowired
    public void setService(MovimentiallegatiService service) {

	this.service = service;
    }

    @Autowired
    public void setDocTipoService(TipimovimentodoctipoService docTipoService) {

	this.docTipoService = docTipoService;
    }

    @Autowired
    public void setMovimentiNoSecurityService(MovimentiNoSecurityService movimentiNoSecurityService) {

	this.movimentiNoSecurityService = movimentiNoSecurityService;
    }

    @Autowired
    public void setMovimentiAllegatiResolverFactoryService(MovimentiAllegatiResolverFactoryService movimentiAllegatiResolverFactoryService) {

	this.movimentiAllegatiResolverFactoryService = movimentiAllegatiResolverFactoryService;
    }

    @Override
    public void onEvent(EventoMovimentoInserito e) throws EventAbortedException {

	try {
	    //1. Recupero il tipomovimento
	    Movimenti mov = this.movimentiNoSecurityService.findById(new PkId(e.getCodiceMovimento()));
	    //2. Se è una scadenza non faccio nulla
	    if (e.isScadenza()) {
		return;
	    }
	    //3. Recupero la lista di documenti tipo da generare
	    List<Integer> idLettere = this.docTipoService.findCodiciLettereAutomaticheByTipoMovimentoAndFase(
		    mov.getTipomovimento().getId().getTipomovimento(), FasiDiEsecuzioneEnum.PRIMA_DELLA_PROTOCOLLAZIONE);
	    for (Integer idLettera : idLettere) {
		//3.1 Generazione allegato
		Movimentiallegati allegato = this.movimentiAllegatiResolverFactoryService.build(this.createDocumentMergeHelper(mov, idLettera),
			e.getCodiceMovimento(), idLettera);
		//3.2 invoco la conversione in pdf
		this.service.insertTrasformaInPdf(allegato.getId().getCodice());
	    }
	} catch (RuntimeException err) {
	    logger.error("Errore nella gestione dell'evento " + e, err);
	} catch (Exception err) {
	    logger.error("Errore nella gestione dell'evento " + e, err);
	}
    }

    private DocumentMergeHelper createDocumentMergeHelper(Movimenti movimento, Integer idLettera) {

	DocumentMergeHelper dmh = new DocumentMergeHelper();
	dmh.getParams().put("CODICEISTANZA", movimento.getIstanza().getId().getCodice().toString());
	dmh.getParams().put("CODICEMOVIMENTO", movimento.getId().getCodice().toString());
	dmh.getParams().put("TIPOMOVIMENTO", movimento.getTipomovimento().getId().getTipomovimento());
	dmh.getParams().put("CODICEDOCUMENTO", idLettera.toString());
	return dmh;
    }
}
