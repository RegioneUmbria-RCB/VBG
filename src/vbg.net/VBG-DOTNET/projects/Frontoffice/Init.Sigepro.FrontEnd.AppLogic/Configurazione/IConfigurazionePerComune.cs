namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione
{
    public interface IConfigurazionePerComune
    {
        string PaginaIniziale { get; }
        string ProcessFile { get; }
        bool ForzaUsoArCore { get; }
        bool UsaPresentazioneDomandeCore { get; }
    }
}
