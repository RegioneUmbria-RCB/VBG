using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

namespace Init.Sigepro.FrontEnd.AppLogicTests.TestSegnapostoSchedeDinamiche
{
    internal class MockConfigurazioneVbgRepository : IConfigurazioneVbgRepository
    {
        public Configurazione LeggiConfigurazioneComune(string software) => new Configurazione();
    }
}
