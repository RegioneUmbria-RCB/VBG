package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.helper.ScadenzeHelper;
import it.gruppoinit.pal.gp.core.service.RegistrazioniFilterService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniImportiService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class RegistrazioniFilterServiceImpl implements RegistrazioniFilterService {

    private RegistrazioniImportiService registrazioniImportiService;
    private RegistrazioniService registrazioniService;

    @Autowired
    public void setRegistrazioniImportiService(RegistrazioniImportiService registrazioniImportiService) {

	this.registrazioniImportiService = registrazioniImportiService;
    }

    @Autowired
    public void setRegistrazioniService(RegistrazioniService registrazioniService) {

	this.registrazioniService = registrazioniService;
    }

    protected BindingResult result;

    @Override
    public BindingResult getBindingResult() {

	return this.result;
    }

    @Override
    public void setBindingResult(BindingResult result) {

	this.result = result;
    }

    @Override
    public List<ScadenzeHelper> findScadenzeByFilter(RegistrazioniFilter filter) {

	// §§§BEGIN§§§
	Set<Registrazioni> regs = new HashSet<Registrazioni>(0);
	List<ScadenzeHelper> scadenze = new ArrayList<ScadenzeHelper>();
	List<RegistrazioniImporti> list = registrazioniImportiService.findByRegistrazioniFilter(filter);
	Registrazioni reg;
	// creo un lista di Registrazioni distinte
	for (RegistrazioniImporti registrazioniImporti : list) {
	    reg = registrazioniImporti.getRegistrazioni();
	    regs.add(reg);
	}
	ScadenzeHelper scadenzeHelper;
	// creo una lista di scadenze a partire dalla lista di registrazioni
	for (Registrazioni registrazioni : regs) {
	    // verifico se la registrazione ha degli importi non assegnati
	    if (registrazioni.getRimanenza().compareTo(new BigDecimal(0)) != 0) {
		scadenzeHelper = new ScadenzeHelper();
		scadenzeHelper.setRegistrazioni(registrazioni);
		scadenze.add(scadenzeHelper);
	    }
	}
	return scadenze;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public List<RegistrazioniFilter> searchRegistrazioni(RegistrazioniFilter registrazioniFilter) {

	// §§§BEGIN§§§
	return registrazioniService.searchRegistrazioni(registrazioniFilter);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
