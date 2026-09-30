namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes
{
    public interface IParametriService
    {
        VersioneEnum Versione { get; }
        string UserName { get; }
        string Url { get; }
        string Password { get; }
        long? IstatEnte { get; }
        string UfficioProtocollante { get; }
        bool UsaRuoloInEntrata { get; }
        bool UsaRuoloInUscita { get; }
    }
}
