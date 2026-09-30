using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata.Adapters;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata.Builders;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata.Configurations;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata.Segnatura.GetProtoOutput;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata.Segnatura.ProtoOutput;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata.Services;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class PROTOCOLLO_EGRAMMATA : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_EGRAMMATA(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {

            try
            {
                var vert = new EGrammataVerticalizzazioniAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloEgrammata>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                var conf = new EGrammataSegnaturaProtoInputConfiguration(datiProto, protoIn.RecuperaAllegati().ToList(), protoIn.Oggetto, protoIn.Classifica, vert.Registro, protoIn.TipoDocumento, this.DatiProtocollo.Db, DatiProtocollo.IdComune);
                var builder = new EGrammataSegnaturaProtoBuilder(_protocolloLogs, _protocolloSerializer, conf);

                var service = new EGrammataProtocollazioneService(vert.UrlProto, _protocolloLogs, _protocolloSerializer);

                service.InserisciAllegati(protoIn.RecuperaAllegati().ToList());
                string responseString64 = service.Protocollazione(vert.UserId, vert.Password, vert.IdUnita, vert.LivelliUnita, builder.SegnaturaXml64);

                var bufferRes = Convert.FromBase64String(responseString64);
                var responseString = Encoding.UTF8.GetString(bufferRes);

                _protocolloLogs.InfoFormat("Risposta del web service di inserimento protocollo, xml: {0}", responseString);

                Output_NuovaUD response;

                try
                {
                    response = (Output_NuovaUD)_protocolloSerializer.Deserialize(responseString, typeof(Output_NuovaUD));
                }
                catch (Exception)
                {
                    service.SollevaErrore(responseString);
                    throw;
                }

                _protocolloLogs.InfoFormat("PROTOCOLLAZIONE AVVENUTA CON SUCCESSO");
                _protocolloSerializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                var adapterOutput = new EGrammataProtocolloInsertOutputAdapter(response, _protocolloLogs);

                return adapterOutput.DatiProtocollo;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex);
            }
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            try
            {
                var vert = new EGrammataVerticalizzazioniAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloEgrammata>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                var service = new EGrattamataLeggiProtocolloService(vert.UrlLeggi, _protocolloLogs, _protocolloSerializer);
                var builder = new EGrammataSegnaturaLeggiProtoBuilder(_protocolloLogs, _protocolloSerializer, leggiProtocolloRequest.AnnoProtocollo, leggiProtocolloRequest.NumeroProtocollo, vert.Registro);
                var responseString64 = service.LeggiProtocollo(vert.UserId, vert.Password, vert.IdUnita, vert.LivelliUnita, builder.SegnaturaXml64);

                _protocolloLogs.InfoFormat("Risposta del web service a leggi protocollo, base64: {0}", responseString64);

                var bufferRes = Convert.FromBase64String(responseString64);
                var responseString = Encoding.UTF8.GetString(bufferRes);

                _protocolloLogs.InfoFormat("Risposta del web service xml: {0}", responseString);

                DatiUD response;

                try
                {
                    response = (DatiUD)_protocolloSerializer.Deserialize(responseString, typeof(DatiUD));
                }
                catch (Exception)
                {
                    service.SollevaErrore(responseString);
                    throw;
                }

                var adapter = new EGrammataLeggiProtoAdapter(response);

                return new List<DatiProtocolloLettoResponseType>() { adapter.DatiProtocollo };
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO", ex);
            }
        }
    }
}
