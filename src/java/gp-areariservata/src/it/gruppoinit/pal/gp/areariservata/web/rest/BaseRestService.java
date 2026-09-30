package it.gruppoinit.pal.gp.areariservata.web.rest;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.WebAuthenticationDetailsSource;
import org.springframework.security.userdetails.UserDetails;

import com.sun.syndication.io.impl.Base64;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeDTO;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceNomeBean;
import it.gruppoinit.pal.gp.core.domain.helper.ComuneGraduatorieRestBean;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.TitolareAutorizzazioneGradRestBean;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafePFRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafePGRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafeRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafeSedeRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.EsitoChiamataLista;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.IstanzaLista;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.LocalizzazioneIstanza;
import it.gruppoinit.pal.gp.core.exception.SecurityException;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniConcessioniPresenzeRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniConcessioniRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniGraduatoriaRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.ComuniDTO;
import it.gruppoinit.pal.gp.core.service.helper.GiorniMercatoPresenzeRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.GraduatorieMercatiBeanHelper;
import it.gruppoinit.pal.gp.core.service.helper.MercatiPresenzeStoricoRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.MercatiRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatiDaEffettuareHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatiHelper;
import it.gruppoinit.pal.gp.core.service.helper.PosteggioLiberoHelper;
import it.gruppoinit.pal.gp.core.service.helper.StradarioDTO;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import it.gruppoinit.sigeprosecurity.schema.LoginSSORequest;
import it.gruppoinit.sigeprosecurity.schema.LoginSSOResponse;
import it.gruppoinit.sigeprosecurity.schema.LogoutRequest;
import net.sf.sojo.core.filter.ClassPropertyFilter;
import net.sf.sojo.core.filter.ClassPropertyFilterHandler;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

public class BaseRestService {

    private static final Logger log = LoggerFactory.getLogger(BaseRestService.class);
    public static final String AUTHORIZATION = "Authorization";
    @Autowired
    private SecurityWSClient securityWSClient;
    @Autowired
    private ExternalDBResolver externalDBResolver;
    private Serializer serializer;
    private AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();
    @Context
    private MessageContext context;
    @Autowired
    private UserSecurityService userSecurityService;

    @OPTIONS
    @Path("/{var:.*}")
    public Response defaultOptions() {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type, authorization");
	builder.header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
	builder.status(Status.OK);
	return builder.build();
    }

    protected Response rispostaWs(Object entity, Status returnStatus, List<CodiceDescrizioneBean> headerAggiuntivi) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	if (headerAggiuntivi != null) {
	    for (CodiceDescrizioneBean cdb : headerAggiuntivi) {
		builder.header(cdb.getCodice(), cdb.getDescrizione());
	    }
	}
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
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

    protected Response rispostaWsFile(Object entity, Status returnStatus, String nomeFile, List<CodiceDescrizioneBean> headerAggiuntivi) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	builder.header("Content-Disposition", "attachment; filename=\"" + nomeFile + "\"");
	if (headerAggiuntivi != null) {
	    for (CodiceDescrizioneBean cdb : headerAggiuntivi) {
		builder.header(cdb.getCodice(), cdb.getDescrizione());
	    }
	}
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

    protected void setORMHelperFromCodiceServizio(String codiceServizio) {

	String[] aliasSoftware = codiceServizio.split("-");
	String idcomunealias = aliasSoftware[0];
	String software = aliasSoftware[1];
	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(software);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
    }

    protected CheckTokenResponse getTokenInfo(String token) throws SecurityException {

	CheckTokenRequest req = new CheckTokenRequest();
	req.setToken(token);
	req.setTokenInfo(true);
	CheckTokenResponse ti = null;
	try {
	    ti = securityWSClient.getWsPort().checkToken(req);
	} catch (Exception e) {
	    throw new SecurityException(e);
	}
	return ti;
    }

