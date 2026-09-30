package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface GruppiEndoprocedimentiTService extends BaseService<GruppiEndoprocedimentiT, PkId> {

    /**
     * trova i gruppi del software corrente
     * 
     * @param textToSearch
     * @return
     */
    public List<GruppiEndoprocedimentiT> findByDescrizione(String textToSearch);

    /**
     * Elabora il file excel
     * 
     * @param file
     * @return una lista di eventuali errori
     */
    public List<CodiceDescrizioneBean> elaboraFileExcel(InputStream file) throws IOException;
}
