package it.gruppoinit.pal.gp.core.features.rest;

import java.util.List;

import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBException;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;

import it.gruppoinit.pal.gp.core.utils.Utilities;

public class BaseRestService<T> {

    @OPTIONS
    @Path("/{var:.*}")
    public Response defaultOptions() {

	ResponseBuilder builder = this.getBuilder(true);
	builder.header("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
	builder.status(Status.OK);
	return builder.build();
    }

    public Response listaJsonSenzaRoot(List<T> list, Class<T> t, Status returnStatus) throws JAXBException {

	String responseJson = this.toJson(list, t, false);
	return this.rispostaJson(responseJson, returnStatus);
    }

    public Response rispostaJsonSenzaRoot(Object entity, Status returnStatus) throws JAXBException {

	return this.rispostaJson(entity, false, returnStatus);
    }

    public Response rispostaJson(Object entity, boolean includiRoot, Status returnStatus) throws JAXBException {

	String responseJson = this.toJson(entity, includiRoot);
	return this.rispostaJson(responseJson, returnStatus);
    }

    private Response rispostaJson(String jsonResponse, Status returnStatus) {

	ResponseBuilder builder = this.getBuilder(false);
	builder.entity(jsonResponse);
	builder.status(returnStatus);
	return builder.build();
    }

    private String toJson(Object obj, boolean includiRoot) throws JAXBException {

	return Utilities.marshalJsonObject(obj, obj.getClass(), includiRoot, Utilities.JAXB_ENCODING_UTF_8);
    }

    private String toJson(List<T> list, Class<T> t, boolean includiRoot) throws JAXBException {

	return Utilities.marshalJsonObject(list, t, includiRoot, Utilities.JAXB_ENCODING_UTF_8);
    }

    private ResponseBuilder getBuilder(boolean allowAuthorization) {

	ResponseBuilder builder = new ResponseBuilderImpl();
	builder.header("Access-Control-Allow-Origin", "*");
	if (allowAuthorization) {
	    builder.header("Access-Control-Allow-Headers", "Content-Type, authorization");
	} else {
	    builder.header("Access-Control-Allow-Headers", "Content-Type");
	}
	return builder;
    }
}
