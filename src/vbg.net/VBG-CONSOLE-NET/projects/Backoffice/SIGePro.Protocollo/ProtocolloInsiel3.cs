using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Insiel3.Verticalizzazioni;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Insiel3.Allegati;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.Insiel3.Protocollazione;
using Init.SIGePro.Protocollo.Insiel3.TipiDocumento;
using Init.SIGePro.Protocollo.Insiel3.LeggiProtocollo;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_INSIEL3 : ProtocolloBase
    {
        const string SEPARATORE_ID_PROTOCOLLO = ";";

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

            var allegatiSrv = new AllegatiService(vert.UrlUploadFile, _protocolloLogs, _protocolloSerializer);
            var adapterAllegati = new AllegatiAdapter(allegatiSrv, protoIn.Allegati);
            var docs = adapterAllegati.Adatta();
            var srv = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente);
            var adapter = new ProtocollazioneInputAdapter(vert, datiProto, docs, srv);
            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);
            var request = adapter.Adatta(codiceUfficio);
            var response = srv.Protocolla(request);

            var adapterOut = new ProtocollazioneOutputAdapter(response, SEPARATORE_ID_PROTOCOLLO, _protocolloLogs);
            return adapterOut.Adatta();
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente);
            var codiceUfficio = GetUfficioRegistro(vert.CodiceRegistro);
            var factory = LeggiProtocolloFactory.Create(idProtocollo, numeroProtocollo, annoProtocollo, codiceUfficio, vert.CodiceRegistro, service, _protocolloLogs);
            var response = factory.Leggi();
            var adapterOutput = new LeggiProtocolloOutputAdapter(response);
            return adapterOutput.Adatta();
        }

        public override AllOut LeggiAllegato()
        {

            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var serviceProto = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente);
            
            var idProtoAdapter = new IdProtocolloAdapter(IdProtocollo, SEPARATORE_ID_PROTOCOLLO);
            var requestLeggiProto = idProtoAdapter.Adatta();

            var requestDownloadDoc = new DownloadDocumentoRequest
            {
                idDoc = Convert.ToInt64(IdAllegato),
                registrazione = new ProtocolloRequest
                {
                    Item = new IdProtocollo
                    {
                        progDoc = requestLeggiProto.ProgDoc,
                        progMovi = requestLeggiProto.ProgMovi
                    }
                }
            };

            var responseDownloadDoc = serviceProto.DownloadDocumento(requestDownloadDoc);
            
            var service = new AllegatiService(vert.UrlUploadFile, _protocolloLogs, _protocolloSerializer);
            var response = service.Download(responseDownloadDoc.idFile);

            var retVal = new AllOut { Image = response.binaryData, IDBase = responseDownloadDoc.idFile, Serial = responseDownloadDoc.name, Commento = responseDownloadDoc.name };
            return retVal;
        }

        public override ListaTipiDocumento GetTipiDocumento()
        {
            var vert = new InsielVerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloInsiel(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            if (!vert.TipiDocumentoWs)
                return base.GetTipiDocumento();

            var service = new ProtocolloService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.CodiceUtente);
            var adapter = new TipiDocumentoOutputAdapter(service);
            return adapter.Adatta(vert.CodiceUtente, vert.Password);
        }
    }
}
