using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.AutenticazioneUtente;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneMenu;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Xunit;

namespace MovimentiTest.GestioneMenu
{
    public class StaticAuthenticationDataResolver : IAuthenticationDataResolver
    {
        public UserAuthenticationResult DatiAutenticazione { get; set; }

        public bool IsAuthenticated { get; set; }

        public StaticAuthenticationDataResolver(string token = "123-456-789", string idComune = "XXX")
        {
            this.DatiAutenticazione = new UserAuthenticationResult(token, idComune, idComune, new AnagraficaUtente
            {
            }, LivelloAutenticazioneEnum.Identificato);
        }
    }
}
