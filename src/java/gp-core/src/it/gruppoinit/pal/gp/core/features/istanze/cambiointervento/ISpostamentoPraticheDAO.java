package it.gruppoinit.pal.gp.core.features.istanze.cambiointervento;

import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;

@SuppressWarnings("rawtypes")
public interface ISpostamentoPraticheDAO extends BaseDAO {

    Set<Integer> findIstanzeDaSpostare(String idComune, Integer codiceInterventoOrigine);

    void spostaIntervento(String idComune, Integer codiceInterventoOrigine, Integer codiceInterventoDestinazione);

    void aggiungiRuoli(Set<Integer> elencoIstanze, Set<Integer> idRuoliDaAggiungere);

    Set<Integer> schedeMancanti(String idComune, Integer codiceIstanza, Set<Integer> idSchedeDaAggiungere);

    void insertSchedeMancanti(String idComune, Integer codiceIstanza, Set<Integer> elenco);
}
