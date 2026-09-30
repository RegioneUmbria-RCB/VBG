package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "importiContabili", "entiDestinatari" })
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountingData {

    @XmlElement(required = true, name = "ImportiContabili")
    private ImportiContabili importiContabili;
    @XmlElement(required = true, name = "EntiDestinatari")
    private EntiDestinatari entiDestinatari;

    public ImportiContabili getImportiContabili() {

	return importiContabili;
    }

    public void setImportiContabili(ImportiContabili importiContabili) {

	this.importiContabili = importiContabili;
    }

    public EntiDestinatari getEntiDestinatari() {

	return entiDestinatari;
    }

    public void setEntiDestinatari(EntiDestinatari entiDestinatari) {

	this.entiDestinatari = entiDestinatari;
    }
}
