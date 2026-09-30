package it.gruppoinit.pal.gp.core.service;

import java.util.Map;

public interface VelocityRendererService {

    public String getHtmlEndo2(Integer codiceAlberoproc, Map<Object, Object> contextData, String codiceComune, String codificaEnteRfc53,
	    boolean soloSchedaegionale);

    public String getHtmlEndo1(String idcomuneCodiceInventario, Integer codiceInventario, Integer idAlberoproc, Map<Object, Object> contextData,
	    String codiceComune, String rfc53CodiceEnte, boolean soloSchedaegionale, String idComuneAPEndoLoc, String codiceAPEndoLoc);

    public String renderTemplate(Map<Object, Object> contextData, String template);
}
