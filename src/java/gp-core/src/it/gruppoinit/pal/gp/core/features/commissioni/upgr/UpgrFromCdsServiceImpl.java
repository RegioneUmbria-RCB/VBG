package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

import java.util.ArrayList;
import java.util.List;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.ClmenuService;
import it.gruppoinit.pal.gp.core.utils.LoggerArchiviBackoffice;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

@Service
public class UpgrFromCdsServiceImpl implements UpgrFromCdsService {

    @Autowired
    private UpgrFromCdsDAO upgrFromCdsDAO;
    @Autowired
    private ClmenuService clmenuService;
    @Autowired(required = false)
    private CacheManager cacheManager;

    @Override
    public List<CdsReport> eseguiUpgr() {

	String idcomune = ORMHelper.getIdcomune();
	String software = ORMHelper.getSoftware();
	// 1. cerca le CDS da Migrare
	UpgrDaMigrareBean cds = upgrFromCdsDAO.findCdsDaMigrare();
	// 1.1 sono da migrare le CDS delle istanze/movimenti che non hanno record in COMMISSIONIEDILIZIE_R
	List<CdsReport> cdsConErrori = new ArrayList<CdsReport>();
	for (CdsDaMigrareBean cdsDaMigrareBean : cds.getCdsDaMigrare()) {
	    // 2. migra la CDS
	    // 2.2
	    ORMHelper.setIdcomune(cdsDaMigrareBean.getIdcomune());
	    ORMHelper.setSoftware(cdsDaMigrareBean.getSoftware());
	    List<CdsTipologieInserite> codicetipologia = cds.getTipologieCreatePerIdcomune().get(cdsDaMigrareBean.getIdcomune());
	    List<CdsCaricheInserite> cariche = cds.getTipologieCarichePerComune().get(cdsDaMigrareBean.getIdcomune());
	    try {
		CdsReport reportCDS = upgrFromCdsDAO.migraCDS(cdsDaMigrareBean, codicetipologia, cariche);
		if (reportCDS.isErrore()) {
		    cdsConErrori.add(reportCDS);
		}
	    } catch (Exception e) {
		LoggerArchiviBackoffice.log("Errore non gestito nell'elaborazione CDS: " + cdsDaMigrareBean + ". " + e.getMessage(), e);
		CdsReport rpt = new CdsReport(cdsDaMigrareBean);
		rpt.addErrore("Errore non gestito nell'elaborazione CDS: " + e.getMessage());
		cdsConErrori.add(rpt);
	    }
	}
	ORMHelper.setIdcomune(idcomune);
	ORMHelper.setSoftware(software);
	return cdsConErrori;
    }

    @Override
    public void migraMenuSuSoftwareTT() {

	upgrFromCdsDAO.migraMenuSuSoftwareTT();
	clmenuService.resetObjectCached();
	if (cacheManager != null) {
	    Cache cache = cacheManager.getCache(WebConstants.CACHE_MENU_KEY);
	    List keys = cache.getKeys();
	    for (Object key : keys) {
		String k = (String) key;
		cache.remove(key);
	    }
	    cache.flush();
	}
    }

    @Override
    public void migraCommediTipopareriMov() {

	upgrFromCdsDAO.migraCommediTipopareriMov();
    }
}
