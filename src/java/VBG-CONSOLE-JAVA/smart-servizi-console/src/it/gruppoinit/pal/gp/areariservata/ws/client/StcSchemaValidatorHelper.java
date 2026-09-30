package it.gruppoinit.pal.gp.areariservata.ws.client;

import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AllegatoBinarioType;
import it.init.sigepro.rte.types.AltriSoggettiType;
import it.init.sigepro.rte.types.AnagrafeType;
import it.init.sigepro.rte.types.CircoscrizioneType;
import it.init.sigepro.rte.types.CittadinanzaType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.FrazioneType;
import it.init.sigepro.rte.types.IscrizioneRegistroType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.LocalizzazioneType;
import it.init.sigepro.rte.types.NuovaIstanzaType;
import it.init.sigepro.rte.types.PDFSchedaDinamicaBloccoType;
import it.init.sigepro.rte.types.PDFSchedaDinamicaType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.QuartiereType;
import it.init.sigepro.rte.types.RegistroREAType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RiferimentoCatastaleType;
import it.init.sigepro.rte.types.RuoloType;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

public class StcSchemaValidatorHelper {

    private DettaglioPraticaType dpt = null;
    private NuovaIstanzaType nit = null;

    public DettaglioPraticaType getDettaglioPraticaPerInvioSTC(NuovaIstanzaType nuovaIstanzaType) {

	this.nit = nuovaIstanzaType;
	this.dpt = nuovaIstanzaType.getDettaglioPratica();
	if (StringUtils.isBlank(this.dpt.getNumeroProtocolloGenerale())) {
	    this.dpt.setNumeroProtocolloGenerale(null);
	}
	bonificaCodiceComune();
	if (StringUtils.isBlank(this.dpt.getDomicilioElettronico())) {
	    this.dpt.setDomicilioElettronico(null);
	}
	bonificaRichiedente();
	bonificaAziendaRichiedente();
	//TODO procure (non utilizzato)
	bonificaAltriSoggetti();
	bonificaIntervento();
	bonificaLocalizzazione();
	bonificaIntermediario();
	bonificaDocumenti();
	//TODO altriDati (non utilizzato)
	bonificaProcedimenti();
	//TODO schede
	//TODO oneri(non utilizzato)
	if (StringUtils.isBlank(this.dpt.getAnnotazioni())) {
	    this.dpt.setAnnotazioni(null);
	}
	return this.dpt;
    }

    private void bonificaCodiceComune() {

	if (dpt.getCodiceComune() != null && isToRemoveComuneType(dpt.getCodiceComune())) {
	    dpt.setCodiceComune(null);
	}
    }

    private void bonificaRichiedente() {

	if (dpt.getRichiedente() != null) {
	    if (isToRemoveRichiedenteType(dpt.getRichiedente())) {
		dpt.setRichiedente(null);
	    } else {
		bonificaRichiedenteType(dpt.getRichiedente());
	    }
	}
    }

    private void bonificaIntervento() {

	if (dpt.getIntervento() != null) {
	    if (StringUtils.isBlank(dpt.getIntervento().getDescrizione())) {
		dpt.setIntervento(null);
	    } else {
		if (StringUtils.isBlank(dpt.getIntervento().getCodice())) {
		    dpt.getIntervento().setCodice(null);
		}
	    }
	}
    }

    private boolean isToRemoveRichiedenteType(RichiedenteType rich) {

	if (rich.getAnagrafica() == null || isToRemovePersonaFisicaType(rich.getAnagrafica())) {
	    return true;
	}
	return false;
    }

    private void bonificaRuoloType(RuoloType ruolo) {

	if (StringUtils.isBlank(ruolo.getIdRuolo())) {
	    ruolo.setIdRuolo(null);
	}
    }

    private void bonificaRichiedenteType(RichiedenteType rich) {

	if (rich.getRuolo() != null) {
	    if (isToRemoveRuoloType(rich.getRuolo())) {
		rich.setRuolo(null);
	    } else {
		bonificaRuoloType(rich.getRuolo());
	    }
	}
	bonificaPersonaFisicaType(rich.getAnagrafica());
    }

