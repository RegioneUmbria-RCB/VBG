package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.LettureContatoriCommand;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatidLetture;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

import java.util.Date;
import java.util.List;

public interface MercatidLettureService extends BaseService<MercatidLetture, PkId> {

    /**
     * Metodo per la ricerca dei MercatidLetture
     * 
     * @param mercatidLetture
     * @return
     */
    public List<MercatidLetture> findByFilter(MercatidLetture mercatidLetture);

    /**
     * Metodo per ricercare le date letture per manifestazione e uso. Le date sono raggruppate.
     * 
     * @param mercatiUso
     *            : in mercatiUso è settato il mercato.
     * @return
     */
    public List<Date> findDataLettura(MercatiUso mercatiUso);

    /**
     * Metodo per inserire le nuove letture.
     * 
     * @param list
     * @return ritorna la lista di mercatidletture non inserite
     */
    public List<MercatidLetture> insertNuoveLetture(List<MercatidLetture> list);

    /**
     * Metodo per aggiornare i valori delle letture
     * 
     * @param list
     */
    public void updateNuoveLetture(List<MercatidLetture> list);

    /**
     * Metodo per aggiornare le Letture dei contatori importi su file excel
     * 
     * @param list
     */
    public void updateImportLetture(List<MercatidLetture> list);

    /**
     * Metodo per determinare le letture che hanno l'importo maggiore di zero
     * 
     * @return
     */
    public List<MercatidLetture> findByLettureConImporto(MercatidLetture mercatidLetture);

    /**
     * Metodo per determinare l'ultima lettura finale per un determinato posteggio
     * 
     * @return
     */
    public MercatidLetture findUltimaLetturaFinaleByPosteggio(MercatiD mercatiD);

    /**
     * Metodo per inserire le registrazioni per la gestione delle letture.
     * 
     * @param command
     * @param codiceMercato
     * @param uso
     * @param utenteLoggato
     */
    public void insertRegistrazioneGestioneLetture(LettureContatoriCommand command, Integer codiceMercato, MercatiUso uso, Responsabili utenteLoggato);

    /**
     * validata i campi della registrazione per la gestione delle letture
     * 
     * @param command
     */
    public void validateRegistrazioniMercatidLetture(LettureContatoriCommand command);
}
