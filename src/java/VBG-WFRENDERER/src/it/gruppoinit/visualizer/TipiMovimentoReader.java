package it.gruppoinit.visualizer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.log4j.Logger;

public class TipiMovimentoReader {

    public static Logger logger = Logger.getLogger(TipiMovimentoReader.class.getName());
    private Connection conn;
    private int codiceprocedura;
    private String idcomune;
    private String driver;
    private String url;
    private String user;
    private String pwd;
    private String color;
    Vector movimentiVisitati = new Vector();
    Vector ramiCreati = new Vector();

    public TipiMovimentoReader(String driver, String url, String user, String pwd) throws ClassNotFoundException, SQLException {

	this.driver = driver;
	this.url = url;
	this.user = user;
	this.pwd = pwd;
    }

    public void setCodiceProcedura(int codiceprocedura) {

	this.codiceprocedura = codiceprocedura;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    private void openConnection() throws ClassNotFoundException, SQLException {

	// Step 1: Load the JDBC driver.
	logger.debug("Loading driver: " + this.driver);
	Class.forName(this.driver);
	logger.debug("Driver loaded: " + this.driver);
	// Step 2: Establish the connection to the database.
	logger.debug("Opening connection wirh: URL:" + this.url + ",USER:" + this.user + ",PASSWORD:" + this.pwd);
	this.conn = DriverManager.getConnection(this.url, this.user, this.pwd);
	logger.debug("Connection opened: URL:" + this.url + ",USER:" + this.user + ",PASSWORD:" + this.pwd);
    }

    private void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {

	try {
	    pstmt.close();
	} catch (Exception e) {
	}
	try {
	    rs.close();
	} catch (Exception e) {
	}
	try {
	    conn.close();
	} catch (Exception e) {
	}
    }

    public Nodo getMovimentoAvvioDefault() throws Exception {

	PreparedStatement pstmt;
	ResultSet rs;
	Nodo tipoMovimentoAvvio = null;
	openConnection();
	/*
	String query = 
			"select " +
			" tipiprocedureavvio.tipomovimento,tipimovimento.movimento " +
			"from tipiprocedure inner join tipiprocedureavvio" +
			" on tipiprocedure.idcomune=tipiprocedureavvio.idcomune and " +
			" tipiprocedure.codiceprocedura=tipiprocedureavvio.codiceprocedura" +
			" inner join tipimovimento on tipiprocedureavvio.idcomune=tipimovimento.idcomune and" +
			" tipiprocedureavvio.tipomovimento=tipimovimento.tipomovimento" +
			" where tipiprocedure.idcomune=? " +
			" and tipiprocedure.codiceprocedura=? and tipiprocedureavvio.defaultsn=?";
	*/
	StringBuffer str = new StringBuffer();
	str.append("select tipiprocedureavvio.tipomovimento,tipimovimento.movimento  ");
	str.append("from tipiprocedure, tipiprocedureavvio, tipimovimento ");
	str.append("where  ");
	str.append("tipiprocedure.idcomune=tipiprocedureavvio.idcomune and tipiprocedure.codiceprocedura=tipiprocedureavvio.codiceprocedura  ");
	str.append("and tipiprocedureavvio.idcomune=tipimovimento.idcomune and tipiprocedureavvio.tipomovimento=tipimovimento.tipomovimento  ");
	str.append("and tipiprocedure.idcomune=? and tipiprocedure.codiceprocedura=? and tipiprocedureavvio.defaultsn=? ");
	pstmt = conn.prepareStatement(str.toString());
	pstmt.setString(1, this.idcomune);
	pstmt.setInt(2, this.codiceprocedura);
	pstmt.setInt(3, 1);
	rs = pstmt.executeQuery();
	while (rs.next()) {
	    tipoMovimentoAvvio = new Nodo(rs.getString("tipomovimento"), rs.getString("movimento"), this.color);
	}
	logger.debug("getMovimentoAvvioDefault: " + tipoMovimentoAvvio);
	closeResources(conn, pstmt, rs);
	return tipoMovimentoAvvio;
    }

    public Vector getContromovimenti(Nodo mov) throws Exception {

	logger.debug("getContromovimenti(" + mov + ")");
	PreparedStatement pstmt;
	ResultSet rs;
	Vector controMov = new Vector();
	Nodo tempMov;
	openConnection();
	/*
	String query = 
			"select " +
			" tipicontromovimento.tipocontromovimento,tipimovimento.movimento,tipicontromovimento.soloseesitonegativo " +
			" from tipicontromovimento inner join tipimovimento" +
			" on tipicontromovimento.idcomune=tipimovimento.idcomune and " +
			" tipicontromovimento.tipocontromovimento=tipimovimento.tipomovimento" +
			" where tipicontromovimento.idcomune = ? " +
			" and tipicontromovimento.tipomovimento=? and (codiceprocedura is null or codiceprocedura=?) ";
	*/
	StringBuffer str = new StringBuffer();
	str.append("select   ");
	str.append("tipicontromovimento.tipocontromovimento,tipimovimento.movimento,tipicontromovimento.soloseesitonegativo   ");
	str.append("from  ");
	str.append("tipicontromovimento, tipimovimento ");
	str.append("where ");
	str.append(
		"tipicontromovimento.idcomune=tipimovimento.idcomune and  tipicontromovimento.tipocontromovimento=tipimovimento.tipomovimento and  ");
	str.append("tipicontromovimento.idcomune = ? and tipicontromovimento.tipomovimento=? and (codiceprocedura is null or codiceprocedura=?) ");
	pstmt = conn.prepareStatement(str.toString());
	pstmt.setString(1, this.idcomune);
	pstmt.setString(2, mov.getId());
	pstmt.setInt(3, this.codiceprocedura);
	rs = pstmt.executeQuery();
	while (rs.next()) {
	    tempMov = new Nodo(rs.getString("tipocontromovimento"), rs.getString("movimento"), this.color, rs.getInt("soloseesitonegativo"));
	    controMov.add(tempMov);
	}
	closeResources(conn, pstmt, rs);
	logger.debug("lista cmov: " + controMov);
	return controMov;
    }

    public boolean isPresent(Vector movVisited, Nodo mov) {

	boolean present = false;
	Nodo temp;
	for (int i = 0; i < movVisited.size(); i++) {
	    temp = (Nodo) movVisited.get(i);
	    if (temp.getId().equals(mov.getId())) {
		present = true;
		break;
	    }
	}
	return present;
    }

    //recursive function...
    public void visitaCMov(Nodo mov) throws Exception {

	Vector cMovTemp = getContromovimenti(mov);
	Nodo temp;
	for (int i = 0; i < cMovTemp.size(); i++) {
	    temp = (Nodo) cMovTemp.get(i);
	    logger.debug("creo ramo da nodo: " + mov + " a nodo: " + temp);
	    //crea ramo
	    Ramo r = new Ramo(mov.getId(), temp.getId(), temp.getLabel());
	    ramiCreati.add(r);
	    if (!isPresent(movimentiVisitati, temp)) {
		//add nodo a movimentiVisitati
		logger.debug("add nodo visitato: " + temp);
		movimentiVisitati.add(temp);
		visitaCMov(temp);
	    }
	}
    }

    public Vector getMovimentiVisitati() {

	return movimentiVisitati;
    }

    public void addMovDefault(Nodo mov) {

	movimentiVisitati.add(mov);
    }

    public Vector getRamiCreati() {

	return ramiCreati;
    }

    public void setColor(String color) {

	this.color = color;
    }
}
