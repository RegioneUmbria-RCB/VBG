package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.DocumentiContabilitaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.DocumentiContabilita;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.DocumenticontabilitaFilter;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.DocumentiContabilitaService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class DocumentiContabilitaServiceImpl extends BaseServiceImpl<DocumentiContabilita, PkId> implements DocumentiContabilitaService {

    private DocumentiContabilitaDAO documenticontabilitaDAO;
    private OggettiService oggettiService;
    private SoftwareService softwareService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setDocumentiContabilitaDAO(DocumentiContabilitaDAO documenticontabilitaDAO) {

	this.documenticontabilitaDAO = documenticontabilitaDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Override
    protected Class<DocumentiContabilita> getEntityClass() {

	return DocumentiContabilita.class;
    }

    @Override
    public List<DocumentiContabilita> findAll(Integer firstResult, Integer maxResult) {

	return documenticontabilitaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(DocumentiContabilita entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    documenticontabilitaDAO.insert(entity);
	    gestCancellaOggetto(codiceOggettoDaCancellare);
	}
    }

    @Override
    public DocumentiContabilita findById(PkId id) {

	return documenticontabilitaDAO.findById(id);
    }

    @Override
    public void update(DocumentiContabilita entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    documenticontabilitaDAO.update(entity);
	    gestCancellaOggetto(codiceOggettoDaCancellare);
	}
    }

    @Override
    public void delete(DocumentiContabilita entity) {

	if (isDeleteAllowed(entity)) {
	    documenticontabilitaDAO.delete(entity);
	}
    }

    private void dataIntegration(DocumentiContabilita entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro è nullo DocumentiContabilita");
	}
	//setto la data di inserimento con la data di sistema
	entity.setDatainserimento(new Date());
	// Recupero la data di inserimento imposta via web e la scompongo in anno e mese
	if (entity.getData() != null) {
	    SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
	    SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM");
	    String anno = dateFormatYear.format(entity.getData());
	    String mese = dateFormatMonth.format(entity.getData());
	    entity.setAnno(anno);
	    entity.setMese(mese);
	}
	// setto il responsabile loggato
	Responsabili responsabile = (Responsabili)userSecurityService.getCurrentlyAuthenticatedUserDetails();
	entity.setResponsabili(responsabile);
	// Setto il software
	Software software = softwareService.findById(ORMHelper.getSoftware());
	entity.setSoftware(software);
	fixMergeEntityProperties(entity);
    }

    @Override
    public List<DocumentiContabilita> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResult) {

	return documenticontabilitaDAO.findByDescrizione(textToSearch, firstResult, maxResult);
    }

    @Override
    public List<DocumentiContabilita> findByFilter(DocumenticontabilitaFilter filter, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction restriction = new FilterRestriction();
	if (StringUtils.isNotBlank(filter.getAnno())) {
	    restriction.addFilterField(FilterUtils.equals("anno", filter.getAnno(), String.class));
	}
	if (StringUtils.isNotBlank(filter.getMese())) {
	    restriction.addFilterField(FilterUtils.equals("mese", filter.getMese(), String.class));
	}
	if (filter.getDataDa() != null && filter.getDataA() == null) {
	    restriction.addFilterField(FilterUtils.greaterEqual("data", filter.getDataDa(), Date.class));
	}
	if (filter.getDataDa() == null && filter.getDataA() != null) {
	    restriction.addFilterField(FilterUtils.smallerEqual("data", filter.getDataA(), Date.class));
	}
	if (filter.getDataDa() != null && filter.getDataA() != null) {
	    restriction.addFilterField(FilterUtils.between("data", filter.getDataDa(), filter.getDataA(), Date.class));
	}
	ft.addRestriction(restriction);
	ft.addOrder(FilterUtils.orderDesc("data"));
	return documenticontabilitaDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    protected void fixMergeEntityProperties(DocumentiContabilita entity) {

	Oggetti oggetti = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(oggetti);
    }

    //    protected boolean isDeleteAllowed(DocumentiContabilita entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
    /**
     * @param codiceOggettoDaCancellare
     */
    private void gestCancellaOggetto(Integer codiceOggettoDaCancellare) {

	if (codiceOggettoDaCancellare != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    oggettiService.delete(oggettoDaCancellare);
	}
    }
}
