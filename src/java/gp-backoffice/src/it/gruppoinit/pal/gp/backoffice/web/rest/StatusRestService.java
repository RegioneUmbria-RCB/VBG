package it.gruppoinit.pal.gp.backoffice.web.rest;

import java.sql.SQLException;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.impl.ResponseBuilderImpl;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.PooledDataSource;

@Path("/status/")
public class StatusRestService {

    private static final Logger log = LoggerFactory.getLogger(StatusRestService.class);
    private String statusString = "{ \"status\": \"STATUS_INDICATOR\", \"components\": { DB_INDICATOR } }";
    private String statusDBString = "\"db\": { \"status\": \"STATUS_INDICATOR\" }";

    public enum STATUS {
	UP,
	DOWN
    }

    @GET
    @Path("/health")
    @Descriptions({ @Description(value = "Recupera lo stato del servizio", target = DocTarget.METHOD) })
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response health() {

	STATUS genStatus = STATUS.UP;
	String result = null;
	String resultDbString = null;
	STATUS dbStatus = STATUS.UP;
	try {
	    dbStatus = dbStatus();
	} catch (Exception ex) {
	    dbStatus = STATUS.DOWN;
	    genStatus = STATUS.DOWN;
	    log.error("Errore nella verifica dello stato", ex);
	}
	resultDbString = statusDBString.replace("STATUS_INDICATOR", dbStatus.name());
	result = statusString.replace("DB_INDICATOR", resultDbString);
	result = result.replace("STATUS_INDICATOR", genStatus.name());
	return rispostaWs(result, Status.OK);
    }

    private STATUS dbStatus() {

	@SuppressWarnings("unchecked")
	Set<PooledDataSource> pooledDSList = C3P0Registry.getPooledDataSources();
	for (PooledDataSource ds : pooledDSList) {
	    try {
		int numConnectionsAllUsers = ds.getNumConnectionsDefaultUser();
		int numBusyConnectionsAllUsers = ds.getNumBusyConnectionsDefaultUser();
		// int numIdleConnectionsAllUsers = ds.getNumIdleConnectionsAllUsers()
		if (numConnectionsAllUsers == numBusyConnectionsAllUsers) {
		    throw new RuntimeException(
			    "numConnectionsAllUsers(" + numConnectionsAllUsers + ")==numBusyConnectionsAllUsers(" + numConnectionsAllUsers + ")");
		}
	    } catch (SQLException e) {
		log.error("Errore nella verifica dello stato database", e);
		return STATUS.DOWN;
	    }
	}
	return STATUS.UP;
    }

    private Response rispostaWs(Object entity, Status returnStatus) {

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
