package it.gruppoinit.pal.gp.areariservata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.areariservata.domain.AltriSoggettiTypeHelper;
import it.gruppoinit.pal.gp.areariservata.domain.CampoSchedaHelper;
import it.gruppoinit.pal.gp.areariservata.domain.DocumentoHelper;
import it.gruppoinit.pal.gp.areariservata.domain.EstremiAttoTypeHelper;
import it.gruppoinit.pal.gp.areariservata.domain.ProcedimentoHelper;
import it.gruppoinit.pal.gp.areariservata.domain.SchedaHelper;
import it.gruppoinit.pal.gp.areariservata.service.FoArjDomandeHelperService;
import it.gruppoinit.pal.gp.areariservata.service.NuovaIstanzaProcedimentiService;
import it.gruppoinit.pal.gp.areariservata.service.TipiSoggettoARJService;
import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.gruppoinit.pal.gp.areariservata.web.util.StcDomainHelper;
import it.gruppoinit.pal.gp.areariservata.web.util.TipiSoggettoTipoDato;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeOneriService;
import it.gruppoinit.pal.gp.core.service.helper.TipoDocumentoType;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AltriSoggettiType;
import it.init.sigepro.rte.types.CampoSchedaType;
import it.init.sigepro.rte.types.CausaleOnereType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.NuovaIstanzaType;
import it.init.sigepro.rte.types.OneriPagamentiType;
import it.init.sigepro.rte.types.OneriScadenzeType;
import it.init.sigepro.rte.types.OneriType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RiferimentoCatastaleType;
import it.init.sigepro.rte.types.SchedaType;
import it.init.sigepro.rte.types.SegnoType;
import it.init.sigepro.rte.types.VersioneType;

@Service
public class FoArjDomandeHelperServiceImpl implements FoArjDomandeHelperService {

    private static final Logger log = LoggerFactory.getLogger(FoArjDomandeHelperServiceImpl.class);
    private static final String PAGAMENTI_AREA_RISERVATA_PREFIX = "PAGAMENTI AREA RISERVATA: ";
    @Autowired
    private TipiSoggettoARJService tipiSoggettoARJService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private FoArjDomandeOneriService foArjDomandeOneriService;
    @Autowired
    private NuovaIstanzaProcedimentiService nuovaIstanzaProcedimentiService;

