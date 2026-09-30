using Init.SIGePro.Manager.DTO.Pagamenti;
using Init.SIGePro.Manager.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Activation;
using System.Text;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.pagamenti
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsNodoPagamenti" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsNodoPagamenti.svc or WsNodoPagamenti.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsNodoPagamentiService : WcfServiceBase, IWsNodoPagamentiService
    {
        public ConfigurazionePagamentiNodoPagamenti GetConfigurazione(string token, string software, string codiceComune)
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
                SoggettoPendenza = parVertPagamentiNodoPagamenti.SoggettoPendenza,
                PagoDopoAttivo = parVertPagamentiNodoPagamenti.ArAttivaPagoDopo,
                PagoDopoGGScadenza = parVertPagamentiNodoPagamenti.ArPagoDopoGGScadenza
            };
        }
    }
}

