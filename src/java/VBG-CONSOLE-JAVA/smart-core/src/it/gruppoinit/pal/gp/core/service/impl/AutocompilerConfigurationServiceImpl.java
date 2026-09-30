package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.domain.autocompiler.AutocompilerConfig;
import it.gruppoinit.pal.gp.core.service.AutocompilerConfigurationService;
import it.gruppoinit.sigepro.cart.service.utils.AttachmentsUtils;
import it.gruppoinit.sigepro.cart.service.utils.XmlUtils;

import java.io.InputStream;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AutocompilerConfigurationServiceImpl implements AutocompilerConfigurationService {
    
    Logger log = LoggerFactory.getLogger(AutocompilerConfigurationServiceImpl.class);

    @Override
    public AutocompilerConfig getAutocompilerConfiguration(String configContext) {

	AutocompilerConfig retCfg = null;
	InputStream is = this.getClass().getClassLoader().getResourceAsStream(configContext);
	if(null != is){
	    try {
		byte[] cfgData = AttachmentsUtils.readBytesFromStream(is);
		String cfgString = new String(cfgData, FACCTConstants.DEFAULT_CHARSET);
		retCfg = (AutocompilerConfig) XmlUtils.unMarshallString(cfgString, AutocompilerConfig.class);
	    } catch (Exception e) {
		String errMsg = MessageFormat.format("errore durante il recupero della configurazione della compilazione automatica: {0}", new Object[]{e.getMessage()});
		log.error("getAutocompilerConfiguration() - " + errMsg, e);
		throw new RuntimeException(errMsg, e);
	    }
	}
	else{
	    throw new RuntimeException("Impossibile recuperare la configurazione della compilazione automatica per " + configContext);
	}
	return retCfg;
    }
}
