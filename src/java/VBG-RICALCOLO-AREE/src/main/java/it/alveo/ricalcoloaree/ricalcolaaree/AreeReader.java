package it.alveo.ricalcoloaree.ricalcolaaree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mysql.cj.util.StringUtils;

import it.alveo.ricalcoloaree.bean.AreeDettagliBean;
import it.alveo.ricalcoloaree.constants.WebConstants;
import it.alveo.ricalcoloaree.utils.LogUtil;

public class AreeReader {

    public List<String> estraiDescrizioniAree(Map<String, List<AreeDettagliBean>> areeDettagliBeanMap, String idcomune,
            String methodName) {
        List<String> areeDescriptions = new ArrayList<String>();
        for (Map.Entry<String, List<AreeDettagliBean>> entry : areeDettagliBeanMap.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {// sempre true a dire il vero, ma gestiamo
                if (WebConstants.EMPTY.equals(entry.getKey())) {
                    LogUtil.info(this, methodName, "empty key, first areaDettagli id found : "
                            + entry.getValue().get(0).getAreeDettagli().getId() + "     idcomune:[" + idcomune + "]");
                    areeDescriptions.add("Descrizione non trovata");
                } else if (StringUtils
                        .isNullOrEmpty(entry.getValue().get(0).getAreeDettagli().getAree().getDenominazione())
                        || entry.getValue().get(0).getAreeDettagli().getAree().getDenominazione().trim().isEmpty()) {
                    LogUtil.info(this, methodName,
                            "empty description for key [" + entry.getKey() + "]   idcomune:[" + idcomune + "]");
                    LogUtil.info(this, methodName, "description: ["
                            + entry.getValue().get(0).getAreeDettagli().getAree().getDenominazione() + "]");
                    areeDescriptions.add("Descrizione non trovata");
                } else {
                    areeDescriptions.add(entry.getValue().get(0).getAreeDettagli().getAree().getDenominazione());
                }
            } else {
                LogUtil.info(this, methodName,
                        "empty value for key [" + entry.getKey() + "]   idcomune:[" + idcomune + "]");
                areeDescriptions.add("Descrizione non trovata");
            }
        }
        return areeDescriptions;
    }
    
}
