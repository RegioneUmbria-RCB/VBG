package it.alveo.segnalazioniproxy.servicies;

import it.alveo.oggetti.OggettiDeleteResponse;
import it.alveo.oggetti.OggettiInsertResponse;
import it.alveo.segnalazioniproxy.clients.GestoreFileClient;
import it.alveo.segnalazioniproxy.dao.SgConfigurazioniRepository;
import it.alveo.segnalazioniproxy.dao.SgPraticheAllegatiRepository;
import it.alveo.segnalazioniproxy.dao.SgPraticheRepository;
import it.alveo.segnalazioniproxy.entities.SgConfigurazioni;
import it.alveo.segnalazioniproxy.entities.SgPratiche;
import it.alveo.segnalazioniproxy.entities.SgPraticheAllegati;
import it.alveo.segnalazioniproxy.enums.Stato;
import it.alveo.segnalazioniproxy.utils.ByteArrayDataSource;
import jakarta.activation.DataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AllegatiService {
    private static final Logger log = LoggerFactory.getLogger(AllegatiService.class);

    private final SgPraticheAllegatiRepository sgPraticheAllegatiRepository;
    private final SgPraticheRepository sgPraticheRepository;
    private final GestoreFileClient gestoreFileClient;
    private final SigeproSecurityService sigeproSecurityService;

    // limite massimo di allegati che una pratica può avere
    @Value("${allegati.max-number}")
    private int maxAllegatiPerPratica;

    @Autowired
    public AllegatiService(SgPraticheAllegatiRepository sgPraticheAllegatiRepository, SgPraticheRepository sgPraticheRepository, GestoreFileClient gestoreFileClient, SigeproSecurityService sigeproSecurityService) {
        this.sgPraticheAllegatiRepository = sgPraticheAllegatiRepository;
        this.sgPraticheRepository = sgPraticheRepository;
        this.gestoreFileClient = gestoreFileClient;
        this.sigeproSecurityService = sigeproSecurityService;
    }

    /**
     * Metodo per inserire un allegato all'interno del service esterno "oggetti" e all'interno del DB
     *
     * @param alias       the alias
     * @param software    the software
     * @param fileName    nome dell'allegato
     * @param file        binaryData dell'allegato
     * @param uuidPratica uuid della pratica di riferimento
     * @return id ottenuto dalla response del metodo OggettiInsert da parte del servizio "oggetti"
     */
    @Transactional
    public String saveAllegato(String alias, String software, String fileName, MultipartFile file, String uuidPratica) throws Exception {
        log.info("Inizio metodo saveAllegato");
        String token = sigeproSecurityService.getToken(alias, software, uuidPratica);

        SgPratiche pratica = sgPraticheRepository.findByUuid(uuidPratica)
                .orElseThrow(() -> new IllegalArgumentException("Pratica non trovata con uuid: " + uuidPratica));

        // Solo le pratiche in compilazione possono essere modificate, allegati compresi
        if (pratica.getStato() != Stato.IN_COMPILAZIONE) {
            throw new IllegalStateException("La pratica con uuid: `" + uuidPratica + "` non può essere modificata");
        }

        if (contaAllegatiPerPratica(uuidPratica) >= maxAllegatiPerPratica) {
            throw new Exception("Numero massimo di allegati raggiunto per questa pratica");
        }

        String mimeType = file.getContentType();
        byte[] content = file.getBytes();
        DataHandler dataHandler = new DataHandler(new ByteArrayDataSource(content, mimeType));

        OggettiInsertResponse response = gestoreFileClient.oggettiInsert(token, fileName, mimeType, dataHandler);

        SgPraticheAllegati allegato = new SgPraticheAllegati();
        LocalDateTime now = LocalDateTime.now();
        allegato.setPratica(pratica);
        allegato.setNomeFile(fileName);
        allegato.setDtInsert(now);
        allegato.setRiferimentoEsternoUuid(response.getId().toString());
        allegato.setUuid(UUID.randomUUID().toString());

        try {
            sgPraticheAllegatiRepository.save(allegato);
        } catch (Exception e) {
            log.error("Errore nel salvataggio dell'allegato a DB! ", e);
        }
        log.info("Fine metodo saveAllegato");
        return allegato.getUuid();
    }

    /**
     * Metodo per recuperare un allegato all'interno del service esterno "oggetti" e all'interno del DB
     *
     * @param alias        the alias
     * @param software     the software
     * @param uuidPratica  uuid della pratica di riferimento
     * @param uuidAllegato uuid dell'allegato
     * @return stringa contenente il file in base64 ottenuto dal metodo OggettiFind da parte del servizio "oggetti"
     */
    @Transactional
    public String getAllegato(String alias, String software, String uuidPratica, String uuidAllegato) throws Exception {
        log.info("Inizio metodo getAllegato");
        String token = sigeproSecurityService.getToken(alias, software, uuidPratica);

        if (!sgPraticheRepository.existsByUuid(uuidPratica))
            throw new IllegalArgumentException("Pratica non trovata con uuid: " + uuidPratica);

        SgPraticheAllegati allegato = sgPraticheAllegatiRepository.findByUuid(uuidAllegato).
                orElseThrow(() -> new IllegalArgumentException("Allegato non trovato con uuid: " + uuidAllegato));

        String response = gestoreFileClient.oggettiFind(token, new BigInteger(allegato.getRiferimentoEsternoUuid()));

        log.info("Fine metodo getAllegato");
        return response;
    }

    /**
     * Metodo per cancellare un allegato all'interno del service esterno "oggetti" e all'interno del DB
     *
     * @param alias        the alias
     * @param software     the software
     * @param uuidPratica  uuid della pratica di riferimento
     * @param uuidAllegato uuid dell'allegato
     */
    @Transactional
    public void deleteAllegato(String alias, String software, String uuidPratica, String uuidAllegato) throws Exception {
        log.info("Inizio metodo deleteAllegato");
        String token = sigeproSecurityService.getToken(alias, software, uuidPratica);

        SgPratiche pratica = sgPraticheRepository.findByUuid(uuidPratica).
                orElseThrow(() -> new IllegalArgumentException("Pratica non trovata con uuid: " + uuidPratica));

        // Solo le pratiche in compilazione possono essere modificate, allegati compresi
        if (pratica.getStato() != Stato.IN_COMPILAZIONE) {
            throw new IllegalStateException("La pratica con uuid: `" + uuidPratica + "` non può essere modificata");
        }

        SgPraticheAllegati allegato = sgPraticheAllegatiRepository.findByUuid(uuidAllegato).
                orElseThrow(() -> new IllegalArgumentException("Allegato non trovato con uuid: " + uuidAllegato));

        OggettiDeleteResponse response = gestoreFileClient.oggettiDelete(token, new BigInteger(allegato.getRiferimentoEsternoUuid()));

        sgPraticheAllegatiRepository.deleteByUuid(uuidAllegato);
        log.info("Fine metodo deleteAllegato");
    }

    private int contaAllegatiPerPratica(String uuidPratica) {
        return sgPraticheAllegatiRepository.countByPraticaUuid(uuidPratica);
    }

    public Optional<SgPraticheAllegati> findByUuid(String idAllegato) {
        return sgPraticheAllegatiRepository.findByUuid(idAllegato);
    }

}