    @Override
    public NuovaIstanzaType populateNuovaIstanzaType(NuovaIstanzaCommand cmd) {

	NuovaIstanzaType nuovaIstanzaType = new NuovaIstanzaType();
	nuovaIstanzaType.setVersione(VersioneType.V_1_0);
	DettaglioPraticaType pra = new DettaglioPraticaType();
	//id e numero pratica
	pra.setIdPratica(cmd.getIdDomanda());
	pra.setNumeroPratica(cmd.getIdDomanda());
	//anagrafe singola
	boolean richiedenteAggiunto = false;
	boolean tecnicoAggiunto = false;
	List<AltriSoggettiType> altriSogg = new ArrayList<AltriSoggettiType>();
	for (AltriSoggettiTypeHelper astH : cmd.getAltriSoggettiHelper()) {
	    String tipo = astH.getTipoSoggetto().getTipodato();
	    boolean aggiungiAdAltri = true;
	    if (TipiSoggettoTipoDato.R.name().equals(tipo)) {
		if (!richiedenteAggiunto) {
		    RichiedenteType rt = StcDomainHelper.getNewRichiedenteType();
		    rt.setAnagrafica(astH.getSoggetto().getSoggetto().getPersonaFisica());
		    rt.setRuolo(astH.getSoggetto().getTipoRapporto());
		    pra.setRichiedente(rt);
		    if (!EntityUtils.isNestedPropertyBlank(astH.getSoggetto(), "anagraficaCollegata.personaGiuridica.ragioneSociale")) {
			pra.setAziendaRichiedente(astH.getSoggetto().getAnagraficaCollegata().getPersonaGiuridica());
		    }
		    richiedenteAggiunto = true;
		    aggiungiAdAltri = false;
		}
	    } else if (TipiSoggettoTipoDato.T.name().equals(tipo)) {
		if (!tecnicoAggiunto) {
		    pra.setIntermediario(astH.getSoggetto().getSoggetto());
		    tecnicoAggiunto = true;
		    aggiungiAdAltri = false;
		}
	    }
	    if (aggiungiAdAltri) {
		altriSogg.add(astH.getSoggetto());
	    }
	}
	rimuoviAziendaRichiedenteDaAltriSoggetti(pra, altriSogg);
	pra.getAltriSoggetti().clear();
	pra.getAltriSoggetti().addAll(altriSogg);
	//domicilio elettronico
	pra.setDomicilioElettronico(cmd.getDomicilioElettronico());
	//intervento
	if (!EntityUtils.isNestedPropertyBlank(cmd, "intervento.codice")) {
	    pra.setIntervento(cmd.getIntervento());
	}
	//oggetto
	pra.setOggetto(cmd.getOggetto());
	//procedimenti
	for (ProcedimentoHelper pH : cmd.getProcedimentiSelezionati()) {
	    pH.getProcedimento().getDocumenti().clear();
	    for (DocumentoHelper docHelp : cmd.getAllegatiProcedimentiCaricati()) {
		if (docHelp.getProcedimentoId().equals(pH.getProcedimento().getCodice())) {
		    pH.getProcedimento().getDocumenti().add(docHelp.getDoc());
		}
	    }
	    pra.getProcedimenti().add(pH.getProcedimento());
	}
	//catasto
	Map<Integer, RiferimentoCatastaleType> rifCatastali = cmd.getRiferimentiCatastali();
	if (rifCatastali != null) {
	    cmd.getLocalizzazione().getRiferimentoCatastale().clear();
	    for (Map.Entry<Integer, RiferimentoCatastaleType> entry : rifCatastali.entrySet()) {
		cmd.getLocalizzazione().getRiferimentoCatastale().add(entry.getValue());
	    }
	}
	//localizzazione
	if (!EntityUtils.isNestedPropertyBlank(cmd, "localizzazione.denominazione")) {
	    pra.getLocalizzazione().add(cmd.getLocalizzazione());
	}
	//documenti
	for (DocumentoHelper docH : cmd.getAllegatiInterventoCaricati()) {
	    pra.getDocumenti().add(docH.getDoc());
	}
	//schede
	pra.getSchede().clear();
	for (SchedaHelper schedaH : cmd.getSchede()) {
	    SchedaType s = schedaH.getScheda();
	    s.getCampi().clear();
	    for (CampoSchedaHelper campoH : schedaH.getCampi()) {
		s.getCampi().add(campoH.getCampo());
	    }
	    pra.getSchede().add(s);
	}
	//allegati schede
	nuovaIstanzaType.getListaPDFSchedeDinamiche().addAll(cmd.getListaPDFSchede());
	//oneri
	if (cmd.getId() != null) {
	    populateOneri(pra, cmd.getId());
	}
	nuovaIstanzaType.setDettaglioPratica(pra);
	return nuovaIstanzaType;
    }

    private void rimuoviAziendaRichiedenteDaAltriSoggetti(DettaglioPraticaType pra, List<AltriSoggettiType> altriSogg) {

	AltriSoggettiType soggDaRimuovere = null;
	if (!EntityUtils.isNestedPropertyBlank(pra.getAziendaRichiedente(), "ragioneSociale")) {
	    for (AltriSoggettiType altroSogg : altriSogg) {
		if (!EntityUtils.isNestedPropertyBlank(altroSogg.getSoggetto(), "personaGiuridica.ragioneSociale")) {
		    if (altroSogg.getSoggetto().getPersonaGiuridica().getRagioneSociale().equals(pra.getAziendaRichiedente().getRagioneSociale())) {
			soggDaRimuovere = altroSogg;
			break;
		    }
		}
	    }
	}
	if (soggDaRimuovere != null) {
	    altriSogg.remove(soggDaRimuovere);
	}
    }

