using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Manager.Logic.PagamentiESED;
using Init.SIGePro.Data;

namespace Sigepro.net.WebServices.WsAreaRiservata.Classes
{
	public partial class AreaRiservataServiceBase
	{
        [WebMethod]
        public EsitoNotificaPagamenti SalvaNotificaPagamentoESED(string token, string idDomanda, string numeroOperazione, string messaggioXml, string esito, string data, string idOrdine, string idTransazione, string tipoPagamento)
        {
            try
            {
                var authInfo = CheckToken(token);
                var dataBase = authInfo.CreateDatabase();
                var service = new NotificaPagamentiService(authInfo.IdComune, dataBase);
                service.Inserisci(messaggioXml, numeroOperazione, idDomanda, esito, data, idOrdine, idTransazione, tipoPagamento);

                return new EsitoNotificaPagamenti();
            }
            catch (Exception ex)
            {
                return new EsitoNotificaPagamenti(ex.Message);
            }
        }

        [WebMethod]
        public DatiNotificaPagamenti GetDatiNotifica(string token, string numeroOperazione)
        {
            try
            {
                var authInfo = CheckToken(token);
                var dataBase = authInfo.CreateDatabase();
                var service = new NotificaPagamentiService(authInfo.IdComune, dataBase);
                var notificaPagamenti = service.GetNotificaPagamentiByKey(numeroOperazione);

                if (notificaPagamenti == null)
                {
                    throw new Exception("DATI NON TROVATI");
                }

                return notificaPagamenti;
            }
            catch (Exception ex)
            {
                return new DatiNotificaPagamenti(ex.Message);
            }
        }
    }
}