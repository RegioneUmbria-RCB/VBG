using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriPhantomjs : IParametriConfigurazione
    {
        public readonly string PhantomjsPath;

        public ParametriPhantomjs(string phantomjsPath)
        {
            this.PhantomjsPath = phantomjsPath;
        }
    }
}
