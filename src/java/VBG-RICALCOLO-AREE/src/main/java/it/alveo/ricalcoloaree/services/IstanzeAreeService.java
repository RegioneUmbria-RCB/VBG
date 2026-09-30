package it.alveo.ricalcoloaree.services;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import it.alveo.ricalcoloaree.bean.IstanzeCountersBean;
import it.alveo.ricalcoloaree.bean.RicalcoloAreeInBean;
import it.alveo.ricalcoloaree.bean.RicalcoloAreeTriple;
import it.alveo.ricalcoloaree.constants.WebConstants;
import it.alveo.ricalcoloaree.dao.custom.IstanzeDAOCustom;
import it.alveo.ricalcoloaree.datilocalizzativi.RicalcoloFilter;
import it.alveo.ricalcoloaree.entities.Istanze;
import it.alveo.ricalcoloaree.entities.composefields.RicalcoloAreePK;
import it.alveo.ricalcoloaree.getaree.GetAree;
import it.alveo.ricalcoloaree.respdata.RespSessionData;
import it.alveo.ricalcoloaree.ricalcolaaree.IstanzeReader;
import it.alveo.ricalcoloaree.ricalcolaaree.IstanzeWriter;
import it.alveo.ricalcoloaree.ricalcolaaree.IstanzeWriterHelper;
import it.alveo.ricalcoloaree.ricalcolaaree.RicalcoloAreeWriter;
import it.alveo.ricalcoloaree.utils.LogUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@Service
public class IstanzeAreeService {

    TransactionTemplate transactionTemplate;

    public RespSessionData getProcessByRicacloloAreeId(String idcomune, String ricacloloAreeId,
            EntityManager entitymanager) {

        return new GetAree().getProcessByRicacloloAreeId(idcomune, ricacloloAreeId, entitymanager);
    }

    public String insertInRicalcoloAree(String idcomune, String id, EntityManager entitymanager) {

        return new RicalcoloAreeWriter().insertInRicalcoloAree(idcomune, id, entitymanager);
    }

    @Async
    public void ricalcolaAree(RicalcoloAreeInBean ricalcolaAreeInBean, EntityManagerFactory entityManagerFactory,
            List<String> ricalcoloAreeIdL) {

        String methodName = "ricalcolaAree(...)";
        LogUtil.debug(this, methodName, "method async launched");
        if (ricalcoloAreeIdL != null) {
            if (ricalcoloAreeIdL.isEmpty()) {
                LogUtil.debug(this, methodName, "empty uuids for istanze");
                return;
            }
            if ("singleistanza".equals(ricalcolaAreeInBean.getType()) && ricalcoloAreeIdL.size() != 1) {
                LogUtil.debug(this, methodName,
                        "poiché stiamo agendo su singola istanza, deve esserci una sola testata");
                return;
            }
            Map<String, List<Integer>> idsRicalcoloAreeMap = popolamappa(ricalcolaAreeInBean, entityManagerFactory,
                    ricalcoloAreeIdL);

            if ("singleistanza".equals(ricalcolaAreeInBean.getType())) {
                for (Map.Entry<String, List<Integer>> entry : idsRicalcoloAreeMap.entrySet()) {
                    if (entry.getValue().size() != 1) {
                        LogUtil.debug(this, methodName,
                                "poiché stiamo agendo su singola istanza, deve esserci una sola istanza per la testata "
                                        + entry.getKey());
                        return;
                    }
                }
            }

            LogUtil.debug(this, methodName, "idsRicalcoloAree size : " + idsRicalcoloAreeMap.size());

            for (Map.Entry<String, List<Integer>> entry : idsRicalcoloAreeMap.entrySet()) {

                RicalcoloAreeTriple triplebean = new RicalcoloAreeTriple();
                LogUtil.debug(this, methodName, "codice testata: " + entry.getKey());
                LogUtil.debug(this, methodName, "numero istanze da calcolare : " + entry.getValue().size());
                ricalcolaAreeInBean.setPk(entry.getKey());
                ricalcolaAreeInBean.setCodiciIstanze(entry.getValue());

                ricalcolaAreeP(ricalcolaAreeInBean, triplebean, entityManagerFactory);
            }
        } else {
            ricalcolaAreeInBean.setCodiciIstanze(null);
            RicalcoloAreeTriple triplebean = new RicalcoloAreeTriple();
            ricalcolaAreeP(ricalcolaAreeInBean, triplebean, entityManagerFactory);
        }
        LogUtil.debug(this, methodName, "method async ended");
    }

