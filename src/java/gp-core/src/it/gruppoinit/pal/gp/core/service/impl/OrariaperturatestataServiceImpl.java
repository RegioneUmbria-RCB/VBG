package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.OrariaperturatestataDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Orariapertura;
import it.gruppoinit.pal.gp.core.domain.Orariaperturatestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiorario;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.OrariaperturaService;
import it.gruppoinit.pal.gp.core.service.OrariaperturatestataService;
import it.gruppoinit.pal.gp.core.service.TipiorarioService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author lucap
 */
@Service
public class OrariaperturatestataServiceImpl extends BaseServiceImpl<Orariaperturatestata, PkId> implements OrariaperturatestataService {

    private OrariaperturatestataDAO orariaperturatestataDAO;
    private IstanzeService istanzeService;
    private TipiorarioService tipiorarioService;
    private OrariaperturaService orariaperturaService;

    @Autowired
    public void setOrariaperturatestataDAO(OrariaperturatestataDAO orariaperturatestataDAO) {

	this.orariaperturatestataDAO = orariaperturatestataDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setTipiorarioService(TipiorarioService tipiorarioService) {

	this.tipiorarioService = tipiorarioService;
    }

    @Autowired
    public void setOrariaperturaService(OrariaperturaService orariaperturaService) {

	this.orariaperturaService = orariaperturaService;
    }

    @Override
    protected Class<Orariaperturatestata> getEntityClass() {

	return Orariaperturatestata.class;
    }

    @Override
    public List<Orariaperturatestata> findAll(Integer firstResult, Integer maxResult) {

	return orariaperturatestataDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Orariaperturatestata entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Set<Orariapertura> orariaperturas = entity.getOrariaperturas();
	    entity.setOrariaperturas(null);
	    orariaperturatestataDAO.insert(entity);
	    childDataInsert(orariaperturas);
	}
    }

    @Override
    public Orariaperturatestata findById(PkId id) {

	return orariaperturatestataDAO.findById(id);
    }

