package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.autocompiler.AutocompilerConfig;


public interface AutocompilerConfigurationService {
    
    public AutocompilerConfig getAutocompilerConfiguration(String configContext);
}
