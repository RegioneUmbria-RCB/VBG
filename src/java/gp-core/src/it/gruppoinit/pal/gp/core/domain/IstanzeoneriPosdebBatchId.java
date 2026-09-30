package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Embeddable
public class IstanzeoneriPosdebBatchId implements Serializable {

    public enum TipoDocumentoDaGenerare {
	FATTURA,
	AVVISO
    }

    /**
     * 
     */
    private static final long serialVersionUID = 7054522869486296454L;
    private String idcomune;
    private Integer idistanzeoneri;
    private Integer idDettPosizioneDebitoria;
    private TipoDocumentoDaGenerare tipoDocumento;

    public IstanzeoneriPosdebBatchId() {

	super();
	this.idcomune = ORMHelper.getIdcomune();
    }

    public IstanzeoneriPosdebBatchId(Integer idistanzeoneri, Integer idDettPosizioneDebitoria, TipoDocumentoDaGenerare tipoDocumento) {

	this(ORMHelper.getIdcomune(), idistanzeoneri, idDettPosizioneDebitoria, tipoDocumento);
    }

    public IstanzeoneriPosdebBatchId(String idcomune, Integer idistanzeoneri, Integer idDettPosizioneDebitoria,
	    TipoDocumentoDaGenerare tipoDocumento) {

	super();
	this.idcomune = idcomune;
	this.idistanzeoneri = idistanzeoneri;
	this.idDettPosizioneDebitoria = idDettPosizioneDebitoria;
	this.tipoDocumento = tipoDocumento;
    }

    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    @Column(name = "FK_ISTANZEONERI_ID", nullable = false, precision = 10, scale = 0)
    public Integer getIdistanzeoneri() {

	return idistanzeoneri;
    }

    public void setIdistanzeoneri(Integer idistanzeoneri) {

	this.idistanzeoneri = idistanzeoneri;
    }

    @Column(name = "ID_POSIZIONE_DEBITORIA", nullable = false, precision = 10, scale = 0)
    public Integer getIdDettPosizioneDebitoria() {

	return idDettPosizioneDebitoria;
    }

    public void setIdDettPosizioneDebitoria(Integer idDettPosizioneDebitoria) {

	this.idDettPosizioneDebitoria = idDettPosizioneDebitoria;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_DOCUMENTO", nullable = false, length = 80)
    public TipoDocumentoDaGenerare getTipoDocumento() {

	return this.tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoDaGenerare tipoDocumento) {

	this.tipoDocumento = tipoDocumento;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((idDettPosizioneDebitoria == null) ? 0 : idDettPosizioneDebitoria.hashCode());
	result = prime * result + ((idcomune == null) ? 0 : idcomune.hashCode());
	result = prime * result + ((idistanzeoneri == null) ? 0 : idistanzeoneri.hashCode());
	result = prime * result + ((tipoDocumento == null) ? 0 : tipoDocumento.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	IstanzeoneriPosdebBatchId other = (IstanzeoneriPosdebBatchId) obj;
	if (idDettPosizioneDebitoria == null) {
	    if (other.idDettPosizioneDebitoria != null)
		return false;
	} else if (!idDettPosizioneDebitoria.equals(other.idDettPosizioneDebitoria))
	    return false;
	if (idcomune == null) {
	    if (other.idcomune != null)
		return false;
	} else if (!idcomune.equals(other.idcomune))
	    return false;
	if (idistanzeoneri == null) {
	    if (other.idistanzeoneri != null)
		return false;
	} else if (!idistanzeoneri.equals(other.idistanzeoneri))
	    return false;
	if (tipoDocumento != other.tipoDocumento)
	    return false;
	return true;
    }

    @Override
    public String toString() {

	return "[idcomune=" +
		this.idcomune +
		", idistanzeoneri=" +
		this.idistanzeoneri +
		", idDettPosizioneDebitoria=" +
		idDettPosizioneDebitoria +
		", tipoDocumento=" +
		tipoDocumento +
		"] ";
    }
}
