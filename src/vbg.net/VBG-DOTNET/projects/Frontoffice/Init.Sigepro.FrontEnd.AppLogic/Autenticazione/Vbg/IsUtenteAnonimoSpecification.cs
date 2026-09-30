using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg
{
    public class IsUtenteAnonimoSpecification: ISpecification<UserAuthenticationResult>
    {
        IConfigurazione<ParametriLogin> _configurazione;

        public IsUtenteAnonimoSpecification(IConfigurazione<ParametriLogin> configurazione)
        {
            this._configurazione = configurazione;
        }

        public bool IsSatisfiedBy(UserAuthenticationResult uar)
        {
            if (String.IsNullOrEmpty(this._configurazione.Parametri.UsernameUtenteAnonimo))
            {
                return false;
            }

            return uar.DatiUtente.Codicefiscale.ToUpperInvariant() == this._configurazione.Parametri.UsernameUtenteAnonimo.ToUpperInvariant();
        }
    }
}
