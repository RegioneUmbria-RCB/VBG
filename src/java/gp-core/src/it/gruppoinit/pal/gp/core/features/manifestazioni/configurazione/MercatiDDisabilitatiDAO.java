package it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDDisabilitati;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface MercatiDDisabilitatiDAO extends BaseDAO<MercatiDDisabilitati, PkId> {

    List<Integer> findByIdGiornata(Integer idGiornata);

    MercatiDDisabilitati findByDataAndIdPosteggio(Date dataDiRiferimento, Integer idPosteggio);

    void disabilita(MercatipresenzeT giornata, MercatiD posteggio, String note);

    void abilita(MercatipresenzeT giornata, MercatiD posteggio);
}
