package it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;

import it.gruppoinit.pal.gp.core.dao.IstanzemappaliDAO;
import it.gruppoinit.pal.gp.core.dao.IstanzerichiedentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Catasto;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocedimentiHelper;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzeareeService;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterOrder;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatisoftwareService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;

public class UsefulDataForPlaceholderReplacement implements IUsefulDataForPlaceholderReplacement {

    private MovimentiService movimentiService;
    private AmministrazioniService amministrazioniService;
    private ConfigurazioneService configurazioneservice;
    private IstanzestradarioService istanzeStradarioService;
    private IstanzeareeService istanzeAreeService;
    private IstanzemappaliDAO istanzeMappaliService;
    private AlberoprocService alberoprocService;
    private Dyn2CampiService dynCampiService;
    private ComuniassociatisoftwareService comuniAssociatiSoftwareService;
    private IstanzeprocedimentiService istanzeProcedimentiService;
    private IstanzerichiedentiDAO istanzeRichiedentiDao;
    // private IstanzecollegateService istanzecollegateService;
    // private DomandestcService domandestcService;
    private Istanzedyn2datiService istanzeDynDatiService;

    UsefulDataForPlaceholderReplacement(MovimentiService movimentiService, AmministrazioniService amministrazioniService,
	    ConfigurazioneService configurazioneservice, IstanzestradarioService istanzeStradarioService, IstanzeareeService istanzeAreeService,
	    IstanzemappaliDAO istanzeMappaliService, AlberoprocService alberoprocService, Dyn2CampiService dynCampiService,
	    ComuniassociatisoftwareService comuniAssociatiSoftwareService, IstanzeprocedimentiService istanzeProcedimentiService,
	    IstanzerichiedentiDAO istanzeRichiedentiDao,
	    /*IstanzecollegateService istanzecollegateService, DomandestcService domandestcService,*/ Istanzedyn2datiService istanzeDynDatiService,
	    Istanze istanza, Movimenti movimento) {

	this.movimentiService = movimentiService;
	this.amministrazioniService = amministrazioniService;
	this.configurazioneservice = configurazioneservice;
	this.istanzeStradarioService = istanzeStradarioService;
	this.istanzeAreeService = istanzeAreeService;
	this.istanzeMappaliService = istanzeMappaliService;
	this.alberoprocService = alberoprocService;
	this.dynCampiService = dynCampiService;
	this.comuniAssociatiSoftwareService = comuniAssociatiSoftwareService;
	this.istanzeProcedimentiService = istanzeProcedimentiService;
	this.istanzeRichiedentiDao = istanzeRichiedentiDao;
	//this.istanzecollegateService = istanzecollegateService;
	//this.domandestcService = domandestcService;
	this.istanzeDynDatiService = istanzeDynDatiService;
	this.istanza = istanza;
	this.movimento = movimento;
    }

    private Istanze istanza = null;
    private Movimenti movimento = null;
    private Amministrazioni amministrazione = null;
    private Configurazione configurazione = null;
    private Configurazione configurazioneTT = null;
    private Comuniassociatisoftware comuneAssociatoSW = null;
    private Comuniassociatisoftware comuneAssociatoSWTT = null;
    private Istanzestradario istanzaStradarioPrimaria = null;
    private List<Istanzestradario> istanzeStradario = null;
    //private Stradariocolore stradarioColore = null;
    private Istanzearee istanzaAreaPrimaria = null;
    private Istanzemappali istanzaMappalePrimaria = null;
    private List<Istanzeprocedimenti> endoProcedimenti = null;
    private List<Movimenti> movimentiEseguiti = null;
    private List<Istanzemappali> istanzeMappali = null;
    private List<IstanzeprocedimentiHelper> riepilogoEndo = null;

    @Override
    public Istanze getIstanza() {

	return this.istanza;
    }

    @Override
    public Movimenti getMovimento() {

	return this.movimento;
    }

