using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Manager.Logic.PagamentiESED;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.Pagamenti;
using Init.SIGePro.Manager.Verticalizzazioni;

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
        /*
        [WebMethod]
        public ConfigurazionePagamentiNodoPagamenti GetParametriConfigurazioneNodoPagamenti(string token, string software, string codiceComune)
        {
            var authInfo = CheckToken(token);

            var parVertPagamentiNodoPagamenti = new VerticalizzazionePagamentiNodoPagamenti(authInfo.Alias, software, codiceComune);

            if (!parVertPagamentiNodoPagamenti.Attiva)
            {
                return new ConfigurazionePagamentiNodoPagamenti();
            }

            return new ConfigurazionePagamentiNodoPagamenti
            {
                NodoPagamentiAttivo = true,
                CodiceFiscaleEnteCreditore = parVertPagamentiNodoPagamenti.CodiceFiscaleEnteCreditore,
                UrlBack = parVertPagamentiNodoPagamenti.UrlBack,
                UrlRitorno = parVertPagamentiNodoPagamenti.UrlRitorno,
                UrlWs = parVertPagamentiNodoPagamenti.UrlWs,
                IdModalitaPagamento = parVertPagamentiNodoPagamenti.IdModalitaPagamento,
                SoggettoPendenza = parVertPagamentiNodoPagamenti.SoggettoPendenza
            };
        }
        */
    }
}