using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Allegati;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel2.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel2.TipiDocumento;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    internal class PROTOCOLLO_INSIEL2 : ProtocolloBase
    {
        const string SEPARATORE_ID_PROTOCOLLO = ";";
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_INSIEL2(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {   
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var allegatiSrv = new AllegatiService(vert.UrlUploadFile, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente, vert.Password);
            var adapterAllegati = new AllegatiAdapter(allegatiSrv, protoIn.RecuperaAllegati().ToList(), vert.CodiceUtente, vert.Password);
            var docs = adapterAllegati.Adatta();

            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);

            var adapter = new ProtocollazioneInputAdapter(vert, datiProto, _protocolloLogs, docs);
            var request = adapter.Adatta(codiceUfficio);

            var srv = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente, vert.Password);
            var response = srv.Protocolla(request);

            var adapterOut = new ProtocollazioneOutputAdapter(response, SEPARATORE_ID_PROTOCOLLO, _protocolloLogs);
            return adapterOut.Adatta();
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);

            ILeggiProtoInputAdapter leggiProtoAdapter;

            if (!String.IsNullOrEmpty(leggiProtocolloRequest.IdProtocollo))
            {
                var idProtocolloAdapter = new IdProtocolloAdapter(leggiProtocolloRequest.IdProtocollo, SEPARATORE_ID_PROTOCOLLO);
                var idProtoRequest = idProtocolloAdapter.Adatta();
                leggiProtoAdapter = new LeggiProtoIdInputAdapter(idProtoRequest);
            }
            else
                leggiProtoAdapter = new LeggiProtoNumeroAnnoInputAdapter(leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo, vert.CodiceRegistro, codiceUfficio);

            var request = leggiProtoAdapter.Adatta();

            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente, vert.Password);
            var adapterOutput = new LeggiProtocolloOutputAdapter(service);
            var retVal = adapterOutput.Adatta(request);

            return new List<DatiProtocolloLettoResponseType>() { retVal };
        }

        public override ListaTipiDocumentoResponseType GetTipiDocumento()
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            if (!vert.TipiDocumentoWs)
                return base.GetTipiDocumento();

            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente, vert.Password);
            var adapter = new TipiDocumentoOutputAdapter(service);
            return adapter.Adatta(vert.CodiceUtente, vert.Password);
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloInsiel>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var idProtocolloAdapter = new IdProtocolloAdapter(IdProtocollo, SEPARATORE_ID_PROTOCOLLO);
            var idProtoRequest = idProtocolloAdapter.Adatta();

            var downloadRequestAdapter = new DownloadDocumentoRequestAdapter();
            var request = downloadRequestAdapter.Adatta(idProtoRequest, Convert.ToInt64(IdAllegato));

            var service = new AllegatiService(vert.UrlUploadFile, _protocolloLogs, _protocolloSerializer, _bindingFactory, vert.CodiceUtente, vert.Password);
            var response = service.DownloadDocumento(request);

            var retVal = base.LeggiAllegatoDaLeggiProtocollo();
            retVal.Image = response.binaryData;

            return retVal;
        }
    }
}
