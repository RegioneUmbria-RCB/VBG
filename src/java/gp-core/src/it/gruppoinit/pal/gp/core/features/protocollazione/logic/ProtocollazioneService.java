package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.AzioniProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.AnnullamentoProtocolloException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.CambiaFascicoloIstanzaException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.CreaCopieException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.EseguiAccettazioneException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.FascicolaIstanzaException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.FascicolaMovimentoException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.LeggiProtocolloException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.ProtocollaIstanzaException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.ProtocollazioneAutorizzazioneException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.ProtocollazioneFallitaException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.ProtocollazioneMovimentoException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.RecuperaClassificheException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.RecuperaMotiviAnnullamentoException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.RecuperaTipiDocumentoException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.RicercaFascicoliException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.StampaEtichetteException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.VerificaProtocolloAnnullatoException;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.VerificaProtocolloFascicolatoException;
import it.gruppoinit.pal.gp.core.service.BaseService;
import it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.protocollo.schemas.messages.AllegatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiAnagraficiType;
import it.gruppoinit.protocollo.schemas.messages.DatiFascType;
import it.gruppoinit.protocollo.schemas.messages.DatiFascicoloResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiMittentiType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloAnnullatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloEsitatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloFascicolatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloLettoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;
import it.gruppoinit.protocollo.schemas.messages.EtichetteResponseType;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.types.SportelloType;

public interface ProtocollazioneService extends BaseService<ProtocollazioneCommand, String> {

    public static final String NUMERO_PROTOCOLLO_ASINCRONO_CHAR = "-";

    public boolean isLeggiProtocollo(String codiceComune);

    public DatiProtocolloResponseType protocolla(ProtocolloSourceEnum source, ProtocollazioneCommand protocollazioneCommand, String software,
	    String codiceComune) throws ProtocollazioneFallitaException;

    public void protocollaAutorizzazione(int codiceRegistro, int codiceResponsabile, String token, Calendar dataAutorizzazione, Autorizzazioni entity,
	    String codiceComune) throws ProtocollazioneAutorizzazioneException;

    public DatiProtocolloResponseType protocollaIstanza(Istanze entity, String token, TipoInserimento tipoInserimento,
	    DatiMittentiType mittentiDaSovrascrivere) throws ProtocollaIstanzaException;

    public DatiProtocolloResponseType protocollaMovimento(Movimenti movimento, String token);

    public DatiProtocolloResponseType protocollaMovimento(Movimenti movimento, String token, DatiAnagraficiType amministrazioneMittente);

    public DatiProtocolloResponseType protocollaComunicazioneGraduatoria(Movimenti movimento, String token);

    public DatiProtocolloResponseType protocollaComunicazione(Movimenti movimento, String token);

    public DatiProtocolloLettoResponseType leggiProtocollo(String token, Istanze istanza);

    public DatiProtocolloLettoResponseType leggiProtocollo(String token, Movimenti movimento);

    public DatiProtocolloLettoResponseType leggiProtocolloForView(String token, Movimenti movimento);

    public DatiProtocolloLettoResponseType leggiProtocollo(String token, PecInbox pec, String codiceComune, String software);

    public DatiProtocolloAnnullatoResponseType isAnnullato(String token, String fkidProtocollo, String numeroprotocollo, Date dataProtocollo,
	    String software, String codiceComune) throws VerificaProtocolloAnnullatoException;

    public void annullaProtocollo(String token, String fkidProtocollo, Date dataProtocollo, String numProtocollo, String motivoAnnullamento,
	    String noteAnnullamento, String software, String codiceComune) throws AnnullamentoProtocolloException;

    public List<CodiceDescrizioneBean> getMotiviAnnullamento(String token, String software, String codiceComune)
	    throws RecuperaMotiviAnnullamentoException;

    public EtichetteResponseType stampaEtichette(String token, String fkidIdProtocollo, String numeroProtocollo, Date dataProtocollo,
	    Integer numeroCopie, String stampante, String software, String codiceComune) throws StampaEtichetteException;

    public DatiProtocolloFascicolatoResponseType isFascicolato(String token, String fkidProtocollo, String numeroprotocollo, Date dataProtocollo,
	    String software, String codiceComune) throws VerificaProtocolloFascicolatoException;

    public List<CodiceDescrizioneBean> getFascicoliPerIstanza(String token, Istanze istanza) throws RicercaFascicoliException;

    public DatiFascicoloResponseType cambiaFascicoloIstanzaXml(String token, ProtocollazioneCommand protocollazioneCommand)
	    throws CambiaFascicoloIstanzaException;

    public void fascicolaIstanza(Istanze entity, String token, TipoInserimento tipoInserimento);

    public DatiFascicoloResponseType fascicolaIstanzaXml(String token, ProtocollazioneCommand protocollazioneCommand)
	    throws FascicolaIstanzaException;

    public List<String> getListaStampanti(String token);

