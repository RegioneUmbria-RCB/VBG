package it.alveo.segnalazioniproxy.clients;

import it.alveo.segnalazioniproxy.dao.*;
import it.alveo.segnalazioniproxy.entities.*;
import it.alveo.segnalazioniproxy.servicies.SigeproSecurityService;
import it.alveo.segnalazioniproxy.utils.Utils;
import it.alveo.stc.*;
import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class StcClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(StcClient.class);

    private final SgConfigurazioniRepository configurazioniRepository;
    private final SgConfigurazioniEntiRepository configurazioniEntiRepository;
    private final SgPraticheRepository praticheRepository;
    private final SgPraticheAllegatiRepository sgPraticheAllegatiRepository;
    private final GestoreFileClient gestoreFileClient;
    private final SigeproSecurityService sigeproSecurityService;

    // Persona fisica sempre impostata di default
    private final PersonaFisicaType PERSONA_FISICA = new PersonaFisicaType();
    // Parametro fisso
    private final String TIPO_DOCUMENTO = "ALTRO";

    @Value("${persona-fisica.codice-fiscale}")
    private String codiceFiscale;
    @Value("${persona-fisica.nome}")
    private String nome;
    @Value("${persona-fisica.cognome}")
    private String cognome;
    @Value("${persona-fisica.sesso}")
    private String sesso;

    private final ApplicationContext applicationContext;

    @Autowired
    public StcClient(ApplicationContext applicationContext, SgConfigurazioniRepository configurazioniRepository, SgConfigurazioniEntiRepository configurazioniEntiRepository, SgPraticheRepository sgPraticheRepository, SgPraticheAllegatiRepository sgPraticheAllegatiRepository, GestoreFileClient gestoreFileClient, SgDizionarioRepository sgDizionarioRepository, SigeproSecurityService sigeproSecurityService) {
        this.applicationContext = applicationContext;

        this.configurazioniRepository = configurazioniRepository;
        this.configurazioniEntiRepository = configurazioniEntiRepository;
        this.praticheRepository = sgPraticheRepository;
        this.sgPraticheAllegatiRepository = sgPraticheAllegatiRepository;
        this.gestoreFileClient = gestoreFileClient;
        this.sigeproSecurityService = sigeproSecurityService;
    }

    @PostConstruct
    public void init() {
        PERSONA_FISICA.setCodiceFiscale(codiceFiscale);
        PERSONA_FISICA.setNome(nome);
        PERSONA_FISICA.setCognome(cognome);
        PERSONA_FISICA.setSesso(sesso);
    }

    /**
     * Metodo per recuperare il token da STC per permettere l'utilizzo dei vari services (inserimentoPratica, richiestaPratica)
     *
     * @param alias    the alias
     * @param software the software
     * @return il token per effettuare il login su STC
     */
    public LoginResponse login(String alias, String software) {

        SgConfigurazioni configurazione = configurazioniRepository.findByAliasAndSoftware(alias, software)
                .orElseThrow(() -> new IllegalArgumentException("Configurazione non trovata con alias: `" + alias + "` e software: `" + software + "`"));

        String stcUrl = configurazione.getStcWsUrl();
        String stcUsername = configurazione.getStcUsername();
        String stcPassword = configurazione.getStcPassword();

        LoginRequest request = new LoginRequest();
        request.setUsername(stcUsername);
        request.setPassword(stcPassword);
        log.info("Requesting login for username '{}'", stcUsername);
        LoginResponse response = (LoginResponse) Utils.getStcWebServiceTemplate(applicationContext, stcUrl).marshalSendAndReceive(stcUrl, request,
                new SoapActionCallback("http://sigepro.init.it/rte/Login"));
        log.info("Requesting login for username '{}' return token '{}'", stcUsername, response.getToken());
        return response;
    }

    /**
     * Metodo per creare e inviare la request verso STC del metodo InserimentoPratica
     *
     * @param token    token
     * @param alias    alias
     * @param software software
     * @param uuid     uuid della pratica
     * @return la response al servizio ottenuta dal BO passata tramite STC (dati dell'inserimento a BO)
     */
    public InserimentoPraticaResponse inserimentoPratica(String token, String alias, String software, String uuid) throws Exception {
        InserimentoPraticaRequest praticaDaInserire = new InserimentoPraticaRequest();
        praticaDaInserire.setToken(token);

        //recupero della configurazione a DB
        SgConfigurazioni sportelloMittenteConfig = configurazioniRepository.findByAliasAndSoftware(alias, software)
                .orElseThrow(() -> new IllegalArgumentException("Configurazione non trovata con alias: `" + alias + "` e software: `" + software + "`"));

        //recupero della segnalazione a DB
        SgPratiche praticaConfig = praticheRepository.findByUuid(uuid).
                orElseThrow(() -> new IllegalArgumentException("Pratica non trova con uuid: " + uuid));

        //recupero della configurazione dell'ente a DB
        SgConfigurazioniEnti sportelloDestinatarioConfig = configurazioniEntiRepository.findByCodiceComune(praticaConfig.getCodiceComune())
                .orElseThrow(() -> new IllegalArgumentException("Configurazione ente non trovata con alias: `" + alias + "` e software: `" + software + "`"));

        String stcUrl = sportelloMittenteConfig.getStcWsUrl();

        //creazione sezione sportelloMittente
        SportelloType sportelloMittente = new SportelloType();
        sportelloMittente.setIdNodo(sportelloMittenteConfig.getMittIdNodo());
        sportelloMittente.setIdEnte(sportelloMittenteConfig.getMittIdEnte());
        sportelloMittente.setIdSportello(sportelloMittenteConfig.getMittIdSportello());

        //creazione sezione sportelloDestinatario
        SportelloType sportelloDestinatario = new SportelloType();
        sportelloDestinatario.setIdNodo(sportelloDestinatarioConfig.getDestIdNodo());
        sportelloDestinatario.setIdEnte(sportelloDestinatarioConfig.getDestIdEnte());
        sportelloDestinatario.setIdSportello(sportelloDestinatarioConfig.getDestIdSportello());

        //creazione sezione dettaglioPratica
        ComuneType comune = new ComuneType();
        comune.setCodiceCatastale(praticaConfig.getCodiceComune());

        DettaglioPraticaType dettaglioPratica = new DettaglioPraticaType();
        dettaglioPratica.setIdPratica(praticaConfig.getUuid());
        dettaglioPratica.setNumeroPratica(praticaConfig.getUuid());

        LocalDateTime currentUTCTime = LocalDateTime.now();
        String dataPraticaString = currentUTCTime.toString();
        XMLGregorianCalendar dataPraticaFormat =
                DatatypeFactory.newInstance().newXMLGregorianCalendar(dataPraticaString);
        
        dettaglioPratica.setDataPratica(dataPraticaFormat);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); //set format  
        dettaglioPratica.setOraDataPratica(currentUTCTime.format(formatter));
        
        dettaglioPratica.setCodiceComune(comune);

        //creazione sezione richiedente (all'interno di dettaglioPratica)
        RichiedenteType richiedente = new RichiedenteType();
        richiedente.setAnagrafica(PERSONA_FISICA);
        dettaglioPratica.setRichiedente(richiedente);

        //creazione sezione oggetto (all'interno di dettaglioPratica)
        dettaglioPratica.setOggetto(praticaConfig.getOggetto());

        //creazione sezione documenti (all'interno di dettaglioPratica)
        List<SgPraticheAllegati> praticheAllegati = sgPraticheAllegatiRepository.findAllByPraticaUuid(uuid);
        List<DocumentiType> documentiList = new ArrayList<>();

        //recupero del token per accedere al servizio "oggetti"
        String tokenSecurity = sigeproSecurityService.getToken(alias, software, uuid);

        //ciclo tutti gli allegati e li inserisco dentro la sezione documenti (MAX 3)
        for (SgPraticheAllegati praticaAllegati : praticheAllegati) {
            DocumentiType documenti = new DocumentiType();
            AllegatiType allegati = new AllegatiType();
            AllegatoBinarioType allegatoBinario = new AllegatoBinarioType();

            documenti.setId(praticaAllegati.getRiferimentoEsternoUuid());
            documenti.setTipoDocumento(TIPO_DOCUMENTO);
            documenti.setDocumento(praticaAllegati.getNomeFile());

            String fileBase64 = gestoreFileClient.oggettiFind(tokenSecurity,
                    new BigInteger(praticaAllegati.getRiferimentoEsternoUuid()));

            allegati.setId(praticaAllegati.getRiferimentoEsternoUuid());
            allegati.setAllegato(praticaAllegati.getNomeFile());
            String mimeType = Utils.getMimeType(fileBase64);
            allegatoBinario.setMimeType(mimeType);
            allegatoBinario.setBinaryData(Utils.convertToDataHandler(fileBase64, mimeType));
            allegatoBinario.setFileName(praticaAllegati.getNomeFile());

            allegati.setFile(allegatoBinario);
            documenti.setAllegati(allegati);
            documentiList.add(documenti);
        }

        for (DocumentiType documenti : documentiList) {
            dettaglioPratica.getDocumenti().add(documenti);
        }

        SgDizionario sgDizionario = praticaConfig.getDizionario();

        //creazione sezione procedimenti
        ProcedimentoType procedimento = new ProcedimentoType();
        procedimento.setCodice(sgDizionario.getId().toString());
        procedimento.setDescrizione(sgDizionario.getDescrizione());
        procedimento.isPrincipale(); //PARAMETRO FISSO
        dettaglioPratica.getProcedimenti().add(procedimento);

        dettaglioPratica.setAnnotazioni(praticaConfig.getTesto());

        praticaDaInserire.setSportelloMittente(sportelloMittente);
        praticaDaInserire.setSportelloDestinatario(sportelloDestinatario);
        praticaDaInserire.setDettaglioPratica(dettaglioPratica);

        //invio della request verso STC
        log.info("inserimentoPratica return {}", praticaDaInserire.getDettaglioPratica().getIdPratica(), praticaDaInserire.getToken());
        InserimentoPraticaResponse response = (InserimentoPraticaResponse) Utils.getStcWebServiceTemplate(applicationContext, stcUrl).marshalSendAndReceive(
                stcUrl, praticaDaInserire,
                new SoapActionCallback("http://sigepro.init.it/rte/InserimentoPraticaRequest")
        );
        log.info("inserimentoPratica return {}", response);


        return response;
    }

    /**
     * Metodo per creare e inviare la request verso STC del metodo RichiestaPratica
     *
     * @param token    token
     * @param alias    alias
     * @param software software
     * @param uuid     uuid della pratica
     * @return la response al servizio ottenuta dal BO passata tramite STC (recupero della visura della segnalazione)
     */
    public RichiestaPraticaResponse richiestaPratica(String token, String alias, String software, String uuid) {
        RichiestaPraticaRequest praticaDaInserire = new RichiestaPraticaRequest();
        praticaDaInserire.setToken(token);

        //recupero della configurazione a DB
        SgConfigurazioni sportelloMittenteConfig = configurazioniRepository.findByAliasAndSoftware(alias, software)
                .orElseThrow(() -> new IllegalArgumentException("Configurazione non trovata con alias: `" + alias + "` e software: `" + software + "`"));

        //recupero della segnalazione a DB
        SgPratiche praticaConfig = praticheRepository.findByUuid(uuid).
                orElseThrow(() -> new IllegalArgumentException("Pratica non trova con uuid: " + uuid));

        //recupero della configurazione dell'ente a DB
        SgConfigurazioniEnti sportelloDestinatarioConfig = configurazioniEntiRepository.findByCodiceComune(praticaConfig.getCodiceComune())
                .orElseThrow(() -> new IllegalArgumentException("Configurazione ente non trovata con alias: `" + alias + "` e software: `" + software + "`"));

        //creazione sezione sportelloMittente
        SportelloType sportelloMittente = new SportelloType();
        sportelloMittente.setIdNodo(sportelloMittenteConfig.getMittIdNodo());
        sportelloMittente.setIdEnte(sportelloMittenteConfig.getMittIdEnte());
        sportelloMittente.setIdSportello(sportelloMittenteConfig.getMittIdSportello());

        //creazione sezione sportelloDestinatario
        SportelloType sportelloDestinatario = new SportelloType();
        sportelloDestinatario.setIdNodo(sportelloDestinatarioConfig.getDestIdNodo());
        sportelloDestinatario.setIdEnte(sportelloDestinatarioConfig.getDestIdEnte());
        sportelloDestinatario.setIdSportello(sportelloDestinatarioConfig.getDestIdSportello());

        //creazione sezione RifPratica
        RiferimentiPraticaType riferimentoPratica = new RiferimentiPraticaType();
        riferimentoPratica.setIdPratica(praticaConfig.getIdPraticaDestinataria());
        praticaDaInserire.setSportelloMittente(sportelloMittente);
        praticaDaInserire.setSportelloDestinatario(sportelloDestinatario);
        praticaDaInserire.setRifPratica(riferimentoPratica);

        String stcUrl = sportelloMittenteConfig.getStcWsUrl();

        //invio della request verso STC
        log.info("richiestaPratica return {}", praticaDaInserire.getRifPratica().getIdPratica(), praticaDaInserire.getToken());
        RichiestaPraticaResponse response = (RichiestaPraticaResponse) Utils.getStcWebServiceTemplate(applicationContext, stcUrl).marshalSendAndReceive(
                stcUrl, praticaDaInserire,
                new SoapActionCallback("http://sigepro.init.it/rte/RichiestaPraticaRequest")
        );
        log.info("richiestaPratica return {}", response);

        return response;
    }
}
