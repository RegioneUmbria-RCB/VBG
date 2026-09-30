package it.gruppoinit.pal.gp.core.features.rubrica;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Rubrica;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface IRubricaService extends BaseService<Rubrica, PkId> {

    List<RisultatoRicercaRubrica> ricercaIndirizzo(String partial, int resultCount);

    public void updateAllineaDaLDAP(LDAPProperties ldapProperties) throws Exception;
}
