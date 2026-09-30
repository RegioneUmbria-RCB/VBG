package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

public interface AnagrafeManager {

    public void updateAllineaPersoneGiuridiche();

    public void updateAllineaPersoneFisiche();

    public void updateAllineaTuttaAnagrafe();

    public void fermaAllineamentoAnagrafiche();

    public ChiaveValoreBean<String, String> reportAllineamento();

    public boolean elaborazioneInCorso();
}
