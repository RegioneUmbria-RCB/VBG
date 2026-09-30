package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ManifAreePubblicheDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.ManifAreePubbliche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.helper.EmailHelper;
import it.gruppoinit.pal.gp.core.helper.TIPO_MANIFESTAZIONE;
import it.gruppoinit.pal.gp.core.service.MailService;
import it.gruppoinit.pal.gp.core.service.ManifAreePubblicheService;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.ManifestazioniAreePubblicheSearchFilter;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class ManifAreePubblicheServiceImpl extends BaseServiceImpl<ManifAreePubbliche, PkId> implements ManifAreePubblicheService {

    private static final Logger log = LoggerFactory.getLogger(ManifAreePubblicheServiceImpl.class);
    private ManifAreePubblicheDAO manifareepubblicheDAO;
    @Autowired
    private MailService mailService;

    @Autowired
    public void setManifAreePubblicheDAO(ManifAreePubblicheDAO manifareepubblicheDAO) {

	this.manifareepubblicheDAO = manifareepubblicheDAO;
    }

    @Override
    protected Class<ManifAreePubbliche> getEntityClass() {

	return ManifAreePubbliche.class;
    }

    @Override
    public void insert(ManifAreePubbliche entity) {

	if (validateEntity(entity)) {
	    manifareepubblicheDAO.insert(entity);
	    EmailHelper e = mailService.pupolateEmail(entity.getId().getCodice(), TIPO_MANIFESTAZIONE.MANIFESTAZIONI_AREE_PUBBLICHE);
	    if (e != null)
		mailService.sendEmail(e.getOggetto(), e.getCorpo());
	    else {
		log.info("insert# Configurazione email non presente, l'email non verrà inviata...");
	    }
	}
    }

    @Override
    public ManifAreePubbliche findById(PkId id) {

	return manifareepubblicheDAO.findById(id);
    }

    @Override
    public void update(ManifAreePubbliche entity) {

	if (validateEntity(entity)) {
	    manifareepubblicheDAO.update(entity);
	}
    }

    @Override
    public void delete(ManifAreePubbliche entity) {

	if (isDeleteAllowed(entity)) {
	    manifareepubblicheDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ManifAreePubbliche entity) {

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
    public List<ManifAreePubbliche> findByFilter(ManifestazioniAreePubblicheSearchFilter filter) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (StringUtils.isNotBlank(filter.getDenominazione())) {
	    fr.addFilterField(FilterUtils.like("denominazione", filter.getDenominazione()));
	}
	if (StringUtils.isNotBlank(filter.getTipologia())) {
	    fr.addFilterField(FilterUtils.like("tipologia", filter.getTipologia()));
	}
	if (StringUtils.isNotBlank(filter.getCodicecomune())) {
	    fr.addFilterField(FilterUtils.equals("codicecomune", filter.getCodicecomune(), "comuni", String.class));
	}
	if (StringUtils.isNotBlank(filter.getCadenza())) {
	    fr.addFilterField(FilterUtils.like("cadenza", filter.getCadenza()));
	}
	if (StringUtils.isNotBlank(filter.getGioni())) {
	    String[] giornis = StringUtils.split(filter.getGioni(), ",");
	    for (int i = 0; i < giornis.length; i++) {
		String giorno = giornis[i];
		if (giorno.equals("mon")) {
		    fr.addFilterField(FilterUtils.equals("mon", true, Boolean.class));
		} else if (giorno.equals("tue")) {
		    fr.addFilterField(FilterUtils.equals("tue", true, Boolean.class));
		} else if (giorno.equals("wend")) {
		    fr.addFilterField(FilterUtils.equals("wen", true, Boolean.class));
		} else if (giorno.equals("thu")) {
		    fr.addFilterField(FilterUtils.equals("thu", true, Boolean.class));
		} else if (giorno.equals("frid")) {
		    fr.addFilterField(FilterUtils.equals("frid", true, Boolean.class));
		} else if (giorno.equals("sat")) {
		    fr.addFilterField(FilterUtils.equals("sat", true, Boolean.class));
		} else if (giorno.equals("sun")) {
		    fr.addFilterField(FilterUtils.equals("sun", true, Boolean.class));
		}
	    }
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataInserimento"));
	return manifareepubblicheDAO.findByFilterTable(ft);
    }
}
