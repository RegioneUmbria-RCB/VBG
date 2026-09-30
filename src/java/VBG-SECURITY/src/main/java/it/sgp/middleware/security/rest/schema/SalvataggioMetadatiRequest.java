package it.sgp.middleware.security.rest.schema;

import java.util.List;

public class SalvataggioMetadatiRequest {

	private String token;
	private List<Metadati> metadati;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public List<Metadati> getMetadati() {
		return metadati;
	}
	public void setMetadati(List<Metadati> metadati) {
		this.metadati = metadati;
	}
	
	
}
