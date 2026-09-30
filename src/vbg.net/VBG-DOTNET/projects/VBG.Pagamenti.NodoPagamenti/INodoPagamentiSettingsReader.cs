namespace VBG.Pagamenti.NodoPagamenti
{
    public interface INodoPagamentiSettingsReader
    {
        NodoPagamentiSettings Read(string codiceComune);
    }
}
