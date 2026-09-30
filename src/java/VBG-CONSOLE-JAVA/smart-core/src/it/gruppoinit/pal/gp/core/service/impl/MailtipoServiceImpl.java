package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MailtipoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ContestiMailTipoEnum;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MailtipoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.LocalizzazioneType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailtipoServiceImpl extends BaseServiceImpl<Mailtipo, PkId> implements MailtipoService {

    private static Logger log = LoggerFactory.getLogger(MailtipoServiceImpl.class);
    private MailtipoDAO mailtipoDAO;
    private AlberoprocService alberoprocService;
    private ConfigurazioneService configurazioneService;
    private ResponsabiliService responsabiliService;
    private ComuniService comuniService;

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setConfigurazioneService(ConfigurazioneService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setMailtipoDAO(MailtipoDAO mailtipoDAO) {

	this.mailtipoDAO = mailtipoDAO;
    }

    @Override
    protected Class<Mailtipo> getEntityClass() {

	return Mailtipo.class;
    }

    @Override
    public void delete(Mailtipo entity) {

	if (isDeleteAllowed(entity)) {
	    mailtipoDAO.delete(entity);
	}
    }

    @Override
    public List<Mailtipo> findAll(Integer firstResult, Integer maxResult) {

	return mailtipoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Mailtipo findById(PkId id) {

	return mailtipoDAO.findById(id);
    }

    @Override
    public void insert(Mailtipo entity) {

	if (validateEntity(entity)) {
	    if (validateInsertOrUpdate(entity)) {
		mailtipoDAO.insert(entity);
	    }
	}
    }

    @Override
    public void update(Mailtipo entity) {

	if (validateEntity(entity)) {
	    if (validateInsertOrUpdate(entity)) {
		mailtipoDAO.update(entity);
	    }
	}
    }

    @Override
    public List<Mailtipo> findByFilter(Mailtipo filter) {

	return mailtipoDAO.findByFilter(filter);
    }

    @Override
    protected boolean isDeleteAllowed(Mailtipo entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getProtocolloRegistris().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "PROTOCOLLO_REGISTRI", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    /**
     * validazione lato service:<br/>
     * se ambito = mail allora sono obbligatori sia oggetto che corpo<br />
     * se ambito = protocollo allora obbligatorio oggetto<br />
     * se ambito = conferenze allora obbligatorio corpo
     */
    private boolean validateInsertOrUpdate(Mailtipo entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getAmbito().equalsIgnoreCase("M")) {
	    if (StringUtils.isBlank(entity.getOggetto())) {
		_ivs.add(new InvalidValue("field.required", Mailtipo.class, "oggetto", entity.getOggetto(), entity));
	    }
	    if (StringUtils.isBlank(entity.getCorpo())) {
		_ivs.add(new InvalidValue("field.required", Mailtipo.class, "corpo", entity.getOggetto(), entity));
	    }
	} else if (entity.getAmbito().equalsIgnoreCase("P")) {
	    if (StringUtils.isBlank(entity.getOggetto())) {
		_ivs.add(new InvalidValue("field.required", Mailtipo.class, "oggetto", entity.getOggetto(), entity));
	    }
	} else if (entity.getAmbito().equalsIgnoreCase("C")) {
	    if (StringUtils.isBlank(entity.getCorpo())) {
		_ivs.add(new InvalidValue("field.required", Mailtipo.class, "corpo", entity.getOggetto(), entity));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    private List<String> getCodici() {

	List<String> codiciList = new ArrayList<String>();
	codiciList.add("");
	String _1 = "[1]";
	codiciList.add(_1);
	String _2 = "[2]";
	codiciList.add(_2);
	String _3 = "[3]";
	codiciList.add(_3);
	String _4 = "[4]";
	codiciList.add(_4);
	String _5 = "[5]";
	codiciList.add(_5);
	String _6 = "[6]";
	codiciList.add(_6);
	String _7 = "[7]";
	codiciList.add(_7);
	String _8 = "[8]";
	codiciList.add(_8);
	String _9 = "[9]";
	codiciList.add(_9);
	String _10 = "[10]";
	codiciList.add(_10);
	String _11 = "[11]";
	codiciList.add(_11);
	String _12 = "[12]";
	codiciList.add(_12);
	String _13 = "[13]";
	codiciList.add(_13);
	String _14 = "[14]";
	codiciList.add(_14);
	String _15 = "[15]";
	codiciList.add(_15);
	String _16 = "[16]";
	codiciList.add(_16);
	String _17 = "[17]";
	codiciList.add(_17);
	String _18 = "[18]";
	codiciList.add(_18);
	String _19 = "[19]";
	codiciList.add(_19);
	String _20 = "[20]";
	codiciList.add(_20);
	String _21 = "[21]";
	codiciList.add(_21);
	String _22 = "[22]";
	codiciList.add(_22);
	String _23 = "[23]";
	codiciList.add(_23);
	String _24 = "[24]";
	codiciList.add(_24);
	String _25 = "[25]";
	codiciList.add(_25);
	String _26 = "[26]";
	codiciList.add(_26);
	String _27 = "[27]";
	codiciList.add(_27);
	String _28 = "[28]";
	codiciList.add(_28);
	String _29 = "[29]";
	codiciList.add(_29);
	String _30 = "[30]";
	codiciList.add(_30);
	String _31 = "[31]";
	codiciList.add(_31);
	String _32 = "[32]";
	codiciList.add(_32);
	String _33 = "[33]";
	codiciList.add(_33);
	String _34 = "[34]";
	codiciList.add(_34);
	String _35 = "[35]";
	codiciList.add(_35);
	String _36 = "[36]";
	codiciList.add(_36);
	String _37 = "[37]";
	codiciList.add(_37);
	String _38 = "[38(";
	codiciList.add(_38);
	String _39 = "[39(";
	codiciList.add(_39);
	String _40 = "[40]";
	codiciList.add(_40);
	String _41 = "[41]";
	codiciList.add(_41);
	String _42 = "[42]";
	codiciList.add(_42);
	String _DATIGEN_DEN = "[DATIGEN_DEN]";
	codiciList.add(_DATIGEN_DEN);
	String _DATISPO_DEN = "[DATISPO_DEN]";
	codiciList.add(_DATISPO_DEN);
	String _AZRIC_DEN = "[AZRIC_DEN]";
	codiciList.add(_AZRIC_DEN);
	String _AZRIC_CF = "[AZRIC_CF]";
	codiciList.add(_AZRIC_CF);
	String _AZRIC_PI = "[AZRIC_PI]";
	codiciList.add(_AZRIC_PI);
	String _CPT = "[CPT]";
	codiciList.add(_CPT);
	String _DATIGEN_COD_ACCR = "[DATIGEN_COD_ACCR]";
	codiciList.add(_DATIGEN_COD_ACCR);
	//codice fiscale del richiedente.
	String _RIC_CF = "[RIC_CF]";
	codiciList.add(_RIC_CF);
	//partita iva del richiedente.
	String _RIC_PIVA = "[RIC_PIVA]";
	codiciList.add(_RIC_PIVA);
	//identificativo univoco del sistema di protocollazione integrato.
	String _MOVIMENTI_FKIDPROTOCOLLO = "[MOVIMENTI_FKIDPROTOCOLLO]";
	codiciList.add(_MOVIMENTI_FKIDPROTOCOLLO);
	//numero del protocollo (senza /anno)
	String _MOVIMENTI_NUMPROT = "[MOVIMENTI_NUMPROT]";
	codiciList.add(_MOVIMENTI_NUMPROT);
	//anno del protocollo
	String _MOVIMENTI_ANNOPROT = "[MOVIMENTI_ANNOPROT]";
	codiciList.add(_MOVIMENTI_ANNOPROT);
	// Dati sulla localizzazione estesa (Indirizzo, civico / esponente colore, se presente km usato al posto del civico)
	String _LOC_ESTESA = "[LOC_ESTESA]";
	codiciList.add(_LOC_ESTESA);
	String _ESPONENTE = "[ESPONENTE]";
	codiciList.add(_ESPONENTE);
	String _COLORE = "[COLORE]";
	codiciList.add(_COLORE);
	String _SCALA = "[SCALA]";
	codiciList.add(_SCALA);
	String _PIANO = "[PIANO]";
	codiciList.add(_PIANO);
	String _INTERNO = "[INTERNO]";
	codiciList.add(_INTERNO);
	String _ESPONENTE_INTERNO = "[ESPONENTE_INTERNO]";
	codiciList.add(_ESPONENTE_INTERNO);
	String _FABBRICATO = "[FABBRICATO]";
	codiciList.add(_FABBRICATO);
	String _KM = "[KM]";
	codiciList.add(_KM);
	String _CAP = "[CAP]";
	codiciList.add(_CAP);
	String _FRAZIONE = "[FRAZIONE]";
	codiciList.add(_FRAZIONE);
	String _CIRCOSCRIZIONE = "[CIRCOSCRIZIONE]";
	codiciList.add(_CIRCOSCRIZIONE);
	String _QUARTIERE = "[QUARTIERE]";
	codiciList.add(_QUARTIERE);
	String _NOTE = "[NOTE]";
	codiciList.add(_NOTE);
	String _COMUNE_ISTANZA = "[COMUNE_ISTANZA]";
	codiciList.add(_COMUNE_ISTANZA);
	String _INQUALITADI = "[INQUALITADI]";
	codiciList.add(_INQUALITADI);
	String _INTERVENTODAALBEROPRIMAVOCE = "[INTERVENTODAALBEROPRIMAVOCE]";
	codiciList.add(_INTERVENTODAALBEROPRIMAVOCE);
	String _MOV_DATAPROT = "[MOV_DATAPROT]";
	codiciList.add(_MOV_DATAPROT);
	codiciList.add("[RIC_CN]");
	codiciList.add("[RIC_DN]");
	/////////////////////////////////////////////////////////////////////////////
	codiciList.add("[MOV_ENDO_ATTONUM]");
	codiciList.add("[MOV_ENDO_ATTODATA]");
	codiciList.add("[MOV_ENDO_ATTOTIPO]");
	codiciList.add("[MOV_ENDO_ATTOENTE]");
	codiciList.add("[MOV_ENDO_ATTONOTE]");
	return codiciList;
    }

    public Mailtipo replaceOggettoCorpo(Mailtipo mailtipo) {

	Mailtipo mailtipoReplace = new Mailtipo();
	String oggetto = mailtipo.getOggetto();
	String corpo = mailtipo.getCorpo();
	String oggettoReplace = "";
	String corpoReplace = "";
	/*
	 * CODICI che devono essere sostituiti
	 */
	List<String> codiciList = getCodici();
	/*
	 * Variabili da sostituire
	 */
	String richiedenteReplace1 = "";
	String indirizzoReplace2 = "";
	String cittaReplace3 = "";
	String capReplace4 = "";
	String provinciaReplace5 = "";
	String dataIstanzaReplace6 = "";
	String nprotocolloReplace7 = "";
	String dataprotocolloReplace8 = "";
	String tipointerventoReplace9 = "";
	String proceduraReplace10 = "";
	String areaReplace11 = "";
	String codicelottoReplace12 = "";
	String lavoriReplace13 = "";
	String foglioReplace14 = "";
	String particellaReplace15 = "";
	String subReplace16 = "";
	String responsabileReplace17 = "";
	String responsabileprocReplace18 = "";
	String inventarioProcListReplace19 = "";
	String numeroistanzaReplace20 = "";
	String impiantoReplace21 = "";
	String istanzestradarioCivicoReplace22 = "";
	String stradarioReplace23 = "";
	String passwordReplace24 = "";
	String flagviaReplace25 = "";
	String varianteprReplace26 = "";
	String dataodiernaReplace27 = "";
	String tecnicoReplace28 = "";
	String soggetticollegatiReplace29 = "";
	String settoriReplace30 = "";
	String attivitaReplace31 = "";
	String movimentoReplace32 = "";
	String inventarioprocedimentiReplace33 = "";
	String numerodataprotocolloReplace34 = "";
	String esitoReplace35 = "";
	String parereReplace36 = "";
	String datamovReplace37 = "";
	String autnumReplace38 = "";
	String autdataReplace39 = "";
	String istpeopleReplace40 = "";
	// Nuove sostituizioni
	String sorteggidettagliodataReplace41 = "";
	String sorteggidettagliodescrReplace42 = "";
	String datiGeneraliDenominazione43 = "";
	String datiSportelloDenominazione44 = "";
	String aziendaRichiedenteDenominazione45 = "";
	String aziendaRichiedenteCF46 = "";
	String aziendaRichiedentePI47 = "";
	String codicepraticatel48 = "";
	String codAccreditamento49 = "";
	//codice fiscale del richiedente.
	String richiedenteCF50 = "";
	//partita iva del richiedente.
	String richiedentePI51 = "";
	//identificativo univoco del sistema di protocollazione integrato.
	String protocolloIdMov52 = "";
	//numero del protocollo (senza /anno)
	String numeroProtocolloMov53 = "";
	//anno del protocollo
	String annoProtocolloMov54 = "";
	// Dati sulla localizzazione estesa
	String localizzazioneEstesa55 = "";
	//Dati dell'istanza : comune dell'istanza
	String comuneIstanza69 = "";
	//Dati dell'istanza : in qualitità di (tipisoggetto.tiposoggetto)
	String tipisoggetto70 = "";
	//Dati dell'istanza : rappresneta il primo livello gerarchico della voce dell'albero scelta
	String nodopadreIntervento71 = "";
	String dataProtocolloMovimento72 = "";
	//	String esponente56 = "";
	//	String colore57 = "";
	//	String scala58 = "";
	//	String piano59 = "";
	//	String interno60 = "";
	//	String esponeneteInterno61 = "";
	//	String fabbricato62 = "";
	//	String km63 = "";
	//	String cap64 = "";
	//	String frazione65 = "";
	//	String circoscrizione66 = "";
	//	String quartiere67 = "";
	//	String note68 = "";
	// Mappa che conterrà i valori relativi alle informazioni della localizzazione dell'istanza
	//// "[ESPONENTE]";[COLORE]";"[SCALA]";"[PIANO]"; "[INTERNO]";"[ESPONENTE_INTERNO]";"[FABBRICATO]";"[KM]";
	// "[CAP]";"[FRAZIONE]";"[CIRCOSCRIZIONE]";"[QUARTIERE]"; "[NOTE]";
	String richiedenteComuneNascita73 = "";
	String richiedenteDataNascita74 = "";
	// Dati del movimento (Endo procedimenti)
	// [MOV_ENDO_ATTONUM],[MOV_ENDO_ATTODATA],[MOV_ENDO_ATTOTIPO],[MOV_ENDO_ATTOENTE],[MOV_ENDO_ATTONOTE]
	String movEndoNumeroAtto75 = "";
	String movEndoDataAtto76 = "";
	String movEndoTipoAtto77 = "";
	String movEndoEnteAtto78 = "";
	String movEndoNoteAtto79 = "";
	Map<String, String> infoLocalizzaizoneIstanza = new HashMap<String, String>();
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	/*
	 * REPLACE OGGETTO
	 */
	oggetto = StringUtils.defaultIfEmpty(oggetto, "");
	oggettoReplace = oggettoReplace.concat(oggetto);
	oggettoReplace = oggettoReplace.replace(codiciList.get(1), StringUtils.defaultIfEmpty(richiedenteReplace1, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(2), StringUtils.defaultIfEmpty(indirizzoReplace2, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(3), StringUtils.defaultIfEmpty(cittaReplace3, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(4), StringUtils.defaultIfEmpty(capReplace4, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(5), StringUtils.defaultIfEmpty(provinciaReplace5, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(6), StringUtils.defaultIfEmpty(dataIstanzaReplace6, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(7), StringUtils.defaultIfEmpty(nprotocolloReplace7, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(8), StringUtils.defaultIfEmpty(dataprotocolloReplace8, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(9), StringUtils.defaultIfEmpty(tipointerventoReplace9, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(10), StringUtils.defaultIfEmpty(proceduraReplace10, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(11), StringUtils.defaultIfEmpty(areaReplace11, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(12), StringUtils.defaultIfEmpty(codicelottoReplace12, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(13), StringUtils.defaultIfEmpty(lavoriReplace13, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(14), StringUtils.defaultIfEmpty(foglioReplace14, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(15), StringUtils.defaultIfEmpty(particellaReplace15, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(16), StringUtils.defaultIfEmpty(subReplace16, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(17), StringUtils.defaultIfEmpty(responsabileReplace17, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(18), StringUtils.defaultIfEmpty(responsabileprocReplace18, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(19), StringUtils.defaultIfEmpty(inventarioProcListReplace19, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(20), StringUtils.defaultIfEmpty(numeroistanzaReplace20, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(21), StringUtils.defaultIfEmpty(impiantoReplace21, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(22), StringUtils.defaultIfEmpty(istanzestradarioCivicoReplace22, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(23), StringUtils.defaultIfEmpty(stradarioReplace23, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(24), StringUtils.defaultIfEmpty(passwordReplace24, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(25), StringUtils.defaultIfEmpty(flagviaReplace25, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(26), StringUtils.defaultIfEmpty(varianteprReplace26, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(27), StringUtils.defaultIfEmpty(dataodiernaReplace27, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(28), StringUtils.defaultIfEmpty(tecnicoReplace28, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(29), StringUtils.defaultIfEmpty(soggetticollegatiReplace29, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(30), StringUtils.defaultIfEmpty(settoriReplace30, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(31), StringUtils.defaultIfEmpty(attivitaReplace31, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(32), StringUtils.defaultIfEmpty(movimentoReplace32, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(33), StringUtils.defaultIfEmpty(inventarioprocedimentiReplace33, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(34), StringUtils.defaultIfEmpty(numerodataprotocolloReplace34, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(35), StringUtils.defaultIfEmpty(esitoReplace35, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(36), StringUtils.defaultIfEmpty(parereReplace36, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(37), StringUtils.defaultIfEmpty(datamovReplace37, ""));
	// [38(codReg)] e [39(codReg)]
	int indice38 = oggetto.indexOf(codiciList.get(38));
	if (indice38 != -1) {
	    indice38 = indice38 + 4;
	    int indiceparentesi = oggetto.indexOf(")]");
	    String codreg = oggetto.substring(indice38, indiceparentesi);
	    Integer codReg = Integer.parseInt(codreg);
	    oggettoReplace = oggettoReplace.replace(codiciList.get(38) + codreg + ")]", "");
	}
	int indice39 = oggetto.indexOf(codiciList.get(39));
	if (indice39 != -1) {
	    indice39 = indice39 + 4;
	    int indiceparentesi = oggetto.indexOf(")]");
	    String codreg = oggetto.substring(indice39, indiceparentesi);
	    Integer codReg = Integer.parseInt(codreg);
	    oggettoReplace = oggettoReplace.replace(codiciList.get(39) + codreg + ")]", "");
	}
	oggettoReplace = oggettoReplace.replace(codiciList.get(40), StringUtils.defaultIfEmpty(istpeopleReplace40, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(41), StringUtils.defaultIfEmpty(sorteggidettagliodataReplace41, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(42), StringUtils.defaultIfEmpty(sorteggidettagliodescrReplace42, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(43), StringUtils.defaultIfEmpty(datiGeneraliDenominazione43, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(44), StringUtils.defaultIfEmpty(datiSportelloDenominazione44, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(45), StringUtils.defaultIfEmpty(aziendaRichiedenteDenominazione45, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(46), StringUtils.defaultIfEmpty(aziendaRichiedenteCF46, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(47), StringUtils.defaultIfEmpty(aziendaRichiedentePI47, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(48), StringUtils.defaultIfEmpty(codicepraticatel48, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(49), StringUtils.defaultIfEmpty(codAccreditamento49, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(50), StringUtils.defaultIfEmpty(richiedenteCF50, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(51), StringUtils.defaultIfEmpty(richiedentePI51, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(52), StringUtils.defaultIfEmpty(protocolloIdMov52, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(53), StringUtils.defaultIfEmpty(numeroProtocolloMov53, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(54), StringUtils.defaultIfEmpty(annoProtocolloMov54, ""));
	// replace nel sezione oggetto del segna posto [LOC_ESTESA] posizione 55 nella lista
	oggettoReplace = oggettoReplace.replace(codiciList.get(55), StringUtils.defaultIfEmpty(localizzazioneEstesa55, ""));
	// replace nel sezione oggetto dei segna posto 
	// [ESPONENTE] 		posizione 56 ;
	// [COLORE] 		posizione 57;
	// [SCALA]		posizione 58;
	// [PIANO]		posizione 59; 
	// [INTERNO]		posizione 60;
	// [ESPONENTE_INTERNO]	posizione 61;
	// [FABBRICATO]		posizione 62;
	// [KM]			posizione 63;
	// [CAP]		posizione 64;
	// [FRAZIONE] 		posizione 65;
	// [CIRCOSCRIZIONE] 	posizione 66;
	// [QUARTIERE] 		posizione 67; 
	// [NOTE]		posizione 68;
	oggettoReplace = oggettoReplace.replace(codiciList.get(56), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("ESPONENTE"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(57), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("COLORE"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(58), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("SCALA"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(59), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("PIANO"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(60), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("INTERNO"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(61),
		StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("ESPONENTE_INTERNO"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(62), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("FABBRICATO"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(63), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("KM"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(64), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("CAP"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(65), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("FRAZIONE"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(66), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("CIRCOSCRIZIONE"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(67), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("QUARTIERE"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(68), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("NOTE"), ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(69), StringUtils.defaultIfEmpty(comuneIstanza69, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(70), StringUtils.defaultIfEmpty(tipisoggetto70, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(71), StringUtils.defaultIfEmpty(nodopadreIntervento71, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(72), StringUtils.defaultIfEmpty(dataProtocolloMovimento72, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(73), StringUtils.defaultIfEmpty(richiedenteComuneNascita73, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(74), StringUtils.defaultIfEmpty(richiedenteDataNascita74, ""));
	// Replace oggetto campi del movimento riferiti all'endo
	oggettoReplace = oggettoReplace.replace(codiciList.get(75), StringUtils.defaultIfEmpty(movEndoNumeroAtto75, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(76), StringUtils.defaultIfEmpty(movEndoDataAtto76, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(77), StringUtils.defaultIfEmpty(movEndoTipoAtto77, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(78), StringUtils.defaultIfEmpty(movEndoEnteAtto78, ""));
	oggettoReplace = oggettoReplace.replace(codiciList.get(79), StringUtils.defaultIfEmpty(movEndoNoteAtto79, ""));
	/*
	 * REPLACE CORPO
	 */
	if (StringUtils.isNotBlank(corpo)) {
	    corpoReplace = corpoReplace.concat(corpo);
	    corpoReplace = corpoReplace.replace(codiciList.get(1), StringUtils.defaultIfEmpty(richiedenteReplace1, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(2), StringUtils.defaultIfEmpty(indirizzoReplace2, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(3), StringUtils.defaultIfEmpty(cittaReplace3, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(4), StringUtils.defaultIfEmpty(capReplace4, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(5), StringUtils.defaultIfEmpty(provinciaReplace5, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(6), StringUtils.defaultIfEmpty(dataIstanzaReplace6, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(7), StringUtils.defaultIfEmpty(nprotocolloReplace7, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(8), StringUtils.defaultIfEmpty(dataprotocolloReplace8, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(9), StringUtils.defaultIfEmpty(tipointerventoReplace9, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(10), StringUtils.defaultIfEmpty(proceduraReplace10, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(11), StringUtils.defaultIfEmpty(areaReplace11, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(12), StringUtils.defaultIfEmpty(codicelottoReplace12, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(13), StringUtils.defaultIfEmpty(lavoriReplace13, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(14), StringUtils.defaultIfEmpty(foglioReplace14, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(15), StringUtils.defaultIfEmpty(particellaReplace15, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(16), StringUtils.defaultIfEmpty(subReplace16, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(17), StringUtils.defaultIfEmpty(responsabileReplace17, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(18), StringUtils.defaultIfEmpty(responsabileprocReplace18, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(19), StringUtils.defaultIfEmpty(inventarioProcListReplace19, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(20), StringUtils.defaultIfEmpty(numeroistanzaReplace20, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(21), StringUtils.defaultIfEmpty(impiantoReplace21, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(22), StringUtils.defaultIfEmpty(istanzestradarioCivicoReplace22, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(23), StringUtils.defaultIfEmpty(stradarioReplace23, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(24), StringUtils.defaultIfEmpty(passwordReplace24, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(25), StringUtils.defaultIfEmpty(flagviaReplace25, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(26), StringUtils.defaultIfEmpty(varianteprReplace26, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(27), StringUtils.defaultIfEmpty(dataodiernaReplace27, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(28), StringUtils.defaultIfEmpty(tecnicoReplace28, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(29), StringUtils.defaultIfEmpty(soggetticollegatiReplace29, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(30), StringUtils.defaultIfEmpty(settoriReplace30, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(31), StringUtils.defaultIfEmpty(attivitaReplace31, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(32), StringUtils.defaultIfEmpty(movimentoReplace32, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(33), StringUtils.defaultIfEmpty(inventarioprocedimentiReplace33, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(34), StringUtils.defaultIfEmpty(numerodataprotocolloReplace34, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(35), StringUtils.defaultIfEmpty(esitoReplace35, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(36), StringUtils.defaultIfEmpty(parereReplace36, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(37), StringUtils.defaultIfEmpty(datamovReplace37, ""));
	    // [38(codReg)] e [39(codReg)]
	    int indice38Corpo = corpo.indexOf(codiciList.get(38));
	    if (indice38Corpo != -1) {
		indice38Corpo = indice38Corpo + 4;
		int indiceparentesi = corpo.indexOf(")]");
		String codreg = corpo.substring(indice38Corpo, indiceparentesi);
		Integer codReg = Integer.parseInt(codreg);
		corpoReplace = corpoReplace.replace(codiciList.get(38) + codreg + ")]", "");
	    }
	    int indice39Corpo = corpo.indexOf(codiciList.get(39));
	    if (indice39Corpo != -1) {
		indice39Corpo = indice39Corpo + 4;
		int indiceparentesi = corpo.indexOf(")]");
		String codreg = corpo.substring(indice39Corpo, indiceparentesi);
		Integer codReg = Integer.parseInt(codreg);
		corpoReplace = corpoReplace.replace(codiciList.get(39) + codreg + ")]", "");
	    }
	    corpoReplace = corpoReplace.replace(codiciList.get(40), StringUtils.defaultIfEmpty(istpeopleReplace40, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(41), StringUtils.defaultIfEmpty(sorteggidettagliodataReplace41, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(42), StringUtils.defaultIfEmpty(sorteggidettagliodescrReplace42, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(43), StringUtils.defaultIfEmpty(datiGeneraliDenominazione43, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(44), StringUtils.defaultIfEmpty(datiSportelloDenominazione44, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(45), StringUtils.defaultIfEmpty(aziendaRichiedenteDenominazione45, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(46), StringUtils.defaultIfEmpty(aziendaRichiedenteCF46, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(47), StringUtils.defaultIfEmpty(aziendaRichiedentePI47, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(48), StringUtils.defaultIfEmpty(codicepraticatel48, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(49), StringUtils.defaultIfEmpty(codAccreditamento49, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(50), StringUtils.defaultIfEmpty(richiedenteCF50, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(51), StringUtils.defaultIfEmpty(richiedentePI51, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(52), StringUtils.defaultIfEmpty(protocolloIdMov52, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(53), StringUtils.defaultIfEmpty(numeroProtocolloMov53, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(54), StringUtils.defaultIfEmpty(annoProtocolloMov54, ""));
	    // replase nel sezione corpo mail del segnaposto [LOC_ESTESA] posizione 55 nella lista
	    corpoReplace = corpoReplace.replace(codiciList.get(55), StringUtils.defaultIfEmpty(localizzazioneEstesa55, ""));
	    // replace nel sezione oggetto dei segna posto 
	    // [ESPONENTE] 		posizione 56 ;
	    // [COLORE] 		posizione 57;
	    // [SCALA]			posizione 58;
	    // [PIANO]			posizione 59; 
	    // [INTERNO]		posizione 60;
	    // [ESPONENTE_INTERNO]	posizione 61;
	    // [FABBRICATO]		posizione 62;
	    // [KM]			posizione 63;
	    // [CAP]			posizione 64;
	    // [FRAZIONE] 		posizione 65;
	    // [CIRCOSCRIZIONE] 	posizione 66;
	    // [QUARTIERE] 		posizione 67; 
	    // [NOTE]			posizione 68;
	    corpoReplace = corpoReplace.replace(codiciList.get(56), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("ESPONENTE"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(57), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("COLORE"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(58), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("SCALA"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(59), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("PIANO"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(60), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("INTERNO"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(61),
		    StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("ESPONENTE_INTERNO"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(62), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("FABBRICATO"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(63), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("KM"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(64), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("CAP"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(65), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("FRAZIONE"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(66), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("CIRCOSCRIZIONE"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(67), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("QUARTIERE"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(68), StringUtils.defaultIfEmpty(infoLocalizzaizoneIstanza.get("NOTE"), ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(69), StringUtils.defaultIfEmpty(comuneIstanza69, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(70), StringUtils.defaultIfEmpty(tipisoggetto70, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(71), StringUtils.defaultIfEmpty(nodopadreIntervento71, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(72), StringUtils.defaultIfEmpty(dataProtocolloMovimento72, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(73), StringUtils.defaultIfEmpty(richiedenteComuneNascita73, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(74), StringUtils.defaultIfEmpty(richiedenteDataNascita74, ""));
	    //replase nella sezione oggetto dei segna posti
	    //[MOV_ENDO_ATTONUM]
	    //[MOV_ENDO_ATTODATA]
	    //[MOV_ENDO_ATTOTIPO]
	    //[MOV_ENDO_ATTOENTE]
	    //[MOV_ENDO_ATTONOTE]
	    corpoReplace = corpoReplace.replace(codiciList.get(75), StringUtils.defaultIfEmpty(movEndoNumeroAtto75, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(76), StringUtils.defaultIfEmpty(movEndoDataAtto76, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(77), StringUtils.defaultIfEmpty(movEndoTipoAtto77, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(78), StringUtils.defaultIfEmpty(movEndoEnteAtto78, ""));
	    corpoReplace = corpoReplace.replace(codiciList.get(79), StringUtils.defaultIfEmpty(movEndoNoteAtto79, ""));
	}
	mailtipoReplace.setCorpo(corpoReplace);
	mailtipoReplace.setOggetto(oggettoReplace);
	return mailtipoReplace;
    }

    /**
     * @see MailtipoDAO#findAllBySoftwareAndTT(ContestiMailTipoEnum contestiMailTipoEnum)
     */
    @Override
    public List<Mailtipo> findAllBySoftwareAndTT(ContestiMailTipoEnum contestiMailTipoEnum) {

	return mailtipoDAO.findAllBySoftwareAndTT(contestiMailTipoEnum);
    }

    @Override
    public String getOggettoProtocollazioneDefault() {

	return "Protocollo dell'istanza numero ";
    }

    @Override
    public String getOggettoFascicolazioneDefault() {

	return "Fascicolo dell'istanza numero ";
    }

    @Override
    public Mailtipo eseguiSostituzioniFrontend(int codicemailtipo, DettaglioPraticaType dp) {

	Mailtipo mail = this.findById(new PkId(codicemailtipo));
	if (mail == null) {
	    log.error("eseguiSostituzioniFrontend# Lettera tipo non trovata {}", new PkId(codicemailtipo));
	    throw new RuntimeException("Lettera tipo non trovata " + new PkId(codicemailtipo));
	}
	if (!"F".equalsIgnoreCase(mail.getAmbito())) {
	    String ambito = getMessageFromBundle("mailtipo.label.ambito.item_frontend", new Object[] {});
	    log.error("eseguiSostituzioniFrontend# La mail recuperata con codice [{}] non appartiene all'ambito [codice: F, descrizione: {}] ",
		    mail.getId(), ambito);
	    throw new RuntimeException("La mail recuperata con codice [" + mail.getId() + "] non appartiene all'ambito [codice: F, descrizione:"
		    + ambito + "] ");
	}
	String oggetto = StringUtils.defaultString(mail.getOggetto());
	String corpo = StringUtils.defaultString(mail.getCorpo());
	if (dp != null) {
	    String dataOdierna = Utilities.getToday(false);
	    oggetto = oggetto.replace("[27]", dataOdierna);
	    corpo = corpo.replace("[27]", dataOdierna);
	    String richiedente = "";
	    String inQualitaDi = "";
	    String cfRichiedente = "";
	    String residenzaRichiedente = "";
	    String cittaRichiedente = "";
	    String capRichiedente = "";
	    String provinciaRichiedente = "";
	    String comuneNascitaRichiedente = "";
	    String dataNascitaRichiedente = "";
	    if (dp.getRichiedente() != null) {
		if (dp.getRichiedente().getRuolo() != null) {
		    if (StringUtils.isNotBlank(dp.getRichiedente().getRuolo().getRuolo())) {
			inQualitaDi = dp.getRichiedente().getRuolo().getRuolo();
		    }
		}
		if (dp.getRichiedente().getAnagrafica() != null) {
		    PersonaFisicaType r = dp.getRichiedente().getAnagrafica();
		    if (StringUtils.isNotBlank(r.getCognome())) {
			richiedente = r.getCognome() + " ";
		    }
		    if (StringUtils.isNotBlank(r.getNome())) {
			richiedente += r.getNome();
		    }
		    if (StringUtils.isNotBlank(r.getCodiceFiscale())) {
			cfRichiedente = r.getCodiceFiscale();
		    }
		    if (r.getResidenza() != null) {
			LocalizzazioneType residenza = r.getResidenza();
			residenzaRichiedente = residenza.getIndirizzo();
			if (StringUtils.isNotBlank(residenza.getCivico())) {
			    residenzaRichiedente += ", civico " + residenza.getCivico();
			}
			if (StringUtils.isNotBlank(residenza.getCap())) {
			    capRichiedente = residenza.getCap();
			}
			if (StringUtils.isNotBlank(residenza.getLocalita())) {
			    cittaRichiedente = residenza.getLocalita();
			}
			if (StringUtils.isNotBlank(residenza.getProvincia())) {
			    provinciaRichiedente = residenza.getProvincia();
			}
			if (r.getDataNascita() != null) {
			    dataNascitaRichiedente = Utilities.formatDate(Utilities.getDate(r.getDataNascita()), false);
			}
			if (r.getComuneNascita() != null) {
			    ComuneType c = r.getComuneNascita();
			    if (StringUtils.isNotBlank(c.getComune())) {
				comuneNascitaRichiedente = c.getComune();
			    } else {
				Comuni comuneDB = null;
				if (StringUtils.isNotBlank(c.getCodiceCatastale())) {
				    comuneDB = comuniService.findById(c.getCodiceCatastale());
				}
				if (StringUtils.isNotBlank(c.getCodiceIstat())) {
				    Comuni filter = new Comuni();
				    filter.setCodiceistat(c.getCodiceIstat());
				    comuneDB = comuniService.findByComune(filter);
				}
				if (comuneDB != null) {
				    comuneNascitaRichiedente = comuneDB.getComune();
				}
			    }
			}
		    }
		}
	    }
	    oggetto = oggetto.replace("[1]", richiedente);
	    corpo = corpo.replace("[1]", richiedente);
	    oggetto = oggetto.replace("[INQUALITADI]", inQualitaDi);
	    corpo = corpo.replace("[INQUALITADI]", inQualitaDi);
	    oggetto = oggetto.replace("[RIC_CF]", cfRichiedente);
	    corpo = corpo.replace("[RIC_CF]", cfRichiedente);
	    oggetto = oggetto.replace("[2]", residenzaRichiedente);
	    corpo = corpo.replace("[2]", residenzaRichiedente);
	    oggetto = oggetto.replace("[3]", cittaRichiedente);
	    corpo = corpo.replace("[3]", cittaRichiedente);
	    oggetto = oggetto.replace("[4]", capRichiedente);
	    corpo = corpo.replace("[4]", capRichiedente);
	    oggetto = oggetto.replace("[5]", provinciaRichiedente);
	    corpo = corpo.replace("[5]", provinciaRichiedente);
	    oggetto = oggetto.replace("[RIC_CN]", comuneNascitaRichiedente);
	    corpo = corpo.replace("[RIC_CN]", comuneNascitaRichiedente);
	    oggetto = oggetto.replace("[RIC_DN]", dataNascitaRichiedente);
	    corpo = corpo.replace("[RIC_DN]", dataNascitaRichiedente);
	    String istanzaData = "";
	    if (dp.getDataPratica() != null) {
		istanzaData = Utilities.formatDate(Utilities.getDate(dp.getDataPratica()), false);
	    }
	    oggetto = oggetto.replace("[6]", istanzaData);
	    corpo = corpo.replace("[6]", istanzaData);
	    String oggettoIstanza = "";
	    if (StringUtils.isNotBlank(dp.getOggetto())) {
		oggettoIstanza = dp.getOggetto();
	    }
	    oggetto = oggetto.replace("[13]", oggettoIstanza);
	    corpo = corpo.replace("[13]", oggettoIstanza);
	    String azienda = "";
	    String aziendaCF = "";
	    if (dp.getAziendaRichiedente() != null) {
		PersonaGiuridicaType pg = dp.getAziendaRichiedente();
		if (StringUtils.isNotBlank(pg.getRagioneSociale())) {
		    azienda = pg.getRagioneSociale();
		}
		if (StringUtils.isNotBlank(pg.getPartitaIva())) {
		    aziendaCF = pg.getPartitaIva();
		}
		if (StringUtils.isNotBlank(pg.getCodiceFiscale())) {
		    aziendaCF = pg.getCodiceFiscale();
		}
	    }
	    oggetto = oggetto.replace("[AZRIC_CF]", aziendaCF);
	    corpo = corpo.replace("[AZRIC_CF]", aziendaCF);
	    oggetto = oggetto.replace("[AZRIC_DEN]", azienda);
	    corpo = corpo.replace("[AZRIC_DEN]", azienda);
	}
	Mailtipo mailtipo = new Mailtipo();
	mailtipo.setOggetto(oggetto);
	mailtipo.setCorpo(corpo);
	return mailtipo;
    }
}
