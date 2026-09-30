/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwOneriregulusDAO;
import it.gruppoinit.pal.gp.core.domain.VwOneriregulus;
import it.gruppoinit.pal.gp.core.service.VwOneriregulusService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class VwOneriregulusServiceImpl implements VwOneriregulusService {

    private VwOneriregulusDAO vwOneriregulusDAO;

    @Autowired
    public void setVwOneriregulusDAO(VwOneriregulusDAO vwOneriregulusDAO) {

	this.vwOneriregulusDAO = vwOneriregulusDAO;
    }

    @Override
    public List<VwOneriregulus> getDebtSituationOneriRegulus(String codiceFiscale) {

	return vwOneriregulusDAO.getDebtSituationOneriRegulus(codiceFiscale);
    }
}
