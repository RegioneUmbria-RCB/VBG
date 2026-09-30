package it.gruppoinit.stc.service;

import it.gruppoinit.stc.domain.Configurazione;
import it.gruppoinit.stc.domain.Sicurezza;

public interface SicurezzaService extends BaseService<Sicurezza, java.lang.Integer> {
    
    public String getToken(Configurazione nodo);
    
    public boolean checkToken(String token);

}
