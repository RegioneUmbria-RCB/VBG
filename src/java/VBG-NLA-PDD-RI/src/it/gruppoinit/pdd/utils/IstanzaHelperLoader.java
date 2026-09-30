package it.gruppoinit.pdd.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IstanzaHelperLoader {

    private static Logger log = LoggerFactory.getLogger(IstanzaHelperLoader.class);
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public IstanzaHelperLoader(Connection conn) {

	this.conn = conn;
    }

    public IstanzaHelper popolateIstanza(String idcomune, String dbOwner, int codiceIstanza) {

	IstanzaHelper result = null;
	try {
	    if (log.isDebugEnabled()) {
		log.debug("popolateIstanza# sql= {}", sql.replace("DB_SCHEMA", dbOwner));
	    }
	    pstmt = conn.prepareStatement(sql.replace("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codiceIstanza);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		result = new IstanzaHelper();
		result.setCodiceIstanza(codiceIstanza);
		result.setIdcomune(idcomune);
		result.setNumeroistanza(rs.getString("numeroistanza"));
		result.setSoftware(rs.getString("software"));
		result.setLavori(rs.getString("lavori"));
		result.setLavoriEstesa(rs.getString("lavoriestesa"));
		result.setNumeroprotocollo(rs.getString("numeroprotocollo"));
		result.setDataprotocollo(rs.getDate("dataprotocollo"));
		result.setData(rs.getDate("datapresentazione"));
		result.setIntervento(rs.getString("descrizione_intervento"));
		result.setCodiceIntervento(rs.getInt("codiceIntervento"));
		result.setCodiceInterventoRi(rs.getString("codice_intervento_ri"));
		result.setDomicilioElettronico(rs.getString("domicilio_elettronico"));
		result.setIstComuneCodiceComune(rs.getString("ist_comune_codicecomune"));
		result.setIstComune(rs.getString("ist_comune"));
		result.setIstComuneCodiceCatastale(rs.getString("ist_comune_codcatastale"));
		result.setIstComuneSiglaProvincia(rs.getString("ist_comune_siglaprov"));
		result.setIstComuneProvincia(rs.getString("ist_comune_provincia"));
		result.setProcedura(rs.getString("procedura"));
		result.setCodiceProcedimentoRi(rs.getString("codice_procedimento_ri"));
		result.setCodicePraticaTelematica(rs.getString("ist_codicepraticatelematica"));
		result.setIdDomandaSTC(rs.getString("codicedomandastc"));
		if (StringUtils.isNotBlank(rs.getString("numeroprotocollo_ri"))) {
		    ProtocolloRIHelper pri = new ProtocolloRIHelper();
		    pri.setNumeroProtocolloRI(rs.getString("numeroprotocollo_ri"));
		    pri.setAnno(rs.getString("anno_ri"));
		    pri.setDataProtocollo(rs.getString("dataprotocollo_ri"));
		    pri.setUfficioRI(rs.getString("ufficio_ri"));
		    result.setProtocolloRI(pri);
		}
		if (StringUtils.isNotBlank(rs.getString("rich_nome")) || StringUtils.isNotBlank(rs.getString("rich_cognome"))) {
		    RichiedenteHelper richiedente = new RichiedenteHelper();
		    richiedente.setNome(rs.getString("rich_nome"));
		    richiedente.setCognome(rs.getString("rich_cognome"));
		    richiedente.setCodiceFiscale(rs.getString("rich_cf"));
		    richiedente.setTelefono(rs.getString("rich_telefono"));
		    richiedente.setTelefonocellulare(rs.getString("rich_telefonocellulare"));
		    richiedente.setFax(rs.getString("rich_fax"));
		    richiedente.setEmail(rs.getString("rich_email"));
		    richiedente.setPec(rs.getString("rich_pec"));
		    richiedente.setRichComune(rs.getString("rich_comune"));
		    richiedente.setRichComuneCodiceCatastale(rs.getString("rich_codicecatastale"));
		    richiedente.setRichComuneStato(rs.getString("rich_stato"));
		    richiedente.setRichNascComune(rs.getString("rich_nasc_comune"));
		    richiedente.setRichNascComuneCodiceCatastale(rs.getString("rich_nasc_codicecatastale"));
		    richiedente.setRichNascComuneStato(rs.getString("rich_nasc_stato"));
		    richiedente.setTipoSoggetto(rs.getString("rich_tiposoggetto"));
		    richiedente.setCodiceCarica(rs.getString("rich_codcarica"));
		    richiedente.setCarica(rs.getString("rich_desccarica"));
		    result.setRichiedente(richiedente);
		}
		if (StringUtils.isNotBlank(rs.getString("az_nominativo"))) {
		    ImpresaHelper impresa = new ImpresaHelper();
		    impresa.setDenominazione(rs.getString("az_nominativo"));
		    impresa.setCodiceFormaGiuridicaCCIAA(rs.getString("az_codiceformagiuridica"));
		    impresa.setCodiceFiscale(rs.getString("az_codicefiscale"));
		    impresa.setPartitaIva(rs.getString("az_piva"));
		    impresa.setIndirizzoDenominazione(rs.getString("az_indirizzo"));
		    impresa.setIndirizzoCAP(rs.getString("az_cap"));
		    impresa.setNumREA(rs.getString("az_numiscrrea"));
		    impresa.setProvinciaREA(rs.getString("az_provinciarea"));
		    impresa.setDataIscrizioneREA(rs.getDate("az_dataiscrrea"));
		    impresa.setTelefono(rs.getString("az_telefono"));
		    impresa.setTelefonocellulare(rs.getString("az_telefonocellulare"));
		    impresa.setFax(rs.getString("az_fax"));
		    impresa.setEmail(rs.getString("az_email"));
		    impresa.setPec(rs.getString("az_pec"));
		    impresa.setResidenzaCodComune(rs.getString("az_codicecomune"));
		    impresa.setResidenzaComune(rs.getString("az_comune"));
		    impresa.setResidenzaComuneCodCatastale(rs.getString("az_codicecatastale"));
		    impresa.setResidenzaComuneSiglaProv(rs.getString("az_comune_sigla_prov"));
		    impresa.setResidenzaComuneProv(rs.getString("az_comune_prov"));
		    impresa.setComunecciaa(rs.getString("az_cciaa_comune"));
		    impresa.setNumerocciaa(rs.getString("az_numerocciaa"));
		    impresa.setDatacciaa(rs.getDate("az_data_cciaa"));
		    impresa.setProvinciacciaa(rs.getString("az_cciaa_provincia"));
		    impresa.setSiglaProvinciaCciaa(rs.getString("az_cciaa_siglaprovincia"));
		    impresa.setComuneCciaaCodCatastale(rs.getString("az_cciaa_codicecatastale"));
		    result.setImpresa(impresa);
		    result.setResponsabile(rs.getString("operatore"));
		    result.setResponsabileUserid(rs.getString("operatoreuserid"));
		    result.setResponsabileProcedimento(rs.getString("responsabileproc"));
		    result.setResponsabileProcedimentoUserid(rs.getString("responsabileprocuserid"));
		}
		Utilities.gracefullyReleaseResources(null, pstmt, rs);
		LocalizzazioniIstanzaHelper impianto = loadImpiantoProduttivo(idcomune, codiceIstanza, dbOwner);
		if (impianto != null) {
		    result.setImpianto(impianto);
		}
	    } else {
		Utilities.logAndThrowException("Non è stato possibile trovare risalire alle informazioni dell'istanza [" + idcomune + "," +
					       codiceIstanza + "]. Nessuna istanza trovata",
			getClass());
	    }
	} catch (SQLException e) {
	    if (e.getCause() == null) {
		Utilities.logAndThrowException(
			"Errore durante la query di recupero dati dell'istanza [" + idcomune + "," + codiceIstanza + "] a causa di " + e.getMessage(),
			e, getClass());
	    } else {
		Utilities.logAndThrowException("Errore durante la query di recupero dati dell'istanza [" + idcomune + "," + codiceIstanza +
					       "] a causa di " + e.getCause().getMessage(),
			new RuntimeException(e.getCause()), getClass());
	    }
	} finally {
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	}
	return result;
    }

    private LocalizzazioniIstanzaHelper loadImpiantoProduttivo(String idcomune, int codiceIstanza, String dbOwner) throws SQLException {

	pstmt = conn.prepareStatement(caricaStradarioSQL.replace("DB_SCHEMA", dbOwner));
	pstmt.setString(1, idcomune);
	pstmt.setInt(2, codiceIstanza);
	rs = pstmt.executeQuery();
	LocalizzazioniIstanzaHelper impianto = null;
	if (rs.next()) {
	    impianto = new LocalizzazioniIstanzaHelper();
	    impianto.setPrefisso(rs.getString("prefisso"));
	    impianto.setIndirizzo(rs.getString("descrizione"));
	    impianto.setCivico(rs.getString("civico"));
	    impianto.setCap(rs.getString("cap"));
	    if (StringUtils.isBlank(impianto.getCap())) {
		impianto.setCap(rs.getString("capstradario"));
	    }
	    impianto.setTipoCatasto(rs.getString("codicecatasto"));
	    impianto.setSezione(rs.getString("sezione"));
	    impianto.setFoglio(rs.getString("foglio"));
	    impianto.setParticella(rs.getString("particella"));
	    impianto.setSub(rs.getString("sub"));
	}
	return impianto;
    }

    private String sql = "SELECT" + //
			 "  " + ///*DATI ISTANZA*/
			 "  i.codiceistanza as codiceistanza," + //
			 "  i.numeroistanza as numeroistanza," + //
			 "  i.software as software," + //
			 "  i.lavori as lavori," + //
			 "  i.lavoriestesa as lavoriestesa," + //
			 "  i.numeroprotocollo as numeroprotocollo," + //
			 "  i.dataprotocollo as dataprotocollo," + //
			 "  i.data as datapresentazione," + //
			 "  i.domicilio_elettronico as domicilio_elettronico," + //
			 "  i_comune.codicecomune   AS ist_comune_codicecomune," + //
			 "  i_comune.comune         AS ist_comune," + //
			 "  i_comune.cf             AS ist_comune_codcatastale," + //
			 "  i_comune.siglaprovincia AS ist_comune_siglaprov," + //	  
			 "  i_comune.provincia AS ist_comune_provincia," + //	    
			 "  i.codicepraticatel      AS ist_codicepraticatelematica," + //
			 "  i.domicilio_elettronico as domicilio_elettronico," + //
			 "  i.codiceinterventoproc  as codiceIntervento," + //
			 "  alberoproc.fk_riti_codice     AS codice_intervento_ri," + //
			 "  vwap.sc_descrizione     AS descrizione_intervento," + //	    
			 "  " + ///* ISTANZE_RI */
			 "  iri.anno               AS anno_ri," + //
			 "  iri.numeroprotocollori AS numeroprotocollo_ri," + //
			 "  iri.dataprotocollori   AS dataprotocollo_ri," + //
			 "  iri.ufficiori          AS ufficio_ri," + //
			 "  tp.procedura," + //
			 "  tp.fk_ritp_codice as codice_procedimento_ri," + //
			 "  " + ///*RICHIEDENTE*/
			 "  r.nominativo             AS rich_cognome," + //
			 "  r.nome                   AS rich_nome," + //
			 "  r.codicefiscale          AS rich_cf," + //
			 "  r.telefono               AS rich_telefono," + //
			 "  r.telefonocellulare      AS rich_telefonocellulare," + //
			 "  r.fax                    AS rich_fax," + //
			 "  r.email                  AS rich_email," + //
			 "  r.pec                    AS rich_pec," + //
			 "  rich_c.comune            AS rich_comune," + //
			 "  rich_c.cf                AS rich_codicecatastale," + //
			 "  rich_c.codicestatoestero AS rich_stato ," + //
			 "  rich_c_nasc.comune       AS rich_nasc_comune," + //
			 "  rich_c_nasc.cf           AS rich_nasc_codicecatastale," + //
			 "  rich_c_nasc.codicestatoestero AS rich_nasc_stato ," + //	    
			 "  tsr.tiposoggetto         AS rich_tiposoggetto," + //
			 "  ric_carica.codice        AS rich_codcarica," + //
			 "  ric_carica.descrizione   AS rich_desccarica," + //
			 "  " + ///* AZIENDA */
			 "  az.nominativo             AS az_nominativo," + //
			 "  az.codicefiscale          AS az_codicefiscale," + //
			 "  az.partitaiva             AS az_piva," + //
			 "  az.indirizzo              AS az_indirizzo," + //
			 "  az.cap                    AS az_cap," + //
			 "  az.numiscrrea             AS az_numiscrrea," + //
			 "  az.provinciarea           AS az_provinciarea," + //
			 "  az.dataiscrrea            AS az_dataiscrrea," + //
			 "  az.telefono               AS az_telefono," + //
			 "  az.telefonocellulare      AS az_telefonocellulare," + //
			 "  az.fax                    AS az_fax," + //
			 "  az.email                  AS az_email," + //
			 "  az.pec                    AS az_pec," + //
			 "  az.comuneresidenza        AS az_codicecomune," + //
			 "  az.codcomregditte         AS az_codicecomunecciaa," + //
			 "  az.regditte               AS az_numerocciaa," + //
			 "  az.dataregditte           AS az_data_cciaa," + //
			 "  fga.codicecciaa           AS az_codiceformagiuridica," + //
			 "  az_c.comune               AS az_comune," + //
			 "  az_c.cf                   AS az_codicecatastale," + //
			 "  az_c.siglaprovincia       AS az_comune_sigla_prov," + //
			 "  az_c.provincia            AS az_comune_prov," + //
			 "  az_c.codicestatoestero    AS az_stato," + //
			 "  az_c_cciaa.comune         AS az_cciaa_comune," + //
			 "  az_c_cciaa.siglaprovincia AS az_cciaa_siglaprovincia," + //
			 "  az_c_cciaa.provincia      AS az_cciaa_provincia," + //
			 "  az_c_cciaa.cf             AS az_cciaa_codicecatastale, " + //
			 "  responsabile.responsabile as operatore, " + //
			 "  responsabile.userid as operatoreuserid, " + //
			 "  responsabileproc.responsabile as responsabileproc, " + //
			 "  responsabileproc.userid as responsabileprocuserid, " + //
			 "  domandestc.id_domandamitt as codicedomandastc " + //
			 "FROM DB_SCHEMA.istanze i " + //
			 "INNER JOIN DB_SCHEMA.comuni i_comune " + //
			 "ON i.codicecomune=i_comune.codicecomune " + //
			 "INNER JOIN DB_SCHEMA.alberoproc alberoproc " + //
			 "ON i.idcomune             =alberoproc.idcomune " + //
			 "AND i.codiceinterventoproc=alberoproc.sc_id " + //
			 "INNER JOIN DB_SCHEMA.vw_alberoproc vwap " + //
			 "ON i.idcomune             =vwap.idcomune " + //
			 "AND i.codiceinterventoproc =vwap.sc_id " + //
			 "LEFT OUTER JOIN DB_SCHEMA.istanze_ri iri " + //
			 "ON i.idcomune      =iri.idcomune " + //
			 "AND i.codiceistanza=iri.codiceistanza " + //
			 "INNER JOIN DB_SCHEMA.tipiprocedure tp " + //
			 "ON i.idcomune        =tp.idcomune " + //
			 "AND i.codiceprocedura=tp.codiceprocedura " + //
			 "INNER JOIN DB_SCHEMA.anagrafe r " + //
			 "ON r.idcomune       =i.idcomune " + //
			 "AND r.codiceanagrafe=i.codicerichiedente " + //
			 "LEFT OUTER JOIN DB_SCHEMA.comuni rich_c " + //
			 "ON r.comuneresidenza=rich_c.codicecomune " + //
			 "LEFT OUTER JOIN DB_SCHEMA.comuni rich_c_nasc " + //
			 "ON r.codcomnascita=rich_c_nasc.codicecomune " + //
			 "LEFT JOIN DB_SCHEMA.tipisoggetto tsr " + //
			 "ON tsr.idcomune           =i.idcomune " + //
			 "AND tsr.codicetiposoggetto=i.fkcodicesoggetto " + //
			 "LEFT JOIN DB_SCHEMA.ri_cariche ric_carica " + //
			 "ON ric_carica.codice=tsr.fk_rica_codice " + //
			 "LEFT OUTER JOIN DB_SCHEMA.anagrafe az " + //
			 "ON az.idcomune       =i.idcomune " + //
			 "AND az.codiceanagrafe=i.codicetitolarelegale " + //
			 "LEFT JOIN DB_SCHEMA.formegiuridiche fga " + //
			 "ON az.idcomune       =fga.idcomune " + //
			 "AND az.formagiuridica=fga.codiceformagiuridica " + //
			 "LEFT JOIN DB_SCHEMA.comuni az_c " + //
			 "ON az.comuneresidenza=az_c.codicecomune " + //
			 "LEFT JOIN DB_SCHEMA.comuni az_c_cciaa " + //
			 "ON az.codcomregditte=az_c_cciaa.codicecomune " + //
			 "LEFT JOIN DB_SCHEMA.responsabili responsabile " + //
			 "ON responsabile.idcomune       =i.idcomune " + //
			 "AND responsabile.codiceresponsabile=i.codiceresponsabile " + //
			 "LEFT JOIN DB_SCHEMA.responsabili responsabileproc " + //
			 "ON responsabileproc.idcomune       =i.idcomune " + //
			 "AND responsabileproc.codiceresponsabile=i.codiceresponsabileproc " + //
			 "LEFT JOIN DB_SCHEMA.domandestc domandestc " + //
			 "ON domandestc.idcomune       =i.idcomune " + //
			 "AND domandestc.codiceistanza =i.codiceistanza " + //
			 "WHERE i.idcomune    =? " + //
			 "AND i.codiceistanza =? ";
    private String caricaStradarioSQL = "select stradario.prefisso, stradario.descrizione, stradario.cap as capstradario, " +
					"istanzestradario.codiceistanza, istanzestradario.civico, istanzestradario.cap, istanzemappali.codicecatasto, " +
					"istanzemappali.sezione, istanzemappali.foglio, istanzemappali.particella, istanzemappali.sub from DB_SCHEMA.istanzestradario " +
					"inner join DB_SCHEMA.stradario on istanzestradario.idcomune=stradario.idcomune " +
					"and istanzestradario.CODICESTRADARIO=stradario.CODICESTRADARIO " +
					"left join DB_SCHEMA.istanzemappali on istanzestradario.idcomune=istanzemappali.idcomune " +
					"and istanzestradario.ID=istanzemappali.FKIDISTANZESTRADARIO where istanzestradario.idcomune=? " +
					"and istanzestradario.CODICEISTANZA=? " +
					"order by istanzestradario.CODICEISTANZA,istanzestradario.primario desc,  istanzemappali.primario desc ";
}
