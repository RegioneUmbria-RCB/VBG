package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;

import java.util.List;

public interface SdeproxyService extends BaseService<Sdeproxy, String> {

    public List<Comuni> findComuniAssociati(String idente);

    public Sdeproxy findByAlias(String idcomunealias);

    public Sdeproxy findByCodiceCatastale(String codiceCatastale);

    public Sdeproxy findByIdEnte(String idente);

    public Sdeproxy findByCodiceUnioneRFC53(String codiceUnioneRFC53);
}
