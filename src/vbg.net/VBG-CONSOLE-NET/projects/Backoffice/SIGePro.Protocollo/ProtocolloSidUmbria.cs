using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.SidUmbria;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.SidUmbria.Protocollazione;
using Init.SIGePro.Protocollo.ProtocolloSidUmbriaService;
using Init.SIGePro.Manager.Verticalizzazioni;
using System.Threading.Tasks;
using System.Threading;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_SIDUMBRIA : ProtocolloBase
    {

        public override DatiProtocolloRes Protocollazione(Data.DatiProtocolloIn protoIn)
        {
            try
            {
                var vert = new VerticalizzazioniConfiguration(new VerticalizzazioneProtocolloSidumbria(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

                var requestAdapter = new RequestAdapter(datiProto, vert, this.DatiProtocollo, Provenienza);
                var request = requestAdapter.Adatta();

                var service = new ProtocolloService(vert, datiProto.Uo, datiProto.Ruolo, _protocolloLogs, _protocolloSerializer);
                service.Protocolla(request);

                identificatore response = null;

                for (int i = 0; i < 10; i++)
                {
                    Thread.Sleep(2000);
                    _protocolloLogs.InfoFormat("TENTATIVO N. {0} DI LETTURA DEGLI ESTREMI DI PROTOCOLLO DELLA RICHIESTA {1}", i+1, request.idRichiesta);
                    response = service.LeggiEstremi(request.idRichiesta);
                    if (response != null)
                        break;
                }

                return ResponseAdapter.Adatta(response, request.idRichiesta, _protocolloLogs);
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE, ERRORE: {0}", ex.Message), ex);
            }
        }
    }
}
