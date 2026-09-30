package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.service.exception.DurcNonValidoException;
import it.gruppoinit.pal.gp.core.service.helper.NuovoDURCHelper;

import java.util.Date;

public interface InfoDurcService {

    public Anagrafedocumenti verificaEsistenzaDURC(Integer codiceAnagrafe, Integer codiceIstanza, Date dataVerifica)
	    throws FunzioneBusinessRemotaException, InvalidConfigurationException, DurcNonValidoException;

    public Anagrafedocumenti nuovaRichiestaDURC(NuovoDURCHelper helper) throws FunzioneBusinessRemotaException, InvalidConfigurationException;

    @DeletableCacheElements
    public void resetObjectCached();

    public NuovoDURCHelper validaNuovaRichiestaDURC(Integer codiceAnagrafe);

    public NuovoDURCHelper validaDURCHelper(NuovoDURCHelper nuovoDURCHelper);
}
