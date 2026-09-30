/*
 * MIP API - WS Conversion Libreria API del Modulo Incassi e Pagamenti del Comune di Genova per operazioni su Avvisi di
 * Pagamento
 *
 * OpenAPI spec version: 0.0.94 Contact: helpservizionline@comune.genova.it
 *
 */
package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import java.util.Arrays;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * FileAllegato
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FileAllegato", propOrder = { "nomeFile", "file", "tipoFile", "dimensioneFile" })
public class FileAllegato {

    @XmlElement(name = "nomeFile")
    private String nomeFile = null;
    @XmlElement(name = "file")
    private byte[] file = null;
    @XmlElement(name = "tipoFile")
    private MIMEtype tipoFile = null;
    @XmlElement(name = "dimensioneFile")
    private Long dimensioneFile = null;

    public FileAllegato nomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
	return this;
    }

    /**
     * Get nomeFile
     * 
     * @return nomeFile
     **/
    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public FileAllegato file(byte[] file) {

	this.file = file;
	return this;
    }

    /**
     * File codificato in Base64
     * 
     * @return file
     **/
    public byte[] getFile() {

	return file;
    }

    public void setFile(byte[] file) {

	this.file = file;
    }

    public FileAllegato tipoFile(MIMEtype tipoFile) {

	this.tipoFile = tipoFile;
	return this;
    }

    /**
     * Get tipoFile
     * 
     * @return tipoFile
     **/
    public MIMEtype getTipoFile() {

	return tipoFile;
    }

    public void setTipoFile(MIMEtype tipoFile) {

	this.tipoFile = tipoFile;
    }

    public FileAllegato dimensioneFile(Long dimensioneFile) {

	this.dimensioneFile = dimensioneFile;
	return this;
    }

    /**
     * Get dimensioneFile
     * 
     * @return dimensioneFile
     **/
    public Long getDimensioneFile() {

	return dimensioneFile;
    }

    public void setDimensioneFile(Long dimensioneFile) {

	this.dimensioneFile = dimensioneFile;
    }

    @Override
    public boolean equals(java.lang.Object o) {

	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}
	FileAllegato fileAllegato = (FileAllegato) o;
	return Objects.equals(this.nomeFile, fileAllegato.nomeFile) && Arrays.equals(this.file, fileAllegato.file)
		&& Objects.equals(this.tipoFile, fileAllegato.tipoFile) && Objects.equals(this.dimensioneFile, fileAllegato.dimensioneFile);
    }

    @Override
    public int hashCode() {

	return Objects.hash(nomeFile, Arrays.hashCode(file), tipoFile, dimensioneFile);
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("class FileAllegato {\n");
	sb.append("    nomeFile: ").append(toIndentedString(nomeFile)).append("\n");
	sb.append("    file: ").append(toIndentedString(file)).append("\n");
	sb.append("    tipoFile: ").append(toIndentedString(tipoFile)).append("\n");
	sb.append("    dimensioneFile: ").append(toIndentedString(dimensioneFile)).append("\n");
	sb.append("}");
	return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {

	if (o == null) {
	    return "null";
	}
	return o.toString().replace("\n", "\n    ");
    }
}
