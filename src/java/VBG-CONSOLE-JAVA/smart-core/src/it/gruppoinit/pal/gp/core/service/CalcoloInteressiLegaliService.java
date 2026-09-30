/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.CalcoloInteressiLegali;

import org.springframework.validation.BindingResult;

/**
 * @author francescop
 * 
 */
public interface CalcoloInteressiLegaliService {

    public void setBindingResult(BindingResult result);

    public BindingResult getBindingResult();

    /**
     * Validazione dell'oggetto CalcoloInteressiLegali. Controlla se importo, dataInizio, dataFine
     * 
     * @param calcoloInteressiLegali
     */
    public void validate(CalcoloInteressiLegali calcoloInteressiLegali);
}
