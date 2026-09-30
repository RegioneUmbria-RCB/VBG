package it.gruppoinit.ws;

import it.gruppoinit.domain.AvvisoPagamentoInput;
import it.gruppoinit.schemas.messages.utilitypagopa.BollettinopagopaRequest;
import it.gruppoinit.schemas.messages.utilitypagopa.BollettinopagopaResponse;
import it.gruppoinit.schemas.messages.utilitypagopa.Utilititypagopa;
import it.gruppoinit.service.AvvisoPagamentoPdfService;
import it.gruppoinit.utilities.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.activation.DataHandler;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@javax.jws.WebService(serviceName = "UtilititypagopaService", portName = "Utilititypagopa11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/utilititypagopa", endpointInterface = "it.gruppoinit.schemas.messages.utilitypagopa.Utilititypagopa")
public class UtilititypagopaWS implements Utilititypagopa {

    private static final Logger log = LoggerFactory.getLogger(UtilititypagopaWS.class);
    @Autowired
    private AvvisoPagamentoPdfService avvisoPagamentoPdfService;

    @Override
    public BollettinopagopaResponse generabollettinopagopa(BollettinopagopaRequest bollettinopagopaRequest) {

	File file = null;
	log.debug("generabollettinopagopa# call generabollettinopagopa....");
	BollettinopagopaResponse r = new BollettinopagopaResponse();
	AvvisoPagamentoInput input = new AvvisoPagamentoInput();
	try {
	    log.debug("generabollettinopagopa# populateAvvisoPagamentoInput....");
	    avvisoPagamentoPdfService.populateAvvisoPagamentoInput(bollettinopagopaRequest, input);
	    log.debug("generabollettinopagopa# generaAvvisatuaPagoPa....");
	    file = avvisoPagamentoPdfService.generaAvvisatuaPagoPa(input);
	    InputStream targetStream = new FileInputStream(file);
	    byte[] bytes = IOUtils.toByteArray(targetStream);
	    DataHandler handler = Utilities.bytesToDataHandler(bytes);
	    r.setEsito("OK");
	    r.setBinaryData(handler);
	    log.debug("generabollettinopagopa# risultato OK");
	} catch (Exception e) {
	    log.error("generabollettinopagopa# E= {}", e);
	    r.setEsito(e.getMessage());
	} finally {
	    try {
		Utilities.gracefullyDeleteFiles(file);
	    } catch (Exception e) {
		log.error("generaAvvisatuaPagoPa# Non è stato possibile calcellare il file");
	    }
	}
	log.debug("generabollettinopagopa# end generabollettinopagopa....");
	return r;
    }
}
