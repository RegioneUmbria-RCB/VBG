package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.dialect.Dialect;
import org.hibernate.jdbc.Work;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class InserimentoRigheJDBCWorker implements Work {

    private static final Logger log = LoggerFactory.getLogger(InserimentoRigheJDBCWorker.class);
    private String query = null;
    private List<ParameterHelper> parametri;
    private boolean isAzienda;
    private DialettoEnum d;
    private String guid;
    private Integer bollTestataId;
    private java.sql.Date dataScadenza;

    public InserimentoRigheJDBCWorker(String query, List<ParameterHelper> parametri, boolean isAzienda, Dialect dialect, String guid,
	    Integer bollTestataId, Date dataScadenza) {

	log.debug("Query: {}", query);
	log.debug("Parametri: {}", parametri);
	this.query = query;
	this.parametri = parametri;
	this.isAzienda = isAzienda;
	d = DialettoEnum.fromHibernateDialect(dialect.toString());
	this.guid = guid;
	this.bollTestataId = bollTestataId;
	this.dataScadenza = getDataScadenza(dataScadenza);
    }

    @Override
    public void execute(Connection connection) throws SQLException {

	inserisciTabellaRaccordo(connection);
	inserisciDettaglio(connection);
	inserisciDettaglioOneri(connection);
	aggiornaOneri(connection);
	svuotaTabellaRaccordo(connection);
	connection.commit();
    }

    private void aggiornaOneri(Connection connection) throws SQLException {

	PreparedStatement pstmt = null;
	String sql = "";
	if (DialettoEnum.MYSQL.equals(d)) {
	    sql = "update" +
		    " istanzeoneri" +
		    "   inner join boll_gest_oneri_sequence on" +
		    "    istanzeoneri.idcomune = boll_gest_oneri_sequence.idcomune and" +
		    "	 istanzeoneri.id = boll_gest_oneri_sequence.fk_codiceistanzeoneri and" +
		    "	 boll_gest_oneri_sequence.guid=?" +
		    "set " +
		    "  istanzeoneri.datascadenza = ? " +
		    "where istanzeoneri.idcomune = ?";
	    pstmt = connection.prepareStatement(sql);
	    pstmt.setString(1, this.guid);
	    pstmt.setDate(2, dataScadenza);
	    pstmt.setString(3, ORMHelper.getIdcomune());
	} else {
	    sql = "update istanzeoneri set datascadenza=? where idcomune=? AND ID IN " + //
		    " (select fk_codiceistanzeoneri from boll_gest_oneri_sequence where boll_gest_oneri_sequence.guid=? )";
	    pstmt = connection.prepareStatement(sql);
	    pstmt.setDate(1, this.dataScadenza);
	    pstmt.setString(2, ORMHelper.getIdcomune());
	    pstmt.setString(3, this.guid);
	}
	pstmt.executeUpdate();
    }

    private void svuotaTabellaRaccordo(Connection connection) throws SQLException {

	String sql = "delete from boll_gest_oneri_sequence WHERE guid=?";
	final PreparedStatement pstmt = connection.prepareStatement(sql);
	pstmt.setString(1, this.guid);
	pstmt.executeUpdate();
    }

    private void inserisciTabellaRaccordo(Connection connection) throws SQLException {

	final PreparedStatement pstmt = connection.prepareStatement(this.query);
	for (ParameterHelper p : parametri) {
	    log.debug("Processo il parametro {}", p);
	    Type type = p.getType();
	    if (type instanceof TimestampType) {
		pstmt.setDate((p.getPosition() + 1), getDataScadenza((Date) p.getValue()));
	    } else if (type instanceof StringType) {
		pstmt.setString((p.getPosition() + 1), (String) p.getValue());
	    } else if (type instanceof IntegerType) {
		pstmt.setInt((p.getPosition() + 1), (Integer) p.getValue());
	    } else {
		throw new NotImplementedException("Tipo non ancora gestito " + type);
	    }
	}
	pstmt.executeUpdate();
    }

    private void inserisciDettaglioOneri(Connection connection) throws SQLException {

	String sql = "insert into boll_gest_istanzeoneri( idcomune, id, fk_bollgestdet_id, fk_codiceistanzeoneri, fk_bollgest_id) ";
	sql += "(SELECT idcomune,id_bollegest_oneri,id_bollegest_dett,fk_codiceistanzeoneri, fk_bollgest_id from boll_gest_oneri_sequence WHERE guid=?)";
	final PreparedStatement pstmt = connection.prepareStatement(sql);
	pstmt.setString(1, this.guid);
	pstmt.executeUpdate();
    }

    private void inserisciDettaglio(Connection connection) throws SQLException {

	String sql = "insert into boll_gest_dettaglio(  idcomune, id, fk_bollgest_id, flag_validata, descrizione, flag_ins_auto, fk_codiceanagrafe, fk_conto_id, flag_eliminata," + //
		"  flag_rettificata, importo_senza_iva, iva, importo_totale, flag_conguaglio, data_scadenza) (";
	sql += " select "; //
	sql += "   istanzeoneri.idcomune, "; //
	sql += "   boll_gest_oneri_sequence.id_bollegest_dett as id, "; //
	sql += "   ? as fk_bollgest_id, "; //
	sql += "   ? as flag_validata, "; //
	// MYSQL
	sql += getDescrizione();
	// 
	sql += "   ? as flag_ins_auto, "; //
	if (isAzienda) {
	    sql += "   coalesce( "; //
	    sql += "     istanze.codicetitolarelegale, "; //
	    sql += "     istanze.codicerichiedente "; //
	    sql += "   ) as fk_codiceanagrafe, "; //
	} else {
	    sql += "     istanze.codicerichiedente as fk_codiceanagrafe, "; //
	}
	sql += "   tipicausalioneridettaglio.fkconto as fk_conto_id, "; //
	sql += "   ? as flag_eliminata, "; //
	sql += "   ? as flag_rettificata, "; //
	sql += "   istanzeoneri.prezzo as importo_senza_iva, "; //
	sql += "   ? as iva, "; //
	sql += "   istanzeoneri.prezzo as importo_totale, "; //
	sql += "   ? as flag_conguaglio, "; //
	sql += "   coalesce( "; //
	sql += "     istanzeoneri.datascadenza, "; //
	sql += "     ? "; //
	sql += "   ) as data_scadenza "; //
	sql += " from "; //
	sql += "    boll_gest_oneri_sequence inner join  "; //
	sql += "    istanzeoneri on  "; //
	sql += "    istanzeoneri.idcomune=boll_gest_oneri_sequence.idcomune and "; //
	sql += "    istanzeoneri.id=boll_gest_oneri_sequence.fk_codiceistanzeoneri    "; //
	sql += "   inner join  tipicausalioneri on tipicausalioneri.idcomune = istanzeoneri.idcomune "; //
	sql += "   and tipicausalioneri.co_id = istanzeoneri.fkidtipocausale "; //
	sql += "   inner join  tipicausalioneridettaglio on tipicausalioneridettaglio.idcomune = tipicausalioneri.idcomune "; //
	sql += "   and tipicausalioneridettaglio.fkcausale = tipicausalioneri.co_id and tipicausalioneridettaglio.flag_attivo = 1 "; //
	sql += "   inner join  istanze on istanze.idcomune = istanzeoneri.idcomune "; //
	sql += "   and istanze.codiceistanza = istanzeoneri.codiceistanza "; //
	sql += " where  "; //
	sql += " boll_gest_oneri_sequence.guid=?"; //
	sql += ")";
	final PreparedStatement pstmt = connection.prepareStatement(sql);
	pstmt.setInt(1, this.bollTestataId); // fk_bollgest_id
	pstmt.setInt(2, 0);// as flag_validata
	pstmt.setInt(3, 1);// as flag_ins_aut
	pstmt.setInt(4, 0);// as flag_eliminata
	pstmt.setInt(5, 0);// as flag_rettificata
	pstmt.setInt(6, 0); // as iva
	pstmt.setInt(7, 0);// as flag_conguaglio
	pstmt.setDate(8, this.dataScadenza);
	pstmt.setString(9, this.guid);
	pstmt.executeUpdate();
    }

    private java.sql.Date getDataScadenza(Date dataScadenzaUtilDate) {

	Calendar c = Calendar.getInstance();
	c.setTime(dataScadenzaUtilDate);
	return new java.sql.Date(c.getTimeInMillis());
    }

    private String getDescrizione() {

	String sql = "";
	switch (d) {
	    case MYSQL:
		sql = "   concat_ws( '', "; //
		sql += "     'istanza ', "; //
		sql += "     istanze.numeroistanza, "; //
		sql += "     ' del ', "; //
		sql += "     date_format(istanze.data, '%d/%m/%Y'), "; //
		sql += "     ' - ', "; //
		sql += "     tipicausalioneri.co_descrizione, "; //
		sql += "     ' ( ', "; //
		sql += "     date_format(istanzeoneri.data, '%d/%m/%Y'), "; //
		sql += "     ' )' "; //
		sql += "   ) as descrizione, "; //
		return sql;
	    case ORACLE:
		sql += "     'istanza ' || "; //
		sql += "     coalesce(istanze.numeroistanza, '') || "; //
		sql += "     ' del ' || "; //
		sql += "     to_char(istanze.data, 'dd/MM/yyyy') || "; //
		sql += "     ' - ' || "; //
		sql += "     coalesce(tipicausalioneri.co_descrizione, '') || "; //
		sql += "     ' ( ' || "; //
		sql += "     coalesce(to_char(istanzeoneri.data, 'dd/MM/yyyy'),'') || "; //
		sql += "     ' )'"; //
		sql += "    as descrizione, "; //
		return sql;
	    default:
		break;
	}
	throw new NotImplementedException("Dialetto " + d + " non implementato per la funzionalità");
    }
}