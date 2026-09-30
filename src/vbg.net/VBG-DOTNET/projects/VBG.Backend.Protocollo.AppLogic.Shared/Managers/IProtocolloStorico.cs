using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{
    public interface IProtocolloStorico
    {
        bool ProtocollazioneStoricaAttiva { get; }
        DateTime DataUltimaProtocollazione { get; }
        DatiProtocolloLettoResponseType StoricoLeggiProtocolloConData(string idProtocollo, int annoProtocollo, string numeroProtocollo);
        AllegatoResponseType StoricoLeggiAllegato(string IdProtocollo, string numProtocollo, string annoProtocollo, string idAllegato);
    }
}
