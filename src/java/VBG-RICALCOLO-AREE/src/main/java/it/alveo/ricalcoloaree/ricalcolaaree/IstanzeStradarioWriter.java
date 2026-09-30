package it.alveo.ricalcoloaree.ricalcolaaree;

import java.util.Map;

import java.util.Set;

import it.alveo.ricalcoloaree.bean.AreeDettagliBean;
import it.alveo.ricalcoloaree.entities.IstanzeAree;
import it.alveo.ricalcoloaree.utils.LogUtil;
import jakarta.persistence.EntityManager;

public class IstanzeStradarioWriter {

    public void insertArea(AreeDettagliBean dettagliobean, EntityManager em,
            Map<Integer, Set<Integer>> codiciAreaByIstanzaMap, Set<Integer> codiciIstanzePrimarieSet) {

        String methodName = "insertStradarioBySingleArea(...)";
        LogUtil.debug(this, methodName, "begin");
        try {

            if (!(codiciAreaByIstanzaMap.containsKey(dettagliobean.getCodiceIstanza())
                    && codiciAreaByIstanzaMap.get(dettagliobean.getCodiceIstanza())
                            .contains(dettagliobean.getAreeDettagli().getAree().getCodicearea()))) {
                IstanzeAree istanzearee = new IstanzeAree();
                istanzearee.setIdcomune(dettagliobean.getIdcomune());
                istanzearee.setCodicearea(dettagliobean.getAreeDettagli().getAree().getCodicearea());
                istanzearee.setCodiceistanza(dettagliobean.getCodiceIstanza());
                istanzearee.setAutoins(1);
                istanzearee.setPrimario(!(codiciIstanzePrimarieSet.contains(dettagliobean.getCodiceIstanza())));
                istanzearee.setIstanza(dettagliobean.getIstanza());
//		AreePK areePK = new AreePK();
//		areePK.setIdcomune(istanzearee.getIdcomune());
//		areePK.setCodicearea(istanzearee.getCodicearea());
//		Aree newAree = em.find(Aree.class, areePK);
//		istanzearee.setArea(newAree);
//		PkId istanzaPk = new PkId();
//		istanzaPk.setIdcomune(istanzearee.getIdcomune());
//		istanzaPk.setCodice(istanzearee.getCodiceistanza());
//		Istanze newInstanze = em.find(Istanze.class, istanzaPk);
//		istanzearee.setIstanza(newInstanze);
                em.merge(istanzearee);
                em.flush();
            }
            LogUtil.debug(this, methodName, "end");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
