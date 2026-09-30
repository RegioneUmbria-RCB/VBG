package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.dp.model.DpInviaEsitoPagamento;
import it.gruppoinit.pal.gp.pay.connector.easybridge.ws.dp.model.DpInviaEsitoPagamentoResponse;

@WebService(targetNamespace = "http://easybridge.eu/bridge/", name = "esitiPagamento")
@XmlSeeAlso({ ObjectFactory.class })
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface EBEsitiPagamentoInterface {

    @WebMethod(operationName = "dpInviaEsitoPagamento", action = "http://easybridge.eu/bridge/dpInviaEsitoPagamento")
    @WebResult(name = "dpInviaEsitoPagamentoResponse", targetNamespace = "http://easybridge.eu/bridge/", partName = "parameters")
    public DpInviaEsitoPagamentoResponse dpInviaEsitoPagamento(
	    @WebParam(partName = "parameters", name = "dpInviaEsitoPagamento", targetNamespace = "http://easybridge.eu/bridge/") DpInviaEsitoPagamento parameters);
}
