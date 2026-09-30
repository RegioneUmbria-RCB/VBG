package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogico;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestata;
import it.gruppoinit.pal.gp.core.domain.MovimentiZipLogicoTestataId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@SuppressWarnings("rawtypes")
@Repository
public class ZipLogicoDAOImpl extends BaseDAOImpl implements ZipLogicoDAO {

    private static final Logger log = LoggerFactory.getLogger(ZipLogicoDAOImpl.class);
    private MovimentiZipLogicoTestataDAO movimentiZipLogicoTestataDAO;
    private MovimentiZipLogicoDAO movimentiZipLogicoDAO;

    @Autowired
    public ZipLogicoDAOImpl(MovimentiZipLogicoTestataDAO movimentiZipLogicoTestataDAO, MovimentiZipLogicoDAO movimentiZipLogicoDAO) {

	this.movimentiZipLogicoTestataDAO = movimentiZipLogicoTestataDAO;
	this.movimentiZipLogicoDAO = movimentiZipLogicoDAO;
    }

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Override
    public Set<MovimentiZipLogicoTestataHelper> recuperaTestateMancanti() {

	return this.movimentiZipLogicoDAO.recuperaTestateMancanti();
    }

    @Override
    public void eliminaZipLogicoByCodiceMovimento(Integer codiceMovimento) {

	this.movimentiZipLogicoDAO.eliminaZipLogicoByCodiceMovimento(codiceMovimento);
	this.movimentiZipLogicoTestataDAO.eliminaZipLogicoByCodiceMovimento(codiceMovimento);
    }

    @Override
    public Set<MovimentiZipLogico> findMovimentiZipLogicoByMovimento(Integer codicemovimento) {

	return this.movimentiZipLogicoDAO.findMovimentiZipLogicoByMovimento(codicemovimento);
    }

    @Override
    public List<MovimentiZipLogicoDTO> findMovimentiZipLogicoDTOByMovimento(Integer codicemovimento) {

	return this.movimentiZipLogicoDAO.findMovimentiZipLogicoDTOByMovimento(codicemovimento);
    }

    @Override
    public Boolean isZipLogicoExistInMovimento(Integer codicemovimento) {

	return this.movimentiZipLogicoTestataDAO.isZipLogicoExistInMovimento(codicemovimento);
    }

    @Override
    public Boolean isDocumentoPresenteInZipLogico(Integer codiceZipLogico, Integer codiceMovimento, Integer codiceDocumento, String associationPath) {

	return this.movimentiZipLogicoDAO.isDocumentoPresenteInZipLogico(codiceZipLogico, codiceMovimento, codiceDocumento, associationPath);
    }

    @Override
    public void insertDettaglio(MovimentiZipLogico entity) {

	this.movimentiZipLogicoDAO.insert(entity);
    }

    @Override
    public List<MovimentiZipLogico> findAllDettagli(Integer firstResult, Integer maxResult) {

	return this.movimentiZipLogicoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public MovimentiZipLogico getMovimentiZipLogicoById(PkId id) {

	return this.movimentiZipLogicoDAO.getMovimentiZipLogicoById(id);
    }

    @Override
    public void updateDettaglio(MovimentiZipLogico entity) {

	this.movimentiZipLogicoDAO.update(entity);
    }

    @Override
    public void insertTestata(MovimentiZipLogicoTestataHelper testata) {

	MovimentiZipLogicoTestata entity = new MovimentiZipLogicoTestata(testata);
	this.movimentiZipLogicoTestataDAO.insert(entity);
    }

    @Override
    public void deleteDettaglio(MovimentiZipLogico entity) {

	this.movimentiZipLogicoDAO.delete(entity);
    }

    @Override
    public Boolean existDettagliByCodiceMovimento(Integer codiceMovimento) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (codiceMovimento != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", codiceMovimento, "movimenti", Integer.class));
	}
	filterTable.addRestriction(fr);
	return this.movimentiZipLogicoDAO.existsRecords(filterTable);
    }

    @Override
    public MovimentiZipLogicoTestataHelper findByCodiceMovimento(Integer codiceMovimento) {

	MovimentiZipLogicoTestata testata = this.movimentiZipLogicoTestataDAO.findById(new MovimentiZipLogicoTestataId(codiceMovimento));
	if (testata == null) {
	    return new MovimentiZipLogicoTestataHelper();
	}
	Set<MovimentiZipLogico> dettaglio = this.movimentiZipLogicoDAO.findMovimentiZipLogicoByMovimento(codiceMovimento);
	return new MovimentiZipLogicoTestataHelper(testata, dettaglio);
    }

    @Override
    public Integer contaDocumenti(Integer codiceMovimento) {

	if (codiceMovimento == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo contaDocumenti senza passare il riferimento del codice movimento");
	}
	String sql = "select" +
		" count(*) as conteggio " +
		"from" +
		" movimenti_zip_logico " +
		"where" +
		" idcomune = ? and " +
		" codicemovimento = ?";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, codiceMovimento, new IntegerType());
	q.addScalar("conteggio", Hibernate.INTEGER);
	return (Integer) q.uniqueResult();
    }

    @Override
    public MovimentiZipLogicoTestata findByGuid(String guid) {

	if (StringUtils.isBlank(guid)) {
	    throw new IllegalArgumentException("Il parametro guid non può essere nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("guid", guid, String.class));
	filterTable.addRestriction(fr);
	List<MovimentiZipLogicoTestata> list = this.movimentiZipLogicoTestataDAO.findByFilterTable(filterTable, 0, 2);
	if (list.isEmpty() || list.size() > 1) {
	    throw new BusinessValidationException(
		    "Errore nella ricerca dello zip logico per guid [" + guid + "] tornati " + list.size() + " records");
	}
	return list.get(0);
    }
}
