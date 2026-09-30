package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiDLivelloServizioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDLivelloServizioDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDLivelloServizioHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MercatiDLivelloServizioService;
import it.gruppoinit.pal.gp.core.service.MercatiLivelloServizioService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class MercatiDLivelloServizioServiceImpl extends BaseServiceImpl<MercatiDLivelloServizio, PkId> implements MercatiDLivelloServizioService {

    private MercatiDLivelloServizioDAO mercatidlivelloservizioDAO;
    private MercatiDService mercatiDService;
    private MercatiUsoService mercatiUsoService;
    private MercatiLivelloServizioService mercatiLivelloServizioService;

    @Autowired
    public void setMercatiLivelloServizioService(MercatiLivelloServizioService mercatiLivelloServizioService) {

	this.mercatiLivelloServizioService = mercatiLivelloServizioService;
    }

    @Autowired
    public void setMercatiDLivelloServizioDAO(MercatiDLivelloServizioDAO mercatidlivelloservizioDAO) {

	this.mercatidlivelloservizioDAO = mercatidlivelloservizioDAO;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setMercatiUsoService(MercatiUsoService mercatiUsoService) {

	this.mercatiUsoService = mercatiUsoService;
    }

    @Override
    protected Class<MercatiDLivelloServizio> getEntityClass() {

	return MercatiDLivelloServizio.class;
    }

    @Override
    public List<MercatiDLivelloServizio> findAll(Integer firstResult, Integer maxResult) {

	return mercatidlivelloservizioDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(MercatiDLivelloServizio entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity) && isInserOrUpdateAllowed(entity)) {
	    mercatidlivelloservizioDAO.insert(entity);
	}
    }

    @Override
    public void insertMultiplo(MercatiDLivelloServizio entity, String[] listacodici) {

	MercatiDLivelloServizio mercatiDLivelloServizioTemp = null;
	MercatiLivelloServizio mls = mercatiLivelloServizioService.findById(new PkId(entity.getMercatiLivelloServizio().getId().getCodice()));
	for (int i = 0; i < listacodici.length; i++) {
	    MercatiD m = null;
	    mercatiDLivelloServizioTemp = new MercatiDLivelloServizio();
	    m = mercatiDService.findById(new PkId(Integer.parseInt(listacodici[i])));
	    mercatiDLivelloServizioTemp.setMercatiD(m);
	    if (entity.getDataFine() != null) {
		mercatiDLivelloServizioTemp.setDataFine(entity.getDataFine());
	    }
	    if (entity.getDataInizio() != null) {
		mercatiDLivelloServizioTemp.setDataInizio(entity.getDataInizio());
	    } else {
		mercatiDLivelloServizioTemp.setDataInizio(mls.getDataInizioValidita());
	    }
	    if (entity.getFattoreMoltiplicativo() != null) {
		mercatiDLivelloServizioTemp.setFattoreMoltiplicativo(entity.getFattoreMoltiplicativo());
	    }
	    if (EntityUtils.getNestedProperty(entity.getMercatiLivelloServizio(), "id.codice") != null) {
		mercatiDLivelloServizioTemp.setMercatiLivelloServizio(mls);
	    }
	    if (entity.getUsaMqPosteggio() != null) {
		mercatiDLivelloServizioTemp.setUsaMqPosteggio(entity.getUsaMqPosteggio());
	    }
	    this.insert(mercatiDLivelloServizioTemp);
	}
    }

    @Override
    public MercatiDLivelloServizio findById(PkId id) {

	return mercatidlivelloservizioDAO.findById(id);
    }

    @Override
    public void update(MercatiDLivelloServizio entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity) && isInserOrUpdateAllowed(entity)) {
	    mercatidlivelloservizioDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatiDLivelloServizio entity) {

	if (isDeleteAllowed(entity)) {
	    mercatidlivelloservizioDAO.delete(entity);
	}
    }

    @Override
    public List<MercatiDLivelloServizioDTO> findByPosteggio(Integer codiceposteggio, boolean attivi, boolean scaduti) {

	return mercatidlivelloservizioDAO.findByPosteggio(codiceposteggio, attivi, scaduti);
    }

    @Override
    public List<MercatiDLivelloServizio> findByPosteggioAndUso(Integer codiceposteggio, Integer uso, boolean attivi, boolean scaduti) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceposteggio, "mercatiD", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", uso, "mercatiLivelloServizio.mercatiUso", Integer.class));
	if (attivi) {
	    fr.addFilterField(FilterUtils.equals("attivo", attivi, "mercatiLivelloServizio", Integer.class));
	}
	if (!scaduti) {
	    Calendar _date = Calendar.getInstance();
	    _date.set(Calendar.HOUR, 23);
	    _date.set(Calendar.MINUTE, 59);
	    _date.set(Calendar.SECOND, 59);
	    FilterRestriction or = new FilterRestriction();
	    or.setAndOrRestriction(AndOrRestriction.OR);
	    or.addFilterField(FilterUtils.isNull("dataFine"));
	    or.addFilterField(FilterUtils.greaterEqual("dataFine", _date, Date.class));
	    ft.addRestriction(or);
	}
	ft.addRestriction(fr);
	return mercatidlivelloservizioDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatiDLivelloServizioHelper> findMercatiDLivelloServizioHelperByPosteggio(Integer codiceposteggio, boolean attivi, boolean scaduti) {

	List<MercatiDLivelloServizioHelper> helpers = new ArrayList<MercatiDLivelloServizioHelper>();
	MercatiDLivelloServizioHelper mercatiDLivelloServizioHelper = null;
	MercatiD mercatiD = mercatiDService.findById(new PkId(codiceposteggio));
	List<MercatiUso> list = mercatiUsoService.findByMercato(mercatiD.getMercati().getId().getCodice());
	for (MercatiUso mercatiUso : list) {
	    mercatiDLivelloServizioHelper = new MercatiDLivelloServizioHelper();
	    mercatiDLivelloServizioHelper.setMercatiUso(mercatiUso);
	    List<MercatiDLivelloServizio> listLivello = this.findByPosteggioAndUso(codiceposteggio, mercatiUso.getId().getCodice(), attivi, scaduti);
	    mercatiDLivelloServizioHelper.setMercatiDLivelloServizios(listLivello);
	    helpers.add(mercatiDLivelloServizioHelper);
	}
	return helpers;
    }

    @Override
    public List<MercatiDLivelloServizio> findByServizio(Integer codiceServizio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceServizio, "mercatiLivelloServizio", Integer.class));
	ft.addRestriction(fr);
	return mercatidlivelloservizioDAO.findByFilterTable(ft);
    }

    private void dataIntegration(MercatiDLivelloServizio entity, boolean isUpdate) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro ComunicazioniT da validare è nullo");
	}
	if (entity.getUsaMqPosteggio() == null) {
	    entity.setUsaMqPosteggio(false);
	}
	if (entity.getFattoreMoltiplicativo() == null) {
	    entity.setFattoreMoltiplicativo(new BigDecimal("1"));
	}
	if (entity.getDataInizio() == null) {
	    entity.setDataInizio(entity.getMercatiLivelloServizio().getDataInizioValidita());
	}
	if (entity.getDataFine() == null) {
	    if (entity.getMercatiLivelloServizio().getDataFineValidita() != null) {
		entity.setDataFine(entity.getMercatiLivelloServizio().getDataFineValidita());
	    }
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(MercatiDLivelloServizio entity) {

	MercatiLivelloServizio fg = mercatiLivelloServizioService.bindDomainObject(entity.getMercatiLivelloServizio(), PkId.class, "id.codice");
	entity.setMercatiLivelloServizio(fg);
    }

    private boolean isInserOrUpdateAllowed(MercatiDLivelloServizio entity) {

	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (entity.getDataFine() != null) {
	    if (entity.getDataInizio().after(entity.getDataFine())) {
		String mess = getMessageFromBundle("service_error.data_fine_antecendente", null);
		_ivs.add(new InvalidValue(mess, MercatiDLivelloServizio.class, "dataFine", entity, new MercatiDLivelloServizio()));
	    }
	}
	// data inizio sempre maggiore o uguale della data inizio del servizio
	if (entity.getDataInizio().compareTo(entity.getMercatiLivelloServizio().getDataInizioValidita()) < 0) {
	    String mess = getMessageFromBundle("service_error.data_servizio_d_non_compatibile", null);
	    _ivs.add(new InvalidValue(mess, MercatiDLivelloServizio.class, "dataInizio", entity, new MercatiDLivelloServizio()));
	}
	if (entity.getMercatiLivelloServizio().getDataFineValidita() != null) {
	    // data inizio sempre minore o uguale della data fine del servizio (quando popolata)
	    if (entity.getDataInizio().compareTo(entity.getMercatiLivelloServizio().getDataFineValidita()) >= 0) {
		String mess = getMessageFromBundle("service_error.data_servizio_d_non_compatibile", null);
		_ivs.add(new InvalidValue(mess, MercatiDLivelloServizio.class, "dataInizio", entity, new MercatiDLivelloServizio()));
	    }
	    // data fine sempre minore o uguale della data fine del servizio (quando popolata)
	    if (entity.getDataFine() != null && entity.getDataFine().compareTo(entity.getMercatiLivelloServizio().getDataFineValidita()) > 0) {
		String mess = getMessageFromBundle("service_error.data_servizio_d_non_compatibile", null);
		_ivs.add(new InvalidValue(mess, MercatiDLivelloServizio.class, "dataFine", entity, new MercatiDLivelloServizio()));
	    }
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return true;
    }

    protected boolean isDeleteAllowed(MercatiDLivelloServizio entity) {

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
    public void insertMultiPosteggioMultiUso(List<Integer> idposteggi, List<Integer> idLivelliServizio, BigDecimal fattoreMoltiplicativo,
	    Date dataInizio, Date dataFine, boolean usaMqPosteggio) {

	for (Integer idLivelloServizioI : idLivelliServizio) {
	    for (Integer idposteggio : idposteggi) {
		MercatiDLivelloServizio entity = new MercatiDLivelloServizio();
		MercatiD p = mercatiDService.findById(new PkId(idposteggio));
		MercatiLivelloServizio livelloServizio = mercatiLivelloServizioService.findById(new PkId(idLivelloServizioI));
		entity.setMercatiD(p);
		entity.setMercatiLivelloServizio(livelloServizio);
		entity.setFattoreMoltiplicativo(fattoreMoltiplicativo);
		entity.setDataInizio(dataInizio);
		entity.setDataFine(dataFine);
		entity.setUsaMqPosteggio(usaMqPosteggio);
		insert(entity);
	    }
	}
    }
}
