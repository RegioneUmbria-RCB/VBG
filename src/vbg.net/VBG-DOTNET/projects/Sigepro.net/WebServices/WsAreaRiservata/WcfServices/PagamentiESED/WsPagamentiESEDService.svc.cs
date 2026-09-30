using Init.SIGePro.Manager.Logic.PagamentiESED;
using System;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.PagamentiESED
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsPagamentiESEDService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsPagamentiESEDService.svc or WsPagamentiESEDService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsPagamentiESEDService : WcfServiceBase, IWsPagamentiESEDService
    {
        public EsitoNotificaPagamentiESEDDto SalvaNotificaPagamentoESED(string token, string idDomanda, string numeroOperazione, string messaggioXml, string esito, string data, string idOrdine, string idTransazione, string tipoPagamento)
        {
            try
            {
                var authInfo = this.CheckToken(token);
                var dataBase = authInfo.CreateDatabase();
                var service = new NotificaPagamentiService(authInfo.IdComune, dataBase);
                service.Inserisci(messaggioXml, numeroOperazione, idDomanda, esito, data, idOrdine, idTransazione, tipoPagamento);

                var n = new EsitoNotificaPagamenti();

                return new EsitoNotificaPagamentiESEDDto
                {
                    Errore = n.Errore,
                    Esito = n.Esito
                };
            }
            catch (Exception ex)
            {
                var n = new EsitoNotificaPagamenti(ex.Message);

                return new EsitoNotificaPagamentiESEDDto
                {
                    Errore = n.Errore,
                    Esito = n.Esito
                };
            }
        }

        public DatiNotificaPagamentiESEDDto GetDatiNotifica(string token, string numeroOperazione)
        {
            try
            {
                var authInfo = this.CheckToken(token);
                var dataBase = authInfo.CreateDatabase();
                var service = new NotificaPagamentiService(authInfo.IdComune, dataBase);
                var notificaPagamenti = service.GetNotificaPagamentiByKey(numeroOperazione);

                if (notificaPagamenti == null)
                {
                    throw new Exception("DATI NON TROVATI");
                }

                return new DatiNotificaPagamentiESEDDto
                {
                    Messaggio = notificaPagamenti.Messaggio,
                    Errore = notificaPagamenti.Errore,
                    Esito = notificaPagamenti.Esito
                };
            }
            catch (Exception ex)
            {
                var n = new DatiNotificaPagamenti(ex.Message);

                return new DatiNotificaPagamentiESEDDto
                {
                    Messaggio = n.Messaggio,
                    Errore = n.Errore,
                    Esito = n.Esito
                };
            }
        }
    }
}
