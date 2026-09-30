package it.alveo.ricalcoloaree.ricalcolaaree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.type.StandardBasicTypes;

import it.alveo.ricalcoloaree.utils.LogUtil;
import jakarta.persistence.EntityManager;

public class IstanzeReader {

    @SuppressWarnings("unchecked")
    public List<Object[]> findIdRicalcoloAreeByUuids(List<String> ricalcoloAreeIdL, String idcomune, EntityManager em) {

        return em.createNativeQuery("SELECT " + //
                " B.ID, A.ISTANZE_UUID, C.CODICEISTANZA " + //
                "FROM " + //
                " RICALCOLO_AREE B " + //
                "  INNER JOIN RICALCOLO_AREE_ISTANZE A ON B.IDCOMUNE = A.IDCOMUNE AND B.ID = A.ID_RICALCOLO_AREE "
                + " INNER JOIN ISTANZE C ON C.IDCOMUNE = A.IDCOMUNE AND C.UUID = A.ISTANZE_UUID " + "WHERE " + //
                " B.IDCOMUNE = :idcomune AND " + //
                " B.ID IN :ricalcoloAreeIdL AND " + " B.STATO = 'DA ESEGUIRE' " + "").setParameter("idcomune", idcomune)
                .setParameter("ricalcoloAreeIdL", ricalcoloAreeIdL).getResultList();

    }

    @SuppressWarnings("unchecked")
    public List<Integer> findByUuid(String uuid, String idcomune, EntityManager em) {

        List<Integer> codiciIstanza = em
                .createNativeQuery("SELECT CODICEISTANZA FROM ISTANZE WHERE IDCOMUNE = :idcomune AND UUID = :uuid",
                        Integer.class)
                .setParameter("idcomune", idcomune).setParameter("uuid", uuid).getResultList();
        return codiciIstanza;
    }

    @SuppressWarnings("unchecked")
    private List<Object[]> findCodiciAreaOnIstanzeAree(List<Integer> codiciistanze, String idcomune, EntityManager em) {

        org.hibernate.query.NativeQuery<Object[]> query = em.createNativeQuery(
                "SELECT A.CODICEAREA, A.CODICEISTANZA FROM ISTANZEAREE A WHERE A.IDCOMUNE = :idcomune AND A.CODICEISTANZA IN :coidiciistanze")
                .unwrap(org.hibernate.query.NativeQuery.class);

        query.addScalar("CODICEAREA", StandardBasicTypes.INTEGER);
        query.addScalar("CODICEISTANZA", StandardBasicTypes.INTEGER);
        query.setParameter("idcomune", idcomune);
        query.setParameter("coidiciistanze", codiciistanze);

        return query.getResultList();

    }

    public Map<Integer, Set<Integer>> findCodiciAreaOnIstanzeAreeMap(List<Integer> codiciistanze, String idcomune,
            EntityManager em) {
        String methodName = "findCodiciAreaOnIstanzeAreeMap(...)";
        LogUtil.info(this, methodName, "sto memorizzando i codiciarea su cui fare check");
        Map<Integer, Set<Integer>> codiciAreaByIstanzaMap = new HashMap<Integer, Set<Integer>>();
        List<Object[]> result = findCodiciAreaOnIstanzeAree(codiciistanze, idcomune, em);
        if (result != null) {
            for (Object[] rec : result) {
                Integer codiceistanza = (Integer) rec[1];
                if (!codiciAreaByIstanzaMap.containsKey(codiceistanza)) {
                    codiciAreaByIstanzaMap.put(codiceistanza, new HashSet<Integer>());
                }
                codiciAreaByIstanzaMap.get(codiceistanza).add((Integer) rec[0]);
            }
        }
        LogUtil.info(this, methodName, "ho memorizzando i codiciarea su cui fare check");
        return codiciAreaByIstanzaMap;
    }

    @SuppressWarnings("unchecked")
    private List<Integer> findIstanzePrimarioonIstanzeAree(List<Integer> codiciistanze, String idcomune,
            EntityManager em) {

        List<Integer> codiciIstanza = em.createNativeQuery(
                "SELECT CODICEISTANZA FROM ISTANZEAREE WHERE IDCOMUNE = :idcomune AND CODICEISTANZA IN :codiciistanze AND PRIMARIO = :primario",
                Integer.class).setParameter("idcomune", idcomune).setParameter("codiciistanze", codiciistanze)
                .setParameter("primario", true).getResultList();
        return codiciIstanza;
    }

    public Set<Integer> findCodiciIstanzePrimarieSet(List<Integer> codiciistanze, String idcomune, EntityManager em) {
        String methodName = "codiciIstanzePrimarieSet(...)";
        LogUtil.info(this, methodName, "sto memorizzando le istanze primarie");
        Set<Integer> codiciIstanzePrimarieSet = new HashSet<Integer>();
        List<Integer> codiciistanzeprimarie = findIstanzePrimarioonIstanzeAree(codiciistanze, idcomune, em);
        if (codiciistanzeprimarie != null) {
            for (Integer codiceistanza : codiciistanzeprimarie) {
                codiciIstanzePrimarieSet.add(codiceistanza);
            }
        }
        LogUtil.info(this, methodName, "ho memorizzando le istanze primarie");
        return codiciIstanzePrimarieSet;
    }

}
