package it.sgp.middleware.security.domain;

public class CheckTokenResult {

    private boolean valid;
    private ComunisecuritySession session;

    public boolean isValid() {

	return valid;
    }

    public void setValid(boolean valid) {

	this.valid = valid;
    }

    public void setSession(ComunisecuritySession session) {

	this.session = session;
    }

    public ComunisecuritySession getSession() {

	return session;
    }
}
