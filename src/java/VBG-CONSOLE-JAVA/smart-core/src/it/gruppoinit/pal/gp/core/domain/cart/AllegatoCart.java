package it.gruppoinit.pal.gp.core.domain.cart;

import java.io.File;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllegatoCart", propOrder = { "descrizione", "riferimento", "fileName", "idSemantico", "rowIndex" })
@XmlSeeAlso({ AllegatoDaFirmare.class })
public class AllegatoCart {

    @XmlElement(name = "descrizione", required = false)
    private String descrizione;
    @XmlElement(name = "riferimento", required = true)
    private Integer riferimento;
    @XmlElement(name = "fileName", required = true)
    private String fileName;
    @XmlElement(name = "idSemantico", required = false)
    private String idSemantico;
    @XmlElement(name = "rowIndex", required = false)
    private Integer rowIndex;
    @XmlTransient
    private File attachment;
    @XmlTransient
    private String tipoFile;

    public AllegatoCart() {

    }

    public AllegatoCart(Integer codiceOggetto) {

	this.riferimento = codiceOggetto;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Integer getRiferimento() {

	return riferimento;
    }

    public void setRiferimento(Integer riferimento) {

	this.riferimento = riferimento;
    }

    public String getFileName() {

	return fileName;
    }

    public void setFileName(String fileName) {

	this.fileName = fileName;
    }

    public String getIdSemantico() {

	return idSemantico;
    }

    public void setIdSemantico(String idSemantico) {

	this.idSemantico = idSemantico;
    }

    public Integer getRowIndex() {

	return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {

	this.rowIndex = rowIndex;
    }

    public File getAttachment() {

	return attachment;
    }

    public void setAttachment(File attachment) {

	this.attachment = attachment;
    }

    public String getTipoFile() {

	return tipoFile;
    }

    public void setTipoFile(String tipoFile) {

	this.tipoFile = tipoFile;
    }
}