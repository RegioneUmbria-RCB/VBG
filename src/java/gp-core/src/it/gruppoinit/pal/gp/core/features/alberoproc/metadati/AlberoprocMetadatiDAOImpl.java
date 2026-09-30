package it.gruppoinit.pal.gp.core.features.alberoproc.metadati;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocMetadati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class AlberoprocMetadatiDAOImpl extends BaseDAOImpl<AlberoprocMetadati, AlberoprocMetadatiId> implements AlberoprocMetadatiDAO {

    @Override
    public Class<AlberoprocMetadati> getEntityClass() {

	return AlberoprocMetadati.class;
    }

    @Override
    public void delete(Integer codiceInterventoProc, String chiave) {

	AlberoprocMetadati metadato = this.findById(new AlberoprocMetadatiId(ORMHelper.getIdcomune(), codiceInterventoProc, chiave));
	if (metadato != null) {
	    this.delete(metadato);
	}
    }

    @Override
    public AlberoprocMetadati findByInterventoEChiave(Integer codiceInterventoProc, String chiave) {

	return this.findById(new AlberoprocMetadatiId(ORMHelper.getIdcomune(), codiceInterventoProc, chiave));
    }

    @Override
    public void insert(Integer codiceInterventoProc, String chiave, String valore) {

	if (StringUtils.isEmpty(chiave)) {
	    throw new IllegalArgumentException("Non è stato indicato il metadato da inserire");
	}
	if (StringUtils.isEmpty(valore)) {
	    throw new IllegalArgumentException("Non è stato impostato il valore del metadato da inserire");
	}
	AlberoprocMetadati metadato = this.findById(new AlberoprocMetadatiId(ORMHelper.getIdcomune(), codiceInterventoProc, chiave));
	if (metadato != null) {
	    throw new InvalidOperationException(String.format("Esiste già il metadato %s per l'interevnto corrente", chiave));
	}
	this.insert(new AlberoprocMetadati(codiceInterventoProc, chiave, valore));
    }

    @Override
    public Set<AlberoprocMetadati> findAllByIntervento(Integer codiceInterventoProc) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "alberoproc.software", String.class));
	fr.addFilterField(
		FilterUtils.in("scCodice", getPercorsoScCodiceByCodiceIntervento(codiceInterventoProc).toArray(), "alberoproc", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("scCodice", "alberoproc"));
	ft.addOrder(FilterUtils.orderAsc("id.chiave"));
	List<AlberoprocMetadati> elenco = this.findByFilterTable(ft);
	return new HashSet<AlberoprocMetadati>(elenco);
    }

    @Override
    public AlberoprocMetadati findByInterventoRicorsivoEChiave(Integer codiceInterventoProc, String chiave) {

	//1. Recupero la lista degli interventi
	List<String> scCodici = this.getPercorsoScCodiceByCodiceIntervento(codiceInterventoProc);
	//2. Recupero i metadati in maniera ricorsiva
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.in("scCodice", scCodici.toArray(), "alberoproc", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.chiave", chiave, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("scCodice", "alberoproc"));
	List<AlberoprocMetadati> elenco = this.findByFilterTable(ft);
	//3. Torno la prima occorrenza
	if (elenco.isEmpty()) {
	    return null;
	}
	return elenco.get(0);
    }

    private List<String> getPercorsoScCodiceByCodiceIntervento(Integer codiceInterventoProc) {

	Alberoproc albero = (Alberoproc) getHibernateTemplate().get(Alberoproc.class, new PkId(codiceInterventoProc));
	return albero.getPercorsoScCodice();
    }

    @Override
    public List<AlberoprocMetadati> findAllByChiave(String chiave) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.chiave", chiave, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("scCodice", "alberoproc"));
	return this.findByFilterTable(ft);
    }

    @Override
    public boolean metadatiPresenti(List<String> chiavi) {

	//1. Preparazione filter table
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.in("id.chiave", chiavi.toArray(), String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("scCodice", "alberoproc"));
	//2. Uso la count per verificare se ci sono override configurati
	return this.countRecord(ft) > 0;
    }
}