    @Override
    public void populateNuovaIstanzaCommand(NuovaIstanzaCommand cmd, NuovaIstanzaType nuovaIstanzaType) {

	DettaglioPraticaType pra = nuovaIstanzaType.getDettaglioPratica();
	//informativa
	cmd.getInformativa().setAccettata(true);
	//anagrafe singola
	List<AltriSoggettiTypeHelper> soggetti = new ArrayList<AltriSoggettiTypeHelper>();
	if (!EntityUtils.isNestedPropertyBlank(pra, "richiedente.anagrafica.codiceFiscale")) {
	    AltriSoggettiType astRichiedente = StcDomainHelper.getNewAltriSoggettiType();
	    astRichiedente.getSoggetto().setPersonaFisica(pra.getRichiedente().getAnagrafica());
	    AltriSoggettiTypeHelper astHRichiedente = new AltriSoggettiTypeHelper(astRichiedente);
	    if (!EntityUtils.isNestedPropertyBlank(pra, "richiedente.ruolo.idRuolo")) {
		astRichiedente.setTipoRapporto(pra.getRichiedente().getRuolo());
		Tipisoggetto tipoSoggettoRich = tipiSoggettoARJService.findById(new PkId(new Integer(pra.getRichiedente().getRuolo().getIdRuolo())));
		astHRichiedente.setTipoSoggetto(tipoSoggettoRich);
	    }
	    if (!EntityUtils.isNestedPropertyBlank(pra, "aziendaRichiedente.codiceFiscale")
		    || !EntityUtils.isNestedPropertyBlank(pra, "aziendaRichiedente.partitaIva")) {
		astRichiedente.getAnagraficaCollegata().setPersonaGiuridica(pra.getAziendaRichiedente());
	    }
	    soggetti.add(astHRichiedente);
	}
	Integer codiceIntervento = null;
	if (cmd.getIntervento() != null) {
	    if (StringUtils.isNotBlank(StringUtils.defaultIfEmpty(cmd.getIntervento().getCodice(), "").trim())) {
		try {
		    codiceIntervento = Integer.parseInt(StringUtils.defaultIfEmpty(cmd.getIntervento().getCodice(), "").trim());
		} catch (Exception e) {
		    log.error("populateNuovaIstanzaCommand# codiceIntervento non valido: {}", cmd.getIntervento().getCodice());
		}
	    }
	}
	if (!EntityUtils.isNestedPropertyBlank(pra, "aziendaRichiedente.codiceFiscale")
		|| !EntityUtils.isNestedPropertyBlank(pra, "aziendaRichiedente.partitaIva")) {
	    AltriSoggettiType astAzienda = StcDomainHelper.getNewAltriSoggettiType();
	    astAzienda.getSoggetto().setPersonaGiuridica(pra.getAziendaRichiedente());
	    AltriSoggettiTypeHelper astHAzienda = new AltriSoggettiTypeHelper(astAzienda);
	    List<Tipisoggetto> tipiSoggA = tipiSoggettoARJService.findTipiSoggettoPerInterventoAndTipoDato(codiceIntervento, TipiSoggettoTipoDato.A);
	    if (!tipiSoggA.isEmpty()) {
		Tipisoggetto tipoSoggA = tipiSoggA.get(0);
		astAzienda.getTipoRapporto().setIdRuolo(tipoSoggA.getId().getCodice().toString());
		astAzienda.getTipoRapporto().setRuolo(tipoSoggA.getTiposoggetto());
		astHAzienda.setTipoSoggetto(tipoSoggA);
	    }
	    soggetti.add(astHAzienda);
	}
	if (!EntityUtils.isNestedPropertyBlank(pra, "intermediario.personaFisica.codiceFiscale")) {
	    AltriSoggettiType astTecnico = StcDomainHelper.getNewAltriSoggettiType();
	    astTecnico.getSoggetto().setPersonaFisica(pra.getIntermediario().getPersonaFisica());
	    AltriSoggettiTypeHelper astHTecnico = new AltriSoggettiTypeHelper(astTecnico);
	    List<Tipisoggetto> tipiSoggT = tipiSoggettoARJService.findTipiSoggettoPerInterventoAndTipoDato(codiceIntervento, TipiSoggettoTipoDato.T);
	    if (!tipiSoggT.isEmpty()) {
		Tipisoggetto tipoSoggT = tipiSoggT.get(0);
		astTecnico.getTipoRapporto().setIdRuolo(tipoSoggT.getId().getCodice().toString());
		astTecnico.getTipoRapporto().setRuolo(tipoSoggT.getTiposoggetto());
		astHTecnico.setTipoSoggetto(tipoSoggT);
	    }
	    soggetti.add(astHTecnico);
	}
	for (AltriSoggettiType ast : pra.getAltriSoggetti()) {
	    AltriSoggettiTypeHelper astH = new AltriSoggettiTypeHelper(ast);
	    if (!EntityUtils.isNestedPropertyBlank(ast, "tipoRapporto.idRuolo")) {
		Tipisoggetto tipoSoggetto = tipiSoggettoARJService.findById(new PkId(new Integer(ast.getTipoRapporto().getIdRuolo())));
		astH.setTipoSoggetto(tipoSoggetto);
	    }
	    soggetti.add(astH);
	}
	cmd.setAltriSoggettiHelper(soggetti);
	//domicilio elettronico
	cmd.setDomicilioElettronico(pra.getDomicilioElettronico());
	//localizzazione e catasto
	for (LocalizzazioneNelComuneType loc : pra.getLocalizzazione()) {
	    cmd.setLocalizzazione(loc);
	    for (RiferimentoCatastaleType cat : loc.getRiferimentoCatastale()) {
		int size = cmd.getRiferimentiCatastali().size();
		Integer idx = Integer.valueOf(size + 1);
		cmd.getRiferimentiCatastali().put(idx, cat);
	    }
	}
	//oggetto
	cmd.setOggetto(pra.getOggetto());
	if (!EntityUtils.isNestedPropertyBlank(pra.getIntervento(), "codice")) {
	    //intervento
	    cmd.setIntervento(pra.getIntervento());
	    cmd.setInterventoProcedimenti(Integer.valueOf(pra.getIntervento().getCodice()));
	    //procedimenti
	    for (ProcedimentoType pt : pra.getProcedimenti()) {
		ProcedimentoHelper ph = nuovaIstanzaProcedimentiService.getProcedimento(Integer.valueOf(pt.getCodice()));
		ph.setProcedimento(pt);
		cmd.getProcedimentiSelezionati().add(ph);
		if (ph.isTipiTitoloPresent()) {
		    cmd.getEstremiAtto().add(new EstremiAttoTypeHelper(ph.getProcedimento().getEstremiAtto(), ph));
		}
	    }
	    //allegati procedimenti
	    for (ProcedimentoType proc : pra.getProcedimenti()) {
		for (DocumentiType doc : proc.getDocumenti()) {
		    DocumentoHelper docHelp = new DocumentoHelper(doc);
		    docHelp.setProcedimentoId(proc.getCodice());
		    cmd.getAllegatiProcedimentiCaricati().add(docHelp);
		}
	    }
	    //allegati itervento
	    for (DocumentiType doc : pra.getDocumenti()) {
		DocumentoHelper docHelp = new DocumentoHelper(doc);
		docHelp.setInterventoId(pra.getIntervento().getCodice());
		cmd.getAllegatiInterventoCaricati().add(docHelp);
	    }
	    //schede
	    for (SchedaType scheda : pra.getSchede()) {
		SchedaHelper schedaH = new SchedaHelper(scheda);
		schedaH.setConfirmed(true);
		for (CampoSchedaType campo : scheda.getCampi()) {
		    CampoSchedaHelper campoH = new CampoSchedaHelper();
		    campoH.setCampo(campo);
		    schedaH.getCampi().add(campoH);
		}
		cmd.getSchede().add(schedaH);
	    }
	    //allegati schede
	    cmd.getListaPDFSchede().addAll(nuovaIstanzaType.getListaPDFSchedeDinamiche());
	}
    }