    private void bonificaPersonaFisicaType(PersonaFisicaType pft) {

	if (StringUtils.isBlank(pft.getTitolo())) {
	    pft.setTitolo(null);
	}
	if (StringUtils.isBlank(pft.getSesso())) {
	    pft.setSesso(null);
	}
	if (pft.getComuneNascita() != null && isToRemoveComuneType(pft.getComuneNascita())) {
	    pft.setComuneNascita(null);
	}
	if (pft.getResidenza() != null) {
	    if (isToRemoveLocalizzazioneType(pft.getResidenza())) {
		pft.setResidenza(null);
	    } else {
		bonificaLocalizzazioneType(pft.getResidenza());
	    }
	}
	if (pft.getCorrispondenza() != null) {
	    if (isToRemoveLocalizzazioneType(pft.getCorrispondenza())) {
		pft.setCorrispondenza(null);
	    } else {
		bonificaLocalizzazioneType(pft.getCorrispondenza());
	    }
	}
	if (pft.getCittadinanza() != null) {
	    if (isToRemoveCittadinanzaType(pft.getCittadinanza())) {
		pft.setCittadinanza(null);
	    } else {
		bonificaCittadinanzaType(pft.getCittadinanza());
	    }
	}
	//TODO altriDati (non utilizzato)
	if (pft.getProcura() != null) {
	    if (isToRemoveDocumentiType(pft.getProcura())) {
		pft.setProcura(null);
	    } else {
		bonificaDocumentiType(pft.getProcura());
	    }
	}
	if (StringUtils.isBlank(pft.getTelefono())) {
	    pft.setTelefono(null);
	}
	if (StringUtils.isBlank(pft.getEmail())) {
	    pft.setEmail(null);
	}
	if (StringUtils.isBlank(pft.getPec())) {
	    pft.setPec(null);
	}
    }

    private boolean isToRemovePersonaGiuridicaType(PersonaGiuridicaType pgt) {

	if (StringUtils.isBlank(pgt.getRagioneSociale())) {
	    return true;
	}
	return false;
    }

    private void bonificaPersonaGiuridicaType(PersonaGiuridicaType pgt) {

	if (StringUtils.isBlank(pgt.getPartitaIva())) {
	    pgt.setPartitaIva(null);
	}
	if (StringUtils.isBlank(pgt.getCodiceFiscale())) {
	    pgt.setCodiceFiscale(null);
	}
	if (StringUtils.isBlank(pgt.getNaturaGiuridica())) {
	    pgt.setNaturaGiuridica(null);
	}
	if (pgt.getSedeLegale() != null) {
	    if (isToRemoveLocalizzazioneType(pgt.getSedeLegale())) {
		pgt.setSedeLegale(null);
	    } else {
		bonificaLocalizzazioneType(pgt.getSedeLegale());
	    }
	}
	if (pgt.getIndirizzoCorrispondenza() != null) {
	    if (isToRemoveLocalizzazioneType(pgt.getIndirizzoCorrispondenza())) {
		pgt.setIndirizzoCorrispondenza(null);
	    } else {
		bonificaLocalizzazioneType(pgt.getIndirizzoCorrispondenza());
	    }
	}
	if (StringUtils.isBlank(pgt.getTelefono())) {
	    pgt.setTelefono(null);
	}
	if (StringUtils.isBlank(pgt.getFax())) {
	    pgt.setFax(null);
	}
	pgt.setLegaleRappresentante(null);
	if (pgt.getIscrizioneCCIAA() != null) {
	    if (isToRemoveIscrizioneCCIAA(pgt.getIscrizioneCCIAA())) {
		pgt.setIscrizioneCCIAA(null);
	    } else {
		bonificaIscrizioneCCIAA(pgt.getIscrizioneCCIAA());
	    }
	}
	if (pgt.getIscrizioneREA() != null) {
	    if (isToRemoveIscrizioneREA(pgt.getIscrizioneREA())) {
		pgt.setIscrizioneREA(null);
	    } else {
		bonificaIscrizioneREA(pgt.getIscrizioneREA());
	    }
	}
	//TODO altriDati (non utilizzato)
	if (StringUtils.isBlank(pgt.getEmail())) {
	    pgt.setEmail(null);
	}
	if (StringUtils.isBlank(pgt.getPec())) {
	    pgt.setPec(null);
	}
    }

