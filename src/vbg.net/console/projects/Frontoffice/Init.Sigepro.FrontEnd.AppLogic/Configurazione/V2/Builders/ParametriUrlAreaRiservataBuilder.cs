using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2.Builders
{
    public class ParametriUrlAreaRiservataBuilder : IConfigurazioneBuilder<ParametriUrlAreaRiservata>
    {
        public ParametriUrlAreaRiservata Build()
        {
            return new ParametriUrlAreaRiservata();
        }
    }
}