    @Override
    public Movimenti getMovimentoPerTipo(String codiceTipoMov) {

	Tipimovimento tipoMov = new Tipimovimento();
	tipoMov.setId(new TipimovimentoId(codiceTipoMov));
	return movimentiService.findMovimentiByTipoMovimento(getIstanza().getId().getCodice(), tipoMov.getId().getTipomovimento());
    }

    @Override
    public Amministrazioni getAmministrazione() {

	if (this.amministrazione == null) {
	    this.amministrazione = amministrazioniService.findAmministrazioneSportelloUnico();
	}
	return this.amministrazione;
    }

    @Override
    public Configurazione getConfigurazione() {

	if (this.configurazione == null) {
	    this.configurazione = configurazioneservice.findById(new ConfigurazioneId(ORMHelper.getIdcomune(), ORMHelper.getSoftware()));
	}
	return this.configurazione;
    }

    @Override
    public Configurazione getConfigurazioneTT() {

	if (this.configurazioneTT == null) {
	    this.configurazioneTT = configurazioneservice.findById(new ConfigurazioneId(ORMHelper.getIdcomune(), "TT"));
	}
	return this.configurazioneTT;
    }

    @Override
    public Istanzestradario getIstanzaStradarioPrimaria() {

	if (this.istanzaStradarioPrimaria == null) {
	    if (this.istanzeStradario == null) {
		this.istanzeStradario = istanzeStradarioService.findByIstanza(istanza.getId().getCodice());
	    }
	    if (!this.istanzeStradario.isEmpty()) {
		this.istanzaStradarioPrimaria = this.istanzeStradario.get(0);
	    }
	}
	return this.istanzaStradarioPrimaria;
    }

    @Override
    public Stradario getStradarioPrimario() {

	Stradario s = null;
	Istanzestradario is = getIstanzaStradarioPrimaria();
	if (is != null) {
	    s = is.getStradario();
	}
	return s;
    }

    /**
     * @return the istanzaAreaPrimaria
     */
    @Override
    public Istanzearee getIstanzaAreaPrimaria() {

	if (this.istanzaAreaPrimaria == null) {
	    this.istanzaAreaPrimaria = istanzeAreeService.findByPrimarioIstanza(istanza);
	}
	return this.istanzaAreaPrimaria;
    }

    @Override
    public Istanzemappali getIstanzaMappalePrimaria() {

	if (this.istanzaMappalePrimaria == null) {
	    this.istanzaMappalePrimaria = istanzeMappaliService.findByPrimarioIstanza(istanza);
	}
	return istanzaMappalePrimaria;
    }

    @Override
    public Dyn2Campi getCampoDinamico(Integer codCampo) {

	return dynCampiService.findById(new PkId(codCampo));
    }

    @Override
    public Alberoproc getAlberoproc(Integer codAlberoproc) {

	return alberoprocService.findById(new PkId(codAlberoproc));
    }

    @Override
    public Alberoproc getAlberoprocByScCodice(String sccodice) {

	return alberoprocService.findByScCodice(sccodice);
    }

    @Override
    public Comuniassociatisoftware getDatiComuneassociato() {

	if (this.comuneAssociatoSW == null) {
	    this.comuneAssociatoSW = comuniAssociatiSoftwareService.findByComune(new Comuni(getIstanza().getComune().getCodicecomune()));
	}
	return this.comuneAssociatoSW;
    }

    @Override
    public Comuniassociatisoftware getDatiComuneassociatoTT() {

	if (this.comuneAssociatoSWTT == null) {
	    String currentSW = ORMHelper.getSoftware();
	    ORMHelper.setSoftware("TT");
	    this.comuneAssociatoSWTT = comuniAssociatiSoftwareService.findByComune(new Comuni(getIstanza().getComune().getCodicecomune()));
	    ORMHelper.setSoftware(currentSW);
	}
	return this.comuneAssociatoSWTT;
    }

