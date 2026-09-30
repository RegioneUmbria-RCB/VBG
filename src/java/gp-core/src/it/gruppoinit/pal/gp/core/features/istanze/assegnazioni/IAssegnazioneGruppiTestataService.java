package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiTestata;

public interface IAssegnazioneGruppiTestataService {

    /**
     * Ritorna la testata riferita al gruppo istruttori passato aperta
     * 
     * @param fkGruppoIstrutori
     * @return
     */
    public Integer trovaTestataAperta(Integer fkGruppoIstrutori);

    /**
     * Verifica se la testata passata risulta aperta e ritorna l'id
     * 
     * @param fkGruppoIstrutori
     * @param codiceIstanza
     * @return
     */
    public Integer verificaSeAperta(Integer fkGruppoIstrutori, Integer codiceIstanza);

    public void insert(AssegnazioneGruppiTestata assegnazioneGruppiTestata);

    public AssegnazioneGruppiTestata findById(Integer idTestata);

    /**
     * Metodo che permette la chiusura di una testata di assegnazione operatori
     * 
     * @param idTestata
     * @param dataChiusura
     */
    public void chiudiAssegnazione(Integer idTestata, Date dataChiusura);

    /**
     * @see IAssegnazioneGruppiTestataService.chiudiAssegnazione
     * @param idTestata
     */
    public void chiudiAssegnazione(Integer idTestata);
}
