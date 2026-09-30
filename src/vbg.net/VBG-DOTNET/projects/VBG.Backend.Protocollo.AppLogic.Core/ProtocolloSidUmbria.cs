using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System.Collections.Generic;
using System.Threading;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Core.SidUmbria;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_SIDUMBRIA : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_SIDUMBRIA(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(Shared.Data.DatiProtocolloIn protoIn)
        {

            var vert = new VerticalizzazioniConfiguration(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSidumbria>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var factoryRequest = RequestAdapterFactory.Create(datiProto, vert, this.DatiProtocollo, Provenienza);

            var request = factoryRequest.Adatta();

            var service = new ProtocolloService(vert, factoryRequest.Token, factoryRequest.Service, _protocolloLogs, _protocolloSerializer, this._bindingFactory);
            service.Protocolla(request);

            identificatore response = null;

            for (int i = 0; i < 10; i++)
            {
                Thread.Sleep(2000);
                _protocolloLogs.Info($"TENTATIVO N. {i + 1} DI LETTURA DEGLI ESTREMI DI PROTOCOLLO DELLA RICHIESTA {request.idRichiesta}");
                response = service.LeggiEstremi(request.idRichiesta);
                if (response != null)
                    break;
            }

            return ResponseAdapter.Adatta(response, request.idRichiesta, _protocolloLogs);
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var vert = new VerticalizzazioniConfiguration(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloSidumbria>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune), _protocolloLogs);

            var service = new ProtocolloService(vert, base.Uo, base.Ruolo, _protocolloLogs, _protocolloSerializer, this._bindingFactory);
            var response = service.LeggiEstremi(leggiProtocolloRequest.IdProtocollo);

            return new List<DatiProtocolloLettoResponseType>() { new DatiProtocolloLettoResponseType
                {
                    NumeroProtocollo = response == null ? ProtocolloSidUmbriaConstants.SeparatoreCodaNumeroProtocollo : response.numero.ToString(),
                    DataProtocollo = response == null ? "" : response.data,
                    AnnoProtocollo = response == null ? leggiProtocolloRequest.AnnoProtocollo : response.anno.ToString()
                }
            };

        }
    }


}
