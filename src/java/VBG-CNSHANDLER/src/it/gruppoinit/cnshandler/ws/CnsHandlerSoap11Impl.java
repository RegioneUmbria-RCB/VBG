package it.gruppoinit.cnshandler.ws;

import it.cefriel.utility.smartcard.CardUserData;
import it.gruppoinit.cnshandler.schema.GetUserDataRequest;
import it.gruppoinit.cnshandler.schema.GetUserDataResponse;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CnsHandlerSoap11Impl implements CnsHandler {

    private static final Log log = LogFactory.getLog(CnsHandlerSoap11Impl.class);

    public GetUserDataResponse getUserData(GetUserDataRequest getUserDataRequest) throws RemoteException {

	String ticket = getUserDataRequest.getTicket();
	log.info("getUserData: ticket=" + ticket);
	MessageContext mycontext = MessageContext.getCurrentContext();
	HttpServlet servlet = (HttpServlet) mycontext.getProperty(HTTPConstants.MC_HTTP_SERVLET);
	ServletContext servletCtx = servlet.getServletContext();
	CardUserData cardUserData = (CardUserData) servletCtx.getAttribute(ticket);
	if (cardUserData == null) {
	    log.error("getUserData: ticket='" + ticket + "'. Object CardUserData not found");
	    throw new RemoteException("Dati utente non trovati per il ticket: '" + ticket + "'");
	}
	GetUserDataResponse getUserDataResponse = getResponse(cardUserData);
	servletCtx.removeAttribute(ticket);
	return getUserDataResponse;
    }

    private GetUserDataResponse getResponse(CardUserData cardUserData) {

	GetUserDataResponse r = new GetUserDataResponse();
	r.setCodiceFiscale(cardUserData.getCodiceFiscale());
	r.setNome(cardUserData.getNome());
	r.setCognome(cardUserData.getCognome());
	r.setEmail(cardUserData.getEmail());
	try {
	    if (StringUtils.isNotBlank(cardUserData.getDataNascita())) {
		SimpleDateFormat sdf = new SimpleDateFormat(cardUserData.getDatePattern());
		Date dataDiNascita = sdf.parse(cardUserData.getDataNascita());
		r.setDataDiNascita(dataDiNascita);
	    }
	} catch (Exception e) {
	    log.warn("getResponse: error parsing date: '" + cardUserData.getDataNascita() + "'", e);
	}
	return r;
    }
}
