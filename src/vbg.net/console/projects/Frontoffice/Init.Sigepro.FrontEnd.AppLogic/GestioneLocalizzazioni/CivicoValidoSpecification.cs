using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni
{
    public class CivicoValidoSpecification : ISpecification<string>
    {
        IConfigurazione<ParametriLocalizzazioni> _cfg;

        public CivicoValidoSpecification(IConfigurazione<ParametriLocalizzazioni> cfg)
        {
            this._cfg = cfg;
        }

        public bool IsSatisfiedBy(string valoreCivico)
        {
            if (!this._cfg.Parametri.UsaCiviciNumerici)
            {
                return true;
            }

            if (String.IsNullOrEmpty(valoreCivico))
            {
                return true;
            }

            var test = 0;

            return Int32.TryParse(valoreCivico, out test);
        }
    }
}
