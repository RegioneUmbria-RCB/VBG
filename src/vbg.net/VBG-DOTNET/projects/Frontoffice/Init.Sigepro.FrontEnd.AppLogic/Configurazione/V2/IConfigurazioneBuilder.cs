namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public interface IConfigurazioneBuilder<T> where T : class, IParametriConfigurazione
    {
        T Build();
    }
}
