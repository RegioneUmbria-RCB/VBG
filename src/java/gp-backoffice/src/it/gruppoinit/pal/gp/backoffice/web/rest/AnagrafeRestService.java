package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.util.List;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

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

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.CategorieEventiBaseType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.areariservata.IVerticalizzazioneAreaRiservataService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolverWS;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import net.sf.sojo.core.filter.ClassPropertyFilter;
import net.sf.sojo.core.filter.ClassPropertyFilterHandler;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

@Path("/anagrafe/")
public class AnagrafeRestService {

    private static final Logger log = LoggerFactory.getLogger(AnagrafeRestService.class);
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private IVerticalizzazioneAreaRiservataService verticalizzazioneAreaRiservataService;
    @Autowired
    private IstanzeeventiService istanzeeventiService;
    @Autowired
    private UserSecurityService userSecurityService;

    @GET
    @Path("/sendreset/{alias}/{cf}/{mac}")
    @Descriptions({ @Description(value = "resetta le credenziali di un utente fronted", target = DocTarget.METHOD) })
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    public Response sendreset(@PathParam("alias") String alias, @PathParam("cf") String cf, @PathParam("mac") String mac) throws Exception {

	log.debug("sendreset " + alias + ", " + cf + ", " + mac);
	checkrequest(alias, cf, mac);
	setORMHelper(alias, WebConstants.SOFTWARE_TT);
	String authToken = getTokenAdmin(alias);
	ORMHelper.setToken(authToken);
	// invia mail con link
	String result = "";
	boolean mailInviata = false;
	if (isInvioMail()) {
	    log.debug("isinvio mail");
	    String mailTipoReset = recuperaMailtipoInvioReset();
	    if (StringUtils.isNotBlank(mailTipoReset)) {
		log.debug("mailTipoReset {}", mailTipoReset);
		Mailtipo m = mailtipoService.findById(new PkId(Integer.valueOf(mailTipoReset)));
		if (m != null) {
		    log.debug("m {}", m);
		    List<Anagrafe> findByCf = anagrafeService.findByCf(cf, WebConstants.PERSONA_FISICA, true);
		    result = "Nessun utente registrato con userid [" + cf + "]";
		    if (findByCf.size() == 1) {
			result = "";
			String oggetto = m.getOggetto();
			String corpo = m.getCorpo();
			Anagrafe a = findByCf.get(0);
			log.debug("anagrafe {}", a);
			String email = StringUtils.defaultString(a.getEmail()).trim();
			if (StringUtils.isBlank(email)) {
			    email = StringUtils.defaultString(a.getPec()).trim();
			}
			if (StringUtils.isNotBlank(email)) {
			    try {
				mailInviata = anagrafeService.inviaMailResetCredenziali(oggetto, corpo, email, cf, a.getId().getCodice());
				log.debug("mail inviata? " + mailInviata);
			    } catch (Exception e) {
				result = "si e' verificato un errore nella procedura di reset credenziali. provare più tardi.";
				log.error("Si è verificato un errore nell'invio mail inviaMailResetCredenziali all'utente " + a.toString() + ". {}",
					e);
				try {
				    istanzeeventiService.insertEventoBackoffice(
					    "Si è verificato un errore nell'invio mail inviaMailResetCredenziali all'utente " +
						    a.toString() +
						    ". \n\n" +
						    e.getMessage(),
					    CategorieEventiBaseType.MAIL.name(), ORMHelper.getSoftware());
				} catch (Exception e1) {
				    log.error("Errore nella registrazione dell' evento " + a.toString() + ". {}", e1);
				}
			    }
			}
		    }
		}
	    }
	}
	if (!mailInviata) {
	    if (StringUtils.isBlank(result)) {
		result = "Errore generico. Si sono verificati degli errori durante l'invio della richiesta di reset credenziali. Provare piu' tardi.";
	    }
	}
	ORMHelper.destroyORMHelper();
	return rispostaWs(result, Status.OK);
    }

