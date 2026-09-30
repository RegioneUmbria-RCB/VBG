using Init.SIGePro.Manager;
using ProtocolloInsielMercatoService2;
using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.LeggiClassifiche;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.LeggiProtocollo.Identificativo;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Services;
using VBG.Backend.Protocollo.AppLogic.Core.InsielMercato2.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Backend.Protocollo.AppLogic.Shared.Managers;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_INSIELMERCATO2 : ProtocolloBase
    {
        public static class Constants
        {
            public const string SEPARATORE_ID_PROTOCOLLO = ";";
        }

        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_INSIELMERCATO2(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsielmercato>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiAnagrafici = DatiProtocolloInsertFactory.Create(protoIn);

            var ufficio = GetUfficioRegistro(datiAnagrafici.Uo);

            var sequenza = new ProtocolloSequenzeMgr(DatiProtocollo.Db).GetById(DatiProtocollo.IdComune, protoIn.Flusso, DatiProtocollo.Software, DatiProtocollo.CodiceComune);
            if (sequenza == null && String.IsNullOrEmpty(sequenza.Codicesequenza))
                throw new Exception("SEQUENZA NON PRESENTE");

            var utenteProtocollo = new user { code = vert.Username, password = vert.Password };

            var wrapper = new ProtocollazioneService(vert.Url, _protocolloLogs, _protocolloSerializer, utenteProtocollo, this._bindingFactory);
            var adapter = new ProtocollazioneAdapter(wrapper, datiAnagrafici, vert, ufficio, sequenza.Codicesequenza, DatiProtocollo, _protocolloLogs);

            var response = adapter.Adatta();

            var adapterResponse = new ProtocollazioneResponseAdapter(response, _protocolloLogs);
            return adapterResponse.Adatta();
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsielmercato>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var utenteProtocollo = new user { code = vert.Username, password = vert.Password };

            var identificativo = IdentificativoFactory.Create(leggiProtocolloRequest.IdProtocollo, Convert.ToInt32(leggiProtocolloRequest.NumeroProtocollo), Convert.ToInt32(leggiProtocolloRequest.AnnoProtocollo), this.DatiProtocollo);

            var request = new protocolDetailRequest { recordIdentifier = identificativo.GetRecordIdentifier() };

            var service = new ProtocollazioneService(vert.Url, _protocolloLogs, _protocolloSerializer, utenteProtocollo, this._bindingFactory);
            var response = service.LeggiProtocollo(request);

            var adapterResponse = new LeggiProtoResponseAdapter(response, DatiProtocollo.Db);

            return new List<DatiProtocolloLettoResponseType>() { adapterResponse.Adatta() };
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsielmercato>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var utenteProtocollo = new user { code = vert.Username, password = vert.Password };

            var identificativo = new IdentificativoId(IdProtocollo);
            var request = new protocolDetailRequest { recordIdentifier = identificativo.GetRecordIdentifier() };

            var service = new ProtocollazioneService(vert.Url, _protocolloLogs, _protocolloSerializer, utenteProtocollo, this._bindingFactory);
            var response = service.LeggiProtocollo(request);

            var documento = response.documentList.Where(x => x.name == IdAllegato).First();

            return new AllegatoResponseType
            {
                IDBase = documento.name,
                Serial = documento.name,
                Commento = Path.GetFileNameWithoutExtension(documento.name),
                ContentType = new OggettiMgr(this.DatiProtocollo.Db).GetContentType(documento.name),
                TipoFile = Path.GetExtension(documento.name).Replace(".", ""),
                Image = documento.file
            };
        }

        public override ListaTipiClassificaType GetClassifiche()
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsielmercato>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            if (!vert.UsaWsClassifiche)
            {
                _protocolloLogs.Debug("Parametro USA_WS_CLASSIFICHE valorizzato a 0 o non valorizzato, le classifiche non saranno recuperate dal web service");
                return base.GetClassifiche();
            }
            var utenteProtocollo = new user { code = vert.Username, password = vert.Password };
            var service = new ProtocollazioneService(vert.Url, _protocolloLogs, _protocolloSerializer, utenteProtocollo, this._bindingFactory);
            var response = service.LeggiClassifiche(LeggiClassificheRequestAdapter.Adatta());
            var adapterResponse = new LeggiClassificheResponseAdapter(response);

            return adapterResponse.Classifiche;
        }
    }
}