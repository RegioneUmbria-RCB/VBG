namespace VBG.Backend.Protocollo.AppLogic.Core.Halley2.Fascicolazione
{
    public interface IFascicolazioneResolver
    {
        string UrlFascicolaProtocollo { get; }
        string UrlServiziAggiuntivi { get; }
        string Username { get; }
        string Password { get; }
    }
}
