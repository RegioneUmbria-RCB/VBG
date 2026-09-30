package it.gruppoinit.pal.gp.areariservata.web.rest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lowagie.text.DocumentException;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.exception.MercatiAppException;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniGraduatoriaRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniGraduatoriaRestHelperComparator;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniGraduatoriaRestHelperComparator.TIPO_COMPARAZIONE;
import it.gruppoinit.pal.gp.core.service.helper.DettaglioErroreRestBean;
import it.gruppoinit.pal.gp.core.service.helper.GraduatorieMercatiBeanHelper;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import net.sf.sojo.interchange.Serializer;

@Path("/servizi-graduatorie/")
public class GraduatorieRestService extends BaseRestService {

    private static final String X_REQUEST_ID = "X-Request-ID";
    private static final String X_CODICE_SERVIZIO = "X-Codice-Servizio";
    private static final String X_IDENTITA_CODICE_FISCALE = "X-Identita-CodiceFiscale";
    private static final Logger log = LoggerFactory.getLogger(GraduatorieRestService.class.getName());
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    private IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService;

    @Autowired
    public void setComportamentiMercatiService(IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService) {

	this.comportamentiMercatiService = comportamentiMercatiService;
    }

    @GET
    @Path("/mercati/graduatoria")
    @Descriptions({ @Description(value = "servizio per la ricerca della graduatoria tramite descrizione", target = DocTarget.METHOD) })
    public Response ricercaGraduatoriaDescrizione(@HeaderParam(X_IDENTITA_CODICE_FISCALE) String cf,
	    @HeaderParam(X_CODICE_SERVIZIO) String codiceServizio, @HeaderParam(X_REQUEST_ID) String requestId, @QueryParam("mercato") String mercato,
	    @QueryParam("giorno") String giorno, @QueryParam("format") String format, @QueryParam("content_disposition") String content_disposition)
	    throws Exception {

	// imposta ORMHELPER e verifica parametri obbligatori es CodiceServizio, request-id, ecc...
	if (StringUtils.isBlank(content_disposition)) {
	    content_disposition = "attachment";
	}
	List<CodiceDescrizioneBean> headerAggiuntivi = null;
	Serializer serializer = getSerializer();
	try {
	    headerAggiuntivi = headerAggiuntivi(requestId, codiceServizio, cf);
	} catch (MercatiAppException e) {
	    int statusErr = 400;
	    String res = buildDettaglioErroreRestBean(e, statusErr, serializer);
	    return rispostaWs(res, Status.BAD_REQUEST, headerAggiuntivi);
	}
	try {
	    setORMHelperFromCodiceServizio(codiceServizio);
	} catch (RuntimeException e) {
	    int statusErr = 500;
	    MercatiAppException ex = new MercatiAppException("999", "Errore nel recupero dei parametri");
	    String res = buildDettaglioErroreRestBean(ex, statusErr, serializer);
	    return rispostaWs(res, Status.INTERNAL_SERVER_ERROR, headerAggiuntivi);
	}
	boolean addGiorno = this.comportamentiMercatiService.servGradAddNomeGiorno();
	if (Boolean.TRUE.equals(addGiorno)) {
	    mercato = mercato + " " + giorno;
	}
	try {
	    GraduatorieMercatiBeanHelper g = mercatipresenzeTService.graduatoriaMercati(mercato, giorno);
	    log.debug(
		    "X_IDENTITA_CODICE_FISCALE: {}, X_CODICE_SERVIZIO: {}, X_REQUEST_ID: {}, mercato: {}, giorno: {} format: {},content_disposition: {}",
		    new Object[] { codiceServizio, cf, requestId, mercato, giorno, format, content_disposition });
	    if (StringUtils.isBlank(format)) {
		String str = (String) serializer.serialize(g);
		// return rispostaWs(str, Status.OK, headerAggiuntivi);
		ResponseBuilder builder = new ResponseBuilderImpl();
		builder.header("Access-Control-Allow-Origin", "*");
		builder.header("Access-Control-Allow-Headers", "Content-Type");
		ResponseBuilder ok = Response.ok(str, MediaType.APPLICATION_JSON);
		return ok.build();
	    }
	    // al momento solo CSV
	    InputStream is = convertGraduatoria(g, format, !addGiorno);
	    String estensione = "csv";
	    String contentType = "text/csv";
	    if ("pdf".equalsIgnoreCase(format)) {
		estensione = "pdf";
		contentType = "application/pdf";
	    }
	    List<CodiceDescrizioneBean> cdbs = new ArrayList<CodiceDescrizioneBean>();
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("Content-Disposition");
	    cdb.setDescrizione(content_disposition + "; filename=\"graduatoria." + estensione + "\"");
	    cdbs.add(cdb);
	    cdb = new CodiceDescrizioneBean();
	    cdb.setCodice("Content-Type");
	    cdb.setDescrizione(contentType);
	    cdbs.add(cdb);
	    return rispostaWs(is, Status.OK, cdbs);
	} catch (MercatiAppException e) {
	    String str = "Errore interno codice: " + e.getErrore().getCodice() + ", descrizione: " + e.getErrore().getDescrizione();
	    return rispostaWs(str, Status.INTERNAL_SERVER_ERROR, headerAggiuntivi);
	} finally {
	    ORMHelper.destroyORMHelper();
	}
    }

