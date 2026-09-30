using Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;
using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using Init.SIGePro.Manager.DTO.Interventi;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogicTests.Adapters.IstanzaSigeproAdapterTests.Mocks
{
    public class InterventiRepositoryFake : IInterventiRepository
    {
        public bool EsistonoVociAttivabiliTramiteAreaRiservata(string idComune, string software)
        {
            throw new NotImplementedException();
        }

        public string EstraiDescrizioneEstesa(int idIntervento)
        {
            throw new NotImplementedException();
        }

        public NodoAlberoInterventiDto GetAlberaturaNodoDaId(string aliasComune, int idNodo)
        {
            throw new NotImplementedException();
        }

        public LinkedList<InterventoDto> GetAlberaturaNodoDaId2(int idNodo)
        {
            throw new NotImplementedException();
        }

        public NodoAlberoInterventiDto GetAlberoInterventi(string aliasComune, string software)
        {
            throw new NotImplementedException();
        }

        public int? GetCodiceOggettoCertificatoDiInvioDaIdIntervento(int idIntervento)
        {
            throw new NotImplementedException();
        }

        public int? GetCodiceOggettoWorkflow(int idIntervento)
        {
            throw new NotImplementedException();
        }

        public InterventoDto GetDettagliIntervento(string aliasComune, int idNodo, IAmbitoRicercaIntervento ambitoRicercaDocumenti, bool leggiNoteEstese)
        {
            return new InterventoDto
            {
                Note = "Note"
            };
        }

        public InterventoDto GetDettagliIntervento(int idNodo, IAmbitoRicercaIntervento ambitoRicercaDocumenti, bool leggiNoteEstese)
        {
            throw new NotImplementedException();
        }

        public int? GetidDocumentoRiepilogoDaIdIntervento(int idIntervento)
        {
            throw new NotImplementedException();
        }

        public List<int> GetIdNodiPadre(string aliasComune, int idNodo)
        {
            throw new NotImplementedException();
        }

        public NodoConWorkflowDto GetNodoConWorkflowDaIdIntervento(int idIntervento)
        {
            throw new NotImplementedException();
        }

        public string GetNomeLivelloAutenticazionePerInterventi(int idIntervento)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<InterventoDto> GetSottonodi(string aliasComune, string software, int idnodo, IAmbitoRicercaIntervento ambitoRicerca)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<InterventoDto> GetSottonodi(string aliasComune, string software, int idnodo, IAmbitoRicercaIntervento ambitoRicerca, string codiceComune)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<InterventoDto> GetSottonodi2(int idnodo, IAmbitoRicercaIntervento ambitoRicerca)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<InterventoDto> GetSottonodi2(int idnodo, IAmbitoRicercaIntervento ambitoRicerca, string codiceComune)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<InterventoDto>> GetSottonodiAsync(int idnodo, IAmbitoRicercaIntervento ambitoRicerca, string codiceComune)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<InterventoDto> GetSottonodiDaIdAteco(string aliasComune, string software, int idNodoPadre, int idAteco, IAmbitoRicercaIntervento ambitoRicerca)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<InterventoDto> GetSottonodiDaIdAteco(string aliasComune, string software, int idNodoPadre, int idAteco, IAmbitoRicercaIntervento ambitoRicerca, string codiceComune)
        {
            throw new NotImplementedException();
        }

        public Task<TitoloInterventoDto> GetTitoloInterventoAsync(int idIntervento)
        {
            throw new NotImplementedException();
        }

        public bool HaPresentatoDomandePerIntervento(int idIntervento, string codiceFiscale)
        {
            throw new NotImplementedException();
        }

        public bool InterventoSupportaRedirect(int codiceIntervento)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<InterventoBreveDto> RicercaTestuale(string aliasComune, string software, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, IAmbitoRicercaIntervento ambitoRicerca)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<InterventoBreveDto> RicercaTestuale2(string matchParziale, int matchCount, string modoRicerca, string tipoRicerca, IAmbitoRicercaIntervento ambitoRicerca)
        {
            throw new NotImplementedException();
        }

        public RisultatoVerificaAccessoIntervento VerificaAccessoIntervento(int idIntervento, string codiceComune)
        {
            throw new NotImplementedException();
        }

        public Task<RisultatoVerificaAccessoIntervento> VerificaAccessoInterventoDomandaOnLineAsync(int idIntervento, string codiceComune)
        {
            throw new NotImplementedException();
        }
    }
}
