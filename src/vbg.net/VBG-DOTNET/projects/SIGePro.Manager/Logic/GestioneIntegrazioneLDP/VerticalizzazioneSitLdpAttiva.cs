using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.Verticalizzazioni;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;

namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP
{
    public class VerticalizzazioneSitLdpAttiva : IVerticalizzazioneAttiva<VerticalizzazioneSitLdp>
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IAuthenticationInfoResolver _authInfoResolver;

        public VerticalizzazioneSitLdpAttiva(IVerticalizzazioniFactory verticalizzazioniFactory, IAuthenticationInfoResolver authInfoResolver)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._authInfoResolver = authInfoResolver;
        }

        public bool IsAttiva(string software)
        {
            var authenticationInfo = this._authInfoResolver.Resolve();

            return this._verticalizzazioniFactory.Create<VerticalizzazioneSitLdp>(authenticationInfo.Alias, software).Attiva;
        }
    }
}