    private InputStream convertGraduatoria(GraduatorieMercatiBeanHelper g, String format, boolean addGiorno)
	    throws DocumentException, FileNotFoundException {

	if ("pdf".equalsIgnoreCase(format)) {
	    StringBuffer str = new StringBuffer();
	    str.append("<html><style>body{font-face: Verdana, Arial; font-size: 12px;}").append(" table{font-face: Verdana, Arial; font-size: 8px;} ")
		    .append(" th{font-face: Verdana, Arial; font-size: 10px;background-color: #c0c0c0} ")
		    .append(" td{font-face: Verdana, Arial; font-size: 8px;}</style><body>")
		    .append(" <p><font face=\"arial, helvetica, sans-serif\"><b>Graduatoria in data&nbsp;")
		    .append(g.getData_riferimento_graduatoria()).append("</b></font></p>");
	    str.append("<table border=\"1\" cellpadding=\"1\" cellspacing=\"0\">");
	    str.append("	<thead>");
	    str.append("		<tr>");
	    str.append("			<th>aut num</th>");
	    //str.append("			<th>anno</th>");
	    str.append("			<th>aut originaria</th>");
	    str.append("			<th>presenze</th>");
	    str.append("			<th>comune</th>");
	    str.append("			<th>provincia</th>");
	    str.append("			<th>mercato</th>");
	    if (addGiorno) {
		str.append("			<th>giorno</th>");
	    }
	    str.append("			<th>titolare</th>");
	    str.append("		</tr>");
	    str.append("	</thead>");
	    str.append("	<tbody>");
	    List<AutorizzazioniGraduatoriaRestHelper> auts = g.getAutorizzazioni();
	    Collections.sort(auts, new AutorizzazioniGraduatoriaRestHelperComparator(TIPO_COMPARAZIONE.NUMERO_PRESENZE));
	    for (AutorizzazioniGraduatoriaRestHelper agr : auts) {
		String riferimentiProtocollo = "";
		if (StringUtils.isNotBlank(agr.getProtocollo())) {
		    riferimentiProtocollo = "<br/><small>prot. " + agr.getProtocollo();
		    if (StringUtils.isNotBlank(agr.getData_protocollo())) {
			riferimentiProtocollo += " del " + agr.getData_protocollo();
		    }
		    riferimentiProtocollo += "</small>";
		}
		str.append("<tr>                                                                             ");
		str.append(" <td>").append(StringUtils.defaultString(agr.getNumero())).append(riferimentiProtocollo).append("</td>");
		//str.append(" <td>").append(agr.getAnno()).append("</td>");
		str.append(" <td>").append(StringUtils.defaultString(agr.getOriginaria())).append("</td>");
		str.append(" <td>").append(agr.getNumero_presenze()).append("</td>");
		if (agr.getComune_rilascio() != null) {
		    str.append(" <td>").append(StringUtils.defaultString(agr.getComune_rilascio().getNome())).append("</td>");
		    str.append(" <td>").append(StringUtils.defaultString(agr.getComune_rilascio().getSigla_provincia())).append("</td>");
		} else {
		    str.append(" <td>&nbsp;</td>");
		    str.append(" <td>&nbsp;</td>");
		}
		if (agr.getMercato() != null) {
		    str.append(" <td>").append(StringUtils.defaultString(agr.getMercato().getNome())).append("</td>");
		} else {
		    str.append(" <td>&nbsp;</td>");
		}
		if (addGiorno) {
		    if (agr.getGiorno() != null) {
			str.append(" <td>").append(StringUtils.defaultString(agr.getGiorno().getNome())).append("</td>");
		    } else {
			str.append(" <td>&nbsp;</td>");
		    }
		}
		if (agr.getTitolare() != null) {
		    str.append(" <td>").append(StringUtils.defaultString(agr.getTitolare().getDenominazione())).append("</td>");
		} else {
		    str.append(" <td>&nbsp;</td>");
		}
		str.append(" </tr>");
	    }
	    str.append(" </tbody>");
	    str.append("</table></body></html>");
	    FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	    ConvertBinaryRequest req = new ConvertBinaryRequest();
	    req.setToken(System.currentTimeMillis() + ""); // token non necessario
	    req.setConversionType(FileConverterWsClient.ConversionType.PDF.name());
	    req.setContentType(FileConverterWsClient.ConversionType.HTML.name());
	    req.setBinaryData(str.toString().getBytes());
	    ConvertBinaryResponse resp = null;
	    try {
		resp = fileConverterWsClient.convertBinary(req);
	    } catch (Exception e) {
		log.error("Errore durante la conversione del file in PDF: {}, \n{}", e.getMessage(), e);
		throw new RuntimeException("Errore durante la conversione del file in PDF: " + e.getMessage(), e);
	    }
	    InputStream result = new ByteArrayInputStream(resp.getBinaryData());
	    // FileOutputStream fos = new FileOutputStream(new File(Utilities.getSystemTempDir())p_0" + System.currentTimeMillis() + ".pdf");
	    //	    try {
	    //		fos.write(resp.getBinaryData());
	    //		fos.close();
	    //	    } catch (IOException e) {
	    //		// TODO Auto-generated catch block
	    //		e.printStackTrace();
	    //	    }
	    return result;
	}
	StringBuilder sb = new StringBuilder();
	sb.append("numero,originaria,numeropresenze,comune_rilascio,siglaprovincia_rilascio,mercato,");
	if (addGiorno) {
	    sb.append("giorno,");
	}
	sb.append("titolare,titolare_cf,titolare_piva,protocollo_aut,data_protocollo_aut");
	sb.append("\r\n");
	List<AutorizzazioniGraduatoriaRestHelper> auts = g.getAutorizzazioni();
	for (AutorizzazioniGraduatoriaRestHelper agr : auts) {
	    sb.append("\"").append(formattaStringaCSV(agr.getNumero())).append("\",");
	    sb.append("\"").append(formattaStringaCSV(agr.getOriginaria())).append("\",");
	    sb.append(agr.getNumero_presenze()).append(",");
	    if (agr.getComune_rilascio() != null) {
		sb.append("\"").append(formattaStringaCSV(agr.getComune_rilascio().getNome())).append("\",");
		sb.append("\"").append(formattaStringaCSV(agr.getComune_rilascio().getSigla_provincia())).append("\",");
	    } else {
		sb.append("\"\",\"\",");
	    }
	    if (agr.getMercato() != null) {
		sb.append("\"").append(formattaStringaCSV(agr.getMercato().getNome())).append("\",");
	    } else {
		sb.append("\"\",");
	    }
	    if (addGiorno) {
		if (agr.getGiorno() != null) {
		    sb.append("\"").append(formattaStringaCSV(agr.getGiorno().getNome())).append("\",");
		} else {
		    sb.append("\"\",");
		}
	    }
	    if (agr.getTitolare() != null) { //titolare,titolare_cf,titolare_piva
		sb.append("\"").append(formattaStringaCSV(agr.getTitolare().getDenominazione())).append("\",");
		sb.append("\"").append(formattaStringaCSV(agr.getTitolare().getCodice_fiscale())).append("\",");
		sb.append("\"").append(formattaStringaCSV(agr.getTitolare().getPartita_iva())).append("\",");
	    } else {
		sb.append("\"\",\"\",\"\",");
	    }
	    sb.append("\"").append(formattaStringaCSV(agr.getProtocollo())).append("\",");
	    sb.append("\"").append(formattaStringaCSV(agr.getData_protocollo())).append("\"");
	    sb.append("\r\n");
	}
	InputStream stream = IOUtils.toInputStream(sb.toString());
	return stream;
    }

    private Object formattaStringaCSV(String valore) {

	return StringUtils.defaultString(valore).replace("\"", "\"\"");
    }

    private String buildDettaglioErroreRestBean(MercatiAppException e, int errorStatus, Serializer serializer) {

	DettaglioErroreRestBean d = new DettaglioErroreRestBean();
	d.setStatus(errorStatus);
	d.setCode(e.getErrore().getCodice());
	d.setTitle(e.getErrore().getDescrizione());
	ChiaveValoreBean<String, String> det = new ChiaveValoreBean<String, String>();
	det.setChiave(d.getCode());
	det.setValore(d.getTitle());
	d.getDetail().add(det);
	String str = (String) serializer.serialize(d);
	return str;
    }

    private List<CodiceDescrizioneBean> headerAggiuntivi(String requestId, String codiceServizio, String cf) throws MercatiAppException {

	log.debug("requestId: {}, codiceServizio: {}, cf: {}", new Object[] { requestId, codiceServizio, cf });
	List<CodiceDescrizioneBean> hdrs = new ArrayList<CodiceDescrizioneBean>(1);
	CodiceDescrizioneBean cdb = newNVBean(requestId, X_REQUEST_ID);
	hdrs.add(cdb);
	return hdrs;
    }
}
