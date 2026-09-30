package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface IAssegnazioneGruppiTestataDAO extends BaseDAO<AssegnazioneGruppiTestata, PkId> {

    public Integer verificaSeAperta(Integer fkGruppoIstrutori, Integer codiceIstanza);

    public Integer trovaTestataAperta(Integer fkGruppoIstrutori);
}
