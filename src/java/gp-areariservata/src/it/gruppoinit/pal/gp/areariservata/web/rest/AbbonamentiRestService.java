package it.gruppoinit.pal.gp.areariservata.web.rest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.exception.SecurityException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IAbbonamentoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.StatoBorsellinoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AbbonamentoConfigModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.TipoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.IConfigurazioneAppAmbulantiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.BorsellinoAppModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.BorsellinoAppMovimenti;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.BorsellinoAppMovimentiWrapper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.StatoBorsellinoPerAnagrafe;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.ConfigurazioneRicaricheApp;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoAggiornamentoBorsellino;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoOperazioneAggiornamento;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoRicaricaBorsellino;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.RicaricaBorsellinoRequest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.SaldoBorsellinoAppModel;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.VerificaStatoPosizioniDebitorie;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Path("/abbonamenti/")
public class AbbonamentiRestService extends BaseRestService {

    public static final Logger log = LoggerFactory.getLogger(AbbonamentiRestService.class);
    @Autowired
    private IConfigurazioneAppAmbulantiService appAmbulantiService;
    @Autowired
    private IAbbonamentoService borsellinoAppService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private IAbbonamentoService abbonamentoService;

    @GET
    @Path("/ricariche/configurazione")
    @Descriptions({
	    @Description(value = "Torna le Configurazioni per verificare se è possibile effettuare le ricariche", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response configurazioneRicariche(@HeaderParam(AUTHORIZATION) String auth) {

	try {
	    authenticate(auth);
	} catch (SecurityException e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
	Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	try {
	    ConfigurazioneRicaricheApp configurazione = appAmbulantiService.getConfigurazioneRicariche(r.getId().getCodice());
	    String str = Utilities.marshalJsonObject(configurazione, configurazione.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(str, Status.OK);
	} catch (InvalidConfigurationException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	} catch (JAXBException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @GET
    @Path("/borsellino-attivo")
    @Descriptions({ @Description(value = "Torna il dettaglio del borsellino attivo", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response borsellinoAttivo(@HeaderParam(AUTHORIZATION) String auth) {

	try {
	    authenticate(auth);
	} catch (SecurityException e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
	Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	try {
	    BorsellinoAppModel configurazione = borsellinoAppService.getBorsellino(r.getId().getCodice());
	    eliminaRicaricheNonPagate(configurazione);
	    String str = Utilities.marshalJsonObject(configurazione, configurazione.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(str, Status.OK);
	} catch (BorsellinoException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	} catch (JAXBException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }
    
    @GET
    @Path("/movimenti-borsellino")
    @Descriptions({ @Description(value = "Torna i movimenti paginati del borsellino", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response movimentiFilteredPaginated(@HeaderParam(AUTHORIZATION) String auth, @FormParam("dalladata") String dalladata, @FormParam("alladata") String alladata, @FormParam("page") Integer pagina, @FormParam("tipo") String tipo ) {

	try {
	    authenticate(auth);
	} catch (SecurityException e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
	Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	try {
	    
	    List<TipoEnum> tipoenums = null;
	    if(tipo != null){
		tipoenums = new ArrayList<TipoEnum>();
		String[] tiposArray = tipo.split(",");
		for(String s : tiposArray){
		    tipoenums.add(TipoEnum.fromValue(s));
		}
	    }
	    
	    Integer page = pagina != null ? pagina : 1;
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    Date daData = null;
	    Date aData = null;	    
	    if(dalladata != null){
               daData = sdf.parse(dalladata);
	    }
	    if(alladata != null){
	       aData = sdf.parse(alladata);
	    }
	    	    
	    /*
	     * Per ora preferisco non fare una paginazione lato db, perche per la logica replicata da borsellino attivo, viene effettuato
	     * un ulteriore filtro Java side, pertanto si rischierebbero problemi con la paginazione. Dunque per ora estraiamo i record ordinati come
	     * fa borsellino-attivo, e paginiamo Java side. In caso contrario ci ragioniamo
	     */
	    List<BorsellinoAppMovimenti> movimentiResult = borsellinoAppService.getMovimentiFilteredPaginated(r.getId().getCodice(), daData, aData, null, null, tipoenums);
	    //eliminaRicaricheNonPagate
	    List<BorsellinoAppMovimenti> movimentiFiltered = new ArrayList<BorsellinoAppMovimenti>();
	    for (BorsellinoAppMovimenti bm : movimentiResult) {
		    if (!(bm.getTipo().equalsIgnoreCase(TipoEnum.RICARICA.name()) && bm.getPosizioneDebitoria() != null
			    && (!bm.getPosizioneDebitoria().isPagata() || !bm.getPosizioneDebitoria().isAnnullata()))) {
			movimentiFiltered.add(bm);
		    } else {
			if (verificaPosizionePagata(bm)) { // verifico lo stato prima di decidere se rimuoverla o meno
			    movimentiFiltered.add(bm); // se pagata la aggiungo
			}
		    }
	    }
	    
	    int maxResult = 20;
	    int fromIndex = (page - 1) * maxResult;
	    int toIndex = Math.min(fromIndex + maxResult, movimentiFiltered.size());
	    
	    BorsellinoAppMovimentiWrapper movimentiWrapper = new BorsellinoAppMovimentiWrapper();
	    movimentiWrapper.setMaxresult(maxResult);
	    if(fromIndex >= movimentiFiltered.size()){
		movimentiWrapper.setPage(page);
		movimentiWrapper.setTotalsize(movimentiFiltered.size());
		movimentiWrapper.setMovimenti(new ArrayList<BorsellinoAppMovimenti>());
	    }else{
		movimentiWrapper.setPage(page);
		movimentiWrapper.setTotalsize(movimentiFiltered.size());
		movimentiWrapper.setMovimenti(movimentiFiltered.subList(fromIndex, toIndex));
	    }
	    	    
	    String str = Utilities.marshalJsonObject(movimentiWrapper, movimentiWrapper.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(str, Status.OK);
	
	} catch (Exception e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }
    
    @GET
    @Path("/saldo")
    @Descriptions({ @Description(value = "Torna il saldo del borsellino attivo", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response saldoBorsellino(@HeaderParam(AUTHORIZATION) String auth) {

	try {
	    authenticate(auth);
	} catch (SecurityException e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
	Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	try {	    
	    SaldoBorsellinoAppModel saldo = abbonamentoService.findSaldoBorsellinoForApp(r.getId().getCodice());	      	    
	    String str = Utilities.marshalJsonObject(saldo, saldo.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(str, Status.OK);
	} catch (BorsellinoException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	} catch (JAXBException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @GET
    @Path("/borsellino-attivo/uuid")
    @Descriptions({ @Description(value = "Torna il dettaglio del borsellino attivo", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response borsellinoAttivoUuid(@HeaderParam(AUTHORIZATION) String auth) {

	try {
	    authenticate(auth);
	} catch (SecurityException e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
	Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	try {
	    String uuid = borsellinoAppService.findBorsellinoUUID(r.getId().getCodice());
	    String str = "{\"uuid\":\"" + StringUtils.defaultIfEmpty(uuid, "") + "\"}";
	    return rispostaWs(str, Status.OK);
	} catch (BorsellinoException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    private void eliminaRicaricheNonPagate(BorsellinoAppModel configurazione) {

	List<BorsellinoAppMovimenti> movimenti = new ArrayList<BorsellinoAppMovimenti>();
	List<BorsellinoAppMovimenti> m = configurazione.getMovimenti();
	for (BorsellinoAppMovimenti bm : m) {
	    if (!(bm.getTipo().equalsIgnoreCase(TipoEnum.RICARICA.name()) && bm.getPosizioneDebitoria() != null
		    && (!bm.getPosizioneDebitoria().isPagata() || !bm.getPosizioneDebitoria().isAnnullata()))) {
		movimenti.add(bm);
	    } else {
		if (verificaPosizionePagata(bm)) { // verifico lo stato prima di decidere se rimuoverla o meno
		    movimenti.add(bm); // se pagata la aggiungo
		}
	    }
	}
	configurazione.setMovimenti(movimenti);
    }

    private boolean verificaPosizionePagata(BorsellinoAppMovimenti bm) {

	if (bm.getPosizioneDebitoria() == null) {
	    return true;
	}
	if (org.apache.commons.lang.BooleanUtils.isTrue(bm.getPosizioneDebitoria().isPagata())) {
	    return true;
	}
	if (org.apache.commons.lang.BooleanUtils.isTrue(bm.getPosizioneDebitoria().getPagabile())) {
	    try {
		log.debug("Prima di chiamare l'aggiornamento dello stato per la posizione {}", bm.getPosizioneDebitoria().getIdPosizioneDebitoria());
		VerificaStatoPosizioniDebitorie statoAttuale = nodoPagamentiService
			.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(bm.getPosizioneDebitoria().getIdPosizioneDebitoria());
		log.debug("Stato del dettaglio posizione debitoria dopo l'aggiornamento {} = {}",
			bm.getPosizioneDebitoria().getIdPosizioneDebitoria(), statoAttuale.getStatoAttuale().getCodiceStato());
		String codiceStatoAttuale = statoAttuale.getStatoAttuale().getCodiceStato();
		StatiPosizioniDebitorieConverter c = new StatiPosizioniDebitorieConverter();
		return c.isStatoChiusoPositivamente(codiceStatoAttuale);
	    } catch (FunzioneBusinessRemotaException e) {
		log.error("errore nella chiamata al servizio verifica stato per la posizione {}",
			bm.getPosizioneDebitoria().getIdPosizioneDebitoria(), e);
	    }
	}
	return false;
    }

    @GET
    @Path("/ricariche-in-sospeso")
    @Descriptions({ @Description(value = "Torna i movimenti di ricarica non pagati del borsellino attivo", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response ricaricheInSospeso(@HeaderParam(AUTHORIZATION) String auth) {

	try {
	    authenticate(auth);
	} catch (SecurityException e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
	Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	try {
	    BorsellinoAppModel configurazione = borsellinoAppService.getBorsellino(r.getId().getCodice());
	    List<BorsellinoAppMovimenti> movimenti = recuperaRicaricheNonPagate(configurazione);
	    String str = Utilities.marshalJsonObject(movimenti, BorsellinoAppMovimenti.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(str, Status.OK);
	} catch (BorsellinoException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	} catch (JAXBException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @GET
    @Path("/borsellino-disattivato")
    @Descriptions({
	    @Description(value = "Verifica se il borsellino è stato disattivato per l'utente. Se il borsellino non è presente allora non è disattivo. Se l'utente ha più borsellini di cui almeno uno attivo allora non è disattivato", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response borsellinoDisattivato(@HeaderParam(AUTHORIZATION) String auth) {

	try {
	    authenticate(auth);
	} catch (SecurityException e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
	Anagrafe r = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	StatoBorsellinoPerAnagrafe statoDisattivato = borsellinoAppService.checkStatoBorsellinoPerAnagrafe(r.getId().getCodice());
	String str = "{\"disattivo\":" + statoDisattivato.getStato().equalsIgnoreCase(StatoBorsellinoEnum.NONATTIVO.name()) + "}";
	return rispostaWs(str, Status.OK);
    }

    private List<BorsellinoAppMovimenti> recuperaRicaricheNonPagate(BorsellinoAppModel configurazione) {

	List<BorsellinoAppMovimenti> movimenti = new ArrayList<BorsellinoAppMovimenti>();
	List<BorsellinoAppMovimenti> m = configurazione.getMovimenti();
	for (BorsellinoAppMovimenti bm : m) {
	    if (bm.getTipo().equalsIgnoreCase(TipoEnum.RICARICA.name())
		    && (!verificaPosizionePagata(bm) && !(bm.getPosizioneDebitoria() != null && bm.getPosizioneDebitoria().isAnnullata()))) {
		movimenti.add(bm);
	    }
	}
	return movimenti;
    }

    @POST
    @Path("/collega-autorizzazione/{uuid}")
    @Descriptions({ @Description(value = "Collega un'autorizzazione al borsellino", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response collegaAutorizzazione(@HeaderParam(AUTHORIZATION) String auth, @PathParam("uuid") String uuid,
	    @FormParam("id_autorizzazione") Integer idAutorizzazione) {

	try {
	    authenticate(auth);
	} catch (SecurityException e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
	try {
	    EsitoAggiornamentoBorsellino esito = borsellinoAppService.collegaAutorizzazione(uuid, idAutorizzazione);
	    String str = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(str, Status.OK);
	} catch (JAXBException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	} catch (BorsellinoException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @DELETE
    @Path("/rimuovi-autorizzazione/{uuid}")
    @Descriptions({ @Description(value = "Rimnuove un'autorizzazione dal borsellino", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response rimuoviAutorizzazione(@HeaderParam(AUTHORIZATION) String auth, @PathParam("uuid") String uuid,
	    @FormParam("id_autorizzazione") Integer idAutorizzazione) {

	try {
	    authenticate(auth);
	} catch (SecurityException e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
	try {
	    EsitoAggiornamentoBorsellino esito = borsellinoAppService.rimuoviAutorizzazione(uuid, idAutorizzazione);
	    String str = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(str, Status.OK);
	} catch (JAXBException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	} catch (BorsellinoException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }

    @POST
    @Path("/ricarica")
    @Descriptions({ @Description(value = "effettua una ricarica per un borsellino", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response ricarica(@HeaderParam(AUTHORIZATION) String auth, String jsonRicarica) throws JAXBException {

	try {
	    authenticate(auth);
	} catch (SecurityException e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
	Anagrafe utenteCollegato = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	try {
	    RicaricaBorsellinoRequest request = Utilities.unMarshallJsonString(jsonRicarica, RicaricaBorsellinoRequest.class,
		    Utilities.JAXB_ENCODING_UTF_8, true);
	    EsitoRicaricaBorsellino esito = borsellinoAppService.ricaricaBorsellino(request, utenteCollegato.getId().getCodice());
	    String str = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(str, Status.OK);
	} catch (Exception e) {
	    EsitoRicaricaBorsellino esito = new EsitoRicaricaBorsellino(null);
	    esito.setEsito(new EsitoAggiornamentoBorsellino(false, e.getMessage()));
	    String str = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(str, Status.OK);
	}
    }

    @DELETE
    @Path("/rimuovi-ricarica/{uuid}")
    @Descriptions({
	    @Description(value = "Rimuove una ricarica dal borsellino. Operazione possibile solo per le ricariche non pagate.", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response rimuoviRicarica(@HeaderParam(AUTHORIZATION) String auth, @PathParam("uuid") String uuid,
	    @FormParam("id_ricarica") Integer idRicarica) {

	try {
	    authenticate(auth);
	} catch (SecurityException e1) {
	    return rispostaWs(e1.getMessage(), Status.UNAUTHORIZED);
	}
	try {
	    EsitoOperazioneAggiornamento esito = borsellinoAppService.rimuoviRicarica(uuid, idRicarica);
	    String str = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(str, Status.OK);
	} catch (JAXBException e) {
	    return rispostaWs(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
	}
    }
}