    @Override
    public void update(Orariaperturatestata entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Set<Orariapertura> orariaperturaList = entity.getOrariaperturas();
	    orariaperturaService.deleteByOrariaperturatestata(entity);
	    entity.setOrariaperturas(null);
	    orariaperturatestataDAO.update(entity);
	    childDataInsert(orariaperturaList);
	}
    }

    @Override
    public void delete(Orariaperturatestata entity) {

	if (isDeleteAllowed(entity)) {
	    orariaperturaService.deleteByOrariaperturatestata(entity);
	    orariaperturatestataDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Orariaperturatestata entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Orariaperturatestata> findByIstanza(Istanze istanza) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    throw new IllegalArgumentException("il parametro istanza non può essere vuoto o nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", istanza.getId().getCodice(), "istanze", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("toDescrizione", "tipiorario"));
	List<Orariaperturatestata> list = orariaperturatestataDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public void copiaOrariAperturaAttivita(Istanze istanzaSorgente, Istanze istanzaDestinatario) {

	// le istanze attività che devo copiare 
	Set<Orariaperturatestata> listOrariaperturatestataSorgente = istanzaSorgente.getOrariaperturatestatas();
	//
	//1-lista delle istanze attività che utilizzerò per i controllo per non creare doppioni
	Set<Orariaperturatestata> listOrariaperturatestataDestinatarie = istanzaDestinatario.getOrariaperturatestatas();
	for (Orariaperturatestata orariaperturatestataSorgenete : listOrariaperturatestataSorgente) {
	    Orariaperturatestata orariaperturatestata = null;
	    if (!isOrariaperturatestataExists(orariaperturatestataSorgenete, listOrariaperturatestataDestinatarie)) {
		orariaperturatestata = new Orariaperturatestata();
		orariaperturatestata.setIstanze(istanzaDestinatario);
		if (orariaperturatestataSorgenete.getTipiorario() != null) {
		    orariaperturatestata.setTipiorario(orariaperturatestataSorgenete.getTipiorario());
		}
		if (StringUtils.isNotBlank(orariaperturatestataSorgenete.getPeriododa())) {
		    orariaperturatestata.setPeriododa(orariaperturatestataSorgenete.getPeriododa());
		}
		if (StringUtils.isNotBlank(orariaperturatestataSorgenete.getPeriodoa())) {
		    orariaperturatestata.setPeriodoa(orariaperturatestataSorgenete.getPeriodoa());
		}
		// di ogni orario apertura testata che vado a copiare 
		// devo anche replicare ,se ci sono i dettaglio degli orari
		if (orariaperturatestataSorgenete.getOrariaperturas() != null && !orariaperturatestataSorgenete.getOrariaperturas().isEmpty()) {
		    Set<Orariapertura> listOrariAperturaSorgente = orariaperturatestataSorgenete.getOrariaperturas();
		    Set<Orariapertura> listOrariAperturaDestinatarie = new HashSet<Orariapertura>();
		    Orariapertura orariapertura = null;
		    for (Orariapertura orariaperturaSorgente : listOrariAperturaSorgente) {
			orariapertura = new Orariapertura();
			orariapertura.setOrariaperturatestata(orariaperturatestata);
			if (orariaperturaSorgente.getGiornisettimana() != null) {
			    orariapertura.setGiornisettimana(orariaperturaSorgente.getGiornisettimana());
			}
			if (StringUtils.isNotBlank(orariaperturaSorgente.getOaAlleore())) {
			    orariapertura.setOaAlleore(orariaperturaSorgente.getOaAlleore());
			}
			if (StringUtils.isNotBlank(orariaperturaSorgente.getOaAlleorepom())) {
			    orariapertura.setOaAlleorepom(orariaperturaSorgente.getOaAlleorepom());
			}
			if (StringUtils.isNotBlank(orariaperturaSorgente.getOaDalleore())) {
			    orariapertura.setOaDalleore(orariaperturaSorgente.getOaDalleore());
			}
			if (StringUtils.isNotBlank(orariaperturaSorgente.getOaDalleorepom())) {
			    orariapertura.setOaDalleorepom(orariaperturaSorgente.getOaDalleorepom());
			}
			if (orariaperturaSorgente.getTipiapertura() != null) {
			    orariapertura.setTipiapertura(orariaperturaSorgente.getTipiapertura());
			}
			listOrariAperturaDestinatarie.add(orariapertura);
		    }
		    orariaperturatestata.setOrariaperturas(listOrariAperturaDestinatarie);
		}
		this.insert(orariaperturatestata);
	    }
	}
    }

    // Controlla per ogni oggetto della lista destinatario se ce ne è uno uguale a quello sorgente passato
    // quando ne trova uno esce e ritona true
    // altrimenti ritorna false.
    private boolean isOrariaperturatestataExists(Orariaperturatestata orariaperturatestataSorgente,
	    Set<Orariaperturatestata> listOrariaperturatestataDestinatarie) {

	boolean isEquals = false;
	for (Orariaperturatestata orariaperturatestataDestinatario : listOrariaperturatestataDestinatarie) {
	    if ((orariaperturatestataSorgente.getTipiorario() == null && orariaperturatestataDestinatario.getTipiorario() == null)
		    || (EntityUtils.equals(orariaperturatestataSorgente.getTipiorario(), orariaperturatestataDestinatario.getTipiorario()))) {
		return true;
	    }
	}
	return isEquals;
    }

    private void dataIntegration(Orariaperturatestata entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro orariaperturatestata è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Orariaperturatestata entity) {

	Istanze istanza = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanza);
	Tipiorario tipiorario = tipiorarioService.bindDomainObject(entity.getTipiorario(), PkId.class, "id.codice");
	entity.setTipiorario(tipiorario);
    }

    private void childDataInsert(Set<Orariapertura> orariaperturas) {

	for (Orariapertura orariapertura : orariaperturas) {
	    orariaperturaService.insert(orariapertura);
	}
    }
}
