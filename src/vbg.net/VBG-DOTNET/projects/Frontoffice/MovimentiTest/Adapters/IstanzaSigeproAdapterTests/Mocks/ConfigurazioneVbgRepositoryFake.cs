using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.Adapters.IstanzaSigeproAdapterTests.Mocks
{
    public class ConfigurazioneVbgRepositoryFake : IConfigurazioneVbgRepository
    {
        public Configurazione LeggiConfigurazioneComune(string software)
        {
            return new Configurazione
            {

            };
        }
    }
}
