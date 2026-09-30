package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.definitions.iride.IrideMessaggio;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.iride.IrideMessaggioRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.iride.IrideMessaggioResponse;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.helper.IrideMessageUtil;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@WebService(serviceName = "IrideMessaggioService", portName = "IrideMessaggioSoap11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/iride", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.iride.IrideMessaggio")
public class IrideWS extends BaseWS implements IrideMessaggio {

    private static final Logger log = LoggerFactory.getLogger(IrideWS.class);
    private MovimentiService movimentiService;
    private IstanzeeventiService istanzeeventiService;

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    public IrideMessaggioResponse irideMessaggio(IrideMessaggioRequest request) {

	log.debug("getIrideMessaggio(token={},docid={})", request.getToken(), request.getDocid());
	setORMHelper(request.getSoftware(), request.getToken());
	IrideMessaggioResponse response = new IrideMessaggioResponse();
	response.setDocid(request.getDocid());
	IrideMessageUtil irideMessageUtil = new IrideMessageUtil();
	try {
	    String fkidprotocollo = String.valueOf(request.getDocid());
	    List<Movimenti> movs = movimentiService.findMovimentoPerFkIdProtocollo(fkidprotocollo);
	    if (movs.isEmpty()) {
		log.error("Nessun movimento trovato per fkidprotocollo {}", fkidprotocollo);
		response.setEsito("1");
		response.setMessaggio("Nessun movimento trovato con Docid = " + fkidprotocollo);
	    } else {
		String op = request.getOperazione();
		String msg = irideMessageUtil.getMessaggio(op, request.getEsito(), request.getMessaggio());
		for (Movimenti mov : movs) {
		    if (IrideMessageUtil.opFirma.equals(op)) {
			istanzeeventiService.updateSegnaComeLetto(IstanzeeventiConstants.CATEGORIA_FIRMA, mov);
			istanzeeventiService.insert(msg, IstanzeeventiConstants.CATEGORIA_FIRMA, mov, null);
		    }
		    if (IrideMessageUtil.opProtocollazione.equals(op)) {
			if (request.getEsito().equals("0")) {
			    // il messaggio contiene il numero protocollo nella forma: aaaa/nnnnnnn 
			    mov.setNumeroprotocollo(request.getMessaggio());
			    // FIXME da iride non torna la data del protocollo. La setto alla data corrente
			    mov.setDataprotocollo(new Date());
			    movimentiService.update(mov);
			}
			istanzeeventiService.updateSegnaComeLetto(IstanzeeventiConstants.CATEGORIA_PROTOCOLLO, mov);
			istanzeeventiService.insert(msg, IstanzeeventiConstants.CATEGORIA_PROTOCOLLO, mov, null);
		    }
		    if (IrideMessageUtil.opSpedizioneMail.equals(op)) {
			istanzeeventiService.updateSegnaComeLetto(IstanzeeventiConstants.CATEGORIA_MAIL, mov);
			istanzeeventiService.insert(msg, IstanzeeventiConstants.CATEGORIA_MAIL, mov, null);
		    }
		    if (IrideMessageUtil.opAcquisizioneMail.equals(op)) {
			if (request.getEsito().equals("0")) {
			    if (request.getMessaggio().equals("posta certificata")) {
				// TODO recuperare pratica da iride...
				//1. leggere i dettagli del protocollo iride
				//2. lettura oggetto e corpo per identificare se DPR160
				//3. parsing degli allegati xml (tra i quali deve esserci il xxx-MDA.xml)
				//4. inserimento pratica tramite NLA
			    }
			}
			istanzeeventiService.insert(msg, IstanzeeventiConstants.CATEGORIA_MAIL, mov, null);
		    }
		}
		response.setEsito("0");
		response.setMessaggio("Operazione eseguita con successo");
	    }
	} catch (Exception e) {
	    log.error("getIrideMessaggio(): {}", e.getMessage());
	    response.setEsito("2");
	    response.setMessaggio("Errore durante l'elaborazione del messaggio: " + e.getMessage());
	} finally {
	    resetThreadLocalVars();
	}
	return response;
    }
}
