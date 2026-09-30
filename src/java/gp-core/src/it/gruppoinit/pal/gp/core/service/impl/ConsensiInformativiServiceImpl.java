package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ConsensiInformativiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.ConsensiInformativi;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ConsensoInformativoDettaglioBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ConsensoInformativoRestBean;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ConsensiInformativiService;
import it.gruppoinit.pal.gp.core.service.FoConsensiInformativiService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsensiInformativiServiceImpl extends BaseServiceImpl<ConsensiInformativi, PkId> implements ConsensiInformativiService {

    @Autowired
    private ConsensiInformativiDAO consensiInformativiDAO;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private FoConsensiInformativiService foConsensiInformativiService;

    @Override
    public void insert(ConsensiInformativi entity) {

	if (validateEntity(entity)) {
	    consensiInformativiDAO.insert(entity);
	}
    }

    @Override
    public void update(ConsensiInformativi entity) {

	if (validateEntity(entity)) {
	    consensiInformativiDAO.update(entity);
	}
    }

    @Override
    public void delete(ConsensiInformativi entity) {

	if (isDeleteAllowed(entity)) {
	    consensiInformativiDAO.delete(entity);
	}
    }

    @Override
    public List<ConsensiInformativi> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public ConsensiInformativi findById(PkId id) {

	return consensiInformativiDAO.findById(id);
    }

    @Override
    protected Class<ConsensiInformativi> getEntityClass() {

	return ConsensiInformativi.class;
    }

    @Override
    public List<ConsensoInformativoDettaglioBean> findConsensiDaApprovare(String userId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("contesto"));
	ft.addOrder(FilterUtils.orderAsc("versione"));
	Map<String, ConsensiInformativi> m = new HashMap<String, ConsensiInformativi>();
	List<ConsensiInformativi> list = consensiInformativiDAO.findByFilterTable(ft);
	List<ConsensoInformativoDettaglioBean> result = new ArrayList<ConsensoInformativoDettaglioBean>();
	for (ConsensiInformativi c : list) {
	    m.put(c.getContesto(), c);
	}
	for (Map.Entry<String, ConsensiInformativi> entry : m.entrySet()) {
	    ConsensoInformativoDettaglioBean b = new ConsensoInformativoDettaglioBean();
	    ConsensiInformativi c = entry.getValue();
	    b.setCodice(c.getId().getCodice());
	    b.setDescrizione(c.getDescrizione());
	    b.setContesto(c.getContesto());
	    b.setOrdine(c.getOrdine());
	    b.setObbligatorio(c.getObbligatorio());
	    Oggetti o = oggettiService.findById(new PkId(c.getOggetto().getId().getCodice()));
	    String consenso = null;
	    try {
		consenso = new String(o.getOggetto(), "UTF-8");
	    } catch (UnsupportedEncodingException e) {
		consenso = new String(o.getOggetto());
	    }
	    b.setTesto(consenso);
	    b.setVersione(c.getVersione());
	    ConsensoInformativoRestBean cc = foConsensiInformativiService.leggiConsenso(userId, c.getId().getCodice());
	    b.setFlag_letto(false);
	    if (cc != null) {
		b.setFlag_letto(cc.getFlagConsenso());
	    }
	    result.add(b);
	}
	return result;
    }
}
