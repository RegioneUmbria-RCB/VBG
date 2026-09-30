package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Catasto;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.AutorizzazioniConcessioniIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.EndoIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.EsitoChiamataLista;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.IstanzaLista;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.LocalizzazioneIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.MovimentoIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.NomeValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.OnereIstanza;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.SoggettoCollegatoIstanza;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.NlaHelperService;
import it.gruppoinit.pal.gp.core.service.StcMobileCompatibilityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.types.AltriSoggettiType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.EstremiAttoEstesoType;
import it.init.sigepro.rte.types.FiltriUtenteType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.OneriScadenzeType;
import it.init.sigepro.rte.types.OneriType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.RiferimentoCatastaleType;
import it.init.sigepro.rte.types.StatoPraticaType;

@Service
public class StcMobileCompatibilityServiceImpl implements StcMobileCompatibilityService {

    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private NlaHelperService nlaHelperService;

    @Override
    public EsitoChiamataLista<IstanzaLista> findListaIstanzeByParams(String cfUtente, String civico, String indirizzo, String numeroIstanza,
	    String numeroProtocollo, String stato, String dalladata, String alladata, String comune, String nominativoAnagrafe,
	    Integer annoProtocollo, Integer codiceStradario, String tipoCatasto, String foglio, String particella, String sub, Integer firstResult,
	    Integer maxResults) {

	EsitoChiamataLista<IstanzaLista> result = new EsitoChiamataLista<IstanzaLista>();
	IstanzeFilter filter = populateFilter(cfUtente, civico, indirizzo, numeroIstanza, numeroProtocollo, stato, dalladata, alladata, comune,
		nominativoAnagrafe, annoProtocollo, codiceStradario, tipoCatasto, foglio, particella, sub);
	int c = istanzeService.countIstanzeListHelperByFilter(filter);
	result.setNumero_record(c);
	List<IstanzaLista> listaIstanze = null;
	if (c > 0) {
	    List<IstanzeListHelper> lista = istanzeService.findIstanzeListHelperByFilter(filter, firstResult, maxResults);
	    listaIstanze = dtoIstanzaLista(lista, ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware());
	} else {
	    listaIstanze = new ArrayList<IstanzaLista>(0);
	}
	result.setLista(listaIstanze);
	return result;
    }

