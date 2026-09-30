namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione
{

    public class ConfigurazionePerComune : IConfigurazionePerComune
    {
        public string PaginaIniziale { get; set; } = "";

        public string ProcessFile { get; set; } = "";

        public bool ForzaUsoArCore => false;

        public bool UsaPresentazioneDomandeCore => false;
    }
}
