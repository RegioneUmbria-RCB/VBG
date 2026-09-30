package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ArchiviazioniDAO;
import it.gruppoinit.pal.gp.core.domain.Archiviazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneDocumentaleFileAvvio;
import it.gruppoinit.pal.gp.core.service.helper.IstanzePerArchiviazioneFilter;
import it.gruppoinit.pal.gp.core.service.helper.VerticalizzazioneArchiviazioneDocumentale;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface ArchiviazioniService extends BaseService<Archiviazioni, PkId> {

    /**
     * @see ArchiviazioniDAO#findAll(Integer, Integer)
     */
    public List<Archiviazioni> findAll(Integer firstResult, Integer maxResult, Boolean isSoloConErrori);

    public Integer archiviaDocumentiIstanza(Integer codiceIstanza, Archiviazioni archiviazioneCorrente, Integer maxOggettiArchiviabili,
	    VerticalizzazioneArchiviazioneDocumentale vad, ArchiviazioneDocumentaleFileAvvio fileAvvio) throws Exception;

    //    public Integer archiviaDocumentiPerIstanza(Integer codiceIstanza, Archiviazioni archiviazioneCorrente,
    //	    VerticalizzazioneArchiviazioneDocumentale vad, ArchiviazioneDocumentaleFileAvvio fileAvvio) throws Exception;
    public Integer archiviaDocumentiPerOggetto(Integer codice, String prefissoNomePacchetto, VerticalizzazioneArchiviazioneDocumentale vad,
	    ArchiviazioneDocumentaleFileAvvio fileAvvio, IstanzePerArchiviazioneFilter filter, String idSession) throws Exception;

    public Archiviazioni insertArchiviazioneErroreGenerale(String errore, String prefissoNomePacchetto);

    public int countRecords();
}
