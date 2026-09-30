using Init.SIGePro.Manager.Configuration;

internal class NetCoreConfigurationProvider : IFileBasedConfigurationProvider
{
    private const string SectionName = "ConfigurazioneBackend";
    private readonly IConfigurationSection _section;

    public NetCoreConfigurationProvider(IConfiguration configuration)
    {
        this._section = configuration.GetSection(SectionName);
    }
    public string GetSetting(string setting)
    {
        return this._section.GetValue<string>(setting);
    }
}