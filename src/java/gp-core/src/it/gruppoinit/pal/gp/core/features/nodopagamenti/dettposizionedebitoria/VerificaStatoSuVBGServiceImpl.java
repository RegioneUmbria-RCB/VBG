package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.IIdPosizioneSuNodoPagamenti;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.IVerificaStatoSuNodoPagamenti;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.IdPosizioneSuNodoPagamenti;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.VerificaStatoPosizioniDebitorie;

public class VerificaStatoSuVBGServiceImpl implements IVerificaStatoSuVBG {

    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    private IVerificaStatoSuNodoPagamenti verificaStatoService;

    public VerificaStatoSuVBGServiceImpl(DettPosizioneDebitoriaService dettPosizioneDebitoriaService,
	    IVerificaStatoSuNodoPagamenti verificaStatoService) {

	this.verificaStatoService = verificaStatoService;
	this.dettPosizioneDebitoriaService = dettPosizioneDebitoriaService;
    }

    @Override
    public List<VerificaStatoPosizioniDebitorie> verificaStato(Set<Integer> idDettPosizioniDebitorie) throws FunzioneBusinessRemotaException {

	Set<IIdPosizioneSuNodoPagamenti> posizioniDaVerificare = new HashSet<IIdPosizioneSuNodoPagamenti>();
	for (Integer idDettaglio : idDettPosizioniDebitorie) {
	    DettPosizioneDebitoria posizione = this.dettPosizioneDebitoriaService.findById(new PkId(idDettaglio));
	    posizioniDaVerificare.add(new IdPosizioneSuNodoPagamenti(posizione.getCfEnteCreditore(), posizione.getIdPosizioneDebitoria()));
	}
	return verificaStatoService.verificaStato(posizioniDaVerificare);
    }
}
