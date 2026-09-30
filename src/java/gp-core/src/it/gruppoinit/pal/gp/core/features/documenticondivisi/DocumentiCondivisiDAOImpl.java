package it.gruppoinit.pal.gp.core.features.documenticondivisi;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.DocumentiCondivisi;
import it.gruppoinit.pal.gp.core.domain.DocumentiCondivisiLogs;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class DocumentiCondivisiDAOImpl extends BaseDAOImpl implements IDocumentiCondivisiDAO {

    @Override
    public Class getEntityClass() {

	return DocumentiCondivisi.class;
    }

    @Override
    public boolean documentoPresente(Integer idDocumento) {

	if (idDocumento == null) {
	    throw new IllegalArgumentException("Impossibile richiamare documentoPresente(Integer idDocumento) con idDocumento null");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codiceOggetto", idDocumento, Integer.class));
	ft.addRestriction(fr);
	return this.existsRecords(ft);
    }

    @Override
    public void impostaStato(Integer idDocumento, StatiDocumentiCondivisiEnum daCondividere) {

	if (idDocumento == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare impostaStato(Integer idDocumento, StatiDocumentiCondivisiEnum daCondividere) con idDocumento null");
	}
	if (daCondividere == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare impostaStato(Integer idDocumento, StatiDocumentiCondivisiEnum daCondividere) con daCondividere null");
	}
	DocumentiCondivisi doc = (DocumentiCondivisi) this.getById(DocumentiCondivisi.class, idDocumento);
	doc.setStato(daCondividere.getValore());
	this.update(doc);
    }

    @Override
    public Set<DocumentiCondivisi> condividiDocumentiIstanza(Set<Integer> idDocumenti, Integer codiceIstanza) {

	if (idDocumenti == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentiIstanza(Set<Integer> idDocumenti, Integer codiceIstanza) con idDocumenti null");
	}
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentiIstanza(Set<Integer> idDocumenti, Integer codiceIstanza) con codiceIstanza null");
	}
	Set<DocumentiCondivisi> retVal = new HashSet<DocumentiCondivisi>(0);
	if (idDocumenti.isEmpty()) {
	    return retVal;
	}
	for (Iterator<Integer> documento = idDocumenti.iterator(); documento.hasNext();) {
	    DocumentiCondivisi doc = this.condividiDocumentoIstanza(documento.next(), codiceIstanza);
	    if (doc != null) {
		retVal.add(doc);
	    }
	}
	return retVal;
    }

    @Override
    public Set<DocumentiCondivisi> condividiDocumentiMovimento(Set<Integer> idDocumenti, Integer codiceIstanza, Integer codiceMovimento) {

	if (idDocumenti == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentiMovimento(Set<Integer> idDocumenti, Integer codiceMovimento) con idDocumenti null");
	}
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentiMovimento(Set<Integer> idDocumenti, Integer codiceIstanza, Integer codiceMovimento) con codiceIstanza null");
	}
	if (codiceMovimento == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentiMovimento(Set<Integer> idDocumenti, Integer codiceMovimento) con codiceMovimento null");
	}
	Set<DocumentiCondivisi> retVal = new HashSet<DocumentiCondivisi>(0);
	if (idDocumenti.isEmpty()) {
	    return retVal;
	}
	for (Iterator<Integer> documento = idDocumenti.iterator(); documento.hasNext();) {
	    retVal.add(this.condividiDocumentoMovimento(documento.next(), codiceIstanza, codiceMovimento));
	}
	return retVal;
    }

    @Override
    public DocumentiCondivisi condividiDocumentoIstanza(Integer idDocumento, Integer codiceIstanza) {

	if (idDocumento == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentoIstanza(Integer idDocumento, Integer codiceIstanza) con idDocumento null");
	}
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentoIstanza(Integer idDocumento, Integer codiceIstanza) con codiceIstanza null");
	}
	if (this.documentoPresente(idDocumento)) {
	    return null;
	}
	DocumentiCondivisi doc = new DocumentiCondivisi(idDocumento, codiceIstanza);
	this.insert(doc);
	return doc;
    }

    @Override
    public DocumentiCondivisi condividiDocumentoMovimento(Integer idDocumento, Integer codiceIstanza, Integer codiceMovimento) {

	if (idDocumento == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentoIstanza(Integer idDocumento, Integer codiceIstanza) con idDocumento null");
	}
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentoIstanza(Integer idDocumento, Integer codiceIstanza) con codiceIstanza null");
	}
	if (codiceMovimento == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare condividiDocumentoMovimento(Integer idDocumento, Integer codiceIstanza, Integer codiceMovimento) con codiceMovimento null");
	}
	if (this.documentoPresente(idDocumento)) {
	    return null;
	}
	DocumentiCondivisi doc = new DocumentiCondivisi(idDocumento, codiceIstanza, codiceMovimento);
	this.insert(doc);
	return doc;
    }

    @Override
    public void deleteByCodiceOggetto(Integer codiceOggetto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codiceOggetto", codiceOggetto, Integer.class));
	ft.addRestriction(fr);
	List<DocumentiCondivisi> list = findByFilterTable(ft);
	if (!list.isEmpty()) {
	    for (Iterator<DocumentiCondivisi> dc = list.iterator(); dc.hasNext();) {
		this.delete(dc);
	    }
	}
    }

    @Override
    public void condividiDocumenti(List<DocumentiCondivisiHelper> docDaCondividere) {

    }

    @Override
    public void deleteByCodiceMovimento(Integer codiceMovimento) {

	if (codiceMovimento == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteByCodiceMovimento senza passare il riferimento movimento");
	}
	//1. Cancellazione massiva da documenti_condivisi_logs
	String sql = "delete from documenti_condivisi_logs where idcomune = ? and fk_iddoccondiviso in ( select id from documenti_condivisi where idcomune = ? and codicemovimento = ? )";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(DocumentiCondivisiLogs.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setString(1, ORMHelper.getIdcomune());
	query.setInteger(2, codiceMovimento);
	query.executeUpdate();
	//2. Cancellazione massiva da documenti_condivisi
	sql = "delete from documenti_condivisi where idcomune = ? and codicemovimento = ?";
	query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(DocumentiCondivisi.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codiceMovimento);
	query.executeUpdate();
    }
}
