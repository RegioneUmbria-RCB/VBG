using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.DTO.Pagamenti;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;

// using System.ServiceModel;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.pagamenti
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsNodoPagamenti" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsNodoPagamenti.svc or WsNodoPagamenti.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsNodoPagamentiService : WcfServiceBase, IWsNodoPagamentiService
    {
        private readonly IVerticalizzazioniFactory verticalizzazioniFactory;
        public WsNodoPagamentiService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver, IVerticalizzazioniFactory verticalizzazioniFactory) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
            this.verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public ConfigurazionePagamentiNodoPagamenti GetConfigurazione(string token, string software, string codiceComune)
        {
            var authInfo = this.CheckToken(token);

            var parVertPagamentiNodoPagamenti = this.verticalizzazioniFactory.Create<VerticalizzazionePagamentiNodoPagamenti>(authInfo.Alias, software, codiceComune);

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

