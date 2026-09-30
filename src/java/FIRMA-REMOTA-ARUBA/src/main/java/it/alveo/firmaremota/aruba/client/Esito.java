package it.alveo.firmaremota.aruba.client;

public class Esito {

    private EsitoEnum esito;
    private String status;
    private String returnCode;
    private String description;

    public static Esito fromKO(Exception ex) {

	Esito retVal = new Esito();
	retVal.setEsito(EsitoEnum.KO);
	retVal.setStatus("KO");
	retVal.setReturnCode("400");
	retVal.setDescription(ex.getMessage());
	return retVal;
    }

    public static Esito fromKO(String message) {

	Esito retVal = new Esito();
	retVal.setEsito(EsitoEnum.KO);
	retVal.setStatus("KO");
	retVal.setReturnCode("400");
	retVal.setDescription(message);
	return retVal;
    }

    public static Esito fromOK() {

	Esito retVal = new Esito();
	retVal.setEsito(EsitoEnum.OK);
	return retVal;
    }

    public EsitoEnum getEsito() {

	return esito;
    }

    public void setEsito(EsitoEnum esito) {

	this.esito = esito;
    }

    public String getStatus() {

	return status;
    }

    public void setStatus(String status) {

	this.status = status;
    }

    public String getReturnCode() {

	return returnCode;
    }

    public void setReturnCode(String returnCode) {

	this.returnCode = returnCode;
    }

    public String getDescription() {

	return description;
    }

    public void setDescription(String description) {

	this.description = description;
    }
}
