package it.alveo.segnalazioniproxy.servicies;

import it.alveo.segnalazioniproxy.dao.SgPraticheRepository;
import it.alveo.segnalazioniproxy.entities.SgPratiche;
import it.alveo.segnalazioniproxy.enums.Stato;
import it.alveo.stc.InserimentoPraticaResponse;
import it.alveo.stc.RichiestaPraticaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StcService {
    private static final Logger log = LoggerFactory.getLogger(StcService.class);

    private final SgPraticheRepository sgPraticheRepository;


    @Autowired
    public StcService(SgPraticheRepository sgPraticheRepository) {
        this.sgPraticheRepository = sgPraticheRepository;
    }

    /**
     * Metodo per salvare a DB i dati ottenuti dalla response del metodo InserimentoPratica (STC)
     *
     * @param response    response del metodo InserimentoPratica
     * @param uuidPratica uuid della pratica di riferimento
     */
    @Transactional
    public void savePraticaFromSTC(InserimentoPraticaResponse response, String uuidPratica) {
        log.info("Inizio metodo savePraticaFromSTC");

        SgPratiche pratica = sgPraticheRepository.findByUuid(uuidPratica).
                orElseThrow(() -> new IllegalArgumentException("Pratica non trova con uuid: " + uuidPratica));

        if (response.getDettaglioPratica() != null) {
            pratica.setRiferimentoEnte(response.getDettaglioPratica().getNumeroPratica());
            pratica.setIdPraticaDestinataria(response.getDettaglioPratica().getIdPratica());
            pratica.setStato(Stato.RICEVUTA);

            try {
                sgPraticheRepository.save(pratica);
            } catch (Exception e) {
                log.error("Errore nell'inserimento a DB della pratica! ", e);
                throw new IllegalArgumentException("Errore nell'inserimento a DB della pratica! ", e);
            }
        } else if (response.getDettaglioErrore() != null) {
            log.error("C'è un errore nella pratica! Numero errore: `{}`, dettaglio: `{}`",
                    response.getDettaglioErrore().get(0).getNumeroErrore(),
                    response.getDettaglioErrore().get(0).getDescrizione());
            throw new IllegalArgumentException("C'è un errore nella pratica! Numero errore: `"
                    + response.getDettaglioErrore().get(0).getNumeroErrore() + "`, dettaglio: `"
                    + response.getDettaglioErrore().get(0).getDescrizione() + "`");

        } else {
            log.error("La response da STC era vuota");
            throw new IllegalArgumentException("La response da STC era vuota");
        }

        log.info("Fine metodo savePraticaFromSTC");
    }

    /**
     * Metodo per salvare a DB i dati ottenuti dalla response del metodo RichiestaPratica (STC)
     *
     * @param response    response del metodo RichiestaPratica
     * @param uuidPratica uuid della pratica di riferimento
     */
    @Transactional
    public void updatePraticaFromSTC(RichiestaPraticaResponse response, String uuidPratica) {
        log.info("Inizio metodo updatePraticaFromSTC");

        SgPratiche pratica = sgPraticheRepository.findByUuid(uuidPratica).
                orElseThrow(() -> new IllegalArgumentException("Pratica non trova con uuid: " + uuidPratica));

        if (response.getDettaglioPratica() != null) {
            pratica.setNumeroProtocollo(response.getDettaglioPratica().getDettaglioPratica().getNumeroProtocolloGenerale());
            pratica.setDataProtocollo(response.getDettaglioPratica().getDettaglioPratica().getDataProtocolloGenerale().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
            pratica.setDataCreazione(response.getDettaglioPratica().getDettaglioPratica().getDataPratica().toGregorianCalendar().toZonedDateTime().toLocalDateTime());
            pratica.setStatoAvanzamentoEnte(response.getDettaglioPratica().getStatoPratica().toString());
            pratica.setDataRicezione(response.getDettaglioPratica().getDettaglioPratica().getDataPratica().toGregorianCalendar().toZonedDateTime().toLocalDateTime());

            try {
                sgPraticheRepository.save(pratica);
            } catch (Exception e) {
                log.error("Errore nell'aggiornamento a DB della pratica! ", e);
                throw new IllegalArgumentException("Errore nell'aggiornamento a DB della pratica! ", e);
            }

        } else if (response.getDettaglioErrore() != null) {
            log.error("C'è un errore nella pratica! Numero errore: `{}`, dettaglio: `{}`",
                    response.getDettaglioErrore().get(0).getNumeroErrore(),
                    response.getDettaglioErrore().get(0).getDescrizione());
            throw new IllegalArgumentException("C'è un errore nella pratica! Numero errore: `"
                    + response.getDettaglioErrore().get(0).getNumeroErrore() + "`, dettaglio: `"
                    + response.getDettaglioErrore().get(0).getDescrizione() + "`");

        } else {
            log.error("La response da STC era vuota");
            throw new IllegalArgumentException("La response da STC era vuota");
        }

        log.info("Fine metodo updatePraticaFromSTC");

    }

}
