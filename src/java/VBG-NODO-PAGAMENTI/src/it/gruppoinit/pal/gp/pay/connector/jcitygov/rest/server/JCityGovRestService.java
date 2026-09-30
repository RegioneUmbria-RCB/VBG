package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.apache.http.entity.ContentType;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import it.gov.digitpa.schemas._2011.pagamenti.v_6_2_0.CtRicevutaTelematica;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.Esito;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.InfoPagamentoTelematicoDto;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.NotificaPagamentoRequest;
import it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schemanotify.StEsito;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayStatoPagamentiService;
import it.gruppoinit.pal.gp.pay.service.helper.AgidEsitiPagamentoEnum;
import it.gruppoinit.pal.gp.pay.service.helper.RTHelper;
import it.gruppoinit.pal.gp.pay.service.impl.PayPagamentiServiceImpl;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;

@Path(value = "/jcitygov/")
@Component
public class JCityGovRestService extends SpringBeanAutowiringSupport {

    private static final Logger log = LoggerFactory.getLogger(JCityGovRestService.class);
    @Autowired
    ConfigurazionePagamentiService configurazionePagamentiService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private PayPagamentiService payPagamentiService;
    @Autowired
    private PayStatoPagamentiService payStatoPagamentiService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/notifiche/pagamenti")
    public Response getNotificaEsitoRevoca(InputStream json) {

	Esito response = new Esito();
	String idDeb = null;
	try {
	    NotificaPagamentoRequest notificaPagamentoDebito = JCityGovRestService.unmarshal(NotificaPagamentoRequest.class, json, false);
	    if (notificaPagamentoDebito.getChiaveDebitoDto() == null) {
		throw new RuntimeException("Si è verificato un errore, chaveDebitoDto risulta null");
	    }
	    idDeb = notificaPagamentoDebito.getChiaveDebitoDto().getiDeb();
	    PayProfiliEntiCreditori profiloEnte = configurazionePagamentiService.configuraRequestIdAppPspAndIdPosizionePsp(
		    notificaPagamentoDebito.getChiaveDebitoDto().getCodEnteCreditore(), notificaPagamentoDebito.getChiaveDebitoDto().getiDeb());
	    //registra nuovo stato posizione
	    log.info("idPayPos {}, iDeb {}", notificaPagamentoDebito.getChiaveDebitoDto().getiPos(),
		    notificaPagamentoDebito.getChiaveDebitoDto().getiDeb());
	    try {
		List<PayPosizioniDebitorie> payPoss = payPosizioniDebitorieService
			.findAllByIdPosizionePSP(notificaPagamentoDebito.getChiaveDebitoDto().getiDeb());
		if (!payPoss.isEmpty()) {
		    for (PayPosizioniDebitorie payPos : payPoss) {
			verificaPagamento(notificaPagamentoDebito.getListaInfoPagamentoTelematicoDto(), payPos);
		    }
		    response.setEsito(StEsito.OK.name());
		    response.setMessaggio("Notifica di pagamento accettata");
		} else {
		    response.setEsito(StEsito.ERROR.name());
		    response.setMessaggio("Posizione: " + notificaPagamentoDebito.getChiaveDebitoDto().getiDeb() + " non trovata");
		}
		return rispostaWs(response, false);
	    } catch (PayException e) {
		log.error("Errore nella registrazione dell'aggiornamento di stato per la posizione debitoria {}-{}",
			notificaPagamentoDebito.getChiaveDebitoDto().getCodEnteCreditore(), profiloEnte.getCfCodiceProfilo());
	    }
	} catch (Exception e) {
	    log.error("Errore nella registrazione dell'aggiornamento di stato per la posizione debitoria {}-{}", e.getMessage(), e);
	}
	response.setEsito(StEsito.ERROR.name());
	response.setMessaggio("Errore nella ricerca del debito " + idDeb);
	return rispostaWs(response, false);
    }

