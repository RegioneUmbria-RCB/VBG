package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DebtPositionDataDFV implements IDebtPositionDataUpdate{

	@XmlElement
	private String dataFineValidita; //YYYY-MM-DD

	public String getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(String dataFineValidita) {
		//if (dataFineValidita != null && !dataFineValidita.matches("\\d{4}-\\d{2}-\\d{2}")) {
	    //    throw new IllegalArgumentException("Formato YYYY-MM-DD richiesto");
	    //}
		this.dataFineValidita = dataFineValidita;
	}
	
}