    private String getTokenAdmin(String alias) {

	Properties deployProps = WebConstants.getDeployProperties();
	ExternalDBResolverWS externalDBResolver = new ExternalDBResolverWS();
	externalDBResolver.setConfigurationProperties(deployProps);
	SecurityWSClient client = new SecurityWSClient();
	client.setWsUrl(deployProps.getProperty("ws.token.url"));
	client.setUsername(deployProps.getProperty("ws.token.user"));
	client.setPassword(deployProps.getProperty("ws.token.pwd"));
	client.setTimeout(10000);
	externalDBResolver.setSecurityWSClient(client);
	UserDetails user = userSecurityService.loadAdministratorUser();
	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	SecurityContextHolder.getContext().setAuthentication(authToken);
	return externalDBResolver.getToken(alias);
    }

    @GET
    @Path("/reset/{alias}/{cf}/{ca}/{mac}")
    @Descriptions({ @Description(value = "resetta le credenziali di un utente fronted", target = DocTarget.METHOD) })
    @Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    public Response reset(@PathParam("alias") String alias, @PathParam("cf") String cf, @PathParam("ca") Integer ca, @PathParam("mac") String mac)
	    throws Exception {

	log.debug("reset " + alias + ", " + cf + ", " + ca + ", " + mac);
	checkrequest(alias, cf, mac);
	setORMHelper(alias, WebConstants.SOFTWARE_TT);
	String authToken = getTokenAdmin(alias);
	ORMHelper.setToken(authToken);
	// invia mail con link
	String result = "";
	boolean mailInviata = false;
	if (isInvioMail()) {
	    log.debug("isInvioMail");
	    Anagrafe a = anagrafeService.findById(new PkId(ca));
	    result = "Nessun utente associato alla userid [" + cf + "]";
	    if (a != null) {
		result = "Il codice [" + cf + "] non e' coerente con l'anagrafica registrata. Contattare l'ente.";
		log.debug("anagrafe {}", a);
		if (cf.equalsIgnoreCase(a.getCodicefiscale())) {
		    result = "Funzionalita non configurata correttamente.";
		    String codiceMailTipo = StringUtils.defaultString(recuperaMailtipoInvioMailReg()).trim();
		    log.debug("codiceMailTipo {}", codiceMailTipo);
		    Integer codMail = Integer.parseInt(codiceMailTipo);
		    Mailtipo m = mailtipoService.findById(new PkId(codMail));
		    if (m != null) {
			result = "";
			log.debug("codiceMailTipo {}", codiceMailTipo);
			Integer codiceAnagrafe = a.getId().getCodice();
			boolean forzaCreazionePassword = true;
			try {
			    mailInviata = anagrafeService.inviaMailUtente(m, codiceAnagrafe, forzaCreazionePassword);
			    log.debug("mail inviata? " + mailInviata);
			} catch (Exception e) {
			    result = "si sono verificati degli errori durante l'invio della richiesta di reset credenziali. Provare piu' tardi.";
			    log.error("Si è verificato un errore nell'invio mail reset all'utente " + a.toString() + ". {}", e);
			    try {
				istanzeeventiService.insertEventoBackoffice(
					"Si è verificato un errore nell'invio mail reset all'utente " + a.toString() + ". \n\n" + e.getMessage(),
					CategorieEventiBaseType.MAIL.name(), ORMHelper.getSoftware());
			    } catch (Exception e1) {
				log.error("Errore nella registrazione dell' evento " + a.toString() + ". {}", e1);
			    }
			}
		    }
		}
	    }
	}
	if (!mailInviata) {
	    if (StringUtils.isBlank(result)) {
		result = "Errore generico. Si sono verificati degli errori durante l'invio della richiesta di reset credenziali. Provare piu' tardi.";
	    }
	}
	ORMHelper.destroyORMHelper();
	return rispostaWs(result, Status.OK);
    }

    private String recuperaMailtipoInvioMailReg() {

	String result = null;
	boolean isArearis = this.verticalizzazioneAreaRiservataService.isAttiva();
	if (isArearis) {
	    Mailtipo mail = this.verticalizzazioneAreaRiservataService.getModelloPerInvioMailNuovoUtenteRegistrato();
	    log.debug("verificaInvioMailReg: areariservata attiva");
	    if (mail != null) {
		result = mail.getId().getCodice().toString();
	    }
	}
	return result;
    }

    private void checkrequest(String alias, String cf, String mac) {

	String oggi = Utilities.getToday("yyyyMMdd");
	String password = WebConstants.getDeployProperties().getProperty("ws.token.pwd");
	String macDaVerificare = alias + cf + oggi + password;
	macDaVerificare = Utilities.getHashText(macDaVerificare, Utilities.ALGORITHM_SHA1, false);
	if (!macDaVerificare.equalsIgnoreCase(mac)) {
	    throw new SecurityException();
	}
    }

