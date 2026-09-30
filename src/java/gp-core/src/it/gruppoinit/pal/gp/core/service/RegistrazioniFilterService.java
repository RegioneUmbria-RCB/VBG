package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.helper.ScadenzeHelper;

import java.util.List;

import org.springframework.validation.BindingResult;

public interface RegistrazioniFilterService {

    public void setBindingResult(BindingResult result);

    public BindingResult getBindingResult();

    /**
     * metodo per recuperare tutte le registrazioni che hanno delle registrazioniImporti non completamente assegnate
     * filtrate in base ai campi di registrazioniFilter
     * 
     * @param filter
     * @return
     */
    public List<ScadenzeHelper> findScadenzeByFilter(RegistrazioniFilter filter);

    public List<RegistrazioniFilter> searchRegistrazioni(RegistrazioniFilter registrazioniFilter);
}
