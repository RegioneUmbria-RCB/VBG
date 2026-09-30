using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using Init.SIGePro.Manager.DTO.Interventi;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi
{
    public interface IInterventiRepository
    {
        Task<TitoloInterventoDto> GetTitoloInterventoAsync(int idIntervento);
        string EstraiDescrizioneEstesa(int idIntervento);
        NodoAlberoInterventiDto GetAlberaturaNodoDaId(string aliasComune, int idNodo);
        LinkedList<InterventoDto> GetAlberaturaNodoDaId2(int idNodo);
        NodoAlberoInterventiDto GetAlberoInterventi(string aliasComune, string software);

        InterventoDto GetDettagliIntervento(string aliasComune, int idNodo, IAmbitoRicercaIntervento ambitoRicercaDocumenti, bool leggiNoteEstese);
        InterventoDto GetDettagliIntervento(int idNodo, IAmbitoRicercaIntervento ambitoRicercaDocumenti, bool leggiNoteEstese);
        IEnumerable<InterventoDto> GetSottonodi(string aliasComune, string software, int idnodo, IAmbitoRicercaIntervento ambitoRicerca, string codiceComune);
        Task<IEnumerable<InterventoDto>> GetSottonodiAsync(int idnodo, IAmbitoRicercaIntervento ambitoRicerca, string codiceComune);
        IEnumerable<InterventoDto> GetSottonodiDaIdAteco(string aliasComune, string software, int idNodoPadre, int idAteco, IAmbitoRicercaIntervento ambitoRicerca, string codiceComune);
        IEnumerable<InterventoBreveDto> RicercaTestuale(string aliasComune, string software, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, IAmbitoRicercaIntervento ambitoRicerca);
        IEnumerable<InterventoBreveDto> RicercaTestuale2(string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, IAmbitoRicercaIntervento ambitoRicerca);
        List<int> GetIdNodiPadre(string aliasComune, int idNodo);
        int? GetCodiceOggettoCertificatoDiInvioDaIdIntervento(int idIntervento);
        int? GetidDocumentoRiepilogoDaIdIntervento(int idIntervento);
        int? GetCodiceOggettoWorkflow(int idIntervento);
        NodoConWorkflowDto GetNodoConWorkflowDaIdIntervento(int idIntervento);
        bool EsistonoVociAttivabiliTramiteAreaRiservata(string idComune, string software);
        RisultatoVerificaAccessoIntervento VerificaAccessoIntervento(int idIntervento, string codiceComune);
        Task<RisultatoVerificaAccessoIntervento> VerificaAccessoInterventoDomandaOnLineAsync(int idIntervento, string codiceComune);
        string GetNomeLivelloAutenticazionePerInterventi(int idIntervento);
        bool InterventoSupportaRedirect(int codiceIntervento);
        bool HaPresentatoDomandePerIntervento(int idIntervento, string codiceFiscale);
    }
}
