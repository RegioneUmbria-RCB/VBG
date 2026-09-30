package it.gruppoinit.service;

import it.gruppoinit.domain.helper.VerticalizzazioniHelper;

public interface VerticalizzazioniHelperService {

    public VerticalizzazioniHelper getRegole(String software, String token);

    public VerticalizzazioniHelper getRegole(String software, String codicecomune, String token);

    public String getRegola(String nomeParametro, String software, String codicecomune, String token);
}
