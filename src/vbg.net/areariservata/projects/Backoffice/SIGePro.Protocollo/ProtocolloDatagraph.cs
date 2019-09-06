using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Datagraph;
using Init.SIGePro.Protocollo.Datagraph.LeggiProtocollo;
using Init.SIGePro.Protocollo.DocArea.Adapters;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Verticalizzazioni;

namespace Init.SIGePro.Protocollo
{
    /// <summary>
    /// Il protocollo datagraph è un'estenzione del protocollo docarea, per questo motivo deriva dalla classe PROTOCOLLO_DOCAREA, tutti i parametri (verticalizzazioni) 
    /// sono all'interno della regola PROTOCOLLO DOCAREA a meno che non siano specifici per funzionalità non presenti su tale standard.
    /// </summary>
    public class PROTOCOLLO_DATAGRAPH : PROTOCOLLO_DOCAREA
    {
        public override DatiProtocolloRes Protocollazione(DatiProtocolloIn protoIn)
        {
            return base.Protocollazione(protoIn);
        }

        public override DatiProtocolloLetto LeggiProtocollo(string idProtocollo, string annoProtocollo, string numeroProtocollo)
        {
            var vert = new DocAreaVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloDocarea(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var auth = new AuthenticationServiceWrapper(vert.Url, base._protocolloLogs, base._protocolloSerializer);
            var token = auth.Login(vert.Codiceente, vert.Username, vert.Password);
            var service = new LeggiProtocolloService(vert.Url, token, base._protocolloLogs, base._protocolloSerializer);
            var response = service.LeggiProtocollo(Convert.ToInt32(numeroProtocollo), Convert.ToInt32(annoProtocollo));

            var adapter = new LeggiProtocolloResponseAdapter();
            var retVal = adapter.Adatta(response.Registrazione);

            return retVal;
        }

        public override AllOut LeggiAllegato()
        {
            var vert = new DocAreaVerticalizzazioneParametriAdapter(_protocolloLogs, new VerticalizzazioneProtocolloDocarea(DatiProtocollo.IdComuneAlias, DatiProtocollo.Software, DatiProtocollo.CodiceComune));
            var auth = new AuthenticationServiceWrapper(vert.Url, base._protocolloLogs, base._protocolloSerializer);
            var token = auth.Login(vert.Codiceente, vert.Username, vert.Password);
            var service = new LeggiProtocolloService(vert.Url, token, base._protocolloLogs, base._protocolloSerializer);
            var response = service.LeggiProtocolloConAllegati(Convert.ToInt32(base.NumProtocollo), Convert.ToInt32(base.AnnoProtocollo));

            var adapter = new LeggiAllegatoAdapter();
            return adapter.Adatta(response, Convert.ToInt32(base.IdAllegato));
        }
    }
}
