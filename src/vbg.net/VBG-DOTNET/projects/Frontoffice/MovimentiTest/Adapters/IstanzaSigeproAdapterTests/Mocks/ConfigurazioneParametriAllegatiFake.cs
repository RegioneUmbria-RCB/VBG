using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.Adapters.IstanzaSigeproAdapterTests.Mocks
{
    public class ConfigurazioneParametriAllegatiFake : IConfigurazione<ParametriAllegati>
    {
        public ParametriAllegati Parametri => new ParametriAllegati(10000, "AAAAAAAHHHHHH", "Delega a trasmettere");
    }
}
