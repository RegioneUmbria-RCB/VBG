package it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.coefficienti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jdbc.Work;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;

public class RecuperoCoefficientiJDBCWorker implements Work {

    private java.sql.Date dataInizio;
    private java.sql.Date dataFine;
    private final Map<ChiaveCoefficienteMercato, List<ValoreCoefficienteMercato>> mappaCoefficienti = new HashMap<ChiaveCoefficienteMercato, List<ValoreCoefficienteMercato>>();
    private final List<Conti> _contiAttivi;

    public Map<ChiaveCoefficienteMercato, List<ValoreCoefficienteMercato>> getMappaCoefficienti() {

	return mappaCoefficienti;
    }

    public RecuperoCoefficientiJDBCWorker(IntervalloDate intervalloDate, ContiService contiService) {

	this.dataInizio = getData(intervalloDate.getDataInizio());
	this.dataFine = getData(intervalloDate.getDataFine());
	this._contiAttivi = contiService.findContiAttivi(dataInizio);
    }

    @Override
    public void execute(Connection connection) throws SQLException {

	String sqlSelect = "select" + //
		" mercati_cfg_conti.fk_conto," + //
		" mercati_cfg_conti.fk_codicemercato," + //
		" mercati_cfg_conti.fk_categoria_mercato," + //
		" mercati_cfg_conti.fk_posteggisettori_id," + //
		" mercati_cfg_conti.fk_codiceistat," + //
		" mercati_cfg_conti.fk_codice_conc_uso," + //
		" mercati_cfg_conti.datainzioval," + //
		" mercati_cfg_conti.datafineval," + //
		" mercati_cfg_conti.importo " + //
		"from" + //
		" mercati_cfg_conti " + //
		"where" + //
		" mercati_cfg_conti.idcomune = ? and" + //
		" mercati_cfg_conti.datafineval >= ? and" + //
		" mercati_cfg_conti.datainzioval <= ?";
	final PreparedStatement pstmt = connection.prepareStatement(sqlSelect);
	int position = 1;
	pstmt.setString(position++, ORMHelper.getIdcomune());
	pstmt.setDate(position++, this.dataInizio);
	pstmt.setDate(position++, this.dataFine);
	final ResultSet rs = pstmt.executeQuery();
	while (rs.next()) {
	    if (rs.getObject("fk_conto") == null) {
		for (Conti conto : this._contiAttivi) {
		    //chiave
		    ChiaveCoefficienteMercato chiave = new ChiaveCoefficienteMercato();
		    chiave.setIdConto(conto.getId().getCodice());
		    //valore
		    ValoreCoefficienteMercato valore = this.recuperaValoreDaRs(rs);
		    // verifico ed eventualmente aggiungo alla mappa
		    List<ValoreCoefficienteMercato> lista = mappaCoefficienti.get(chiave);
		    if (lista == null) {
			lista = new ArrayList<ValoreCoefficienteMercato>();
			mappaCoefficienti.put(chiave, lista);
		    }
		    lista.add(valore);
		}
	    } else {
		//chiave
		ChiaveCoefficienteMercato chiave = new ChiaveCoefficienteMercato();
		chiave.setIdConto(rs.getInt("fk_conto"));
		//valore
		ValoreCoefficienteMercato valore = this.recuperaValoreDaRs(rs);
		// verifico ed eventualmente aggiungo alla mappa
		List<ValoreCoefficienteMercato> lista = mappaCoefficienti.get(chiave);
		if (lista == null) {
		    lista = new ArrayList<ValoreCoefficienteMercato>();
		    mappaCoefficienti.put(chiave, lista);
		}
		lista.add(valore);
	    }
	}
	rs.close();
	pstmt.close();
    }

    private ValoreCoefficienteMercato recuperaValoreDaRs(ResultSet rs) throws SQLException {

	ValoreCoefficienteMercato valore = new ValoreCoefficienteMercato();
	if (rs.getObject("fk_codicemercato") != null) {
	    valore.setIdMercato(rs.getInt("fk_codicemercato"));
	}
	if (rs.getObject("fk_categoria_mercato") != null) {
	    valore.setIdCategoriaMercato(rs.getInt("fk_categoria_mercato"));
	}
	if (rs.getObject("fk_posteggisettori_id") != null) {
	    valore.setIdSettorePosteggio(rs.getInt("fk_posteggisettori_id"));
	}
	valore.setCodiceIstat(rs.getString("fk_codiceistat"));
	if (rs.getObject("fk_codice_conc_uso") != null) {
	    valore.setIdConcessioneUso(rs.getInt("fk_codice_conc_uso"));
	}
	if (rs.getObject("datainzioval") != null) {
	    valore.setInizioValidita(rs.getDate("datainzioval"));
	}
	if (rs.getObject("datafineval") != null) {
	    valore.setFineValidita(rs.getDate("datafineval"));
	}
	if (rs.getObject("importo") != null) {
	    valore.setImporto(rs.getBigDecimal("importo"));
	}
	return valore;
    }

    private java.sql.Date getData(Date data) {

	Calendar c = Calendar.getInstance();
	c.setTime(data);
	return new java.sql.Date(c.getTimeInMillis());
    }
}
