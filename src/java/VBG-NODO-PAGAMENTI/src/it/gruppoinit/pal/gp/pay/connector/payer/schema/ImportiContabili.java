package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "importiContabili" })
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportiContabili {

    @XmlElement(required = true, name = "ImportoContabile")
    private List<ImportoContabile> importiContabili = new ArrayList<ImportoContabile>();

    public List<ImportoContabile> getImportiContabili() {

	return importiContabili;
    }
}
