package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.hibernate.PreparedStatementWork;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("upgrInsertIstanzeCollegateTask")
public class InsertIstanzeCollegateTask extends IdGeneratorTask {

    //Logger log = LoggerFactory.getLogger(InsertIstanzeCollegateTask.class);

    @Override
    public int run(Session session) throws SetupRunException {

	int retVal = 0;
	StringBuilder sqlIstanzeCollOld = new StringBuilder("SELECT I.IDCOMUNE, I.CODICEISTANZA, I.CODISTANZACOLLEGATA FROM ISTANZE I ");
	sqlIstanzeCollOld.append("LEFT OUTER JOIN ISTANZECOLLEGATE IC ON I.IDCOMUNE = IC.IDCOMUNE AND I.CODICEISTANZA = IC.CODICEISTANZA");
	sqlIstanzeCollOld.append(" AND I.CODISTANZACOLLEGATA = IC.CODICEISTANZACOLLEGATA WHERE I.CODISTANZACOLLEGATA IS NOT NULL ");
	sqlIstanzeCollOld.append(" AND IC.PROGRESSIVO IS NULL ORDER BY I.IDCOMUNE, I.DATA, I.CODICEISTANZA DESC");
	StringBuilder sqlIstanzeLastLink = new StringBuilder("SELECT I.IDCOMUNE, I.CODICEISTANZA, I.CODISTANZACOLLEGATA FROM ISTANZE I ");
	sqlIstanzeLastLink.append("JOIN ISTANZE II ON I.IDCOMUNE = II.IDCOMUNE AND I.CODICEISTANZA = II.CODISTANZACOLLEGATA ");
	sqlIstanzeLastLink.append("LEFT OUTER JOIN ISTANZECOLLEGATE IC ON I.IDCOMUNE = IC.IDCOMUNE AND I.CODICEISTANZA = IC.CODICEISTANZA ");
	sqlIstanzeLastLink.append("AND IC.CODICEISTANZACOLLEGATA IS NULL WHERE I.CODISTANZACOLLEGATA IS NULL ");
	sqlIstanzeLastLink.append("AND IC.PROGRESSIVO IS NULL GROUP BY I.IDCOMUNE, I.CODICEISTANZA, I.CODISTANZACOLLEGATA ");
	sqlIstanzeLastLink.append("ORDER BY I.IDCOMUNE, I.CODICEISTANZA DESC");
	StringBuilder sqlLinkSearch = new StringBuilder(
		"SELECT I.IDCOMUNE, I.CODICEISTANZA, I.CODISTANZACOLLEGATA, IC.PROGRESSIVO, IC.ORDINE FROM ISTANZE I ");
	sqlLinkSearch.append("LEFT OUTER JOIN ISTANZECOLLEGATE IC ON I.IDCOMUNE = IC.IDCOMUNE AND I.CODICEISTANZA = IC.CODICEISTANZA");
	sqlLinkSearch.append(" AND I.CODISTANZACOLLEGATA = IC.CODICEISTANZACOLLEGATA WHERE I.IDCOMUNE = ? AND I.CODICEISTANZA = ? ");
	StringBuilder sqlLinkSearchBackWard = new StringBuilder(
		"SELECT I.IDCOMUNE, I.CODICEISTANZA, I.CODISTANZACOLLEGATA, IC.PROGRESSIVO, IC.ORDINE FROM ISTANZE I ");
	sqlLinkSearchBackWard.append("LEFT OUTER JOIN ISTANZECOLLEGATE IC ON I.IDCOMUNE = IC.IDCOMUNE AND I.CODICEISTANZA = IC.CODICEISTANZA");
	sqlLinkSearchBackWard.append(" AND I.CODISTANZACOLLEGATA = IC.CODICEISTANZACOLLEGATA WHERE I.IDCOMUNE = ? AND I.CODISTANZACOLLEGATA = ? ");
	String sqlMaxProgressivo = new String("SELECT MAX(PROGRESSIVO) VAL FROM ISTANZECOLLEGATE WHERE IDCOMUNE = ?");
	String sqlMaxId = new String("SELECT MAX(CURRVAL) VAL FROM SEQUENCETABLE WHERE SEQUENCENAME = 'ISTANZECOLLEGATE.ID' AND IDCOMUNE = ?");
	String sqlInsert = "INSERT INTO ISTANZECOLLEGATE (IDCOMUNE,ID,PROGRESSIVO,CODICEISTANZA,ORDINE,CODICEISTANZACOLLEGATA) VALUES (?,?,?,?,?,?)";
	int startFrom = 0;
	int maxRows = 1;
	String currentIdComune = null;
	int maxIdPerComune = -1;
	int maxProgressivoPerComune = -1;
	boolean readon = true;
	boolean queryLastLink = true;
	SQLQuery istanzeQ = null;
	List<Object[]> istanzeList = null;
	//Ciclo i record di ISTANZE che hanno una qualche istanza collegata ma non sono ancora inseriti in ISTANZECOLLEGATE
	//esauriti i record del primo ciclo, il ciclo continua interrogando anche i record di ISTANZE che non hanno istanza collegata 
	//ma che sono essi stessi l'istanza collegata di una altra istanza per lo stesso comune e che non sono presenti in ISTANZECOLLEGATE
	//Questi ultimi record rappresentano gli ultimi anelli di catene di collegamenti che non sarebbero restituiti dalla query del primo ciclo
	//ma anch'essi devono essere inseriti in ISTANZECOLLEGATE come previsto dalla logica applicativa di VBG
	while (readon) {
	    String query = queryLastLink ? sqlIstanzeLastLink.toString() : sqlIstanzeCollOld.toString();
	    maxRows = queryLastLink ? 100 : 1;
	    istanzeQ = session.createSQLQuery(query);
	    istanzeQ.setFirstResult(startFrom);
	    istanzeQ.setMaxResults(maxRows);
	    istanzeQ.setCacheable(false);
	    istanzeQ.setCacheMode(CacheMode.IGNORE);
	    istanzeList = istanzeQ.list();
	    if (istanzeList.size() < maxRows) {
		if (queryLastLink) {
		    queryLastLink = false;
		} else {
		    readon = false;
		}
	    }
	    for (Object[] datiIstanza : istanzeList) {
		String idComune = (String) datiIstanza[0];
		if (!idComune.equals(currentIdComune)) {
		    //aggiorno la sequence per il comune precedente prima di passare al prossimo comune.
		    if (StringUtils.isNotEmpty(currentIdComune)) {
			updateSequence(session, "ISTANZECOLLEGATE.ID", currentIdComune, maxIdPerComune);
		    }
		    maxIdPerComune = -1;
		    maxProgressivoPerComune = -1;
		    currentIdComune = idComune;
		}
		int progressivo = -1;
		//per ciasuna istanza che ha un collegamento ricostruisco l'intera catena dei collegamenti (in entrambe le direzioni)
		List<IstanzaDaCollegare> linkChain = new ArrayList<IstanzaDaCollegare>();
		IstanzaDaCollegare firstLink = new IstanzaDaCollegare();
		firstLink.setCodiceIstanza(((BigDecimal) datiIstanza[1]).intValue());
		if (datiIstanza[2] != null) {
		    firstLink.setCodiceIstanzaDaCollegare(((BigDecimal) datiIstanza[2]).intValue());
		}
		//e la memorizzo in un Set di collegamenti (identificando le catene circolari)
		linkChain.add(firstLink);
		Integer linked = firstLink.getCodiceIstanzaDaCollegare();
		IstanzaDaCollegare link = null;
		//ricostruisco la catena scorrendola tutta fino alla fine
		SQLQuery linkedQ = null;
		int ordine = 0;
		while (linked != null && !queryLastLink) {
		    linkedQ = session.createSQLQuery(sqlLinkSearch.toString());
		    linkedQ.setString(0, idComune);
		    linkedQ.setInteger(1, linked);
		    List<Object[]> childrenList = linkedQ.list();
		    //un solo record nella lista
		    if (childrenList.size() == 0) {
			linked = null;
		    } else {
			Object[] linkData = childrenList.get(0);
			//if (linkData[2] != null) {
			ordine--;
			link = new IstanzaDaCollegare();
			link.setCodiceIstanza(((BigDecimal) linkData[1]).intValue());
			if (linkData[2] != null) {
			    link.setCodiceIstanzaDaCollegare(((BigDecimal) linkData[2]).intValue());
			}
			link.setOrdinale(ordine);
			linked = link.getCodiceIstanzaDaCollegare();
			if (linkData[3] != null) {
			    Integer tempProgressivo = ((BigDecimal) linkData[3]).intValue();
			    if (progressivo != -1 && tempProgressivo != progressivo) {
				//TODO il progressivo letto dal DB è diverso dal progressivo letto in anelli precedenti della catena. GENERARE ERRORE?
			    }
			    if (tempProgressivo != null) {
				progressivo = tempProgressivo;
				//se il record esiste già su ISTANZECOLLEGATE lo memorizzo nel Set impostando il progressivo per riconoscerlo in seguito ed evitare di reinserirlo
				link.setProgressivo(progressivo);
			    }
			}
			if (!linkChain.contains(link)) {
			    linkChain.add(link);
			} else {
			    //il collegamento esiste già nella catena ==> è una catena chiusa ==> esco dal ciclo while per evitare il loop
			    errorLogWarn("E' stata identificata una catena di collegamenti circolare: {}", new Object[]{linkChain});
			    linked = null;
			}
			/*
			} else {
			    //se l'istanza collegata non ha a sua volta un'altra istanza collegata vuol dire che siamo alla fine della catena
			    //l'ultima istanza della catena deve essere inserita in ISTANZECOLLEGATE e collegata a NULL
			    linked = null;
			}
			*/
		    }
		}
		//ricostruisco i pezzi della catena che si trovano prima del punto da cui ho iniziato
		linked = firstLink.getCodiceIstanza();
		ordine = 0;
		while (linked != null) {
		    linkedQ = session.createSQLQuery(sqlLinkSearchBackWard.toString());
		    linkedQ.setString(0, idComune);
		    linkedQ.setInteger(1, linked);
		    List<Object[]> childrenList = linkedQ.list();
		    if (childrenList.size() > 0) {
			//in teoria possono esserci più di una istanza collegate alla stessa, in questi casi tutte vengono memorizzate nella stessa catena con lo stesso progressivo
			for (int i = 0; i < childrenList.size(); i++) {
			    ordine++;
			    Object[] linkData = childrenList.get(i);
			    link = new IstanzaDaCollegare();
			    link.setCodiceIstanza(((BigDecimal) linkData[1]).intValue());
			    if (linkData[2] != null) {
				link.setCodiceIstanzaDaCollegare(((BigDecimal) linkData[2]).intValue());
			    }
			    link.setOrdinale(ordine);
			    linked = link.getCodiceIstanza();
			    if (linkData[3] != null) {
				Integer tempProgressivo = ((BigDecimal) linkData[3]).intValue();
				if (progressivo != -1 && tempProgressivo != progressivo) {
				    //TODO il progressivo letto dal DB è diverso dal progressivo letto in anelli precedenti della stessa catena. GENERARE ERRORE?
				}
				if (tempProgressivo != null) {
				    progressivo = tempProgressivo;
				    //se il record esiste già su ISTANZECOLLEGATE lo memorizzo nel Set impostando il progressivo per riconoscerlo in seguito ed evitare di reinserirlo
				    link.setProgressivo(progressivo);
				}
			    }
			    if (!linkChain.contains(link)) {
				linkChain.add(link);
			    } else {
				//il collegamento esiste già nella catena ==> è una catena chiusa ==> esco dal ciclo while per evitare il loop
				errorLogWarn("E' stata identificata una catena di collegamenti circolare: {}", new Object[]{linkChain});
				linked = null;
			    }
			}
		    } else {
			linked = null;
		    }
		}
		//In istanzecollegate devo inserire il progressivo che deve essere uguale per tutti i nodi di una stessa catena per uno stesso comune
		//quindi se uno qualunque dei collegamenti memorizzati esiste in istanze collegate allora uso lo stesso progressivo per tutti i nodi della stessa catena
		//se no ne genero uno nuovo con la max per comune +1
		if (progressivo == -1) {
		    if (maxProgressivoPerComune == -1) {
			SQLQuery maxProgressivoQ = session.createSQLQuery(sqlMaxProgressivo);
			maxProgressivoQ.setString(0, idComune);
			maxProgressivoQ.addScalar("VAL", Hibernate.INTEGER);
			Integer result = (Integer) maxProgressivoQ.uniqueResult();
			if (result == null) {
			    result = new Integer(0);
			}
			maxProgressivoPerComune = result + 1;
		    } else {
			//evitiamo di interrogare nuovamente il DB se già si conosce il MAX progressivo per comune.
			maxProgressivoPerComune++;
		    }
		    progressivo = maxProgressivoPerComune;
		}
		//Riordino tutta la catena di istanze collegate in base al campo Ordine
		Collections.sort(linkChain);
		//Individuata tutta la singola catena e determinato il progressivo inserisco in ISTANZECOLLEGATE tutti i nodi della catena che mancano
		Iterator<IstanzaDaCollegare> chainIterator = linkChain.iterator();
		activityLogDebug("CATENA DI COLLEGAMENTI INDIVIDUATA. N°{} ELEMENTI: {}, INIZIO INSERIMENTI IN ISTANZECOLLEGATE.", new Object[] {
			    linkChain.size(), linkChain.toString() });
		int counter = 0;
		while (chainIterator.hasNext()) {
		    IstanzaDaCollegare istanzaDaCollegare = (IstanzaDaCollegare) chainIterator.next();
		    counter++;
		    if (istanzaDaCollegare.getProgressivo() != null) {
			//se il progressivo è impostato significa che il collegamento è già presente su istanzecollegate
			//TODO si potrebbe effettuare una verifica sul valore del campo ORDINE ed eventualmente aggiornarlo nel DB se il suo valore non risulta corretto
			continue;
		    } else {
			//il collegamento deve essere inserito in ISTANZECOLLEGATE
			//se ho cambiato comune stacco un nuovo ID per il record che sto per inserire
			if (maxIdPerComune == -1) {
			    /*super.
			    SQLQuery maxIdQ = session.createSQLQuery(sqlMaxId);
			    maxIdQ.setString(0, currentIdComune);
			    maxIdQ.addScalar("VAL", Hibernate.INTEGER);
			    Integer maxId = (Integer) maxIdQ.uniqueResult();
			    */
			    maxIdPerComune = getOrCreateSequenceCurrval(session, "ISTANZECOLLEGATE.ID", currentIdComune);
			}
			//inserisco il record
			PreparedStatementWork insertPS = new PreparedStatementWork();
			insertPS.setSqlStatement(sqlInsert);
			Object[] parameters = new Object[6];
			parameters[0] = currentIdComune;
			parameters[1] = ++maxIdPerComune;
			parameters[2] = progressivo;
			parameters[3] = istanzaDaCollegare.getCodiceIstanza();
			parameters[4] = counter;
			parameters[5] = istanzaDaCollegare.getCodiceIstanzaDaCollegare();
			insertPS.setParameters(parameters);
			session.doWork(insertPS);
			retVal += insertPS.getLastResult();
			if (activityLog.isInfoEnabled()) {
			    Object[] logData = new Object[] { currentIdComune, istanzaDaCollegare.getCodiceIstanza(),
				    istanzaDaCollegare.getCodiceIstanzaDaCollegare(), progressivo, counter };
			    activityLogInfo("COMUNE: {}. INSERITA ISTANZA COLLEGATA. CODICEISTANZA: {}, CODICEISTANZACOLLEGATA: {}, PROGRESSIVO: {}, ORDINE: {}", logData);
			}
		    }
		}
		//committo al termine dell'inserimento di tutti i collegamenti di una stessa catena
		this.commitTransaction();
	    }
	}
	//aggiorno l'ultima sequence utilizzata
	if (StringUtils.isNotEmpty(currentIdComune)) {
	    updateSequence(session, "ISTANZECOLLEGATE.ID", currentIdComune, maxIdPerComune);
	}
	this.commitTransaction();
	return retVal;
    }
}