    private boolean isToRemoveIscrizioneREA(RegistroREAType rrt) {

	if (StringUtils.isBlank(rrt.getNumero()) || StringUtils.isBlank(rrt.getSiglaProvincia()) || rrt.getData() == null) {
	    return true;
	}
	return false;
    }

    private void bonificaIscrizioneREA(RegistroREAType rrt) {

	if (StringUtils.isBlank(rrt.getNumero())) {
	    rrt.setNumero(null);
	}
	if (StringUtils.isBlank(rrt.getSiglaProvincia())) {
	    rrt.setSiglaProvincia(null);
	}
    }

    private boolean isToRemoveIscrizioneCCIAA(IscrizioneRegistroType irt) {

	if (StringUtils.isBlank(irt.getNumero()) && irt.getData() == null && (irt.getComune() == null || isToRemoveComuneType(irt.getComune()))) {
	    return true;
	}
	return false;
    }

    private void bonificaIscrizioneCCIAA(IscrizioneRegistroType irt) {

	//nulla da fare
    }

    private void bonificaAziendaRichiedente() {

	if (dpt.getAziendaRichiedente() != null) {
	    if (isToRemovePersonaGiuridicaType(dpt.getAziendaRichiedente())) {
		dpt.setAziendaRichiedente(null);
	    } else {
		bonificaPersonaGiuridicaType(dpt.getAziendaRichiedente());
	    }
	}
    }

    private boolean isToRemoveComuneType(ComuneType comune) {

	if (StringUtils.isBlank(comune.getCodiceCatastale()) && StringUtils.isBlank(comune.getCodiceIstat())
		&& StringUtils.isBlank(comune.getComune())) {
	    return true;
	}
	return false;
    }

    private boolean isToRemoveRuoloType(RuoloType ruolo) {

	if (StringUtils.isBlank(ruolo.getRuolo())) {
	    return true;
	}
	return false;
    }

    private boolean isToRemovePersonaFisicaType(PersonaFisicaType pft) {

	if (StringUtils.isBlank(pft.getCodiceFiscale()) || StringUtils.isBlank(pft.getNome()) || StringUtils.isBlank(pft.getCognome())) {
	    return true;
	}
	return false;
    }

    private boolean isToRemoveLocalizzazioneType(LocalizzazioneType lt) {

	if (StringUtils.isBlank(lt.getIndirizzo())) {
	    return true;
	}
	return false;
    }

    private void bonificaLocalizzazioneType(LocalizzazioneType lt) {

	if (StringUtils.isBlank(lt.getCivico())) {
	    lt.setCivico(null);
	}
	if (StringUtils.isBlank(lt.getLocalita())) {
	    lt.setLocalita(null);
	}
	if (StringUtils.isBlank(lt.getCap())) {
	    lt.setCap(null);
	}
	if (lt.getComune() != null && isToRemoveComuneType(lt.getComune())) {
	    lt.setComune(null);
	}
	if (StringUtils.isBlank(lt.getProvincia())) {
	    lt.setProvincia(null);
	}
    }

    private boolean isToRemoveCittadinanzaType(CittadinanzaType citt) {

	if (StringUtils.isBlank(citt.getCodiceCatastale()) && StringUtils.isBlank(citt.getDescrizione()) && StringUtils.isBlank(citt.getId())) {
	    return true;
	}
	return false;
    }

    private void bonificaCittadinanzaType(CittadinanzaType citt) {

	if (StringUtils.isBlank(citt.getCodiceCatastale())) {
	    citt.setCodiceCatastale(null);
	}
	if (StringUtils.isBlank(citt.getDescrizione())) {
	    citt.setDescrizione(null);
	}
	if (StringUtils.isBlank(citt.getId())) {
	    citt.setId(null);
	}
    }

