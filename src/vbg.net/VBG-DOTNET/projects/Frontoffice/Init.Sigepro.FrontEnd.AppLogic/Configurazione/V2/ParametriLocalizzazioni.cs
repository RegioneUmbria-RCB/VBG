using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriLocalizzazioni: IParametriConfigurazione
    {
        public readonly bool UsaCiviciNumerici;
        public readonly bool UsaEsponentiNumerici;

        public ParametriLocalizzazioni(bool usaCiviciNumerici, bool usaEsponentiNumerici)
        {
            this.UsaCiviciNumerici = usaCiviciNumerici;
            this.UsaEsponentiNumerici = usaEsponentiNumerici;
        }
    }
}
