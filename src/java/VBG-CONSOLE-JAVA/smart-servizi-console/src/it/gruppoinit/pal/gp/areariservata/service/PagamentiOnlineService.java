package it.gruppoinit.pal.gp.areariservata.service;

import java.util.Map;

public interface PagamentiOnlineService {

    public String updateCalculateUrlPagamentoOnline(Integer codiceDomanda, String currentAppPath, String emailPagamentoOnline);

    public void updateRientroPagamenti(Integer codiceDomanda, Map<String, String[]> requestParamMap);
    //public void updateStatoPagamenti(Integer codiceDomanda, Integer identificativo, Map<String, String[]> requestParamMap);
}
