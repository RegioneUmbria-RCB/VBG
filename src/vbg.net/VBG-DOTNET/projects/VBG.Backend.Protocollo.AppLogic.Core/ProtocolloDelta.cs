using VBG.Backend.Protocollo.Verticalizzazioni.Core;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Core.Delta.Adapters;
using VBG.Backend.Protocollo.AppLogic.Core.Delta.Builders;
using VBG.Backend.Protocollo.AppLogic.Core.Delta.Services;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using SIGePro.Manager.VerticalizzazioniBase;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core
{
    public class PROTOCOLLO_DELTA : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly IBindingFactory _bindingFactory;

        public PROTOCOLLO_DELTA(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._bindingFactory = bindingFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {
                var vert = new DeltaVerticalizzazioneParametriAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDelta>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                var configuration = new DeltaSegnaturaParamConfiguration(vert, datiProto, protoIn.Oggetto, protoIn.Classifica, protoIn.TipoDocumento, _protocolloLogs);
                var segnatura = new DeltaSegnaturaBuilder(_protocolloLogs, _protocolloSerializer, configuration);

                var protoSrv = new DeltaProtocollazioneService(vert.Url, _protocolloLogs, _protocolloSerializer, this._bindingFactory, vert.Username, vert.Password, ProxyAddress);
                var responseProto = protoSrv.Protocolla(segnatura.SegnaturaRequest);

                var allegatiSrv = new DeltaAllegatiService(vert.Url, _protocolloLogs, _protocolloSerializer, this._bindingFactory, vert.Username, vert.Password, ProxyAddress);
                allegatiSrv.UploadAllegati(protoIn.RecuperaAllegati().ToList(), vert.Registro, responseProto.Anno, responseProto.Progressivo, responseProto.DataProtocollo.ToString("dd/MM/yyyy"));

                var responseAdapter = new DeltaProtocolloOutputAdapter(responseProto);

                return responseAdapter.DatiProtocollo;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex);
            }
        }

        public override ListaTipiDocumentoResponseType GetTipiDocumento()
        {
            try
            {
                //return base.GetTipiDocumento();

                var vert = new DeltaVerticalizzazioneParametriAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDelta>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                var tipiDocSrv = new DeltaTipiDocumentiService(vert.Url, _protocolloLogs, _protocolloSerializer, this._bindingFactory, vert.Username, vert.Password, ProxyAddress);
                var listTipiDocWs = tipiDocSrv.GetTipidocumenti();

                if (listTipiDocWs == null)
                {
                    _protocolloLogs.WarnFormat("NON E' STATO RESTITUITO NESSUN TIPO DOCUMENTO DAL WEB SERVICE");
                    return base.GetTipiDocumento();
                }

                var documenti = new List<ListaTipiDocumentoDocumentoType>();

                listTipiDocWs.ToList().ForEach(x => documenti.Add(new ListaTipiDocumentoDocumentoType { Codice = x.Codice, Descrizione = x.Descrizione }));

                var retVal = new ListaTipiDocumentoResponseType { Documento = documenti.ToArray() };

                return retVal;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE IL RECUPERO DELLE TIPOLOGIE DI DOCUMENTI (TIPO LETTERE)", ex);
            }
        }


        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            try
            {
                var vert = new DeltaVerticalizzazioneParametriAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDelta>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var protoLetto = new DeltaProtocollazioneService(vert.Url, _protocolloLogs, _protocolloSerializer, this._bindingFactory, vert.Username, vert.Password, ProxyAddress);
                var response = protoLetto.LeggiProtocollo(vert.Registro, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo);

                var tipoDocSrv = new DeltaTipiDocumentiService(vert.Url, _protocolloLogs, _protocolloSerializer, this._bindingFactory, vert.Username, vert.Password, ProxyAddress);

                var datiProtoLetto = new DeltaProtocolloLettoAdapter(response, tipoDocSrv, _protocolloLogs, DatiProtocollo.Db);

                return new List<DatiProtocolloLettoResponseType>() { datiProtoLetto.ProtocolloLetto };
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("SI E' VERIFICATO UN ERRORE DURANTE LA LETTURA DEL PROTOCOLLO NUMERO: {0}, ANNO: {1}", leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo), ex);
            }
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            try
            {
                var vert = new DeltaVerticalizzazioneParametriAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDelta>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var allegatiSrv = new DeltaAllegatiService(vert.Url, _protocolloLogs, _protocolloSerializer, this._bindingFactory, vert.Username, vert.Password, ProxyAddress);
                var response = allegatiSrv.GetAllegato(vert.Registro, AnnoProtocollo, NumProtocollo, IdAllegato);

                var allegato = new AllegatoResponseType { Serial = response.name, Image = response.file };

                return allegato;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA LETTURA DELL'ALLEGATO CON ID {0}", IdAllegato.ToString()), ex);
            }
        }
    }
}
