package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.wsanagrafe2.ws.WsAnagrafe2CxfClient;
import it.gruppoinit.wsanagrafe2.ws.WsAnagrafe2Soap;

public class Anagrafe2WsClient extends BaseWsClient {

    private long timeout = 30000;

    public Anagrafe2WsClient() {

	super();
    }

    public long getTimeout() {

	return timeout;
    }

    public void setTimeout(long timeout) {

	this.timeout = timeout;
    }

    public WsAnagrafe2Soap getAnagrafe2WsPort(String anagrafeWsUrl) throws Exception {

	WsAnagrafe2CxfClient client = new WsAnagrafe2CxfClient();
	client.setTimeout(this.timeout);
	WsAnagrafe2Soap port = client.getAnagrafe2WsPort(anagrafeWsUrl);
	return port;
    }
}
