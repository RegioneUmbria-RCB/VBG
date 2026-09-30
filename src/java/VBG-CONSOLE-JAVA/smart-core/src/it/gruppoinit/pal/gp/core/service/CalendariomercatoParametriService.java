package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.CalendariomercatoParametri;
import it.gruppoinit.pal.gp.core.domain.Giornisettimana;
import it.gruppoinit.pal.gp.core.domain.Giorno;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;

import java.util.Calendar;
import java.util.List;

import org.springframework.validation.BindingResult;

public interface CalendariomercatoParametriService {

    /**
     * service che ritorna la lista di uno o più giorni della settimana per un in determinato mercato nell'anno scelto e
     * per l'uso specificato
     * 
     * @param mercati
     *            il mercato sul quale cercare le giornate
     * @param anno
     *            l'anno
     * @param mercatiUso
     *            uso del mercato
     * @return
     */
    public CalendariomercatoParametri findGiorniMercatoInUnAnno(Mercati mercati, int anno, MercatiUso mercatiUso,
	    List<Giornisettimana> giorniSettimana);

    /**
     * Ritorna una lista di giorni festivi di un determinato anno
     * 
     * @param anno
     * @return
     */
    public List<Giorno> findGiornifestivi(int anno);

    /**
     * Trova se un giorno è nella lista
     * 
     * @param list
     * @param date
     * @return true-->rimuove e ristituisce true false-->restituisce false
     */
    public boolean isFindDayAndRemove(List<Giorno> list, Calendar date);

    public void setBindingResult(BindingResult result);

    public BindingResult getBindingResult();
}
