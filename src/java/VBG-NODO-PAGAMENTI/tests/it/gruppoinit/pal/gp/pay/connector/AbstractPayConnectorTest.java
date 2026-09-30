package it.gruppoinit.pal.gp.pay.connector;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.transport.http.auth.HttpAuthHeader;
import org.junit.Assert;
import org.junit.Test;

public class AbstractPayConnectorTest {

    @Test(expected = IllegalArgumentException.class)
    public void getBasicAuthorizationLanciaErroreInCasoDiCredenzialiNulla() {

	AbstractPayConnectorAdapter c = new AbstractPayConnectorAdapter();
	c.getBasicAuthorization(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBasicAuthorizationLanciaErroreInCasoDiCredenzialiStringaVuota() {

	AbstractPayConnectorAdapter c = new AbstractPayConnectorAdapter();
	c.getBasicAuthorization("", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBasicAuthorizationLanciaErroreInCasoDiCredenzialiStringaConSpazi() {

	AbstractPayConnectorAdapter c = new AbstractPayConnectorAdapter();
	c.getBasicAuthorization(" ", " ");
    }

    @Test()
    public void getBasicAuthorizationTornaBasicAuthorization() {

	AbstractPayConnectorAdapter c = new AbstractPayConnectorAdapter();
	String userName = "User";
	String password = "Password";
	AuthorizationPolicy r = c.getBasicAuthorization(userName, password);
	Assert.assertTrue("Torna Authrization Type " + HttpAuthHeader.AUTH_TYPE_BASIC,
		r.getAuthorizationType().equals(HttpAuthHeader.AUTH_TYPE_BASIC));
	Assert.assertTrue("Torna username " + userName, r.getUserName().equals(userName));
	Assert.assertTrue("Torna password " + password, r.getPassword().equals(password));
    }
}
