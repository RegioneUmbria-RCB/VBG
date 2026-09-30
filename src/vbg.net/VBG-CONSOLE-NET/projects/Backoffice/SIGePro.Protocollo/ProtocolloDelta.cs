using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Delta.Adapters;
using Init.SIGePro.Protocollo.Delta.Builders;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.Delta.Services;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_DELTA : ProtocolloBase
    {

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {
                var vert = new DeltaVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloDelta(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                var configuration = new DeltaSegnaturaParamConfiguration(vert, datiProto, protoIn.Oggetto, protoIn.Classifica, protoIn.TipoDocumento, _protocolloLogs);
                var segnatura = new DeltaSegnaturaBuilder(_protocolloLogs, _protocolloSerializer, configuration);

                var protoSrv = new DeltaProtocollazioneService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, ProxyAddress);
                var responseProto = protoSrv.Protocolla(segnatura.SegnaturaRequest);

                var allegatiSrv = new DeltaAllegatiService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, ProxyAddress);
                allegatiSrv.UploadAllegati(protoIn.Allegati, vert.Registro, responseProto.Anno, responseProto.Progressivo, responseProto.DataProtocollo.ToString("dd/MM/yyyy"));

                var responseAdapter = new DeltaProtocolloOutputAdapter(responseProto);

                return responseAdapter.DatiProtocollo;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex);
            }
        }

        public override ListaTipiDocumento GetTipiDocumento()
        {
            try
            {
                //return base.GetTipiDocumento();

                var vert = new DeltaVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloDelta(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                
                var tipiDocSrv = new DeltaTipiDocumentiService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, ProxyAddress);
                var listTipiDocWs = tipiDocSrv.GetTipidocumenti();

                if (listTipiDocWs == null)
                {
                    _protocolloLogs.WarnFormat("NON E' STATO RESTITUITO NESSUN TIPO DOCUMENTO DAL WEB SERVICE");
                    return base.GetTipiDocumento();
                }

                var documenti = new List<ListaTipiDocumentoDocumento>();

                listTipiDocWs.ToList().ForEach(x => documenti.Add(new ListaTipiDocumentoDocumento { Codice = x.Codice, Descrizione = x.Descrizione }));

                var retVal = new ListaTipiDocumento { Documento = documenti.ToArray() };

                return retVal;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DELLE TIPOLOGIE DI DOCUMENTI (TIPO LETTERE)", ex);
            }
        }


        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                var vert = new DeltaVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloDelta(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var protoLetto = new DeltaProtocollazioneService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, ProxyAddress);
                var response = protoLetto.LeggiProtocollo(vert.Registro, annoProtocollo, numeroProtocollo);

                var tipoDocSrv = new DeltaTipiDocumentiService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, ProxyAddress);

                var datiProtoLetto = new DeltaProtocolloLettoAdapter(response, tipoDocSrv, _protocolloLogs, DatiProtocollo.Db);

                return datiProtoLetto.ProtocolloLetto;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("SI E' VERIFICATO UN ERRORE DURANTE LA LETTURA DEL PROTOCOLLO NUMERO: {0}, ANNO: {1}", numeroProtocollo, annoProtocollo), ex);
            }
        }

        public override AllOut LeggiAllegato()
        {
            try
            {
                var vert = new DeltaVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloDelta(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var allegatiSrv = new DeltaAllegatiService(vert.Url, _protocolloLogs, _protocolloSerializer, vert.Username, vert.Password, ProxyAddress);
                var response = allegatiSrv.GetAllegato(vert.Registro, AnnoProtocollo, NumProtocollo, IdAllegato);

                var allegato = new AllOut { Serial = response.name, Image = response.file };

                return allegato;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA LETTURA DELL'ALLEGATO CON ID {0}", IdAllegato.ToString()), ex);
            }
        }
    }
}
