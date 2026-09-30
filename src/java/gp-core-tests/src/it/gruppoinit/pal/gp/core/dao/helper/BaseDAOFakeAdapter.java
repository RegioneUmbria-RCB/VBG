package it.gruppoinit.pal.gp.core.dao.helper;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

public class BaseDAOFakeAdapter<E, F extends Serializable> implements BaseDAO<E, F> {

    @Override
    public void insert(E entity) {

	// TODO Auto-generated method stub..
    }

    @Override
    public void update(E entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void insertOrUpdate(E entity, F id, boolean isUpdate) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(E entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void evict(E entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<E> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<E> findAll(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
	    DAOOrderTypeEnum orderType) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public E findById(F id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Class<E> getEntityClass() {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<E> findByFilterTable(FilterTable filterTable) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<E> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean existsRecords(FilterTable filterTable) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public int countRecord(FilterTable filterTable) {

	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public Object max(FilterTable filterTable, String propertyName) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void flush() {

	// TODO Auto-generated method stub
    }

    @Override
    public void clear() {

	// TODO Auto-generated method stub
    }

    @Override
    public F newIdFromSequence(E entity) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void commit() {

	// TODO Auto-generated method stub
    }

    @Override
    public DynaBean findDynaBeanById(String idcomune, Integer id, DynaClass dynaClass, Class daoEntityClass) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DynaBean> findDynaBeanByFilterTable(FilterTable ft, DynaClass dynaClass, Integer firstResult, Integer maxResult,
	    Class daoEntityClass) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public <T> T getById(Class<T> cls, Integer id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public <T> T getById(Class<T> cls, PkId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public <T> void saveEntity(T entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void refreshEntity(Object entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void commitFlush() {

	// TODO Auto-generated method stub
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
