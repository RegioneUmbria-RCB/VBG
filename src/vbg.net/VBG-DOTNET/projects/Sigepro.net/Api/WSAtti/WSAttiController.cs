using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Protocollo.WsDataClass;
using it.gruppoinit.Protocollazione;
using log4net;
using Sigepro.net.Api.WSAtti.AggiungiAllegato;
using Sigepro.net.Api.WSAtti.ElencoFirmatari;
using Sigepro.net.Api.WSAtti.FascicolaDetermina;
using Sigepro.net.Api.WSAtti.InserisciDetermina;
using Sigepro.net.Api.WSAtti.LeggiDetermina;
using Sigepro.net.Api.WSAtti.NumeraDetermina;
using Sigepro.net.WsProtocollo;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System.Web.Http;
using WSAtti;

namespace Sigepro.net.Api.WSAtti
{

    [RoutePrefix("web-api/wsatti/{idComuneAlias}/{software}")]
    public class WSAttiController : ApiController
    {
        private readonly ILog _logger = LogManager.GetLogger("WSAttiController");
        private readonly IAuthenticationManager _authenticationManager;
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly ProtocolloServiceCreator _protocolloServiceCreator;

        public WSAttiController(IAuthenticationManager authenticationManager, IVerticalizzazioniFactory verticalizzazioniFactory, ProtocolloServiceCreator protocolloServiceCreator)
        {
            this._authenticationManager = authenticationManager;
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._protocolloServiceCreator = protocolloServiceCreator;
        }

        [Route("inserisci-determina/{codiceComune}")]
        [HttpPost()]
        public InserisciDeterminaResponse InserisciDetermina(string idComuneAlias, string software, string codiceComune, InserisciDeterminaRequest request)
        {

            var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneWSAtti>(idComuneAlias, software, codiceComune);

            var configurazione = new WSAttiConfigurazione
            {
                TipoConnettore = verticalizzazione.TipoConnettore,
                Url = verticalizzazione.Url,
                Utente = verticalizzazione.Utente,
                Ruolo = verticalizzazione.Utente,
                CodiceAmministrazione = verticalizzazione.Utente
            };

            var note = verticalizzazione.NoteIntegrative;
            if (!string.IsNullOrEmpty(request.Note))
            {
                note = $"{request.Note}\r\n{verticalizzazione.NoteIntegrative}";
            }

            var wsAttiRequest = new WSAttiInserisciDeterminaRequest
            {
                Classifica = request.Classifica ?? verticalizzazione.Classifica,
                CodiceAmministrazione = verticalizzazione.Utente,
                Utente = verticalizzazione.Utente,
                DataDocumento = request.Data,
                Dirigente = request.Dirigente ?? verticalizzazione.CodiceDirigente,
                Note = note,
                Oggetto = request.Oggetto,
                Proponente = request.Proponente ?? verticalizzazione.CodiceProponente,
                Pubblicare = request.Pubblicare,
                Trattamento = request.Trattamento ?? verticalizzazione.CodiceTrattamento,
                Ruolo = request.Ruolo ?? verticalizzazione.Ruolo
            };

            var service = new WSAttiFactory().Build(configurazione);

            var response = service.InserisciDetermina(wsAttiRequest);

            return InserisciDeterminaResponse.FromWSAttiInserisciDeterminaResponse(response);
        }

        [Route("numera-determina/{codiceComune}")]
        [HttpPost()]
        public NumeraDeterminaResponse NumeraDetermina(string idComuneAlias, string software, string codiceComune, NumeraDeterminaRequest request)
        {
            var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneWSAtti>(idComuneAlias, software, codiceComune);

            var configurazione = new WSAttiConfigurazione
            {
                TipoConnettore = verticalizzazione.TipoConnettore,
                Url = verticalizzazione.Url,
                Utente = verticalizzazione.Utente,
                Ruolo = verticalizzazione.Utente,
                CodiceAmministrazione = verticalizzazione.Utente
            };

            var wsAttiRequest = new WSAttiNumeraDeterminaRequest
            {
                IdDocumento = request.IdDocumento
            };

            var service = new WSAttiFactory().Build(configurazione);

            var response = service.NumeraDetermina(wsAttiRequest);

            return NumeraDeterminaResponse.FromWSAttiNumeraDeterminaResponse(response);
        }

