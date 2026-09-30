using Init.Sigepro.FrontEnd.AppLogic.Configurazione;

namespace Init.Sigepro.FrontEnd.CoreServices.Configurazione
{
    public class ConfigurazionePerComune : IConfigurazionePerComune
    {
        public const string SectionName = "ConfigurazionePerComune";

        public string? PaginaIniziale { get; set; }

        public string? ProcessFile { get; set; }

        public bool ForzaUsoArCore { get; set; } = false;

        public bool UsaPresentazioneDomandeCore { get; set; } = false;
    }
}