    @Override
    public List<Istanzedyn2dati> getValoriCampoDinamico(Integer codiceCampoDinamico) {

	List<Istanzedyn2dati> datiDyn = istanzeDynDatiService.findByIstanzaAndDyn2Campi(getIstanza().getId().getCodice(), codiceCampoDinamico);
	return datiDyn;
    }

    /*
    public String getCheckboxInterventoProc(Integer codiceInterventoProc) {
    
    return getIstanza().getAlberoproc().getId().getCodice().equals(codiceInterventoProc) ? CheckboxUtils.getCheckedImg()
    	: CheckboxUtils.getUncheckedImg();
    }
    */
    @Override
    public List<Istanzeprocedimenti> getEndoProcedimenti() {

	if (this.endoProcedimenti == null) {
	    this.endoProcedimenti = istanzeProcedimentiService.findByIstanze(istanza);
	}
	return this.endoProcedimenti;
    }

    @Override
    public List<Istanzeprocedimenti> getEndoProcedimentiAAAA(Boolean attivi, Boolean autorizzativi, Boolean acquisiti, Boolean autocertificabili) {

	List<Istanzeprocedimenti> procs = getEndoProcedimenti();
	List<Istanzeprocedimenti> retProcs = new ArrayList<Istanzeprocedimenti>();
	for (Istanzeprocedimenti proc : procs) {
	    if (attivi != null) {
		if (proc.getInventarioprocedimenti().getDisabilitato() != attivi) {
		    continue;
		}
	    }
	    if (autorizzativi != null) {
		if (proc.getInventarioprocedimenti().getPerprovvedimento() != autorizzativi) {
		    continue;
		}
	    }
	    if (acquisiti != null) {
		if (proc.getAcquisito() != acquisiti) {
		    continue;
		}
	    }
	    if (autocertificabili != null) {
		if ((proc.getInventarioprocedimenti().getNaturaendo().getId().getCodice() == 2) != autorizzativi) {
		    continue;
		}
	    }
	    retProcs.add(proc);
	}
	return retProcs;
    }

    @Override
    public List<Movimenti> getMovimentiEseguiti() {

	if (this.movimentiEseguiti == null) {
	    this.movimentiEseguiti = movimentiService.findEseguitiByIstanza(istanza);
	}
	return this.movimentiEseguiti;
    }

    @Override
    public List<Istanzemappali> getIstanzeMappali() {

	if (this.istanzeMappali == null) {
	    //query per tutte le istanzemappalli dell'istanza ordinate per catasto.descrizione, foglio, particella, sub
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    FilterField<Istanze> ffi = new FilterField<Istanze>("istanza", new Istanze[] { getIstanza() }, Istanzemappali.class);
	    fr.addFilterField(ffi);
	    //ft.addRestriction(fr);
	    //("catasto", new Catasto[] {}, Istanzemappali.class);
	    //fr.addFilterField(ffc);
	    ft.addRestriction(fr);
	    Set<FilterOrder> orderBys = new HashSet<FilterOrder>();
	    FilterField<String> ffs = new FilterField<String>("descrizione", "catasto", new String[] {}, Catasto.class);
	    //FilterField<String> ffs = new FilterField<String>("_catasto.descrizione", new String[] {}, Catasto.class);
	    FilterOrder<String> fo = new FilterOrder<String>(ffs);
	    orderBys.add(fo);
	    ffs = new FilterField<String>("foglio", new String[] {}, Istanzemappali.class);
	    fo = new FilterOrder<String>(ffs);
	    orderBys.add(fo);
	    ffs = new FilterField<String>("particella", new String[] {}, Istanzemappali.class);
	    fo = new FilterOrder<String>(ffs);
	    orderBys.add(fo);
	    ffs = new FilterField<String>("sub", new String[] {}, Istanzemappali.class);
	    fo = new FilterOrder<String>(ffs);
	    orderBys.add(fo);
	    ft.setOrderings(orderBys);
	    this.istanzeMappali = istanzeMappaliService.findByFilterTable(ft);
	}
	return this.istanzeMappali;
    }

