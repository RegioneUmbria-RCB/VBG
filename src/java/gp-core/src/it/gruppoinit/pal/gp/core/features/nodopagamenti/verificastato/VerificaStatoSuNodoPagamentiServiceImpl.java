package it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.paevolution.ws.pagamenti_types.ElencoPosizioniDebitorieType;
import com.paevolution.ws.pagamenti_types.RiferimentoPosizioneDebitoriaType;
import com.paevolution.ws.pagamenti_types.StatoPosizioneType;
import com.paevolution.ws.pagamenti_types.VerificaStatoPosizioniResponseType;
import com.paevolution.ws.pagamenti_types.VerificaStatoPosizioniType;

import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.ws.client.NodoPagamentiWsClient;

public class VerificaStatoSuNodoPagamentiServiceImpl implements IVerificaStatoSuNodoPagamenti {

    private NodoPagamentiWsClient client;
    private VerticalizzazioneNodoPagamentiServiceImpl vertService;

    public VerificaStatoSuNodoPagamentiServiceImpl(VerticalizzazioneNodoPagamentiServiceImpl vertService) {

	this.vertService = vertService;
	this.client = new NodoPagamentiWsClient(vertService.urlWs());
    }

    @Override
    public List<VerificaStatoPosizioniDebitorie> verificaStato(Set<IIdPosizioneSuNodoPagamenti> idPosizioniDebitorie)
	    throws FunzioneBusinessRemotaException {

	if (idPosizioniDebitorie == null || idPosizioniDebitorie.isEmpty()) {
	    throw new IllegalArgumentException("Impossibile richiamare il metodo verificaStato senza passare la lista delle posizioni da verificare");
	}
	Set<VerificaStatoPosizioniType> richieste = this.createVerificaStatoPosizioniDebitorieRequest(idPosizioniDebitorie);
	List<VerificaStatoPosizioniDebitorie> retVal = new ArrayList<VerificaStatoPosizioniDebitorie>();
	for (VerificaStatoPosizioniType richiesta : richieste) {
	    VerificaStatoPosizioniResponseType response = this.client.verificaStatoPosizioniDebitorie(richiesta);
	    List<StatoPosizioneType> statoPosizioni = response.getStatoPosizioni().getStatoPosizioni();
	    for (StatoPosizioneType statoPosizione : statoPosizioni) {
		retVal.add(VerificaStatoPosizioniDebitorie
			.fromStatoPosizioneType(new StatoPosizionePerVerifica(statoPosizione, this.vertService.idModalitaPagamento())));
	    }
	}
	return retVal;
    }

    private Set<VerificaStatoPosizioniType> createVerificaStatoPosizioniDebitorieRequest(Set<IIdPosizioneSuNodoPagamenti> fkIdPosizioniDebitorie) {

	Map<String, Set<Integer>> posizioniDaVerificare = new HashMap<String, Set<Integer>>();
	for (IIdPosizioneSuNodoPagamenti idPosizione : fkIdPosizioniDebitorie) {
	    if (!posizioniDaVerificare.containsKey(idPosizione.getCfEnteCreditore())) {
		posizioniDaVerificare.put(idPosizione.getCfEnteCreditore(), new HashSet<Integer>());
	    }
	    posizioniDaVerificare.get(idPosizione.getCfEnteCreditore()).add(idPosizione.getIdPosizioneDebitoria());
	}
	Set<VerificaStatoPosizioniType> richieste = new HashSet<VerificaStatoPosizioniType>();
	for (Map.Entry<String, Set<Integer>> posizione : posizioniDaVerificare.entrySet()) {
	    VerificaStatoPosizioniType request = new VerificaStatoPosizioniType();
	    request.setCfEnteCreditore(posizione.getKey());
	    ElencoPosizioniDebitorieType elenco = new ElencoPosizioniDebitorieType();
	    for (Integer idPosizione : posizione.getValue()) {
		RiferimentoPosizioneDebitoriaType rpd = new RiferimentoPosizioneDebitoriaType();
		rpd.setIdPosizione(BigInteger.valueOf(idPosizione));
		elenco.getPosizione().add(rpd);
	    }
	    request.setPosizione(elenco);
	    richieste.add(request);
	}
	return richieste;
    }
}
