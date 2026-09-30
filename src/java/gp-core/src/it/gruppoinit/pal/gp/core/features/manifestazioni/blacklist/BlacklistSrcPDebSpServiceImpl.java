package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.BlacklistSrcPDebSp;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class BlacklistSrcPDebSpServiceImpl extends BaseServiceImpl<BlacklistSrcPDebSp, PkId> implements BlacklistSrcPDebSpService {

    private BlacklistSrcPDebSpDAO blacklistSrcPDebSpDAO;

    @Autowired
    public void setBlacklistSrcPDebSpDAO(BlacklistSrcPDebSpDAO blacklistSrcPDebSpDAO) {

	this.blacklistSrcPDebSpDAO = blacklistSrcPDebSpDAO;
    }

    @Override
    public void insert(BlacklistSrcPDebSp entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    blacklistSrcPDebSpDAO.insert(entity);
	}
    }

    private void dataIntegration(BlacklistSrcPDebSp entity) {

	fixMergeEntityProperties(entity);
    }

    @Override
    public void update(BlacklistSrcPDebSp entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    blacklistSrcPDebSpDAO.update(entity);
	}
    }

    @Override
    protected void fixMergeEntityProperties(BlacklistSrcPDebSp entity) {

    }

    @Override
    public void delete(BlacklistSrcPDebSp entity) {

	if (isDeleteAllowed(entity)) {
	    blacklistSrcPDebSpDAO.delete(entity);
	}
    }

    @Override
    public List<BlacklistSrcPDebSp> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public BlacklistSrcPDebSp findById(PkId id) {

	return blacklistSrcPDebSpDAO.findById(id);
    }

    protected java.lang.Class<BlacklistSrcPDebSp> getEntityClass() {

	return BlacklistSrcPDebSp.class;
    }

    @Override
    public List<BlacklistSrcPDebSp> findByDettPosDebIdAttive(Integer dettPosizioneDebitoriaId) {

	FilterTable ft = findByDettPosAttiveFT(dettPosizioneDebitoriaId, false);
	return blacklistSrcPDebSpDAO.findByFilterTable(ft);
    }

    private FilterTable findByDettPosAttiveFT(Integer dettPosizioneDebitoriaId, boolean isExist) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("dettPosizioneDebitoriaId", dettPosizioneDebitoriaId, Integer.class));
	ft.addRestriction(fr);
	FilterRestriction data = new FilterRestriction();
	data.addFilterField(FilterUtils.isNull("dataFineBl", "blacklistMotivi"));
	ft.addRestriction(data);
	if (!isExist) {
	    ft.addOrder(FilterUtils.orderAsc("dataFineBl", "blacklistMotivi"));
	}
	return ft;
    }

    @Override
    public boolean existsByDettPosDebIdAttive(Integer dettPosizioneDebitoriaId) {

	FilterTable ft = findByDettPosAttiveFT(dettPosizioneDebitoriaId, true);
	return blacklistSrcPDebSpDAO.existsRecords(ft);
    }

    @Override
    public int countBlackListAperte() {

	FilterTable ft = findByPayPosAttive(true);
	return blacklistSrcPDebSpDAO.countRecord(ft);
    }

    @Override
    public List<BlacklistSrcPDebSp> findBlackListAperte(Integer firstResult, Integer maxResult) {

	FilterTable ft = findByPayPosAttive(false);
	return blacklistSrcPDebSpDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    private FilterTable findByPayPosAttive(boolean isCount) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction data = new FilterRestriction();
	data.addFilterField(FilterUtils.isNull("dataFineBl", "blacklistMotivi"));
	ft.addRestriction(data);
	if (!isCount) {
	    ft.addOrder(FilterUtils.orderAsc("dataInizioBl", "blacklistMotivi"));
	}
	return ft;
    }

    @Override
    public List<BlacklistSrcPDebSp> findByBlackListMotivo(Integer codiceMotivo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("blacklistMotiviId", codiceMotivo, Integer.class));
	ft.addRestriction(fr);
	return blacklistSrcPDebSpDAO.findByFilterTable(ft);
    }
}
