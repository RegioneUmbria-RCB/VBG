using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione.Segnatura;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Core.Tinn.Protocollazione;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_TINN : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_TINN(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloTinn>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                var srv = new ProtocolloService(vert.Url, ProxyAddress, vert.Username, _protocolloLogs, _protocolloSerializer, this._bindingFactory);
                var adapter = new SegnaturaAdapter(datiProto, protoIn.RecuperaAllegati().ToList(), _protocolloLogs, _protocolloSerializer, vert, srv);
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
