package it.alveo.segnalazioniproxy.servicies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.alveo.segnalazioniproxy.dao.*;
import it.alveo.segnalazioniproxy.entities.*;
import it.alveo.segnalazioniproxy.enums.Stato;
import it.alveo.stc.RichiestaPraticaResponse;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SegnalazioneService {

    private final SgPraticheRepository sgPraticheRepository;
    private final SgConfigurazioniRepository sgConfigurazioniRepository;
    private final SgConfigurazioniEntiRepository sgConfigurazioniEntiRepository;
    private final SgDizionarioRepository sgDizionarioRepository;
    private final SgPraticheAllegatiRepository sgPraticheAllegatiRepository;

    @Autowired
    public SegnalazioneService(SgPraticheRepository sgPraticheRepository, SgConfigurazioniRepository sgConfigurazioniRepository, SgConfigurazioniEntiRepository sgConfigurazioniEntiRepository, SgDizionarioRepository sgDizionarioRepository, SgPraticheAllegatiRepository sgPraticheAllegatiRepository) {
        this.sgPraticheRepository = sgPraticheRepository;
        this.sgConfigurazioniRepository = sgConfigurazioniRepository;
        this.sgConfigurazioniEntiRepository = sgConfigurazioniEntiRepository;
        this.sgDizionarioRepository = sgDizionarioRepository;
        this.sgPraticheAllegatiRepository = sgPraticheAllegatiRepository;
    }

    @Transactional
    public SgPratiche creaSegnalazione(String alias, String software, String clientId, String utente, String codiceComune, int idCategoria, String stato, String oggetto, String testo) throws JSONException {

        // Recupera la configurazione
        SgConfigurazioni configurazione = sgConfigurazioniRepository.findByAliasAndSoftware(alias, software)
                .orElseThrow(() -> new IllegalArgumentException("Configurazione non trovata con alias: `" + alias + "` e software: `" + software + "`"));

        // Verifica che l'ente/comune sia valido
        if (!sgConfigurazioniEntiRepository.existsByCodiceComuneAndConfigurazioneId(codiceComune, configurazione.getId()))
            throw new IllegalArgumentException("Ente/comune non trovato con codiceComune: `" + codiceComune + "` e id configurazione: `" + configurazione.getId() + "`");

        // Verifica che la categoria sia valida
        SgDizionario dizionario = sgDizionarioRepository.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Dizionario/categoria non trovata con id: `" + idCategoria + "`"));
        if (!dizionario.getAttivabile()) {
            throw new IllegalArgumentException("Dizionario/categoria NON attivabile, id: `" + idCategoria + "`");
        }


        // Crea una nuova pratica
        SgPratiche nuovaPratica = new SgPratiche();
        nuovaPratica.setUuid(UUID.randomUUID().toString());  // Genera un UUID unico per la pratica
        nuovaPratica.setConfigurazione(configurazione);
        nuovaPratica.setDizionario(dizionario);
        nuovaPratica.setCodiceComune(codiceComune);
        nuovaPratica.setStato(Stato.valueOf(stato));
        nuovaPratica.setOggetto(oggetto);
        nuovaPratica.setTesto(testo);
        nuovaPratica.setUtente(utente);
        nuovaPratica.setXClientId(clientId);

        // Salva la pratica nel database
        return sgPraticheRepository.save(nuovaPratica);
    }

    @Transactional
    public SgPratiche updateSegnalazione(String alias, String software, int configurationId, String clientId, String uuid, String utente, String codiceComune, int idCategoria, String stato, String oggetto, String testo) throws JSONException {

        // Verifica se la pratica è presente
        SgPratiche pratica = sgPraticheRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Segnalazione non trovata per uuid: `" + uuid + "`"));

        if (pratica.getStato() == Stato.ELIMINATA) {
            throw new IllegalStateException("La pratica con uuid: `" + uuid + "` non può essere ricevuta perché eliminata");
        }
        if (pratica.getStato() != Stato.IN_COMPILAZIONE) {
            throw new IllegalStateException("La pratica con uuid: `" + uuid + "` non può essere modificata");
        }

        // Verifica che l'ente/comune sia valido
        if (!sgConfigurazioniEntiRepository.existsByCodiceComuneAndConfigurazioneId(codiceComune, configurationId))
            throw new IllegalArgumentException("Ente/comune non trovato con codiceComune: `" + codiceComune + "` e id configurazione: `" + configurationId + "`");

        // Verifica che la categoria sia valida
        SgDizionario dizionario = sgDizionarioRepository.findById(idCategoria)
                .orElseThrow(() -> new IllegalArgumentException("Dizionario/categoria non trovata con id: `" + idCategoria + "`"));
        if (!dizionario.getAttivabile()) {
            throw new IllegalArgumentException("Dizionario/categoria NON attivabile, id: `" + idCategoria + "`");
        }

        pratica.setDizionario(dizionario);
        pratica.setCodiceComune(codiceComune);
        pratica.setStato(Stato.valueOf(stato));
        pratica.setOggetto(oggetto);
        pratica.setTesto(testo);
        pratica.setUtente(utente);
        pratica.setXClientId(clientId);

        // Salva la pratica nel database aggiornandola
        return sgPraticheRepository.save(pratica);
    }

    @Transactional
    public SgPratiche deleteSegnalazione(String alias, String software, int configurationId, String clientId, String uuid) throws JSONException {

        // Verifica se la pratica è presente
        SgPratiche pratica = sgPraticheRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Segnalazione non trovata per uuid: `" + uuid + "`"));

        if (pratica.getStato() == Stato.ELIMINATA) {
            throw new IllegalStateException("La pratica con uuid: `" + uuid + "` è già stata eliminata");
        }
        if (pratica.getStato() != Stato.IN_COMPILAZIONE) {
            throw new IllegalStateException("La pratica con uuid: `" + uuid + "` non può essere eliminata");
        }

        // Eliminazione solo logica, NON fisica
        pratica.setStato(Stato.ELIMINATA);
        pratica.setXClientId(clientId);

        // Salva la pratica nel database aggiornandola
        return sgPraticheRepository.save(pratica);
    }

    public String ottieniSegnalazioni(String alias, String software, int configurationId, String clientId,
                                      String utente, List<String> comune, List<Integer> categoria,
                                      LocalDateTime dallaData, LocalDateTime allaData, String statoAvanzamento, String statoPratica,
                                      Integer limit, Integer offset) throws JSONException {

        // Se i parametri limit e offset non sono forniti, imposta valori di default
        limit = (limit != null) ? limit : 100;
        offset = (offset != null) ? offset : 0;

        // Imposta la paginazione
        Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by("dataInvio").ascending());

        // Aggiunge % per il LIKE, se il dato è presente
        String likeStatoAvanzamento = statoAvanzamento != null ? "%" + statoAvanzamento + "%" : null;
        String likeStatoPratica = statoPratica != null ? "%" + statoPratica + "%" : null;

        // Recupera le segnalazioni filtrando con i parametri forniti
        Page<SgPratiche> pratichePage = sgPraticheRepository.findSelezionePraticheNotEliminate(
                configurationId, utente, comune, categoria, dallaData, allaData, likeStatoAvanzamento, likeStatoPratica, pageable);

        // Calcola il totale delle segnalazioni
        long total = sgPraticheRepository.countSelezionePraticheNotEliminate(
                configurationId, utente, comune, categoria, dallaData, allaData, likeStatoAvanzamento, likeStatoPratica);

        // Recupera il numero degli allegati per ogni pratica
        Map<String, Integer> countPraticheAllegatiMap = getNumeroAllegatiPerPratica(pratichePage);

        // Mappa le segnalazioni per il JSON
        List<Map<String, Object>> segnalazioni = pratichePage.stream().map(pratica -> {
            Map<String, Object> segnalazione = new LinkedHashMap<>();
            segnalazione.put("identificativo", pratica.getUuid());
            segnalazione.put("oggetto", pratica.getOggetto());
            segnalazione.put("comune", pratica.getCodiceComune());
            segnalazione.put("stato", pratica.getStato().toString());
            segnalazione.put("data_invio", pratica.getDataInvio() != null ? pratica.getDataInvio().toString() : null);    //Necessario toString perché Jackson non supporta LocalDateTime
            segnalazione.put("stato_avanzamento", pratica.getStatoAvanzamentoEnte());
            segnalazione.put("data_creazione", pratica.getDataCreazione() != null ? pratica.getDataCreazione().toString() : null);    //Necessario toString perché Jackson non supporta LocalDateTime
            segnalazione.put("conteggio_allegati", countPraticheAllegatiMap.get(pratica.getUuid()) != null ? countPraticheAllegatiMap.get(pratica.getUuid()) : 0);
            segnalazione.put("utente", pratica.getUtente());
            segnalazione.put("categoria_id", pratica.getDizionario().getId());

            return segnalazione;
        }).toList();

        // Creazione del risultato finale
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", total);
        resultMap.put("limit", limit);
        resultMap.put("offset", offset);
        resultMap.put("segnalazioni", segnalazioni);

        String result;
        try {
            result = new ObjectMapper().writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Transactional
    public Map<String, Object> getSegnalazioneJson(String alias, String software, int configurationId, String clientId, String uuid, RichiestaPraticaResponse richiestaPraticaResponse) {

        // Verifica se la pratica è presente
        SgPratiche pratica = sgPraticheRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Segnalazione non trovata per uuid: `" + uuid + "`"));

        if (pratica.getStato() == Stato.ELIMINATA) {
            throw new IllegalStateException("La pratica con uuid: `" + uuid + "` non può essere ricevuta perché eliminata");
        }

        // Verifica che l'ente/comune sia valido
        SgConfigurazioniEnti ente = sgConfigurazioniEntiRepository.findByCodiceComuneAndConfigurazioneId(pratica.getCodiceComune(), configurationId)
                .orElseThrow(() -> new IllegalArgumentException("Ente/comune non trovato con codiceComune: `" + pratica.getCodiceComune() + "` e id configurazione: `" + configurationId + "`"));

        // Recupera gli allegati relativi alla pratica
        List<SgPraticheAllegati> allegati = sgPraticheAllegatiRepository.findAllByPraticaUuid(uuid);

        Map<String, Object> response = new HashMap<>();
        response.put("identificativo", pratica.getUuid());
        response.put("data_invio", pratica.getDataInvio());
        response.put("data_protocollo", pratica.getDataProtocollo());
        response.put("numero_protocollo", pratica.getNumeroProtocollo());
        response.put("stato_avanzamento", pratica.getStatoAvanzamentoEnte());
        response.put("riferimento_ente", pratica.getRiferimentoEnte());

        Map<String, Object> segnalazione = new HashMap<>();
        segnalazione.put("utente", pratica.getUtente());

        Map<String, String> comune = new HashMap<>();
        comune.put("codice", pratica.getCodiceComune());
        comune.put("descrizione", ente.getDescrizioneComune());
        segnalazione.put("comune", comune);

        Map<String, Object> categoria = new HashMap<>();
        categoria.put("id", pratica.getDizionario().getId());
        categoria.put("descrizione", pratica.getDizionario().getDescrizione());
        segnalazione.put("categoria", categoria);

        segnalazione.put("stato", pratica.getStato().name());
        segnalazione.put("oggetto", pratica.getOggetto());
        segnalazione.put("testo", pratica.getTesto());

        response.put("segnalazione", segnalazione);

        // Inserisce i dati delle fasi solo se è arrivata una risposta da STC (quindi se gli era già stata inviata)
        if (richiestaPraticaResponse == null
                || richiestaPraticaResponse.getDettaglioPratica() == null
                || richiestaPraticaResponse.getDettaglioPratica().getListaAttivita() == null
                || richiestaPraticaResponse.getDettaglioPratica().getListaAttivita().isEmpty()) {
            response.put("fasi", null);
        } else {
            List<Map<String, String>> fasiJson = richiestaPraticaResponse.getDettaglioPratica().getListaAttivita().stream()
                    .sorted(Comparator.comparing(
                            attivita -> attivita.getDataAttivita() != null
                                    ? attivita.getDataAttivita().toGregorianCalendar().toZonedDateTime().toLocalDateTime()
                                    : null,
                            Comparator.nullsLast(Comparator.reverseOrder())
                    ))
                    .map(attivita -> {
                        Map<String, String> mappa = new HashMap<>();
                        mappa.put("id", attivita.getIdAttivita());
                        mappa.put("tipo", attivita.getTipoAttivita() != null ? attivita.getTipoAttivita().getCodice() : null);
                        mappa.put("descrizione", attivita.getTipoAttivita() != null ? attivita.getTipoAttivita().getDescrizione() : null);
                        mappa.put("data", attivita.getDataAttivita() != null ? attivita.getDataAttivita().toString() : null);
                        mappa.put("annotazioni", attivita.getParere());
                        return mappa;
                    })
                    .toList();

            segnalazione.put("fasi", fasiJson);
        }

        if (allegati.isEmpty()) {
            response.put("allegati", null);
        } else {
            List<Map<String, String>> allegatiJson = allegati.stream()
                    .sorted(Comparator.comparing(SgPraticheAllegati::getNomeFile, Comparator.nullsLast(Comparator.naturalOrder())))
                    .map(allegato -> {
                        Map<String, String> mappa = new HashMap<>();
                        mappa.put("id_allegato", allegato.getUuid());
                        mappa.put("nome_file", allegato.getNomeFile());
                        return mappa;
                    })
                    .toList();

            segnalazione.put("allegati", allegatiJson);
        }

        return response;
    }

    // Da chiamare possibilmente dopo checkConfigurazioneSegnalazione, per essere sicuri che la polizza esista
    public SgPratiche getSegnalazione(String uuid) {
        return sgPraticheRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Segnalazione non trovata per uuid: `" + uuid + "`"));
    }

    public void checkConfigurazioneSegnalazione(int configurationId, String uuid) {
        // Verifica se coppia configurazione-pratica è valida
        if (!sgPraticheRepository.existsByConfigurazioneIdAndUuid(configurationId, uuid))
            throw new IllegalArgumentException("Segnalazione non trovata per uuid: `" + uuid + "` e id configurazione: `" + configurationId + "`");
    }

    public Map<String, Integer> getNumeroAllegatiPerPratica(Page<SgPratiche> pratichePage) {
        List<String> praticheUuids = pratichePage.getContent().stream()
                .map(SgPratiche::getUuid)
                .toList();

        List<Object[]> results = sgPraticheAllegatiRepository.countByPraticaUuidIn(praticheUuids);

        return results.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],     // UUID della pratica
                        row -> ((Number) row[1]).intValue()     // Conteggio degli allegati
                ));
    }

    public SgPratiche findByUuidAndStato(String idSegnalazione, Stato stato) {
        return sgPraticheRepository.findByUuidAndStato(idSegnalazione, stato);
    }

}
