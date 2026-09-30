package it.gruppoinit.pal.gp.core.service;

import java.util.Date;

public interface ArchiviazioniManager {

    public void archiviazione(String idcomunealias, String[] software) throws Exception;

    public void eseguiArchiviazione(String idcomunealias, String software) throws Exception;

    //    public void eseguiArchiviazionePerIstanza(String idcomunealias, String software) throws Exception;
    public void eseguiArchiviazionePerOggetto(String idcomunealias, String software) throws Exception;

    public void eseguiArchiviazionePerOggetto(String idcomunealias, String software, Date da, Date a) throws Exception;
}
