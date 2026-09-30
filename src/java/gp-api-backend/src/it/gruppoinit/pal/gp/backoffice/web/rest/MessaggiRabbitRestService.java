package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.exception.SecurityException;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.CodiceFiscaleBean;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.CodiciFiscaliDestinatariBean;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.DettPosizioneDebitoriaProvenienzaEnum;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.MovimentiRabbitTestoBean;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.RabbitMQRiferimentoIstanza;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.TestiMovimentiRabbitRequest;
import it.gruppoinit.pal.gp.core.features.rabbitmq.posizionidebitorie.DettposizioniDebitorieDestinatariFactory;
import it.gruppoinit.pal.gp.core.features.rabbitmq.posizionidebitorie.IDestinatariDettPosizioneDebitoriaResolver;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.BollGestTestataService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Path("/messaggirabbit/")
public class MessaggiRabbitRestService {

    private static final Logger log = LoggerFactory.getLogger(MessaggiRabbitRestService.class);
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    @Autowired
    private IstanzeoneriService istanzeoneriService;
    @Autowired
    IstanzerichiedentiService istanzerichiedentiService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private BollGestTestataService bollGestTestataService;

    @POST
    @Path("/testotipo/movimenti")
    @Descriptions({
	    @Description(value = "Ottiene la lista dei cf principali dell'istanza (Richiedente / tecnico) solo se persone fisische.", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response testoTipo(String json) throws UnsupportedEncodingException, JAXBException {

	TestiMovimentiRabbitRequest request = Utilities.unMarshallJsonString(json, TestiMovimentiRabbitRequest.class, Utilities.JAXB_ENCODING_UTF_8,
		true);
	log.debug("testoTipo# uuId={}", request.getUuidMovimento());
	Movimenti mov = movimentiService.findMovimentoByUuId(request.getUuidMovimento());
	if (mov != null) {
	    Istanze i = istanzeService.findById(new PkId(mov.getIstanza().getId().getCodice()));
	    ORMHelper.setSoftware(i.getSoftware().getCodice());
	}
	try {
	    MovimentiRabbitTestoBean ret = movimentiService.replaceTestoPerMovimentoeTopic(mov.getId().getCodice(), request.getTopic());
	    String list = Utilities.marshalJsonObject(ret, MovimentiRabbitTestoBean.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(list, Status.OK);
	} catch (Exception e) {
	    MovimentiRabbitTestoBean ret = new MovimentiRabbitTestoBean();
	    CodiceDescrizioneBean err = new CodiceDescrizioneBean("500", e.getMessage());
	    ret.setErrore(err);
	    String list = Utilities.marshalJsonObject(ret, MovimentiRabbitTestoBean.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(list, Status.INTERNAL_SERVER_ERROR);
	}
    }

    @GET
    @Path("/riferimenti-pratica/{uuid}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getRiferimentiPratica(@PathParam("uuid") String uuIdPratica) throws JAXBException {

	Istanze i = istanzeService.findByUiid(uuIdPratica);
	RabbitMQRiferimentoIstanza rif = new RabbitMQRiferimentoIstanza();
	rif.setSoftware(i.getSoftware().getCodice());
	rif.setNumeroIstanza(i.getNumeroistanza());
	rif.setUuid(i.getUuid());
	String resp = Utilities.marshalJsonObject(rif, RabbitMQRiferimentoIstanza.class, false, Utilities.JAXB_ENCODING_UTF_8);
	return rispostaWs(resp, Status.OK);
    }

    @GET
    @Path("/richiedenti-visura/cf/{uuid}")
    public Response getCfVisuraIstanza(@PathParam("uuid") String uuid) throws JAXBException {

	Istanze istanza = this.istanzeService.findByUiid(uuid);
	if (istanza == null) {
	    log.error("istanza con uuid {} non trovata", uuid);
	    CodiceDescrizioneBean err = new CodiceDescrizioneBean("500", "Istanza con uuid " + uuid + " non trovata");
	    String list = Utilities.marshalJsonObject(err, CodiceDescrizioneBean.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(list, Status.INTERNAL_SERVER_ERROR);
	}
	Set<String> cfs = istanzeService.getCfRichiedentiPrincipaliIstanzaAut(istanza.getId().getCodice());
	List<CodiceFiscaleBean> ret = new ArrayList<CodiceFiscaleBean>(cfs.size());
	for (String r : cfs) {
	    ret.add(new CodiceFiscaleBean(r));
	}
	String list = Utilities.marshalJsonObject(ret, CodiceFiscaleBean.class, false, Utilities.JAXB_ENCODING_UTF_8);
	return rispostaWs(list, Status.OK);
    }

    @GET
    @Path("/{cf_ente}/posizionedebitoria/{uuid}/destinatari")
    public Response getCfDaPosizioneDebitoria(@PathParam("uuid") String uuid, @PathParam("cf_ente") String cfEnte) throws JAXBException {

	//Recupero la DettPosizioneDebitoria
	try {
	    DettPosizioneDebitoria dettPosizioneDebitoria = dettPosizioneDebitoriaService.findByCfEnteEUuid(cfEnte, uuid);
	    //setto Ormhelper col software recuperato
	    if (dettPosizioneDebitoria == null) {
		String list = Utilities.marshalJsonObject(new CodiciFiscaliDestinatariBean(), CodiciFiscaliDestinatariBean.class, false,
			Utilities.JAXB_ENCODING_UTF_8);
		return rispostaWs(list, Status.OK);
	    }
	    ORMHelper.setSoftware(dettPosizioneDebitoria.getCodiceSoftware());
	    DettPosizioneDebitoriaProvenienzaEnum dettPosEnum = dettPosizioneDebitoriaService.provenienza(dettPosizioneDebitoria.getId().getCodice());
	    IDestinatariDettPosizioneDebitoriaResolver resolver = new DettposizioniDebitorieDestinatariFactory(istanzeoneriService, istanzeService,
		    istanzerichiedentiService, anagrafeService, bollGestTestataService).getResolvers(dettPosEnum, dettPosizioneDebitoria)
			    .get(dettPosEnum.name());
	    if (resolver == null) {
		throw new IllegalArgumentException(
			"Resolver con provenienza " + dettPosEnum + " non trovato per Posizione debitoria con id " + uuid + " e cf_ente " + cfEnte);
	    }
	    CodiciFiscaliDestinatariBean ret = resolver.getDestinatariPersoneFisiche();
	    String list = Utilities.marshalJsonObject(ret, CodiciFiscaliDestinatariBean.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(list, Status.OK);
	} catch (Exception e) {
	    log.error("Errore nel recupero dei codicifiscali ", e);
	    CodiceDescrizioneBean err = new CodiceDescrizioneBean("500", e.getMessage());
	    String list = Utilities.marshalJsonObject(err, CodiceDescrizioneBean.class, false, Utilities.JAXB_ENCODING_UTF_8);
	    return rispostaWs(list, Status.INTERNAL_SERVER_ERROR);
	}
    }

    @OPTIONS
    @Path("/{var:.*}")
    public Response defaultOptions() {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
	builder.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
	builder.status(Status.OK);
	return builder.build();
    }

    protected Response rispostaWs(Object entity, Status returnStatus) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	return builder.build();
    }

    public void setORMHelper(String alias, String software) throws SecurityException {

	ExternalDBResolver externalDBResolver = (ExternalDBResolver) ContextLoader.getCurrentWebApplicationContext().getBean("externalDBResolver");
	Properties connProps = externalDBResolver.getConnectionProperties(alias);
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	if (StringUtils.isNotBlank(software)) {
	    ORMHelper.setSoftware(software);
	} else {
	    ORMHelper.setSoftware(WebConstants.SOFTWARE_TT);
	}
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	ORMHelper.setToken(connProps.getProperty(WebConstants.TOKEN));
	this.login();
    }

    private void login() throws SecurityException {

	String userid = null;
	try {
	    VerticalizzazioniService verticalizzazioniService = (VerticalizzazioniService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("verticalizzazioniServiceImpl");
	    //se la verticalizzazione WS_LOGIN è attiva utilizzo il valore del parametro CODICE_OPERATORE come userid
	    Verticalizzazioniparametri vParam = verticalizzazioniService.getVerticalizzazioniparametri("WS_LOGIN", "USERID_OPERATORE");
	    if (vParam != null) {
		userid = vParam.getValore();
		log.debug("login(userid:{}) recuperato dalla verticalizzazione WS_LOGIN", userid);
	    }
	    UserDetails user = this.getUser(userid);
	    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	    // update the current context to the target user
	    SecurityContextHolder.getContext().setAuthentication(authRequest);
	    log.debug("login(userid:{})", userid);
	} catch (UsernameNotFoundException e) {
	    log.error("login(userid:" + userid + ") idcomunealias:" + ORMHelper.getIdcomuneAlias(), e);
	    throw new SecurityException("login: " + e.getMessage());
	}
    }

    protected UserDetails getUser(String userId) {

	if (userId == null) {
	    return userSecurityService.loadAdministratorUser();
	}
	try {
	    return userSecurityService.loadUserByUsername(userId);
	} catch (UsernameNotFoundException e) {
	    return userSecurityService.loadAdministratorUser();
	}
    }
}
