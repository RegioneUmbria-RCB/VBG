package it.gruppoinit.pal.gp.core.features.nodopagamenti.jobs.csipiemonte;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.DettPosizioneDebitoriaBean;

public interface AllineaPosizioniDebitorieDAO extends BaseDAO {

    public List<DettPosizioneDebitoriaBean> getPosizioniDaAllineare();
}
