using VBG.Shared.Infrastructure.ServiceModel;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Core.ApSystems;
using VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Allegati;
using VBG.Backend.Protocollo.AppLogic.Core.ApSystems.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.Corrispondenti.Get;
using VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.Corrispondenti.Insert;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_APSYSTEMS : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_APSYSTEMS(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloApsystems>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var corrGetSrv = new CorrispondentiGetServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url, _bindingFactory);
            var corrInsertSrv = new CorrispondentiInsertServiceWrapper(_protocolloLogs, _protocolloSerializer, _bindingFactory, vert.Username, vert.Password, vert.Url, Operatore);
            var protoSrv = new ProtocollazioneServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url, Operatore, vert.FormatoData, this._bindingFactory);

            var factory = ProtocollazioneFactory.Create(protoIn.Flusso, this.Anagrafiche, corrGetSrv, corrInsertSrv, Operatore, datiProto.Uo, vert.TipoProtocollazionePartenza);
            var service = new ProtocollazioneService(factory);
            var request = service.CreaRequest(protoIn, vert.EscludiClassifica);
            var response = service.Protocolla(protoSrv, request);

            var allSrv = new AllegatiServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url, Operatore, this._bindingFactory);
            service.InserisciAllegati(protoIn.RecuperaAllegati(), response.IdProtocollo, response.NumeroProtocollo, response.DataProtocollo, allSrv);

            return response;
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloApsystems>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var wrapper = new LeggiProtocolloServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Url, vert.Username, vert.Password, this._bindingFactory);
            var response = wrapper.LeggiProtocollo(leggiProtocolloRequest.IdProtocollo, leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo);
            var adapter = new LeggiProtocolloResponseAdapter();
            return adapter.Adatta(response, vert.FormatoData, vert.FormatoOra);
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var vert = new VerticalizzazioniWrapper(this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloApsystems>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var retVal = this.LeggiAllegatoDaLeggiProtocollo();
            var wrapper = new AllegatiServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url, "", this._bindingFactory);
            var buffer = wrapper.DownloadAllegato(IdAllegato, NumProtocollo, AnnoProtocollo);
            retVal.Image = buffer;
            return retVal;
        }
    }
}
