using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.CoreServices.Autenticazione;
using Microsoft.AspNetCore.Components.Authorization;
using System.Security.Claims;

namespace AreaRiservataCore.Auth
{
    public class CustomAuthStateProvider : AuthenticationStateProvider
    {
        private readonly IAuthenticationDataStore _authenticationDataStore;
        private readonly IAuthenticationDataResolver _authenticationDataResolver;

        public CustomAuthStateProvider(IAuthenticationDataStore authenticationDataStore, IAuthenticationDataResolver authenticationDataResolver)
        {
            this._authenticationDataStore = authenticationDataStore;
            this._authenticationDataResolver = authenticationDataResolver;
        }

        public override async Task<AuthenticationState> GetAuthenticationStateAsync()
        {
            await Task.CompletedTask;

            if (!this._authenticationDataResolver.IsAuthenticated)
            {
                return new AuthenticationState(new ClaimsPrincipal(new ClaimsIdentity() { }));
            }
            var authResult = this._authenticationDataResolver.DatiAutenticazione;

            var claimsIdentity = new ClaimsIdentity(new[]
            {
                new Claim(ClaimTypes.Name, authResult.DatiUtente.Nome),
                new Claim(ClaimTypes.Surname, authResult.DatiUtente.Nominativo),
                new Claim("vbg.alias", authResult.Alias),
                new Claim("vbg.livelloAutenticazione", authResult.LivelloAutenticazione.ToString()),
                new Claim("vbg.utenteTester", authResult.DatiUtente.UtenteTester ? "1" : "0"),
                new Claim(ClaimTypes.Sid, authResult.Token)
            }, "Authenticated");

            var userClaimPrincipal = new ClaimsPrincipal(claimsIdentity);

            return new AuthenticationState(userClaimPrincipal);
        }

        public void Login(UserAuthenticationResult uar)
        {
            this._authenticationDataStore.Save(uar);

            this.NotifyAuthenticationStateChanged(this.GetAuthenticationStateAsync());
        }

        public void Logout()
        {
            this._authenticationDataStore.LogOut();
            this.NotifyAuthenticationStateChanged(this.GetAuthenticationStateAsync());
        }


    }
}
