package it.gruppoinit.pal.gp.core.features.rabbitmq.model.messaggi;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.features.rabbitmq.EnvVariables;

public class Header {

    public final static String VERSIONE_MESSAGGI_RABBIT = "1.0";
    private String versione;
    private String alias;
    private String software;

    public String getVersione() {

	return versione;
    }

    public void setVersione(String versione) {

	this.versione = versione;
    }

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static Header fromVariables(String alias, String software) {

	Header ret = new Header();
	ret.setAlias(alias);
	ret.setSoftware(software);
	ret.setVersione(VERSIONE_MESSAGGI_RABBIT);
	return ret;
    }
}
