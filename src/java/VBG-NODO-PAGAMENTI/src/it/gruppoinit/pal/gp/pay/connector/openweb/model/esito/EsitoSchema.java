package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "scheme", //
	"user", //
	"password", //
	"host", //
	"port", //
	"path", //
	"query", //
	"fragment", //
})
public class EsitoSchema {

    @XmlElement
    private String scheme;
    @XmlElement
    private String user;
    @XmlElement
    private String password;
    @XmlElement
    private String host;
    @XmlElement
    private Integer port;
    @XmlElement
    private String path;
    @XmlElement
    private String query;
    @XmlElement
    private String fragment;

    public String getScheme() {

	return scheme;
    }

    public void setScheme(String scheme) {

	this.scheme = scheme;
    }

    public String getUser() {

	return user;
    }

    public void setUser(String user) {

	this.user = user;
    }

    public String getPassword() {

	return password;
    }

    public void setPassword(String password) {

	this.password = password;
    }

    public String getHost() {

	return host;
    }

    public void setHost(String host) {

	this.host = host;
    }

    public Integer getPort() {

	return port;
    }

    public void setPort(Integer port) {

	this.port = port;
    }

    public String getPath() {

	return path;
    }

    public void setPath(String path) {

	this.path = path;
    }

    public String getQuery() {

	return query;
    }

    public void setQuery(String query) {

	this.query = query;
    }

    public String getFragment() {

	return fragment;
    }

    public void setFragment(String fragment) {

	this.fragment = fragment;
    }
}
