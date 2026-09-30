package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.helper.TitolaritaPagamentiEnum;
import it.gruppoinit.pal.gp.core.domain.BollGestDettRate;
import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.ConcessioneHelper;
import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.AbstractQueryBollettazioneMercatiHelper;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollettazioneDAO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollGestDettaglioDTO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollettazioneIstanzeFiltriRicerca;
import it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti.VerificaPosizioneDebitoriaBean;
import it.gruppoinit.pal.gp.core.features.contabilita.ImportoIvato;

public class BollettazioneDAOFakeAdapter implements BollettazioneDAO {

    @Override
    public List<RigaDettaglioCalcolo> findByFiltriBollettazioneIstanza(List<String> filtriCodiceComune, List<String> filtriScCodice,
	    List<Integer> filtriCodiceEndo, List<Integer> filtriCausaleOnere, IntervalloDate intervalloDate, Boolean conguaglio, boolean isAzienda) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public <T> T getByIdForBollettazione(Class<T> cls, Integer id) {

	return null;
    }

    @Override
    public <T> void save(T entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public <T> void save(List<T> entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<BollGestTestata> findTestateByCodiciRuoli(List<Integer> codiciRuoli, Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Boolean esistonoRigheInviateASistemaPagamenti(Integer idBollettazione) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void updateRigheSetRiferimentiPosizioneDebitoria(Integer idDettPosizioneDebitoria, List<Integer> idRighe) {

	// TODO Auto-generated method stub
    }

    @Override
    public Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorieByBollettazioneEAnagrafe(Integer idBollettazione,
	    Integer idAnagrafica) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer countTestateByCodiciRuoli(List<Integer> codiciRuoli) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorieByBollettazione(Integer idBollettazione) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void updateStatoTestata(Integer idBollettazione, String descrizioneStato) {

	// TODO Auto-generated method stub
    }

    @Override
    public void copiaRiferimentiIstanzeOneriSuBollGestDettaglio(BollGestDettaglio oldEntity, BollGestDettaglio newEntity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<LivelloServizio> findLivelliServizio() {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Boolean esisteBollettazioneStessoPeriodo(CreazioneBollTestata bollTestata) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ConcessioneHelper getInfoConcessione(Integer idAutorizzazioniConcessione, Boolean passaggioStorico) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public BollGestTestata findBollettazionePrecedenteByDataAndTipologia(Integer codiceTipologiaBollettazione,
	    Date dataPartenzaBollettazioneAttuale) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<MercatiFormuleCalcolo> findFormuleByIntervallo(Date dataInizio, Date dataFine) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void delete(Integer idBollettazione) {

	// TODO Auto-generated method stub
    }

    @Override
    public Integer recuperaCodiceLetteraAccompagnamento(String cf_ente_creditore, Integer riIdPosizioneDebitoria) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<BollGestDettaglio> findRigheValidabili(Integer idBollettazione) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void copiaRiferimentiConcessioniSuBollGestDettaglio(BollGestDettaglio oldEntity, BollGestDettaglio newEntity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void aggiornaDataScadenza(String idcomune, Integer idBollettazione, Date dataScadenza) {

	// TODO Auto-generated method stub
    }

    @Override
    public void inserisciRata(BollGestDettRate rata) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<DettaglioRateizzazione> findDettaglioRateizzazione(Integer idRiga) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DettaglioRateizzazione> findDettaglioRateizzazionePerAnagrafica(Integer idBollettazione, Integer idAnagrafica) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public String findDescrizionePosteggio(Integer idPosteggio) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int aggiornaSequenzaBollGestIstanzeOneri(int totaleRecordDaInserire) {

	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int aggiornaSequenzaBollGestDettaglio(int totaleRecordDaInserire) {

	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public void inserisciRigheIstanzeOneri(BollettazioneIstanzeFiltriRicerca filtriRicerca, int numeroInizialeBollGestDettaglio,
	    int numeroInizialeBollGestIstanzeOneri, boolean conguaglio, boolean azienda, String guid, Integer bollTestataId, Date dataScadenza) {

	// TODO Auto-generated method stub
    }

    @Override
    public int contaRigheIstanzeOneri(BollettazioneIstanzeFiltriRicerca filtriRicerca, boolean conguaglio, boolean azienda) {

	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public List<BollGestDettaglioDTO> findBollGestDettaglioDTOByTestata(Integer idBollettazione) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<BollGestDettaglioDTO> findRigheByIdBollettazioneAndAnagrafe(Integer idBollettazione, Integer idAnagrafica) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ImportoIvato getImportoRigaValidaByTestataAnagrafeEContoEAutorizzazione(Integer idBollettazione, Integer idAnagrafe, Integer idConto,
	    Integer idAutorizzazione) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void commit() {

	// TODO Auto-generated method stub
    }

    @Override
    public void flush() {

	// TODO Auto-generated method stub
    }

    @Override
    public void clear() {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Object[]> getSummaryForBollettazione(int idbollettazionetestata, int idanagrafe, boolean soloValidati) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<RigaDettaglioCalcoloMercati> findByFiltriBollettazioneMercato(String guid, TitolaritaPagamentiEnum titolarita,
	    List<Integer> filtriMercati, IntervalloDate intervalloDate, Boolean conguaglio) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int aggiornaSequenzaBollGestDettAutorizz(int totaleRecordDaInserire) {

	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int aggiornaSequenzaBollGestMercatiDett(int totaleRecordDaInserire) {

	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public AbstractQueryBollettazioneMercatiHelper getQueryBuilder(String guid, TitolaritaPagamentiEnum titolarita, List<Integer> filtriMercati,
	    IntervalloDate intervalloDate, Boolean conguaglio) {

	// TODO Auto-generated method stub
	return null;
    }
}
