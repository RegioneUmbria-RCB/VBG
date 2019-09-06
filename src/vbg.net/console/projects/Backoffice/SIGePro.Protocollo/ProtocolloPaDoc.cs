using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Data;
using System.Net;
using System.Net.Http;
using Init.SIGePro.Protocollo.PaDoc.Verticalizzazioni;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.PaDoc.Protocollazione;
using Init.SIGePro.Protocollo.PaDoc.Services;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.PaDoc.LeggiProtocollo;
using Init.SIGePro.Protocollo.PaDoc.Pec;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_PADOC : ProtocolloBase
    {

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloPadoc(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var datiProto = DatiProtocolloInsertFactory.Create(protoIn);

                var tipoProtocollazione = ProtocollazioneFactory.Create(this.DatiProtocollo, vert);

                var segnaturaAdapter = new SegnaturaAdapter(datiProto, vert, _protocolloSerializer, tipoProtocollazione, DatiProtocollo.IdComuneAlias, DatiProtocollo.Software);
                var segnatura = segnaturaAdapter.Adatta();

                var metadatiAdapter = new MetadatiAdapter(vert, datiProto, _protocolloSerializer);
                var metadati = metadatiAdapter.Adatta(segnatura, tipoProtocollazione, DatiProtocollo.IdComuneAlias, DatiProtocollo.Software);

                var serviceProtocollo = new ProtocolloService(vert.UrlProto, _protocolloLogs, _protocolloSerializer);
                serviceProtocollo.Protocolla(metadati, segnaturaAdapter.Flusso, vert.Verb);

                return new DatiProtocolloRes
                {
                    NumeroProtocollo = "-",
                    IdProtocollo = datiProto.Uo,
                    DataProtocollo = DateTime.Now.ToString("dd/MM/yyyy"),
                    AnnoProtocollo = "",
                    Warning = "LA RICHIESTA È STATA INOLTRATA CORRETTAMENTE AL SISTEMA PADOC, LA RISPOSTA, CONTENTE I RIFERIMENTI DEL PROTOCOLLO, VERRÀ RESTITUITA IN UN SECONDO MOMENTO SECONDO LE MODALITÀ CONFIGURATE NEL SISTEMA DI PROTOCOLLO."
                };
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE {0}", ex.Message), ex);
            }
        }

        public override AllOut LeggiAllegato()
        {
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloPadoc(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var service = new ProtocolloService(vert.UrlProto, _protocolloLogs, _protocolloSerializer);
                var response = service.LeggiAllegato(IdAllegato, AnnoProtocollo, NumProtocollo, IdProtocollo);

                return new AllOut { Image = response.ToArray(), Serial = IdAllegato };

            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException(String.Format("ERRORE GENERATO DURANTE LA LETTURA DELL'ALLEGATO: {0}, ERRORE: {1}", IdAllegato, ex.Message), ex);
            }
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloPadoc(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            
            var service = new LeggiProtocolloService(vert.UrlLeggi, _protocolloLogs, _protocolloSerializer);
            var response = service.LeggiProtocollo(vert.Username, vert.Password, numeroProtocollo, annoProtocollo);

            return LeggiProtocolloResponseAdapter.Adatta(response);
        }
    }
}
