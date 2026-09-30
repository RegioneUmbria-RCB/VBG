package it.gruppoinit.pal.gp.core.utils;

public class ParamsSFTP {

    private String username;
    private String password;
    private String remoteHost;
    private int remotePort;
    private boolean gestisciConnessione;

    private ParamsSFTP() {

	super();
    }

    public ParamsSFTP(String username, String password, String remoteHost, int remotePort, boolean gestisciConnessione) {

	this();
	this.username = username;
	this.password = password;
	this.remoteHost = remoteHost;
	this.remotePort = remotePort;
	this.gestisciConnessione = gestisciConnessione;
    }

    public String getUsername() {

	return username;
    }

    public String getPassword() {

	return password;
    }

    public int getRemotePort() {

	return remotePort;
    }

    public String getRemoteHost() {

	return remoteHost;
    }

    public boolean isGestisciConnessione() {

	return gestisciConnessione;
    }
}
