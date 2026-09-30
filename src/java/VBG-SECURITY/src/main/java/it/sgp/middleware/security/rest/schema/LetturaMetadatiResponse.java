package it.sgp.middleware.security.rest.schema;

import java.util.List;

public class LetturaMetadatiResponse {

	List<Metadati> metadati;

	public List<Metadati> getMetadati() {
		return metadati;
	}

	public void setMetadati(List<Metadati> metadati) {
		this.metadati = metadati;
	}
	
}
