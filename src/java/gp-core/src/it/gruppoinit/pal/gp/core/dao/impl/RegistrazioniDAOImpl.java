package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.AmministrazioniDAO;
import it.gruppoinit.pal.gp.core.dao.AnagrafeDAO;
import it.gruppoinit.pal.gp.core.dao.ContiDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiDDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiUsoDAO;
import it.gruppoinit.pal.gp.core.dao.RegistrazioniCausaliDAO;
import it.gruppoinit.pal.gp.core.dao.RegistrazioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.RaggruppamentoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.SituazioneContabileAnnoComparator;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggio;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniDaMercato;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniStatisticheMercati;
import it.gruppoinit.pal.gp.core.domain.SituazioneContabile;
import it.gruppoinit.pal.gp.core.domain.helper.RegistrazioniFilterMercatoUsoPosteggioComparator;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class RegistrazioniDAOImpl extends BaseDAOImpl<Registrazioni, PkId> implements RegistrazioniDAO {

    private static final String ALIAS_CODICE_REGISTRAZIONE = "codiceRegistrazione";
    private static final String ALIAS_DATA_REGISTRAZIONE = "dataRegistrazione";
    private static final String ALIAS_ANNO_REGISTRAZIONE = "annoRegistrazione";
    private MercatiDAO mercatiDAO;
    private AnagrafeDAO anagrafeDAO;
    private RegistrazioniCausaliDAO registrazioniCausaliDAO;
    private MercatiUsoDAO mercatiUsoDAO;
    private AlberoprocDAO alberoprocDAO;
    private ContiDAO contiDAO;
    private AmministrazioniDAO amministrazioniDAO;
    private MercatiDDAO mercatiDDAO;

    @Autowired
    public void setMercatiDDAO(MercatiDDAO mercatiDDAO) {

	this.mercatiDDAO = mercatiDDAO;
    }

    @Autowired
    public void setAmministrazioniDAO(AmministrazioniDAO amministrazioniDAO) {

	this.amministrazioniDAO = amministrazioniDAO;
    }

    @Autowired
    public void setContiDAO(ContiDAO contiDAO) {

	this.contiDAO = contiDAO;
    }

    @Autowired
    public void setAlberoprocDAO(AlberoprocDAO alberoprocDAO) {

	this.alberoprocDAO = alberoprocDAO;
    }

    @Autowired
    public void setMercatiUsoDAO(MercatiUsoDAO mercatiUsoDAO) {

	this.mercatiUsoDAO = mercatiUsoDAO;
    }

    @Autowired
    public void setRegistrazioniCausaliDAO(RegistrazioniCausaliDAO registrazioniCausaliDAO) {

	this.registrazioniCausaliDAO = registrazioniCausaliDAO;
    }

    @Autowired
    public void setAnagrafeDAO(AnagrafeDAO anagrafeDAO) {

	this.anagrafeDAO = anagrafeDAO;
    }

    @Autowired
    public void setMercatiDAO(MercatiDAO mercatiDAO) {

	this.mercatiDAO = mercatiDAO;
    }

    @Override
    public Class<Registrazioni> getEntityClass() {

	return Registrazioni.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Registrazioni> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public Integer findMaxProgressivo(String year) {

	// §§§BEGIN§§§
	Integer val;
	// creo due oggetti Date da passare come parametri della query
	Date FromDate = null;
	Date ToDate = null;
	try {
	    // formatto le date nel formato yyyy-MM-dd
	    FromDate = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-01-01");
	    ToDate = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-12-31");
	} catch (ParseException e) {
	    throw new RuntimeException(e);
	}
	// Query HQL
	String query = "select " + " max(reg.progressivo) from Registrazioni reg" + " where reg.dataRegistrazione >=  :FromDate "
		+ "    and reg.dataRegistrazione <=  :ToDate";
	String paramNames[] = new String[2];
	paramNames[0] = "FromDate";
	paramNames[1] = "ToDate";
	Date values[] = new Date[2];
	values[0] = FromDate;
	values[1] = ToDate;
	// findByNamedParam accetta la query,array di parametri,array di valori
	String progressivo = (String) getHibernateTemplate().findByNamedParam(query, paramNames, values).get(0);
	// se il progressivo è diverso da null allora recupero il valore del max
	// progressivo
	// se il progressivo è null allora imposto come valore val=0
	if (progressivo != null) {
	    String[] numProg = progressivo.split("/");
	    String num = numProg[0];
	    int beginInt = 0;
	    for (int i = 0; i < num.length(); i++) {
		char numChar = num.charAt(i);
		if (numChar != '0') {
		    beginInt = i;
		    break;
		}
	    }
	    val = Integer.parseInt(num.substring(beginInt));
	} else {
	    val = 0;
	}
	return val;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Registrazioni> findByAnagrafe(Anagrafe anagrafe) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.eq("anagrafe", anagrafe));
	return (List<Registrazioni>) getHibernateTemplate().findByCriteria(det);
    }

    /**
     * metodo che esegue la query per generare i risultati a partire dalla maschera di ricerca
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<RegistrazioniFilter> searchRegistrazioni(RegistrazioniFilter registrazioniFilter) {

	// §§§BEGIN§§§
	// list che il metodo ritornerà
	List<RegistrazioniFilter> registrazioniFilterList = new ArrayList<RegistrazioniFilter>();
	// valore enum settatto nel maschera di ricerca
	RaggruppamentoEnum raggruppamentoEnum = registrazioniFilter.getRaggruppamentoEnum();
	// numero dei valori dei parametri da passare alla query
	Integer numValues = 2;
	Date FromDate = new Date();
	Date ToDate = new Date();
	Anagrafe anagrafe = new Anagrafe();
	Amministrazioni amministrazioni = new Amministrazioni();
	Mercati mercati = new Mercati();
	MercatiUso mercatiUso = new MercatiUso();
	Conti conti = new Conti();
	RegistrazioniCausali registrazioniCausali = new RegistrazioniCausali();
	Alberoproc alberoproc = new Alberoproc();
	// variabili per verificare la presenza o meno del campo di ricerca
	boolean dateStartSearch = false;
	boolean dateEndSearch = false;
	boolean anagrafeSearch = false;
	boolean amministrazioniSearch = false;
	boolean registrazioniCausaliSearch = false;
	boolean contiSearch = false;
	boolean mercatiSearch = false;
	boolean mercatiUsoSearch = false;
	boolean alberoprocSearch = false;
	// stringa del group by per la query
	String groupBy = "";
	// stringa from per la query
	String from = "";
	// stringa where per la query
	String where = " where _registrazioni.software.codice = :registrazioneSoftware" + " and _registrazioni.id.idcomune = :registrazioneIdcomune";
	if (raggruppamentoEnum == RaggruppamentoEnum.AMMINISTRAZIONE) {
	    from = from + " inner join _regIoAssegnazioni.registrazioniInOut _registrazioniInOut ";
	    groupBy = " _registrazioniInOut.amministrazioni.id.codice ";
	}
	if (raggruppamentoEnum == RaggruppamentoEnum.ANAGRAFE) {
	    groupBy = " _registrazioni.anagrafe.id.codice , _registrazioni.anno ";
	}
	if (raggruppamentoEnum == RaggruppamentoEnum.CONTO) {
	    groupBy = " _conti.id.codice ";
	}
	if (raggruppamentoEnum == RaggruppamentoEnum.CAUSALE) {
	    groupBy = " _registrazioni.registrazioniCausali.id.codice";
	}
	// aggiunti
	if (raggruppamentoEnum == RaggruppamentoEnum.MERCATO) {
	    from = from
		    + "inner join _registrazioni.mercatiD _mercatiD inner join _mercatiD.mercati _mercati inner join _registrazioni.mercatiUso _mercatiUso  ";
	    groupBy = "  _mercatiD.id.codice, _mercati.id.codice , _mercatiUso.id.codice ";
	}
	String query = "select " + groupBy + ", _registrazioni.id.codice  from Registrazioni _registrazioni "
		+ "left outer join _registrazioni.registrazioniImportis  _registrazioniImporti  " + "inner join  _registrazioniImporti.conti _conti "
		+ "left outer join _registrazioniImporti.regIoAssegnazionis _regIoAssegnazioni ";
	if (registrazioniFilter.getDataInizio() != null || registrazioniFilter.getDataFine() != null) {
	    from = from + " inner join _regIoAssegnazioni.registrazioniInOut _registrazioniInOut ";
	}
	if (registrazioniFilter.getDataInizio() != null) {
	    // creo due oggetti Date da passare come parametri della query
	    FromDate = registrazioniFilter.getDataInizio();
	    numValues = numValues + 1;
	    where = where + " and _registrazioniInOut.dataDistinta >=  :FromDate";
	    dateStartSearch = true;
	}
	if (registrazioniFilter.getDataFine() != null) {
	    // creo due oggetti Date da passare come parametri della query
	    ToDate = registrazioniFilter.getDataFine();
	    numValues = numValues + 1;
	    where = where + " and _registrazioniInOut.dataDistinta <=  :ToDate";
	    dateEndSearch = true;
	}
	if (registrazioniFilter.getAnagrafe().getId().getCodice() != null) {
	    anagrafe = registrazioniFilter.getAnagrafe();
	    from = from + " inner join _registrazioni.anagrafe _anagrafe";
	    where = where + " and _registrazioni.anagrafe.id.codice = :Anagrafe";
	    numValues += 1;
	    anagrafeSearch = true;
	}
	if (registrazioniFilter.getRegistrazioniCausali().getId().getCodice() != null) {
	    registrazioniCausali = registrazioniFilter.getRegistrazioniCausali();
	    from = from + "";
	    where = where + " and _registrazioni.registrazioniCausali.id.codice = :RegistrazioniCausali";
	    numValues += 1;
	    registrazioniCausaliSearch = true;
	}
	if (registrazioniFilter.getConti().getId().getCodice() != null) {
	    conti = registrazioniFilter.getConti();
	    where = where + " and _conti.id.codice = :Conti";
	    numValues += 1;
	    contiSearch = true;
	}
	if (registrazioniFilter.getAmministrazioni().getId().getCodice() != null) {
	    amministrazioni = registrazioniFilter.getAmministrazioni();
	    if (raggruppamentoEnum != RaggruppamentoEnum.AMMINISTRAZIONE && registrazioniFilter.getDataInizio() == null
		    && registrazioniFilter.getDataFine() == null) {
		from = from + " inner join _regIoAssegnazioni.registrazioniInOut _registrazioniInOut ";
	    }
	    where = where + " and _registrazioniInOut.amministrazioni.id.codice = :Amministrazioni";
	    numValues += 1;
	    amministrazioniSearch = true;
	}
	if (registrazioniFilter.getMercati().getId().getCodice() != null) {
	    mercati = registrazioniFilter.getMercati();
	    from = from + "inner join _registrazioni.mercatiD _mercatiD inner join _mercatiD.mercati _mercati ";
	    where = where + " and _mercati.id.codice = :Mercati";
	    numValues += 1;
	    mercatiSearch = true;
	}
	if (registrazioniFilter.getMercatiUso().getId().getCodice() != null) {
	    mercatiUso = registrazioniFilter.getMercatiUso();
	    from = from + "inner join _registrazioni.mercatiUso _mercatiUso ";
	    where = where + " and _mercatiUso.id.codice = :MercatiUso";
	    numValues += 1;
	    mercatiUsoSearch = true;
	}
	if (registrazioniFilter.getAlberoproc().getId().getCodice() != null) {
	    alberoproc = registrazioniFilter.getAlberoproc();
	    from = from + " inner join _registrazioni.istanze _istanze inner join _istanze.alberoproc _alberoproc ";
	    where = where + " and _alberoproc.scCodice like :scCodice and _alberoproc.software.codice = :alberoProcSW ";
	    numValues += 2;
	    alberoprocSearch = true;
	}
	String paramNames[] = new String[numValues];
	Object values[] = new Object[numValues];
	numValues -= 1;
	paramNames[numValues] = "registrazioneSoftware";
	values[numValues] = (String) ORMHelper.getSoftware();
	numValues -= 1;
	paramNames[numValues] = "registrazioneIdcomune";
	values[numValues] = (String) ORMHelper.getIdcomune();
	if (dateStartSearch) {
	    numValues -= 1;
	    paramNames[numValues] = "FromDate";
	    values[numValues] = (Date) FromDate;
	}
	if (dateEndSearch) {
	    numValues -= 1;
	    paramNames[numValues] = "ToDate";
	    values[numValues] = (Date) ToDate;
	}
	if (anagrafeSearch) {
	    numValues -= 1;
	    paramNames[numValues] = "Anagrafe";
	    values[numValues] = (Integer) anagrafe.getId().getCodice();
	}
	if (registrazioniCausaliSearch) {
	    numValues -= 1;
	    paramNames[numValues] = "RegistrazioniCausali";
	    values[numValues] = (Integer) registrazioniCausali.getId().getCodice();
	}
	if (contiSearch) {
	    numValues -= 1;
	    paramNames[numValues] = "Conti";
	    values[numValues] = (Integer) conti.getId().getCodice();
	}
	if (amministrazioniSearch) {
	    numValues -= 1;
	    paramNames[numValues] = "Amministrazioni";
	    values[numValues] = (Integer) amministrazioni.getId().getCodice();
	}
	if (mercatiSearch) {
	    numValues -= 1;
	    paramNames[numValues] = "Mercati";
	    values[numValues] = (Integer) mercati.getId().getCodice();
	}
	if (mercatiUsoSearch) {
	    numValues -= 1;
	    paramNames[numValues] = "MercatiUso";
	    values[numValues] = (Integer) mercatiUso.getId().getCodice();
	}
	if (alberoprocSearch) {
	    numValues -= 1;
	    paramNames[numValues] = "scCodice";
	    values[numValues] = (String) alberoproc.getScCodice() + "%";
	    numValues -= 1;
	    paramNames[numValues] = "alberoProcSW";
	    values[numValues] = (String) ORMHelper.getSoftware();
	}
	query = query + from + where;
	query = query + " group by " + groupBy + ", _registrazioni.id.codice  ";
	List<Object> obList = (List<Object>) getHibernateTemplate().findByNamedParam(query, paramNames, values);
	// creo un set di elementi per cui ho raggruppato la query
	Set<Object> raggruppamentoSet = new HashSet<Object>();
	for (Iterator iterator = obList.iterator(); iterator.hasNext();) {
	    Object[] object = (Object[]) iterator.next();
	    PkId id = new PkId((Integer) object[0]);
	    if (raggruppamentoEnum == RaggruppamentoEnum.AMMINISTRAZIONE) {
		Amministrazioni amministrazioni2 = new Amministrazioni();
		amministrazioni2 = amministrazioniDAO.findById(id);
		raggruppamentoSet.add(amministrazioni2);
	    }
	    if (raggruppamentoEnum == RaggruppamentoEnum.ANAGRAFE) {
		Anagrafe anagrafe2 = new Anagrafe();
		anagrafe2 = anagrafeDAO.findById(id);
		raggruppamentoSet.add(anagrafe2);
	    }
	    if (raggruppamentoEnum == RaggruppamentoEnum.CONTO) {
		Conti conti2 = new Conti();
		conti2 = contiDAO.findById(id);
		raggruppamentoSet.add(conti2);
	    }
	    if (raggruppamentoEnum == RaggruppamentoEnum.CAUSALE) {
		RegistrazioniCausali causali = new RegistrazioniCausali();
		causali = registrazioniCausaliDAO.findById(id);
		raggruppamentoSet.add(causali);
	    }
	    // aggiunto
	    if (raggruppamentoEnum == RaggruppamentoEnum.MERCATO) {
		MercatiD mercatiD = new MercatiD();
		mercatiD = mercatiDDAO.findById(id);
		raggruppamentoSet.add(mercatiD);
	    }
	}
	// scorro il set e determino il totale di importo e incassato per ogni elemento del set
	for (Object object : raggruppamentoSet) {
	    BigDecimal emesso = new BigDecimal(0);
	    BigDecimal incassato = new BigDecimal(0);
	    BigDecimal saldo = new BigDecimal(0);
	    RegistrazioniFilter regFilter = new RegistrazioniFilter();
	    // itero la lista risultante della query e popolo la lista di
	    // registrazioni filter che verrà restituita dal
	    // metodo
	    // utilizzato solo nel caso di raggruppamento per mercati
	    Mercati mercati2 = new Mercati();
	    MercatiUso mercatiUso2 = new MercatiUso();
	    for (Iterator iterator = obList.iterator(); iterator.hasNext();) {
		Object[] row = (Object[]) iterator.next();
		if (raggruppamentoEnum == RaggruppamentoEnum.AMMINISTRAZIONE) {
		    Amministrazioni amministrazioni2 = (Amministrazioni) object;
		    PkId id = new PkId((Integer) row[0]);
		    if (amministrazioni2 != null) {
			if (id.getCodice().compareTo(amministrazioni2.getId().getCodice()) == 0) {
			    Registrazioni registrazioni = this.findById(new PkId((Integer) row[1]));
			    emesso = emesso.add(registrazioni.getImporto());
			    incassato = incassato.add(registrazioni.getIncassato());
			}
		    }
		}
		if (raggruppamentoEnum == RaggruppamentoEnum.ANAGRAFE) {
		    Anagrafe anagrafe2 = (Anagrafe) object;
		    PkId id = new PkId((Integer) row[0]);
		    regFilter.setAnagrafe(anagrafe2);
		    regFilter.setAnno((Short) row[1]);
		    if (anagrafe2 != null) {
			if (id.getCodice().compareTo(anagrafe2.getId().getCodice()) == 0) {
			    Registrazioni registrazioni = this.findById(new PkId((Integer) row[2]));
			    emesso = emesso.add(registrazioni.getImporto());
			    incassato = incassato.add(registrazioni.getIncassato());
			}
		    }
		}
		if (raggruppamentoEnum == RaggruppamentoEnum.CONTO) {
		    Conti conti2 = (Conti) object;
		    PkId id = new PkId((Integer) row[0]);
		    if (conti2 != null) {
			if (id.getCodice().compareTo(conti2.getId().getCodice()) == 0) {
			    Registrazioni registrazioni = this.findById(new PkId((Integer) row[1]));
			    Set<RegistrazioniImporti> set = registrazioni.getRegistrazioniImportis();
			    for (RegistrazioniImporti registrazioniImporti : set) {
				if (registrazioniImporti.getConti().getId().getCodice().compareTo(conti2.getId().getCodice()) == 0) {
				    emesso = emesso.add(registrazioniImporti.getImporto());
				    incassato = incassato.add(registrazioniImporti.getIncassato());
				}
			    }
			}
		    }
		}
		if (raggruppamentoEnum == RaggruppamentoEnum.CAUSALE) {
		    RegistrazioniCausali causali = (RegistrazioniCausali) object;
		    PkId id = new PkId((Integer) row[0]);
		    if (causali != null) {
			if (id.getCodice().compareTo(causali.getId().getCodice()) == 0) {
			    Registrazioni registrazioni = this.findById(new PkId((Integer) row[1]));
			    emesso = emesso.add(registrazioni.getImporto());
			    incassato = incassato.add(registrazioni.getIncassato());
			}
		    }
		}
		if (raggruppamentoEnum == RaggruppamentoEnum.MERCATO) {
		    MercatiD mercatiD = (MercatiD) object;
		    PkId id = new PkId((Integer) row[0]);
		    if (mercatiD != null) {
			if (id.getCodice().compareTo(mercatiD.getId().getCodice()) == 0) {
			    Registrazioni registrazioni = this.findById(new PkId((Integer) row[3]));
			    mercati2 = mercatiDAO.findById(new PkId((Integer) row[1]));
			    mercatiUso2 = mercatiUsoDAO.findById(new PkId((Integer) row[2]));
			    emesso = emesso.add(registrazioni.getImporto());
			    incassato = incassato.add(registrazioni.getIncassato());
			}
		    }
		}
	    }
	    if (raggruppamentoEnum == RaggruppamentoEnum.AMMINISTRAZIONE) {
		Amministrazioni amministrazioni2 = (Amministrazioni) object;
		regFilter.setAmministrazioni(amministrazioni2);
	    }
	    if (raggruppamentoEnum == RaggruppamentoEnum.ANAGRAFE) {
		Anagrafe anagrafe2 = (Anagrafe) object;
		regFilter.setAnagrafe(anagrafe2);
	    }
	    if (raggruppamentoEnum == RaggruppamentoEnum.CONTO) {
		Conti conti2 = (Conti) object;
		regFilter.setConti(conti2);
	    }
	    if (raggruppamentoEnum == RaggruppamentoEnum.CAUSALE) {
		RegistrazioniCausali causali = (RegistrazioniCausali) object;
		regFilter.setRegistrazioniCausali(causali);
	    }
	    if (raggruppamentoEnum == RaggruppamentoEnum.MERCATO) {
		MercatiD mercatiD = (MercatiD) object;
		regFilter.setPosteggio(mercatiD);
		regFilter.setMercati(mercati2);
		regFilter.setMercatiUso(mercatiUso2);
	    }
	    regFilter.setEmesso(emesso);
	    regFilter.setIncassato(incassato);
	    saldo = regFilter.getEmesso().subtract(regFilter.getIncassato());
	    regFilter.setSaldo(saldo);
	    regFilter.setRaggruppamentoEnum(registrazioniFilter.getRaggruppamentoEnum());
	    registrazioniFilterList.add(regFilter);
	}
	if (raggruppamentoEnum == RaggruppamentoEnum.MERCATO) {
	    Collections.sort(registrazioniFilterList, new RegistrazioniFilterMercatoUsoPosteggioComparator());
	}
	return registrazioniFilterList;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings({ "unchecked", "static-access" })
    public List<RegistrazioniStatisticheMercati> findRegByMercato(Integer idMercato) {

	// §§§BEGIN§§§
	List<RegistrazioniStatisticheMercati> registrazioniStatisticheList = new ArrayList<RegistrazioniStatisticheMercati>();
	Registrazioni registrazioni = new Registrazioni();
	MercatiD mercatiD = new MercatiD();
	PkId id = new PkId(idMercato);
	Mercati mercati = mercatiDAO.findById(id);
	mercatiD.setMercati(mercati);
	registrazioni.setMercatiD(mercatiD);
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("mercatiD", "_mercatiD", criteria.INNER_JOIN);
	criteria.add(Restrictions.eq("software", mercati.getSoftware()));
	criteria.add(Restrictions.eq("_mercatiD.mercati.id.codice", mercati.getId().getCodice()));
	ProjectionList projList = Projections.projectionList();
	projList.add(Projections.groupProperty("anno"));
	projList.add(Projections.groupProperty("id.codice"));
	criteria.setProjection(projList);
	List<Object> list = getHibernateTemplate().findByCriteria(criteria);
	Iterator iter = list.iterator();
	RegistrazioniStatisticheMercati temp = null;
	Registrazioni reg = null;
	Set<Short> groupYear = new HashSet<Short>();
	while (iter.hasNext()) {
	    Object[] obj = (Object[]) iter.next();
	    groupYear.add((Short) obj[0]);
	}
	Iterator iter2 = groupYear.iterator();
	while (iter2.hasNext()) {
	    Short annoCorrente = (Short) iter2.next();
	    Iterator record = list.iterator();
	    BigDecimal importoTotale = new BigDecimal(0);
	    BigDecimal incassoTotale = new BigDecimal(0);
	    BigDecimal rimanenzaTotale = new BigDecimal(0);
	    while (record.hasNext()) {
		Object[] obj2 = (Object[]) record.next();
		Short year = (Short) obj2[0];
		if (year.compareTo(annoCorrente) == 0) {
		    reg = this.findById(new PkId((Integer) obj2[1]));
		    Set<RegistrazioniImporti> set = reg.getRegistrazioniImportis();
		    for (RegistrazioniImporti registrazioniImporti : set) {
			importoTotale = importoTotale.add(registrazioniImporti.getImporto());
			incassoTotale = incassoTotale.add(registrazioniImporti.getIncassato());
		    }
		    rimanenzaTotale = importoTotale.subtract(incassoTotale);
		}
	    }
	    temp = new RegistrazioniStatisticheMercati(annoCorrente.intValue(), incassoTotale, rimanenzaTotale, importoTotale);
	    registrazioniStatisticheList.add(temp);
	}
	return registrazioniStatisticheList;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RegistrazioniDaMercato> findRegByMercatoForCausale(short anno, Integer idMercato, MercatiUso uso) {

	// §§§BEGIN§§§
	List<RegistrazioniDaMercato> registrazioniDaMercatoList = new ArrayList<RegistrazioniDaMercato>();
	String query = "select _registrazioni.registrazioniCausali.id.codice,_registrazioni.id.codice  from Registrazioni _registrazioni inner join  _registrazioni.registrazioniImportis _registrazioniImporti left outer join _registrazioniImporti.regIoAssegnazionis _regIoAssegnazioni inner join _registrazioni.mercatiD _mercatiD inner join _mercatiD.mercati _mercati "
		+ " where _mercati.id.codice = :mercatiCodice and  _registrazioni.software.codice = :software and _registrazioni.id.idcomune = :idcomune and _registrazioni.anno = :anno and _registrazioni.mercatiUso.id.codice = :mercatoUso"
		+ " group by _registrazioni.registrazioniCausali.id.codice,_registrazioni.id.codice ";
	String paramNames[] = new String[5];
	paramNames[0] = "mercatiCodice";
	paramNames[1] = "software";
	paramNames[2] = "idcomune";
	paramNames[3] = "anno";
	paramNames[4] = "mercatoUso";
	Object values[] = new Object[5];
	values[0] = (Integer) idMercato;
	values[1] = (String) ORMHelper.getSoftware();
	values[2] = (String) ORMHelper.getIdcomune();
	values[3] = (short) anno;
	values[4] = (Integer) uso.getId().getCodice();
	List<Object> objectList = (List<Object>) getHibernateTemplate().findByNamedParam(query, paramNames, values);
	// creo il set di registrazioni causali
	Set<RegistrazioniCausali> regCausaliSet = new HashSet<RegistrazioniCausali>();
	for (Object object : objectList) {
	    Object[] row = (Object[]) object;
	    RegistrazioniCausali causali = registrazioniCausaliDAO.findById(new PkId((Integer) row[0]));
	    regCausaliSet.add(causali);
	}
	// scorro il set per il raggruppamento
	for (RegistrazioniCausali causali : regCausaliSet) {
	    RegistrazioniDaMercato registrazioniDaMercato = new RegistrazioniDaMercato();
	    BigDecimal importo = new BigDecimal(0);
	    BigDecimal incassato = new BigDecimal(0);
	    BigDecimal rimanenza = new BigDecimal(0);
	    for (Iterator iterator = objectList.iterator(); iterator.hasNext();) {
		Object[] object = (Object[]) iterator.next();
		Registrazioni registrazioni = this.findById(new PkId((Integer) object[1]));
		if (causali.getId().getCodice().compareTo(registrazioni.getRegistrazioniCausali().getId().getCodice()) == 0) {
		    importo = importo.add(registrazioni.getImporto());
		    incassato = incassato.add(registrazioni.getIncassato());
		    rimanenza = rimanenza.add(registrazioni.getRimanenza());
		}
	    }
	    registrazioniDaMercato.setImporto(importo);
	    registrazioniDaMercato.setIncassato(incassato);
	    registrazioniDaMercato.setRimanenza(rimanenza);
	    registrazioniDaMercato.setRegistrazioniCausali(causali);
	    registrazioniDaMercatoList.add(registrazioniDaMercato);
	}
	return registrazioniDaMercatoList;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Registrazioni> findByMercatoUsoData(Mercati mercato, MercatiUso mercatoUso, Date dataRegistrazione,
	    RegistrazioniMercatoEnum registrazioniMercatoEnum) {

	// §§§BEGIN§§§
	List registrazioniDaMercatoList = null;
	List<Registrazioni> answer = new ArrayList<Registrazioni>();
	String query = "";
	query = "select _registrazioni, _presenzeMercatoConcessionari, _presenzeMercatoSpuntisti from Registrazioni _registrazioni "
		+ " left join _registrazioni.presenzeMercatoConcessionari" + " _presenzeMercatoConcessionari "
		+ " left join _registrazioni.presenzeMercatoSpuntisti" + " _presenzeMercatoSpuntisti "
		+ " inner join _registrazioni.mercatiD _mercatiD inner join _mercatiD.mercati _mercati "
		+ " where _mercati.id.codice = :mercatiCodice and  _registrazioni.software.codice = :software and"
		+ " _registrazioni.id.idcomune = :idcomune and _registrazioni.dataRegistrazione = :dataRegistrazione "
		+ " and _registrazioni.mercatiUso.id.codice = :mercatoUso ";
	String paramNames[] = new String[5];
	paramNames[0] = "mercatiCodice";
	paramNames[1] = "software";
	paramNames[2] = "idcomune";
	paramNames[3] = "dataRegistrazione";
	paramNames[4] = "mercatoUso";
	Object values[] = new Object[5];
	values[0] = (Integer) mercato.getId().getCodice();
	values[1] = (String) ORMHelper.getSoftware();
	values[2] = (String) ORMHelper.getIdcomune();
	values[3] = dataRegistrazione;
	values[4] = mercatoUso.getId().getCodice();
	registrazioniDaMercatoList = getHibernateTemplate().findByNamedParam(query, paramNames, values);
	for (Iterator iterator = registrazioniDaMercatoList.iterator(); iterator.hasNext();) {
	    Object obj = iterator.next();
	    if (obj instanceof Object[]) {
		Object[] resultObj = (Object[]) obj;
		Registrazioni registrazione = (Registrazioni) resultObj[0];
		MercatipresenzeD presenzaConcessionario = (MercatipresenzeD) resultObj[1];
		MercatipresenzeD presenzaSpuntista = (MercatipresenzeD) resultObj[2];
		switch (registrazioniMercatoEnum) {
		case ALL:
		    answer.add(registrazione);
		    break;
		case CONCESSIONARI:
		    if (presenzaConcessionario != null) {
			answer.add(registrazione);
		    }
		    break;
		case SPUNTISTI:
		    if (presenzaSpuntista != null) {
			answer.add(registrazione);
		    }
		    break;
		default:
		    answer.add(registrazione);
		    break;
		}
	    }
	}
	return answer;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Registrazioni> findByRegistrazioniFilter(RegistrazioniFilter registrazioniFilter) {

	// §§§BEGIN§§§
	DetachedCriteria criteria = getFilterCriteria(registrazioniFilter, false);
	return getHibernateTemplate().findByCriteria(criteria);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    // Metodo privata che setta i criteri in base ai campi di
    // RegistrazioneFilter passati
    // Se si deve creare un altro metodo per fare nuove ricerche con i campi
    // presenti in RegistrazioneFilter
    // aggiungere in semplicemento in questo metodo privato i nuovi filtri
    @SuppressWarnings("static-access")
    private DetachedCriteria getFilterCriteria(RegistrazioniFilter registrazioniFilter, boolean solamenteCodiceregistrazione) {

	// §§§BEGIN§§§
	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	// Filtra per progressivo ( settato il match ANYWHERE)
	if (registrazioniFilter.getProgressivo() != null && !registrazioniFilter.getProgressivo().equals("")
		&& !registrazioniFilter.getProgressivo().equals("%")) {
	    criteria.add(Restrictions.like("progressivo", registrazioniFilter.getProgressivo(), MatchMode.ANYWHERE));
	}
	// Filtra per descrizione ( settato il match ANYWHERE)
	if (registrazioniFilter.getDescrizione() != null && !registrazioniFilter.getDescrizione().equals("")
		&& !registrazioniFilter.getDescrizione().equals("%")) {
	    criteria.add(Restrictions.like("descrizione", registrazioniFilter.getDescrizione(), MatchMode.ANYWHERE));
	}
	// Filtra per data
	if (registrazioniFilter.getDataInizio() != null || registrazioniFilter.getDataFine() != null) {
	    // Filtra per da data di inizio in poi
	    if ((registrazioniFilter.getDataInizio() != null) && (registrazioniFilter.getDataFine() == null)) {
		criteria.add(Restrictions.ge("dataRegistrazione", registrazioniFilter.getDataInizio()));
	    }
	    // Filtra data minore uguale di quella inserita
	    if ((registrazioniFilter.getDataInizio() == null) && (registrazioniFilter.getDataFine() != null)) {
		criteria.add(Restrictions.le("dataRegistrazione", registrazioniFilter.getDataFine()));
	    }
	    // Filtra per data inizio e fine inserite
	    if ((registrazioniFilter.getDataInizio() != null) && (registrazioniFilter.getDataFine() != null)) {
		criteria.add(Restrictions.between("dataRegistrazione", registrazioniFilter.getDataInizio(), registrazioniFilter.getDataFine()));
	    }
	}
	// Filtra per anagrafe
	if (registrazioniFilter.getAnagrafe() != null && registrazioniFilter.getAnagrafe().getId().getCodice() != null) {
	    Anagrafe anagrafe = anagrafeDAO.findById(new PkId(registrazioniFilter.getAnagrafe().getId().getCodice()));
	    registrazioniFilter.setAnagrafe(anagrafe);
	    criteria.add(Restrictions.eq("anagrafeId", registrazioniFilter.getAnagrafe().getId().getCodice()));
	}
	// Filtra per registrazioni causali
	if (registrazioniFilter.getRegistrazioniCausali() != null && registrazioniFilter.getRegistrazioniCausali().getId().getCodice() != null) {
	    RegistrazioniCausali registrazioniCausali = registrazioniCausaliDAO.findById(new PkId(registrazioniFilter.getRegistrazioniCausali()
		    .getId().getCodice()));
	    registrazioniFilter.setRegistrazioniCausali(registrazioniCausali);
	    criteria.add(Restrictions.eq("registrazioniCausaliId", registrazioniFilter.getRegistrazioniCausali().getId().getCodice()));
	}
	// Filtra per mercato uso
	if (registrazioniFilter.getMercatiUso() != null && registrazioniFilter.getMercatiUso().getId().getCodice() != null) {
	    MercatiUso mercatiUso = mercatiUsoDAO.findById(new PkId(registrazioniFilter.getMercatiUso().getId().getCodice()));
	    registrazioniFilter.setMercatiUso(mercatiUso);
	    criteria.add(Restrictions.eq("mercatiUsoId", registrazioniFilter.getMercatiUso().getId().getCodice()));
	}
	// Filtra per mercato
	if (registrazioniFilter.getMercati() != null && registrazioniFilter.getMercati().getId().getCodice() != null) {
	    Mercati mercati = mercatiDAO.findById(new PkId(registrazioniFilter.getMercati().getId().getCodice()));
	    MercatiD mercatiD = mercatiDDAO.findById(new PkId(registrazioniFilter.getPosteggio().getId().getCodice()));
	    criteria.createAlias("mercatiD", "_mercatiD", criteria.INNER_JOIN);
	    // criteria.add(Restrictions.eq("software", mercati.getSoftware()));
	    criteria.add(Restrictions.eq("_mercatiD.mercati.id.codice", mercati.getId().getCodice()));
	    if (mercatiD != null && mercatiD.getId().getCodice() != null) {
		criteria.add(Restrictions.eq("_mercatiD.id.codice", mercatiD.getId().getCodice()));
	    }
	}
	// Filtra per Tipologia di intervento
	if (registrazioniFilter.getAlberoproc() != null && registrazioniFilter.getAlberoproc().getId().getCodice() != null) {
	    Alberoproc alberoproc = alberoprocDAO.findById(new PkId(registrazioniFilter.getAlberoproc().getId().getCodice()));
	    criteria.createAlias("istanze", "_istanze", criteria.INNER_JOIN);
	    criteria.add(Restrictions.eq("software", alberoproc.getSoftware()));
	    criteria.add(Restrictions.eq("_istanze.alberoproc.id.codice", alberoproc.getId().getCodice()));
	}
	// Filtra per conti
	if (registrazioniFilter.getConti() != null && registrazioniFilter.getConti().getId().getCodice() != null) {
	    criteria.createCriteria("registrazioniImportis", "registrazioniImporti");
	    criteria.add(Restrictions.eq("registrazioniImporti.conti.id.codice", registrazioniFilter.getConti().getId().getCodice()));
	}
	if (registrazioniFilter.getAmministrazioni() != null && registrazioniFilter.getAmministrazioni().getId().getCodice() != null) {
	    criteria.createCriteria("registrazioniImportis", "registrazioniImporti");
	    criteria.createCriteria("registrazioniImporti.regIoAssegnazionis", "regIoAssegnazioni");
	    criteria.createAlias("regIoAssegnazioni.registrazioniInOut", "_registrazioniInOut", Criteria.INNER_JOIN);
	    criteria.add(Restrictions.eq("_registrazioniInOut.amministrazioni",
		    amministrazioniDAO.findById(new PkId(registrazioniFilter.getAmministrazioni().getId().getCodice()))));
	}
	// Filtra per importo
	if (registrazioniFilter.getImporto() != null) {
	    criteria.add(Restrictions.eq("importo", registrazioniFilter.getImporto()));
	}
	if (registrazioniFilter.getSaldo() != null) {
	    criteria.createAlias("vwRegistrazionisaldo", "_vwRegistrazionisaldo");
	    criteria.add(Restrictions.ge("_vwRegistrazionisaldo.saldo", registrazioniFilter.getSaldo()));
	}
	if (solamenteCodiceregistrazione) {
	    ProjectionList projectionList = Projections.projectionList();
	    projectionList.add(Projections.groupProperty("id.codice"), ALIAS_CODICE_REGISTRAZIONE);
	    projectionList.add(Projections.groupProperty("dataRegistrazione"));
	    projectionList.add(Projections.groupProperty("anno"));
	    criteria.setProjection(projectionList);
	}
	criteria.addOrder(Order.asc("dataRegistrazione"));
	criteria.addOrder(Order.asc("anno"));
	return criteria;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings({ "static-access", "unchecked" })
    @Override
    public List<Posteggio> findSituazioneContabileByMercatoAndPosteggio(Mercati mercati, MercatiUso mercatiUso) {

	// §§§BEGIN§§§
	// setto tutti i criteri delle query
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("mercatiD", "_mercatiD", criteria.INNER_JOIN);
	criteria.createAlias("_mercatiD.mercati", "_mercati", criteria.INNER_JOIN);
	criteria.add(Restrictions.eq("_mercati.id.codice", mercati.getId().getCodice()));
	criteria.createAlias("mercatiUso", "_mercatiUso");
	criteria.add(Restrictions.eq("_mercatiUso.id.codice", mercatiUso.getId().getCodice()));
	List<Registrazioni> registrazioni = getHibernateTemplate().findByCriteria(criteria);
	Map<String, SituazioneContabile> mappa = new HashMap<String, SituazioneContabile>();
	for (Registrazioni registrazioni2 : registrazioni) {
	    Set<RegistrazioniImporti> regImportis = registrazioni2.getRegistrazioniImportis();
	    SituazioneContabile situazioneContabile = null;
	    for (RegistrazioniImporti registrazioniImporti : regImportis) {
		String key = registrazioni2.getMercatiD().getCodiceposteggio() + "-" + mercatiUso.getId().getCodice() + "-"
			+ registrazioni2.getAnno() + "-" + registrazioniImporti.getConti().getId().getCodice();
		BigDecimal emesso = new BigDecimal(0);
		BigDecimal incassato = new BigDecimal(0);
		if (mappa.get(key) == null) {
		    situazioneContabile = new SituazioneContabile();
		    emesso = emesso.add(registrazioniImporti.getImporto());
		    incassato = incassato.add(registrazioniImporti.getIncassato());
		} else {
		    situazioneContabile = mappa.get(key);
		    emesso = situazioneContabile.getImporto();
		    emesso = emesso.add(registrazioniImporti.getImporto());
		    incassato = situazioneContabile.getIncassato();
		    incassato = incassato.add(registrazioniImporti.getIncassato());
		}
		situazioneContabile.setAnno(registrazioni2.getAnno());
		situazioneContabile.setConti(registrazioniImporti.getConti());
		situazioneContabile.setImporto(emesso);
		situazioneContabile.setIncassato(incassato);
		mappa.put(key, situazioneContabile);
	    }
	}
	// lista che conterrà il risultato che ritorna il metodo
	List<Posteggio> result = new ArrayList<Posteggio>();
	Mercati mercato = mercatiDAO.findById(mercati.getId());
	Set<MercatiD> posteggiSet = mercato.getMercatiDs();
	for (MercatiD mercatiD : posteggiSet) {
	    Posteggio posteggio = new Posteggio();
	    posteggio.setMercatiUso(mercatiUso);
	    posteggio.setPosteggio(mercatiD);
	    List<SituazioneContabile> situazionecontabileList = new ArrayList<SituazioneContabile>();
	    Set<Entry<String, SituazioneContabile>> iterator = mappa.entrySet();
	    for (Entry<String, SituazioneContabile> entry : iterator) {
		String key = entry.getKey();
		String keyToCompare = mercatiD.getCodiceposteggio() + "-" + mercatiUso.getId().getCodice();
		if (key.startsWith(keyToCompare)) {
		    situazionecontabileList.add(entry.getValue());
		}
	    }
	    Comparator comparator = SituazioneContabileAnnoComparator.getInstance();
	    Collections.sort(situazionecontabileList, comparator);
	    posteggio.setSituazioneContabileList(situazionecontabileList);
	    result.add(posteggio);
	}
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    // esegue la query di findSituazioneContabileByMercatoAndPosteggio(Mercati mercati,MercatiUso mercatiUso)
    // per tutti i mercatiUsi presenti in quel mercato
    @Override
    public Vector<List<Posteggio>> findSituazioneContabileByMercatoAndPosteggioAndMercatoUso(Mercati mercati, MercatiUso mercatiUso) {

	// §§§BEGIN§§§
	Vector<List<Posteggio>> result = new Vector<List<Posteggio>>();
	if (mercatiUso == null) {
	    Iterator iter = mercati.getMercatiUsos().iterator();
	    while (iter.hasNext()) {
		List<Posteggio> posteggiolist = findSituazioneContabileByMercatoAndPosteggio(mercati, (MercatiUso) iter.next());
		result.add(posteggiolist);
	    }
	} else {
	    List<Posteggio> posteggiolist = findSituazioneContabileByMercatoAndPosteggio(mercati, mercatiUso);
	    result.add(posteggiolist);
	}
	return result;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Anagrafe> findAnagrafeByRegistrazioni(Anagrafe entity) {

	// §§§BEGIN§§§
	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.createAlias("anagrafe", "_anagrafe");
	if (entity != null) {
	    if (entity.getNominativo() != null && !entity.getNominativo().equals("") && !entity.getNominativo().equals("%")) {
		Criterion nominativo = getCriterionForSplittableString(entity.getNominativo(), "_anagrafe.nominativo", "_anagrafe.nome");
		if (nominativo != null) {
		    det.add(nominativo);
		}
	    }
	}
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.distinct(Projections.property("anagrafe.id.codice")));
	det.setProjection(projectionList);
	List<Integer> temp = getHibernateTemplate().findByCriteria(det);
	List<Anagrafe> anagrafeList = new ArrayList<Anagrafe>();
	for (Integer id : temp) {
	    Anagrafe anagrafe = anagrafeDAO.findById(new PkId(id));
	    anagrafeList.add(anagrafe);
	}
	return anagrafeList;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Registrazioni> findMercatiByRegistrazioniAndAnagrafe(Anagrafe anagrafe) {

	// §§§BEGIN§§§
	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.createAlias("mercatiD", "_mercatiD");
	det.createAlias("mercatiD.mercati", "_mercati");
	det.add(Restrictions.eq("anagrafe.id.codice", anagrafe.getId().getCodice()));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.groupProperty("anagrafe.id.codice"));
	projectionList.add(Projections.groupProperty("_mercati.id.codice"));
	projectionList.add(Projections.groupProperty("_mercatiD.id.codice"));
	projectionList.add(Projections.groupProperty("mercatiUso.id.codice"));
	det.setProjection(projectionList);
	List<Object> listTemp = getHibernateTemplate().findByCriteria(det);
	List<Registrazioni> list = new ArrayList<Registrazioni>();
	for (Object object : listTemp) {
	    Registrazioni registrazioni = new Registrazioni();
	    Object[] resultObj = (Object[]) object;
	    Integer codiceAnagrafe = (Integer) resultObj[0];
	    Anagrafe anagrafe2 = anagrafeDAO.findById(new PkId(codiceAnagrafe));
	    registrazioni.setAnagrafe(anagrafe2);
	    Mercati mercati2 = mercatiDAO.findById(new PkId((Integer) resultObj[1]));
	    MercatiD mercatiD = mercatiDDAO.findById(new PkId((Integer) resultObj[2]));
	    mercatiD.setMercati(mercati2);
	    registrazioni.setMercatiD(mercatiD);
	    MercatiUso uso = mercatiUsoDAO.findById(new PkId((Integer) resultObj[3]));
	    registrazioni.setMercatiUso(uso);
	    list.add(registrazioni);
	}
	return list;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Registrazioni> findAnagrafeByRegistrazioniAndMercati(Mercati mercati) {

	// §§§BEGIN§§§
	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.createAlias("mercatiD", "_mercatiD");
	det.createAlias("mercatiD.mercati", "_mercati");
	det.add(Restrictions.eq("_mercati.id.codice", mercati.getId().getCodice()));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.groupProperty("anagrafe.id.codice"));
	projectionList.add(Projections.groupProperty("_mercati.id.codice"));
	projectionList.add(Projections.groupProperty("_mercatiD.id.codice"));
	projectionList.add(Projections.groupProperty("mercatiUso.id.codice"));
	det.setProjection(projectionList);
	List<Object> listTemp = getHibernateTemplate().findByCriteria(det);
	List<Registrazioni> list = new ArrayList<Registrazioni>();
	for (Object object : listTemp) {
	    Registrazioni registrazioni = new Registrazioni();
	    Object[] resultObj = (Object[]) object;
	    Integer codiceAnagrafe = (Integer) resultObj[0];
	    Anagrafe anagrafe = anagrafeDAO.findById(new PkId(codiceAnagrafe));
	    registrazioni.setAnagrafe(anagrafe);
	    Mercati mercati2 = mercatiDAO.findById(new PkId((Integer) resultObj[1]));
	    MercatiD mercatiD = mercatiDDAO.findById(new PkId((Integer) resultObj[2]));
	    mercatiD.setMercati(mercati2);
	    registrazioni.setMercatiD(mercatiD);
	    MercatiUso uso = mercatiUsoDAO.findById(new PkId((Integer) resultObj[3]));
	    registrazioni.setMercatiUso(uso);
	    list.add(registrazioni);
	}
	return list;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Registrazioni> findRegistrazioniByAnno(short anno) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.eq("anno", anno));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findCodiciByRegistrazioniFilter(RegistrazioniFilter registrazioniFilter) {

	// §§§BEGIN§§§
	DetachedCriteria criteria = getFilterCriteria(registrazioniFilter, true);
	criteria.setResultTransformer(new ResultTransformer() {

	    @Override
	    public Object transformTuple(Object[] tuple, String[] aliases) {

		Object result = null;
		for (int i = 0; i < aliases.length; i++) {
		    String alias = aliases[i];
		    if (alias != null) {
			if (StringUtils.isNotBlank(alias)) {
			    if (alias.equalsIgnoreCase(ALIAS_CODICE_REGISTRAZIONE)) {
				result = tuple[i];
			    }
			}
		    }
		}
		return result;
	    }

	    @Override
	    public List transformList(List collection) {

		return collection;
	    }
	});
	return getHibernateTemplate().findByCriteria(criteria);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