    public DatiFascicoloResponseType fascicolaMovimento(String token, Movimenti movimento);

    public DatiFascicoloResponseType fascicolaMovimentoXml(String token, ProtocollazioneCommand protocollazioneCommand)
	    throws FascicolaMovimentoException;

    public DatiProtocolloResponseType creaCopie(Integer codiceistanza, String token) throws CreaCopieException;

    public AllegatoResponseType leggiAllegato(String token, String idAllegato, String software, String codiceComune);

    public AllegatoResponseType leggiAllegatoUORuolo(String token, String idAllegato, String uo, String ruolo, String software, String codiceComune);

    public CodiceDescrizioneBean[] getListaClassifiche(String software, String codiceComune) throws RecuperaClassificheException;

    public CodiceDescrizioneBean[] getListaTipiDocumento(String software, String codiceComune) throws RecuperaTipiDocumentoException;

    void resetObjectCached();

    public DatiProtocolloLettoResponseType leggiProtocollo(String token, String numeroProtocollo, String annoProtocollo, String idProtocollo,
	    String software, String codiceComune) throws LeggiProtocolloException;

    public DatiProtocolloLettoResponseType leggiProtocolloUORuolo(String token, String numeroProtocollo, String annoProtocollo, String idProtocollo,
	    String uo, String ruolo, String software, String codiceComune) throws LeggiProtocolloException;

    public DatiProtocolloLettoResponseType leggiProtocolloConData(String token, String numeroProtocollo, Date dataProtocollo, String idProtocollo,
	    String software, String codiceComune) throws LeggiProtocolloException;

    public InserimentoPraticaResponse insertPraticaSTCDaSistemaEsterno(AzioniProtocollazioneCommand cmd) throws FunzioneBusinessRemotaException;

    public InserimentoPraticaResponse insertPraticaSTCDaAzioni(AzioniProtocollazioneCommand azioniProtocollazioneCommand)
	    throws FunzioneBusinessRemotaException, BusinessValidationException;

    public InserimentoPraticaResponse insertPraticaSTCDaSuapInRete(AzioniProtocollazioneCommand azioniProtocollazioneCommand)
	    throws FunzioneBusinessRemotaException, BusinessValidationException;

    public NotificaAttivitaResponse insertMovimentoSTCDaAzioni(AzioniProtocollazioneCommand azioniProtocollazioneCommand,
	    SportelloType sportelloTypeMitt, SportelloType sportelloTypeDest) throws FunzioneBusinessRemotaException, BusinessValidationException;

    public NotificaAttivitaResponse insertMovimentoSTCSuapInRete(AzioniProtocollazioneCommand azioniProtocollazioneCommand)
	    throws FunzioneBusinessRemotaException, BusinessValidationException;
    
    public NotificaAttivitaResponse insertMovimentoSistemaExt(AzioniProtocollazioneCommand azioniProtocollazioneCommand)
	    throws FunzioneBusinessRemotaException, BusinessValidationException;

    public List<DatiFascType> cercaFascicoli(String software, String codiceComune, DatiFascType datiFascicolo);

    public String creaUnitadocumentale(String software, String codiceComune, ProtocollazioneCommand command);

    DatiProtocolloResponseType registrazioneDocer(ProtocollazioneCommand command);

    void invioPECDocer(Integer codiceMovimento);

    /**
     * Il medoto invia i documenti selelazionati al protocollo. I documenti inviati, saranno quelli all'interno dell'
     * oggetto documentiHelper flaggati come da inviare
     */
    public void updateInviaDocumenti(ProtocollazioneCommand protocolloCommand);

    public DatiProtocolloResponseType protocollaDomandaOnline(InserimentoPraticaNLARequest request, Istanze istanza)
	    throws ProtocollaIstanzaException;

    public DatiProtocolloResponseType protocollaMovimentoOnline(Movimenti mov, InserimentoAttivitaNLARequest request, Istanze istanza)
	    throws ProtocollazioneMovimentoException;

    /**
     * <pre>
     * 0 non attivo 
     * 1 flusso interno/arrivo 
     * 2 flusso arrivo
     * 3 flusso interno
     * </pre>
     * 
     * @param codiceComune
     * @param codiceSoftware
     * @return
     */
    public int findSmistamentoMultiplo(String codiceComune, String codiceSoftware);

    public String findClassifica(Istanze entity, String codiceComune, String software);

    public String findProtocolloSmistamentoDefault(String codiceComune, String software);

    public List<ProtocolloAttivoBean> verificaProtocolloAttivo(List<ISoftwareComuneData> softwareComuneFromIdDettaglioList);

    void eseguiAccettazione(String token, String numeroProtocollo, String annoProtocollo, String idProtocollo, String software, String codiceComune)
	    throws EseguiAccettazioneException;

    public DatiProtocolloEsitatoResponseType isEsitato(String token, String numeroProtocollo, String annoProtocollo, String idProtocollo,
	    String software, String codiceComune);
}