    public static void main(String[] args) {

	String oggi = Utilities.getToday("yyyyMMdd");
	String password = "9a44bd17bbd836547850c08a75d17011";
	String cf = "BCCRCR73H23G888O";
	String macDaVerificare = "E256" + cf + oggi + password;
	macDaVerificare = Utilities.getHashText(macDaVerificare, Utilities.ALGORITHM_SHA1, false);
	System.out.println(macDaVerificare);
    }

    private boolean isInvioMail() {

	boolean inviaMail = this.verticalizzazioneAreaRiservataService.isAttiva()
		&& this.verticalizzazioneAreaRiservataService.isInvioMailNuovoUtenteRegistrato();
	if (inviaMail) {
	    String codiceMailTipo = StringUtils.defaultString(recuperaMailtipoInvioReset()).trim();
	    if (StringUtils.isNotBlank(codiceMailTipo)) {
		Integer codMail = Integer.parseInt(codiceMailTipo);
		Mailtipo m = mailtipoService.findById(new PkId(codMail));
		if (m != null) {
		    return true;
		}
	    }
	}
	return false;
    }

    private String recuperaMailtipoInvioReset() {

	String result = null;
	boolean isArearis = this.verticalizzazioneAreaRiservataService.isAttiva();
	if (isArearis) {
	    log.debug("verificaInvioMailReg: areariservata attiva");
	    Mailtipo mailReset = this.verticalizzazioneAreaRiservataService.getModelloPerInvioMailResetCredenziali();
	    if (mailReset != null) {
		return mailReset.getId().getCodice().toString();
	    }
	}
	return result;
    }

    @Autowired
    private SecurityWSClient securityWSClient;
    @Autowired
    private ExternalDBResolver externalDBResolver;
    private Serializer serializer;

    @OPTIONS
    @Path("/{var:.*}")
    public Response defaultOptions() {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	builder.header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
	builder.status(Status.OK);
	return builder.build();
    }

    protected Response rispostaWs(Object entity, Status returnStatus) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	return builder.build();
    }

    protected Response rispostaWsFile(Object entity, Status returnStatus, String nomeFile) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	builder.header("Content-Disposition", "attachment; filename=\"" + nomeFile + "\"");
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	return builder.build();
    }

    protected CodiceDescrizioneBean newNVBean(String descrizione, String codice) {

	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	result.setDescrizione(descrizione);
	result.setCodice(codice);
	return result;
    }

    protected void setORMHelper(String idcomunealias, String software) {

	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(software);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
    }

    protected CheckTokenResponse getTokenInfo(String token) throws Exception {

	CheckTokenRequest req = new CheckTokenRequest();
	req.setToken(token);
	req.setTokenInfo(true);
	CheckTokenResponse ti = securityWSClient.getWsPort().checkToken(req);
	return ti;
    }

    protected void checkrequest(Serializer serializer, CheckTokenResponse ti) throws Exception {

	if (!ti.isValid() || ti.getTokenInfo() == null) {
	    throw new RuntimeException("Richiesta non valida");
	}
	if (!(ti.getTokenInfo().getContesto().equals(ContestoType.UTE) || ti.getTokenInfo().getContesto().equals(ContestoType.UTEG))) {
	    throw new RuntimeException("Richiesta non valida");
	}
	String cf = ti.getTokenInfo().getUserid();
	if (StringUtils.isBlank(cf)) {
	    throw new RuntimeException("Richiesta non valida");
	}
    }

    protected Serializer getSerializer() {

	if (true /*this.serializer == null*/) {
	    this.serializer = new JsonSerializer();
	    serializer.setClassPropertyFilterHandler(new ClassPropertyFilterHandler() {

		@SuppressWarnings("rawtypes")
		@Override
		public ClassPropertyFilter getClassPropertyFilterByClass(Class arg0) {

		    if (arg0 == new CodiceDescrizioneBean().getClass()) {
			ClassPropertyFilter cpf = new ClassPropertyFilter(CodiceDescrizioneBean.class);
			cpf.setSupport4AddClassProperty(true);
			cpf.addProperties(new String[] { "class", "~unique-id~" });
			return cpf;
		    }
		    return null;
		}
	    });
	}
	return this.serializer;
    }
}
