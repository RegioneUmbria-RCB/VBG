package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.math.BigDecimal;

public class BollGestMercatiDettHelper {

    private Integer idConto;
    private Integer idPosteggio;
    private Integer idMercatoUso;
    private Integer idGiornata;
    private BigDecimal importoTotale;

    public BollGestMercatiDettHelper(Integer idConto, Integer idPosteggio, Integer idMercatoUso, Integer idGiornata, BigDecimal importoTotale) {

	super();
	this.idConto = idConto;
	this.idPosteggio = idPosteggio;
	this.idMercatoUso = idMercatoUso;
	this.idGiornata = idGiornata;
	this.importoTotale = importoTotale;
    }

    public Integer getIdConto() {

	return idConto;
    }

    public Integer getIdPosteggio() {

	return idPosteggio;
    }

    public Integer getIdMercatoUso() {

	return idMercatoUso;
    }

    public Integer getIdGiornata() {

	return idGiornata;
    }

    public BigDecimal getImportoTotale() {

	return importoTotale;
    }
}
