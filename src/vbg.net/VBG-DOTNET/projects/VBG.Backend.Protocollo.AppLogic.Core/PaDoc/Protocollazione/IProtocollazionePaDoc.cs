namespace VBG.Backend.Protocollo.AppLogic.Core.PaDoc.Protocollazione
{
    public interface IProtocollazionePaDoc
    {
        string Codice { get; }
        string UrlUpdate { get; }
        string UrlError { get; }
    }
}
