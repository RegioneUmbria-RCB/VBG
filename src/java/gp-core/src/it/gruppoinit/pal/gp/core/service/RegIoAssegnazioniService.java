package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegIoAssegnazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniInOut;

import java.math.BigDecimal;
import java.util.Set;

public interface RegIoAssegnazioniService extends BaseService<RegIoAssegnazioni, PkId> {

    public void assegna(RegistrazioniInOut registrazioniInOut, Set<RegistrazioniImporti> registrazioniImportiList, BigDecimal importo);

    public void assegnaRigaImporto(RegistrazioniInOut registrazioniInOut, RegistrazioniImporti registrazioniImporti, BigDecimal incasso);

    public void resetAssegnazioni(Set<RegIoAssegnazioni> regIoAssegnazioniList);
}
