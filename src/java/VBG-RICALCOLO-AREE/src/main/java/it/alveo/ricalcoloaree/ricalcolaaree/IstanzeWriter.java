package it.alveo.ricalcoloaree.ricalcolaaree;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.alveo.ricalcoloaree.bean.IstanzeCountersBean;
import it.alveo.ricalcoloaree.bean.RicalcoloAreeTriple;
import it.alveo.ricalcoloaree.constants.WebConstants;
import it.alveo.ricalcoloaree.entities.IstanzeEventi;
import it.alveo.ricalcoloaree.entities.SequenceTable;
import it.alveo.ricalcoloaree.entities.composefields.IstanzeEventiPK;
import it.alveo.ricalcoloaree.entities.composefields.RicalcoloAreePK;
import it.alveo.ricalcoloaree.entities.composefields.SequenceTabPK;
import it.alveo.ricalcoloaree.utils.LogUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Query;

public class IstanzeWriter {

    public static final int MAX_HI_VALUE = 90000000;

    public void gestisciIstanzaSingola(EntityManagerFactory entityManagerFactory, EntityManager entityManager,
            String idComune, Integer codiceIstanza, String methodName, RicalcoloAreePK pk,
            RicalcoloAreeTriple triplebean, IstanzeCountersBean istanzeCountersBean) throws Exception {

        istanzeCountersBean.setCodiceIstanzaExc(codiceIstanza);

        List<Integer> singoloCodiceIstanzaL = Arrays.asList(new Integer[] { codiceIstanza });
        new IstanzeWriter().deleteOnIstanzeAree(singoloCodiceIstanzaL, entityManager, idComune);
        Map<Integer, Set<Integer>> codiciAreaByIstanzaMap = new IstanzeReader()
                .findCodiciAreaOnIstanzeAreeMap(singoloCodiceIstanzaL, idComune, entityManager);
        Set<Integer> codiciIstanzePrimarieSet = new IstanzeReader().findCodiciIstanzePrimarieSet(singoloCodiceIstanzaL,
                idComune, entityManager);

        new IstanzeWriterHelper().ricalcoloPerIstanza(codiceIstanza, idComune, entityManager, entityManagerFactory,
                triplebean, pk, new HashSet<String>(), codiciAreaByIstanzaMap, codiciIstanzePrimarieSet); // metto null
                                                                                                          // per ora
    }

    public void insertInIstanzeEventi(String idcomune, Integer codiceIstanza, String descrizione,
            EntityManagerFactory entityManagerFactory) {

        String methodName = "insertInIstanzeEventi(...)";
        LogUtil.info(this, methodName, "inserting event istance");

        long seq;
        try {
            seq = getNextSeqForIstanzeEventi(idcomune, entityManagerFactory);
        } catch (Exception e) {
            LogUtil.error(this, methodName, "error getting sequence ", e);
            return;
        }

        EntityManager em = null;
        try {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            IstanzeEventi evento = new IstanzeEventi();
            IstanzeEventiPK pk = new IstanzeEventiPK();
            pk.setIdcomune(idcomune);
            pk.setIdevento(seq);
            evento.setPk(pk);
            evento.setCodiceistanza(codiceIstanza);
            evento.setFkidcategoriaevento(WebConstants.CATEGORIA_AVVERTIMENTI);
            evento.setDescrizione(descrizione);
            evento.setData(new Timestamp(new java.util.Date().getTime()));
            evento.setFlagLetto(false);
            em.persist(evento);
            em.getTransaction().commit();
            LogUtil.info(this, methodName, "event istance inserted");
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                System.out.println("Rolling back transaction");
                em.getTransaction().rollback();
                System.out.println("Transaction rolled back");
            }
            LogUtil.error(this, methodName, "error during inserting event istance", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @SuppressWarnings({ "unchecked" })
    private long getNextSeqForIstanzeEventi(String idcomune, EntityManagerFactory entityManagerFactory) {

        EntityManager em = null;
        long res;
        try {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            List<SequenceTable> eL = em.createQuery(
                    "SELECT e FROM SequenceTable e WHERE e.id.idcomune = :idcomune AND e.id.sequencename = :sequencename",
                    SequenceTable.class).setParameter("idcomune", idcomune)
                    .setParameter("sequencename", "ISTANZEEVENTI.IDEVENTO").setLockMode(LockModeType.PESSIMISTIC_WRITE) // o
                    // UPGRADE
                    .getResultList();
            if (!eL.isEmpty()) {
                res = getSequenceElNotEmpty(eL, idcomune);
            } else {
                List<Long> mL = em
                        .createNativeQuery("SELECT MAX(IDEVENTO) FROM ISTANZEEVENTI WHERE IDCOMUNE = :idcomune",
                                Long.class)
                        .setParameter("idcomune", idcomune).getResultList();
                res = getResByList(mL);

                if ((res + 1) > MAX_HI_VALUE) {
                    throw new RuntimeException(
                            "Superato il limite massimo [" + MAX_HI_VALUE + "] per ottenere una sequenza per il campo ["
                                    + "IDEVENTO" + "] e idcomune [" + idcomune + "]. Contattare l'assistenza.");
                }

                SequenceTable sequence = new SequenceTable();
                SequenceTabPK pk = new SequenceTabPK();
                pk.setIdcomune(idcomune);
                pk.setSequencename("ISTANZEEVENTI.IDEVENTO");
                sequence.setPk(pk);
                sequence.setCurrval(res + 1);
                em.persist(sequence);
            }
            em.getTransaction().commit();
            return res + 1;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                System.out.println("Rolling back transaction");
                em.getTransaction().rollback();
                System.out.println("Transaction rolled back");
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private long getSequenceElNotEmpty(List<SequenceTable> eL, String idcomune) {
        SequenceTable seqence = eL.get(0);
        long res = seqence.getCurrval();

        if ((res + 1) > MAX_HI_VALUE) {
            throw new RuntimeException(
                    "Superato il limite massimo [" + MAX_HI_VALUE + "] per ottenere una sequenza per il campo ["
                            + "IDEVENTO" + "] e idcomune [" + idcomune + "]. Contattare l'assistenza.");
        }

        seqence.setCurrval(res + 1);
        return res;
    }

    private long getResByList(List<Long> mL) {

        long res;
        if (mL.isEmpty() || mL.get(0) == null || mL.get(0) <= 0) {
            res = 0;
        } else {
            res = mL.get(0);
        }
        return res;
    }

    public void deleteOnIstanzeAree(List<Integer> codiciIstanze, EntityManager entityManager, String idComune) {
        String methodName = "deleteOnIstanzeAree(...)";
        LogUtil.info(this, methodName, "sto cancellando righe istanzearee con autoins = 1");
        String qry = "DELETE FROM IstanzeAree a WHERE a.id.idcomune = :idcomune AND a.id.codiceistanza IN :codiciistanza AND a.autoins = :autoins";
        Query query = entityManager.createQuery(qry);
        query.setParameter("idcomune", idComune);
        query.setParameter("codiciistanza", codiciIstanze);
        query.setParameter("autoins", 1);
        query.executeUpdate();
        entityManager.flush();
        LogUtil.info(this, methodName,
                "ho cancellato righe istanzearee con autoins = 1, numero istanze: " + codiciIstanze.size());
    }
}
