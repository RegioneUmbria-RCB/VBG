using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;

namespace VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.DatiConfigurazioneProtocollo
{
    public class DatiConfigurazioneProtocolloFactory
    {
        public static IDatiConfigurazioneInterventoProtocollo Create(TipoProvenienza provenienza, ResolveDatiProtocollazioneService datiProtoBase, IDatiProtocollo datiProto, VerticalizzazioniServiceWrapper vert, ProtocolloLogs logs)
        {
            if (provenienza == TipoProvenienza.ONLINE && datiProtoBase.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                return new DatiConfigurazioneProtocolloOnline(datiProto, vert, datiProtoBase.Db, datiProtoBase.CodiceIstanza, datiProtoBase.IdComune, datiProtoBase.Software, datiProtoBase.CodiceComune, logs);
            }
            else
            {
                return new DatiConfigurazioneProtocolloDefault(datiProto, vert, logs);
            }
        }
    }
}