    private Map<String, List<Integer>> popolamappa(RicalcoloAreeInBean ricalcolaAreeInBean,
            EntityManagerFactory entityManagerFactory, List<String> ricalcoloAreeIdL) {
        List<List<String>> sottoLists = ListUtils.partition(ricalcoloAreeIdL, 100);
        Map<String, List<Integer>> idsRicalcoloAreeMap = new HashMap<String, List<Integer>>();

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            for (List<String> lista : sottoLists) {

                List<Object[]> idsRicalcoloAree = new IstanzeReader().findIdRicalcoloAreeByUuids(lista,
                        ricalcolaAreeInBean.getIdcomune(), entityManager);
                for (Object[] oL : idsRicalcoloAree) {
                    String idRicalcoloAree = (String) oL[0];
                    Integer codiceIstanza = oL[2] != null ? ((Number) oL[2]).intValue() : null;
                    if (!idsRicalcoloAreeMap.containsKey(idRicalcoloAree)) {
                        idsRicalcoloAreeMap.put(idRicalcoloAree, new ArrayList<Integer>());
                    }
                    idsRicalcoloAreeMap.get(idRicalcoloAree).add(codiceIstanza);
                }

            }
        }

        return idsRicalcoloAreeMap;
    }

    public List<Istanze> findCodiciIstanzaPerRicalcolo(RicalcoloFilter filter, String idComune, EntityManager em) {

        return IstanzeDAOCustom.findIstanzeByFilters(idComune, filter.getSoftware(), filter.getDallaData(),
                filter.getAllaData(), em);
    }

    @SuppressWarnings("unchecked")
    private void gestisciAree(EntityManagerFactory entityManagerFactory, EntityManager entityManager,
            RicalcoloAreeInBean ricalcoloAreeInBean, IstanzeCountersBean istanzeCountersBean,
            RicalcoloAreeTriple triplebean) {

        String methodName = "gestisciAree(...)";
        Date dallaData = ricalcoloAreeInBean.getDateDA();
        Date allaData = ricalcoloAreeInBean.getDateA();
        String software = ricalcoloAreeInBean.getSoftware();
        String idComune = ricalcoloAreeInBean.getIdcomune();
        RicalcoloAreePK pk = RicalcoloAreePK.getIsttance(idComune, ricalcoloAreeInBean.getPk());
        List<Integer> idareelist = ricalcoloAreeInBean.getIdAree();
        RicalcoloFilter filter = costruisciFiltroRicalcolo(software, dallaData, allaData);
        List<Integer> codiciIstanze;

        if (ricalcoloAreeInBean.getCodiciIstanze() != null) {
            LogUtil.info(this, methodName,
                    "stiamo calcolando per istanze ben definite dalla testata in input " + pk.getId());
            codiciIstanze = ricalcoloAreeInBean.getCodiciIstanze();
        } else {
            List<Istanze> istanze = this.findCodiciIstanzaPerRicalcolo(filter, idComune, entityManager);
            if (idareelist != null && !idareelist.isEmpty()) {
                codiciIstanze = intersezioneWithAree(istanze, idComune, entityManager, idareelist);
            } else {
                codiciIstanze = istanze.stream().map(elemento -> elemento.getPkId().getCodice())
                        .collect(Collectors.toList());
            }
        }

        LogUtil.info(this, methodName,
                "codiciIstanze found: " + (codiciIstanze != null ? codiciIstanze.size() : "null"));
        triplebean.setDafare(codiciIstanze.size());
        triplebean.setFatti(0);
        triplebean.setTotali(codiciIstanze.size());
        LogUtil.info(this, methodName, "updating for first time");
        new RicalcoloAreeWriter().updateRicalcoloAreeComm(pk, triplebean.getDafare(), 0, triplebean.getTotali(),
                "Aree:  " + new ArrayList<>() + "", WebConstants.STATO_IN_CORSO, entityManagerFactory);
        LogUtil.info(this, methodName, "update executed");
        Set<String> areeDescriptionSet = new HashSet<String>();

        List<List<Integer>> sottoLists = ListUtils.partition(codiciIstanze, 1000);

        for (List<Integer> li : sottoLists) {
            LogUtil.info(this, methodName, "numero istanze: " + li.size());

            new IstanzeWriter().deleteOnIstanzeAree(li, entityManager, idComune);
            Map<Integer, Set<Integer>> codiciAreaByIstanzaMap = new IstanzeReader().findCodiciAreaOnIstanzeAreeMap(li,
                    idComune, entityManager);
            Set<Integer> codiciIstanzePrimarieSet = new IstanzeReader().findCodiciIstanzePrimarieSet(li, idComune,
                    entityManager);

            for (Integer codiceIstanza : li) {
                istanzeCountersBean.setCodiceIstanzaCorrente(codiceIstanza);
                try {
                    new IstanzeWriterHelper().ricalcoloPerIstanza(codiceIstanza, idComune, entityManager,
                            entityManagerFactory, triplebean, pk, areeDescriptionSet, codiciAreaByIstanzaMap,
                            codiciIstanzePrimarieSet);
                } catch (Exception e) {
                    LogUtil.error(this, methodName, "error during single area with codiceIstanza " + codiceIstanza, e);
                }
            }

        }

    }

    @SuppressWarnings("unchecked")
    private List<Integer> intersezioneWithAree(List<Istanze> istanze, String idComune, EntityManager entityManager,
            List<Integer> idareelist) {
        String qry = "SELECT DISTINCT ist.CODICEISTANZA FROM AREE a1 "
                + "JOIN AREEDETTAGLI a2 on a1.IDCOMUNE = a2.IDCOMUNE AND a1.CODICEAREA = a2.CODICEAREA "
                + "JOIN ISTANZESTRADARIO  ist on a2.IDCOMUNE = ist.IDCOMUNE AND a2.CODICESTRADARIO = ist.CODICESTRADARIO "
                + "WHERE a1.IDCOMUNE = :idcomune and a1.CODICEAREA IN :idaree";
        List<Integer> codiciIstanzeByAreaL = entityManager.createNativeQuery(qry, Integer.class)
                .setParameter("idcomune", idComune).setParameter("idaree", idareelist).getResultList();
        Set<Integer> codiciIstanzeByArea = toSetCodiciIstanzeByArea(codiciIstanzeByAreaL);
        List<Integer> codiciIstanze = new ArrayList<Integer>();
        for (Istanze istanza : istanze) {
            if (codiciIstanzeByArea.contains(istanza.getPkId().getCodice())) {
                codiciIstanze.add(istanza.getPkId().getCodice());
            }
        }
        return codiciIstanze;
    }

    private Set<Integer> toSetCodiciIstanzeByArea(List<Integer> codiciIstanzeByAreaL) {
        Set<Integer> codiciIstanzeByArea = new HashSet<>();
        for (Integer intt : codiciIstanzeByAreaL) {
            codiciIstanzeByArea.add(intt);// Il distinct già agisce di suo, ma preferisco agire su un Set
        }
        return codiciIstanzeByArea;
    }

    private RicalcoloFilter costruisciFiltroRicalcolo(String software, Date dallaData, Date allaData) {

        RicalcoloFilter filter = new RicalcoloFilter();
        if (!software.equals(WebConstants.SOFTWARE_TT)) {
            filter.setSoftware(software);
        }
        filter.setDallaData(dallaData);
        filter.setAllaData(allaData);
        return filter;
    }

    private String checkLengthOnErrorDescription(String description, String methodName) {

        String errdescription = description;
        try {
            if (errdescription.getBytes(WebConstants.UFF8CHARSET).length > WebConstants.LENGTHDESCR) {
                errdescription = "Guardare il server log per i dettagli";
            }
        } catch (Exception e2) {
            LogUtil.error(this, methodName, "errore nel get bytes UTF8 dell'errdescription", e2);
            errdescription = "Guardare il server log per i dettagli";
        }
        return errdescription;
    }

    private String checkLengthOnEventDescription(String exIstEvent) {

        String methodName = "checkLengthOnEventDescription(...)";
        String errIstEvent = exIstEvent;
        try {
            if (errIstEvent.getBytes(WebConstants.UFF8CHARSET).length > WebConstants.LENGTHDESCRISTEV) {
                errIstEvent = "Errore su istanza : Guardare il server log per i dettagli";
            }
        } catch (Exception e2) {
            LogUtil.error(this, methodName, "errore nel get bytes UTF8 dell'errdescription", e2);
            errIstEvent = "Errore su istanza : Guardare il server log per i dettagli";
        }
        return errIstEvent;
    }

    private void handleExceptionRicalcolaAreeP(EntityManagerFactory entityManagerFactory, EntityManager entityManager,
            String idComune, RicalcoloAreePK pk, Exception ex, IstanzeCountersBean istanzeCountersBean) {

        String methodName = "handleExceptionRicalcolaAreeP";
        LogUtil.error(this, methodName, "error during async process for id " + pk, ex);
        String errdescription = ex + WebConstants.EMPTY;
        errdescription = checkLengthOnErrorDescription(errdescription, methodName);
        new RicalcoloAreeWriter().updateRicalcoloAreeForError(pk, 0, 0, 0, errdescription, WebConstants.IN_ERRORE,
                entityManagerFactory);
        if (istanzeCountersBean.getCodiceIstanzaExc() != null) {
            String errIstEvent = "Errore su istanza : " + ex;
            errIstEvent = checkLengthOnEventDescription(errIstEvent);

            try {
                new IstanzeWriter().insertInIstanzeEventi(idComune, istanzeCountersBean.getCodiceIstanzaExc(),
                        errIstEvent, entityManagerFactory);
            } catch (Exception e) {
                LogUtil.error(this, methodName, "error during inserting event ", ex);
            }

        }
        if (entityManager != null && entityManager.getTransaction().isActive()) {
            LogUtil.info(this, methodName, "Rolling back transaction");
            entityManager.getTransaction().rollback();
            LogUtil.info(this, methodName, "Transaction rolled back");
        }
        if (istanzeCountersBean.getCodiceIstanzaExc() == null) {
            throw new RuntimeException("error during async process " + istanzeCountersBean.getCodiceIstanzaCorrente()
                    + ": " + ex.getMessage());
        } else {
            LogUtil.error(this, methodName, "error during async process "
                    + istanzeCountersBean.getCodiceIstanzaCorrente() + ": " + ex.getMessage(), ex);
        }
    }

    private void ricalcolaAreeP(RicalcoloAreeInBean ricalcoloAreeInBean, RicalcoloAreeTriple triplebean,
            EntityManagerFactory entityManagerFactory) {

        String methodName = "ricalcolaAreeP(...)";
        String idComune = ricalcoloAreeInBean.getIdcomune();
        RicalcoloAreePK pk = RicalcoloAreePK.getIsttance(idComune, ricalcoloAreeInBean.getPk());
        LogUtil.info(this, methodName, "method ricalcolaAreeP launched for id " + pk);
        EntityManager entityManager = null;
        IstanzeCountersBean istanzeCountersBean = new IstanzeCountersBean();
        istanzeCountersBean.setCodiceIstanzaCorrente(-1);
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            /*
             * La singola istanza continueremo a gestirla come prima con l'apposito metodo,
             * mentre più istanze le gestiremo come col ricalcolo aree, un gruppo di istanze
             * per singola testata
             */
            if (ricalcoloAreeInBean.getCodiciIstanze() != null && ricalcoloAreeInBean.getCodiciIstanze().size() == 1) {
                triplebean.setDafare(1);
                triplebean.setTotali(1);
                new IstanzeWriter().gestisciIstanzaSingola(entityManagerFactory, entityManager, idComune,
                        ricalcoloAreeInBean.getCodiciIstanze().get(0), methodName, pk, triplebean, istanzeCountersBean);
            } else {
                gestisciAree(entityManagerFactory, entityManager, ricalcoloAreeInBean, istanzeCountersBean, triplebean);
            }

            new RicalcoloAreeWriter().updateRicalcoloAreeComplete(pk, triplebean, entityManagerFactory); // Questo non
                                                                                                         // richiede il
                                                                                                         // NEW
            LogUtil.info(this, methodName, "final committing");
            entityManager.getTransaction().commit();
            LogUtil.info(this, methodName, "commit executed");
        } catch (Exception ex) {
            handleExceptionRicalcolaAreeP(entityManagerFactory, entityManager, idComune, pk, ex, istanzeCountersBean);
        } finally {
            if (entityManager != null) {
                LogUtil.info(this, methodName, "Closing entityManager");
                entityManager.close();
                LogUtil.info(this, methodName, "Closed entityManager");
            }
        }
    }
}