class IstanzaDaCollegare implements Comparable<IstanzaDaCollegare> {

    private Integer codiceIstanza;
    private Integer codiceIstanzaDaCollegare;
    private Integer progressivo;
    private Integer ordinale = new Integer(0);

    public IstanzaDaCollegare() {

    }

    public IstanzaDaCollegare(Integer codiceIstanza, Integer codiceIstanzaDaCollegare) {

	this.codiceIstanza = codiceIstanza;
	this.codiceIstanzaDaCollegare = codiceIstanzaDaCollegare;
    }

    /**
     * @return the codiceIstanza
     */
    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    /**
     * @param codiceIstanza
     *            the codiceIstanza to set
     */
    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    /**
     * @return the codiceIstanzaDaCollegare
     */
    public Integer getCodiceIstanzaDaCollegare() {

	return codiceIstanzaDaCollegare;
    }

    /**
     * @param codiceIstanzaDaCollegare
     *            the codiceIstanzaDaCollegare to set
     */
    public void setCodiceIstanzaDaCollegare(Integer codiceIstanzaDaCollegare) {

	this.codiceIstanzaDaCollegare = codiceIstanzaDaCollegare;
    }

    /**
     * @return the ordinale
     */
    public Integer getOrdinale() {

	return ordinale;
    }

