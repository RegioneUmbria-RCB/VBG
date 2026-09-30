using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata.Adapters;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Anagrafiche;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Legacy.EGrammata2.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    public class PROTOCOLLO_EGRAMMATA2 : ProtocolloBase
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_EGRAMMATA2(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloEgrammata2>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
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

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            try
            {
                var vert = new VerticalizzazioniConfiguration(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloEgrammata2>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
                var adapter = new LeggiProtocolloRequestAdapter().Adatta(leggiProtocolloRequest.NumeroProtocollo, leggiProtocolloRequest.AnnoProtocollo);

                var service = new LeggiProtocolloService(_protocolloLogs, _protocolloSerializer, vert);
                var response = service.LeggiProtocollo(adapter);

                var adapterOutput = new LeggiProtocolloResponseAdapter(response);

                return new List<DatiProtocolloLettoResponseType>() { adapterOutput.Adatta() };
            }
            catch (Exception ex)
            {
                throw _protocolloLogs.LogErrorException("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO", ex);
            }

        }
    }
}
