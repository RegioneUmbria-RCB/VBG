using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.RicercaPratiche;
using System.ServiceModel.Activation;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.RicercaPratiche
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "WsRicercaPraticheService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select WsRicercaPraticheService.svc or WsRicercaPraticheService.svc.cs at the Solution Explorer and start debugging.
    [AspNetCompatibilityRequirements(RequirementsMode = AspNetCompatibilityRequirementsMode.Allowed)]
    public class WsRicercaPraticheService : WcfServiceBase, IWsRicercaPraticheService
    {
        public WsRicercaPraticheService(IAuthenticationManager authenticationManager, ITransientAuthenticationInfoResolver transientAuthenticationInfoResolver) : base(authenticationManager, transientAuthenticationInfoResolver)
        {
        }

        public RisultatoRicercaPratiche RicercaPraticaDaEstremiProtocollo(string token, string software, string codiceComune, string numeroProtocollo, DateTime? dataProtocollo, string cfUtenteRicerca)
        {
            if (string.IsNullOrEmpty(token))
            {
                throw new ArgumentException($"'{nameof(token)}' cannot be null or empty.", nameof(token));
            }

            if (string.IsNullOrEmpty(codiceComune))
            {
                throw new ArgumentException($"'{nameof(codiceComune)}' cannot be null or empty.", nameof(codiceComune));
            }

            if (string.IsNullOrEmpty(software))
            {
                throw new ArgumentException($"'{nameof(software)}' cannot be null or empty.", nameof(software));
            }

            if (string.IsNullOrEmpty(numeroProtocollo))
            {
                throw new ArgumentException($"'{nameof(numeroProtocollo)}' cannot be null or empty.", nameof(numeroProtocollo));
            }

            if (dataProtocollo is null)
            {
                throw new ArgumentNullException(nameof(dataProtocollo));
            }

            if (string.IsNullOrEmpty(cfUtenteRicerca))
            {
                throw new ArgumentException($"'{nameof(cfUtenteRicerca)}' cannot be null or empty.", nameof(cfUtenteRicerca));
            }

            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var svc = new RicercaPraticheService(db, authInfo.IdComune);

                return svc.RicercaPraticaDaEstremiProtocollo(software, codiceComune, numeroProtocollo, dataProtocollo.Value, cfUtenteRicerca);
            }
        }

        public RisultatoRicercaPratiche RicercaPraticaDaNumeroIstanza(string token, string software, string codiceComune, string numeroIstanza, string cfUtenteRicerca)
        {
            if (string.IsNullOrEmpty(token))
            {
                throw new ArgumentException($"'{nameof(token)}' cannot be null or empty.", nameof(token));
            }

            if (string.IsNullOrEmpty(codiceComune))
            {
                throw new ArgumentException($"'{nameof(codiceComune)}' cannot be null or empty.", nameof(codiceComune));
            }

            if (string.IsNullOrEmpty(software))
            {
                throw new ArgumentException($"'{nameof(software)}' cannot be null or empty.", nameof(software));
            }

            if (string.IsNullOrEmpty(numeroIstanza))
            {
                throw new ArgumentException($"'{nameof(numeroIstanza)}' cannot be null or empty.", nameof(numeroIstanza));
            }

            if (string.IsNullOrEmpty(cfUtenteRicerca))
            {
                throw new ArgumentException($"'{nameof(cfUtenteRicerca)}' cannot be null or empty.", nameof(cfUtenteRicerca));
            }


            var authInfo = this.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var svc = new RicercaPraticheService(db, authInfo.IdComune);

                return svc.RicercaPraticaDaNumeroIstanza(software, codiceComune, numeroIstanza, cfUtenteRicerca);
            }
        }
    }
}
