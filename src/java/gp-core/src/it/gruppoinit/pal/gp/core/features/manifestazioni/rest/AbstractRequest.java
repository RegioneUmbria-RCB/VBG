package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public abstract class AbstractRequest {
    
    public abstract void valida() throws BusinessValidationException;
}
