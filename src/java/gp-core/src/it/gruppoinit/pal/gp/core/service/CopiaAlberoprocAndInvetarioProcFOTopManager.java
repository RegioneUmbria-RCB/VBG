package it.gruppoinit.pal.gp.core.service;

import java.util.List;

public interface CopiaAlberoprocAndInvetarioProcFOTopManager {

    public void eseguiCopia(String idComuneAlias, String software, List<String> softwares, Integer intervallogironi, Integer maxInterventi,
	    Integer maxProcedimenti);
}