    private void populateOneri(DettaglioPraticaType pra, Integer codiceDomanda) {

	List<OneriType> ot = new ArrayList<OneriType>();
	List<FoArjDomandeOneri> oneris = foArjDomandeOneriService.findByIdDomanda(codiceDomanda);
	// cancello i documenti contrassegnati da "PAGAMENTI AREA RISERVATA:"
	List<DocumentiType> result = new ArrayList<DocumentiType>();
	result.addAll(pra.getDocumenti());
	List<DocumentiType> docs = pra.getDocumenti();
	for (DocumentiType doc : docs) {
	    if (doc.getDocumento().startsWith(PAGAMENTI_AREA_RISERVATA_PREFIX)) {
		if (StringUtils.isNotBlank(doc.getTipoDocumento())) {
		    if (doc.getTipoDocumento().equalsIgnoreCase(TipoDocumentoType.ALTRO.name())) {
			// 
			result.remove(doc);
		    }
		}
	    }
	}
	pra.getDocumenti().clear();
	pra.getDocumenti().addAll(result);
	Map<Integer, String> mappaDoc = new HashMap<Integer, String>();
	for (FoArjDomandeOneri fado : oneris) {
	    if (fado.getImporto() != null) {
		OneriType onere = new OneriType();
		CausaleOnereType causale = new CausaleOnereType();
		causale.setId(String.valueOf(fado.getTipicausalioneri().getId().getCodice()));
		causale.setCausale(fado.getTipicausalioneri().getCoDescrizione());
		onere.setCausale(causale);
		if (fado.getInventarioprocedimenti() != null) {
		    onere.setCodiceProcedimento(String.valueOf(fado.getInventarioprocedimenti().getId().getCodice()));
		}
		onere.setImporto(fado.getImporto().doubleValue());
		onere.setSegno(SegnoType.ENTRATA);
		OneriScadenzeType scadenza = new OneriScadenzeType();
		scadenza.setImportoRata(onere.getImporto());
		scadenza.setNumeroRata("1");
		scadenza.setDataScadenza(Utilities.getToday());
		if (BooleanUtils.isTrue(fado.getFlagStato())) {
		    OneriPagamentiType pag = new OneriPagamentiType();
		    pag.setData(Utilities.getToday());
		    pag.setImporto(onere.getImporto());
		    if (BooleanUtils.isTrue(fado.getFlagOnline())) {
			pag.setModalita("ONLINE");
		    }
		    pag.setRifDocumento(fado.getIdordineSistemaPagamenti());
		    scadenza.getPagamenti().add(pag);
		}
		onere.getScadenze().add(scadenza);
		ot.add(onere);
		gestMappaDocumenti(mappaDoc, fado.getOggettoPdf(), fado, false);
		gestMappaDocumenti(mappaDoc, fado.getOggettoXml(), fado, true);
	    }
	}
	if (mappaDoc.size() > 0) {
	    for (Entry<Integer, String> m : mappaDoc.entrySet()) {
		Integer codiceOggetto = m.getKey();
		String descrizione = m.getValue();
		DocumentiType doc = documentoOnere(codiceOggetto, descrizione);
		if (doc != null) {
		    pra.getDocumenti().add(doc);
		}
	    }
	}
	// rimuovere tutti i documenti precedentemente caricati
	pra.getOneri().clear();
	pra.getOneri().addAll(ot);
    }

