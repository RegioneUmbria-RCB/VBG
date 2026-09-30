package it.alveo.ricalcoloaree.ricalcolaaree;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.alveo.ricalcoloaree.bean.AreeDettagliBean;
import it.alveo.ricalcoloaree.bean.RicalcoloAreeTriple;
import it.alveo.ricalcoloaree.constants.WebConstants;
import it.alveo.ricalcoloaree.entities.RicalcoloAree;
import it.alveo.ricalcoloaree.entities.composefields.RicalcoloAreePK;
import it.alveo.ricalcoloaree.utils.LogUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class RicalcoloAreeWriter {

    public String insertInRicalcoloAree(String idcomune, String id, EntityManager entitymanager) {

	String methodName = "insertInRicalcoloAree(...)";
	LogUtil.info(this, methodName, "inserting in ricalcoloAree for the first time");
	RicalcoloAree ricalcoloaree = entitymanager.find(RicalcoloAree.class, RicalcoloAreePK.getIsttance(idcomune, id));
	if (ricalcoloaree != null) {
	    throw new RuntimeException("record already exists for id " + id);
	}
	RicalcoloAree ricalcoloAree = new RicalcoloAree();
	ricalcoloAree.setPk1(RicalcoloAreePK.getIsttance(idcomune, id));
	ricalcoloAree.setStato(WebConstants.STATO_IN_CORSO);
	ricalcoloAree.setDatafine(new java.util.Date());
	ricalcoloAree.setDafare(0);
	ricalcoloAree.setFatti(0);
	ricalcoloAree.setTotali(0);
	try {
	    entitymanager.getTransaction().begin();
	    entitymanager.persist(ricalcoloAree);
	    entitymanager.flush();
	    entitymanager.getTransaction().commit();
	} catch (Exception e) {
	    if (entitymanager != null && entitymanager.getTransaction().isActive()) {
		entitymanager.getTransaction().rollback();
	    }
	    throw e;
	}
	LogUtil.info(this, methodName, "record inserted");
	return id;
    }

    public void updateRicalcoloAreeFirstTime(Map<String, List<AreeDettagliBean>> areeDettagliBeanMapp, RicalcoloAreePK pk, String idcomune,
	    EntityManagerFactory entityManagerFactory) {

	String methodName = "updateRicalcoloAreeFirstTime(...)";
	LogUtil.info(this, methodName, "updating description field with ids of aree");
	Map<String, List<AreeDettagliBean>> areeDettagliBeanMap = areeDettagliBeanMapp;
	if (areeDettagliBeanMap == null) {// Giusto per sicurezza
	    areeDettagliBeanMap = new HashMap<String, List<AreeDettagliBean>>();
	}
	List<String> areeDescriptions = new AreeReader().estraiDescrizioniAree(areeDettagliBeanMap, idcomune, methodName);
	StringBuilder sb = new StringBuilder();
	sb.append("Aree:  ");
	sb.append(areeDescriptions.toString());
	String descrizione;
	LogUtil.info(this, methodName, sb.toString());
	try {
	    if (sb.toString().getBytes(WebConstants.UFF8CHARSET).length > 1000) {
		descrizione = "Too long for set ids of Aree";
	    } else {
		descrizione = sb.toString();
	    }
	} catch (UnsupportedEncodingException e) {
	    throw new RuntimeException(e);
	}
	updateRicalcoloAreeComm(pk, areeDettagliBeanMap.size(), 0, areeDettagliBeanMap.size(), descrizione, WebConstants.STATO_IN_CORSO,
		entityManagerFactory);
	LogUtil.info(this, methodName, "update executed");
    }

    public void updateRicalcoloAreeComm(RicalcoloAreePK pk, int dafare, int fatti, int totali, String descrizione, String status,
	    EntityManagerFactory entityManagerFactory) {

	EntityManager em = null;
	try {
	    em = entityManagerFactory.createEntityManager();
	    em.getTransaction().begin();
	    updateRicalcoloAree(pk, dafare, fatti, totali, descrizione, status, em);
	    em.getTransaction().commit();
	} catch (Exception e) {
	    if (em != null && em.getTransaction().isActive()) {
		LogUtil.error(this, "updateRicalcoloAreeComm", "Rolling back transaction", e);
		em.getTransaction().rollback();
	    }
	    throw e;
	} finally {
	    if (em != null) {
		em.close();
	    }
	}
    }

    public void updateRicalcoloAree(RicalcoloAreePK pk, int dafare, int fatti, int totali, String descrizione, String status, EntityManager em) {

	RicalcoloAree ricalcoloAree = em.find(RicalcoloAree.class, pk);
	ricalcoloAree.setDafare(dafare);
	ricalcoloAree.setFatti(fatti);
	ricalcoloAree.setTotali(totali);
	ricalcoloAree.setDatafine(new Date());
	if (descrizione != null) {
	    ricalcoloAree.setDescrizione(descrizione);
	}
	if (status != null) {
	    ricalcoloAree.setStato(status);
	}
	em.merge(ricalcoloAree);
	em.flush();
    }

    public void updateRicalcoloAreeComplete(RicalcoloAreePK pk, RicalcoloAreeTriple bean, EntityManagerFactory entityManagerFactory) {

	String stato = WebConstants.COMPLETATA;
	if (bean.getFatti() != bean.getTotali()) {
	    stato = WebConstants.CON_SCARTI;
	}
	updateRicalcoloAreeComm(pk, bean.getDafare(), bean.getFatti(), bean.getTotali(), null, stato, entityManagerFactory);
    }

    public void updateRicalcoloAreeForCheckpoint(RicalcoloAreePK pk, int dafare, int fatti, int totali, String descrizione,
	    EntityManagerFactory entityManagerFactory) {

	updateRicalcoloAreeComm(pk, dafare, fatti, totali, descrizione, null, entityManagerFactory);
    }

    public void updateRicalcoloAreeForCheckpoint(RicalcoloAreePK pk, int dafare, int fatti, int totali, String descrizione, String status,
	    EntityManagerFactory entityManagerFactory) {

	updateRicalcoloAreeComm(pk, dafare, fatti, totali, descrizione, status, entityManagerFactory);
    }

    public void updateRicalcoloAreeForError(RicalcoloAreePK pk, int dafare, int fatti, int totali, String descrizione, String status,
	    EntityManagerFactory entityManagerFactory) {

	String methodName = "updateRicalcoloAreeForError";
	try {
	    LogUtil.info(this, methodName, "updating error");
	    updateRicalcoloAreeComm(pk, dafare, fatti, totali, descrizione, status, entityManagerFactory);
	    LogUtil.info(this, methodName, "error updated");
	} catch (Exception e1) {
	    LogUtil.error(this, methodName, "error during updating error", e1);
	}
    }
}
