using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Protocollo.WsDataClass;
using log4net;
using Sigepro.net.Api.NotificaFirma;
using Sigepro.net.WsProtocollo;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Linq;
using System.Web.Http;

namespace Sigepro.net.Api.NorificaFirma
{
    [RoutePrefix("web-api/notificafirma/{idComuneAlias}/{software}")]
    public class NotificaFirmaController : ApiController
    {
        private readonly ILog _logger = LogManager.GetLogger("NotificaFirmaController");
        private readonly IAuthenticationManager _authenticationManager;
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly ProtocolloServiceCreator _protocolloServiceCreator;

        public NotificaFirmaController(IAuthenticationManager authenticationManager, IVerticalizzazioniFactory verticalizzazioniFactory, ProtocolloServiceCreator protocolloServiceCreator)
        {
            this._authenticationManager = authenticationManager;
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._protocolloServiceCreator = protocolloServiceCreator;
        }

        [HttpPost()]
        [Route("notifica-firma")]
        public NotificaFirmaResponse NotificaFirma(string idComuneAlias, string software, NotificaFirmaRequest request)
        {
            try
            {
                //1. autenticazione con token applicativo
                this._logger.Debug("Inizio NotificaFirma");

                var authInfo = this._authenticationManager.GetTokenApplicativo(idComuneAlias);
                this._logger.Debug($"Token:{authInfo.Token}");

                var protocolli = this._protocolloServiceCreator.Call(ws => ws.LeggiProtocollo(new LeggiProtocolloRequest() { IdProtocollo = request.IdDocumento, Token = authInfo.Token }));

                var protocollo = protocolli.FirstOrDefault();

                if (protocollo != null)
                {
                    this._logger.Debug($"LeggiProtocollo per id {request.IdDocumento} => numero {protocollo.NumeroProtocollo}, data {protocollo.DataProtocollo}");

                    //3. aggiornamento riferimenti protocollo su istanza e/o movimento
                    using (var db = authInfo.CreateDatabase())
                    {
                        var dataProtocollo = string.IsNullOrEmpty(protocollo.DataProtocollo) ? (DateTime?)null : DateTime.Parse(protocollo.DataProtocollo);

                        new AggiornaProtocolloService(this._logger, db).AggiornaRiferimentiProtocollo(authInfo.IdComune, software, request.IdDocumento, protocollo.NumeroProtocollo, dataProtocollo);
                    }

                    //4. response OK
                    this._logger.Debug("Fine NotificaFirma");
                    return NotificaFirmaResponse.OK();
                }
                else
                {
                    var msg = "Protocollo non trovato";
                    this._logger.Debug(msg);
                    return NotificaFirmaResponse.KO(msg);
                }
            }
            catch (Exception ex)
            {
                this._logger.Error(ex.Message, ex);
                return NotificaFirmaResponse.KO(ex.Message);
            }
        }
    }
}