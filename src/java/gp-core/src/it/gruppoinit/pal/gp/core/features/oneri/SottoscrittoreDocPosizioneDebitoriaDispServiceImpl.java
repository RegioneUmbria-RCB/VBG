package it.gruppoinit.pal.gp.core.features.oneri;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paevolution.ws.pagamenti_types.ElencoDocumentiEsitoType;
import com.paevolution.ws.pagamenti_types.EsitoDocumentoPosizioneDebitoriaType;
import com.paevolution.ws.pagamenti_types.TipoDocumentoType;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatch;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatchId.TipoDocumentoDaGenerare;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoDocumentoPosizioneDebitoriaDisponibile;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;

@Service
public class SottoscrittoreDocPosizioneDebitoriaDispServiceImpl implements IEventSubscriber<EventoDocumentoPosizioneDebitoriaDisponibile> {

    private static final Logger log = LoggerFactory.getLogger(SottoscrittoreDocPosizioneDebitoriaDispServiceImpl.class);
    private VerticalizzazioniService verticalizzazioniService;
    private NodoPagamentiService nodoPagamentiService;
    private OggettiService oggettiService;
    private MovimentiService movimentiService;
    private TipiMovimentoService tipiMovimentoService;
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    private IstanzeService istanzeService;

    @Autowired
    public SottoscrittoreDocPosizioneDebitoriaDispServiceImpl(VerticalizzazioniService verticalizzazioniService,
	    NodoPagamentiService nodoPagamentiService, OggettiService oggettiService, MovimentiService movimentiService,
	    TipiMovimentoService tipiMovimentoService, DettPosizioneDebitoriaService dettPosizioneDebitoriaService, IstanzeService istanzeService) {

	super();
	this.verticalizzazioniService = verticalizzazioniService;
	this.nodoPagamentiService = nodoPagamentiService;
	this.oggettiService = oggettiService;
	this.movimentiService = movimentiService;
	this.tipiMovimentoService = tipiMovimentoService;
	this.dettPosizioneDebitoriaService = dettPosizioneDebitoriaService;
	this.istanzeService = istanzeService;
    }

