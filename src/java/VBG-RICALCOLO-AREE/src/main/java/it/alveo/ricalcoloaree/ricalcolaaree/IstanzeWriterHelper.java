package it.alveo.ricalcoloaree.ricalcolaaree;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import io.micrometer.common.util.StringUtils;
import it.alveo.ricalcoloaree.bean.AreeDettagliBean;
import it.alveo.ricalcoloaree.bean.RicalcoloAreeTriple;
import it.alveo.ricalcoloaree.entities.Aree;
import it.alveo.ricalcoloaree.entities.AreeDettagli;
import it.alveo.ricalcoloaree.entities.IstanzeStradario;
import it.alveo.ricalcoloaree.entities.composefields.RicalcoloAreePK;
import it.alveo.ricalcoloaree.utils.LogUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;


public class IstanzeWriterHelper {

    @SuppressWarnings("unchecked")
    public void ricalcoloPerIstanza(Integer codiceistanza, String idComune, EntityManager em,
            EntityManagerFactory entityManagerFactory, RicalcoloAreeTriple triplebean, RicalcoloAreePK pk,
            Set<String> areeDescriptionSet, Map<Integer, Set<Integer>> codiciAreaByIstanzaMap, Set<Integer> codiciIstanzePrimarieSet) {

        String methodName = "ricalcoloPerIstanza(...)";
        LogUtil.debug(this, methodName,
                "ricalcolaPerIstanza: inizio elaborazione per l'istanza codice " + codiceistanza);
//        LogUtil.debug(this, methodName, "ricalcolaPerIstanza: eliminazione di istanzearee con autoins = 1  ");
//        String qry = "DELETE FROM IstanzeAree a WHERE a.id.idcomune = :idcomune AND a.id.codiceistanza = :codiceistanza AND a.autoins = :autoins";
//        Query query = em.createQuery(qry);
//        query.setParameter("idcomune", idComune);
//        query.setParameter("codiceistanza", codiceistanza);
//        query.setParameter("autoins", 1);
//        query.executeUpdate();
//        em.flush();
        List<IstanzeStradario> istanzestradarios = em
                .createNativeQuery(
                        "SELECT * FROM ISTANZESTRADARIO WHERE IDCOMUNE = :idcomune AND CODICEISTANZA = :codiceIstanza",
                        IstanzeStradario.class)
                .setParameter("idcomune", idComune).setParameter("codiceIstanza", codiceistanza).getResultList();
        List<AreeDettagliBean> list = new ArrayList<AreeDettagliBean>();
        
        if(istanzestradarios.isEmpty()) {
            LogUtil.info(this, methodName, "non sono stati trovati stradari per codiceistanza " + codiceistanza);
        }
        
        for (IstanzeStradario istanzestradario : istanzestradarios) {
            var aree = new AreeDettagliReader().getAreeDettagliFromIstanzeStradario(istanzestradario, idComune, em);
            list.addAll(aree);
        }
        LogUtil.info(this, methodName, "starting inserts for stradarios for codiceistanza " + codiceistanza);
        if (!list.isEmpty()) {
            var istanzeStradarioWriter = new IstanzeStradarioWriter();
            for (AreeDettagliBean dettagliobean : list) {
                istanzeStradarioWriter.insertArea(dettagliobean, em, codiciAreaByIstanzaMap, codiciIstanzePrimarieSet);
            }
        }else {
            LogUtil.info(this, methodName, "non ci sono aree da calcolare per codiceistanza " + codiceistanza);
        }
        LogUtil.info(this, methodName, "inserts ended for codiceistanza " + codiceistanza);
        // building description
        LogUtil.info(this, methodName, "grouping by idAreas for progress");

        for (AreeDettagliBean bean : list) {
            areeDescriptionSet.add(Optional.ofNullable(bean).map(AreeDettagliBean::getAreeDettagli)
                    .map(AreeDettagli::getAree).map(Aree::getDenominazione).orElse("Non definita"));
        }

        var descrizione = areeDescriptionSet.stream().collect(Collectors.joining(","));

        if (StringUtils.isBlank(descrizione)) {
            descrizione = "Nessuna area ricalcolata";
        }
        if (descrizione.length() > 1000) {
            descrizione = descrizione.substring(0, 1000);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Aree:  ").append(descrizione);
        LogUtil.info(this, methodName, sb.toString());
        // builded description
        new RicalcoloAreeWriter().updateRicalcoloAreeForCheckpoint(pk, triplebean.getDafare() - 1,
                triplebean.getFatti() + 1, triplebean.getTotali(), descrizione, entityManagerFactory);
        triplebean.setDafare(triplebean.getDafare() - 1); // PRIMA SI AGGIORNA, POI SI DECREMENTA
        triplebean.setFatti(triplebean.getFatti() + 1);
        LogUtil.debug(this, methodName, "ricalcolaPerIstanza: fine elaborazione per l'istanza codice " + codiceistanza);
    }
}