    /**
     * @param ordinale
     *            the ordinale to set
     */
    public void setOrdinale(Integer ordinale) {

	this.ordinale = ordinale;
    }

    /**
     * @return the progressivo
     */
    public Integer getProgressivo() {

	return progressivo;
    }

    /**
     * @param progressivo
     *            the progressivo to set
     */
    public void setProgressivo(Integer progressivo) {

	this.progressivo = progressivo;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	int codIst = codiceIstanza == null ? 0 : codiceIstanza.intValue();
	int codIstColl = codiceIstanzaDaCollegare == null ? 0 : codiceIstanzaDaCollegare.intValue();
	result = prime * result + codIst;
	result = prime * result + codIstColl;
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	IstanzaDaCollegare other = (IstanzaDaCollegare) obj;
	/*
	if (!getOuterType().equals(other.getOuterType()))
	return false;
	*/
	if (codiceIstanza != null) {
	    if (!codiceIstanza.equals(other.getCodiceIstanza())) {
		return false;
	    }
	} else if (other.getCodiceIstanza() != null) {
	    return false;
	}
	if (codiceIstanzaDaCollegare != null) {
	    if (!codiceIstanzaDaCollegare.equals(other.getCodiceIstanzaDaCollegare())) {
		return false;
	    }
	} else if (other.getCodiceIstanzaDaCollegare() != null) {
	    return false;
	}
	return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("CodiceIstanza: ").append(codiceIstanza);
	sb.append(", CodiceIstanza Da Collegare: ").append(codiceIstanzaDaCollegare);
	sb.append(", Ordine: ").append(ordinale);
	sb.append(", Progressivo: ").append(progressivo);
	return sb.toString();
    }

    @Override
    public int compareTo(IstanzaDaCollegare o) {

	if (this.equals(o)) {
	    return 0;
	}
	int compVal = o == null ? 0 : o.getOrdinale();
	return new Integer(this.getOrdinale()).compareTo(new Integer(compVal));
    }
}
