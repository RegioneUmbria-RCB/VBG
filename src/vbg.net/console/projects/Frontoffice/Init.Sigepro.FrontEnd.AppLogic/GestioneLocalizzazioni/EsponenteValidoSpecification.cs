using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.Infrastructure;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni
{
    public class EsponenteValidoSpecification : ISpecification<string>
    {
        IConfigurazione<ParametriLocalizzazioni> _cfg;

        public EsponenteValidoSpecification(IConfigurazione<ParametriLocalizzazioni> cfg)
        {
            this._cfg = cfg;
        }

        public bool IsSatisfiedBy(string valoreEsponente)
        {
            if (!this._cfg.Parametri.UsaCiviciNumerici)
            {
                return true;
            }

            if (String.IsNullOrEmpty(valoreEsponente))
            {
                return true;
            }

            var test = 0;

            return Int32.TryParse(valoreEsponente, out test);
        }
    }
}
