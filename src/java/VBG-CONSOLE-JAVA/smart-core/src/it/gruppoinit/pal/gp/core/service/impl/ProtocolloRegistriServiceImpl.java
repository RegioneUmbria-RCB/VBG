package it.gruppoinit.pal.gp.core.service.impl;

/**
 * @author gianpaolot
 */
import it.gruppoinit.pal.gp.core.dao.ProtocolloRegistriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.ProtocolloRegistri;
import it.gruppoinit.pal.gp.core.domain.helper.ProtocolloRegistriHelper;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.MailtipoService;
import it.gruppoinit.pal.gp.core.service.ProtocolloFlussoService;
import it.gruppoinit.pal.gp.core.service.ProtocolloRegistriService;
import it.gruppoinit.pal.gp.core.service.TipologiaregistriService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProtocolloRegistriServiceImpl extends BaseServiceImpl<ProtocolloRegistri, PkId> implements ProtocolloRegistriService {

    private ProtocolloRegistriDAO protocolloRegistriDAO;
    private TipologiaregistriService tipologiaregistriService;
    private MailtipoService mailtipoService;
    private AmministrazioniService amministrazioniService;
    private ProtocolloFlussoService protocolloFlussoService;
    private ComuniService comuniService;

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setProtocolloFlussoService(ProtocolloFlussoService protocolloFlussoService) {

	this.protocolloFlussoService = protocolloFlussoService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setTipologiaregistriService(TipologiaregistriService tipologiaregistriService) {

	this.tipologiaregistriService = tipologiaregistriService;
    }

    @Autowired
    public void setProtocolloRegistriDAO(ProtocolloRegistriDAO protocolloRegistriDAO) {

	this.protocolloRegistriDAO = protocolloRegistriDAO;
    }

    @Override
    protected Class<ProtocolloRegistri> getEntityClass() {

	return ProtocolloRegistri.class;
    }

    @Override
    public void delete(ProtocolloRegistri entity) {

	protocolloRegistriDAO.delete(entity);
    }

    @Override
    public List<ProtocolloRegistri> findAll(Integer firstResult, Integer maxResult) {

	return protocolloRegistriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public ProtocolloRegistri findById(PkId id) {

	return protocolloRegistriDAO.findById(id);
    }

    @Override
    public void insert(ProtocolloRegistri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    protocolloRegistriDAO.insert(entity);
	}
    }

    private void dataIntegration(ProtocolloRegistri entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro entity non può essere nullo");
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(ProtocolloRegistri entity) {

	Comuni c = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(c);
	Mailtipo mailtipo = mailtipoService.bindDomainObject(entity.getMailtipo(), PkId.class, "id.codice");
	entity.setMailtipo(mailtipo);
	Amministrazioni mittente = amministrazioniService.bindDomainObject(entity.getMittente(), PkId.class, "id.codice");
	entity.setMittente(mittente);
	Amministrazioni destinatario = amministrazioniService.bindDomainObject(entity.getDestinatario(), PkId.class, "id.codice");
	entity.setDestinatario(destinatario);
	ProtocolloFlusso pf = protocolloFlussoService.bindDomainObject(entity.getProtocolloFlusso(), String.class, "codice");
	entity.setProtocolloFlusso(pf);
    }

    @Override
    public void update(ProtocolloRegistri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    protocolloRegistriDAO.update(entity);
	}
    }

    @Override
    protected boolean validateEntity(ProtocolloRegistri entity) {

	String codiceComune = null;
	if (entity.getComune() != null) {
	    if (StringUtils.isNotBlank(entity.getComune().getCodicecomune())) {
		codiceComune = entity.getComune().getCodicecomune();
	    }
	}
	return super.validateEntity(entity);
    }

    @Override
    public List<ProtocolloRegistri> findByAmministrazioniMittente(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioniMittente: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "mittente", Integer.class));
	filterTable.addRestriction(fr);
	return protocolloRegistriDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<ProtocolloRegistri> findByAmministrazioniDestinatario(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioniDestinatario: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "destinatario", Integer.class));
	filterTable.addRestriction(fr);
	return protocolloRegistriDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<ProtocolloRegistri> findByTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult) {

	if (StringUtils.isBlank(tipomovimento)) {
	    throw new IllegalArgumentException("findByTipimovimento: il parametro tipomovimento e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipimovimentoId", tipomovimento, String.class));
	filterTable.addRestriction(fr);
	return protocolloRegistriDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public ProtocolloRegistriHelper findByRegistroSoftwareComune(Integer idRegistro, String codiceComune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipologiaregistriId", idRegistro, Integer.class));
	if (StringUtils.isNotBlank(codiceComune)) {
	    FilterRestriction comune = new FilterRestriction();
	    comune.setAndOrRestriction(AndOrRestriction.OR);
	    comune.addFilterField(FilterUtils.equals("codicecomune", codiceComune, "comune", String.class));
	    comune.addFilterField(FilterUtils.isNull("codicecomune", "comune"));
	    ft.addRestriction(comune);
	} else {
	    fr.addFilterField(FilterUtils.isNull("codicecomune", "comune"));
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("comune", "comune", FunctionsEnum.NVL_FUNCTION, "'AAAAAAAAAAAA'"));
	List<ProtocolloRegistri> list = protocolloRegistriDAO.findByFilterTable(ft);
	ProtocolloRegistriHelper helper = null;
	if (!list.isEmpty()) {
	    helper = new ProtocolloRegistriHelper();
	    for (ProtocolloRegistri pr : list) {
		helper.setClassifica(pr.getClassifica());
		helper.setDestinatario(pr.getDestinatario());
		helper.setIdtipodocumento(pr.getIdtipodocumento());
		helper.setMailtipo(pr.getMailtipo());
		helper.setMittente(pr.getMittente());
		helper.setProtocolloFlusso(pr.getProtocolloFlusso());
		helper.setTipologiaregistri(pr.getTipologiaregistri());
	    }
	}
	return helper;
    }

    @Override
    public List<ProtocolloRegistri> findByRegistro(Integer codiceRegistro) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipologiaregistriId", codiceRegistro, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("comune", "comune", FunctionsEnum.NVL_FUNCTION, "'AAAAAAAAAAAA'"));
	return protocolloRegistriDAO.findByFilterTable(ft);
    }
}