    private void bonificaProcedimenti() {

	List<ProcedimentoType> procToRemove = new ArrayList<ProcedimentoType>();
	for (ProcedimentoType proc : dpt.getProcedimenti()) {
	    if (isToRemoveProcedimentoType(proc)) {
		procToRemove.add(proc);
	    } else {
		bonificaProcedimentoType(proc);
	    }
	}
	for (ProcedimentoType _proc : procToRemove) {
	    dpt.getProcedimenti().remove(_proc);
	}
    }

    private boolean isToRemoveProcedimentoType(ProcedimentoType proc) {

	if (StringUtils.isBlank(proc.getCodice())) {
	    return true;
	}
	return false;
    }

    private void bonificaProcedimentoType(ProcedimentoType proc) {

	if (StringUtils.isBlank(proc.getDescrizione())) {
	    proc.setDescrizione(null);
	}
	if (!BooleanUtils.isTrue(proc.isPrincipale())) {
	    proc.setPrincipale(Boolean.FALSE);
	}
	List<DocumentiType> docsToRemove = new ArrayList<DocumentiType>();
	for (DocumentiType doc : proc.getDocumenti()) {
	    if (isToRemoveDocumentiType(doc)) {
		docsToRemove.add(doc);
	    } else {
		bonificaDocumentiType(doc);
	    }
	}
	for (DocumentiType _doc : docsToRemove) {
	    proc.getDocumenti().remove(_doc);
	}
	if (proc.getEstremiAtto() != null && StringUtils.isBlank(proc.getEstremiAtto().getTipoAtto())) {
	    proc.setEstremiAtto(null);
	}
    }

    private boolean isToRemoveDocumentiType(DocumentiType doc) {

	if (StringUtils.isBlank(doc.getId()) && StringUtils.isBlank(doc.getDocumento())) {
	    return true;
	}
	return false;
    }

    private void bonificaDocumentiType(DocumentiType doc) {

	if (doc != null) {
	    if (isToRemoveAllegatiType(doc.getAllegati())) {
		doc.setAllegati(null);
	    } else {
		bonificaAllegatiType(doc.getAllegati());
	    }
	}
    }

    private boolean isToRemoveAllegatiType(AllegatiType at) {

	if (StringUtils.isBlank(at.getAllegato()) && StringUtils.isBlank(at.getId())) {
	    return true;
	}
	return false;
    }

    private void bonificaAllegatiType(AllegatiType at) {

	if (at.getFile() != null) {
	    if (isToRemoveAllegatoBinarioType(at.getFile())) {
		at.setFile(null);
	    } else {
		bonificaAllegatoBinarioType(at.getFile());
	    }
	}
    }

    private boolean isToRemoveAllegatoBinarioType(AllegatoBinarioType abt) {

	if (StringUtils.isBlank(abt.getFileName()) && abt.getBinaryData() == null) {
	    return true;
	}
	return false;
    }

    private void bonificaAllegatoBinarioType(AllegatoBinarioType abt) {

	if (StringUtils.isBlank(abt.getMimeType())) {
	    abt.setMimeType(null);
	}
    }

    private void bonificaLocalizzazione() {

	List<LocalizzazioneNelComuneType> locsToRemove = new ArrayList<LocalizzazioneNelComuneType>();
	List<LocalizzazioneNelComuneType> locs = dpt.getLocalizzazione();
	for (LocalizzazioneNelComuneType loc : locs) {
	    if (isToRemoveLocalizzazioneNelComuneType(loc)) {
		locsToRemove.add(loc);
	    } else {
		bonificaLocalizzazioneNelComuneType(loc);
	    }
	}
	for (LocalizzazioneNelComuneType locToRemove : locsToRemove) {
	    locs.remove(locToRemove);
	}
    }

    private boolean isToRemoveLocalizzazioneNelComuneType(LocalizzazioneNelComuneType loc) {

	if (StringUtils.isBlank(loc.getId()) && StringUtils.isBlank(loc.getCodiceViario()) && StringUtils.isBlank(loc.getDenominazione())) {
	    return true;
	}
	return false;
    }

