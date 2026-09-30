package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.MercatiConsorzi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Date;
import java.util.List;

public interface MercatiConsorziService extends BaseService<MercatiConsorzi, PkId> {

    public List<MercatiConsorzi> findByCodiceMercato(Integer codiceMercato);

    public List<MercatiConsorzi> findByCodiceMercatoAndDataRiferimento(Integer codiceMercato, Date datariferimento);

    public List<MercatiConsorzi> findByCodiceAnagrafe(Integer codiceAnagrafe);
}
