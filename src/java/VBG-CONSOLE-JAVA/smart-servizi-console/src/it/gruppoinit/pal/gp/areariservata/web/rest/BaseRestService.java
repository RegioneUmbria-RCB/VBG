package it.gruppoinit.pal.gp.areariservata.web.rest;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.areariservata.web.util.DeployProperties;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

public class BaseRestService {

    @Autowired
    protected ExternalDBResolver externalDBResolver;
    @Autowired
    protected DeployProperties deployProperties;
    @Autowired
    protected SdeproxyService sdeproxyService;

    protected Sdeproxy setORMHelper(String idente, String software) {

	// Quello che mi è stato passato è l'idente, dall'id ente recupero Alias ente dalla tabelle sdeproy
	// per fare questa chiamata uso l'idcomune alias defautl
	Sdeproxy sdeproxy = sdeproxyService.findByIdEnte(idente);
	Properties connProps = externalDBResolver.getConnectionProperties(sdeproxy.getAliasEnte());
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(software);
	ORMHelper.setIdente(idente);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	ORMHelper.setIdcomunebase(sdeproxy.getIdcomunebase());
	return sdeproxy;
    }

    protected <T> T fromJson(InputStream iStream, Class<T> cls) throws JAXBException {

	JAXBContext jc = JAXBContext.newInstance(cls);
	Unmarshaller marshaller = jc.createUnmarshaller();
	marshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	// marshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
	return (T) marshaller.unmarshal(iStream);
    }

    protected String toJson(Object obj, boolean includiRoot) throws JAXBException {

	JAXBContext jc = JAXBContext.newInstance(obj.getClass());
	Marshaller marshaller = jc.createMarshaller();
	marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	if (!includiRoot) {
	    marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
	}
	StringWriter sw = new StringWriter();
	marshaller.marshal(obj, sw);
	return sw.toString();
    }

    protected Response rispostaWs(Object entity, Status returnStatus) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	return builder.build();
    }

    @OPTIONS
    @Path("/{var:.*}")
    public Response defaultOptions() {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	builder.header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
	builder.status(Status.OK);
	return builder.build();
    }

    protected Response rispostaWs(Object entity, Status returnStatus, List<CodiceDescrizioneBean> headerAggiuntivi) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	if (headerAggiuntivi != null) {
	    for (CodiceDescrizioneBean cdb : headerAggiuntivi) {
		builder.header(cdb.getCodice(), cdb.getDescrizione());
	    }
	}
	if (entity != null) {
	    builder.entity(entity);
	}
	builder.status(returnStatus);
	return builder.build();
    }

    protected Response rispostaWsWithProblemResult(Object entity, Status returnStatus) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	builder.header("Access-Control-Allow-Headers", "Content-Type");
	if (entity != null) {
	    builder.entity(entity);
	}
	if (returnStatus == Status.OK) {
	    // builder.sta
	}
	builder.status(returnStatus);
	return builder.build();
    }
}
