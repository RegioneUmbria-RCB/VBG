namespace VBG.Backend.Protocollo.AppLogic.Core.Pal.Protocollazione
{
    public interface IProtocollazionePal
    {
        string Flusso { get; }
        MittenteType[] GetMittenti();
        DestinatariType GetDestinatari();
        AssegnatariType GetAssegnatari();
    }
}
