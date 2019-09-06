using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloApSystemsService;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.ApSystems;
using System.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ApSystems.Protocollazione;
using Init.SIGePro.Protocollo.ApSystems.Protocollazione.Corrispondenti.Get;
using Init.SIGePro.Protocollo.ApSystems.Protocollazione.Corrispondenti.Insert;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.ApSystems.Allegati;
using Init.SIGePro.Protocollo.ApSystems.LeggiProtocollo;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_APSYSTEMS : ProtocolloBase
    {
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloApsystems(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var corrGetSrv = new CorrispondentiGetServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url);
            var corrInsertSrv = new CorrispondentiInsertServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url, Operatore);
            var protoSrv = new ProtocollazioneServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url, Operatore, vert.FormatoData);

            var factory = ProtocollazioneFactory.Create(protoIn.Flusso, this.Anagrafiche, corrGetSrv, corrInsertSrv, Operatore, datiProto.Uo, vert.TipoProtocollazionePartenza);
            var service = new ProtocollazioneService(factory);
            var request = service.CreaRequest(protoIn, vert.EscludiClassifica);
            var response = service.Protocolla(protoSrv, request);

            var allSrv = new AllegatiServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url, Operatore);
            service.InserisciAllegati(protoIn.Allegati, response.IdProtocollo, response.NumeroProtocollo, response.DataProtocollo, allSrv);

            return response;
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloApsystems(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var wrapper = new LeggiProtocolloServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Url, vert.Username, vert.Password);
            var response = wrapper.LeggiProtocollo(idProtocollo, numeroProtocollo, annoProtocollo);
            var adapter = new LeggiProtocolloResponseAdapter();
            return adapter.Adatta(response, vert.FormatoData, vert.FormatoOra);
        }

        public override AllOut LeggiAllegato()
        {
            var vert = new VerticalizzazioniWrapper(new VerticalizzazioneProtocolloApsystems(this.DatiProtocollo.IdComuneAlias, this.DatiProtocollo.Software, this.DatiProtocollo.CodiceComune));
            var retVal = this.GetAllegato();
            var wrapper = new AllegatiServiceWrapper(_protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, vert.Url, "");
            var buffer = wrapper.DownloadAllegato(IdAllegato, NumProtocollo, AnnoProtocollo);
            retVal.Image = buffer;
            return retVal;
        }
    }
}
