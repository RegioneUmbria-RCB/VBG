package it.gruppoinit.pal.gp.core.service;

import java.math.BigDecimal;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

public interface RegistrazionimercatoService extends BaseService<Registrazioni, PkId> {
    /**
     * crea le registrazioni contabili per un mercato data una lista di giorni
     * 
     * @param mercato
     *            il mercato per il quale creare le registrazioni
     * @param registrazionimercato
     *            il bean con le impostazioni per la creazione delle registrazioni
     * @return la lista di registrazioni create
     */
    //  public void createRegistrazioni(Mercati mercato, Registrazionimercato registrazionimercato);

    /**
     * crea le registrazioni contabili per un mercato ed un anno di riferimento. È il tipo di calcolo specifico per le
     * registrazioni annuali indipendentemente dal tipo di rateizzazione applicata. Ma prende il costo del posteggio e
     * fa le registrazioni per 12 mesi.
     * 
     * @param mercati
     * @param registrazionimercato
     */
    // public void createRegistrazioniAnnuali(Mercati mercati, Registrazionimercato registrazionimercato);
    public Registrazioni registraCostoPosteggioDaWizard(Mercati mercato, MercatiUso uso, MercatiD posteggio, Anagrafe a, RegistrazioniCausali rc,
	    List<ChiaveValoreBean<Integer, BigDecimal>> meseImportis, Integer anno, Conti conto, TipiScadenza tipiScadenza);
}
