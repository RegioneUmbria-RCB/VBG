using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Archiflow.Protocollazione
{
    public interface IProtocollazioneArchiflow
    {
        DatiProtocolloResponseType Protocolla();
        Guid GuidCardProtocollo { get; }
    }
}
