package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "")
public class InnescoResponse {

    @JsonProperty("url")
    private String url;
    @JsonProperty("method")
    private MethodEnumBean method;
    @JsonProperty("body")
    private List<BodyItem> body;
    @JsonProperty("esito")
    private EsitoBean esito;

    public String getUrl() {

	return url;
    }

    public void setUrl(String url) {

	this.url = url;
    }

    public MethodEnumBean getMethod() {

	return method;
    }

    public void setMethod(MethodEnumBean method) {

	this.method = method;
    }

    public List<BodyItem> getBody() {

	return body;
    }

    public void setBody(List<BodyItem> body) {

	this.body = body;
    }

    public EsitoBean getEsito() {

	return esito;
    }

    public void setEsito(EsitoBean esito) {

	this.esito = esito;
    }

    public static InnescoResponse fromGenericException(Exception ex) {

	InnescoResponse response = new InnescoResponse();
	response.setUrl(null);
	response.setEsito(new EsitoBean());
	response.getEsito().setEsito(EsitoEnum.KO);
	response.getEsito().setExceptions(new ArrayList<String>());
	response.getEsito().getExceptions().add(ex.getMessage());
	return response;
    }
}
