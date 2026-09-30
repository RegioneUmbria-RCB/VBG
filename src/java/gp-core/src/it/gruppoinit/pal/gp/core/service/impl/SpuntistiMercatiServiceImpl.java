package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.SpuntistiMercatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.SpuntistiMercati;
import it.gruppoinit.pal.gp.core.domain.helper.SpuntistiMercatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeStoricoService;
import it.gruppoinit.pal.gp.core.service.SpuntistiMercatiService;
import it.gruppoinit.pal.gp.core.service.helper.SpuntistiMercatiHelper;

/**
 * 
 * @author
 */
@Service
public class SpuntistiMercatiServiceImpl extends BaseServiceImpl<SpuntistiMercati, PkId> implements SpuntistiMercatiService {

    private SpuntistiMercatiDAO spuntistimercatiDAO;
    private MercatiService mercatiService;
    private MercatiUsoService mercatiUsoService;
    private AutorizzazioniService autorizzazioniService;
    private IstanzeService istanzeService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    private MercatipresenzeDService mercatipresenzeDService;
    private MercatipresenzeStoricoService mercatipresenzeStoricoService;

    @Autowired
    public void setSpuntistiMercatiDAO(SpuntistiMercatiDAO spuntistimercatiDAO) {

	this.spuntistimercatiDAO = spuntistimercatiDAO;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setMercatiUsoService(MercatiUsoService mercatiUsoService) {

	this.mercatiUsoService = mercatiUsoService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setMercatiConfigurazioneService(MercatiConfigurazioneService mercatiConfigurazioneService) {

	this.mercatiConfigurazioneService = mercatiConfigurazioneService;
    }

    @Autowired
    public void setMercatipresenzeDService(MercatipresenzeDService mercatipresenzeDService) {

	this.mercatipresenzeDService = mercatipresenzeDService;
    }

    @Autowired
    public void setMercatipresenzeStoricoService(MercatipresenzeStoricoService mercatipresenzeStoricoService) {

	this.mercatipresenzeStoricoService = mercatipresenzeStoricoService;
    }

    @Override
    protected Class<SpuntistiMercati> getEntityClass() {

	return SpuntistiMercati.class;
    }

    @Override
    public List<SpuntistiMercati> findAll(Integer firstResult, Integer maxResult) {

	return spuntistimercatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(SpuntistiMercati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    spuntistimercatiDAO.insert(entity);
	}
    }

    @Override
    public SpuntistiMercati findById(PkId id) {

	return spuntistimercatiDAO.findById(id);
    }

    @Override
    public void update(SpuntistiMercati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    spuntistimercatiDAO.update(entity);
	}
    }

    @Override
    public void delete(SpuntistiMercati entity) {

	if (isDeleteAllowed(entity)) {
	    spuntistimercatiDAO.delete(entity);
	}
    }

    private void dataIntegration(SpuntistiMercati entity) {

	if (entity.getFlgAttivo() == null) {
	    entity.setFlgAttivo(true);
	}
	if (entity.getDataRegistrazione() == null) {
	    entity.setDataRegistrazione(new Date());
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(SpuntistiMercati entity) {

	Mercati mercati = mercatiService.bindDomainObject(entity.getMercati(), PkId.class, "id.codice");
	entity.setMercati(mercati);
	MercatiUso mercatiUso = mercatiUsoService.bindDomainObject(entity.getMercatiUso(), PkId.class, "id.codice");
	entity.setMercatiUso(mercatiUso);
	Autorizzazioni autorizzazioni = autorizzazioniService.bindDomainObject(entity.getAutorizzazioni(), PkId.class, "id.codice");
	entity.setAutorizzazioni(autorizzazioni);
	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanze);
    }

    protected boolean isDeleteAllowed(SpuntistiMercati entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }

    @Override
    public List<SpuntistiMercati> findByAutorizzazioni(Integer codiceAutorizzazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAutorizzazione, "autorizzazioni", Integer.class));
	ft.addRestriction(fr);
	return spuntistimercatiDAO.findByFilterTable(ft);
    }

