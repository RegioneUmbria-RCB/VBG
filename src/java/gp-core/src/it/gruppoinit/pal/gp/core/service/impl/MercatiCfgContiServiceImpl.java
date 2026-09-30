package it.gruppoinit.pal.gp.core.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.MercatiCfgContiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.MercatiCategorie;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgConti;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.PosteggiSettori;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.ConcessioniusoService;
import it.gruppoinit.pal.gp.core.service.MercatiCategorieService;
import it.gruppoinit.pal.gp.core.service.MercatiCfgContiService;
import it.gruppoinit.pal.gp.core.service.PosteggiSettoriService;

@Service
public class MercatiCfgContiServiceImpl extends BaseServiceImpl<MercatiCfgConti, PkId> implements MercatiCfgContiService {

    private static final Logger log = LoggerFactory.getLogger(MercatiCfgContiServiceImpl.class);
    private MercatiCfgContiDAO mercatiCfgContiDAO;
    private MercatiCategorieService mercatiCategorieService;
    private PosteggiSettoriService posteggiSettoriService;
    private ConcessioniusoService concessioniusoService;
    private ContiService contiService;
    private AttivitaService attivitaService;
    private MercatipresenzeTService mercatipresenzeTService;
    private MercatipresenzeDService mercatipresenzeDService;
    private MercatiDService mercatiDService;

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setMercatipresenzeDService(MercatipresenzeDService mercatipresenzeDService) {

	this.mercatipresenzeDService = mercatipresenzeDService;
    }

    @Autowired
    public void setMercatipresenzeTService(MercatipresenzeTService mercatipresenzeTService) {

	this.mercatipresenzeTService = mercatipresenzeTService;
    }

    @Autowired
    public void setMercatiCfgContiDAO(MercatiCfgContiDAO mercatiCfgContiDAO) {

	this.mercatiCfgContiDAO = mercatiCfgContiDAO;
    }

    @Autowired
    public void setMercatiCategorieService(MercatiCategorieService mercatiCategorieService) {

	this.mercatiCategorieService = mercatiCategorieService;
    }

    @Autowired
    public void setPosteggiSettoriService(PosteggiSettoriService posteggiSettoriService) {

	this.posteggiSettoriService = posteggiSettoriService;
    }

    @Autowired
    public void setConcessioniusoService(ConcessioniusoService concessioniusoService) {

	this.concessioniusoService = concessioniusoService;
    }

    @Autowired
    public void setContiService(ContiService contiService) {

	this.contiService = contiService;
    }

    @Autowired
    public void setAttivitaService(AttivitaService attivitaService) {

	this.attivitaService = attivitaService;
    }

