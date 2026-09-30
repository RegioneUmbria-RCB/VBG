using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;

namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP
{
    public class LdpProxyServiceWrapperFactory : ILdpProxyServiceWrapperFactory
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IAuthenticationInfoResolver _authenticationInfoResolver;

        public LdpProxyServiceWrapperFactory(IVerticalizzazioniFactory verticalizzazioniFactory, IAuthenticationInfoResolver authenticationInfoResolver)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._authenticationInfoResolver = authenticationInfoResolver;
        }

        public ILdpAnnullamentoServiceWrapper GetAnnullamentoService(string software)
        {

            var verticalizzazioneSitLdp = this.GetVerticalizzazione(software);
            var serviceUrl = verticalizzazioneSitLdp.UrlServizioDomande;
            var serviceUserName = verticalizzazioneSitLdp.Username;
            var servicePassword = verticalizzazioneSitLdp.Password;

            return new LdpProxyServiceWrapper(serviceUrl, serviceUserName, servicePassword);
        }

        protected VerticalizzazioneSitLdp GetVerticalizzazione(string software)
        {
            return this._verticalizzazioniFactory.Create<VerticalizzazioneSitLdp>(this._authenticationInfoResolver.Resolve().Alias, software);
        }
    }
}
