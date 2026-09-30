package it.gruppoinit.pal.gp.core.oggettimetadati;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;

import it.gruppoinit.pal.gp.core.dao.OggettiMetadatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

public class OggettiMetadatiDAOFake implements OggettiMetadatiDAO {

    @Override
    public void insert(OggettiMetadati entity) {

    }

    @Override
    public void update(OggettiMetadati entity) {

    }

    @Override
    public void insertOrUpdate(OggettiMetadati entity, OggettiMetadatiId id, boolean isUpdate) {

    }

    @Override
    public void delete(OggettiMetadati entity) {

    }

    @Override
    public void evict(OggettiMetadati entity) {

    }

    @Override
    public List<OggettiMetadati> findAll(Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public List<OggettiMetadati> findAll(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
	    DAOOrderTypeEnum orderType) {

	return null;
    }

    @Override
    public OggettiMetadati findById(OggettiMetadatiId id) {

	return null;
    }

    @Override
    public Class<OggettiMetadati> getEntityClass() {

	return null;
    }

    @Override
    public List<OggettiMetadati> findByFilterTable(FilterTable filterTable) {

	return null;
    }

    @Override
    public List<OggettiMetadati> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return new ArrayList<OggettiMetadati>();
    }

    @Override
    public boolean existsRecords(FilterTable filterTable) {

	return false;
    }

    @Override
    public int countRecord(FilterTable filterTable) {

	return 0;
    }

    @Override
    public Object max(FilterTable filterTable, String propertyName) {

	return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public void clear() {

    }

    @Override
    public OggettiMetadatiId newIdFromSequence(OggettiMetadati entity) {

	return null;
    }

    @Override
    public void commit() {

    }

    @Override
    public DynaBean findDynaBeanById(String idcomune, Integer id, DynaClass dynaClass, Class daoEntityClass) {

	return null;
    }

    @Override
    public List<DynaBean> findDynaBeanByFilterTable(FilterTable ft, DynaClass dynaClass, Integer firstResult, Integer maxResult,
	    Class daoEntityClass) {

	return null;
    }

    @Override
    public void deleteByOggetto(Integer codiceOggetto) {

    }

    @Override
    public void insertInNewTransaction(Integer codiceOggetto, String chiave, String valore) {

    }

    @Override
    public void updateInNewTransaction(Integer codiceOggetto, String chiave, String valore) {

    }

    @Override
    public <T> T getById(Class<T> cls, Integer id) {

	return null;
    }

    @Override
    public <T> T getById(Class<T> cls, PkId id) {

	return null;
    }

    @Override
    public <T> void saveEntity(T entity) {

    }

    @Override
    public void refreshEntity(Object entity) {

    }

    @Override
    public void commitFlush() {

    }

    @Override
    public <T, I> T getByIdCustom(Class<T> cls, I id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int aggiornaSequenza(String sequenceName, String tableName, String idColumn, int totaleRecordDaInserire) {

	// TODO Auto-generated method stub
	return 0;
    }
}
