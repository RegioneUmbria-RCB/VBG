using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloFilesInsielService2;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;
using Init.SIGePro.Protocollo.Insiel2.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Insiel2.Services;
using Init.SIGePro.Protocollo.Insiel2.Allegati;
using Init.SIGePro.Protocollo.Insiel2.Protocollazione;
using Init.SIGePro.Protocollo.Insiel2.LeggiProtocollo;
using Init.SIGePro.Protocollo.Insiel2.TipiDocumento;

namespace Init.SIGePro.Protocollo
{
    internal class PROTOCOLLO_INSIEL2 : ProtocolloBase
    {
        const string SEPARATORE_ID_PROTOCOLLO = ";";

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var allegatiSrv = new AllegatiService(vert.UrlUploadFile, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente, vert.Password);
            var adapterAllegati = new AllegatiAdapter(allegatiSrv, protoIn.Allegati, vert.CodiceUtente, vert.Password);
            var docs = adapterAllegati.Adatta();

            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);

            var adapter = new ProtocollazioneInputAdapter(vert, datiProto, _protocolloLogs, docs);
            var request = adapter.Adatta(codiceUfficio);

            var srv = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente, vert.Password);
            var response = srv.Protocolla(request);

            var adapterOut = new ProtocollazioneOutputAdapter(response, SEPARATORE_ID_PROTOCOLLO, _protocolloLogs);
            return adapterOut.Adatta();
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            
            ILeggiProtoInputAdapter leggiProtoAdapter;

            if (!String.IsNullOrEmpty(idProtocollo))
            {
                var idProtocolloAdapter = new IdProtocolloAdapter(idProtocollo, SEPARATORE_ID_PROTOCOLLO);
                var idProtoRequest = idProtocolloAdapter.Adatta();
                leggiProtoAdapter = new LeggiProtoIdInputAdapter(idProtoRequest);
            }
                
            else
                leggiProtoAdapter = new LeggiProtoNumeroAnnoInputAdapter(numeroProtocollo, annoProtocollo);

            var request = leggiProtoAdapter.Adatta();

            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente, vert.Password);
            var adapterOutput = new LeggiProtocolloOutputAdapter(service);
            var retVal = adapterOutput.Adatta(request);

            return retVal;
        }

        public override ListaTipiDocumento GetTipiDocumento()
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            if (!vert.TipiDocumentoWs)
                return base.GetTipiDocumento();

            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente, vert.Password);
            var adapter = new TipiDocumentoOutputAdapter(service);
            return adapter.Adatta(vert.CodiceUtente, vert.Password);
        }

        public override AllOut LeggiAllegato()
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var idProtocolloAdapter = new IdProtocolloAdapter(IdProtocollo, SEPARATORE_ID_PROTOCOLLO);
            var idProtoRequest = idProtocolloAdapter.Adatta();

            var downloadRequestAdapter = new DownloadDocumentoRequestAdapter();
            var request = downloadRequestAdapter.Adatta(idProtoRequest, Convert.ToInt64(IdAllegato));

            var service = new AllegatiService(vert.UrlUploadFile, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente, vert.Password);
            var response = service.DownloadDocumento(request);

            var retVal = base.GetAllegato();
            retVal.Image = response.binaryData;

            return retVal;
        }
    }
}
