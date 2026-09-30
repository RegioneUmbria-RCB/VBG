package it.gruppoinit.pal.gp.pay.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;
import it.gruppoinit.pal.gp.core.domain.BaseDomainObject;
import it.gruppoinit.pal.gp.core.domain.IdAwareDomainObject;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.service.impl.PayDocumentiServiceImpl;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * PayDocumenti 
 * @author lion
 */
@Entity
@Table(name = "PAY_DOCUMENTI")
public class PayDocumenti extends BaseDomainObject implements Serializable, IdAwareDomainObject<PkId> {

    private static final long serialVersionUID = 2822747568398325402L;
    private PkId id;
    private String nomeFileSystem;
    private String nomeDocumento;
    private String percorso;
    private byte[] datiFile;
    private Integer dimensione;
    @Transient
    private DataHandler dh;

    public PayDocumenti() {

	this.id = new PkId();
    }

    public PayDocumenti(PkId id) {

	this.id = id;
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = TableGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = TableGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = TableGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = TableGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = TableGenerator.SEGMENT_VALUE_PARAM, value = "PAY_DOCUMENTI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Length(max = 256)
    @Column(name = "NOME_FILESYSTEM", length = 256)
    public String getNomeFileSystem() {

	return this.nomeFileSystem;
    }

    public void setNomeFileSystem(String nomeFileSystem) {

	this.nomeFileSystem = nomeFileSystem;
	this.setDataHandlerInternal();
    }

    @NotEmpty
    @Length(max = 256)
    @Column(name = "NOME_DOCUMENTO", length = 256)
    public String getNomeDocumento() {

	return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {

	this.nomeDocumento = nomeDocumento;
    }

    @Lob
    @Column(name = "DATI_FILE", columnDefinition = "BLOB")
    @Basic(fetch = FetchType.LAZY)
    public byte[] getDatiFileBlob() {

	return this.datiFile;
    }

    public void setDatiFileBlob(byte[] datiFile) {

	this.datiFile = datiFile;
    }

    @Transient
    public void setFileDataHandler(File file) {

	if (file != null) {
	    DataSource ds = new FileDataSource(file);
	    this.dh = new DataHandler(ds);
	} else {
	    this.dh = null;
	}
    }

    /**
     * per accedere ai dati binari del documento come byte[] deve essere invocato questo metodo che restituisce comunque i
     * i dati indipendentemente da dove e come siano memorizzati 
     * @return
     */
    @Transient
    public byte[] getBytes() {

	if (this.dh != null) {
	    return IOUtils.dataHandlerToBytes(this.dh);
	} else {
	    if (PayConfigurationHelper.isDocumentiSuFilesystem()) {
		return new byte[0];
	    } else {
		return this.getDatiFileBlob();
	    }
	}
    }

    public void setBytes(byte[] data) {

	this.dh = IOUtils.bytesToDataHandler(data);
    }

    /**
     * per accedere ai dati binari del documento come {@link DataHandler} deve essere invocato questo metodo che restituisce comunque i
     * i dati indipendentemente da dove e come siano memorizzati 
     * @return
     */
    @Transient
    public DataHandler getDataHandler() {

	if (PayConfigurationHelper.isDocumentiSuFilesystem()) {
	    return dh;
	} else {
	    // restituire un dh costruito sui byte[] del campo blob
	    return IOUtils.bytesToDataHandler(this.getDatiFileBlob());
	}
    }

    @Transient
    public void setDataHandler(DataHandler dh) {

	this.dh = dh;
    }

    @Column(name = "DIMENSIONE", precision = 9, scale = 0)
    public Integer getDimensione() {

	return dimensione;
    }

    public void setDimensione(Integer dimensione) {

	this.dimensione = dimensione;
    }

    @Transient
    private String getDimensioneInBytes() {

	if (null == this.dimensione) {
	    return "";
	}
	return String.valueOf(this.dimensione) + " Bytes ";
    }

    @Transient
    private String getDimensioneInKb() {

	if (null == this.dimensione) {
	    return "";
	}
	return String.valueOf(this.dimensione / 1000) + " Kb ";
    }

    @Transient
    private String getDimensioneInMb() {

	if (null == this.dimensione) {
	    return "";
	}
	return String.valueOf(this.dimensione / 1000000) + " Mb ";
    }

    @Transient
    public String getDimensioneLeggibile() {

	if (null == this.dimensione) {
	    return "";
	}
	if (this.dimensione < 1000) {
	    return getDimensioneInBytes();
	} else if (this.dimensione < 1000000) {
	    return getDimensioneInKb();
	} else {
	    return getDimensioneInMb();
	}
    }

    /**
     * @return the percorso
     */
    @Length(max = 256)
    @Column(name = "PERCORSO", length = 256)
    public String getPercorso() {

	return percorso;
    }

    /**
     * @param percorso
     *            the percorso to set
     */
    public void setPercorso(String percorso) {

	this.percorso = percorso;
	this.setDataHandlerInternal();
    }

    /**
     * metodo che imposta automaticaMENTE IL DATA HANDLER QUANDO VENGONO IMPOSTATe le proprietà percorso o nomeFilesystem
     * il data handler risulta impostato solo quando entrambe sono valorizzate
     */
    private void setDataHandlerInternal() {

	if (PayConfigurationHelper.isDocumentiSuFilesystem() && StringUtils.isNotBlank(this.percorso)
		&& StringUtils.isNotBlank(this.nomeFileSystem)) {
	    //root folder impostata come parametro di configurazione
	    File f = new File(PayConfigurationHelper.getDocumentiFilesystemPath());
	    //sottodirectory in cui il sistema ha scritto il file in base alla sua pk
	    f = new File(f, this.percorso);
	    //nome del documento su FS
	    f = new File(f, this.nomeFileSystem);
	    this.setFileDataHandler(f);
	}
    }
}
