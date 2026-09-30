package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImportoContabileWrapper", propOrder = { "importocontabile" })
public class ImportoContabileWrapper {

    @XmlElement(name = "ImportoContabile")
    ImportoContabile importocontabile;

    public ImportoContabile getImportocontabile() {

	return importocontabile;
    }

    public void setImportocontabile(ImportoContabile importocontabile) {

	this.importocontabile = importocontabile;
    }
}
