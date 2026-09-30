package it.gruppoinit.pdd.ri.decorators;

import it.gruppoinit.pdd.ri.interceptors.InsielMsgInterceptorIn;
import it.gruppoinit.pdd.ri.interceptors.InsielMsgInterceptorOut;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;

public class InsielDecorator implements RIDecorator {

    String idcomunealias = null;

    public InsielDecorator(String idcomunealias) {

	this.idcomunealias = idcomunealias;
    }

    @Override
    public void decore(Object port, String metodo) {

	Client proxy = ClientProxy.getClient(port);
	Endpoint cxfEndpoint = proxy.getEndpoint();
	cxfEndpoint.getInInterceptors().add(new InsielMsgInterceptorIn(metodo, idcomunealias));
	cxfEndpoint.getOutInterceptors().add(new InsielMsgInterceptorOut(metodo, idcomunealias));
    }
}