    private boolean verificaPagamento(List<InfoPagamentoTelematicoDto> infoPagamentoTelematico, PayPosizioniDebitorie payPos) throws PayException {

	if (infoPagamentoTelematico == null || infoPagamentoTelematico.isEmpty()) {
	    log.error("infoPagamentoTelematico.isEmpty() per la posizione {}", payPos);
	    throw new PayException("infoPagamentoTelematico non trovato per la posizione " + payPos.getIdPosizionePsp());
	}
	for (InfoPagamentoTelematicoDto infoP : infoPagamentoTelematico) {
	    if (log.isDebugEnabled()) {
		log.debug("InfoPagamentoTelematicoDto {}", ReflectionToStringBuilder.toString(infoP));
	    }
	    String statoTecnico = infoP.getStatoTecnicoPagamento();
	    String esitoRichiesta = infoP.getEsitoRichiestaPagamento();
	    log.debug("payPos{}, StatoTecnico={}, EsitoRichiesta={}", payPos, statoTecnico, esitoRichiesta);
	    if ("CONCLUSO_ESEGUITO".equalsIgnoreCase(esitoRichiesta)) {
		log.debug("StatoPagamentoType.NOTIFICATO_DA_PSP");
		if ("CONTABILIZZATO".equalsIgnoreCase(statoTecnico) && StringUtils.isNotBlank(infoP.getFlussoRicevuta())) {
		    String rtXml = Utilities.decodeBase64Binary(infoP.getFlussoRicevuta());
		    CtRicevutaTelematica ctrt = RTHelper.parseRicevutaTelematica(rtXml);
		    if (ctrt.getDatiPagamento().getCodiceEsitoPagamento().equals(AgidEsitiPagamentoEnum.PAGAMENTO_ESEGUITO.getValore())
			    || ctrt.getDatiPagamento().getCodiceEsitoPagamento()
				    .equals(AgidEsitiPagamentoEnum.PAGAMENTO_PARZIALMENTE_ESEGUITO.getValore())) {
			DatiPagamentoType datiPag = RTHelper.popolaDatiPagamentoDaRicevutaTelematica(ctrt, null);
			DataSource ds;
			try {
			    ds = new ByteArrayDataSource(rtXml, ContentType.APPLICATION_OCTET_STREAM.getMimeType());
			    datiPag.setRicevutaXml(new DataHandler(ds));
			} catch (IOException e) {
			    log.error("Errore nella conversione della ricevuta per la posizione debitoria {}-{}", e.getMessage(), e);
			}
			StatoPagamentoType statoPag = StatoPagamentoType.RENDICONTATO_DA_IC;
			PayPagamenti payPagamenti = PayPagamentiServiceImpl.populateDomainObject(datiPag, payPos, statoPag);
			payPagamenti.setDataSistema(new Date());
			if (payPagamenti.getDataPagamento() == null) {
			    log.warn("data pagamento presa da infop.GetDataPagamento {}", infoP.getDataPagamento());
			    payPagamenti.setDataPagamento(Utilities.getDate(infoP.getDataPagamento(), "yyyy-MM-dd"));
			}
			payPagamenti.setIuv(infoP.getIdentificativoUnivocoVersamento());
			this.payPagamentiService.registraAvvenutoPagamento(payPagamenti, payPos, rtXml);
			log.debug("StatoPagamentoType.RENDICONTATO_DA_IC");
		    }
		} else {
		    PayStatoPagamenti newStatus = new PayStatoPagamenti();
		    newStatus.setDataEvento(new Date());
		    newStatus.setPosizioneDebitoria(payPos);
		    StatiPagamento pagato = StatiPagamento.NOTIFICATO_DA_PSP;
		    newStatus.setStato(pagato.name());
		    newStatus.setDescStato(pagato.description());
		    this.payStatoPagamentiService.insert(newStatus);
		}
		break;
	    }
	}
	return true;
    }

    @OPTIONS
    @Path("/{var:.*}")
    public Response defaultOptions() {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
	builder.header("Access-Control-Allow-Methods", "POST, OPTIONS");
	builder.status(Status.OK);
	return builder.build();
    }

    private Response rispostaWs(Object entity, boolean jsonIncludeRoot) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
	if (entity != null) {
	    String ret = null;
	    try {
		ret = JCityGovRestService.marshal(entity, jsonIncludeRoot);
	    } catch (JAXBException ex) {
		throw new RuntimeException(ex);
	    }
	    builder.entity(ret);
	}
	builder.status(Status.OK);
	return builder.build();
    }

    private static String marshal(Object obj, boolean includeRoot) throws JAXBException {

	StringWriter sw = new StringWriter();
	Map<String, Object> props = new HashMap<>();
	props.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
	props.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
	JAXBContext jc = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { obj.getClass() }, props);
	Marshaller marshaller = jc.createMarshaller();
	marshaller.marshal(obj, sw);
	return sw.toString();
    }

    private static <T> T unmarshal(Class<T> clazz, InputStream is, boolean includeRoot) throws JAXBException {

	Map<String, Object> props = new HashMap<>();
	props.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
	props.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);
	JAXBContext jc = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext(new Class[] { clazz }, props);
	Unmarshaller unmarshaller = jc.createUnmarshaller();
	return (T) unmarshaller.unmarshal(new StreamSource(is), clazz).getValue();
    }
}
