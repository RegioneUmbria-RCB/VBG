package it.alveo.segnalazioniproxy.servicies;

import it.alveo.segnalazioniproxy.dao.SgConfigurazioniEntiRepository;
import it.alveo.segnalazioniproxy.dao.SgConfigurazioniRepository;
import it.alveo.segnalazioniproxy.dao.SgDizionarioRepository;
import it.alveo.segnalazioniproxy.entities.SgConfigurazioni;
import it.alveo.segnalazioniproxy.entities.SgDizionario;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConfigurazioneService {

    private final SgConfigurazioniRepository sgConfigurazioniRepository;
    private final SgConfigurazioniEntiRepository sgConfigurazioniEntiRepository;
    private final SgDizionarioRepository sgDizionarioRepository;

    @Autowired
    public ConfigurazioneService(SgConfigurazioniRepository sgConfigurazioniRepository, SgConfigurazioniEntiRepository sgConfigurazioniEntiRepository, SgDizionarioRepository sgDizionarioRepository) {
        this.sgConfigurazioniRepository = sgConfigurazioniRepository;
        this.sgConfigurazioniEntiRepository = sgConfigurazioniEntiRepository;
        this.sgDizionarioRepository = sgDizionarioRepository;
    }

    /**
     * Recupera l'id della configurazione
     *
     * @param alias    the alias
     * @param software the software
     * @return L'id della configurazione
     */
    public Integer getConfigurationId(String alias, String software) {

        // Recupera la configurazione verificando se alias e software sono validi
        SgConfigurazioni configurazione = sgConfigurazioniRepository.findByAliasAndSoftware(alias, software)
                .orElseThrow(() -> new IllegalArgumentException("Configurazione non trovata con alias: `" + alias + "` e software: `" + software + "`"));

        return configurazione.getId();
    }

    // Metodo principale per ottenere la configurazione
    public String getConfigurazione(String alias, String software, int configurationId, String clientId) throws JSONException {

        // Recupera codice e descrizione degli enti
        List<Map<String, Object>> entiCodiceDescrizione = sgConfigurazioniEntiRepository.findEntiByConfigurazioneIdNative(configurationId);
        if (entiCodiceDescrizione.isEmpty()) {
            throw new IllegalArgumentException("Enti non trovati con alias: `" + alias + "`, software: `" + software + "` e id configurazione: `" + configurationId + "`");
        }

        // Recupera i dizionari/categorie della configurazione
        List<Map<String, Object>> categorie = getCategorie(configurationId);
        if (categorie.isEmpty()) {
            throw new IllegalArgumentException("Categorie non trovate con alias: `" + alias + "`, software: `" + software + "` e id configurazione: `" + configurationId + "`");
        }

        // Costruzione del risultato finale
        JSONObject result = new JSONObject();
        result.put("enti", new JSONArray(entiCodiceDescrizione));  // Mappa direttamente gli enti
        result.put("categorie", new JSONArray(categorie));  // Mappa direttamente le categorie

        // Restituisce il risultato come stringa JSON
        return result.toString();
    }

    // Metodo per recuperare le categorie
    public List<Map<String, Object>> getCategorie(Integer configurazioneId) {
        List<SgDizionario> dizionari = sgDizionarioRepository.findAllByConfigurazioneId(configurazioneId);

        // Mappa per associare i dizionari per ID
        Map<Integer, Map<String, Object>> dizionarioMap = new HashMap<>();

        // Lista per i root nodes (senza padre)
        List<Map<String, Object>> rootNodes = new ArrayList<>();

        for (SgDizionario dizionario : dizionari) {
            // Mappatura del nodo corrente
            Map<String, Object> currentNode = mapDizionario(dizionario);
            dizionarioMap.put(dizionario.getId(), currentNode);

            if (dizionario.getPadre() == null) {
                rootNodes.add(currentNode); // Nodo senza padre è un nodo root
            } else {
                // Trova il genitore e aggiungi il nodo corrente come figlio
                Map<String, Object> parentNode = dizionarioMap.get(dizionario.getPadre().getId());
                if (parentNode != null) {
                    // Aggiungi il nodo corrente ai figli del nodo padre
                    List<Map<String, Object>> figli = (List<Map<String, Object>>) parentNode.get("categorie");
                    if (figli == null) {
                        figli = new ArrayList<>();
                        parentNode.put("categorie", figli);
                    }
                    figli.add(currentNode);
                }
            }
        }

        return rootNodes; // Restituisce solo i nodi root con tutta la loro gerarchia
    }

    // Metodo di mappatura del dizionario
    private Map<String, Object> mapDizionario(SgDizionario dizionario) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id", dizionario.getId());
        result.put("descrizione", dizionario.getDescrizione());
        result.put("annotazioni", dizionario.getAnnotazioni());
        result.put("attivabile", dizionario.getAttivabile());
        result.put("categorie", null);  // Placeholder per figli che verranno popolati successivamente
        return result;
    }

}
