using Init.SIGePro.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Data.People;
using Init.SIGePro.Exceptions.Token;
using Init.SIGePro.Manager.DTO.Configurazione;
using Init.SIGePro.Manager.Logic.GestioneSit;
using Init.SIGePro.Manager.Logic.ServiziConsole;
using Init.SIGePro.Manager.Logic.ServiziConsole.GestioneEndoprocedimenti;
using Init.SIGePro.Manager.Verticalizzazioni;
using Microsoft.JScript;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace Sigepro.net.WebServices.WsAreaRiservata
{

    [WebService(Namespace = "http://init.sigepro.it")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    public class SitConsoleService : System.Web.Services.WebService
    {
        [WebMethod]
        public List<ConfigurazioneSitDto> GetActiveSitConfigurations(string alias, string token, string software)
        {
            var authResult = AuthenticationManager.CheckToken(token);

            if (authResult == null)
                throw new ArgumentException("Token non valido: " + token);

            var ret = new List<ConfigurazioneSitDto>();

            using (var db = authResult.CreateDatabase())
            {
                var comuni = new SitConsoleDbService(db).ListCodiciComuniSitAttivi(authResult.Alias);

                foreach (var codiceComune in comuni)
                {
                    var verticalizzazioneSitConsole = new VerticalizzazioneSitConsole(alias, software, codiceComune);

                    if (verticalizzazioneSitConsole.Attiva)
                    {
                        ret.Add(new ConfigurazioneSitDto
                        {
                            Attivo = true,
                            UrlWsSit = verticalizzazioneSitConsole.UrlWssit,
                            AliasBackendLocale = verticalizzazioneSitConsole.AliasBackendLocale,
                            CodiceComune = codiceComune,
                            ForzaStepLocalizzazioniSit = verticalizzazioneSitConsole.RedirectToSitPage
                        });
                    }
                }
            }            

            return ret;
        }
    }
}
