package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazione;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.OrariEContattiBean;

import java.util.List;

public interface ConfigurazioneService extends BaseService<Configurazione, ConfigurazioneId> {

    /**
     * metodo per l'inserimento senza validazione
     * 
     * @param entity
     */
    public void insertNoValidate(Configurazione entity);

    /**
     * Torna i codici delle amministrazioni configurate come di sistema es:
     * <ul>
     * <li>
     * <b>codammsportellounico</b></li>
     * <li>
     * <b>codicetutteamministrazioni</b></li>
     * <li>
     * <b>codicelastessaamministrazione</b></li>
     * <ul>
     * 
     * 
     * @return
     */
    public Integer[] getCodiciAmministrazioniSistema();

    /**
     * Il metodo va ad inserire l'oltre che l'oggetto configurazione anche l'oggett comuni associati software collegato
     * all'oggetto configurazione se si tratta di un'associazione di comuni
     * 
     * @param configurazione
     * @param comuniassociatisoftware
     */
    public void insertConfigurazionedatigeneraliAndComuniassociatesoftware(Configurazione configurazione,
	    Comuniassociatisoftware comuniassociatisoftware, Comuniassociati comuniassociati);

    /**
     * Il metodo inserisce i campi di configurazione che gestiscono la configurazione dei dati generali. La validazione
     * è applicata solo sui campi che compongono e i dati generali
     * 
     * @param entity
     */
    public void insertDatigenerali(Configurazione entity);

    /**
     * Fa la insert dei loghi del comune e della regione
     * 
     * @param configurazione
     */
    public void insertLoghi(Configurazione entity);

    /**
     * Fa la update dei loghi del comune e della regione
     * 
     * @param configurazione
     */
    public void updateLoghi(Configurazione entity);

    /**
     * 
     * <pre>
     * Fa l'insert o l'update dei campi 
     * 
     * mailtipoAmministrazioneEndo,mailtipoMovimentoNegativo,mailtipoMovimentoRichiedente,mailtipoMovimentoAmministrazione della tabella configurazione
     * e
     * mailtipoByFkIstanza,mailtipoByFkMovimento della tabella ProtocolloConfigurazione;
     * @param configurazione
     * @param protocolloConfigurazione
     * 
     * </pre>
     */
    public void insertOrUpdateConfigurazioneMailAntTestiTipo(Configurazione configurazione, ProtocolloConfigurazione protocolloConfigurazione);

    /**
     * Ricerca se esiste un record in configurazione collegato con un responsabile
     * 
     * @param codice
     * @return
     */
    public List<Configurazione> findbyResponsabile(Integer codiceResponsabile);

    public OrariEContattiBean getOrariEContatti();
}
