package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeIstanze;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeIstanzeId;

public interface RicalcoloAreeIstanzeDAO extends BaseDAO<RicalcoloAreeIstanze, RicalcoloAreeIstanzeId> {

    public boolean existsRicalcoloInProgressIst(String uuidIstanza);

    public void aggiungiIstanzaARicalcolo(String uuIdRicalcolo, String uuIdIstanza);

    public List<RicalcoloAreeIstanze> findRicalcoloInProgressIst(String uuidIstanza);

    public List<String> getTestateDaRicalcolare();
}
