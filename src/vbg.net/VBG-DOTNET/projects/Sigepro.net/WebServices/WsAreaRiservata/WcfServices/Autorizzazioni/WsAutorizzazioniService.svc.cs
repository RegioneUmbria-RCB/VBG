using Init.SIGePro.Manager.Logic.GestioneMercati;
using System.Collections.Generic;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Autorizzazioni
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsAutorizzazioniService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsAutorizzazioniService.svc or WsAutorizzazioniService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsAutorizzazioniService : WcfServiceBase, IWsAutorizzazioniService
    {
        public List<ListaAutorizzazioniItem> GetAutorizzazioni(string token, string[] registri, int codiceAnagrafe, string espressioneFormattazioneDati, int codiceManifestazione, int? codiceUso)
        {
            var ai = this.CheckToken(token);

            var a = new AutorizzazioniService(ai);
            return a.GetAutorizzazioni(registri, codiceAnagrafe, espressioneFormattazioneDati, codiceManifestazione, codiceUso);
        }

        public List<ListaAutorizzazioniItem> GetAutorizzazioniConCodiceIntervento(string token, string[] registri, int codiceAnagrafe, string espressioneFormattazioneDati, int codiceIntervento)
        {
            var ai = this.CheckToken(token);

            var a = new AutorizzazioniService(ai);
            return a.GetAutorizzazioniConCodiceIntervento(registri, codiceAnagrafe, espressioneFormattazioneDati, codiceIntervento);
        }

        public DettagliAutorizzazione GetAutorizzazione(string token, int idAutorizzazione, int codiceManifestazione, int? codiceUso)
        {
            var ai = this.CheckToken(token);

            var a = new AutorizzazioniService(ai);
            return a.GetAutorizzazione(idAutorizzazione, codiceManifestazione, codiceUso);
        }

        public DettagliAutorizzazione GetAutorizzazioneConCodiceIntervento(string token, int idAutorizzazione, int codiceIntervento)
        {
            var ai = this.CheckToken(token);

            var a = new AutorizzazioniService(ai);
            return a.GetAutorizzazioneConCodiceIntervento(idAutorizzazione, codiceIntervento);
        }

        public List<EnteAutorizzazione> GetEnti(string token)
        {
            var ai = this.CheckToken(token);

            var a = new AutorizzazioniService(ai);
            return a.GetEnti();

        }

    }
}
