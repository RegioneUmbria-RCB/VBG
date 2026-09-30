package it.gruppoinit.pal.gp.pay.ws.client.bollettinopagopa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.AvvisiPagoPAService;
import it.gruppoinit.pal.gp.pay.service.impl.AvvisiPAgoPAServiceImpl;
import it.gruppoinit.schemas.messages.utilitypagopa.BollettinopagopaRequest;
import it.gruppoinit.schemas.messages.utilitypagopa.BollettinopagopaResponse;
import it.gruppoinit.schemas.messages.utilitypagopa.Utilititypagopa;

public class BollettinoPagoPAClient {

    private static final Logger log = LoggerFactory.getLogger(BollettinoPagoPAClient.class);
    private String url = null;
    private long connectionTimeout = 10000;
    private long readTimeout = 50000;

    public BollettinoPagoPAClient(PayConnectorWsEndpoint endpoint) {

	super();
	this.url = endpoint.getEndpointUrl();
    }

    public BollettinoPagoPAClient(PayConnectorWsEndpoint endpoint, long connectionTimeout, long readTimeout) {

	this(endpoint);
	this.connectionTimeout = connectionTimeout;
	this.readTimeout = readTimeout;
    }

    private Utilititypagopa getWsPort() {

	log.debug("getWsPOrt Bollettino {}", this.url);
	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	Map<String, Object> props = new HashMap<>();
	props.put("mtom-enabled", Boolean.TRUE);
	factory.setProperties(props);
	factory.setServiceClass(Utilititypagopa.class);
	factory.setAddress(this.url);
	Utilititypagopa info = (Utilititypagopa) factory.create();
	Client client = ClientProxy.getClient(info);
	HTTPConduit conduit = (HTTPConduit) client.getConduit();
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	httpClientPolicy.setConnectionTimeout(connectionTimeout);
	httpClientPolicy.setReceiveTimeout(readTimeout);
	conduit.setClient(httpClientPolicy);
	return info;
    }

    public DataHandler generaAvviso(BollettinopagopaRequest r) throws PayException {

	String mesg = "Si è Verificato un errore nella generazione dell'avviso. ";
	try {
	    BollettinopagopaResponse res = getWsPort().generabollettinopagopa(r);
	    if (res.getEsito().equals("OK")) {
		return res.getBinaryData();
	    }
	    mesg += "Esito " + res.getEsito();
	} catch (Exception e) {
	    mesg += e.getMessage();
	    log.error(mesg);
	}
	throw new PayException(mesg);
    }

    public static void main(String[] args) throws Exception {

	AvvisiPagoPAService h = new AvvisiPAgoPAServiceImpl();
	PayPosizioniDebitorie pos = new PayPosizioniDebitorie();
	PaySoggettiDebitori sd = new PaySoggettiDebitori();
	sd.setNome("Riccardo Bocci");
	sd.setVia("Via armonica, 5");
	sd.setLocalita("Perugia");
	sd.setCfPi("BCCRCR73H23G888O");
	PayDettaglioImporti imp = new PayDettaglioImporti();
	imp.setImporto(new BigDecimal("10.01"));
	pos.getDettagliImporto().add(imp);
	PayDettaglioImporti imp2 = new PayDettaglioImporti();
	imp2.setImporto(new BigDecimal("10.02"));
	pos.getDettagliImporto().add(imp2);
	pos.setSoggettoDebitore(sd);
	pos.setIuv("0222222222");
	pos.setCodiceAvviso("40020222222222");
	pos.setQrCode("PAGOPA|2|0222222222|02297100545");
	pos.setDescrizioneCausale("Pagamento oneri pratica 123/2021");
	pos.setDataRegistrazione(Calendar.getInstance().getTime());
	PayProfiliEntiCreditori cf = new PayProfiliEntiCreditori();
	cf.setCfEnteQrcodePagopa("01907990012");
	cf.setCbill("J7608");
	cf.setCcPostale("cposte21");
	Amministrazioni amm = new Amministrazioni();
	amm.setAmministrazione("Città Metropolitana di Torino");
	amm.setIndirizzo("Corso Inghilterra, 7");
	amm.setCap("10138");
	amm.setCitta("Torino");
	amm.setProvincia("TO");
	amm.setWeb("http://www.cittametropolitana.torino.it/tributi/pagopa.shtml");
	cf.setAmministrazione(amm);
	BollettinopagopaRequest r = h.popolaAvvisoRequestDaPosizioneDebitoria(pos, cf);
	PayConnectorWsEndpoint p = new PayConnectorWsEndpoint();
	p.setEndpointUrl("http://localhost:8080/bollettinopagopa/services/utilititypagopa?wsdl");
	BollettinoPagoPAClient c = new BollettinoPagoPAClient(p);
	BollettinopagopaResponse res = c.getWsPort().generabollettinopagopa(r);
	if (res.getEsito().equals("OK")) {
	    System.out.println("generabollettinopagopa.result=" + res.getEsito());
	    InputStream is = res.getBinaryData().getInputStream();
	    OutputStream os = new FileOutputStream(new File("C:/Temp/PAGOPA_" + System.currentTimeMillis() + ".pdf"));
	    // This will copy the file from the two streams
	    IOUtils.copy(is, os);
	    // This will close two streams catching exception
	    IOUtils.closeQuietly(os);
	    IOUtils.closeQuietly(is);
	} else {
	    System.out.println("generabollettinopagopa.result=" + res.getEsito());
	}
    }
}
