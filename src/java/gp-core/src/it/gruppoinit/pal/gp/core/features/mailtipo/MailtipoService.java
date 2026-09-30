package it.gruppoinit.pal.gp.core.features.mailtipo;

import java.util.List;

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.dao.helper.ContestiMailTipoEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.init.sigepro.rte.types.DettaglioPraticaType;

public interface MailtipoService extends BaseService<Mailtipo, PkId> {

    /**
     * @see MailtipoDAO#findAll(Integer, Integer)
     */
    public List<Mailtipo> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see MailtipoDAO#findByFilter(Mailtipo filter)
     */
    public List<Mailtipo> findByFilter(Mailtipo filter);

    /**
     * Metodo utilizzato per effettuare il replace dell'oggetto e del corpo dell'oggetto Mailtipo secondo i valori
     * riportati nella tabella:
     * 
     * <table border="1" align="center">
     * <tr class="intestazionetabella">
     * <td colspan="8">DATI DEL RICHIEDENTE</td>
     * </tr>
     * <tr>
     * <td>[1]</td>
     * <td>Richiedente</td>
     * <td>[2]</td>
     * <td>Indirizzo</td>
     * <td>[3]</td>
     * <td>Città</td>
     * <td>[4]</td>
     * <td>Cap</td>
     * </tr>
     * <tr>
     * <td>[5]</td>
     * <td colspan="7">Provincia</td>
     * </tr>
     * <tr class="intestazionetabella">
     * <td colspan="8">DATI DELL'ISTANZA</td>
     * </tr>
     * <tr>
     * <td>[20]</td>
     * <td>Codice Istanza</td>
     * <td>[6]</td>
     * <td>Data presentazione Istanza</td>
     * <td>[7]</td>
     * <td>N.ro Protocollo Istanza</td>
     * <td>[8]</td>
     * <td>Data protocollo</td>
     * </tr>
     * <tr>
     * <td>[13]</td>
     * <td>Descrizione dei lavori</td>
     * <td>[24]</td>
     * <td colspan="5">Password</td>
     * </tr>
     * <tr>
     * <td>[17]</td>
     * <td>Operatore sportello</td>
     * <td>[28]</td>
     * <td>Tecnico</td>
     * <td>[18]</td>
     * <td>Respons. Procedimento</td>
     * <td>[29]</td>
     * <td>Cointestatari</td>
     * </tr>
     * <tr>
     * <td>[12]</td>
     * <td>Lotto</td>
     * <td>[16]</td>
     * <td>Sub</td>
     * <td>[15]</td>
     * <td>Particella</td>
     * <td>[14]</td>
     * <td>Foglio</td>
     * </tr>
     * <tr>
     * <td>[9]</td>
     * <td>Tipo intervento</td>
     * <td>[10]</td>
     * <td>Tipo procedura</td>
     * <td>[21]</td>
     * <td>Tipo Impianto</td>
     * <td>[11]</td>
     * <td>Area industriale</td>
     * </tr>
     * <tr>
     * <td>[26]</td>
     * <td>Variante P.R.G.</td>
     * <td>[25]</td>
     * <td>Valutaz. Impatto Ambientale</td>
     * <td>[19]</td>
     * <td colspan="3">Lista Endo Attivati</td>
     * </tr>
     * <tr>
     * <td>[30]</td>
     * <td>Settore Istat</td>
     * <td>[31]</td>
     * <td>Attività Istat</td>
     * <td>[22]</td>
     * <td>Civico</td>
     * <td>[23]</td>
     * <td>Localizzazione</td>
     * </tr>
     * <tr>
     * <td>[40]</td>
     * <td>Cod. Istanza People</td>
     * <td>&nbsp;</td>
     * <td>&nbsp;</td>
     * <td>&nbsp;</td>
     * <td>&nbsp;</td>
     * <td>&nbsp;</td>
     * <td>&nbsp;</td>
     * </tr>
     * <tr class="intestazionetabella">
     * <td colspan="8">DATI DEL MOVIMENTO</td>
     * </tr>
     * <tr>
     * <td>[32]</td>
     * <td>Tipo movimento</td>
     * <td>[33]</td>
     * <td>Endoprocedimento</td>
     * <td>[34]</td>
     * <td>Nr e Data protocollo</td>
     * <td>[35]</td>
     * <td>Esito</td>
     * </tr>
     * <tr>
     * <td>[36]</td>
     * <td>Parere</td>
     * <td>[37]</td>
     * <td colspan="5">Data</td>
     * </tr>
     * <tr class="intestazionetabella">
     * <td colspan="8">DATI DELL'AUTORIZZAZIONE</td>
     * </tr>
     * <tr>
     * <td>[38(<b>CodReg</b>)]</td>
     * <td>Numero autorizzazione</td>
     * <td>[39(<b>CodReg</b>)]</td>
     * <td colspan="5">Data autorizzazione</td>
     * </tr>
     * <tr class="intestazionetabella">
     * <td colspan="8">SORTEGGI</td>
     * </tr>
     * <tr>
     * <td>[41]</td>
     * <td>Data Sorteggio</td>
     * <td>[42]</td>
     * <td colspan="5">Descrizione</td>
     * </tr>
     * <tr class="intestazionetabella">
     * <td colspan="8">ALTRI DATI</td>
     * </tr>
     * <tr>
     * <td>[27]</td>
     * <td colspan="7">Data Odierna</td>
     * </tr>
     * </table>
     * 
     * 
     * @param mailtipo
     * @param istanza
     *            è facoltativo. può essere null.
     * @param movimento
     *            è facoltativo. può essere null. Da un movimento viene ricavata l'istanza
     * @return restituisce l'oggetto mailtipo passato come parametro con l'oggetto e il corpo con le sostituzioni
     */
    public Mailtipo replaceOggettoCorpo(Mailtipo mailtipo, Istanze istanza, Movimenti movimento);