    @Override
    public boolean existRecordPerIstanzaAutMercatoAndUsoAttivo(Integer codiceIstanza, Integer idautorizzazione, Integer codiceMercato,
	    Integer codiceUso) {

	return existRecordPerIstanzaAutMercatoAndUso(codiceIstanza, idautorizzazione, codiceMercato, codiceUso, true);
    }

    @Override
    public boolean existRecordPerIstanzaAutMercatoAndUso(Integer codiceIstanza, Integer idautorizzazione, Integer codiceMercato, Integer codiceUso,
	    Boolean isAttivo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.equals("autorizzazioniId", idautorizzazione, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	if (codiceUso != null) {
	    fr.addFilterField(FilterUtils.equals("mercatiUsoId", codiceUso, Integer.class));
	}
	if (isAttivo != null) {
	    fr.addFilterField(FilterUtils.equals("flgAttivo", isAttivo, Boolean.class));
	}
	ft.addRestriction(fr);
	return spuntistimercatiDAO.existsRecords(ft);
    }

    @Override
    public SpuntistiMercati findAutMercatoAndUso(Integer idautorizzazione, Integer codiceMercato, Integer codiceUso) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	//fr.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.equals("autorizzazioniId", idautorizzazione, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	if (codiceUso != null) {
	    fr.addFilterField(FilterUtils.equals("mercatiUsoId", codiceUso, Integer.class));
	}
	ft.addRestriction(fr);
	List<SpuntistiMercati> l = spuntistimercatiDAO.findByFilterTable(ft);
	if (!l.isEmpty()) {
	    return l.get(0);
	}
	return null;
    }

    @Override
    public List<SpuntistiMercatiHelper> findMercatiRichiestaSpuntaPerIstanza(Integer codiceIstanza, Integer codiceAurorizzazione) {

	// Recupero configurazioni da mercatoconfigurazione per capire quali campi della schede dinamiche
	// indicano mercato ed uso per la richiesta spunta
	List<SpuntistiMercatiHelper> mercatiSpuntistiHelpers = new ArrayList<SpuntistiMercatiHelper>();
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findConfigurazione();
	if (EntityUtils.getNestedProperty(mercatiConfigurazione.getDyn2CampiByFkMCfgMercSpunt(), "id.codice") != null
		&& EntityUtils.getNestedProperty(mercatiConfigurazione.getDyn2CampiByFkMCfgUsoSpunt(), "id.codice") != null) {
	    int coutCampiMercato = istanzedyn2datiService.countByIstanzaAndNomecampo(codiceIstanza,
		    mercatiConfigurazione.getDyn2CampiByFkMCfgMercSpunt().getNomecampo());
	    SpuntistiMercatiHelper mercatiSpuntistiHelper = null;
	    // Recupera mercati per cui si richiede la spunta 
	    for (int i = 0; i < coutCampiMercato; i++) {
		mercatiSpuntistiHelper = new SpuntistiMercatiHelper();
		Istanzedyn2dati istanzedyn2datisMercato = istanzedyn2datiService.findByIstanzaAndNomeCampoAndMolteplicita(codiceIstanza,
			mercatiConfigurazione.getDyn2CampiByFkMCfgMercSpunt().getNomecampo(), i);
		Istanzedyn2dati istanzedyn2datisGiorno = istanzedyn2datiService.findByIstanzaAndNomeCampoAndMolteplicita(codiceIstanza,
			mercatiConfigurazione.getDyn2CampiByFkMCfgUsoSpunt().getNomecampo(), i);
		mercatiSpuntistiHelper.setCodiceMercato(Integer.parseInt(istanzedyn2datisMercato.getValore()));
		mercatiSpuntistiHelper.setMercato(istanzedyn2datisMercato.getValoredecodificato());
		if (istanzedyn2datisGiorno != null) {
		    mercatiSpuntistiHelper.setCodiceUso(Integer.parseInt(istanzedyn2datisGiorno.getValore()));
		    mercatiSpuntistiHelper.setUso(istanzedyn2datisGiorno.getValoredecodificato());
		}
		// Controlle se già è stato associato per questa istanza
		//	    boolean isPresente = this.existRecordPerIstanzaAutMercatoAndUsoAttivo(codiceIstanza, codiceAurorizzazione,
		//		    mercatiSpuntistiHelper.getCodiceMercato(), mercatiSpuntistiHelper.getCodiceUso());
		boolean isPresente = this.existRecordPerIstanzaAutMercatoAndUso(codiceIstanza, codiceAurorizzazione,
			mercatiSpuntistiHelper.getCodiceMercato(), mercatiSpuntistiHelper.getCodiceUso(), null);
		if (!isPresente) {
		    // controllo se c'è già un record per aut e mercato ed uso
		    SpuntistiMercati spuntistiMercati = this.findAutMercatoAndUso(codiceAurorizzazione, mercatiSpuntistiHelper.getCodiceMercato(),
			    mercatiSpuntistiHelper.getCodiceUso());
		    if (spuntistiMercati != null && spuntistiMercati.getFlgAttivo()) {
			mercatiSpuntistiHelper.setSegnalazione(true);
			mercatiSpuntistiHelper.setMessSegnalazione(getMessageFromBundle("label.alert.gia_presente_come_spuntista",
				new Object[] { spuntistiMercati.getIstanze().getNumeroistanza() }));
		    }
		    mercatiSpuntistiHelpers.add(mercatiSpuntistiHelper);
		}
	    }
	}
	return mercatiSpuntistiHelpers;
    }

    @Override
    public void insertSpuntistiMercati(Integer codiceIstanza, Integer idautorizzazione) {

	List<SpuntistiMercatiHelper> mercatiSpuntistiHelpers = this.findMercatiRichiestaSpuntaPerIstanza(codiceIstanza, idautorizzazione);
	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(idautorizzazione));
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	SpuntistiMercati spuntistiMercati = null;
	for (SpuntistiMercatiHelper spuntistiMercatiHelper : mercatiSpuntistiHelpers) {
	    if (!spuntistiMercatiHelper.isSegnalazione()) {
		spuntistiMercati = new SpuntistiMercati();
		spuntistiMercati.setAutorizzazioni(autorizzazioni);
		spuntistiMercati.setIstanze(istanza);
		Mercati mercati = mercatiService.findById(new PkId(spuntistiMercatiHelper.getCodiceMercato()));
		spuntistiMercati.setMercati(mercati);
		if (spuntistiMercatiHelper.getCodiceUso() != null) {
		    MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(spuntistiMercatiHelper.getCodiceUso()));
		    spuntistiMercati.setMercatiUso(mercatiUso);
		}
		//TODO: oppure data odierna??
		Date datavalidita = istanza.getDatavalidita() != null ? istanza.getDatavalidita() : istanza.getData();
		spuntistiMercati.setDataRegistrazione(datavalidita);
		this.insert(spuntistiMercati);
	    }
	}
    }

    @Override
    public List<SpuntistiMercati> findByMercatoEdUso(Integer codiceMercato, Integer codiceUso, Boolean isAttivi, Boolean isDataValidita) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	//fr.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatiUsoId", codiceUso, Integer.class));
	if (isAttivi != null) {
	    fr.addFilterField(FilterUtils.equals("flgAttivo", isAttivi, Boolean.class));
	}
	ft.addOrder(FilterUtils.order("flgAttivo", OrderTypeEnum.DESC));
	ft.addOrder(FilterUtils.order("dataDisattivazione", OrderTypeEnum.DESC));
	ft.addRestriction(fr);
	List<SpuntistiMercati> listaSpuntisti = spuntistimercatiDAO.findByFilterTable(ft);
	if (isDataValidita) {
	    return listaSpuntisti;
	}
	List<SpuntistiMercati> listaSpuntistiEffettivi = new ArrayList<SpuntistiMercati>();
	for (SpuntistiMercati spuntistiMercati : listaSpuntisti) {
	    if (spuntistiMercati.getAutorizzazioni().getIstanza().getDatavalidita() != null
		    && spuntistiMercati.getAutorizzazioni().getIstanza().getDatavalidita().before(new Date())) {
		listaSpuntistiEffettivi.add(spuntistiMercati);
	    }
	}
	return listaSpuntistiEffettivi;
    }

    @Override
    public Boolean exsitByMercato(Integer codiceMercato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	//fr.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	ft.addRestriction(fr);
	return spuntistimercatiDAO.existsRecords(ft);
    }

    @Override
    public boolean existRecordPerAutMercatoAndUsoAttivo(Integer idAutorizzazione, Integer codiceMercato, Integer codiceUso) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	//fr.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	fr.addFilterField(FilterUtils.equals("autorizzazioniId", idAutorizzazione, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	if (codiceUso != null) {
	    fr.addFilterField(FilterUtils.equals("mercatiUsoId", codiceUso, Integer.class));
	}
	ft.addRestriction(fr);
	return spuntistimercatiDAO.existsRecords(ft);
    }

    @Override
    public List<SpuntistiMercati> findByIstanza(Integer codiceIstanza, Boolean isAttivi) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanze", Integer.class));
	if (isAttivi != null) {
	    fr.addFilterField(FilterUtils.equals("flgAttivo", BooleanUtils.toBoolean(isAttivi), Boolean.class));
	}
	ft.addRestriction(fr);
	return spuntistimercatiDAO.findByFilterTable(ft);
    }

    @Override
    public List<SpuntistiMercatiDTO> findSpuntistaAssenteDa(Integer codiceMercato, Integer codiceUso, Integer giorniDiAssenza) {

	return spuntistimercatiDAO.findSpuntistaAssenteDa(codiceMercato, codiceUso, giorniDiAssenza);
    }

    @Override
    public List<SpuntistiMercatiDTO> findSpuntistaAssenteDaWithDataUltimaPresenza(Integer codiceMercato, Integer codiceUso, Integer giorniDiAssenza) {

	List<SpuntistiMercatiDTO> l = this.findSpuntistaAssenteDa(codiceMercato, codiceUso, giorniDiAssenza);
	for (SpuntistiMercatiDTO spuntistiMercatiDTO : l) {
	    MercatipresenzeD md = mercatipresenzeDService.findUltimaPresenza(codiceMercato, codiceUso,
		    spuntistiMercatiDTO.getIdautorizzazione().intValue());
	    if (md != null) {
		spuntistiMercatiDTO.setDataregistrazionepresenza(md.getMercatiPresenzeT().getDataRegistrazione());
	    }
	}
	return l;
    }

    @Override
    public void updateDisabilitaPerAssenza(Integer codiceMercato, Integer codiceuso, Integer ggAssenzaPermssi) {

	List<SpuntistiMercatiDTO> listDaDisabilitare = this.findSpuntistaAssenteDa(codiceMercato, codiceuso, ggAssenzaPermssi);
	for (SpuntistiMercatiDTO spuntistiMercatiDTO : listDaDisabilitare) {
	    SpuntistiMercati spuntistiMercati = this.findById(new PkId(spuntistiMercatiDTO.getIdspuntistimercati().intValue()));
	    spuntistiMercati.setFlgAttivo(false);
	    spuntistiMercati.setDataDisattivazione(new Date());
	    this.update(spuntistiMercati);
	    mercatipresenzeDService.updateAzzeraPresenzeByAutorizzazioneAndMercato(spuntistiMercatiDTO.getIdautorizzazione().intValue(),
		    codiceMercato, codiceuso);
	    mercatipresenzeStoricoService.updateAzzeraPresenzeStoricheByAutorizzazioneAndMercato(spuntistiMercatiDTO.getIdautorizzazione().intValue(),
		    codiceMercato, codiceuso);
	}
    }

    @Override
    public void disabilitaPerAssenza(Integer codiceMercato, Integer codiceuso, Integer ggAssenzaPermssi) {

	updateDisabilitaPerAssenza(codiceMercato, codiceuso, ggAssenzaPermssi);
    }

    @Override
    public void updatedisabilitaSpuntistaFieraPerTermine(Integer codiceMercato, Integer codiceuso) {

	List<SpuntistiMercati> listDaDisabilitare = this.findByMercatoEdUso(codiceMercato, codiceuso, true, false);
	for (SpuntistiMercati spuntistiMercati : listDaDisabilitare) {
	    spuntistiMercati.setFlgAttivo(false);
	    spuntistiMercati.setDataDisattivazione(new Date());
	    this.update(spuntistiMercati);
	}
    }
}
