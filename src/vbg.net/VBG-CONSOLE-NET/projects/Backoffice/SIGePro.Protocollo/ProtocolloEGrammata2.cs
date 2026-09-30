using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.EGrammata2.Verticalizzazioni;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.EGrammata2.Anagrafiche;

namespace Init.SIGePro.Protocollo
{
    public class PROTOCOLLO_EGRAMMATA2 : ProtocolloBase
    {

        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloEgrammata2(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                IDatiProtocollo datiProto = DatiProtocolloInsertFactory.Create(protoIn);

                var leggiProtoService = new LeggiProtocolloService(_protocolloLogs, _protocolloSerializer, vert);
                var conf = new ProtocollazioneRequestConfiguration(datiProto, leggiProtoService, this.DatiProtocollo, vert);

                var anagraficheWrapper = new AnagraficheService(_protocolloLogs, _protocolloSerializer, vert);

                var adapter = new ProtocollazioneRequestAdapter(conf, anagraficheWrapper);
                var segnatura = adapter.AdattaSegnatura();
                var request = adapter.AdattaRequest(segnatura, _protocolloSerializer, _protocolloLogs);

                var service = new ProtocollazioneService(_protocolloLogs, _protocolloSerializer, vert);
                var response = service.Protocollazione(request);

                var adapterOut = new ProtocollazioneResponseAdapter(_protocolloLogs, response);

                return adapterOut.Adatta();
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
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, new VerticalizzazioneProtocolloEgrammata2(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var adapter = new LeggiProtocolloRequestAdapter().Adatta(numeroProtocollo, annoProtocollo);

                var service = new LeggiProtocolloService(_protocolloLogs, _protocolloSerializer, vert);
                var response = service.LeggiProtocollo(adapter);

                var adapterOutput = new LeggiProtocolloResponseAdapter(response);
                
                return adapterOutput.Adatta();
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO", ex);
            }
            
        }
    }
}
