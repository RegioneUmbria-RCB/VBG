package it.alveo.ricalcoloaree.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ricalcoloaree")
public class RicalcoloAreeAppPropConfig {

    private String tokenuser;
    private String tokenpwd;
    private String tokenurl;

    public String getTokenuser() {

	return tokenuser;
    }

    public void setTokenuser(String tokenuser) {

	this.tokenuser = tokenuser;
    }

    public String getTokenpwd() {

	return tokenpwd;
    }

    public void setTokenpwd(String tokenpwd) {

	this.tokenpwd = tokenpwd;
    }

    public String getTokenurl() {

	return tokenurl;
    }

    public void setTokenurl(String tokenurl) {

	this.tokenurl = tokenurl;
    }

    @Override
    public String toString() {

	return "tokenUser: " + getTokenuser() + ", tokenurl" + getTokenurl() + ", (tokenpwd) ?" + (getTokenpwd() != null);
    }
}
