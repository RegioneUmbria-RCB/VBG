package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.service.helper.NuovoDURCHelper;

public interface InfoDurcService {

    //    public Anagrafedocumenti verificaEsistenzaDURC(Integer codiceAnagrafe, Integer codiceIstanza, Date dataVerifica)
    //	    throws FunzioneBusinessRemotaException, InvalidConfigurationException, DurcNonValidoException;
    //
    //    public Anagrafedocumenti nuovaRichiestaDURC(NuovoDURCHelper helper) throws FunzioneBusinessRemotaException, InvalidConfigurationException;
    @DeletableCacheElements
    public void resetObjectCached();

    public NuovoDURCHelper validaNuovaRichiestaDURC(Integer codiceAnagrafe);

    public NuovoDURCHelper validaDURCHelper(NuovoDURCHelper nuovoDURCHelper);
}