    private void bonificaLocalizzazioneNelComuneType(LocalizzazioneNelComuneType loc) {

	if (StringUtils.isBlank(loc.getCivico())) {
	    loc.setCivico(null);
	}
	if (StringUtils.isBlank(loc.getEsponente())) {
	    loc.setEsponente(null);
	}
	if (StringUtils.isBlank(loc.getColore())) {
	    loc.setColore(null);
	}
	if (StringUtils.isBlank(loc.getScala())) {
	    loc.setScala(null);
	}
	if (StringUtils.isBlank(loc.getInterno())) {
	    loc.setInterno(null);
	}
	if (StringUtils.isBlank(loc.getEsponenteInterno())) {
	    loc.setEsponenteInterno(null);
	}
	if (StringUtils.isBlank(loc.getPiano())) {
	    loc.setPiano(null);
	}
	if (StringUtils.isBlank(loc.getFabbricato())) {
	    loc.setFabbricato(null);
	}
	if (StringUtils.isBlank(loc.getKm())) {
	    loc.setKm(null);
	}
	if (loc.getQuartiere() != null) {
	    if (isToRemoveQuartiereType(loc.getQuartiere())) {
		loc.setQuartiere(null);
	    } else {
		bonificaQuartiereType(loc.getQuartiere());
	    }
	}
	if (loc.getFrazione() != null) {
	    if (isToRemoveFrazioneType(loc.getFrazione())) {
		loc.setFrazione(null);
	    } else {
		bonificaFrazioneType(loc.getFrazione());
	    }
	}
	if (loc.getCircoscrizione() != null) {
	    if (isToRemoveCircoscrizioneType(loc.getCircoscrizione())) {
		loc.setCircoscrizione(null);
	    } else {
		bonificaCircoscrizioneType(loc.getCircoscrizione());
	    }
	}
	List<RiferimentoCatastaleType> rifCatToRemove = new ArrayList<RiferimentoCatastaleType>();
	for (RiferimentoCatastaleType rifC : loc.getRiferimentoCatastale()) {
	    if (isToRemoveRiferimentoCatastaleType(rifC)) {
		rifCatToRemove.add(rifC);
	    } else {
		bonificaRiferimentoCatastaleType(rifC);
	    }
	}
	for (RiferimentoCatastaleType _rifC : rifCatToRemove) {
	    loc.getRiferimentoCatastale().remove(_rifC);
	}
    }

    private boolean isToRemoveQuartiereType(QuartiereType qt) {

	if (StringUtils.isBlank(qt.getCodice()) && StringUtils.isBlank(qt.getDescrizione())) {
	    return true;
	}
	return false;
    }

    private boolean isToRemoveFrazioneType(FrazioneType ft) {

	if (StringUtils.isBlank(ft.getCodice()) && StringUtils.isBlank(ft.getDescrizione())) {
	    return true;
	}
	return false;
    }

    private boolean isToRemoveCircoscrizioneType(CircoscrizioneType ct) {

	if (StringUtils.isBlank(ct.getCodice()) && StringUtils.isBlank(ct.getDescrizione())) {
	    return true;
	}
	return false;
    }

    private boolean isToRemoveRiferimentoCatastaleType(RiferimentoCatastaleType rct) {

	if (StringUtils.isBlank(rct.getFoglio()) || StringUtils.isBlank(rct.getParticella()) || StringUtils.isBlank(rct.getTipoCatasto())) {
	    return true;
	}
	return false;
    }

    private void bonificaRiferimentoCatastaleType(RiferimentoCatastaleType rct) {

	if (StringUtils.isBlank(rct.getSub())) {
	    rct.setSub(null);
	}
    }

    private void bonificaQuartiereType(QuartiereType qt) {

	if (StringUtils.isBlank(qt.getCodice())) {
	    qt.setCodice(null);
	}
	if (StringUtils.isBlank(qt.getDescrizione())) {
	    qt.setDescrizione(null);
	}
    }

    private void bonificaFrazioneType(FrazioneType ft) {

	if (StringUtils.isBlank(ft.getCodice())) {
	    ft.setCodice(null);
	}
	if (StringUtils.isBlank(ft.getDescrizione())) {
	    ft.setDescrizione(null);
	}
    }

    private void bonificaCircoscrizioneType(CircoscrizioneType ct) {

	if (StringUtils.isBlank(ct.getCodice())) {
	    ct.setCodice(null);
	}
	if (StringUtils.isBlank(ct.getDescrizione())) {
	    ct.setDescrizione(null);
	}
    }

