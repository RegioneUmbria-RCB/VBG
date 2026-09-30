package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeDaChiudereHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ReportIstanzaChiusaHelper;

import java.util.List;

public interface IstanzeManager {

    public IstanzeService getIstanzeService();

    // public void insert(String token, Istanze entity, Integer tipoInserimento);
    /**
     * La funzionalità a partire da una istanza di origine esegue n° copie delle informazioni in nuove istanze con gli
     * interventi identificati dai codici intervento indicati. <br />
     * Le informazioni che vengono copiate sono:
     * <ul>
     * <li>Documentiistanza</li>
     * <li>Istanzearee</li>
     * <li>Istanzeattivita</li>
     * <li>Istanzedyn2dati</li>
     * <li>Istanzedyn2modellit</li>
     * 
     * <li>Istanzerichiedenti</li>
     * <li>Istanzeruoli</li>
     * <li>Istanzestradario
     * <ul>
     * <li>Istanzemappali</li>
     * </ul>
     * </li>
     * </ul>
     * Verranno ricalcolati:
     * <ul>
     * <li>Numeroistanza</li>
     * <li>Procedura</li>
     * <li>Responsabile del procedimento</li>
     * <li>Movimento di avvio</li>
     * <li>Stato dell'istanza</li>
     * </ul>
     * Le istanze verranno inoltre collegate in istanzecollegate
     * 
     * @param istanza
     *            l'istanza di origine
     * @param codiciIntervento
     *            la lista dei codici intervento individuati
     * @param isGestioneAttivita
     *            : se true verrà getsista la replica considerando l'eventuale attività collegata all' istanza sorgente
     *            (Il sistema in base alla nuova istanza creata ricalcolerà :Attiva,Operante,Ultima istanza)
     * @return
     */
    public List<Istanze> creaRepliche(Istanze istanza, List<Integer> codiciIntervento, boolean isGestioneAttivita);

    /**
     * La funzione elimina le istanze di un determinato software ed intervento (Opzionale). Non vengono sollevate
     * eccezioni ma vengono scritti su FlashMessages la lista delle istanze cancellate (info) e quelle istanze per le
     * quali non è avvenuta la cancellazione (warning)
     * 
     * @param software
     * @param List
     *            &lt;codiceIntervento&gt; (Può essere nullo)
     */
    public void deleteIstanzeBySoftwareAndIntervento(String software, List<Integer> codiceIntervento);

    public List<ReportIstanzaChiusaHelper> updateProcessaIstanzedaChiudere(String idcomunealias, String software, boolean setORMHelper,
	    boolean chiudiLeInterrotteSospese);

    public List<IstanzeDaChiudereHelper> udpateProcessaValidazioneStradario(boolean settaANulliNonValidi);

    /***
     * Aggiorna i contatori per l'alias
     * 
     * esegue le seguenti query
     * 
     * <pre>
     * update configurazione set progressivoistanze='1/2018' || substr(progressivoistanze,instr(progressivoistanze,'/2017', 1,1)+5) where progressivoistanze is not null and instr(progressivoistanze,'/2017', 1,1)>0 ;
     * 
     * update alberoproc set progressivoistanze='1/2018' || substr(progressivoistanze,instr(progressivoistanze,'/2017', 1,1)+5) where progressivoistanze is not null and instr(progressivoistanze,'/2017', 1,1)>0 ;
     * 
     * UPDATE TIPOLOGIAREGISTRI SET tr_progressivo='1/2018' || SUBSTR(tr_progressivo,instr(tr_progressivo,'/2017', 1,1)+5) WHERE tr_progressivo IS NOT NULL AND instr(tr_progressivo,'/2017', 1,1)>0 ;
     * </pre>
     * 
     * @param idcomunealias
     * @param software
     */
    public void updateContatori(String idcomunealias, String software);
}
