using Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference;
using Init.SIGePro.Manager.DTO.Configurazione;

namespace Init.Sigepro.FrontEnd.AppLogicTests.Adapters.IstanzaSigeproAdapterTests.Mocks
{
    public class ConfigurazioneAreaRiservataRepositoryFake : IConfigurazioneAreaRiservataRepository
    {
        public ConfigurazioneAreaRiservataDto DatiConfigurazione(string idComune, string software)
        {
            return new ConfigurazioneAreaRiservataDto
            {
                StatoInizialeIstanza = "STATO_INIZIALE"
            };
        }
    }
}
