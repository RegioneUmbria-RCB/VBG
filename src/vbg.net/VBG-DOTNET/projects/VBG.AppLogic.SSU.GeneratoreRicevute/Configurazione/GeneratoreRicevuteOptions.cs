namespace VBG.AppLogic.SSU.GeneratoreRicevute.Configurazione
{
    public class GeneratoreRicevuteOptions
    {
        public const string SectionName = "GeneratoreRicevute";

        public string AliasIniziale { get; set; } = string.Empty;
        public int MaxFailedAttemptsThreshold { get; set; }
    }
}