    private void bonificaIntermediario() {

	if (dpt.getIntermediario() != null) {
	    if (isToRemoveAnagrafeType(dpt.getIntermediario())) {
		dpt.setIntermediario(null);
	    } else {
		bonificaAnagrafeType(dpt.getIntermediario());
	    }
	}
    }

    private boolean isToRemoveAnagrafeType(AnagrafeType ana) {

	boolean removePF = false;
	boolean removePG = false;
	if (ana.getPersonaFisica() == null && ana.getPersonaGiuridica() == null) {
	    return true;
	}
	if (ana.getPersonaFisica() != null && isToRemovePersonaFisicaType(ana.getPersonaFisica())) {
	    removePF = true;
	}
	if (ana.getPersonaGiuridica() != null && isToRemovePersonaGiuridicaType(ana.getPersonaGiuridica())) {
	    removePG = true;
	}
	if (removePF && removePG) {
	    return true;
	}
	return false;
    }

    private void bonificaAnagrafeType(AnagrafeType ana) {

	if (ana.getPersonaFisica() != null) {
	    if (isToRemovePersonaFisicaType(ana.getPersonaFisica())) {
		ana.setPersonaFisica(null);
	    } else {
		bonificaPersonaFisicaType(ana.getPersonaFisica());
	    }
	}
	if (ana.getPersonaGiuridica() != null) {
	    if (isToRemovePersonaGiuridicaType(ana.getPersonaGiuridica())) {
		ana.setPersonaGiuridica(null);
	    } else {
		bonificaPersonaGiuridicaType(ana.getPersonaGiuridica());
	    }
	}
    }

    private void bonificaAltriSoggetti() {

	List<AltriSoggettiType> soggToRemove = new ArrayList<AltriSoggettiType>();
	for (AltriSoggettiType ast : dpt.getAltriSoggetti()) {
	    if (isToRemoveAltriSoggettiType(ast)) {
		soggToRemove.add(ast);
	    } else {
		bonificaAltriSoggettiType(ast);
	    }
	}
	for (AltriSoggettiType _ast : soggToRemove) {
	    dpt.getAltriSoggetti().remove(_ast);
	}
    }

    private boolean isToRemoveAltriSoggettiType(AltriSoggettiType ast) {

	if ((ast.getTipoRapporto() == null || isToRemoveRuoloType(ast.getTipoRapporto()))
		&& (ast.getSoggetto() == null || isToRemoveAnagrafeType(ast.getSoggetto()))) {
	    return true;
	}
	return false;
    }

    private void bonificaAltriSoggettiType(AltriSoggettiType ast) {

	if (ast.getAnagraficaCollegata() != null) {
	    if (isToRemoveAnagrafeType(ast.getAnagraficaCollegata())) {
		ast.setAnagraficaCollegata(null);
	    } else {
		bonificaAnagrafeType(ast.getAnagraficaCollegata());
	    }
	}
	bonificaAnagrafeType(ast.getSoggetto());
	bonificaRuoloType(ast.getTipoRapporto());
    }

    private void bonificaDocumenti() {

	for (PDFSchedaDinamicaType pdfScheda : nit.getListaPDFSchedeDinamiche()) {
	    if (pdfScheda.isBloccoMultiplo()) {
		for (PDFSchedaDinamicaBloccoType pdfBloccoScheda : pdfScheda.getListaPDFBlocchiSchedaDinamica()) {
		    dpt.getDocumenti().add(pdfBloccoScheda.getDocumento());
		}
	    } else {
		dpt.getDocumenti().add(pdfScheda.getDocumento());
	    }
	}
	List<DocumentiType> docsToRemove = new ArrayList<DocumentiType>();
	List<DocumentiType> docs = dpt.getDocumenti();
	for (DocumentiType doc : docs) {
	    if (isToRemoveDocumentiType(doc)) {
		docsToRemove.add(doc);
	    } else {
		bonificaDocumentiType(doc);
	    }
	}
	for (DocumentiType docToRemove : docsToRemove) {
	    docs.remove(docToRemove);
	}
    }
}