    @Override
    public void insert(MercatiCfgConti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatiCfgContiDAO.insert(entity);
	}
    }

    private void dataIntegration(MercatiCfgConti entity) {

	if (entity.getFlagMoltiplicaMq() == null) {
	    entity.setFlagMoltiplicaMq(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(MercatiCfgConti entity) {

	MercatiCategorie mc = mercatiCategorieService.bindDomainObject(entity.getMercatiCategorie(), PkId.class, "id.codice");
	entity.setMercatiCategorie(mc);
	PosteggiSettori ps = posteggiSettoriService.bindDomainObject(entity.getPosteggiSettori(), PkId.class, "id.codice");
	entity.setPosteggiSettori(ps);
	Conti c = contiService.bindDomainObject(entity.getConti(), PkId.class, "id.codice");
	entity.setConti(c);
	Concessioniuso u = concessioniusoService.bindDomainObject(entity.getConcessioniuso(), PkId.class, "id.codice");
	entity.setConcessioniuso(u);
	Attivita a = attivitaService.bindDomainObject(entity.getAttivita(), AttivitaId.class, "id.codiceistat");
	entity.setAttivita(a);
    }

    @Override
    public void update(MercatiCfgConti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatiCfgContiDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatiCfgConti entity) {

	if (isDeleteAllowed(entity)) {
	    mercatiCfgContiDAO.delete(entity);
	}
    }

    @Override
    public List<MercatiCfgConti> findAll(Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	ft.addOrder(FilterUtils.orderAsc("descrizione", "mercatiCategorie"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "concessioniuso"));
	ft.addOrder(FilterUtils.orderAsc("settore", "posteggiSettori"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "conti"));
	ft.addOrder(FilterUtils.orderAsc("datainizioval"));
	return mercatiCfgContiDAO.findByFilterTable(ft);
    }

    @Override
    public MercatiCfgConti findById(PkId id) {

	return mercatiCfgContiDAO.findById(id);
    }

    @Override
    protected Class<MercatiCfgConti> getEntityClass() {

	return MercatiCfgConti.class;
    }

    @Override
    public boolean existsDati() {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	return mercatiCfgContiDAO.existsRecords(filterTable);
    }

    @Override
    public List<MercatiCfgConti> findByDataPresenza(Date dataPresenza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.smallerEqual("datainizioval", dataPresenza, Date.class));
	fr.addFilterField(FilterUtils.greaterEqual("datafineval", dataPresenza, Date.class));
	fr.addFilterField(FilterUtils.isNull("mercatiCategorieId"));
	ft.addRestriction(fr);
	return mercatiCfgContiDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatiCfgConti> findByMercatoCategoriaAndDataPresenza(Integer codiceCategoriaMercato, Date dataPresenza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.smallerEqual("datainizioval", dataPresenza, Date.class));
	fr.addFilterField(FilterUtils.greaterEqual("datafineval", dataPresenza, Date.class));
	fr.addFilterField(FilterUtils.equals("mercatiCategorieId", codiceCategoriaMercato, Integer.class));
	fr.addFilterField(FilterUtils.isNull("posteggiSettoriId"));
	ft.addRestriction(fr);
	return mercatiCfgContiDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatiCfgConti> findByMercatoCategoriaAndPosteggiSettoriAndDataPresenza(Integer codiceCategoriaMercato, Integer posteggioSettoreId,
	    Date dataPresenza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.smallerEqual("datainizioval", dataPresenza, Date.class));
	fr.addFilterField(FilterUtils.greaterEqual("datafineval", dataPresenza, Date.class));
	fr.addFilterField(FilterUtils.equals("mercatiCategorieId", codiceCategoriaMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("posteggiSettoriId", posteggioSettoreId, Integer.class));
	ft.addRestriction(fr);
	return mercatiCfgContiDAO.findByFilterTable(ft);
    }

    @Override
    public BigDecimal getCoefficienteMercatoByContoGiornataEPosteggio(Integer idConto, Integer idGiornata, Integer idPosteggio) {

	MercatipresenzeT giornata = mercatipresenzeTService.findById(new PkId(idGiornata));
	MercatiD posteggio = mercatiDService.findById(new PkId(idPosteggio));
	MercatipresenzeD pres = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(idGiornata, idPosteggio);
	Attivita attivitaIstat = null;
	if (pres != null) {
	    attivitaIstat = pres.getAttivita();
	}
	MercatiCategorie categoriaDiMercato = giornata.getMercato().getMercatiCategorie();
	Concessioniuso usoDellaConcessione = giornata.getConcessioniuso();
	PosteggiSettori settorePosteggioDellaConcessione = posteggio.getPosteggiSettori();
	Conti conto = (idConto != null) ? this.contiService.findById(new PkId(idConto)) : null;
	List<MercatiCfgConti> mcs = mercatiCfgContiDAO.findAttiviInData(giornata.getDataRegistrazione());
	for (MercatiCfgConti cfgConto : mcs) {
	    if (this.mercatiCategoriaConsentita(categoriaDiMercato, cfgConto.getMercatiCategorie())) {
		if (this.concessioniUsoConsentito(usoDellaConcessione, cfgConto.getConcessioniuso())) {
		    if (this.attivitaIstatConsentita(attivitaIstat, cfgConto.getAttivita())) {
			if (this.settorePosteggioConsentito(settorePosteggioDellaConcessione, cfgConto.getPosteggiSettori())) {
			    if (this.contoConsentito(conto, cfgConto.getConti())) {
				return cfgConto.getImporto();
			    }
			}
		    }
		}
	    }
	}
	return null;
    }

    private boolean mercatiCategoriaConsentita(MercatiCategorie categoriaDellagiornataMercato, MercatiCategorie categoriaConfigurata) {

	if (categoriaConfigurata == null) {
	    return true;
	}
	if (categoriaDellagiornataMercato == null) {
	    return false;
	}
	return categoriaConfigurata.getId().getCodice().equals(categoriaDellagiornataMercato.getId().getCodice());
    }

    private boolean concessioniUsoConsentito(Concessioniuso usoConcessione, Concessioniuso usoConfigurato) {

	if (usoConfigurato == null) {
	    return true;
	}
	if (usoConcessione == null) {
	    return false;
	}
	return usoConfigurato.getId().getCodice().equals(usoConcessione.getId().getCodice());
    }

    private boolean attivitaIstatConsentita(Attivita attivitaPresenza, Attivita attivitaIstatConfigurata) {

	if (attivitaIstatConfigurata == null) {
	    return true;
	}
	if (attivitaPresenza == null) {
	    return false;
	}
	return attivitaPresenza.getId().getCodiceistat().equalsIgnoreCase(attivitaIstatConfigurata.getId().getCodiceistat());
    }

    private boolean settorePosteggioConsentito(PosteggiSettori settorePosteggioConcessione, PosteggiSettori settorePosteggioConfigurato) {

	if (settorePosteggioConfigurato == null) {
	    return true;
	}
	if (settorePosteggioConcessione == null) {
	    return false;
	}
	return settorePosteggioConfigurato.getId().getCodice().equals(settorePosteggioConcessione.getId().getCodice());
    }

    private boolean contoConsentito(Conti contoDellaFormula, Conti contoConfigurato) {

	if (contoConfigurato == null) {
	    return true;
	}
	if (contoDellaFormula == null) {
	    return false;
	}
	return contoConfigurato.getId().getCodice().equals(contoDellaFormula.getId().getCodice());
    }
}
