package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.NotificheAusl;
import it.gruppoinit.pal.gp.core.domain.NotificheAuslId;

import java.util.List;

public interface NotificheAuslService extends BaseService<NotificheAusl, NotificheAuslId> {

    /**
     * Metodo per inserire le notifiche importate da excel
     * 
     * @param list
     * @param updateDati
     * 
     */
    public void updateImportNotifiche(List<NotificheAusl> list, String updateDati);

    /**
     * Torna se ci sono record nella tabella NotificheAUSL
     * 
     * @return
     */
    public boolean existsRecords();
}
