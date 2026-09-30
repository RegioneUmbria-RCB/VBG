package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ProdottoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Prodotto;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;
import it.gruppoinit.pal.gp.core.service.ProdottoService;
import it.toscana.regione.suap.sem.types.procedimento.AttoreReteSuap;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdottoServiceImpl extends BaseServiceImpl<Prodotto, String> implements ProdottoService {

    private ProdottoDAO prodottoDAO;
    private AlberoprocService alberoprocService;
    private FoDomandeService foDomandeService;

    @Autowired
    public void setFoDomandeService(FoDomandeService foDomandeService) {

	this.foDomandeService = foDomandeService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setProdottoDAO(ProdottoDAO prodottoDAO) {

	this.prodottoDAO = prodottoDAO;
    }

    @Override
    public void insert(Prodotto entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Prodotto entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(Prodotto entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<Prodotto> findAll(Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	ft.addOrder(FilterUtils.orderAsc("prodotto"));
	return prodottoDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public Prodotto findById(String id) {

	return prodottoDAO.findById(id);
    }

    @Override
    protected Class<Prodotto> getEntityClass() {

	return Prodotto.class;
    }

    @Override
    public Prodotto findProdotto() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	ft.addOrder(FilterUtils.orderAsc("prodotto"));
	List<Prodotto> l = prodottoDAO.findByFilterTable(ft, 0, 1);
	if (!l.isEmpty()) {
	    return l.get(0);
	}
	return null;
    }

    @Override
    public String getIdentificativoProdotto(Alberoproc alberoproc) {

	String result = findIdentificativoProdottoInstallazione();
	AlberoprocHelper ap = alberoprocService.findAlberoprocHelper(alberoproc, null);
	return result;
    }

    @Override
    public String getIdentificativoFromPratica(Integer fodomandeId) {

	FoDomande dom = foDomandeService.findById(new PkId(fodomandeId));
	String result = null; //dom.getIdentificativoDestinatario();
	if (StringUtils.isNotBlank(result)) {
	    return result;
	}
	Prodotto p = findProdotto();
	if (p != null) {
	    String identificativoMittente = StringUtils.defaultString(p.getIdentificativoMittente()).trim();
	    if (BooleanUtils.isTrue(p.getFlagModMittente())) {
		if (StringUtils.isNotBlank(identificativoMittente)) {
		    return identificativoMittente;
		}
	    }
	}
	return AttoreReteSuap.FACCT.name();
    }

    @Override
    public String findIdentificativoProdottoInstallazione() {

	Prodotto p = this.findProdotto();
	if (p != null) {
	    String identificativoMittente = StringUtils.defaultString(p.getIdentificativoMittente()).trim();
	    if (StringUtils.isNotBlank(identificativoMittente)) {
		return identificativoMittente;
	    }
	}
	return AttoreReteSuap.FACCT.name();
    }
}
