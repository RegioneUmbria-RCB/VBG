package it.gruppoinit.pal.gp.core.scheduler.task;

import java.io.IOException;
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

public class JdbcREGIONE extends JdbcConnection {

    private static final Logger log = LoggerFactory.getLogger(JdbcREGIONE.class);
    private Properties deployProps = null;
    private Properties jdbcProps = new Properties();

    public JdbcREGIONE(Properties dbProps, Properties deployProps) {

	super(dbProps);
	this.deployProps = deployProps;
	try {
	    jdbcProps.load(JdbcConnection.class.getClassLoader().getResourceAsStream("jdbcREGU.properties"));
	} catch (IOException e) {
	    log.error("errore durante il caricamento dei file di properties jdbcREGU.properties", e);
	    throw new RuntimeException(e);
	}
    }

    public List<IstanzaDaElaborareDTO> getIstanze() throws Exception {

	try {
	    List<IstanzaDaElaborareDTO> istanzaDaElaborareList = getIstanzeDaElaborare();
	    for (IstanzaDaElaborareDTO istanzaDaElaborare : istanzaDaElaborareList) {
		populateDatiScheda(istanzaDaElaborare);
		populateDatiSchedaDate(istanzaDaElaborare);
		populateDatiSchedaMerceologie(istanzaDaElaborare);
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
	    pstmt.setInt(1, Integer.valueOf(deployProps.getProperty("exporter.regione.db.codiceScheda")));
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		IstanzaDaElaborareDTO dto = new IstanzaDaElaborareDTO();
		dto.setIDCOMUNE(rs.getString("IDCOMUNE"));
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

    // 2.1
    private void populateDatiScheda(IstanzaDaElaborareDTO istanzaDaElaborare) throws Exception {

	log.info("populateDatiScheda");
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    conn = getConnection();
	    log.debug("populateDatiScheda: query={}", Query.SELECT_DATI_SCHEDA_FIERE);
	    pstmt = conn.prepareStatement(Query.SELECT_DATI_SCHEDA_FIERE);
	    pstmt.setInt(1, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.classificazione_fg.id")));
	    pstmt.setInt(2, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.classificazione_fs.id")));
	    pstmt.setInt(3, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.classificazione_mm.id")));
	    pstmt.setInt(4, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.classificazione_e.id")));
	    pstmt.setInt(5, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.qualifica_i.id")));
	    pstmt.setInt(6, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.qualifica_r.id")));
	    pstmt.setInt(7, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.qualifica_n.id")));
	    pstmt.setInt(8, Integer.valueOf(istanzaDaElaborare.getCODICEISTANZA()));
	    pstmt.setString(9, istanzaDaElaborare.getIDCOMUNE());
	    pstmt.setInt(10, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.denominazione.id")));
	    pstmt.setInt(11, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.comune.id")));
	    pstmt.setInt(12, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.luogo_svolgimento.id")));
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		String classifFG = rs.getString("CLASSIF_FG");
		String classifFS = rs.getString("CLASSIF_FS");
		String classifMM = rs.getString("CLASSIF_MM");
		String classifE = rs.getString("CLASSIF_E");
		if ("1".equals(classifFG)) {
		    istanzaDaElaborare.setCLASSIFICAZIONE("Fiera generale");
		} else if ("1".equals(classifFS)) {
		    istanzaDaElaborare.setCLASSIFICAZIONE("Fiera specializzata");
		} else if ("1".equals(classifMM)) {
		    istanzaDaElaborare.setCLASSIFICAZIONE("Mostra mercato");
		} else if ("1".equals(classifE)) {
		    istanzaDaElaborare.setCLASSIFICAZIONE("Esposizione");
		}
		String qualifI = rs.getString("QUALIF_I");
		String qualifR = rs.getString("QUALIF_R");
		String qualifN = rs.getString("QUALIF_N");
		if ("1".equals(qualifI)) {
		    istanzaDaElaborare.setQUALIFICA("Internazionale");
		} else if ("1".equals(qualifR)) {
		    istanzaDaElaborare.setQUALIFICA("Regionale");
		} else if ("1".equals(qualifN)) {
		    istanzaDaElaborare.setQUALIFICA("Nazionale");
		}
		istanzaDaElaborare.setDENOMINAZIONE(rs.getString("DENOMIN"));
		istanzaDaElaborare.setCOMUNE_SVOLGIMENTO(rs.getString("COMUNE_SVOLGIMENTO"));
		istanzaDaElaborare.setLUOGO_SVOLGIMENTO(rs.getString("LUOGO_SVOLGIMENTO"));
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

    // 2.2
    private void populateDatiSchedaDate(IstanzaDaElaborareDTO istanzaDaElaborare) throws Exception {

	log.info("populateDatiSchedaDate");
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    conn = getConnection();
	    log.debug("populateDatiSchedaDate: query={}", Query.SELECT_DATI_SCHEDA_FIERE_DAL_AL);
	    pstmt = conn.prepareStatement(Query.SELECT_DATI_SCHEDA_FIERE_DAL_AL);
	    pstmt.setInt(1, Integer.valueOf(istanzaDaElaborare.getCODICEISTANZA()));
	    pstmt.setString(2, istanzaDaElaborare.getIDCOMUNE());
	    pstmt.setInt(3, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.dal.id")));
	    pstmt.setInt(4, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.al.id")));
	    rs = pstmt.executeQuery();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    while (rs.next()) {
		Date dal = sdf.parse(rs.getString("DAL"));
		Date al = sdf.parse(rs.getString("AL"));
		Periodo p = new Periodo(dal, al);
		istanzaDaElaborare.getPERIODI().add(p);
	    }
	    log.info("populateDatiSchedaDate...end");
	} catch (Exception e) {
	    log.error("populateDatiSchedaDate", e);
	    throw e;
	} finally {
	    closeConnection(conn, pstmt, rs);
	}
    }

    // 2.3
    private void populateDatiSchedaMerceologie(IstanzaDaElaborareDTO istanzaDaElaborare) throws Exception {

	log.info("populateDatiSchedaMerceologie");
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    conn = getConnection();
	    log.debug("populateDatiSchedaMerceologie: query={}", Query.SELECT_DATI_SCHEDA_FIERE_MERCEOLOGIE);
	    pstmt = conn.prepareStatement(Query.SELECT_DATI_SCHEDA_FIERE_MERCEOLOGIE);
	    pstmt.setInt(1, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.merceologia_altro.id")));
	    pstmt.setInt(2, Integer.valueOf(istanzaDaElaborare.getCODICEISTANZA()));
	    pstmt.setString(3, istanzaDaElaborare.getIDCOMUNE());
	    pstmt.setInt(4, Integer.valueOf(deployProps.getProperty("exporter.regione.db.dyn2dati.fiere_mostre.merceologia.id")));
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		String m = rs.getString("MERCEOLOGIA");
		String ma = rs.getString("ALTRO");
		if (StringUtils.isNotBlank(ma)) {
		    istanzaDaElaborare.getMERCEOLOGIE().add(ma);
		} else {
		    istanzaDaElaborare.getMERCEOLOGIE().add(m);
		}
	    }
	    log.info("populateDatiSchedaMerceologie...end");
	} catch (Exception e) {
	    log.error("populateDatiSchedaMerceologie", e);
	    throw e;
	} finally {
	    closeConnection(conn, pstmt, rs);
	}
    }

    // 3
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
