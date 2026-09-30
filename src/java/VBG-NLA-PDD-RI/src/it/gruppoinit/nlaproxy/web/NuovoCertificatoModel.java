package it.gruppoinit.nlaproxy.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class NuovoCertificatoModel {

    private String codiceCatastale;
    private MultipartFile certificato;
    private String password;
    private String alias;

    public String getCodiceCatastale() {

	return codiceCatastale;
    }

    public void setCodiceCatastale(String codiceCatastale) {

	this.codiceCatastale = codiceCatastale;
    }

    public MultipartFile getCertificato() {

	return certificato;
    }

    public void setCertificato(MultipartFile certificato) {

	this.certificato = certificato;
    }

    public String getPassword() {

	return password;
    }

    public void setPassword(String password) {

	this.password = password;
    }

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public void valida() throws IllegalArgumentException {

	if (StringUtils.isBlank(this.codiceCatastale)) {
	    throw new RuntimeException("Il campo codice catastale è obbligatorio");
	}
	if (certificato == null || certificato.isEmpty()) {
	    throw new RuntimeException("Il campo certificato è obbligatorio");
	}
	if (password == null || password.isEmpty()) {
	    throw new RuntimeException("Il campo password è obbligatorio");
	}
	if (alias == null || alias.isEmpty()) {
	    throw new RuntimeException("Il campo alias è obbligatorio");
	}
    }
}
