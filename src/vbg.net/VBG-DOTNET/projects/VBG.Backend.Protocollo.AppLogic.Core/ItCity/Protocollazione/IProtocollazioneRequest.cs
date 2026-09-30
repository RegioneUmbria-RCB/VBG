using ItCityService;

namespace VBG.Backend.Protocollo.AppLogic.Core.ItCity.Protocollazione
{
    public interface IProtocollazioneRequest
    {
        CoordinateArchivio Coordinate { get; }
        RecapitoInterno MittenteInternoInfo { get; }
        RecapitoEsterno[] MittentiEsterniInfo { get; }
        DestinatarioInterno[] DestinatariInterniInfo { get; }
        DestinatarioEsterno[] DestinatariEsterniInfo { get; }
        Allegato[] Allegati { get; }
        IEnumerable<byte[]> AllegatiBuffer { get; }
    }
}
