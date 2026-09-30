package it.gruppoinit.pal.gp.core.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Bandi;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ApiService;
import it.gruppoinit.pal.gp.core.service.helper.RiferimentiPraticaSTCRestBean;
import net.sf.sojo.core.filter.ClassPropertyFilter;
import net.sf.sojo.core.filter.ClassPropertyFilterHandler;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

@Service
public class ApiServiceImpl extends BaseServiceImpl<String, Bandi> implements ApiService {

    private static final String EXCEPTION_MESSAGE_FOR_THE_FORM_INPUT = "Tutti i campi sono obbligatori.";
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    private String getAPIWsUrl() {

	String url = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_API_SERVICE,
		WebConstants.VERTICALIZZAZIONE_API_SERVICE_URL);
	if (StringUtils.isBlank(url)) {
	    // la verticalizzazione sovrascrive la configurazione del security
	    url = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_APIBACKEND);
	}
	if (StringUtils.isBlank(url)) {
	    throw new InvalidConfigurationException("Non è stata trovata la configurazione nell'applicativo della security. Il parametro " +
						    WebConstants.VERTICALIZZAZIONE_API_SERVICE_URL + " della regola " +
						    WebConstants.VERTICALIZZAZIONE_API_SERVICE + " non e' stato impostato correttamente.");
	}
	return url;
    }

    @Override
    public RiferimentiPraticaSTCRestBean creaPraticaDaFileZip(String numeroProtocollo, String dataProtocollo, MultipartFile zipFile)
	    throws Exception {

	String url = getAPIWsUrl() + "/services/api-rest/pratiche/crea-pratica-da-zip";
	RiferimentiPraticaSTCRestBean o = null;
	if (!zipFile.isEmpty() && numeroProtocollo != null && dataProtocollo != null) {
	    /// config path param
	    WebClient client = WebClient.create(url).path("{numero_protocollo}/{data_protocollo}", numeroProtocollo, dataProtocollo);
	    // connection timeout
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	    client.type("multipart/mixed").accept(MediaType.APPLICATION_JSON);
	    ContentDisposition cdZip = new ContentDisposition("form-data;name=\"" + WebConstants.API_SERVICE_ALLEGATI_ZIP_FILE + "\";filename=\"" +
							      WebConstants.API_SERVICE_ALLEGATI_ZIP_FILE + "\"");
	    Attachment att = new Attachment(WebConstants.API_SERVICE_ALLEGATI_ZIP_FILE, zipFile.getInputStream(), cdZip);
	    List<Attachment> atts = new ArrayList<Attachment>();
	    atts.add(att);
	    MultipartBody mpb = new MultipartBody(atts);
	    Response response = client.post(mpb);
	    InputStream is = ((InputStream) response.getEntity());
	    String s = IOUtils.toString(is, "UTF-8");
	    Serializer srlzr = getSerializer();
	    o = (RiferimentiPraticaSTCRestBean) srlzr.deserialize(s, RiferimentiPraticaSTCRestBean.class);
	    return o;
	} else {
	    throw new IllegalArgumentException(EXCEPTION_MESSAGE_FOR_THE_FORM_INPUT);
	}
    }

    protected Serializer getSerializer() {

	Serializer serializer = new JsonSerializer();
	serializer.setClassPropertyFilterHandler(new ClassPropertyFilterHandler() {

	    @Override
	    public ClassPropertyFilter getClassPropertyFilterByClass(Class arg0) {

		ClassPropertyFilter cpf = new ClassPropertyFilter(arg0);
		cpf.setSupport4AddClassProperty(true);
		cpf.addProperties(new String[] { "class", "~unique-id~" });
		return cpf;
	    }
	});
	return serializer;
    }

    @Override
    public void insert(String entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(String entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(String entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<String> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public String findById(Bandi id) {

	throw new NotImplementedException();
    }

    @Override
    protected Class<String> getEntityClass() {

	throw new NotImplementedException();
    }
}
