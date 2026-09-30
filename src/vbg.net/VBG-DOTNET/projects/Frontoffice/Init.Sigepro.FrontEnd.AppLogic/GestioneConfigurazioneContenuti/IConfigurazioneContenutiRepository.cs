using Init.SIGePro.Manager.DTO.Configurazione;
namespace Init.Sigepro.FrontEnd.AppLogic.GestioneConfigurazioneContenuti
{
    public interface IConfigurazioneContenutiRepository
    {
        ConfigurazioneContenutiDto GetConfigurazione(string alias, string software);
    }
}
