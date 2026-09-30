using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces
{
    internal interface IProtocollazioneService
    {
        DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn);
        List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest);
        DatiProtocolloResponseType CreaCopie();
        ListaMotiviAnnullamentoResponseType GetMotivoAnnullamento();
        DatiProtocolloAnnullatoResponseType IsAnnullato(string idProtocollo, string annoProtocollo, string numeroProtocollo);
        void AnnullaProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo, string motivoAnnullamento, string noteAnnullamento);
        void CheckProtocolloLetto(string annoProtocollo, string numeroProtocollo, string idProtocollo, DatiProtocolloLettoResponseType pDatiProtocolloLetto);
    }
}
