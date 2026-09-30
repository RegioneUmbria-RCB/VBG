package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerArchiviazioneDTO;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiIstanza;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiOggetto;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiOggettoList;
import it.gruppoinit.pal.gp.core.service.helper.IstanzePerArchiviazioneFilter;
import it.gruppoinit.pal.gp.core.service.helper.VerticalizzazioneArchiviazioneDocumentale;

import java.util.List;
import java.util.Set;

public interface IstanzePerArchiviazioneService extends BaseService<Istanze, PkId> {

    /**
     * Recupera istanze non aperte e che non hanno un riferimento in ARCHIVIAZIONE_ISTANZE
     * 
     * @param filter
     * @return
     */
    public List<IstanzePerArchiviazioneDTO> findIstanzePerArchiviazioneDocumentale(IstanzePerArchiviazioneFilter filter);

    /**
     * <pre>
     * Recupera tutte le istanza che hanno almeno un allegato da mandare in conservazione.
     * Oggetti da mandare in conservazione sono:
     * 	1. Documenti istanza
     *  2. Documenti dei movimenti
     *  3. Focumenti degli endo
     *  4. Procure
     * 
     * Un documento sarà da mandare in coseservazione se:
     *  1. Non ha un riferimento alla tabella archiviazione_oggetti
     *  2. L'istanza di cui fa parte non deve avere un rigo su archiviazione_istanze 
     *  
     *  Se c'è un record su archiviazione_istanze e non c'è su archiviazione_oggetti significa che i 
     *  documenti non sono stati mandati in coservazione perchè nell'istanza mancano dati per compilare
     *  i medatadi dei file per la conservazione, quindi è inutile fino a quando non verrà corretta ed
     *  eliminata la riga su archiviazione_istanze (coportamento precedente)
     *  
     *  
     *  
     * 
     * @param filter
     * @return
     * </pre>
     */
    public Set<IstanzePerArchiviazioneDTO> findIstanzeConOggettiPerArchiviazioneDocumentale(IstanzePerArchiviazioneFilter filter);

    public ArchiviazioneMetadatiIstanza findMetadatiIstanza(Integer codiceIstanza);

    public ArchiviazioneMetadatiOggettoList findOggettiArchiviabili(ArchiviazioneMetadatiIstanza metadatiIstanza,
	    VerticalizzazioneArchiviazioneDocumentale vad);

    public ArchiviazioneMetadatiOggettoList findOggettiArchiviabili(ArchiviazioneMetadatiIstanza metadatiIstanza,
	    IstanzePerArchiviazioneFilter filter, VerticalizzazioneArchiviazioneDocumentale vad);

    public String[] isOggettoArchiviabile(ArchiviazioneMetadatiIstanza metaIstanza, ArchiviazioneMetadatiOggetto metaOgg,
	    VerticalizzazioneArchiviazioneDocumentale vad);
}