        [Route("fascicola-determina/{codiceComune}")]
        [HttpPost()]
        public FascicolaDeterminaResponse FascicolaDetermina(string idComuneAlias, string software, string codiceComune, FascicolaDeterminaRequest request)
        {
            var authInfo = this._authenticationManager.GetTokenApplicativo(idComuneAlias);

            var dati = new DatiFascType
            {
                ClassificaFascicolo = request.Classifica,
                OggettoFascicolo = request.Oggetto
            };

            var response = this._protocolloServiceCreator.Call(ws => ws.FascicolazioneXml(authInfo.Token, software, dati, codiceComune, request.IdDocumento.ToString(), null, null));
            return FascicolaDeterminaResponse.FromDatiFascicolo(response);
        }

        [Route("leggi-determina/{codiceComune}")]
        [HttpPost()]
        public LeggiDeterminaResponse LeggiDetermina(string idComuneAlias, string software, string codiceComune, LeggiDeterminaRequest request)
        {
            var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneWSAtti>(idComuneAlias, software, codiceComune);

            var configurazione = new WSAttiConfigurazione
            {
                TipoConnettore = verticalizzazione.TipoConnettore,
                Url = verticalizzazione.Url,
                Utente = verticalizzazione.Utente,
                Ruolo = verticalizzazione.Utente,
                CodiceAmministrazione = verticalizzazione.Utente
            };

            var wsAttiRequest = new WSAttiLeggiDeterminaRequest
            {
                IdDocumento = request.IdDocumento.ToString(),
                Anno = request.Anno.HasValue ? request.Anno.Value.ToString() : null,
                Numero = request.Numero
            };

            var service = new WSAttiFactory().Build(configurazione);

            var response = service.LeggiDetermina(wsAttiRequest);

            return LeggiDeterminaResponse.FromWSAttiLeggiDeterminaResponse(response);
        }

        [Route("determina-fascicolata/{codiceComune}")]
        [HttpPost]
        public FascicolaDeterminaResponse DeterminaFascicolata(string idComuneAlias, string software, string codiceComune, DeterminaFascicolataRequest request)
        {
            var authInfo = this._authenticationManager.GetTokenApplicativo(idComuneAlias);

            var response = this._protocolloServiceCreator.Call(ws => ws.IsFascicolato(authInfo.Token, request.IdDocumento.ToString(), null, null, software, codiceComune));
            return FascicolaDeterminaResponse.FromDatiProtocolloFascicolato(response);
        }

        [Route("aggiungi-allegato/{codiceComune}")]
        [HttpPost()]
        public AggiungiAllegatoResponse AggiungiAllegato(string idComuneAlias, string software, string codiceComune, AggiungiAllegatoRequest request)
        {
            var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneWSAtti>(idComuneAlias, software, codiceComune);

            var configurazione = new WSAttiConfigurazione
            {
                TipoConnettore = verticalizzazione.TipoConnettore,
                Url = verticalizzazione.Url,
                Utente = verticalizzazione.Utente,
                Ruolo = verticalizzazione.Utente,
                CodiceAmministrazione = verticalizzazione.Utente
            };

            var wsAttiRequest = new WSAttiAggiungiAllegatoRequest
            {
                AllegatoPrincipale = request.Principale,
                IdDocumento = request.IdDocumento,
                Image = request.Image,
                NomeAllegato = request.NomeAllegato,
                Serial = request.Serial,
                TipoFile = request.TipoFile,
                Utente = configurazione.Utente
            };

            var service = new WSAttiFactory().Build(configurazione);

            var response = service.AggiungiAllegato(wsAttiRequest);

            return AggiungiAllegatoResponse.FromWSAttiAggiungiAllegatoResponse(response);
        }

        [Route("elenco-firmatari/{codiceComune}")]
        [HttpPost()]
        public ElencoFirmatariResponse ElencoFirmatari(string idComuneAlias, string software, string codiceComune)
        {
            var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneWSAtti>(idComuneAlias, software, codiceComune);

            var configurazione = new WSAttiConfigurazione
            {
                TipoConnettore = verticalizzazione.TipoConnettore,
                Url = verticalizzazione.Url,
                UrlFirmatari = verticalizzazione.UrlFirmatari,
                Utente = verticalizzazione.Utente,
                Ruolo = verticalizzazione.Utente,
                CodiceAmministrazione = verticalizzazione.Utente
            };

            var service = new WSAttiFactory().Build(configurazione);

            var response = service.ElencoFirmatari();

            return ElencoFirmatariResponse.FromWsAttiElencoFirmatariResponse(response);
        }
    }
}