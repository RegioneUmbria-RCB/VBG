package it.gruppoinit.pal.gp.areariservata.filter;

import it.gruppoinit.pal.gp.areariservata.service.AnagrafeARJService;
import it.gruppoinit.pal.gp.areariservata.web.util.UserProfileConstants;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.rules.AnagrafeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class RegistrationFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(RegistrationFilter.class);
    private AnagrafeService anagrafeService;
    private AnagrafeARJService anagrafeARJService;

    @Override
    public void init(FilterConfig arg0) throws ServletException {

	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());
	anagrafeService = (AnagrafeService) wac.getBean("anagrafeServiceImpl");
	anagrafeARJService = (AnagrafeARJService) wac.getBean("anagrafeARJServiceImpl");
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {

	//	HttpServletRequest request = (HttpServletRequest) arg0;
	//	HttpServletResponse response = (HttpServletResponse) arg1;
	//	String authType = request.getParameter("AUTH_TYPE");
	//	if ("AUTH_TYPE_REG".equals(authType) || "AUTH_TYPE_REG_SC".equals(authType)) {
	//	    Anagrafe anagrafe = populate(request);
	//	    Anagrafe registeredUser = anagrafeARJService.findPFByCF(anagrafe.getCodicefiscale());
	//	    String esitoReg = "OK";
	//	    if (registeredUser != null) {
	//		esitoReg = "KO_USER_REGISTERED";
	//	    } else {
	//		boolean esito = doRegistration(anagrafe);
	//		if (!esito) {
	//		    esitoReg = "KO";
	//		}
	//	    }
	//	    request.getSession().setAttribute("ESITOREG", esitoReg);
	//	    request.getSession().setAttribute("AUTH_TYPE", authType);
	//	    request.getSession().setAttribute("utente", anagrafe);
	//	    response.sendRedirect(request.getContextPath() + "/home/registrazione.htm");
	//	    return;
	//	}
	arg2.doFilter(arg0, arg1);
    }

    @Override
    public void destroy() {

    }

    private boolean doRegistration(Anagrafe entity) {

	boolean success = false;
	AnagrafeBusinessRules anagrafeBusinessRules = new AnagrafeBusinessRules(true, true);
	SigeproBusinessRules.setClassRules(AnagrafeBusinessRules.class, anagrafeBusinessRules);
	try {
	    entity = anagrafeService.bindDomainObject(entity, PkId.class, "id.codice");
	    if (entity != null) {
		log.info("doRegistration: codiceanagrafe={}", entity.getId());
		success = true;
	    } else {
		log.error("doRegistration: anagrafeService.bindDomainObject return null");
	    }
	} catch (Exception e) {
	    log.error("doRegistration: {}", e.getMessage(), e);
	}
	return success;
    }

    private Anagrafe populate(HttpServletRequest request) {

	Anagrafe a = new Anagrafe();
	String nome = request.getParameter(UserProfileConstants.NOME);
	String cognome = request.getParameter(UserProfileConstants.COGNOME);
	String sesso = request.getParameter(UserProfileConstants.SESSO);
	String cf = request.getParameter(UserProfileConstants.CODICE_FISCALE);
	String dn = request.getParameter(UserProfileConstants.DATA_NASCITA);
	String cn = request.getParameter(UserProfileConstants.COMUNE_NASCITA);
	String ir = request.getParameter(UserProfileConstants.INDIRIZZO_RESIDENZA);
	String cr = request.getParameter(UserProfileConstants.COMUNE_RESIDENZA);
	String mail = request.getParameter(UserProfileConstants.EMAIL);
	log.info("populate:\nnome={}\ncognome={}\nsesso={}\ncf={}\ndn={}\ncn={}\nir={}\ncr={}\nmail={}", new Object[] { nome, cognome, sesso, cf, dn,
		cn, ir, cr, mail });
	a.setNome(nome);
	a.setNominativo(cognome);
	a.setSesso(sesso);
	a.setCodicefiscale(cf);
	try {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    a.setDatanascita(sdf.parse(dn));
	} catch (ParseException e) {
	    log.error("populate: errore durante il parsing della data di nascita: {}", dn);
	}
	Comuni comuneNascita = new Comuni();
	comuneNascita.setCf(cn);
	a.setComuneNascita(comuneNascita);
	a.setIndirizzo(ir);
	Comuni comuneResidenza = new Comuni();
	comuneResidenza.setCf(cf);
	a.setComuneResidenza(comuneResidenza);
	a.setEmail(mail);
	return a;
    }
}
