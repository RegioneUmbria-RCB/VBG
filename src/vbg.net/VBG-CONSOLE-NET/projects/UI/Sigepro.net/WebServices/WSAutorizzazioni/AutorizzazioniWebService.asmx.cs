using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Sigepro.net.WebServices.WsSIGePro;
using Init.SIGePro.Manager.Logic.GestioneMercati;

namespace Sigepro.net.WebServices.WSAutorizzazioni
{
    /// <summary>
    /// Summary description for Autorizzazioni
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class AutorizzazioniWebService : SigeproWebService
    {

        [WebMethod]
        public List<ListaAutorizzazioniItem> GetAutorizzazioni(string token, string[] registri, int codiceAnagrafe, string espressioneFormattazioneDati, int codiceManifestazione, int? codiceUso)
        {
            var ai = CheckToken(token);

            var a = new AutorizzazioniService( ai );
            return a.GetAutorizzazioni(registri, codiceAnagrafe, espressioneFormattazioneDati, codiceManifestazione, codiceUso);
        }

        [WebMethod]
        public List<ListaAutorizzazioniItem> GetAutorizzazioniConCodiceIntervento(string token, string[] registri, int codiceAnagrafe, string espressioneFormattazioneDati, int codiceIntervento)
        {
            var ai = CheckToken(token);

            var a = new AutorizzazioniService(ai);
            return a.GetAutorizzazioniConCodiceIntervento(registri, codiceAnagrafe, espressioneFormattazioneDati, codiceIntervento);
        }

        [WebMethod]
        public DettagliAutorizzazione GetAutorizzazione(string token, int idAutorizzazione, int codiceManifestazione, int? codiceUso)
        {
            var ai = CheckToken(token);

            var a = new AutorizzazioniService(ai);
            return a.GetAutorizzazione(idAutorizzazione, codiceManifestazione, codiceUso);
        }

        [WebMethod]
        public DettagliAutorizzazione GetAutorizzazioneConCodiceIntervento(string token, int idAutorizzazione, int codiceIntervento )
        {
            var ai = CheckToken(token);

            var a = new AutorizzazioniService(ai);
            return a.GetAutorizzazioneConCodiceIntervento(idAutorizzazione, codiceIntervento );
        }

        [WebMethod]
        public List<EnteAutorizzazione> GetEnti(string token)
        {
            var ai = CheckToken(token);

            var a = new AutorizzazioniService(ai);
            return a.GetEnti();

        }        
    }
}
