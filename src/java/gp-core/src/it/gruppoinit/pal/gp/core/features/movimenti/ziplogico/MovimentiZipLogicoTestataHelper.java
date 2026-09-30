package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogico;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestata;

public class MovimentiZipLogicoTestataHelper {

    private String idComune;
    private Integer codiceMovimento;
    private String guid;
    private String guidCollegato;
    private Integer codiceOggettoDocumentoAllegato;
    private Set<MovimentiZipLogicoHelper> dettaglii;

    public MovimentiZipLogicoTestataHelper() {

	this.setIdComune(ORMHelper.getIdcomune());
	this.setGuid(this.generaGuid());
    }

    public MovimentiZipLogicoTestataHelper(Integer codiceMovimento) {

	this.setIdComune(ORMHelper.getIdcomune());
	this.setCodiceMovimento(codiceMovimento);
	this.setGuid(this.generaGuid());
    }

    public MovimentiZipLogicoTestataHelper(Integer codiceMovimento, String guidCollegato) {

	this.setIdComune(ORMHelper.getIdcomune());
	this.setCodiceMovimento(codiceMovimento);
	this.setGuid(this.generaGuid());
	this.setGuidCollegato(guidCollegato);
    }

    public MovimentiZipLogicoTestataHelper(MovimentiZipLogicoTestata testata, Set<MovimentiZipLogico> dettagli) {

	if (testata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile invocare MovimentiZipLogicoTestataHelper(MovimentiZipLogicoTestata testata, Set<MovimentiZipLogico> dettagli) senza passare una testata valida");
	}
	this.idComune = testata.getId().getIdcomune();
	this.codiceMovimento = testata.getId().getCodicemovimento();
	this.guid = testata.getGuid();
	this.guidCollegato = testata.getGuidCollegato();
	this.codiceOggettoDocumentoAllegato = null;
	this.codiceOggettoDocumentoAllegato = testata.getCodiceoggettoDocAll();
	if (dettagli != null) {
	    for (MovimentiZipLogico dettaglio : dettagli) {
		this.getDettaglii().add(new MovimentiZipLogicoHelper(dettaglio));
	    }
	}
    }

    private String generaGuid() {

	return UUID.randomUUID().toString();
    }

    public String getIdComune() {

	return idComune;
    }

    public void setIdComune(String idComune) {

	this.idComune = idComune;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    public String getGuid() {

	return guid;
    }

    public void setGuid(String guid) {

	this.guid = guid;
    }

    public String getGuidCollegato() {

	return guidCollegato;
    }

    public void setGuidCollegato(String guidCollegato) {

	this.guidCollegato = guidCollegato;
    }

    public Set<MovimentiZipLogicoHelper> getDettaglii() {

	if (this.dettaglii == null) {
	    this.dettaglii = new HashSet<MovimentiZipLogicoHelper>(0);
	}
	return dettaglii;
    }

    public void setDettaglii(Set<MovimentiZipLogicoHelper> dettaglii) {

	this.dettaglii = dettaglii;
    }

    public Integer getCodiceOggettoDocumentoAllegato() {

	return codiceOggettoDocumentoAllegato;
    }
}
