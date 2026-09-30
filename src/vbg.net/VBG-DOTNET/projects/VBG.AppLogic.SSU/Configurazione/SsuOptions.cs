
namespace VBG.AppLogic.SSU.Configurazione
{
    public class SsuOptions
    {
        public const string SectionName = "SSU";

        public bool Abilitato { get; set; } = false;
        public string BaseUrlApi { get; set; } = string.Empty;
        public string SignalRHubUrl { get; set; } = string.Empty;
    }
}
