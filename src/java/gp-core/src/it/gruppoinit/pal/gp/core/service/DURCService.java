package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.service.exception.DurcNonValidoException;
import it.gruppoinit.pal.gp.core.service.helper.NuovoDURCHelper;

import java.util.Date;

public interface DURCService {

    public Anagrafedocumenti verificaEsistenzaDURC(Integer codiceAnagrafe, Integer codiceIstanza, Date dataVerifica)
	    throws FunzioneBusinessRemotaException, InvalidConfigurationException, DurcNonValidoException;

    public NuovoDURCHelper validaNuovaRichiestaDURC(Integer codiceAnagrafe);

    public NuovoDURCHelper validaDURCHelper(NuovoDURCHelper nuovoDURCHelper);

    public Anagrafedocumenti nuovaRichiestaDURC(NuovoDURCHelper nuovoDURCHelper) throws FunzioneBusinessRemotaException,
	    InvalidConfigurationException;
}
