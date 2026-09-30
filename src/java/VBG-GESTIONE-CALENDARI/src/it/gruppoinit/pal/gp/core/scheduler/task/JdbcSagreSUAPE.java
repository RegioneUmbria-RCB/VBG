package it.gruppoinit.pal.gp.core.scheduler.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcSagreSUAPE extends JdbcConnection {

    private static final Logger log = LoggerFactory.getLogger(JdbcSagreSUAPE.class);
    private Properties deployProps = null;
    private Properties jdbcProps = new Properties();

    public JdbcSagreSUAPE(Properties dbProps, Properties deployProps, String jdbcPropertiesFileName) {

	super(dbProps);
	this.deployProps = deployProps;
	try {
	    jdbcProps.load(JdbcConnection.class.getClassLoader().getResourceAsStream(jdbcPropertiesFileName));
	} catch (Exception e) {
	    log.error("errore durante il caricamento dei file di properties {}", jdbcPropertiesFileName);
	    throw new RuntimeException(e);
	}
    }

    public List<IstanzaDaElaborareDTO> getIstanze() throws Exception {

	try {
	    List<IstanzaDaElaborareDTO> istanzaDaElaborareList = getIstanzeDaElaborare();
	    for (IstanzaDaElaborareDTO istanzaDaElaborare : istanzaDaElaborareList) {
		populateDatiScheda(istanzaDaElaborare);
	    }
	    return istanzaDaElaborareList;
	} catch (Exception e) {
	    log.error("getIstanze", e);
	    throw e;
	}
    }

    // 1
    private List<IstanzaDaElaborareDTO> getIstanzeDaElaborare() throws Exception {

	log.debug("getIstanzeDaElaborare");
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	List<IstanzaDaElaborareDTO> list = new ArrayList<IstanzaDaElaborareDTO>();
	try {
	    conn = getConnection();
	    log.debug("getIstanzeDaElaborare: query={}", Query.SELECT_ISTANZE_DA_ELABORARE);
	    pstmt = conn.prepareStatement(Query.SELECT_ISTANZE_DA_ELABORARE);
	    pstmt.setInt(1, Integer.valueOf(deployProps.getProperty("exporter.suape.db.feste_sagre.codiceScheda")));
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		IstanzaDaElaborareDTO dto = new IstanzaDaElaborareDTO();
		dto.setIDCOMUNE(rs.getString("IDCOMUNE"));
		dto.setCODICECOMUNE(rs.getString("CODICECOMUNE"));
		dto.setCODICEPRATICATEL(rs.getString("CODICEPRATICATEL"));
		dto.setCODICEISTANZA(rs.getString("CODICEISTANZA"));
		dto.setNUMEROISTANZA(rs.getString("NUMEROISTANZA"));
		dto.setDATAISTANZA(rs.getString("DATAISTANZA"));
		dto.setNUMEROPROTOCOLLO(rs.getString("NUMEROPROTOCOLLO"));
		dto.setDATAPROTOCOLLO(rs.getString("DATAPROTOCOLLO"));
		String nominativoRichiedente = rs.getString("NOMINATIVOTITOLARE");
		if (StringUtils.isBlank(nominativoRichiedente)) {
		    dto.setNOMERICHIEDENTE(rs.getString("NOMERICHIEDENTE"));
		    dto.setNOMINATIVORICHIEDENTE(rs.getString("COGNOMERICHIEDENTE"));
		    dto.setCFRICHIEDENTE(rs.getString("CFRICHIEDENTE"));
		    dto.setPIVARICHIEDENTE(rs.getString("PIVARICHIEDENTE"));
		} else {
		    dto.setNOMERICHIEDENTE(rs.getString("NOMETITOLARE"));
		    dto.setNOMINATIVORICHIEDENTE(nominativoRichiedente);
		    dto.setCFRICHIEDENTE(rs.getString("CFTITOLARE"));
		    dto.setPIVARICHIEDENTE(rs.getString("PIVATITOLARE"));
		}
		dto.setAUTORIZNUMERO(rs.getString("AUTORIZNUMERO"));
		dto.setAUTORIZDATA(rs.getString("AUTORIZDATA"));
		list.add(dto);
	    }
	    log.info("getIstanzeDaElaborare: {}", list.size());
	    return list;
	} catch (Exception e) {
	    log.error("getIstanzeDaElaborare", e);
	    throw e;
	} finally {
	    closeConnection(conn, pstmt, rs);
	}
    }

    private void populateDatiScheda(IstanzaDaElaborareDTO istanzaDaElaborare) throws Exception {

	log.info("populateDatiScheda");
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    conn = getConnection();
	    log.debug("populateDatiScheda: query={}", Query.SELECT_DATI_SCHEDA_SAGRE_SUAPE);
	    pstmt = conn.prepareStatement(Query.SELECT_DATI_SCHEDA_SAGRE_SUAPE);
	    pstmt.setInt(1, Integer.valueOf(deployProps.getProperty("exporter.suape.db.dyn2dati.feste_sagre.tipologia_a.id")));
	    pstmt.setInt(2, Integer.valueOf(deployProps.getProperty("exporter.suape.db.dyn2dati.feste_sagre.tipologia_b.id")));
	    pstmt.setInt(3, Integer.valueOf(deployProps.getProperty("exporter.suape.db.dyn2dati.feste_sagre.luogo_svolgimento_civico.id")));
	    pstmt.setInt(4, Integer.valueOf(istanzaDaElaborare.getCODICEISTANZA()));
	    pstmt.setString(5, istanzaDaElaborare.getIDCOMUNE());
	    pstmt.setInt(6, Integer.valueOf(deployProps.getProperty("exporter.suape.db.dyn2dati.feste_sagre.denominazione.id")));
	    pstmt.setInt(7, Integer.valueOf(deployProps.getProperty("exporter.suape.db.dyn2dati.feste_sagre.comune.id")));
	    pstmt.setInt(8, Integer.valueOf(deployProps.getProperty("exporter.suape.db.dyn2dati.feste_sagre.luogo_svolgimento.id")));
	    pstmt.setInt(9, Integer.valueOf(deployProps.getProperty("exporter.suape.db.dyn2dati.feste_sagre.dal.id")));
	    pstmt.setInt(10, Integer.valueOf(deployProps.getProperty("exporter.suape.db.dyn2dati.feste_sagre.al.id")));
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		String tipologiaA = rs.getString("TIPOLOGIA_A");
		String tipologiaB = rs.getString("TIPOLOGIA_B");
		// TODO controllare se giusti
		if ("SAGRA DELL'UMBRIA".equals(tipologiaA)) {
		    istanzaDaElaborare.setTIPOLOGIA(tipologiaA);
		} else if ("FESTA POPOLARE".equals(tipologiaB)) {
		    istanzaDaElaborare.setTIPOLOGIA(tipologiaB);
		}
		istanzaDaElaborare.setDENOMINAZIONE(rs.getString("DENOMIN"));
		istanzaDaElaborare.setCOMUNE_SVOLGIMENTO(rs.getString("COMUNE_SVOLGIMENTO"));
		istanzaDaElaborare.setLUOGO_SVOLGIMENTO(rs.getString("LUOGO_SVOLGIMENTO") + ", " + rs.getString("LUOGO_SVOLGIMENTO_CIVICO"));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dal = sdf.parse(rs.getString("DAL"));
		Date al = sdf.parse(rs.getString("AL"));
		Periodo p = new Periodo(dal, al);
		istanzaDaElaborare.getPERIODI().add(p);
	    } else {
		log.error("populateDatiScheda: la query non ha trovato tutti i dati della scheda per l'istanza codiceistanza={},idcomune={} ",
			istanzaDaElaborare.getCODICEISTANZA(), istanzaDaElaborare.getIDCOMUNE());
		throw new Exception("i dati della scheda dinamica non sono completi");
	    }
	    log.info("populateDatiScheda end");
	} catch (Exception e) {
	    log.error("populateDatiScheda", e);
	    throw e;
	} finally {
	    closeConnection(conn, pstmt, rs);
	}
    }

    public void updateIstanzaElaborata(IstanzaDaElaborareDTO istanzaDaElaborare) throws Exception {

	log.info("updateIstanzaElaborata");
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    conn = getConnection();
	    log.debug("updateIstanzaElaborata: query={}", Query.INSERT_ISTANZE_ELABORATE);
	    pstmt = conn.prepareStatement(Query.INSERT_ISTANZE_ELABORATE);
	    pstmt.setString(1, istanzaDaElaborare.getIDCOMUNE());
	    pstmt.setInt(2, Integer.valueOf(istanzaDaElaborare.getCODICEISTANZA()));
	    pstmt.executeUpdate();
	    log.info("updateIstanzaElaborata...end");
	} catch (Exception e) {
	    log.error("updateIstanzaElaborata", e);
	    throw e;
	} finally {
	    closeConnection(conn, pstmt, rs);
	}
    }

    private Connection getConnection() throws Exception {

	return getConnection(jdbcProps.getProperty("db.url"), jdbcProps.getProperty("db.user"), jdbcProps.getProperty("db.password"));
    }
}
