package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AttoNumeratoType {

    public String numeroAtto;
    public Date dataAtto;

    public String getNumeroAtto() {

	return numeroAtto;
    }

    public void setNumeroAtto(String numeroAtto) {

	this.numeroAtto = numeroAtto;
    }

    public Date getDataAtto() {

	return dataAtto;
    }

    public void setDataAtto(Date dataAtto) {

	this.dataAtto = dataAtto;
    }

    public Integer getAnnoAtto() {

	if (this.getDataAtto() == null) {
	    return null;
	}
	Calendar calendar = new GregorianCalendar();
	calendar.setTime(this.getDataAtto());
	return calendar.get(Calendar.YEAR);
    }
}
