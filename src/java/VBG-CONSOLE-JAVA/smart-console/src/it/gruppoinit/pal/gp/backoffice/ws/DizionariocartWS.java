package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.definitions.dizionariocart.Dizionario;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.dizionariocart.UploadDizionarioRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.dizionariocart.UploadDizionarioResponse;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import java.math.BigInteger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

// @WebService(targetNamespace = "http://gruppoinit.it/cart/dizionario", name = "Dizionario")
@WebService(serviceName = "DizionarioService", name = "DizionarioService", portName = "UploadDizionarioSoap11", targetNamespace = "http://gruppoinit.it/cart/dizionario", endpointInterface = "it.gruppoinit.pal.gp.backoffice.definitions.dizionariocart.Dizionario")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class DizionariocartWS extends BaseWS implements Dizionario {

    @Autowired
    private ExternalDBResolver externalDBResolver;

    @WebResult(name = "UploadDizionarioResponse", targetNamespace = "http://gruppoinit.it/cart/dizionario", partName = "UploadDizionarioResponse")
    @WebMethod(operationName = "UploadDizionario", action = "uploadDizionario")
    public UploadDizionarioResponse uploadDizionario(
	    @WebParam(partName = "UploadDizionarioRequest", name = "UploadDizionarioRequest", targetNamespace = "http://gruppoinit.it/cart/dizionario") UploadDizionarioRequest request) {

	//	validateRequest(request);
	//	String tokenApplicativo = externalDBResolver.getTokenDefaultAlias();
	//	ORMHelper.setToken(tokenApplicativo);
	//	Properties p = externalDBResolver.checkToken(tokenApplicativo);
	//	ORMHelper.setIdcomuneAlias(p.getProperty(WebConstants.IDCOMUNE_ALIAS));
	//	ORMHelper.setIdcomune(p.getProperty(WebConstants.IDCOMUNE));
	//	login("BACKOFFICE");
	//	List<DizionariCart> l = dizionaricartService.findAll(null, null);
	//	DizionariCart entity = new DizionariCart();
	//	boolean isInsert = true;
	//	if (l.size() > 0) {
	//	    entity = l.get(0);
	//	    isInsert = false;
	//	}
	//	entity.setNomefile(request.getFileName());
	//	entity.setDataPubblicazione(request.getDataPubblicazione().toGregorianCalendar().getTime());
	//	entity.setOggetto(Utilities.dataHandlerToBytes(request.getBinaryData()));
	//	// inserisce o scrive il file su tabella
	//	if (isInsert) {
	//	    dizionaricartService.insert(entity);
	//	} else {
	//	    dizionaricartService.update(entity);
	//	}
	//	aggiornamentiDizionarioService.deleteAll();
	//	ORMHelper.setToken(null);
	//	ORMHelper.setIdcomuneAlias(null);
	UploadDizionarioResponse s = new UploadDizionarioResponse();
	s.setCode(BigInteger.ZERO);
	return s;
    }

    private void validateRequest(UploadDizionarioRequest request) {

	if (request.getDataPubblicazione() == null) {
	    throw new RuntimeException("Il parametro data è nullo");
	}
	if (StringUtils.isBlank(request.getFileName())) {
	    throw new RuntimeException("Il parametro filename è nullo");
	}
    }
}
