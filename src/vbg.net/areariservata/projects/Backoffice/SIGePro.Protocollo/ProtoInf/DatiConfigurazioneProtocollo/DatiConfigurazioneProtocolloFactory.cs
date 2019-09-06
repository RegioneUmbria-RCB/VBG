using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ProtoInf.DatiConfigurazioneProtocollo
{
    public class DatiConfigurazioneProtocolloFactory
    {
        public static IDatiConfigurazioneInterventoProtocollo Create(ProtocolloEnum.TipoProvenienza provenienza, ResolveDatiProtocollazioneService datiProtoBase, IDatiProtocollo datiProto, VerticalizzazioniServiceWrapper vert, ProtocolloLogs logs)
        {
            if (provenienza == ProtocolloEnum.TipoProvenienza.ONLINE && datiProtoBase.TipoAmbito == ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
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
