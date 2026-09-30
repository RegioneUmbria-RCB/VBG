using Init.SIGePro.Manager.DTO.Configurazione;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.ServiceReference
{
    public interface IConfigurazioneAreaRiservataRepository
    {
        ConfigurazioneAreaRiservataDto DatiConfigurazione(string idComune, string software);
    }
}
