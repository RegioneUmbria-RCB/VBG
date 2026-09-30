package it.gruppoinit.pal.gp.core.features.protocollazione.logic.notificafirma;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;

@Service
public class NotificaFirmaServiceImpl implements NotificaFirmaService {

    private static String urlNotificaFirmaNET = "/web-api/notificafirma/{ALIAS}/{SOFTWARE}";
    private final Logger log = LoggerFactory.getLogger(NotificaFirmaServiceImpl.class);
    private NotificaFirmaDAO notificaDAO;

    @Autowired
    public void setNotificaDAO(NotificaFirmaDAO notificaDAO) {

	this.notificaDAO = notificaDAO;
    }

    @Override
    public void verificaFirmeNonNotificate() {

	List<String> idProtocolli = this.notificaDAO.findFirmeNonNotificate();
	this.log.debug("Trovati " + idProtocolli.size() + " di cui non è stata ricevuta la notifica della firma");
	for (String idProtocollo : idProtocolli) {
	    try {
		this.log.debug("Inizio verifica firma per il protocollo con id " + idProtocollo);
		NotificaFirmaResponse response = this.verificaFirma(idProtocollo);
		this.log.debug("Esito: " + response);
	    } catch (Exception e) {
		this.log.debug("Errore:", e);
	    } finally {
		this.log.debug("Fine verifica firma per il protocollo con id " + idProtocollo);
	    }
	}
	this.log.debug("Fine della verifica delle firme non notificate");
    }

    private NotificaFirmaResponse verificaFirma(String idProtocollo) throws FunzioneBusinessRemotaException {

	NotificaFirmaRequest request = NotificaFirmaRequest.fromIdProtocollo(idProtocollo);
	return new NotificaFirmaRestClient(this.getUrl()).notifica(request);
    }

    private String getUrl() {

	String result = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_ASPNET);
	result += urlNotificaFirmaNET;
	result = result.replace("{ALIAS}", ORMHelper.getIdcomuneAlias());
	result = result.replace("{SOFTWARE}", ORMHelper.getSoftware());
	return result;
    }
}
