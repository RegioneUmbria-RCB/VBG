namespace Init.SIGePro.Manager.Configuration
{
    public interface IConfigurazioneGenerale
    {
        string GetApplicationInfoValue(string param);
        string AppJava { get; }
    }
}
