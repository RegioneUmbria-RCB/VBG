package it.gruppoinit.pal.gp.backoffice.web.rest.interceptors;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class InterceptorOut extends AbstractPhaseInterceptor<Message> {

    public InterceptorOut() {

	super(Phase.POST_PROTOCOL_ENDING);
    }

    public InterceptorOut(String phase) {

	super(phase);
    }

    @Override
    public void handleFault(Message message) {

    }

    @Override
    public void handleMessage(Message message) throws Fault {

	// non posso fare questo che da errore
	//	HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
	//	request.getSession().invalidate();
	ORMHelper.destroyORMHelper();
    }
}
