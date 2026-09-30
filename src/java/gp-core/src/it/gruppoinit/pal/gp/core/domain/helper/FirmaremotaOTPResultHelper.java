package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Oggetti;

import javax.activation.DataHandler;

public class FirmaremotaOTPResultHelper {

    public enum ESITO {
	OK, KO
    };

    private DataHandler stream;
    private String returnCode;
    private String status;
    private String description;
    private ESITO esito;
    private Oggetti oggettoFirmato;
    private Integer codiceOggetto;
    private Integer codiceDocumentiDaFirmare;

    public DataHandler getStream() {

	return stream;
    }

    public void setStream(DataHandler stream) {

	this.stream = stream;
    }

    public String getReturnCode() {

	return returnCode;
    }

    public void setReturnCode(String returnCode) {

	this.returnCode = returnCode;
    }

    public String getStatus() {

	return status;
    }

    public void setStatus(String status) {

	this.status = status;
    }

    public String getDescription() {

	return description;
    }

    public void setDescription(String description) {

	this.description = description;
    }

    public ESITO getEsito() {

	return esito;
    }

    public void setEsito(ESITO esito) {

	this.esito = esito;
    }

    public Oggetti getOggettoFirmato() {

	return oggettoFirmato;
    }

    public void setOggettoFirmato(Oggetti oggettoFirmato) {

	this.oggettoFirmato = oggettoFirmato;
    }

    public Integer getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(Integer codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }

    public Integer getCodiceDocumentiDaFirmare() {

	return codiceDocumentiDaFirmare;
    }

    public void setCodiceDocumentiDaFirmare(Integer codiceDocumentiDaFirmare) {

	this.codiceDocumentiDaFirmare = codiceDocumentiDaFirmare;
    }
}