    private IstanzeFilter populateFilter(String cfUtente, String civico, String indirizzo, String numeroIstanza, String numeroProtocollo,
	    String stato, String dalladata, String alladata, String comune, String referente, Integer annoProtocollo, Integer codiceStradario,
	    String tipoCatasto, String foglio, String particella, String sub) {

	IstanzeFilter istanzeFilter = new IstanzeFilter();
	istanzeFilter.setDefaultWhereCondition(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	istanzeFilter.setFlagEscludiRisultatiDaRicercaPubblica(Boolean.TRUE);
	if (StringUtils.isNotBlank(cfUtente)) {
	    FiltriUtenteType filtriUtente = new FiltriUtenteType();
	    filtriUtente.setCodiceFiscale(cfUtente);
	    filtriUtente.setCercaComeAziendaRichiedente(Boolean.TRUE);
	    filtriUtente.setCercaComeIntermediario(Boolean.TRUE);
	    filtriUtente.setCercaComeRichiedente(Boolean.TRUE);
	    filtriUtente.setCercaNeiSoggettiCollegati(Boolean.TRUE);
	    if (filtriUtente != null) {
		String cf = filtriUtente.getCodiceFiscale();
		boolean searchAll = true;
		if (BooleanUtils.isTrue(filtriUtente.isCercaComeRichiedente())) {
		    istanzeFilter.getSoggettiIstanzaFilterCF().setRichiedenteCF(cf);
		    searchAll = false;
		}
		if (BooleanUtils.isTrue(filtriUtente.isCercaComeAziendaRichiedente())) {
		    istanzeFilter.getSoggettiIstanzaFilterCF().setTitolarelegaleCF(cf);
		    searchAll = false;
		}
		if (BooleanUtils.isTrue(filtriUtente.isCercaComeIntermediario())) {
		    istanzeFilter.getSoggettiIstanzaFilterCF().setProfessionistaCF(cf);
		    searchAll = false;
		}
		if (BooleanUtils.isTrue(filtriUtente.isCercaNeiSoggettiCollegati())) {
		    istanzeFilter.getSoggettiIstanzaFilterCF().setIstanzerichiedentisCF(cf);
		    searchAll = false;
		}
		if (searchAll) {
		    istanzeFilter.getSoggettiIstanzaFilterCF().setRichiedenteCF(cf);
		    istanzeFilter.getSoggettiIstanzaFilterCF().setTitolarelegaleCF(cf);
		    istanzeFilter.getSoggettiIstanzaFilterCF().setProfessionistaCF(cf);
		    istanzeFilter.getSoggettiIstanzaFilterCF().setIstanzerichiedentisCF(cf);
		}
	    }
	}
	if (StringUtils.isNotBlank(comune)) {
	    Comuni comuni = new Comuni();
	    if (StringUtils.isNotEmpty(comune)) {
		comuni.setCodicecomune(comune);
	    }
	    istanzeFilter.setComune(comuni);
	}
	istanzeFilter.setRicercaNumPraticaConLike(false);
	if (StringUtils.isNotBlank(numeroIstanza)) {
	    istanzeFilter.setNumeroistanza(numeroIstanza);
	}
	Date dallaDataDate = null;
	Date allaDataDate = null;
	if (StringUtils.isNotBlank(dalladata)) {
	    dallaDataDate = Utilities.parseDateString(dalladata, "yyyyMMdd");
	}
	if (StringUtils.isNotBlank(alladata)) {
	    allaDataDate = Utilities.parseDateString(alladata, "yyyyMMdd");
	}
	if (alladata != null) {
	    if (dalladata != null) {
		istanzeFilter.setAllaData(allaDataDate);
		istanzeFilter.setDallaData(dallaDataDate);
	    } else {
		istanzeFilter.setAllaData(allaDataDate);
	    }
	} else {
	    if (dallaDataDate != null) {
		istanzeFilter.setDallaData(dallaDataDate);
	    }
	}
	//OK
	if (StringUtils.isNotBlank(numeroProtocollo)) {
	    istanzeFilter.setCercaprotocolloinmovimenti(true);
	    istanzeFilter.setNumeroprotocollo(numeroProtocollo);
	}
	if (annoProtocollo != null && annoProtocollo.intValue() > 0 && annoProtocollo < 9999) {
	    Calendar protDa = Calendar.getInstance();
	    protDa.set(Calendar.YEAR, annoProtocollo);
	    protDa.set(Calendar.DATE, 1);
	    protDa.set(Calendar.MONTH, 1);
	    protDa.set(Calendar.HOUR, 0);
	    protDa.set(Calendar.MINUTE, 0);
	    protDa.set(Calendar.SECOND, 1);
	    Calendar protA = Calendar.getInstance();
	    protA.set(Calendar.YEAR, annoProtocollo);
	    protA.set(Calendar.DATE, 31);
	    protA.set(Calendar.MONTH, 12);
	    protA.set(Calendar.HOUR, 23);
	    protA.set(Calendar.MINUTE, 59);
	    protA.set(Calendar.SECOND, 59);
	    Date dallaDataprotocolloDate = protDa.getTime();
	    Date allaDataprotocolloDate = protA.getTime();
	    istanzeFilter.setDallaDataProtocollo(dallaDataprotocolloDate);
	    istanzeFilter.setAllaDataProtocollo(allaDataprotocolloDate);
	}
	if (StringUtils.isNotBlank(referente)) {
	    istanzeFilter.setSoggettiistanza(referente);
	}
	Istanzestradario istanzestradario = new Istanzestradario();
	///OK
	if (codiceStradario != null) {
	    Stradario d = new Stradario();
	    d.setId(new PkId(codiceStradario));
	    istanzestradario.setStradario(d);
	    istanzeFilter.setCercalocalizzazioneinaltri(true);
	} else {
	    if (StringUtils.isNotBlank(indirizzo)) {
		istanzeFilter.setStradarioDescrizione(indirizzo);
	    }
	}
	//OK
	if (StringUtils.isNotBlank(civico)) {
	    istanzestradario.setCivico(civico);
	}
	istanzeFilter.setIstanzestradario(istanzestradario);
	setIstanzemappaliFilter(tipoCatasto, foglio, particella, sub, istanzeFilter);
	//	//???????????? PERCHé?????????????????????????????
	//	if (StringUtils.isNotBlank(filtriPratica.getCodiceFiscaleRichiedente())) {
	//	    //	    filterPraticaRestriction.addFilterField(FilterUtils.equals("codicefiscale", filtriPratica.getCodiceFiscaleRichiedente(), "richiedente",
	//	    //		    String.class));
	//	}
	if (stato != null) {
	    setStatiistanzaFilter(stato, istanzeFilter);
	}
	//	ft.addRestriction(filterPraticaRestriction);
	// BOCCI 2012-08-31 ORDINAMENTO DEFAULT DATA DESC, NUMEROPRATICA DESC
	istanzeFilter.setOrderBy("data");
	istanzeFilter.setOrderAscDesc(OrderTypeEnum.DESC);
	return istanzeFilter;
    }

    private void setStatiistanzaFilter(String stato, IstanzeFilter istanzeFilter) {

	Statiistanza si = new Statiistanza();
	StatiistanzaId id = new StatiistanzaId();
	si.setId(id);
	if (stato.equals(StatoPraticaType.ATTIVA.value())) {
	    id.setCodicestato("stato_aperte");
	} else if (stato.equals(StatoPraticaType.CHIUSA_NEGATIVAMENTE.value())) {
	    id.setCodicestato("stato_chiuse_negativamente");
	} else if (stato.equals(StatoPraticaType.CHIUSA_POSITIVAMENTE.value())) {
	    id.setCodicestato("stato_chiuse_positivamente");
	} else {
	    id.setCodicestato(stato);
	    id.setIdcomune(ORMHelper.getIdcomuneAlias());
	    id.setSoftware(ORMHelper.getSoftware());
	    si.setId(id);
	}
	if (id.getCodicestato() != null) {
	    istanzeFilter.setChiusura(si);
	}
    }

    private void setIstanzemappaliFilter(String tipoCatasto, String foglio, String particella, String sub, IstanzeFilter istanzeFilter) {

	Istanzemappali istanzemappali = new Istanzemappali();
	if (StringUtils.isNotBlank(tipoCatasto)) {
	    Catasto catasto = new Catasto();
	    String _tipoCatasto = "";
	    if (tipoCatasto.equals("Fabbricati")) {
		_tipoCatasto = "F";
	    } else if (tipoCatasto.equals("Terreni")) {
		_tipoCatasto = "T";
	    } else {
		_tipoCatasto = tipoCatasto;
	    }
	    catasto.setCodice(_tipoCatasto);
	    catasto.setDescrizione(tipoCatasto);
	    istanzemappali.setCatasto(catasto);
	}
	if (StringUtils.isNotBlank(foglio)) {
	    istanzemappali.setFoglio(foglio);
	}
	if (StringUtils.isNotBlank(particella)) {
	    istanzemappali.setParticella(particella);
	}
	if (StringUtils.isNotBlank(sub)) {
	    istanzemappali.setSub(sub);
	}
	istanzeFilter.setIstanzemappali(istanzemappali);
    }

    @Override
    public List<NomeValoreBean> getDatiGeneraliIstanzaByUid(String id) {

	Istanze dp = istanzeService.findByUiid(id);
	List<NomeValoreBean> dg = new ArrayList<NomeValoreBean>();
	dg.add(newNomeValoreVBean("Pratica", dp.getNumeroistanza() + " del " + Utilities.formatDate(dp.getData(), false)));
	if (StringUtils.isNotBlank(dp.getNumeroprotocollo())) {
	    dg.add(newNomeValoreVBean("Protocollo", dp.getNumeroprotocollo() + " del " + Utilities.formatDate(dp.getDataprotocollo(), false)));
	}
	dg.add(newNomeValoreVBean("Intervento",
		StringUtils.defaultString(dp.getAlberoproc().getDescrizioneCompleta(), dp.getAlberoproc().getVwAlberoproc().getScDescrizione())));
	dg.add(newNomeValoreVBean("Oggetto", dp.getLavori()));
	String indirizzoStr = dp.getTransientLocalizzazionePrimario();
	dg.add(newNomeValoreVBean("Indirizzo", indirizzoStr));
	String stato = decodeStatoPratica(dp);
	if (StringUtils.isNotBlank(stato)) {
	    dg.add(newNomeValoreVBean("Stato", stato));
	}
	if (dp.getResponsabileProcedimento() != null) {
	    dg.add(newNomeValoreVBean("Responsabile del procedimento", dp.getResponsabileProcedimento().getResponsabile()));
	}
	if (dp.getIstruttore() != null) {
	    dg.add(newNomeValoreVBean("Istruttore", dp.getIstruttore().getResponsabile()));
	}
	// Per motivi di privacy vengono esclusi dai risultati della ricerca
	//	if (dp.getRichiedente() != null) {
	//	    String richiedente = dp.getRichiedente().getNominativo();
	//	    if (StringUtils.isNotBlank(dp.getRichiedente().getNome())) {
	//		richiedente += " " + dp.getRichiedente().getNome();
	//	    }
	//	    dg.add(newNomeValoreVBean("Richiedente", richiedente));
	//	}
	//	if (dp.getTipisoggetto() != null && StringUtils.isNotBlank(dp.getTipisoggetto().getTiposoggetto())) {
	//	    String tipoSoggetto = dp.getTipisoggetto().getTiposoggetto();
	//	    if (StringUtils.isNotBlank(dp.getDescrsoggetto())) {
	//		tipoSoggetto += " " + tipoSoggetto;
	//	    }
	//	    dg.add(newNomeValoreVBean("In qualita' di", tipoSoggetto));
	//	}
	//	if (dp.getTitolarelegale() != null) {
	//	    String descrizioneAzienda = dp.getTitolarelegale().getNominativo();
	//	    if (StringUtils.isNotBlank(dp.getTitolarelegale().getNome())) {
	//		descrizioneAzienda += " " + dp.getTitolarelegale().getNome();
	//	    }
	//	    if (dp.getTitolarelegale().getFormagiuridica() != null) {
	//		descrizioneAzienda += " " + dp.getTitolarelegale().getFormagiuridica().getFormagiuridica();
	//	    }
	//	    dg.add(newNomeValoreVBean("Azienda Richiedente", descrizioneAzienda));
	//	}
	return dg;
    }

    @Override
    public List<LocalizzazioneIstanza> getLocalizzazioniPraticabyUid(String id) {

	RichiestaPraticaNLAResponse res = null;
	try {
	    res = cercaPratica(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware(), id);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	List<LocalizzazioneIstanza> addrs = new ArrayList<LocalizzazioneIstanza>();
	DettaglioPraticaType dp = res.getDettaglioPratica().getDettaglioPratica();
	ComuneType comuneIstanza = dp.getCodiceComune();
	List<LocalizzazioneNelComuneType> locs = dp.getLocalizzazione();
	for (LocalizzazioneNelComuneType d : locs) {
	    LocalizzazioneIstanza l = new LocalizzazioneIstanza();
	    l = getIndirizzo(d, comuneIstanza);
	    addrs.add(l);
	}
	return addrs;
    }

    @Override
    public List<EndoIstanza> getEndoPraticaByUid(String id) {

	RichiestaPraticaNLAResponse res = null;
	try {
	    res = cercaPratica(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware(), id);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	List<EndoIstanza> endos = new ArrayList<EndoIstanza>();
	DettaglioPraticaType dp = res.getDettaglioPratica().getDettaglioPratica();
	List<ProcedimentoType> ds = dp.getProcedimenti();
	for (ProcedimentoType d : ds) {
	    EndoIstanza nd = new EndoIstanza();
	    nd.setId(d.getCodice());
	    nd.setNome(d.getDescrizione());
	    endos.add(nd);
	}
	return endos;
    }

    @Override
    public List<OnereIstanza> getOneriPraticaByUid(String id) {

	RichiestaPraticaNLAResponse res = null;
	try {
	    res = cercaPratica(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware(), id);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	List<OnereIstanza> oneris = new ArrayList<OnereIstanza>();
	DettaglioPraticaType dp = res.getDettaglioPratica().getDettaglioPratica();
	List<OneriType> ds = dp.getOneri();
	for (OneriType d : ds) {
	    List<OneriScadenzeType> s = d.getScadenze();
	    for (OneriScadenzeType os : s) {
		OnereIstanza nd = new OnereIstanza();
		double importo = os.getImportoRata();
		nd.setImporto("€ " + importo);
		if (d.getCausale() != null) {
		    nd.setNome(d.getCausale().getCausale());
		}
		OneriScadenzeType ss = s.get(0);
		nd.setData(XMLGCToString(ss.getDataScadenza()));
		nd.setNrRata(os.getNumeroRata());
		oneris.add(nd);
	    }
	}
	return oneris;
    }

    @Override
    public List<MovimentoIstanza> getMovimentiPraticaByUid(String id) {

	RichiestaPraticaNLAResponse res = null;
	try {
	    res = cercaPratica(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware(), id);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	List<MovimentoIstanza> movs = new ArrayList<MovimentoIstanza>();
	List<DettaglioAttivitaType> dp = res.getDettaglioPratica().getListaAttivita();
	for (DettaglioAttivitaType d : dp) {
	    MovimentoIstanza nd = new MovimentoIstanza();
	    nd.setId(d.getIdAttivita());
	    nd.setNome(d.getTipoAttivita().getDescrizione());
	    nd.setData(XMLGCToString(d.getDataAttivita()));
	    movs.add(nd);
	}
	return movs;
    }

    @Override
    public List<AutorizzazioniConcessioniIstanza> getAutorizzazioniConcessioniPraticaByUid(String id) {

	RichiestaPraticaNLAResponse res = null;
	try {
	    res = cercaPratica(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware(), id);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	List<AutorizzazioniConcessioniIstanza> addrs = new ArrayList<AutorizzazioniConcessioniIstanza>();
	List<EstremiAttoEstesoType> dp = res.getDettaglioPratica().getListaAtti();
	for (EstremiAttoEstesoType d : dp) {
	    AutorizzazioniConcessioniIstanza aut = new AutorizzazioniConcessioniIstanza();
	    String numero = d.getNumero();
	    if (d.getData() != null) {
		numero += " del " + XMLGCToString(d.getData());
	    }
	    aut.setRegistro(d.getTipoRegistro());
	    aut.setNumero(numero);
	    addrs.add(aut);
	}
	return addrs;
    }

    @Override
    public List<SoggettoCollegatoIstanza> getSoggettiCollegatiPraticaByUId(String id) {

	// Per motivi di privacy vengono esclusi dai risultati della ricerca
	return new ArrayList<SoggettoCollegatoIstanza>(0);
	//	RichiestaPraticaNLAResponse res = null;	
	//	try {
	//	    res = cercaPratica(ORMHelper.getIdcomuneAlias(), ORMHelper.getSoftware(), id);
	//	} catch (Exception e) {
	//	    throw new RuntimeException(e);
	//	}
	//	List<SoggettoCollegatoIstanza> movs = new ArrayList<SoggettoCollegatoIstanza>();
	//	DettaglioPraticaType dp = res.getDettaglioPratica().getDettaglioPratica();
	//	popolaSoggettiIstanza(dp, movs);
	//	List<AltriSoggettiType> soggs = dp.getAltriSoggetti();
	//	for (AltriSoggettiType d : soggs) {
	//	    SoggettoCollegatoIstanza nd = new SoggettoCollegatoIstanza();
	//	    String nome = "";
	//	    if (d.getSoggetto() != null) {
	//		if (d.getSoggetto().getPersonaGiuridica() != null) {
	//		    nome = d.getSoggetto().getPersonaGiuridica().getRagioneSociale();
	//		}
	//		if (d.getSoggetto().getPersonaFisica() != null) {
	//		    nome = d.getSoggetto().getPersonaFisica().getCognome() + " " + d.getSoggetto().getPersonaFisica().getNome();
	//		}
	//	    }
	//	    nd.setNome(nome);
	//	    if (d.getTipoRapporto() != null) {
	//		if (StringUtils.isNotBlank(d.getTipoRapporto().getRuolo())) {
	//		    nd.setQualifica(d.getTipoRapporto().getRuolo());
	//		}
	//	    }
	//	    String nomeAnColl = "";
	//	    if (d.getAnagraficaCollegata() != null) {
	//		if (d.getAnagraficaCollegata().getPersonaGiuridica() != null) {
	//		    nomeAnColl = d.getAnagraficaCollegata().getPersonaGiuridica().getRagioneSociale();
	//		}
	//		if (d.getAnagraficaCollegata().getPersonaFisica() != null) {
	//		    nomeAnColl = d.getAnagraficaCollegata().getPersonaFisica().getCognome() + " " +
	//				 d.getAnagraficaCollegata().getPersonaFisica().getNome();
	//		}
	//	    }
	//	    nd.setSoggettoCollegato(nomeAnColl);
	//	    movs.add(nd);
	//	}
	//	return movs;
    }
    //    private void popolaSoggettiIstanza(DettaglioPraticaType dp, List<SoggettoCollegatoIstanza> movs) {
    //
    //	// RICHIEDENTE
    //	if (dp.getRichiedente() != null) {
    //	    if (dp.getRichiedente().getAnagrafica() != null) {
    //		PersonaFisicaType r = dp.getRichiedente().getAnagrafica();
    //		SoggettoCollegatoIstanza rs = new SoggettoCollegatoIstanza();
    //		rs.setNome(r.getCognome() + " " + StringUtils.defaultString(r.getNome()));
    //		String nome = "";
    //		nome = r.getCognome() + " " + r.getNome();
    //		rs.setNome(nome);
    //		if (dp.getRichiedente().getRuolo() != null) {
    //		    if (StringUtils.isNotBlank(dp.getRichiedente().getRuolo().getRuolo())) {
    //			rs.setQualifica(dp.getRichiedente().getRuolo().getRuolo());
    //		    }
    //		}
    //		// AZIENDA
    //		String nomeAnColl = "";
    //		if (dp.getAziendaRichiedente() != null) {
    //		    if (dp.getAziendaRichiedente() != null) {
    //			nomeAnColl = dp.getAziendaRichiedente().getRagioneSociale();
    //		    }
    //		}
    //		rs.setSoggettoCollegato(nomeAnColl);
    //		movs.add(0, rs);
    //	    }
    //	}
    //	// INTERMEDIARIO
    //	if (dp.getIntermediario() != null) {
    //	    SoggettoCollegatoIstanza rs = new SoggettoCollegatoIstanza();
    //	    String nome = "";
    //	    if (dp.getIntermediario().getPersonaGiuridica() != null) {
    //		nome = dp.getIntermediario().getPersonaGiuridica().getRagioneSociale();
    //	    }
    //	    if (dp.getIntermediario().getPersonaFisica() != null) {
    //		nome = dp.getIntermediario().getPersonaFisica().getCognome() + " " + dp.getIntermediario().getPersonaFisica().getNome();
    //	    }
    //	    rs.setNome(nome);
    //	    rs.setQualifica("Intermediario");
    //	    movs.add(1, rs);
    //	}
    //    }

    private String decodeStatoPratica(Istanze dp) {

	return dp.getChiusura().getStato(); //
    }

    protected NomeValoreBean newNomeValoreVBean(String nome, String valore) {

	NomeValoreBean result = new NomeValoreBean();
	result.setNome(nome);
	result.setValore(valore);
	return result;
    }

    private LocalizzazioneIstanza getIndirizzo(LocalizzazioneNelComuneType l, ComuneType comuneIstanza) {

	if (l == null) {
	    return null;
	}
	LocalizzazioneIstanza ret = new LocalizzazioneIstanza();
	String indirizzo = l.getDenominazione();
	if (StringUtils.isNotBlank(l.getCivico())) {
	    indirizzo += ", " + l.getCivico();
	}
	if (StringUtils.isNotBlank(l.getEsponente())) {
	    indirizzo += "/" + l.getEsponente();
	}
	if (StringUtils.isNotBlank(l.getColore())) {
	    indirizzo += ", " + l.getColore();
	}
	String datiCatastali = null;
	if (l.getRiferimentoCatastale() != null) {
	    if (l.getRiferimentoCatastale().size() > 0) {
		List<RiferimentoCatastaleType> cat = l.getRiferimentoCatastale();
		datiCatastali = "";
		for (RiferimentoCatastaleType rc : cat) {
		    if (StringUtils.isNotBlank(rc.getTipoCatasto())) {
			datiCatastali += "Cat: " + rc.getTipoCatasto();
		    }
		    if (StringUtils.isNotBlank(rc.getSezione())) {
			datiCatastali += ", Sez.: " + rc.getSezione();
		    }
		    if (StringUtils.isNotBlank(rc.getFoglio())) {
			datiCatastali += ", Foglio: " + rc.getFoglio();
		    }
		    if (StringUtils.isNotBlank(rc.getParticella())) {
			datiCatastali += ", Part.: " + rc.getParticella();
		    }
		    if (StringUtils.isNotBlank(rc.getSub())) {
			datiCatastali += ", Sub.: " + rc.getSub();
		    }
		    datiCatastali += "\n";
		}
	    }
	}
	ret.setIndirizzo(indirizzo);
	ret.setDatiCatastali(datiCatastali);
	ret.setNazione("Italia");
	if (comuneIstanza != null) {
	    if (StringUtils.isNotBlank(comuneIstanza.getComune())) {
		ret.setComune(comuneIstanza.getComune());
	    } else if (StringUtils.isNotBlank(comuneIstanza.getCodiceCatastale())) {
		ret.setComune(comuneFromCodiceCatastale(comuneIstanza.getCodiceCatastale()));
	    } else if (StringUtils.isNotBlank(comuneIstanza.getCodiceIstat())) {
		ret.setComune(comuneFromCodiceIstat(comuneIstanza.getCodiceIstat()));
	    }
	}
	return ret;
    }

    private String comuneFromCodiceIstat(String codiceIstat) {

	return "";
    }

    private String comuneFromCodiceCatastale(String codiceCatastale) {

	return codiceCatastale; // COMMENTATO PER VERIFICHE PROBLEMI DI FIRENZE
	// return StringUtils.defaultString(Utilities.getComuneFromCodiceCatastale(codiceCatastale));
    }

    private RichiestaPraticaNLAResponse cercaPratica(String alias, String software, String idpratica) throws Exception {

	Istanze istanza = istanzeService.findByUiid(idpratica);
	RichiestaPraticaNLAResponse response = nlaHelperService.populateRichiestaPraticaNLAResponse(istanza, true, null);
	return response;
    }

    private List<IstanzaLista> dtoIstanzaLista(List<IstanzeListHelper> dettaglioPratica, String alias, String idSportello) {

	List<IstanzaLista> result = new ArrayList<IstanzaLista>();
	for (IstanzeListHelper dp : dettaglioPratica) {
	    IstanzaLista i = new IstanzaLista();
	    i.setAlias(alias);
	    i.setSportello(idSportello);
	    i.setId(dp.getUuid());
	    i.setNumero(dp.getNumeroistanza());
	    i.setNumeroProtocollo(dp.getNumeroprotocollo());
	    if (dp.getDataprotocollo() != null) {
		String datap = Utilities.formatDate(dp.getDataprotocollo(), "dd/MM/yyyy");
		i.setDataProtocollo(datap);
	    }
	    if (dp.getData() != null) {
		String datap = Utilities.formatDate(dp.getData(), "dd/MM/yyyy");
		i.setData(datap);
	    }
	    if (dp.getInterventoproc() != null) {
		i.setIntervento(dp.getInterventoproc());
	    }
	    if (dp.getTransientDescrizioneLocalizzazione() != null) {
		LocalizzazioneIstanza indirizzo = null;
		indirizzo = getIndirizzo(dp);
		i.setIndirizzo(indirizzo);
	    }
	    // Per motivi di privacy vengono esclusi dai risultati della ricerca
	    // i.setRichiedente(getRichiedente(dp));
	    //i.setRuolo(getRuolo(dp));
	    // i.setAzienda(getAzienda(dp));
	    result.add(i);
	}
	return result;
    }
    //    private String getAzienda(IstanzeListHelper dp) {
    //
    //	if (StringUtils.isNotBlank(dp.getAziendanominativo())) {
    //	    if (StringUtils.isNotBlank(dp.getAziendanome())) {
    //		return dp.getAziendanominativo() + " " + dp.getAziendanome();
    //	    }
    //	    return dp.getAziendanominativo();
    //	}
    //	return null;
    //    }
    //
    //    private String getRuolo(IstanzeListHelper dp) {
    //
    //	if (StringUtils.isNotBlank(dp.getTiposoggetto())) {
    //	    if (StringUtils.isNotBlank(dp.getDescrizionesoggetto())) {
    //		return dp.getTiposoggetto() + " " + dp.getDescrizionesoggetto();
    //	    }
    //	    return dp.getTiposoggetto();
    //	}
    //	return null;
    //    }
    //
    //    private String getRichiedente(IstanzeListHelper dp) {
    //
    //	if (StringUtils.isNotBlank(dp.getRichiedentenominativo())) {
    //	    if (StringUtils.isNotBlank(dp.getRichiedentenome())) {
    //		return dp.getRichiedentenominativo() + " " + dp.getRichiedentenome();
    //	    }
    //	    return dp.getAziendanominativo();
    //	}
    //	return null;
    //    }

    private LocalizzazioneIstanza getIndirizzo(IstanzeListHelper dp) {

	if (dp.getCodicestradarioprimario() != null) {
	    LocalizzazioneIstanza l = new LocalizzazioneIstanza();
	    if (StringUtils.isNotBlank(dp.getComune())) {
		l.setComune(dp.getComune());
	    }
	    l.setIndirizzo(dp.getTransientDescrizioneLocalizzazione());
	    // l.setDatiCatastali(dc);
	    return l;
	}
	return null;
    }

    protected String XMLGCToString(XMLGregorianCalendar c) {

	if (c != null) {
	    return Utilities.formatDate(c.toGregorianCalendar().getTime(), false);
	}
	return "";
    }
}