    @Override
    public void onEvent(EventoDocumentoPosizioneDebitoriaDisponibile e) {

	// VIENE INTERCETTATO
	// a seconda del tipo documento generato 
	// verifica se presente la verticalizzazione e se non presente rilancia eccezione
	VerticalizzazioneNodoPagamentiServiceImpl v = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, e.getCodiceComune());
	if (!verticalizzazioniService.isAttiva(VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE)) {
	    log.error("La verticalizzazione {} ,non è stata impostata!", VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE);
	    throw new RuntimeException(
		    "La verticalizzazione " + VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE + " non è stata impostata!");
	}
	// Effettua la chiamata a InviaAvvisoPagamento o generaFattura a seconda del tipo di documento e lo salva nella tabella oggetti
	// prende il parametro di verticalizzazione (che corrisponde all'informazione tipo movimento) e inserisce il movimento e il movimento allegato per quel tipo di documento.
	// I parametri di verticalizzazione sono specifici per il tipo di documento
	// NODO_PAGAMENTI.TIPOMOVIMENTO_DOC_FATTURA
	// NODO_PAGAMENTI.TIPOMOVIMENTO_DOC_AVVISO
	//Se non c'è il parametro della verticalizzazione rilancia una eccezione.
	Map<TipoDocumentoDaGenerare, List<IstanzeoneriPosdebBatch>> perTipoDocumento = new HashMap<TipoDocumentoDaGenerare, List<IstanzeoneriPosdebBatch>>();
	log.debug("Popolo la mappa per tipo documento ");
	List<IstanzeoneriPosdebBatch> listaRecord = e.getListaRecord();
	for (IstanzeoneriPosdebBatch istanzeoneriPosdebBatch : listaRecord) {
	    TipoDocumentoDaGenerare tipoDocumento = istanzeoneriPosdebBatch.getId().getTipoDocumento();
	    if (perTipoDocumento.get(tipoDocumento) == null) {
		perTipoDocumento.put(tipoDocumento, new ArrayList<IstanzeoneriPosdebBatch>());
	    }
	    perTipoDocumento.get(tipoDocumento).add(istanzeoneriPosdebBatch);
	}
	for (Entry<TipoDocumentoDaGenerare, List<IstanzeoneriPosdebBatch>> m : perTipoDocumento.entrySet()) {
	    TipoDocumentoDaGenerare tipoDocumento = m.getKey();
	    log.debug("Elaboro per tipo documento {}", tipoDocumento);
	    elaboraPerTipoDocumento(tipoDocumento, m.getValue(), e.getCodiceIstanza(), v);
	}
    }

    private void elaboraPerTipoDocumento(TipoDocumentoDaGenerare tddg, List<IstanzeoneriPosdebBatch> listaRecord, Integer codiceIstanza,
	    VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiService) {

	if (tddg.name().equals(TipoDocumentoType.AVVISO.name()) && verticalizzazioneNodoPagamentiService.tipomovimentoDocAvviso() == null) {
	    log.error("Il parametro della verticalizzazione" + VerticalizzazioneNodoPagamentiServiceImpl.TIPOMOVIMENTO_DOC_AVVISO +
		      " non è impostato correttamente!");
	    throw new RuntimeException("Il parametro della verticalizzazione" + VerticalizzazioneNodoPagamentiServiceImpl.TIPOMOVIMENTO_DOC_AVVISO +
				       " non è impostato correttamente!");
	} else if (tddg.name().equals(TipoDocumentoType.FATTURA.name()) && verticalizzazioneNodoPagamentiService.tipomovimentoDocFattura() == null) {
	    log.error("Il parametro della verticalizzazione " + VerticalizzazioneNodoPagamentiServiceImpl.TIPOMOVIMENTO_DOC_FATTURA +
		      " non è impostato correttamente!");
	    throw new RuntimeException("Il parametro della verticalizzazione " + VerticalizzazioneNodoPagamentiServiceImpl.TIPOMOVIMENTO_DOC_FATTURA +
				       " non è impostato correttamente!");
	}
	try {
	    ElencoDocumentiEsitoType documentiEsitoType = null;
	    log.debug("elaboraPerTipoDocumento: ciclo i record da elaborare");
	    Movimenti movimento = new Movimenti();
	    Set<Movimentiallegati> malls = new HashSet<Movimentiallegati>();
	    // DA RICORDARSI
	    IstanzeoneriPosdebBatch istanzeoneriPosdebBatch = listaRecord.get(0); // PER CONVENZIONE UN DEBITO 
										  // AVVISO O FATTURA DOVREBBE AVERE UN SOLO DOCUMENTO DISPONIBILE E NON 
										  // N° A SECONDA DELLE RATE PER CUI SCARICO IL PRIMO DOCUMENTO DISPONIBILE E SEGNO LE ALTRE EVENTUALI COME 
										  // COMPLETATE
										  // for (IstanzeoneriPosdebBatch istanzeoneriPosdebBatch : listaRecord) { 
	    Integer idDettPosizioneDebitoria = istanzeoneriPosdebBatch.getId().getIdDettPosizioneDebitoria();
	    log.debug("elaboraPerTipoDocumento: {}", istanzeoneriPosdebBatch.getId());
	    if (tddg.name().equals(TipoDocumentoType.AVVISO.name())) {
		documentiEsitoType = nodoPagamentiService.inviaAvviso(idDettPosizioneDebitoria);
	    } else if (tddg.name().equals(TipoDocumentoType.FATTURA.name())) {
		documentiEsitoType = nodoPagamentiService.generaFattura(idDettPosizioneDebitoria);
	    } else {
		throw new RuntimeException("Il tipo documento " + tddg.name() + " non è impostato correttamente!");
	    }
	    for (EsitoDocumentoPosizioneDebitoriaType doc : documentiEsitoType.getEsitoPosizione()) {
		Oggetti ogg = new Oggetti();
		String nomeDocumento = StringUtils.defaultString(doc.getNomeDocumento());
		if (!nomeDocumento.toLowerCase().endsWith(".pdf")) {
		    nomeDocumento = nomeDocumento + ".pdf";
		}
		ogg.setNomefile(nomeDocumento);
		try {
		    InputStream is = doc.getDocumento().getInputStream();
		    byte[] documento = org.apache.commons.io.IOUtils.toByteArray(is);
		    ogg.setOggetto(documento);
		} catch (IOException e1) {
		    throw new RuntimeException(e1);
		}
		oggettiService.insert(ogg);
		Movimentiallegati movallegati = new Movimentiallegati();
		movallegati.setMovimento(movimento);
		movallegati.setOggetto(ogg);
		DettPosizioneDebitoria dettposdeb = dettPosizioneDebitoriaService.findById(new PkId(idDettPosizioneDebitoria));
		String descr = "Documento di " + tddg.name() + " della posizione debitoria " + dettposdeb.getDescrizioneCausale() + " con id " +
			       dettposdeb.getId().getCodice();
		movallegati.setDescrizione(descr);
		malls.add(movallegati);
	    }
	    // }
	    Tipimovimento tipimov = new Tipimovimento();
	    if (tddg.name().equals(TipoDocumentoType.FATTURA.name())) {
		tipimov = tipiMovimentoService.findById(new TipimovimentoId(verticalizzazioneNodoPagamentiService.tipomovimentoDocFattura()));
	    } else if (tddg.name().equals(TipoDocumentoType.AVVISO.name())) {
		tipimov = tipiMovimentoService.findById(new TipimovimentoId(verticalizzazioneNodoPagamentiService.tipomovimentoDocAvviso()));
	    }
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    movimento.setTipomovimento(tipimov);
	    movimento.setIstanza(istanza);
	    movimento.setData(Calendar.getInstance().getTime());
	    movimento.setMovimentiallegatis(malls);
	    movimentiService.insert(movimento);
	} catch (FunzioneBusinessRemotaException e1) {
	    throw new RuntimeException(e1);
	}
    }
}
