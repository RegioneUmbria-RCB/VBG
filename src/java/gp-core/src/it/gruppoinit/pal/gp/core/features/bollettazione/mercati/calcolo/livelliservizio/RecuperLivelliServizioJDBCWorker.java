package it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.livelliservizio;

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
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;

public class RecuperLivelliServizioJDBCWorker implements Work {

    private final java.sql.Date dataInizio;
    private final java.sql.Date dataFine;
    private final Map<ChiaveLivelloServizio, List<ValoreLivelloServizio>> mappaLivelliServizio = new HashMap<ChiaveLivelloServizio, List<ValoreLivelloServizio>>();

    public Map<ChiaveLivelloServizio, List<ValoreLivelloServizio>> getMappaLivelliServizio() {

	return mappaLivelliServizio;
    }

    public RecuperLivelliServizioJDBCWorker(IntervalloDate intervalloDate) {

	Calendar dataFi = Calendar.getInstance();
	dataFi.setTime(intervalloDate.getDataFine());
	dataFi.set(Calendar.HOUR, 23);
	dataFi.set(Calendar.MINUTE, 59);
	dataFi.set(Calendar.SECOND, 59);
	this.dataFine = this.getData(dataFi.getTime());
	Calendar dataIn = Calendar.getInstance();
	dataIn.setTime(intervalloDate.getDataInizio());
	dataIn.set(Calendar.HOUR, 0);
	dataIn.set(Calendar.MINUTE, 0);
	dataIn.set(Calendar.SECOND, 0);
	this.dataInizio = this.getData(dataIn.getTime());
    }

    @Override
    public void execute(Connection connection) throws SQLException {

	String sqlSelect = "select" + //
		" mercati_d_livello_servizio.fk_mercato_d," + //
		" mercati_livello_servizio.fk_mercato_uso," + //
		" mercati_livello_servizio.data_inizio_validita," + //
		" mercati_livello_servizio.data_fine_validita," + //
		" mercati_d_livello_servizio.data_inizio," + //
		" mercati_d_livello_servizio.data_fine," + //
		" livello_servizio.segnaposto," + //
		" mercati_livello_servizio.tariffa," + //
		" sum(case mercati_d_livello_servizio.usa_mq_posteggio when 1 then mercati_d.superficie else mercati_d_livello_servizio.fattore_moltiplicativo end ) as quantita " + //
		"from" + //
		" mercati_d_livello_servizio" + //
		"  inner join mercati_d on" + //
		"    mercati_d.idcomune = mercati_d_livello_servizio.idcomune and" + //
		"    mercati_d.idposteggio = mercati_d_livello_servizio.fk_mercato_d" + //
		"  inner join mercati_livello_servizio on" + //
		"    mercati_livello_servizio.idcomune = mercati_d_livello_servizio.idcomune and" + //
		"    mercati_livello_servizio.id = mercati_d_livello_servizio.fk_merc_servizio" + //
		"  inner join livello_servizio on" + //
		"    livello_servizio.idcomune = mercati_livello_servizio.idcomune and" + //
		"    livello_servizio.id = mercati_livello_servizio.fk_servizio " + //
		"where" + //
		" mercati_d_livello_servizio.idcomune = ? and" + //
		" mercati_livello_servizio.attivo = ? and" + //
		" (" + //
		"   mercati_livello_servizio.data_inizio_validita <= ? and" + //
		"   mercati_livello_servizio.data_fine_validita >= ?" + //
		" ) and" + //
		" (" + //
		"   mercati_d_livello_servizio.data_inizio <= ? and" + //
		"   mercati_d_livello_servizio.data_fine >= ?" + //
		" ) " + //
		"group by" + //
		" mercati_d_livello_servizio.fk_mercato_d," + //
		" mercati_livello_servizio.fk_mercato_uso," + //
		" mercati_livello_servizio.data_inizio_validita," + //
		" mercati_livello_servizio.data_fine_validita," + //
		" mercati_d_livello_servizio.data_inizio," + //
		" mercati_d_livello_servizio.data_fine," + //
		" livello_servizio.segnaposto," + //
		" mercati_livello_servizio.tariffa";
	final PreparedStatement pstmt = connection.prepareStatement(sqlSelect);
	int position = 1;
	pstmt.setString(position++, ORMHelper.getIdcomune());
	pstmt.setInt(position++, 1);
	pstmt.setDate(position++, this.dataInizio);
	pstmt.setDate(position++, this.dataFine);
	pstmt.setDate(position++, this.dataInizio);
	pstmt.setDate(position++, this.dataFine);
	final ResultSet rs = pstmt.executeQuery();
	while (rs.next()) {
	    //chiave
	    ChiaveLivelloServizio chiave = new ChiaveLivelloServizio();
	    chiave.setIdPosteggio(rs.getInt("fk_mercato_d"));
	    chiave.setIdUso(rs.getInt("fk_mercato_uso"));
	    //valore
	    ValoreLivelloServizio valore = new ValoreLivelloServizio();
	    valore.setInizioValiditaLivelloMercato(rs.getDate("data_inizio_validita"));
	    valore.setFineValiditaLivelloMercato(rs.getDate("data_fine_validita"));
	    valore.setInizioValiditaLivelloPosteggio(rs.getDate("data_inizio"));
	    valore.setFineValiditaLivelloPosteggio(rs.getDate("data_fine"));
	    valore.setSegnaposto(rs.getString("segnaposto"));
	    valore.setTariffa(rs.getBigDecimal("tariffa"));
	    valore.setQuantita(rs.getBigDecimal("quantita"));
	    // verifico ed eventualmente aggiungo alla mappa
	    List<ValoreLivelloServizio> lista = mappaLivelliServizio.get(chiave);
	    if (lista == null) {
		lista = new ArrayList<ValoreLivelloServizio>();
		mappaLivelliServizio.put(chiave, lista);
	    }
	    lista.add(valore);
	}
	rs.close();
	pstmt.close();
    }

    private java.sql.Date getData(Date data) {

	Calendar c = Calendar.getInstance();
	c.setTime(data);
	return new java.sql.Date(c.getTimeInMillis());
    }
}
