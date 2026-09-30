package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AppIoCodaStati;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaStatiId;

public interface IAppIoCodaStatiService {

    void insert(AppIoCodaStati codaStati);

    public AppIoCodaStati findById(AppIoCodaStatiId entity);

    public List<AppIoCodaStati> findByGuid(String guid);
}
