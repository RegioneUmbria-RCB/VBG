package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Permistanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvv;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmSca;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.VwIstanzeOpeRuoli;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchScadenzarioFilterHelper {

    private ComuniassociatiService comuniassociatiService;
    private ResponsabiliService responsabiliService;
    private SoftwareService softwareService;
    private AlberoprocService alberoprocService;
    private StatiistanzaService statiistanzaService;
    private static final Logger log = LoggerFactory.getLogger(BatchScadenzarioFilterHelper.class);

    public BatchScadenzarioFilterHelper(ResponsabiliService responsabiliService, SoftwareService softwareService,
	    AlberoprocService alberoprocService, StatiistanzaService statiistanzaService, ComuniassociatiService comuniassociatiService) {

	super();
	this.responsabiliService = responsabiliService;
	this.softwareService = softwareService;
	this.alberoprocService = alberoprocService;
	this.statiistanzaService = statiistanzaService;
	this.comuniassociatiService = comuniassociatiService;
    }

    public static enum QUERY_PER {
	BATCH_SCADENZARIO, MOVIMENTI_DA_VISIONARE, MOVIMENTI_DA_NOTIFICARE, ISTANZE_EVENTI
    }

    /**
     * 
     * @param filter
     * @return
     */
    public FilterTable getFilterTable(BatchScadenzarioFilter filter, boolean consideraDataScadenza, QUERY_PER queryPer) {

	String istanzePrefix = "istanza";
	String dataScadenzaName = "datascadenza";
	String movimentiPrefix = "";
	switch (queryPer) {
	case BATCH_SCADENZARIO:
	    dataScadenzaName = "datascadenza";
	    break;
	case ISTANZE_EVENTI:
	    dataScadenzaName = "data";
	    istanzePrefix = "istanze";
	    movimentiPrefix = "movimenti";
	    break;
	case MOVIMENTI_DA_VISIONARE:
	case MOVIMENTI_DA_NOTIFICARE:
	    dataScadenzaName = "dataScadenza";
	    break;
	}
	FilterRestriction restriction = new FilterRestriction();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	if (consideraDataScadenza) {
	    FilterRestriction dateRestriction = new FilterRestriction();
	    dateRestriction.setAndOrRestriction(AndOrRestriction.OR);
	    boolean filtraPerData = false;
	    if (filter.getDallaData() != null && filter.getAllaData() != null) {
		// mostrare solo le scadenze dell'intervallo
		filtraPerData = true;
		dateRestriction.addFilterField(FilterUtils.between(dataScadenzaName, filter.getDallaData(), filter.getAllaData(), movimentiPrefix,
			Date.class));
	    } else {
		if (filter.getDallaData() != null) {
		    filtraPerData = true;
		    // mostrare solo le scadenze dalla data in poi
		    dateRestriction.addFilterField(FilterUtils.greaterEqual(dataScadenzaName, filter.getDallaData(), movimentiPrefix, Date.class));
		}
		if (filter.getAllaData() != null) {
		    filtraPerData = true;
		    // mostrare solo le scadenze fino alla data
		    dateRestriction.addFilterField(FilterUtils.smallerEqual(dataScadenzaName, filter.getAllaData(), movimentiPrefix, Date.class));
		}
	    }
	    if (filtraPerData) {
		dateRestriction.addFilterField(FilterUtils.isNull(dataScadenzaName));
		ft.addRestriction(dateRestriction);
	    }
	}
	if (StringUtils.isNotBlank(filter.getNumeroIstanza())) {
	    restriction.addFilterField(FilterUtils.equals("numeroistanza", filter.getNumeroIstanza(), istanzePrefix, String.class));
	}
	boolean isAmministratore = false;
	boolean isAmministratoreSoftware = false;
	boolean isOperatoreSettato = false;
	Responsabili responsabile = null;
	if (filter.getUtenteLoggato().getId().getCodice() != null) {
	    responsabile = responsabiliService.findById(new PkId(filter.getUtenteLoggato().getId().getCodice()));
	    if (responsabile != null) {
		isOperatoreSettato = true;
		isAmministratore = StringUtils.defaultIfEmpty(responsabile.getAmministratore(), "0").equalsIgnoreCase("1") ? true : false;
		isAmministratoreSoftware = StringUtils.defaultIfEmpty(responsabile.getAmministratoresoftware(), "0").equalsIgnoreCase("1") ? true
			: false;
		if (!(isAmministratore || isAmministratoreSoftware)) {
		    FilterRestriction responsabileRestriction = new FilterRestriction();
		    responsabileRestriction.setAndOrRestriction(AndOrRestriction.OR);
		    FilterField<Integer> existsPermistanzeOperatore = new FilterField<Integer>("id.codiceresponsabile", istanzePrefix
			    + ".permistanzes", FieldOperationsEnum.EXISTS, new Integer[] { responsabile.getId().getCodice() }, Permistanze.class);
		    existsPermistanzeOperatore.setExistsChildEntityId("istanze.id");
		    existsPermistanzeOperatore.setExistsParentEntityId(istanzePrefix + ".id");
		    responsabileRestriction.addFilterField(existsPermistanzeOperatore);
		    FilterField<Integer> existsRuoloOperatore = new FilterField<Integer>("id.codiceresponsabile", istanzePrefix
			    + ".vwIstanzeOperatoriRuolis", FieldOperationsEnum.EXISTS, new Integer[] { responsabile.getId().getCodice() },
			    VwIstanzeOpeRuoli.class);
		    existsRuoloOperatore.setExistsChildEntityId("istanza.id");
		    existsRuoloOperatore.setExistsParentEntityId(istanzePrefix + ".id");
		    responsabileRestriction.addFilterField(existsRuoloOperatore);
		    ft.addRestriction(responsabileRestriction);
		}
	    }
	}
	if (EntityUtils.getNestedProperty(filter.getResponsabile(), "id.codice") != null) {
	    FilterRestriction operatoreRestriction = new FilterRestriction();
	    operatoreRestriction.setAndOrRestriction(AndOrRestriction.OR);
	    operatoreRestriction.addFilterField(FilterUtils.equals("responsabileId", filter.getResponsabile().getId().getCodice(), istanzePrefix,
		    Integer.class));
	    operatoreRestriction.addFilterField(FilterUtils.equals("responsabileProcedimentoId", filter.getResponsabile().getId().getCodice(),
		    istanzePrefix, Integer.class));
	    operatoreRestriction.addFilterField(FilterUtils.equals("istruttoreId", filter.getResponsabile().getId().getCodice(), istanzePrefix,
		    Integer.class));
	    ft.addRestriction(operatoreRestriction);
	}
	String filtroSoftware = EntityUtils.getNestedProperty(filter.getScadSoftware(), "codice") == null ? null : filter.getScadSoftware()
		.getCodice();
	if (!StringUtils.defaultIfEmpty(filtroSoftware, WebConstants.SOFTWARE_TT).equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    switch (queryPer) {
	    case BATCH_SCADENZARIO:
		restriction.addFilterField(FilterUtils.equals("software.codice", filter.getScadSoftware().getCodice(), String.class));
		break;
	    case MOVIMENTI_DA_VISIONARE:
	    case MOVIMENTI_DA_NOTIFICARE:
		restriction.addFilterField(FilterUtils.equals("software.codice", filter.getScadSoftware().getCodice(), istanzePrefix, String.class));
		break;
	    case ISTANZE_EVENTI:
		FilterRestriction softwareR = new FilterRestriction();
		softwareR.setAndOrRestriction(AndOrRestriction.OR);
		softwareR.addFilterField(FilterUtils.equals("software.codice", filter.getScadSoftware().getCodice(), istanzePrefix, String.class));
		softwareR.addFilterField(FilterUtils.equals("software.codice", filter.getScadSoftware().getCodice(), "movimenti.istanza",
			String.class));
		ft.addRestriction(softwareR);
		break;
	    }
	} else {
	    if (ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
		// BOCCI 2011-11-16 SE SPECIFICATO L'OPERATORE DEVO RICERCARE NON IN TUTTI I SOFTWARE ATTIVI X IL COMUNE
		// MA IN QUELLI ABILITATI PER L'OPERATORE
		// se la chiamata arriva da TT allora recupero le istanze per tutti i software attivi
		List<Software> softwareAttiviList = new ArrayList<Software>();
		if (isOperatoreSettato && responsabile != null) {
		    softwareAttiviList = softwareService.findSoftwareAbilitati(responsabile);
		} else {
		    softwareAttiviList = softwareService.findSoftwareAttivi(false);
		}
		if (!softwareAttiviList.isEmpty()) {
		    String[] softwareAttivi = new String[softwareAttiviList.size()];
		    for (int i = 0; i < softwareAttiviList.size(); i++) {
			Software softwareAttivo = (Software) softwareAttiviList.get(i);
			softwareAttivi[i] = softwareAttivo.getCodice();
		    }
		    switch (queryPer) {
		    case BATCH_SCADENZARIO:
			restriction.addFilterField(FilterUtils.in("software.codice", softwareAttivi, String.class));
			break;
		    case MOVIMENTI_DA_VISIONARE:
		    case MOVIMENTI_DA_NOTIFICARE:
			restriction.addFilterField(FilterUtils.in("software.codice", softwareAttivi, istanzePrefix, String.class));
			break;
		    case ISTANZE_EVENTI:
			FilterRestriction softwareR = new FilterRestriction();
			softwareR.setAndOrRestriction(AndOrRestriction.OR);
			softwareR.addFilterField(FilterUtils.in("software.codice", softwareAttivi, istanzePrefix, String.class));
			softwareR.addFilterField(FilterUtils.in("software.codice", softwareAttivi, "movimenti.istanza", String.class));
			ft.addRestriction(softwareR);
			break;
		    }
		}
	    } else {
		switch (queryPer) {
		case BATCH_SCADENZARIO:
		    restriction.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), String.class));
		    break;
		case MOVIMENTI_DA_VISIONARE:
		case MOVIMENTI_DA_NOTIFICARE:
		    restriction.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), istanzePrefix, String.class));
		    break;
		case ISTANZE_EVENTI:
		    FilterRestriction softwareR = new FilterRestriction();
		    softwareR.setAndOrRestriction(AndOrRestriction.OR);
		    softwareR.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), istanzePrefix, String.class));
		    softwareR.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), "movimenti.istanza", String.class));
		    ft.addRestriction(softwareR);
		    break;
		}
	    }
	}
	if (filter.getIntervento().getId().getCodice() != null) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(filter.getIntervento().getId().getCodice()));
	    restriction.addFilterField(FilterUtils.startsWith("scCodice", alberoproc.getScCodice(), istanzePrefix + ".alberoproc"));
	}
	switch (queryPer) {
	case BATCH_SCADENZARIO:
	    if (StringUtils.isNotBlank(filter.getTipoMovimentoFatto().getId().getTipomovimento())) {
		restriction.addFilterField(FilterUtils.equals("tipomovimentoId", filter.getTipoMovimentoFatto().getId().getTipomovimento(),
			"movimentofatto", String.class));
	    }
	    if (StringUtils.isNotBlank(filter.getTipoMovimentoDaFare().getId().getTipomovimento())) {
		restriction.addFilterField(FilterUtils.equals("id.tipomovimento", filter.getTipoMovimentoDaFare().getId().getTipomovimento(),
			"tipomovimentodafare", String.class));
	    }
	    break;
	case MOVIMENTI_DA_VISIONARE:
	case MOVIMENTI_DA_NOTIFICARE:
	    if (StringUtils.isNotBlank(filter.getTipoMovimentoFatto().getId().getTipomovimento())) {
		restriction.addFilterField(FilterUtils.equals("tipomovimentoId", filter.getTipoMovimentoFatto().getId().getTipomovimento(),
			String.class));
	    }
	    break;
	}
	if (filter.getSoloScadenzeImportanti() != null) {
	    if (filter.getSoloScadenzeImportanti().booleanValue()) {
		List<String> tmavvs = new ArrayList<String>();
		if (filter.getTipimovimentoAvv() != null) {
		    for (ResponsabiliTmAvv tma : filter.getTipimovimentoAvv()) {
			tmavvs.add(tma.getId().getTipomovimento());
		    }
		}
//		for (Tipimovimento tma : filter.getTipimovimentoAvv()) {
//			tmavvs.add(tma.getId().getTipomovimento());
//		    }
//		}
		List<String> tmscas = new ArrayList<String>();
		if (filter.getTipimovimentoSca() != null) {
		    for (ResponsabiliTmSca tms : filter.getTipimovimentoSca()) {
			tmscas.add(tms.getId().getTipomovimento());
		    }
		}
//		    for (Tipimovimento tms : filter.getTipimovimentoSca()) {
//					tmscas.add(tms.getId().getTipomovimento());
//				    }
//				}
		switch (queryPer) {
		case BATCH_SCADENZARIO:
		    if (!tmscas.isEmpty()) {
			restriction.addFilterField(FilterUtils.in("id.tipomovimento", tmscas.toArray(), "tipomovimentodafare", String.class));
		    }
		    break;
		case MOVIMENTI_DA_VISIONARE:
		    if (!tmavvs.isEmpty()) {
			restriction.addFilterField(FilterUtils.in("tipomovimentoId", tmavvs.toArray(), String.class));
		    }
		    break;
		}
	    }
	}
	List<Statiistanza> statiIstanza = filter.getStatiIstanza();
	if (statiIstanza != null && !statiIstanza.isEmpty()) {
	    String[] stati = new String[statiIstanza.size()];
	    for (int i = 0; i < statiIstanza.size(); i++) {
		Statiistanza statoIstanza = (Statiistanza) statiIstanza.get(i);
		stati[i] = statoIstanza.getId().getCodicestato();
	    }
	    switch (queryPer) {
	    case BATCH_SCADENZARIO:
	    case MOVIMENTI_DA_VISIONARE:
	    case MOVIMENTI_DA_NOTIFICARE:
		restriction.addFilterField(FilterUtils.in("chiusuraId", stati, istanzePrefix, String.class));
		break;
	    case ISTANZE_EVENTI:
		FilterRestriction statiR = new FilterRestriction();
		statiR.setAndOrRestriction(AndOrRestriction.OR);
		statiR.addFilterField(FilterUtils.in("chiusuraId", stati, istanzePrefix, String.class));
		statiR.addFilterField(FilterUtils.in("chiusuraId", stati, "movimenti.istanza", String.class));
		ft.addRestriction(statiR);
		break;
	    }
	}
	ft.addRestriction(restriction);
	if (filter.getScadComportamento() != null) {
	    switch (queryPer) {
	    case BATCH_SCADENZARIO:
	    case MOVIMENTI_DA_VISIONARE:
	    case MOVIMENTI_DA_NOTIFICARE:
		FilterRestriction statiComp = new FilterRestriction();
		if (filter.getScadComportamento().equals(Integer.valueOf(0))) {
		    statiComp
			    .addFilterField(FilterUtils.equals("codcomportamento", 0, istanzePrefix + ".chiusura.staticomportamento", Integer.class));
		} else {
		    statiComp.addFilterField(FilterUtils.in("codcomportamento", new Integer[] { 1, -1 }, istanzePrefix
			    + ".chiusura.staticomportamento", Integer.class));
		}
		ft.addRestriction(statiComp);
		break;
	    case ISTANZE_EVENTI:
		FilterRestriction statiCompR = new FilterRestriction();
		statiCompR.setAndOrRestriction(AndOrRestriction.OR);
		if (filter.getScadComportamento().equals(Integer.valueOf(0))) {
		    statiCompR.addFilterField(FilterUtils
			    .equals("codcomportamento", 0, istanzePrefix + ".chiusura.staticomportamento", Integer.class));
		    statiCompR.addFilterField(FilterUtils.equals("codcomportamento", 0, "movimenti.istanza.chiusura.staticomportamento",
			    Integer.class));
		} else {
		    statiCompR.addFilterField(FilterUtils.in("codcomportamento", new Integer[] { 1, -1 }, istanzePrefix
			    + ".chiusura.staticomportamento", Integer.class));
		    statiCompR.addFilterField(FilterUtils.in("codcomportamento", new Integer[] { 1, -1 },
			    "movimenti.istanza.chiusura.staticomportamento", Integer.class));
		}
		ft.addRestriction(statiCompR);
		break;
	    }
	    //	    List<Statiistanza> statiIstanzaComp = null;
	    //	    // exists su stati istanza con comportamento
	    //	    if (filter.getScadComportamento().equals(Integer.valueOf(0))) {
	    //		statiIstanzaComp = statiistanzaService.findByStatocomportamentoAperte(true);
	    //	    } else {
	    //		statiIstanzaComp = statiistanzaService.findByStatocomportamentoChiuse(true);
	    //	    }
	    //	    if (statiIstanzaComp != null) {
	    //		if (!statiIstanzaComp.isEmpty()) {
	    //		    Set<String> statiDistinct = new HashSet<String>();
	    //		    for (Statiistanza statiistanza2 : statiIstanzaComp) {
	    //			Statiistanza statoIstanza = statiistanza2;
	    //			statiDistinct.add(statoIstanza.getId().getCodicestato());
	    //		    }
	    //		    String[] stati = new String[statiDistinct.size()];
	    //		    stati = statiDistinct.toArray(stati);
	    //		    FilterRestriction statiComp = new FilterRestriction();
	    //		    statiComp.addFilterField(FilterUtils.in("chiusuraId", stati, istanzePrefix, String.class));
	    //		    ft.addRestriction(statiComp);
	    //		}
	    //	    }
	}
	// Controllo se l'istanza è multi comune, nel saco devo aggiungere i filtro per codice comune dell'istanza.I record dovranno essere
	// filtrati per i soli comuni abilitati all'operatore
	if (log.isDebugEnabled()) {
	    log.debug("buildQuery# Controllo se si tratta di un installazione con idcomune {} è multi comune", ORMHelper.getIdcomune());
	}
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (isComuniAssociati) {
	    if (log.isDebugEnabled()) {
		log.debug("buildQuery# E' un installazione multicomune, recupero i comuni configurati per l'operatore {} ({})", new Object[] {
			responsabile.getResponsabile(), responsabile.getId().getCodice() });
	    }
	    List<Responsabilicomuni> responsabilicomunis = comuniassociatiService.checkComuniAbilitatiPerResponsabile(false);
	    if (responsabilicomunis != null && !responsabilicomunis.isEmpty()) {
		String[] codiceComune = new String[responsabilicomunis.size()];
		int i = 0;
		for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
		    codiceComune[i] = responsabilicomuni.getId().getCodicecomune();
		    i++;
		}
		switch (queryPer) {
		//		    case BATCH_SCADENZARIO:
		case MOVIMENTI_DA_VISIONARE:
		case MOVIMENTI_DA_NOTIFICARE:
		    restriction.addFilterField(FilterUtils.in("codicecomune", codiceComune, istanzePrefix+".comune", String.class));
		}
	    }
	}
	//ORDINAMENTI
	if (filter.getOrdinamentoScadenze() != null) {
	    ft.addOrder(FilterUtils.order(dataScadenzaName, filter.getOrdinamentoScadenze()));
	} else {
	    ft.addOrder(FilterUtils.order(dataScadenzaName, OrderTypeEnum.DESC));
	}
	switch (queryPer) {
	case BATCH_SCADENZARIO:
	    ft.addOrder(FilterUtils.order("descrizione", "software", OrderTypeEnum.ASC));
	case MOVIMENTI_DA_VISIONARE:
	case MOVIMENTI_DA_NOTIFICARE:
	case ISTANZE_EVENTI:
	    ft.addOrder(FilterUtils.order("descrizione", istanzePrefix + ".software", OrderTypeEnum.ASC));
	}
	// ft.addOrder(FilterUtils.orderAsc(property, orderByFunction, orderFunctionParams)("numeroistanza", "istanza", OrderTypeEnum.ASC));
	String[] padNumeroistanza = new String[] { "20", "' '" };
	ft.addOrder(FilterUtils.orderAsc("numeroistanza", istanzePrefix, FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
	return ft;
    }
}
