package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.AggiungiAllegatoRequest;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.AggiungiAllegatoResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.DeterminaFascicolataRequest;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.ElencoFirmatariResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.FascicolaDeterminaRequest;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.FascicolaDeterminaResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.InserisciDeterminaRequest;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.InserisciDeterminaResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.LeggiDeterminaRequest;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.LeggiDeterminaResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.NumeraDeterminaRequest;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.NumeraDeterminaResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.WSAttiRestClient;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;

@Service
public class WSAttiServiceImpl implements WSAttiService {

    private IConfigurazioneResolverService configurazioneService;
    private MovimentiService movimentiService;
    private MovimentiallegatiService movimentiallegatiService;
    private DocumentiAutorizzazioneService documentiAutorizzazioneService;
    private OggettiService oggettiService;
    private IstanzeService istanzeService;

    @Autowired
    public void setConfigurazioneService(IConfigurazioneResolverService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setDocumentiAutorizzazioneService(DocumentiAutorizzazioneService documentiAutorizzazioneService) {

	this.documentiAutorizzazioneService = documentiAutorizzazioneService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Override
    public InserisciDeterminaResponse inserisci(String codiceComune, String oggetto, Integer idAlberoProc, String codiceFirmatario) {

	try {
	    //1. Risolvo la configurazione
	    ConfigurazioneWSAtti config = this.configurazioneService.getConfigurazione(idAlberoProc);
	    if (!StringUtils.isBlank(codiceFirmatario)) {
		config.setCodiceDirigente(codiceFirmatario);
	    }
	    //1. Creo la request a partire dai dati della verticalizzazione
	    InserisciDeterminaRequest requestInserisci = InserisciDeterminaRequest.fromConfigurazione(config);
	    //2. Integro i dati mancanti	    
	    requestInserisci.setOggetto(oggetto);
	    //3. Sostituisco i valori di default
	    requestInserisci.setPubblicare(true);
	    //4. Chiamata per inserimento atto	    
	    return new WSAttiRestClient(this.replaceUrl(getUrlInserisciDeterminaNET(), codiceComune)).inserisciDetermina(requestInserisci);
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public NumeraDeterminaResponse numera(Integer idDocumento, Date dataInserimento, String codiceComune) {

	try {
	    //1. Preparo la request per la numerazione dell'atto
	    NumeraDeterminaRequest requestNumera = NumeraDeterminaRequest.fromIdDocumento(idDocumento);
	    //7. Chiamata per numerazione atto
	    return new WSAttiRestClient(this.replaceUrl(getUrlNumeraDeterminaNET(), codiceComune)).numeraDetermina(requestNumera);
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public LeggiDeterminaResponse leggi(Integer idDocumento, String codiceComune) {

	try {
	    //1. Preparo la request per la lettura dei dati dell'atto
	    LeggiDeterminaRequest requestLeggi = new LeggiDeterminaRequest();
	    requestLeggi.setIdDocumento(idDocumento);
	    //2. Invoco il servizio
	    return new WSAttiRestClient(this.replaceUrl(getUrlLeggiDeterminaNET(), codiceComune)).leggiDetermina(requestLeggi);
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public ElencoFirmatariResponse elencoFirmatari(String codiceComune) {

	//1. Invoco il servizio
	try {
	    return new WSAttiRestClient(this.replaceUrl(getUrlElencoFirmatariNET(), codiceComune)).elencoFirmatari();
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public String aggiungiAllegato(String codiceComune, Allegato allegato) {

	try {
	    WSAttiRestClient client = new WSAttiRestClient(this.replaceUrl(getUrlAggiungiAllegatoNET(), codiceComune));
	    AggiungiAllegatoRequest request = new AggiungiAllegatoRequest();
	    request.setAllegatoBase64(allegato.getAllegatoBase64());
	    request.setAnno(allegato.getAnnoDocumento());
	    request.setEstensioneFile(allegato.getEstensioneFile());
	    request.setIdDocumento(allegato.getIdDocumento());
	    request.setNomeFile(allegato.getNomeFile());
	    request.setNumero(allegato.getNumeroDocumento());
	    request.setPrincipale(allegato.isPrincipale());
	    request.setSerial(allegato.getSerial());
	    AggiungiAllegatoResponse response = client.aggiungiAllegato(request);
	    return response.getIdAllegato();
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void fascicola(String codiceComune, Fascicolo datiFascicolo) {

	try {
	    WSAttiRestClient client = new WSAttiRestClient(this.replaceUrl(getUrlFascicolaDeterminaNET(), codiceComune));
	    FascicolaDeterminaRequest request = new FascicolaDeterminaRequest();
	    request.setClassifica(datiFascicolo.getClassifica());
	    request.setIdDocumento(datiFascicolo.getIdDocumento());
	    request.setOggetto(datiFascicolo.getOggetto());
	    client.fascicolaDetermina(request);
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public boolean isFascicolata(String codiceComune, Integer idDocumento) {

	try {
	    WSAttiRestClient client = new WSAttiRestClient(this.replaceUrl(getUrlDeterminaFascicolataNET(), codiceComune));
	    DeterminaFascicolataRequest request = new DeterminaFascicolataRequest();
	    request.setIdDocumento(idDocumento);
	    FascicolaDeterminaResponse response = client.isFascicolata(request);
	    return StringUtils.isNotBlank(response.getNumero());
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    private String replaceUrl(String url, String codiceComune) {

	String result = url;
	result = result.replace("{ALIAS}", ORMHelper.getIdcomuneAlias());
	result = result.replace("{SOFTWARE}", ORMHelper.getSoftware());
	result = result.replace("{CODICECOMUNE}", codiceComune);
	return result;
    }

    @Override
    public void precompilaAllegatiSecondari(Autorizzazioni aut) {

	if (aut == null) {
	    throw new IllegalArgumentException(
		    "Impossibile precompilare gli allegati dell'autorizzazione senza passare l'autorizzazione di riferimento");
	}
	if (aut.getIstanza() == null || aut.getIstanza().getAlberoproc() == null || aut.getIstanza().getAlberoproc().getId() == null
		|| aut.getIstanza().getAlberoproc().getId().getCodice() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile precompilare gli allegati in quanto l'autorizzazione non è stata rilasciata tramite un movimento, impossibile risalire alla configurazione");
	}
	Integer idAlberoProc = aut.getIstanza().getAlberoproc().getId().getCodice();
	ConfigurazioneWSAtti config = this.configurazioneService.getConfigurazione(idAlberoProc);
	Integer codiceIstanza = aut.getIstanza().getId().getCodice();
	for (String tipoMovimento : config.getTipiMovimento()) {
	    List<Movimenti> movimenti = this.movimentiService.findMovimentiIstanzaFattiByTipoMovimento(tipoMovimento, codiceIstanza);
	    for (Movimenti movimento : movimenti) {
		for (Movimentiallegati allegato : movimento.getMovimentiallegatis()) {
		    if ((allegato.getControllook() == null || allegato.getControllook() != 0) && allegato.getOggetto() != null) {
			this.documentiAutorizzazioneService.insertDocumentoAutorizzazione(allegato.getOggetto().getId().getCodice(),
				aut.getId().getCodice(), allegato.getId().getCodice(), WebConstants.DOCAUTORIZZAZIONE_CODICE_MOVIMENTIALLEGATI);
		    }
		}
	    }
	}
    }

    @Override
    public void generaAllegatoPrincipale(Autorizzazioni aut) {

	if (aut == null) {
	    throw new IllegalArgumentException(
		    "Impossibile generare l'allegato principale dell'autorizzazione senza passare l'autorizzazione di riferimento");
	}
	if (aut.getMovimenti() == null || aut.getMovimenti().getId() == null || aut.getMovimenti().getId().getCodice() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile generare l'allegato principale dell'autorizzazione in quanto l'autorizzazione non è stata rilasciata tramite un movimento, impossibile risalire alla configurazione");
	}
	if (aut.getMovimenti().getTipomovimento().getTipimovimentodoctipos() == null
		|| aut.getMovimenti().getTipomovimento().getTipimovimentodoctipos().size() != 1) {
	    throw new IllegalArgumentException(
		    "Impossibile generare l'allegato principale dell'autorizzazione in quanto nel tipo movimento non è configurato l'allegato principale da generare");
	}
	//1. Generazione dell'allegato
	Letteretipo lettera = aut.getMovimenti().getTipomovimento().getTipimovimentodoctipos().iterator().next().getLetteretipo();
	Integer codiceIstanza = aut.getMovimenti().getIstanza().getId().getCodice();
	Integer codiceMovimento = aut.getMovimenti().getId().getCodice();
	Integer idMovimentiAllegati = this.movimentiallegatiService.createAndInsertMovimentoAllegato(lettera, codiceIstanza, codiceMovimento);
	//2. Trasformazione in PDF
	Movimentiallegati allegato = this.movimentiallegatiService.findById(new PkId(idMovimentiAllegati));
	Oggetti oggetto = allegato.getOggetto();
	byte[] oggettoPDFByte = this.oggettiService.trasformInPdf(oggetto, null);
	oggetto.setOggetto(oggettoPDFByte);
	String nomeFile = oggetto.getNomefile().substring(0, oggetto.getNomefile().lastIndexOf(".")) + ".pdf";
	oggetto.setNomefile(nomeFile);
	this.oggettiService.update(oggetto);
	//3. Aggiunta del documento principale nell'autorizzazione
	this.documentiAutorizzazioneService.insertDocumentoAutorizzazione(oggetto.getId().getCodice(), aut.getId().getCodice(),
		allegato.getId().getCodice(), true, WebConstants.DOCAUTORIZZAZIONE_CODICE_MOVIMENTIALLEGATI);
    }

    @Override
    public void registraCompletamentoAtto(Autorizzazioni autorizzazione) {

	//1. Verifico se da verticalizzazione va eseguito un movimento al completamento dell'atto
	Tipimovimento tipo = this.configurazioneService.getConfigurazione(autorizzazione.getIstanza().getAlberoproc().getId().getCodice())
		.getMovimentoAttoCompletato();
	if (tipo == null) {
	    return;
	}
	//2. Verifico se il movimento da effettuare è parte dell'iter, in caso contrario non può essere fatto
	if (autorizzazione.getMovimenti() != null && autorizzazione.getMovimenti().getId() != null
		&& autorizzazione.getMovimenti().getId().getCodice() != null) {
	    List<Movimenti> movimenti = this.movimentiService.findContromovimentidaEffettuare(autorizzazione.getMovimenti());
	    for (Movimenti movimento : movimenti) {
		if (tipo.getId().getTipomovimento().compareToIgnoreCase(movimento.getTipomovimento().getId().getTipomovimento()) == 0) {
		    movimento.setData(new Date());
		    this.movimentiService.update(movimento);
		    //3. Verifica cambio stato
		    this.movimentiService.updateStatoistanza(movimento);
		    //4. Elaborazione
		    Date dataDaElaborare = this.movimentiService.getDataMovimentoDaElaborare(movimento);
		    this.istanzeService.elabora(movimento.getIstanza().getId().getCodice(), false, dataDaElaborare);
		    return;
		}
	    }
	}
    }

    private String getBaseUrl() {

	return WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_ASPNET) + "/web-api/wsatti/{ALIAS}/{SOFTWARE}/";
    }

    private String getUrlInserisciDeterminaNET() {

	return getBaseUrl() + "inserisci-determina/{CODICECOMUNE}";
    }

    private String getUrlNumeraDeterminaNET() {

	return getBaseUrl() + "numera-determina/{CODICECOMUNE}";
    }

    private String getUrlLeggiDeterminaNET() {

	return getBaseUrl() + "leggi-determina/{CODICECOMUNE}";
    }

    private String getUrlElencoFirmatariNET() {

	return getBaseUrl() + "elenco-firmatari/{CODICECOMUNE}";
    }

    private String getUrlAggiungiAllegatoNET() {

	return getBaseUrl() + "aggiungi-allegato/{CODICECOMUNE}";
    }

    private String getUrlFascicolaDeterminaNET() {

	return getBaseUrl() + "fascicola-determina/{CODICECOMUNE}";
    }

    private String getUrlDeterminaFascicolataNET() {

	return getBaseUrl() + "determina-fascicolata/{CODICECOMUNE}";
    }
}
