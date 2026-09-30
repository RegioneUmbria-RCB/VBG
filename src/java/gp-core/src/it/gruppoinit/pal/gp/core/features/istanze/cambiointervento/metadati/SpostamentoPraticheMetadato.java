package it.gruppoinit.pal.gp.core.features.istanze.cambiointervento.metadati;

import java.util.Calendar;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.istanze.metadati.IMetadatoIstanza;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class SpostamentoPraticheMetadato implements IMetadatoIstanza {

    private String idComune;
    private Integer codiceIstanza;
    private String chiave;
    private static final String NOME = "SPOSTAMENTO_PRATICHE";
    private String valore;

    @Override
    public String getIdComune() {

	return this.idComune;
    }

    @Override
    public Integer getCodiceIstanza() {

	return this.codiceIstanza;
    }

    @Override
    public String getChiave() {

	return this.chiave;
    }

    @Override
    public String getValore() {

	return this.valore;
    }

    public SpostamentoPraticheMetadato(Integer codiceIstanza, Integer codiceInterventoProc) {

	super();
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Parametro codiceIstanza non corretto");
	}
	if (codiceInterventoProc == null) {
	    throw new IllegalArgumentException("Parametro codiceInterventoProc non corretto");
	}
	this.chiave = SpostamentoPraticheMetadato.NOME + Utilities.formatDate(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
	this.idComune = ORMHelper.getIdcomune();
	this.codiceIstanza = codiceIstanza;
	this.valore = codiceInterventoProc.toString();
    }
}
