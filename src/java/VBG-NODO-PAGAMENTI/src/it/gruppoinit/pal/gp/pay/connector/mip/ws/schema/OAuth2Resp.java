package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OAuth2Resp", propOrder = { "accessToken", "scope", "tokenType", "expiresIn" })
public class OAuth2Resp {

    @XmlElement(name = "access_token")
    String accessToken;
    @XmlElement(name = "scope")
    String scope;
    @XmlElement(name = "token_type")
    String tokenType;
    @XmlElement(name = "expires_in")
    long expiresIn;

    public String getAccessToken() {

	return accessToken;
    }

    public void setAccessToken(String accessToken) {

	this.accessToken = accessToken;
    }

    public String getScope() {

	return scope;
    }

    public void setScope(String scope) {

	this.scope = scope;
    }

    public String getTokenType() {

	return tokenType;
    }

    public void setTokenType(String tokenType) {

	this.tokenType = tokenType;
    }

    public long getExpiresIn() {

	return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {

	this.expiresIn = expiresIn;
    }
}
