using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.GestioneConsole.UrlAccesso;
using SIGePro.Manager.VerticalizzazioniBase;

// using System.ServiceModel;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Console
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsUrlAccessoConsoleService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsUrlAccessoConsoleService.svc or WsUrlAccessoConsoleService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsUrlAccessoConsoleService : WcfServiceBase, IWsUrlAccessoConsoleService
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public WsUrlAccessoConsoleService(IVerticalizzazioniFactory verticalizzazioniFactory, IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public ConfigurazioneUrlConsole GetUrlAccessoConsole(string token, string software)
        {
            var authInfo = this.CheckToken(token);

            return new UrlAccessoConsoleService(this._verticalizzazioniFactory, authInfo).GetUrlAccessoConsole(software);
        }
    }
}
