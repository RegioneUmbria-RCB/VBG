package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.IstanzeStradarioRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.MovimentoRestBean;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.istanze.rest.AggiornaRiferimentiProtocolloIstanzaRequest;
import it.gruppoinit.pal.gp.core.features.movimenti.rest.AggiornaRiferimentiProtocolloMovimentoRequest;
import it.gruppoinit.pal.gp.core.service.IstanzeManager;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import net.sf.sojo.core.filter.ClassPropertyFilter;
import net.sf.sojo.core.filter.ClassPropertyFilterHandler;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

@Path("/istanze/")
public class IstanzeRestService {

    private static final Logger log = LoggerFactory.getLogger(IstanzeRestService.class);
    @Autowired
    private IstanzestradarioService istanzestradarioService;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private IstanzeManager istanzeManager;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;

    // GET
    // recupero la lista di istanze stradario
    @GET
    @Path("/stradario/{codiceistanza}")
    @Descriptions({ @Description(value = "Ottiene la lista di istanze stradario", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getListaIstanzdestradario(@PathParam("codiceistanza") Integer codiceIstanza) throws Exception {

	log.debug("getListaIstanzdestradario# codiceistanza={}", codiceIstanza);
	List<IstanzeStradarioRestBean> result = istanzestradarioService.listaIstanzeStradarioRest(codiceIstanza);
	Serializer serializer = getSerializer();
	String output = (String) serializer.serialize(result);
	return rispostaWs(output, Status.OK);
    }

    // dettaglio
    @GET
    @Path("/stradario/{codiceistanza}/{id}")
    @Descriptions({ @Description(value = "Ottiene il dettaglio di una istanza stradario", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getDettaglioIstanzestradario(@PathParam("codiceistanza") Integer codiceIstanza, @PathParam("id") Integer id) throws Exception {

	log.debug("getDettaglioIstanzdestradario# codice={}", id);
	IstanzeStradarioRestBean istanzeStradarioRestBean = istanzestradarioService.dettaglioIstanzastradarioRest(id, codiceIstanza);
	Serializer serializer = getSerializer();
	String output = (String) serializer.serialize(istanzeStradarioRestBean);
	return rispostaWs(output, Status.OK);
    }

    // dettaglio
    @PUT
    @Path("/stradario/{codiceistanza}/{id}")
    @Descriptions({ @Description(value = "Modifica una istanza stradario", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response aggiornaDettaglioIstanzestradario(@PathParam("codiceistanza") Integer codiceIstanza, @PathParam("id") Integer id, String json)
	    throws Exception {

	Serializer serializer = getSerializer();
	IstanzeStradarioRestBean bean = (IstanzeStradarioRestBean) serializer.deserialize(json, IstanzeStradarioRestBean.class);
	log.debug("getDettaglioIstanzdestradario# codice={}", id);
	IstanzeStradarioRestBean istanzeStradarioRestBean = istanzestradarioService.updateIstanzastradarioRest(bean, codiceIstanza, id);
	String output = (String) serializer.serialize(istanzeStradarioRestBean);
	return rispostaWs(output, Status.OK);
    }

    @DELETE
    @Path("/stradario/{codiceistanza}/{id}")
    @Descriptions({ @Description(value = "Elimina una istanza stradario", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response eliminaIstanzestradario(@PathParam("codiceistanza") Integer codiceIstanza, @PathParam("id") Integer id) throws Exception {

	log.debug("getDettaglioIstanzdestradario# codice={}", id);
	istanzestradarioService.deleteIstanzastradarioRest(codiceIstanza, id);
	return rispostaWs("ELIMINAZIONE CON SUCCESSO", Status.OK);
    }

    @POST
    @Path("/stradario/{codiceistanza}")
    @Descriptions({ @Description(value = "Inserisce una istanza stradario", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response inserisciDettaglioIstanzestradario(@PathParam("codiceistanza") Integer codiceIstanza, String json) throws Exception {

	Serializer serializer = getSerializer();
	IstanzeStradarioRestBean bean = (IstanzeStradarioRestBean) serializer.deserialize(json, IstanzeStradarioRestBean.class);
	IstanzeStradarioRestBean istanzeStradarioRestBean = istanzestradarioService.insertIstanzastradarioRest(bean);
	String output = (String) serializer.serialize(istanzeStradarioRestBean);
	return rispostaWs(output, Status.OK);
    }

    @POST
    @Path("/{codiceistanza}/movimenti")
    @Descriptions({ @Description(value = "Inserisce un movimento in un'istanza", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response inserisciMovimento(@PathParam("codiceistanza") Integer codiceIstanza, String json) throws Exception {

	Serializer serializer = getSerializer();
	MovimentoRestBean bean = (MovimentoRestBean) serializer.deserialize(json, MovimentoRestBean.class);
	MovimentoRestBean retval = this.movimentiService.insertRest(bean);
	String output = (String) serializer.serialize(retval);
	return rispostaWs(output, Status.OK);
    }

    @POST
    @Path("/replica-istanza")
    @Descriptions({ @Description(value = "Replica un'istanza e salva i dati delle schede dinamiche", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response creaRepliche(@QueryParam("codice-istanza") Integer codiceIstanza, @QueryParam("codice-intervento") Integer codiceIntervento,
	    String json) throws Exception {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	List<Integer> codiciIntervento = new ArrayList<Integer>();
	codiciIntervento.add(Integer.valueOf(codiceIntervento));
	if (istanza != null && codiceIntervento != null) {
	    List<Istanze> istanze = istanzeManager.creaRepliche(istanza, codiciIntervento, true);
	    String output = (String) serializer.serialize(istanze.get(0).getId().getCodice());
	    return rispostaWs(output, Status.OK);
	}
	return null;
    }

    @POST
    @Path("/aggiorna-protocollo-istanza")
    @Descriptions({ @Description(value = "Aggiorna i riferimenti del protocollo di un'istanza", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response aggiornaProtocolloIstanza(String json) throws JAXBException {

	try {
	    AggiornaRiferimentiProtocolloIstanzaRequest request = Utilities.unMarshallJsonString(json,
		    AggiornaRiferimentiProtocolloIstanzaRequest.class, Utilities.JAXB_ENCODING_UTF_8, true);
	    istanzeService.updateRiferimentiProtocollo(request);
	} catch (Exception e) {
	    log.error("", e);
	    return rispostaWs(Utilities.marshalJsonObject(new CodiceDescrizioneBean("500", e.getMessage()), CodiceDescrizioneBean.class, true,
		    Utilities.JAXB_ENCODING_UTF_8), Status.INTERNAL_SERVER_ERROR);
	}
	return rispostaWs(Utilities.marshalJsonObject(new CodiceDescrizioneBean("200", null), CodiceDescrizioneBean.class, false,
		Utilities.JAXB_ENCODING_UTF_8), Status.OK);
    }

    @POST
    @Path("/aggiorna-protocollo-movimento")
    @Descriptions({ @Description(value = "Aggiorna i riferimenti del protocollo di un'istanza", target = DocTarget.METHOD) })
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response aggiornaProtocolloMovimento(String json) throws JAXBException {

	try {
	    AggiornaRiferimentiProtocolloMovimentoRequest request = Utilities.unMarshallJsonString(json,
		    AggiornaRiferimentiProtocolloMovimentoRequest.class, Utilities.JAXB_ENCODING_UTF_8, true);
	    movimentiService.updateRiferimentiProtocolloMovimento(request);
	} catch (Exception e) {
	    log.error("", e);
	    return rispostaWs(Utilities.marshalJsonObject(new CodiceDescrizioneBean("500", e.getMessage()), CodiceDescrizioneBean.class, true,
		    Utilities.JAXB_ENCODING_UTF_8), Status.INTERNAL_SERVER_ERROR);
	}
	return rispostaWs(Utilities.marshalJsonObject(new CodiceDescrizioneBean("200", null), CodiceDescrizioneBean.class, false,
		Utilities.JAXB_ENCODING_UTF_8), Status.OK);
    }

    @GET
    @Path("/tipimovimento-soggetti-esterni/{codiceistanza}")
    @Descriptions({
	    @Description(value = "Ottiene il codicetipimovimento eseguito da frontoffice e marcato come rientro integrazione di una istanza", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getTipiMovimentoEseguitoDaFrontOffice(@PathParam("codiceistanza") Integer codiceIstanza) throws Exception {

	log.debug("getTipiMovimentoEseguitoDaFrontOffice# codiceIstanza={}", codiceIstanza);
	Tipimovimento tipiMov = tipiMovimentoService.findTipimovimentoBySoggettiEsterniAndRichiestaIntegrazioniAndCodiceIstanza(codiceIstanza,
		ORMHelper.getIdcomune());
	String output = "{\"risposta\":{\"tipologiaMovimenti\": SEGNAPOSTO_MOV}}";
	String tipomov = "null";
	if (tipiMov != null) {
	    tipomov = "\"" + tipiMov.getId().getTipomovimento() + "\"";
	}
	return rispostaWs(output.replace("SEGNAPOSTO_MOV", tipomov), Status.OK);
    }

    private Serializer serializer;

    protected Serializer getSerializer() {

	if (true /* this.serializer == null */) {
	    this.serializer = new JsonSerializer();
	    serializer.setClassPropertyFilterHandler(new ClassPropertyFilterHandler() {

		@Override
		public ClassPropertyFilter getClassPropertyFilterByClass(Class arg0) {

		    if (arg0 == IstanzeStradarioRestBean.class) {
			ClassPropertyFilter cpf = new ClassPropertyFilter(IstanzeStradarioRestBean.class);
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
}
