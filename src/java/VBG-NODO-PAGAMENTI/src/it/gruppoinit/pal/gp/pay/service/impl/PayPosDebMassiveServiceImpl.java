package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.pay.dao.PayPosDebMassiveDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPosDebMassive;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.service.PayPosDebMassiveService;

@Service
public class PayPosDebMassiveServiceImpl extends BaseServiceImpl<PayPosDebMassive, PkId> implements PayPosDebMassiveService {

    @Autowired
    private PayPosDebMassiveDAO payPosDebMassiveDAO;

    @Override
    public void insert(PayPosDebMassive entity) {

	dataIntegration(entity);
	payPosDebMassiveDAO.insert(entity);
    }

    private void dataIntegration(PayPosDebMassive entity) {

	if (entity == null) {
	    return;
	}
	if (StringUtils.length(entity.getMessaggio()) > 4000) {
	    entity.setMessaggio(StringUtils.left(entity.getMessaggio(), 4000));
	}
    }

    @Override
    public void update(PayPosDebMassive entity) {

	payPosDebMassiveDAO.update(entity);
    }

    @Override
    public void delete(PayPosDebMassive entity) {

	payPosDebMassiveDAO.delete(entity);
    }

    @Override
    public List<PayPosDebMassive> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public PayPosDebMassive findById(PkId id) {

	return payPosDebMassiveDAO.findById(id);
    }

    @Override
    protected Class<PayPosDebMassive> getEntityClass() {

	return payPosDebMassiveDAO.getEntityClass();
    }

    @Override
    public Map<String, List<Integer>> findPosizioniDaElaborare() {

	return payPosDebMassiveDAO.findPosizioniDaElaborare();
    }

    @Override
    public PayPosDebMassive findByIdPosizioneDebitoriaAndOperazione(PayPosizioniDebitorie posElaborata, String idoperazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("idOperazione", idoperazione, String.class));
	fr.addFilterField(FilterUtils.equals("posizioneDebitoriaId", posElaborata.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	List<PayPosDebMassive> ret = payPosDebMassiveDAO.findByFilterTable(ft, 0, 2);
	if (ret.isEmpty()) {
	    return null;
	}
	return ret.get(0);
    }
}
