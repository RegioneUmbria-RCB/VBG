package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.IstanzeareeId;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.RicalcoloFilter;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.BaseService;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author francescop
 * @author gianpaolot
 */
public interface IstanzeareeService extends BaseService<Istanzearee, IstanzeareeId> {

    public List<Istanzearee> findByFilterTable(FilterTable filterTable);

    /**
     * Metodo per ricercare l'area primaria collegata ad una istanza
     * 
     * @param istanza
     * @return
     */
    public Istanzearee findByPrimarioIstanza(Istanze istanza);

    /**
     * Ricostruzione delle Zonizzazioni legate alle istanze<br/>
     * Questa operazione permette di aggiornare le Zonizzazioni di tutte le istanze selezionate del modulo software
     * selezionato che sono state inserite automaticamente in base alla configurazione che lega lo stradario alle zone.<br/>
     * Se il software attivo è TT allora aggiorna le istanze di tutti i software.
     * 
     * <pre>
     *     Per tutte le istanze
     *     Se software attivo<>”TT” allora solo le istanze di quel software
     *     	Per ogni istanza
     *     		CANCELLO ISTANZEAREE CON AUTOINS=1
     *     		TROVO TUTTE LE LOCALIZZAZIONI STRADARIO, CIVICO 	
     *     		PER OGNI LOCALIZZAZIONE 
     *     			TROVO L’AREA SULLA TABELLA AREEDETTAGLI FACENDO ATTENZIONE A PARI/DISPARI
     *     			VADO SU ISTANZEAREE CON QUELL’AREA PER VEDERE CHE NON ESISTA IL RECORD
     *     				SE NON ESISTE L’INSERISCO (LA PRIMA CON PRIMARIO=1) ALTRIMENTI VADO AVANTI
     * </pre>
     * 
     * @param filterTable
     *            contiene i parametri per filtrare le istanze
     */
    public void ricalcolaAree(Date dallaData, Date allaData);

    /**
     * Inserisce l'istanza areea passata, in più controllo se la nuva istanza passata è settata come primaria, in tal
     * caso aggiorna anche il flag "primario" della precedente istanza area primaria a <b>false</b>
     * 
     * @param istanzearee
     */
    public void insertAltraAreea(Istanzearee istanzearee);

    /**
     * Metodo per ricercare le aree collegate all'istanza
     * 
     * @param istanza
     * @return
     */
    public List<Istanzearee> findByIstanza(Istanze istanza);

    /**
     * Il metodo trasforma l'istanza area passata come primaria e mette a false il flag primario dell'area che lo era in
     * precedenza
     * 
     * @param istanzearee
     */
    public void updatePrimario(Istanzearee istanzearee);

    /**
     * <pre>
     * Metodo che fa l'update del istanza area primaria.
     * Logica:
     * 
     * 1- Controlla se dal form di inserimento dell'istanza è passata l'istanza area primaria
     * 		1.1 Si: Controllo se esiste già un primario
     * 			1.1.1 Si: Verifico che il primario passato sia diverso da quello trovato.
     * 				1.1.1.1 Si: Allora inserisco il nuovo primario o faccio l'update se già era collegato all'istanza
     * 					    ma non era primario	
     *                  	1.1.1.2 No : Significa che non ho modificato il primario sul form e non faccio null
     *          	1.1.2 No: Allora faccio direttamente un inserimento dul primario (se non esiste il primario , 
     *          		  non possono esisterne altri)
     *          1.2 No: Controllo se esiste già un primario
     *          	1.2.1 Si : Lo cancello ed eventualmente vedo se esistono altre istanze collegate per ed eventualmente
     *           		   prendo al prima per settarla come primario.
     * 1.2.2 No : Non fai niete (Non c'era il primario e non sto tentando di inserirlo)
     * 
     * <pre>
     * @param istanzearee
     */
    public void updateIstanzaareaPrimaria(Istanzearee istanzearee);

    /**
     * TROVO L’AREA SULLA TABELLA AREEDETTAGLI FACENDO ATTENZIONE A PARI/DISPARI VADO SU ISTANZEAREE CON QUELL’AREA PER
     * VEDERE CHE NON ESISTA IL RECORD SE NON ESISTE L’INSERISCO (LA PRIMA CON PRIMARIO=1) ALTRIMENTI VADO AVANTI
     * 
     * @param istanzestradario
     */
    public void insertFromIstanzeStradario(Istanzestradario istanzestradario);

    /**
     * ricalcola le aree inserite in automatico per l'istanza
     * 
     * @param istanza
     */
    public void ricalcolaPerIstanza(Integer codiceistanza);
    
    public List<Integer> findCodiciIstanzaPerRicalcolo(RicalcoloFilter filter);
    
}
