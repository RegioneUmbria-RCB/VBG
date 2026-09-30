package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.AppIoCoda;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaId;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfig;

public interface IAppIoCodaService {

    public void nuovoMessaggio(Integer codiceMovimento);

    public AppIoCoda findById(AppIoCodaId entity);

    void inviaAppIo(AppIoServiziConfig ioServiziConfig, String idservizio, String oggetto, String corpo, Anagrafe a,
	    Integer idDettaglioComunicazione);
}
