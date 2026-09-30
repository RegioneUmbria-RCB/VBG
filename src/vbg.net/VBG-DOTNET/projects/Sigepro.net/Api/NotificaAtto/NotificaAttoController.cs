using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.GestioneAutorizzazioni;
using log4net;
using Sigepro.net.Api.NotificaFirma;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Linq;
using System.Web.Http;
using WSAtti;

namespace Sigepro.net.Api.NotificaAtto
{
    [RoutePrefix("web-api/notificaatto/{idComuneAlias}/{software}")]
    public class NotificaAttoController : ApiController
    {
        private readonly ILog _logger = LogManager.GetLogger("NotificaAttoController");
        private readonly IAuthenticationManager _authenticationManager;
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public NotificaAttoController(IAuthenticationManager authenticationManager, IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._authenticationManager = authenticationManager;
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        [HttpPost()]
        [Route("notifica-firma/{codiceComune}")]
        public NotificaFirmaResponse NotificaFirma(string idComuneAlias, string software, string codiceComune, NotificaFirmaRequest request)
        {
            try
            {
                //1. autenticazione con token applicativo
                this._logger.Debug("Inizio NotificaFirma di un atto");

                var authInfo = this._authenticationManager.GetTokenApplicativo(idComuneAlias);
                this._logger.Debug($"Token:{authInfo.Token}");

                var autService = new AutorizzazioniService(authInfo);
                var autorizzazione = autService.GetAutorizzazioneDaFkIdProtocollo(request.IdDocumento);

                if (autorizzazione == null)
                {
                    throw new Exception($"Non è presente nessun atto collegato all'identificativo {request.IdDocumento} fornito!");
                }

                //2. rilettura dei dati completi dell'atto
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
                    IdDocumento = request.IdDocumento
                };

                var service = new WSAttiFactory().Build(configurazione);

                var response = service.LeggiDetermina(wsAttiRequest);

                this._logger.Debug($"LeggiDetermina per id {request.IdDocumento}");

                //3. Sostituzione degli allegati presenti nell'atto ( vengono storicizzati ) con quelli ricevuti dal protocollo ( firmati )
                using (var db = authInfo.CreateDatabase())
                {
                    var oggettiMgr = new OggettiMgr(db);
                    response.Allegati.ToList().ForEach(x =>
                    {
                        if (!String.IsNullOrEmpty(x.IdVBG))
                        {
                            oggettiMgr.AggiornaCorpoOggetto(authInfo.IdComune, Convert.ToInt32(x.IdVBG), x.Nome, x.Image);
                        }
                        else
                        {
                            var oggetto = oggettiMgr.Insert(authInfo.IdComune, "", x.Nome, x.Image);

                            var idAutorizzazione = Convert.ToInt32(autorizzazione.ID);
                            var codiceOggetto = Convert.ToInt32(oggetto.CODICEOGGETTO);

                            autService.RegistraNuovoDocumentoInAutorizzazione(idAutorizzazione, codiceOggetto, x.IdEsterno, false);

                        }
                    });
                }


                //4. response OK
                this._logger.Debug("Fine NotificaFirma");
                return NotificaFirmaResponse.OK();
            }
            catch (Exception ex)
            {
                this._logger.Error(ex.Message, ex);
                return NotificaFirmaResponse.KO(ex.Message);
            }
        }
    }
}