    public Mailtipo replaceOggettoCorpo(Mailtipo mailtipo, Istanze istanza, Movimenti movimento, Sorteggitestata sorteggio);

    /**
     * Metodo che restituisce la lista di mailtipo filtrate per il software TT e per il software corrente.
     * 
     * @return
     */
    public List<Mailtipo> findAllBySoftwareAndTT(ContestiMailTipoEnum contestiMailTipoEnum);

    /**
     * Torna "Protocollo dell'istanza numero "
     * 
     * @return
     */
    public String getOggettoProtocollazioneDefault();

    /**
     * Torna "Fascicolo dell'istanza numero "
     * 
     * @return
     */
    public String getOggettoFascicolazioneDefault();

    public Mailtipo eseguiSostituzioniFrontend(int codicemailtipo, DettaglioPraticaType dettaglioPratica);

    /**
     * 
     * @param codicemailtipo
     * @param anagrafe
     * @return
     */
    public Mailtipo eseguiSostituzioniAnagrafe(int codicemailtipo, Anagrafe anagrafe);

    /**
     * Popola un oggetto MailMessageType per invio di una email nella fase di esportazione
     * 
     * @param emailDestinatario
     * @param pathFile
     * @param mailtipo
     * @return
     */
    public MailMessageType populateMailMessageForExport(String emailDestinatario, String pathFile, Mailtipo mailtipo);

    /**
    * @param mailtipo
    * @param istanza
    *            è facoltativo. può essere null.
    * @param movimento
    *            è facoltativo. può essere null. Da un movimento viene ricavata l'istanza
    * @return restituisce l'oggetto mailtipo passato come parametro con l'oggettoProtocolloMail e il corpoProtocolloMail con le sostituzioni
    */
    Mailtipo replaceOggettoCorpoProtocollo(Mailtipo mailtipo, Istanze istanza, Movimenti movimento);
}
