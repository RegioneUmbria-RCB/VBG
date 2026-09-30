package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "rowNumber", //
	"esitiRate" //
})
public class EsitiRate {

    @XmlElement(name = "row_number")
    private Integer rowNumber;
    @XmlElement(name = "rate")
    private List<EsitiRata> esitiRate;

    public Integer getRowNumber() {

	return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {

	this.rowNumber = rowNumber;
    }

    public List<EsitiRata> getEsitiRate() {

	if (this.esitiRate == null) {
	    this.esitiRate = new ArrayList<EsitiRata>();
	}
	return esitiRate;
    }

    public void setEsitiRate(List<EsitiRata> esitiRate) {

	this.esitiRate = esitiRate;
    }
}
