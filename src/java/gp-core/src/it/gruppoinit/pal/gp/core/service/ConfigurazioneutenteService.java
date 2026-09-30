package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

public interface ConfigurazioneutenteService extends BaseService<Configurazioneutente, ConfigurazioneutenteId> {

    public enum PARAMETRI {
	PROFILO_FIRMA_REMOTA
    }

    public Map<String, String> mapByResponsabile(Responsabili responsabile);

    public List<Configurazioneutente> findByResponsabile(Responsabili responsabile);

    /**
     * 
     * Il metodo aggiorna o inserisce la configurazione utente:
     * 
     * <ol>
     * <li><b>inserimento</b> : se l'oggetto configurazione utente è null allora ne crea uno con i valori passati e lo
     * inserisce</li>
     * <li><b>aggiornamento</b>: se l'oggetto configurazione utente è diverso da null allora lo modifica con il dato
     * valore passato</li> passato
     * </ol>
     * 
     * @param configurazioneutente
     * @param valore
     * 
     */
    public void insertOrUpdate(Configurazioneutente configurazioneutente, String parametro, String valore, Responsabili responsabile);

    public void insertOrUpdate(String parametro, String valore, Integer codiceresponsabile);

    public String leggiParametroConfigurazioneUtente(String nomeParametro, String valorePredefinito);
}
