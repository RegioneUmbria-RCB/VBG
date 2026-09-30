package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MassiveParametri;
import it.gruppoinit.pal.gp.core.domain.MassiveTProtocollo;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.LetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocolloPerEnte;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametroConfigurazioneComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.DettaglioLetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ResocontoOperazioniMassive;

public interface IComunicazioniMassiveDAO {

    void insert(MassiveTestata testata);

    void insert(MassiveParametri parametro);

    void insertAllegatoFisso(int idTestata, int codiceOggetto);

    void insertLettera(int idTestata, int codiceLettera);

    void insertSoggettoFirmatario(int idTestata, int idSoggetto);

    MassiveTestata getTestataById(int idTestata);

    List<ParametroConfigurazioneComunicazione> getParametriByIdTestata(int idTestata);

    List<AllegatoComunicazione> getAllegatiFissiByIdTestata(int idTestata);

    List<LetteraComunicazione> getLettereComunicazioneByIdTestata(int idTestata);

    List<DettaglioLetteraComunicazione> getDettaglioLettereComunicazioneByIdTestata(int idTestata);

    List<Integer> getFirmatariByIdTestata(int idTestata);

    List<Integer> findByIdBollettazione(Integer idBollettazione);

    List<ResocontoOperazioniMassive> findResocontoOperazioniMassiveById(Integer idTestata);

    List<String> getDescrizioneFirmatariByIdTestata(int idTestata);

    void insertParametriProtocollo(MassiveTProtocollo parametriProtocollo);

    List<ParametriProtocolloPerEnte> getParametriProtocollazione(int idTestata);

    /**
     * Il metodo esegue la cancellazione <b>logica</b> della testata impostando la data_cancellazione nella tabella
     * massive_testata ed il codice Responsabile passato come riferimento e la data/ora
     * 
     * @param idTestata
     * @param codiceResponsabile
     * @param dataCancellazione
     */
    void eliminaMassiva(int idTestata, Integer codiceResponsabile, Date dataCancellazione);

    List<Integer> findByIdCommissioni(Integer idCommissioni);

    public ContestoComunicazioneEnum getContestoMassiva(int idTestata);

    public <T> void saveEntity(T entity);

    List<Integer> findByIdMercato(Integer idMercato);

    List<Integer> findIdTestataByGen(String sql, Object[] params, String fkscalar);
}
