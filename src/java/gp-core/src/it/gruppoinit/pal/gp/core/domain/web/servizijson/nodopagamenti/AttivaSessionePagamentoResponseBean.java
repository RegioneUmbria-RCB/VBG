package it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "sessionepagamento")
public class AttivaSessionePagamentoResponseBean {

    @XmlElement(name = "esito")
    private Boolean esito;
    @XmlElement(name = "descEsito")
    private String descEsito;
    @XmlElement(name = "idSessione")
    private String idSessione;
    @XmlElement(name = "securityDigest")
    private String securityDigest;
    @XmlElement(name = "payUrl")
    private String payUrl;
    @XmlElement(name = "httpMethod")
    private String httpMethod;
    @XmlElement(name = "formParams")
    private List<AttivaSessionePagamentoFormParamsResponseBean> formParams = new ArrayList<AttivaSessionePagamentoFormParamsResponseBean>();

    @XmlTransient
    public Boolean getEsito() {

	return esito;
    }

    public void setEsito(Boolean esito) {

	this.esito = esito;
    }

    @XmlTransient
    public String getDescEsito() {

	return descEsito;
    }

    public void setDescEsito(String descEsito) {

	this.descEsito = descEsito;
    }

    @XmlTransient
    public String getIdSessione() {

	return idSessione;
    }

    public void setIdSessione(String idSessione) {

	this.idSessione = idSessione;
    }

    @XmlTransient
    public String getSecurityDigest() {

	return securityDigest;
    }

    public void setSecurityDigest(String securityDigest) {

	this.securityDigest = securityDigest;
    }

    @XmlTransient
    public String getPayUrl() {

	return payUrl;
    }

    public void setPayUrl(String payUrl) {

	this.payUrl = payUrl;
    }

    @XmlTransient
    public List<AttivaSessionePagamentoFormParamsResponseBean> getFormParams() {

	if (this.formParams == null) {
	    this.formParams = new ArrayList<AttivaSessionePagamentoFormParamsResponseBean>();
	}
	return formParams;
    }

    public void setFormParams(List<AttivaSessionePagamentoFormParamsResponseBean> formParams) {

	this.formParams = formParams;
    }

    @XmlTransient
    public String getHttpMethod() {

	return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {

	this.httpMethod = httpMethod;
    }
}
