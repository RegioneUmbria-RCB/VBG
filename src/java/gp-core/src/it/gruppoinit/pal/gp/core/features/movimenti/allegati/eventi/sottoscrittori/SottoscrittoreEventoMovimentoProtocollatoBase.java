package it.gruppoinit.pal.gp.core.features.movimenti.allegati.eventi.sottoscrittori;

import java.util.List;
import java.util.UUID;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver.MovimentiAllegatiResolverException;
import it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver.MovimentiAllegatiResolverFactoryService;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.FasiDiEsecuzioneEnum;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.TipimovimentodoctipoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;

public class SottoscrittoreEventoMovimentoProtocollatoBase {

    public void generaDocumentiAutomatici(TipimovimentodoctipoService docTipoService, MovimentiAllegatiResolverFactoryService factory,
	    MovimentiallegatiService movimentiallegatiService, Movimenti movimento, TempLinkallegatiService tempLinkallegatiService)
	    throws MovimentiAllegatiResolverException {

	//1. Recupero la lista dei tipi documento configurati per la generazione automatica post protocollazione
	List<Integer> idLettere = docTipoService.findCodiciLettereAutomaticheByTipoMovimentoAndFase(
		movimento.getTipomovimento().getId().getTipomovimento(), FasiDiEsecuzioneEnum.DOPO_LA_PROTOCOLLAZIONE);
	if (!idLettere.isEmpty()) {
	    String uuidTempLink = UUID.randomUUID().toString();
	    List<MovimentiallegatiDTO> movalls = movimentiallegatiService.findMovimentiallegatiDTOByMovimenti(movimento.getId().getCodice());
	    for (MovimentiallegatiDTO movimentiallegatiDTO : movalls) {
		TempLinkallegati tempLinkallegati = movimentiallegatiService.populateTempLinkallegati(movimentiallegatiDTO.getCodiceOggetto(),
			movimentiallegatiDTO.getNomeFile(), uuidTempLink, movimentiallegatiDTO.getDescrizione());
		tempLinkallegatiService.insert(tempLinkallegati);
	    }
	    for (Integer idLettera : idLettere) {
		//2. Generazione allegato
		Movimentiallegati allegato = factory.build(this.createDocumentMergeHelper(movimento, idLettera, uuidTempLink),
			movimento.getId().getCodice(), idLettera);
		//3. invoco la conversione in pdf
		movimentiallegatiService.insertTrasformaInPdf(allegato.getId().getCodice());
	    }
	}
    }

    private DocumentMergeHelper createDocumentMergeHelper(Movimenti movimento, Integer idLettera, String uuidTempLink) {

	DocumentMergeHelper dmh = new DocumentMergeHelper();
	dmh.getParams().put("CODICEISTANZA", movimento.getIstanza().getId().getCodice().toString());
	dmh.getParams().put("CODICEMOVIMENTO", movimento.getId().getCodice().toString());
	dmh.getParams().put("TIPOMOVIMENTO", movimento.getTipomovimento().getId().getTipomovimento());
	dmh.getParams().put("CODICEDOCUMENTO", idLettera.toString());
	dmh.setUuidLinkTemp(uuidTempLink);
	return dmh;
    }
}