    protected String refreshToken(String token) throws SecurityException {

	LoginSSORequest lsso = new LoginSSORequest();
	CheckTokenResponse ti = getTokenInfo(token);
	lsso.setAlias(ti.getTokenInfo().getAlias());
	lsso.setContesto(ti.getTokenInfo().getContesto());
	lsso.setUsername(ti.getTokenInfo().getUserid());
	lsso.setIpAddress(ti.getTokenInfo().getClientIp());
	LoginSSOResponse loginSSO;
	try {
	    loginSSO = securityWSClient.getWsPort().loginSSO(lsso);
	    LogoutRequest lo = new LogoutRequest();
	    lo.setToken(token);
	    securityWSClient.getWsPort().logout(lo);
	    return loginSSO.getToken();
	} catch (Exception e) {
	    throw new SecurityException(e);
	}
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

	this.serializer = new JsonSerializer();
	serializer.setClassPropertyFilterHandler(new ClassPropertyFilterHandler() {

	    @Override
	    public ClassPropertyFilter getClassPropertyFilterByClass(Class arg0) {

		if (arg0 == new EsitoChiamataLista().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(EsitoChiamataLista.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new LocalizzazioneIstanza().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(LocalizzazioneIstanza.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new IstanzaLista().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(IstanzaLista.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new ComuniDTO().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(ComuniDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new DettaglioAnagrafeRestBean().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(DettaglioAnagrafeRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new DettaglioAnagrafePFRestBean().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(DettaglioAnagrafePFRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new DettaglioAnagrafePGRestBean().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(DettaglioAnagrafePGRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new DettaglioAnagrafeSedeRestBean().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(DettaglioAnagrafeSedeRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new CodiceDescrizioneBean().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(CodiceDescrizioneBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new AutorizzazioniConcessioniRestHelper().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniConcessioniRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new ChiaveValoreBean().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(ChiaveValoreBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new MercatiRestHelper().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatiRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new AnagrafeDTO().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AnagrafeDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new MercatiDDTO().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatiDDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new AutorizzazioniDTO().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new MercatipresenzeDDTO().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatipresenzeDDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new PkId().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PkId.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new MercatiPresenzeStoricoRestHelper().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(MercatiPresenzeStoricoRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new GiorniMercatoPresenzeRestHelper().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(GiorniMercatoPresenzeRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new AutorizzazioniConcessioniPresenzeRestHelper().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniConcessioniPresenzeRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new PosteggioLiberoHelper().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PosteggioLiberoHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new PagamentiMercatiHelper().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PagamentiMercatiHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new PagamentiMercatiDaEffettuareHelper().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(PagamentiMercatiDaEffettuareHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new StradarioDTO().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(StradarioDTO.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new GraduatorieMercatiBeanHelper().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(GraduatorieMercatiBeanHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new AutorizzazioniGraduatoriaRestHelper().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(AutorizzazioniGraduatoriaRestHelper.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new TitolareAutorizzazioneGradRestBean().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(TitolareAutorizzazioneGradRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new ComuneGraduatorieRestBean().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(ComuneGraduatorieRestBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new CodiceNomeBean().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(CodiceNomeBean.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		if (arg0 == new StatiistanzaType().getClass()) {
		    ClassPropertyFilter cpf = new ClassPropertyFilter(StatiistanzaType.class);
		    cpf.setSupport4AddClassProperty(true);
		    cpf.addProperties(new String[] { "class", "~unique-id~" });
		    return cpf;
		}
		return null;
	    }
	});
	return this.serializer;
    }

    protected void authenticate(String auth) throws SecurityException {

	setORMHelper(auth);
    }

    private void setORMHelperTest(HttpServletRequest request) {

	Properties connProps = externalDBResolver.getConnectionProperties("L219");
	ORMHelper.setIdcomune("L219");
	ORMHelper.setIdcomuneAlias("L219");
	ORMHelper.setSoftware("CO");
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	UsernamePasswordAuthenticationToken authToken = null;
	UserDetails user = userSecurityService.loadUserByUsername("admin");
	authToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	authToken.setDetails(authenticationDetailsSource.buildDetails(request));
	SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    protected void setORMHelper(String headerauth) throws SecurityException {

	//	{
	//            "token": "7cae862a-0081-4f27-bf1c-4d3df00546fb",
	//            "alias": "L219",
	//            "software": "CO"
	//	}
	HttpServletRequest request = context.getHttpServletRequest();
	String decode = Base64.decode(headerauth);
	Serializer serializer = new JsonSerializer();
	Map<String, String> o = (Map<String, String>) serializer.deserialize(decode);
	if (o.get("token") != null) {
	    try {
		CheckTokenResponse r = getTokenInfo(o.get("token"));
		boolean autenticato = (r != null && r.getTokenInfo() != null && r.isValid());
		if (autenticato) {
		    Properties connProps = externalDBResolver.getConnectionProperties(r.getTokenInfo().getAlias());
		    ORMHelper.setToken(o.get("token"));
		    ORMHelper.setIdcomune(r.getTokenInfo().getIdcomune());
		    ORMHelper.setIdcomuneAlias(r.getTokenInfo().getAlias());
		    ORMHelper.setSoftware(o.get("software"));
		    ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
		    UsernamePasswordAuthenticationToken authToken = null;
		    UserDetails user = userSecurityService.loadUserByUsername(r.getTokenInfo().getUserid());
		    authToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
		    authToken.setDetails(authenticationDetailsSource.buildDetails(request));
		    SecurityContextHolder.getContext().setAuthentication(authToken);
		} else {
		    throw new SecurityException("Errore nell'autenticazione");
		}
	    } catch (Exception e) {
		log.error("Errore in autenticazione " + e.getMessage(), e);
		throw new SecurityException("Errore nell'autenticazione");
	    }
	}
    }

    protected String XMLGCToString(XMLGregorianCalendar c) {

	if (c != null) {
	    return Utilities.formatDate(c.toGregorianCalendar().getTime(), false);
	}
	return "";
    }
}
