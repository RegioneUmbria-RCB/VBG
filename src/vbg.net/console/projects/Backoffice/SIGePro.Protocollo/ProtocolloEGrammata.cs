using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.EGrammata.Builders;
using Init.SIGePro.Protocollo.EGrammata.Services;
using Init.SIGePro.Protocollo.EGrammata.Configurations;
using Init.SIGePro.Protocollo.EGrammata.Segnatura.ProtoInput;
using Init.SIGePro.Protocollo.EGrammata.Adapters;
using Init.SIGePro.Protocollo.EGrammata.Segnatura.ProtoOutput;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.EGrammata.Segnatura.GetProtoOutput;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.WsDataClass;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_EGRAMMATA : ProtocolloBase
    {
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {

            try
            {
                var vert = new EGrammataVerticalizzazioniAdapter(_protocolloLogs, new VerticalizzazioneProtocolloEgrammata(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);
                var conf = new EGrammataSegnaturaProtoInputConfiguration(datiProto, protoIn.Allegati, protoIn.Oggetto, protoIn.Classifica, vert.Registro, protoIn.TipoDocumento, this.DatiProtocollo.Db, DatiProtocollo.IdComune);
                var builder = new EGrammataSegnaturaProtoBuilder(_protocolloLogs, _protocolloSerializer, conf);

                var service = new EGrammataProtocollazioneService(vert.UrlProto, _protocolloLogs, _protocolloSerializer);
                
                service.InserisciAllegati(protoIn.Allegati);
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
                _protocolloSerializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                var adapterOutput = new EGrammataProtocolloInsertOutputAdapter(response, _protocolloLogs);

                return adapterOutput.DatiProtocollo;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE", ex);
            }
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            try
            {
                var vert = new EGrammataVerticalizzazioniAdapter(_protocolloLogs, new VerticalizzazioneProtocolloEgrammata(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

                var service = new EGrattamataLeggiProtocolloService(vert.UrlLeggi, _protocolloLogs, _protocolloSerializer);
                var builder = new EGrammataSegnaturaLeggiProtoBuilder(_protocolloLogs, _protocolloSerializer, annoProtocollo, numeroProtocollo, vert.Registro);
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

                return adapter.DatiProtocollo;
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO", ex);
            }
        }
    }
}