    private void gestMappaDocumenti(Map<Integer, String> mappaDoc, Oggetti oggetto, FoArjDomandeOneri fado, boolean isXml) {

	Integer codiceOggetto = null;
	if (oggetto != null) {
	    if (oggetto.getId() != null) {
		if (oggetto.getId().getCodice() != null) {
		    codiceOggetto = oggetto.getId().getCodice();
		}
	    }
	}
	if (codiceOggetto != null) {
	    String descrizione = mappaDoc.get(codiceOggetto);
	    if (descrizione == null) {
		descrizione = new String();
	    }
	    descrizione += descrizioneDocumentoOneri(fado, isXml);
	    mappaDoc.put(codiceOggetto, descrizione);
	}
    }

    private String descrizioneDocumentoOneri(FoArjDomandeOneri fado, boolean isXml) {

	String dd = "\n - ";//PAGAMENTI_AREA_RISERVATA_PREFIX + "ricevuta " + (isXml ? "xml" : "pdf") + " pagamento oneri ";
	if (fado.getInventarioprocedimenti() != null) {
	    dd += "dell'endoprocedimento [" + fado.getInventarioprocedimenti().getProcedimento() + "]";
	} else {
	    dd += "dell'intervento ";
	}
	dd += " con causale [" + fado.getTipicausalioneri().getCoDescrizione() + "]";
	return dd;
    }

    private DocumentiType documentoOnere(Integer codiceOggetto, String descrizione) {

	if (codiceOggetto != null) {
	    Oggetti o = oggettiService.findByIdLazy(new PkId(codiceOggetto));
	    DocumentiType doc = new DocumentiType();
	    doc.setId(String.valueOf(codiceOggetto));
	    String dd = PAGAMENTI_AREA_RISERVATA_PREFIX + "ricevuta pagamento oneri: ";
	    dd += descrizione;
	    doc.setDocumento(dd);
	    doc.setTipoDocumento(TipoDocumentoType.ALTRO.value());
	    AllegatiType allegato = new AllegatiType();
	    allegato.setId(String.valueOf(codiceOggetto));
	    allegato.setAllegato(o.getNomefile());
	    doc.setAllegati(allegato);
	    return doc;
	}
	return null;
    }
}
