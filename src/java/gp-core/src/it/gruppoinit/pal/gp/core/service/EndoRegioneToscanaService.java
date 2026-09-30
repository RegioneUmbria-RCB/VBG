package it.gruppoinit.pal.gp.core.service;

import it.eng.suap.xengine.model.service.xcommon.AzioneType;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.cart.ElenchiEndoFACCT;
import it.gruppoinit.pal.gp.core.domain.cart.EndoFACCT;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;

import java.util.List;

public interface EndoRegioneToscanaService {

    /**
     * Cerca tutti gli endo attivabili definiti dal CART. Sono attivabili tutti gli endo che hanno un record in
     * stp_endo_tipo1.<br />
     * Se presente nel file deploy.properties la proprietà cart.codici_endo_regionali.attivabili è valorizzata allora
     * saranno attivabili solamente quelli censiti in questa proprietà
     * 
     * @param idAlberoProc
     * @return
     */
    public List<EndoFACCT> findElencoEndoCARTPerAttivita(Integer idAlberoProc);

    /**
     * Restituisce tre elenchi endo (endo CART, endo nono CART necessari e endo non CART ricorrenti) strutturati
     * gerarchicamente per famiglia e categoria
     * 
     * @param idAlberoProc
     * @return
     */
    public ElenchiEndoFACCT getElenchiEndoPerAttivita(Integer idAlberoProc, boolean flagLavoriSuFabbricati, AzioneType tipoAzione);

    public List<EndoFACCT> getEndoLocaliSelezionati(Integer idAlberoProc, List<String> codiciEndoSelez, String searchCodiceEndo);

    public StpEndoTipo2 getDatiAttivitaBdr(Integer idAttivita);

    public AzioneType checkAzione(Integer idAlberoproc);

    public List<Allegati> getAllegatiEndoAttivi(List<String> codiciEndoAttivi, String idProc, String descAllegato,
	    FieldOperationsEnum searchAllegatoStyle);
    
    public List<AlberoprocDocumenti> getDocumentiEreditatiEndoAttivi(Integer idAlberoProc, String descAllegato,
	    FieldOperationsEnum searchAllegatoStyle);

    public List<Inventarioprocdyn2modellit> getSchedeDinamicheEndoAttivi(List<String> codiciEndoAttivi, String idProc, String descDynModello,
	    FieldOperationsEnum searchAllegatoStyle);
}
