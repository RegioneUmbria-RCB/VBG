using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using VBG.Backend.Protocollo.AppLogic.Legacy.Datagraph;
using VBG.Backend.Protocollo.AppLogic.Legacy.Datagraph.LeggiProtocollo;
using VBG.Backend.Protocollo.AppLogic.Legacy.DocArea.Adapters;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.Verticalizzazioni.Legacy;

namespace VBG.Backend.Protocollo.AppLogic.Legacy
{
    /// <summary>
    /// Il protocollo datagraph è un'estenzione del protocollo docarea, per questo motivo deriva dalla classe PROTOCOLLO_DOCAREA, tutti i parametri (verticalizzazioni) 
    /// sono all'interno della regola PROTOCOLLO DOCAREA a meno che non siano specifici per funzionalità non presenti su tale standard.
    /// </summary>
    public class PROTOCOLLO_DATAGRAPH : PROTOCOLLO_DOCAREA
    {
        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;

        public PROTOCOLLO_DATAGRAPH(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
        }

        public override DatiProtocolloResponseType Protocollazione(DatiProtocolloIn protoIn)
        {
            return base.Protocollazione(protoIn);
        }

        public override List<DatiProtocolloLettoResponseType> LeggiProtocollo(LeggiProtocolloRequest leggiProtocolloRequest)
        {
            var vert = new DocAreaVerticalizzazioneParametriAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocarea>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));

            var auth = new AuthenticationServiceWrapper(vert.Url, base._protocolloLogs, base._protocolloSerializer);
            var token = auth.Login(vert.Codiceente, vert.Username, vert.Password);
            var service = new LeggiProtocolloService(vert.Url, token, base._protocolloLogs, base._protocolloSerializer);
            var response = service.LeggiProtocollo(Convert.ToInt32(leggiProtocolloRequest.NumeroProtocollo), Convert.ToInt32(leggiProtocolloRequest.AnnoProtocollo));

            var adapter = new LeggiProtocolloResponseAdapter();
            var retVal = adapter.Adatta(response.Registrazione);

            return new List<DatiProtocolloLettoResponseType>() { retVal };
        }

        public override AllegatoResponseType LeggiAllegato()
        {
            var vert = new DocAreaVerticalizzazioneParametriAdapter(_protocolloLogs, this._verticalizzazioniFactory.Create<VerticalizzazioneProtocolloDocarea>(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var auth = new AuthenticationServiceWrapper(vert.Url, base._protocolloLogs, base._protocolloSerializer);
            var token = auth.Login(vert.Codiceente, vert.Username, vert.Password);
            var service = new LeggiProtocolloService(vert.Url, token, base._protocolloLogs, base._protocolloSerializer);
            var response = service.LeggiProtocolloConAllegati(Convert.ToInt32(base.NumProtocollo), Convert.ToInt32(base.AnnoProtocollo));

            var adapter = new LeggiAllegatoAdapter();
            return adapter.Adatta(response, Convert.ToInt32(base.IdAllegato));
        }
    }
}
