package it.gruppoinit.pal.gp.areariservata.service.impl;

import it.gruppoinit.pal.gp.areariservata.service.AnagrafeARJService;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnagrafeARJServiceImpl extends BaseServiceImpl<Anagrafe, PkId> implements AnagrafeARJService {

    private static final Logger log = LoggerFactory.getLogger(AnagrafeARJServiceImpl.class);
    @Autowired
    private AnagrafeService anagrafeService;

    @Override
    public Anagrafe findById(PkId id) {

	return anagrafeService.findById(id);
    }

    @Override
    public Anagrafe findPFByCF(String cf) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction cfRestriction = new FilterRestriction();
	cfRestriction.addFilterField(FilterUtils.equalsIgnoreCase("codicefiscale", cf));
	filterTable.addRestriction(cfRestriction);
	FilterRestriction tipoAnagrafeRestriction = new FilterRestriction();
	tipoAnagrafeRestriction.addFilterField(FilterUtils.equals("tipoanagrafe", "F", String.class));
	filterTable.addRestriction(tipoAnagrafeRestriction);
	FilterRestriction disabilitatoRestriction = new FilterRestriction();
	disabilitatoRestriction.addFilterField(FilterUtils.notEquals("flagDisabilitato", 1, Integer.class));
	filterTable.addRestriction(disabilitatoRestriction);
	List<Anagrafe> list = anagrafeService.findByFilterTable(filterTable, null, null);
	if (list.size() > 1) {
	    log.error("findPFByCF: {}, La query ha restituito più di un risultato.", cf);
	    throw new RuntimeException("Esiste più di un'anagrafe con questo CF");
	}
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @Override
    public Anagrafe findPGByCFoPI(String cfOpi) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction cfRestriction = new FilterRestriction();
	cfRestriction.addFilterField(FilterUtils.equalsIgnoreCase("codicefiscale", cfOpi));
	cfRestriction.addFilterField(FilterUtils.equalsIgnoreCase("partitaiva", cfOpi));
	cfRestriction.setAndOrRestriction(AndOrRestriction.OR);
	filterTable.addRestriction(cfRestriction);
	FilterRestriction tipoAnagrafeRestriction = new FilterRestriction();
	tipoAnagrafeRestriction.addFilterField(FilterUtils.equals("tipoanagrafe", "G", String.class));
	filterTable.addRestriction(tipoAnagrafeRestriction);
	FilterRestriction disabilitatoRestriction = new FilterRestriction();
	disabilitatoRestriction.addFilterField(FilterUtils.notEquals("flagDisabilitato", 1, Integer.class));
	filterTable.addRestriction(disabilitatoRestriction);
	List<Anagrafe> list = anagrafeService.findByFilterTable(filterTable, null, null);
	if (list.size() > 1) {
	    log.error("findPGByCFoPI: {}, La query ha restituito più di un risultato.", cfOpi);
	    throw new RuntimeException("Esiste più di un'anagrafe con questo CF o PI");
	}
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @Override
    public Anagrafe findByUserId(String userid) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction cfRestriction = new FilterRestriction();
	cfRestriction.addFilterField(FilterUtils.equalsIgnoreCase("codicefiscale", userid));
	cfRestriction.addFilterField(FilterUtils.equalsIgnoreCase("strongAuthId", userid));
	cfRestriction.setAndOrRestriction(AndOrRestriction.OR);
	filterTable.addRestriction(cfRestriction);
	FilterRestriction disabilitatoRestriction = new FilterRestriction();
	disabilitatoRestriction.addFilterField(FilterUtils.notEquals("flagDisabilitato", 1, Integer.class));
	filterTable.addRestriction(disabilitatoRestriction);
	FilterRestriction pf = new FilterRestriction();
	pf.addFilterField(FilterUtils.equals("tipoanagrafe", WebConstants.PERSONA_FISICA, String.class));
	filterTable.addRestriction(pf);
	List<Anagrafe> list = anagrafeService.findByFilterTable(filterTable, 0, 10);
	//if (list.size() > 1) {
	//    log.error("findByUserId: {}, La query ha restituito più di un risultato.", userid);
	//    throw new RuntimeException(
	//	    "Non è stato possibile effettuare l'accesso in quanto esistono più anagrafiche associate all'utenza. Rivolgersi all'ente di competenza.");
	//}
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @Override
    public void delete(Anagrafe arg0) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Anagrafe> findAll(Integer arg0, Integer arg1) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void insert(Anagrafe arg0) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(Anagrafe arg0) {

	// TODO Auto-generated method stub
    }

    @Override
    protected Class<Anagrafe> getEntityClass() {

	return Anagrafe.class;
    }
}
