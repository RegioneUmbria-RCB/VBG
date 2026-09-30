package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "response")
public class VerificaStatoWSResponse {

    @JsonProperty("stato")
    private String stato;
    @JsonProperty("documenti")
    private VerificaStatoDocWsResponse documenti;

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public VerificaStatoDocWsResponse getDocumenti() {

	return documenti;
    }

    public void setDocumenti(VerificaStatoDocWsResponse documenti) {

	this.documenti = documenti;
    }
}
