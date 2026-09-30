using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Tinn.Verticalizzazioni;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Tinn.Services;
using Init.SIGePro.Protocollo.Tinn.Protocollazione.Segnatura;
using Init.SIGePro.Protocollo.Tinn.Protocollazione;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_TINN : ProtocolloBase
    {

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloTinn(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                var srv = new ProtocolloService(vert.Url, ProxyAddress, vert.Username, _protocolloLogs, _protocolloSerializer);
                var adapter = new SegnaturaAdapter(datiProto, protoIn.Allegati, _protocolloLogs, _protocolloSerializer, vert, srv);
                var response = adapter.Adatta();

                return new ResponseAdapter(response, _protocolloLogs).Adatta();
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex);
            }
        }

    }
}