    @Override
    public List<Istanzerichiedenti> getIstanzeRichiedenti(Integer codiceTipoSoggetto) {

	//query per tutte le istanzerichiedenti dell'istanza filtrate per tiposoggetto.id.codice
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	FilterField<Istanze> ffi = new FilterField<Istanze>("istanza", new Istanze[] { getIstanza() }, Istanzerichiedenti.class);
	fr.addFilterField(ffi);
	FilterField<Integer> ffci = new FilterField<Integer>("id.codice", new Integer[0], Istanzerichiedenti.class);
	FilterOrder<Integer> fo = new FilterOrder<Integer>(ffci, OrderTypeEnum.DESC);
	ft.addOrder(fo);
	if (codiceTipoSoggetto != null) {
	    //fr = new FilterRestriction();
	    FilterField<Integer> ffcts = new FilterField<Integer>("tiposoggetto.id.codice", new Integer[] { codiceTipoSoggetto },
		    Istanzerichiedenti.class);
	    fr.addFilterField(ffcts);
	}
	ft.addRestriction(fr);
	List<Istanzerichiedenti> irs = istanzeRichiedentiDao.findByFilterTable(ft);
	return irs;
    }

    @Override
    public List<Documentiistanza> getDocumentiIstanza(Boolean necessario, Boolean presente) {

	Set<Documentiistanza> docs = getIstanza().getDocumentiistanzas();
	List<Documentiistanza> retDocs = new ArrayList<Documentiistanza>();
	for (Documentiistanza doc : docs) {
	    if (necessario != null) {
		if (doc.getNecessario() != necessario) {
		    continue;
		}
	    }
	    if (presente != null) {
		if (doc.getPresente() != presente) {
		    continue;
		}
	    }
	    retDocs.add(doc);
	}
	return retDocs;
    }

    @Override
    public List<Documentiistanza> getDocumentiIstanzaValidi(boolean isvalido) {

	Integer _isValido = 0;
	if (isvalido) {
	    _isValido = 1;
	}
	Set<Documentiistanza> docs = getIstanza().getDocumentiistanzas();
	List<Documentiistanza> retDocs = new ArrayList<Documentiistanza>();
	for (Documentiistanza doc : docs) {
	    if (doc.getControllook() != null && doc.getControllook().equals(_isValido)) {
		retDocs.add(doc);
	    }
	}
	return retDocs;
    }

    @Override
    public List<Istanzeallegati> getDocumentiEndo(Boolean presente) {

	Set<Istanzeallegati> docs = getIstanza().getIstanzeallegatis();
	List<Istanzeallegati> retDocs = new ArrayList<Istanzeallegati>();
	if (presente != null) {
	    for (Istanzeallegati doc : docs) {
		if (BooleanUtils.toBoolean(doc.getPresente()) == presente) {
		    retDocs.add(doc);
		}
	    }
	} else {
	    for (Istanzeallegati doc : docs) {
		retDocs.add(doc);
	    }
	}
	return retDocs;
    }

    @Override
    public List<IstanzeprocedimentiHelper> getRiepilogoEndo() {

	if (this.riepilogoEndo == null) {
	    this.riepilogoEndo = istanzeProcedimentiService.findRiepilogoEndo(getIstanza());
	}
	return this.riepilogoEndo;
    }
    /*
    public List<Istanzecollegate> getIstanzeCollegate() {
    
    if (this.istanzecollegates == null) {
        this.istanzecollegates = istanzecollegateService.findIstanzeCollegateByIstanza(getIstanza());
    }
    return this.istanzecollegates;
    }
    
    public List<Domandestc> getDomandeStc() {
    
    if (this.domandestcs == null) {
        this.domandestcs = domandestcService.findByIstanza(getIstanza().getId().getCodice());
    }
    return this.domandestcs;
    }*/
}
