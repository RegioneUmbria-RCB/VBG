using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriMenuV2 : IParametriConfigurazione
    {
        public readonly int? CodiceOggettoMenu;

        public ParametriMenuV2(int? codiceOggettoMenu)
        {
            this.CodiceOggettoMenu = codiceOggettoMenu;
        }
    }
}
