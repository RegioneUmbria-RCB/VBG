package it.gruppoinit.pal.gp.areariservata.dao;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AlberoProcARJDAO extends BaseDAO<Alberoproc, PkId> {

    public List<Alberoproc> findSubTree(String idcomune, String scCodice, boolean filtraSoloComunica, boolean soloFlagModulisticaNazionale);

    public boolean hasSubTree(String idcomune, String scCodice);
}
