package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;

public interface ConfigurazioneDAO extends BaseDAO<Configurazione, ConfigurazioneId> {

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
     * Torna i codici delle amministrazioni configurate come di sistema tranne codammsportello unico:
     * <ul>
     * <li>
     * <b>codicetutteamministrazioni</b></li>
     * <li>
     * <b>codicelastessaamministrazione</b></li>
     * <ul>
     * 
     * 
     * @return
     */
    public Integer[] getCodiciTutteEStessaAmministrazioniSistema();
}
